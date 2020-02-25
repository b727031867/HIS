package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DoctorException;
import com.gxf.his.mapper.dao.*;
import com.gxf.his.po.generate.*;
import com.gxf.his.po.vo.DoctorVo;
import com.gxf.his.po.vo.PatientVo;
import com.gxf.his.po.vo.TicketVo;
import com.gxf.his.service.DoctorService;
import com.gxf.his.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-20
 */
@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    @Resource
    private ITicketMapper iTicketMapper;
    @Resource
    private IDoctorMapper iDoctorMapper;
    @Resource
    private UserService userService;
    @Resource
    private IPatientMapper iPatientMapper;
    @Resource
    private IPrescriptionMapper iPrescriptionMapper;
    @Resource
    private ICheckItemInfoMapper iCheckItemInfoMapper;
    @Resource
    private IPatientMedicalRecord iPatientMedicalRecord;

    @Override
    public List<PatientVo> getOutpatients(Long doctorId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        //根据挂号激活时间（开始排队的时间）获取患者列表，包含就诊中的与就诊完毕的人
        List<DoctorTicket> tickets = iTicketMapper.selectTicketByActiveTimeAndDoctorId(doctorId, startDate, endDate);
        List<PatientVo> patients = new ArrayList<>(16);
        for (DoctorTicket ticket : tickets) {
            PatientVo patientVo = new PatientVo();
            //获取患者信息
            Patient patient = iPatientMapper.selectByPrimaryKey(ticket.getPatientId());
            patientVo.setPatient(patient);
            //获取处方单信息
            List<Prescription> prescriptions = iPrescriptionMapper.selectPrescriptionByDoctorIdAndPatientIdAndRange(doctorId, patient.getPatientId(), startDate, endDate);
            patientVo.setPrescriptions(prescriptions);
            //获取检查单信息
            List<CheckItemInfo> checkItemInfos = iCheckItemInfoMapper.selectCheckItemInfoByDoctorIdAndPatientIdAndRange(doctorId, patient.getPatientId(), startDate, endDate);
            patientVo.setCheckItemInfos(checkItemInfos);
            //电子病历信息
            List<PatientMedicalRecord> patientMedicalRecords = iPatientMedicalRecord.selectPatientMedicalRecordByDoctorIdAndPatientIdAndRange(doctorId, patient.getPatientId(), startDate, endDate);
            patientVo.setPatientMedicalRecords(patientMedicalRecords);
            patients.add(patientVo);
        }
        return patients;
    }

    @Override
    public TicketVo getCurrentRankPatient(Long doctorId, Integer rank) {
        //如果为0 表示当前是第一次打开就诊页面或者没有候诊中的患者而有叫号中的患者
        if (rank == 0) {
            //优先查找叫号中的病人，所以找出挂号时间最早的叫号中的病人
            TicketVo ticketVo = iTicketMapper.getPatientByShouldBeCalled(doctorId, 6);
            //如果没有叫号中，则查看有没有候诊病人
            if (ticketVo == null) {
                //获取候诊中的的病人
                TicketVo waitingTicket = iTicketMapper.getPatientByShouldBeCalled(doctorId, 4);
                waitingTicket.setStatus(6);
                iTicketMapper.updateByPrimaryKey(waitingTicket);
                return waitingTicket;
            } else {
                //有叫号中的患者，则将叫号中的患者返回开始就诊
                return ticketVo;
            }
        }
        TicketVo currentPatient = iTicketMapper.getQueuePatientByDoctorIdAndRank(doctorId, rank);
        currentPatient.setDoctorId(currentPatient.getDoctorVo().getDoctorId());
        currentPatient.setPatientId(currentPatient.getPatient().getPatientId());
        //设置为叫号中
        currentPatient.setStatus(6);
        iTicketMapper.updateByPrimaryKey(currentPatient);
        //设置此排名之前的排队中的人错过叫号
        List<TicketVo> doctorTickets = iTicketMapper.getUsingTicketsByDoctorId(doctorId);
        for (TicketVo doctorTicket : doctorTickets) {
            if (doctorTicket.getTicketNumber() != null) {
                //让小于等于上一位rank并且在排队中的挂号者错过挂号
                if (doctorTicket.getTicketNumber() < rank) {
                    doctorTicket.setStatus(2);
                    doctorTicket.setPatientId(doctorTicket.getPatient().getPatientId());
                    iTicketMapper.updateByPrimaryKey(doctorTicket);
                }
            } else {
                log.error("数据异常！出现排队但是却没有计算排名的情况!");
                throw new DoctorException(ServerResponseEnum.DOCTOR_CALL_FAIL);
            }
        }
        return currentPatient;
    }

    @Override
    public TicketVo getCallingPatient(Long doctorId) {
        try {
            return iTicketMapper.getCallingPatient(doctorId);
        } catch (Exception e) {
            log.error("叫号中票务信息查询失败", e);
            throw new DoctorException(ServerResponseEnum.TICKET_LIST_FAIL);
        }

    }

    @Override
    public HashMap<String, String> getCurrentRankInfo(Long doctorId, Integer status) {
        TicketVo nextUsingTicketRank = iTicketMapper.getNextUsingTicketRank(doctorId);
        TicketVo ticketTotalRank = iTicketMapper.countTicketTotalRank(doctorId, status);
        HashMap<String, String> map = new HashMap<>(2);
        //如果没有候诊患者,当前就诊的患者应该是最后一位就诊中的患者
        if (nextUsingTicketRank == null) {
            //获取最后一位就诊中的患者挂号信息
            TicketVo ticketVo = iTicketMapper.countLastTicketSeeing(doctorId, 5);
            //如果最后一位就诊中的患者也不存在，那说明当前没有人进行就诊,大家都已经完成就诊
            if (ticketVo == null) {
                map.put("currentRank", null);
            } else {
                map.put("currentRank", String.valueOf(ticketVo.getTicketNumber()));
            }
            map.put("rankTotal", String.valueOf(0));
            return map;
        }
        if (nextUsingTicketRank.getTicketNumber() != null) {
            //当前就诊的名次应该是将要的就诊的候诊患者的排名
            map.put("currentRank", String.valueOf(nextUsingTicketRank.getTicketNumber()));
            map.put("rankTotal", String.valueOf(ticketTotalRank.getRank()));
            return map;
        } else {
            throw new DoctorException(ServerResponseEnum.DOCTOR_GET_RANK_FAIL);
        }
    }

    @Override
    public Integer getTotalRank(Long doctorId, Integer status) {
        try {
            TicketVo ticketVo = iTicketMapper.countTicketTotalRank(doctorId, status);
            //没查到表示没有患者排队候诊
            if (ticketVo == null) {
                return 0;
            }
            return ticketVo.getRank();
        } catch (Exception e) {
            log.error("获取某医生候诊中或叫号中的患者总数失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_GET_TOTAL_RANK_FAIL);
        }
    }

    @Override
    public int addDoctor(Doctor doctor) throws DoctorException {
        try {
            return iDoctorMapper.insert(doctor);
        } catch (Exception e) {
            log.error("添加医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_SAVE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDoctorAndUser(Long doctorId, Long userId) throws Exception {
        int a = 0;
        int b = 2;
        a = iDoctorMapper.deleteByPrimaryKey(doctorId) + a;
        a = userService.deleteUser(userId) + a;
        if (a != b) {
            log.warn("删除时，没有找到ID为" + doctorId + "的用户或ID为" + userId + "的医生！");
            throw new Exception();
        }
        return b;
    }

    @Override
    @Transactional(rollbackFor = DoctorException.class)
    public Integer deleteDoctorAndUserBatch(List<Doctor> doctors, List<User> users) {
        try {
            Integer a;
            a = iDoctorMapper.batchDoctorDelete(doctors);
            a = userService.deleteUserBatch(users) + a;
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DoctorException(ServerResponseEnum.DOCTOR_DELETE_FAIL);
        }
    }

    @Override
    public DoctorVo getDoctorByDoctorId(Long doctorId) {
        try {
            return iDoctorMapper.selectByPrimaryKeyRelated(doctorId);
        } catch (Exception e) {
            log.error("根据ID查找医生失败");
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
    }

    @Override
    public List<DoctorVo> getAllDoctors() {
        List<DoctorVo> doctors;
        try {
            doctors = iDoctorMapper.selectAllDoctorsInfo();
        } catch (Exception e) {
            log.error("查询所有医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public List<DoctorVo> getDoctorsByDepartmentCode(String departmentCode) {
        List<DoctorVo> doctors;
        try {
            doctors = iDoctorMapper.selectDoctorByDepartmentCode(departmentCode);
        } catch (Exception e) {
            log.error("按部门查询医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public List<DoctorVo> selectDoctorByAttribute(DoctorVo doctorVo) {
        List<DoctorVo> doctors;
        try {
            doctors = iDoctorMapper.selectDoctorByAttribute(doctorVo);
        } catch (Exception e) {
            log.error("按属性查询医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
        return doctors;
    }

    @Override
    public int updateDoctor(Doctor doctor) {
        try {
            return iDoctorMapper.updateByPrimaryKey(doctor);
        } catch (Exception e) {
            log.error("医生信息更新失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_UPDATE_FAIL);
        }
    }

    @Override
    public DoctorTicket endSeeDoctor(Long doctorTicketId) {
        try {
            TicketVo doctorTicket = iTicketMapper.selectByPrimaryKeyRelative(doctorTicketId);
            //设置为已经完成
            doctorTicket.setStatus(1);
            iTicketMapper.updateByPrimaryKey(doctorTicket);
            return doctorTicket;
        } catch (Exception e) {
            log.error("挂号状态修改失败", e);
            throw new DoctorException(ServerResponseEnum.TICKET_CHANGE_STATUS_FAIL);
        }
    }

    @Override
    public DoctorTicket usedRecentVisitingDoctorTicket(String doctorId) {
        try {
            Long id = Long.parseLong(doctorId);
            TicketVo ticketVo = iTicketMapper.getPatientByShouldBeCalled(id, 5);
            //当前没有需要就诊完毕的挂号
            if (ticketVo == null) {
                return null;
            }
            TicketVo doctorTicket = iTicketMapper.selectByPrimaryKeyRelative(ticketVo.getTicketId());
            doctorTicket.setStatus(1);
            iTicketMapper.updateByPrimaryKey(doctorTicket);
            return doctorTicket;
        } catch (Exception e) {
            log.error("让就诊中的患者完成就诊失败", e);
            throw new DoctorException(ServerResponseEnum.TICKET_CHANGE_STATUS_FAIL);
        }

    }

    @Override
    public void saveCaseHistory(Long doctorTicketId, String content) {
        try {
            TicketVo ticketVo = iTicketMapper.selectByPrimaryKeyRelative(doctorTicketId);
            //解析content，转换成电子病历
            Document document = Jsoup.parse(content);
            String zs = document.getElementById("zs").text();
            String xbs = document.getElementById("xbs").text();
            String jws = document.getElementById("jws").text();
            String tgjc = document.getElementById("tgjc").text();
            String fzjc = document.getElementById("fzjc").text();
            String zdyj = document.getElementById("zdyj").text();
            String zlyj = document.getElementById("zlyj").text();
            String narrator = document.getElementById("narrator").text();
            PatientMedicalRecord patientMedicalRecord = new PatientMedicalRecord();
            patientMedicalRecord.setNarrator(narrator.substring(narrator.indexOf("：") + 1));
            patientMedicalRecord.setChiefComplaint(zs.substring(zs.indexOf("：") + 1));
            patientMedicalRecord.setPastHistory(jws.substring(jws.indexOf("：") + 1));
            patientMedicalRecord.setCurrentMedicalHistory(xbs.substring(xbs.indexOf("：") + 1));
            //辅助检查的结果
            patientMedicalRecord.setAuxiliaryInspection(fzjc.substring(fzjc.indexOf("：") + 1));
            patientMedicalRecord.setTgjc(tgjc.substring(tgjc.indexOf("：") + 1));
            //诊断意见
            patientMedicalRecord.setDiagnosis(zdyj.substring(zdyj.indexOf("：") + 1));
            //诊疗意见
            patientMedicalRecord.setZlyj(zlyj.substring(zlyj.indexOf("：") + 1));
            patientMedicalRecord.setDoctorId(ticketVo.getDoctorVo().getDoctorId());
            patientMedicalRecord.setPatientId(ticketVo.getPatient().getPatientId());
            patientMedicalRecord.setCreateDatetime(new Date());
            iPatientMedicalRecord.insertAndInjectPrimaryKey(patientMedicalRecord);
        } catch (Exception e) {
            log.error("病历插入失败!", e);
            throw new DoctorException(ServerResponseEnum.PATIENT_FILE_SAVE_FAIL);
        }

    }


}
