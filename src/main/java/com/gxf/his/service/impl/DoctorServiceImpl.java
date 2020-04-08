package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DoctorException;
import com.gxf.his.mapper.dao.*;
import com.gxf.his.po.generate.*;
import com.gxf.his.po.vo.DoctorVo;
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
    private IPrescriptionInfoMapper iPrescriptionInfoMapper;
    @Resource
    private IPrescriptionExtraCostMapper iPrescriptionExtraCostMapper;
    @Resource
    private ICheckItemInfoMapper iCheckItemInfoMapper;
    @Resource
    private IPatientMedicalRecordMapper iPatientMedicalRecordMapper;
    @Resource
    private IDoctorSchedulingMapper iDoctorSchedulingMapper;

    @Override
    public List<TicketVo> getOutpatients(Long doctorId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        //根据挂号激活时间（开始排队的时间）获取患者列表，包含就诊中的与就诊完毕的人
        List<DoctorTicket> tickets = iTicketMapper.selectTicketHistory(doctorId, startDate, endDate);
        List<TicketVo> ticketVos = new ArrayList<>(tickets.size());
        for (DoctorTicket ticket : tickets) {
            TicketVo ticketVo = new TicketVo();
            ticketVo.setRank(ticket.getTicketNumber());
            ticketVo.setTicketId(ticket.getTicketId());
            ticketVo.setTicketNumber(ticket.getTicketNumber());
            ticketVo.setTicketType(ticket.getTicketType());
            ticketVo.setTicketCreateTime(ticket.getTicketCreateTime());
            ticketVo.setActiveTime(ticket.getActiveTime());
            ticketVo.setTicketValidityStart(ticket.getTicketValidityStart());
            ticketVo.setTicketValidityEnd(ticket.getTicketValidityEnd());
            ticketVo.setDoctorId(ticket.getDoctorId());
            ticketVo.setPatientId(ticket.getPatientId());
            ticketVo.setRegisteredResourceId(ticket.getRegisteredResourceId());
            ticketVo.setOrderId(ticket.getOrderId());
            ticketVo.setStatus(ticket.getStatus());
            //获取患者信息
            Patient patient = iPatientMapper.selectByPrimaryKey(ticket.getPatientId());
            ticketVo.setPatient(patient);
            //获取处方单信息
            Prescription prescription = iPrescriptionMapper.selectPrescriptionByTicketId(ticket.getTicketId());
            ticketVo.setPrescription(prescription);
            //获取检查单信息
            List<CheckItemInfo> checkItemInfos = iCheckItemInfoMapper.selectCheckItemInfosByTicketId(ticket.getTicketId());
            ticketVo.setCheckItemInfos(checkItemInfos);
            //电子病历信息
            PatientMedicalRecord patientMedicalRecord = iPatientMedicalRecordMapper.selectPatientMedicalRecordByTicketId(ticket.getTicketId());
            ticketVo.setPatientMedicalRecord(patientMedicalRecord);
            ticketVos.add(ticketVo);
        }
        return ticketVos;
    }


    @Override
    public TicketVo getCallingPatient(Long doctorId) {
        try {
            TicketVo callingPatient = iTicketMapper.getCallingPatient(doctorId);
            //将该叫号中得信息改为就诊中
            callingPatient.setStatus(5);
            iTicketMapper.updateByPrimaryKey(callingPatient);
            return callingPatient;
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
    public int deleteDoctorAndUser(Long doctorId, Long userId, Long doctorSchedulingId) throws Exception {
        int a = 0;
        int b = 2;
        a = iDoctorMapper.deleteByPrimaryKey(doctorId) + a;
        a = userService.deleteUser(userId) + a;
        if (a != b) {
            log.warn("删除时，没有找到ID为" + doctorId + "的用户或ID为" + userId + "的医生！");
            throw new Exception();
        }
        //删除排班信息
        iDoctorSchedulingMapper.deleteByPrimaryKey(doctorSchedulingId);
        return b;
    }

    @Override
    @Transactional(rollbackFor = DoctorException.class)
    public Integer deleteDoctorAndUserBatch(List<Doctor> doctors, List<User> users) {
        try {
            //删除关联的排班信息
            for (Doctor doctor : doctors) {
                Doctor delDoctor = iDoctorMapper.selectByPrimaryKey(doctor.getDoctorId());
                iDoctorSchedulingMapper.deleteByPrimaryKey(delDoctor.getSchedulingId());
            }
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
    public DoctorTicket usedRecentVisitingDoctorTicket(Long id) {
        try {
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
    public PatientMedicalRecord saveCaseHistory(Long doctorTicketId, String content) {
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
            patientMedicalRecord.setContent(content);
            patientMedicalRecord.setDoctorId(ticketVo.getDoctorVo().getDoctorId());
            patientMedicalRecord.setPatientId(ticketVo.getPatient().getPatientId());
            patientMedicalRecord.setTicketId(doctorTicketId);
            patientMedicalRecord.setCreateDatetime(new Date());
            iPatientMedicalRecordMapper.insertAndInjectPrimaryKey(patientMedicalRecord);
            return patientMedicalRecord;
        } catch (Exception e) {
            log.error("病历插入失败!", e);
            throw new DoctorException(ServerResponseEnum.PATIENT_FILE_SAVE_FAIL);
        }

    }


    @Override
    public DoctorVo getDoctorByDoctorTicketId(Long doctorTicketId) {
        try {
            DoctorTicket doctorTicket = iTicketMapper.selectByPrimaryKey(doctorTicketId);
            return iDoctorMapper.selectByPrimaryKeyRelated(doctorTicket.getDoctorId());
        } catch (Exception e) {
            log.error("根据挂号信息ID查询医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
    }

    @Override
    public void addDoctorScheduling(DoctorScheduling doctorScheduling) {
        try {
            iDoctorSchedulingMapper.insertAndRejectId(doctorScheduling);
        } catch (Exception e) {
            log.error("插入排班信息失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_SCHEDULING_SAVE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDoctorAndDoctorScheduling(Doctor doctor, DoctorScheduling doctorScheduling) {
        try {
            iDoctorMapper.updateByPrimaryKey(doctor);
            iDoctorSchedulingMapper.updateByPrimaryKey(doctorScheduling);
        } catch (Exception e) {
            log.error("医生信息更新失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_UPDATE_FAIL);
        }
    }

    @Override
    public DoctorVo getDoctorDetailById(Long doctorId) {
        try {
            return iDoctorMapper.selectByPrimaryKeyRelated(doctorId);
        }catch (Exception e){
            log.error("根据ID关联查询医生失败", e);
            throw new DoctorException(ServerResponseEnum.DOCTOR_LIST_FAIL);
        }
    }


}
