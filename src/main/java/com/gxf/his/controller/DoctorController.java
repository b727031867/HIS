package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.*;
import com.gxf.his.po.vo.*;
import com.gxf.his.service.*;
import com.gxf.his.uitls.MyUtil;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @author 龚秀峰
 * @date 2019-10-24
 */
@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController extends BaseController {

    @Resource
    private DoctorService doctorService;
    @Resource
    private UserService userService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private TicketResourceService ticketResourceService;
    @Resource
    private TicketService ticketService;
    @Resource
    private TemplateService templateService;

    @GetMapping("/outpatients")
    public <T> ServerResponseVO<T> getOutpatients(@RequestParam("uid") Long uid, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        if (uid == null || startDate == null || endDate == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        String doctorId = userService.getLoginEntityId(uid);
        List<PatientVo> patientVos = doctorService.getOutpatients(Long.parseLong(doctorId), startDate, endDate);
        return MyUtil.cast(ServerResponseVO.success(patientVos));
    }

    @GetMapping("/callNextPatients")
    public <T> ServerResponseVO<T> getNextCallPatient(@RequestParam("uid") Long uid, @RequestParam(value = "rank", required = false, defaultValue = "0") Integer rank) {
        if (uid == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        String doctorId = userService.getLoginEntityId(uid);
        TicketVo ticketVo = doctorService.getCurrentRankPatient(Long.parseLong(doctorId), rank);
        return MyUtil.cast(ServerResponseVO.success(ticketVo));
    }

    @GetMapping("/currentRankInfo")
    public <T> ServerResponseVO<T> getCurrentPatientInfo(@RequestParam("uid") Long uid) {
        if (uid == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        String doctorId = userService.getLoginEntityId(uid);
        HashMap<String, String> info = doctorService.getCurrentRankInfo(Long.parseLong(doctorId), 4);
        return MyUtil.cast(ServerResponseVO.success(info));
    }

    /**
     * 根据医生的用户ID和总数类型
     * 获取候诊总数或者叫号中的总数
     * totalType状态类型 同挂号信息的status状态
     *
     * @param uid       医生的用户ID
     * @param totalType 总数类型
     * @return 总数
     */
    @GetMapping("/totalRank")
    public <T> ServerResponseVO<T> getTotalRank(@RequestParam("uid") Long uid, @RequestParam("totalType") String totalType) {
        if (uid == null || totalType == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        String doctorId = userService.getLoginEntityId(uid);
        Integer total = doctorService.getTotalRank(Long.parseLong(doctorId), Integer.valueOf(totalType));
        return MyUtil.cast(ServerResponseVO.success(total));
    }

    @GetMapping("/callingPatient")
    public <T> ServerResponseVO<T> getCallingPatient(@RequestParam("uid") Long uid) {
        if (uid == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        String doctorId = userService.getLoginEntityId(uid);
        TicketVo ticketVo = doctorService.getCallingPatient(Long.parseLong(doctorId));
        return MyUtil.cast(ServerResponseVO.success(ticketVo));
    }

    @GetMapping
    public <T> ServerResponseVO<T> getDoctors(@RequestParam(required = false, name = "departmentCode") String departmentCode
            , @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if (null == departmentCode) {
            PageHelper.startPage(page, size);
            List<DoctorVo> doctors = doctorService.getAllDoctors();
            PageInfo<DoctorVo> pageInfo = PageInfo.of(doctors);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        }
        PageHelper.startPage(page, size);
        List<DoctorVo> doctors = doctorService.getDoctorsByDepartmentCode(departmentCode);
        PageInfo<DoctorVo> pageInfo = PageInfo.of(doctors);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @GetMapping("departmentCode")
    public <T> ServerResponseVO<T> getDoctorsByDepartmentCode(String departmentCode) {
        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        List<DoctorVo> doctorVos = doctorService.getDoctorsByDepartmentCode(departmentCode);
        //获取每位医生未来一周内的订票情况
        for (DoctorVo doctorVo : doctorVos) {
            Date expirationDate = ticketResourceService.getTicketResourceMaxDate();
            Date startDate = ticketResourceService.getTicketResourceMinDate();
            List<DoctorTicketResource> ticketResources = ticketResourceService.getTicketResourceByDoctorIdAndAvailableDate(doctorVo.getDoctorId(), startDate, expirationDate);
            doctorVo.setTicketResources(ticketResources);
        }
        return MyUtil.cast(ServerResponseVO.success(doctorVos));
    }

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getDoctorsByAttribute(@RequestParam(value = "attribute", defaultValue = "doctorName") String attribute
            , @RequestParam(value = "value") String value, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        //可能使用的查询属性
        String doctorName = "doctorName";
        String doctorProfessionalTitle = "doctorProfessionalTitle";
        String departmentName = "departmentName";
        DoctorVo doctorVo = new DoctorVo();
        if (doctorName.equals(attribute)) {
            doctorVo.setDoctorName(value);
        } else if (doctorProfessionalTitle.equals(attribute)) {
            doctorVo.setDoctorProfessionalTitle(value);
        } else if (departmentName.equals(attribute)) {
            List<Department> departments = departmentService.getDepartmentsByVaguelyDepartmentName(value);
            doctorVo.setDepartments(departments);
        } else {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PageHelper.startPage(page, size);
        List<DoctorVo> doctors = doctorService.selectDoctorByAttribute(doctorVo);
        PageInfo<DoctorVo> pageInfo = PageInfo.of(doctors);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @PutMapping
    public <T> ServerResponseVO<T> saveDoctorAndUser(@RequestBody DoctorVo doctorVo) {
        log.info("当前更新的医生信息为：" + doctorVo.toString());
        Doctor doctor = new Doctor();
        //更新医生信息
        doctor.setDoctorId(doctorVo.getDoctorId());
        doctor.setEmployeeId(doctorVo.getEmployeeId());
        doctor.setDoctorName(doctorVo.getDoctorName());
        doctor.setDoctorProfessionalTitle(doctorVo.getDoctorProfessionalTitle());
        doctor.setDoctorIntroduction(doctorVo.getDoctorIntroduction());
        doctor.setDepartmentCode(doctorVo.getDepartment().getDepartmentCode());
        doctor.setSchedulingId(doctorVo.getDoctorScheduling().getSchedulingId());
        doctor.setUserId(doctorVo.getUser().getUserId());
        doctor.setTicketDayNum(doctorVo.getTicketDayNum());
        doctorService.updateDoctor(doctor);
        //更新用户信息
        User user = doctorVo.getUser();
        userService.updateUser(user);
        return ServerResponseVO.success();
    }

    @PostMapping
    public <T> ServerResponseVO<T> saveDoctor(@RequestBody DoctorVo doctorVo) {
        if (StringUtils.isEmpty(doctorVo.getUser().getUserName().trim()) || StringUtils.isEmpty(doctorVo.getUser().getUserPassword().trim())
                || StringUtils.isEmpty(doctorVo.getDepartment().getDepartmentCode().trim()) || StringUtils.isEmpty(doctorVo.getDoctorIntroduction().trim())
                || StringUtils.isEmpty(doctorVo.getDoctorName().trim()) || StringUtils.isEmpty(doctorVo.getDoctorProfessionalTitle().trim())
                || StringUtils.isEmpty(doctorVo.getEmployeeId().trim())
        ) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if (userService.findByUserName(doctorVo.getUser().getUserName()) != null) {
            return ServerResponseVO.error(ServerResponseEnum.USER_REPEAT_ERROR);
        }
        User user = UserController.doHashedCredentials(doctorVo.getUser().getUserName(), doctorVo.getUser().getUserPassword());
        userService.addUser(user);
        Doctor doctor = new Doctor();
        doctor.setDepartmentCode(doctorVo.getDepartment().getDepartmentCode());
        doctor.setDoctorIntroduction(doctorVo.getDoctorIntroduction());
        doctor.setDoctorName(doctorVo.getDoctorName());
        doctor.setDoctorProfessionalTitle(doctorVo.getDoctorProfessionalTitle());
        doctor.setEmployeeId(doctorVo.getEmployeeId());
        doctor.setUserId(user.getUserId());
        doctorService.addDoctor(doctor);
        return ServerResponseVO.success();
    }

    @PostMapping("/startSeeDoctor")
    public <T> ServerResponseVO<T> startSeeDoctor(@RequestBody TicketVo ticketVo) {
        if (ticketVo == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        //如果前端页面刷新丢失叫号的票务信息，则自动获取最早排队且叫号中的票务信息
        if (ticketVo.getTicketId() == null) {
            String doctorId = userService.getLoginEntityId(ticketVo.getUid());
            ticketVo = doctorService.getCurrentRankPatient(Long.parseLong(doctorId), 0);
            //丢失信息默认设置电子病历模板
            ticketVo.setType(2);
        }
        DoctorVo doctorVo = doctorService.getDoctorByDoctorId(ticketVo.getDoctorVo().getDoctorId());
        String doctorName = doctorVo.getDoctorName();
        String departmentName = doctorVo.getDepartment().getDepartmentName();
        String patientName = ticketVo.getPatient().getPatientName();
        Integer patientAge = ticketVo.getPatient().getPatientAge();
        Byte patientSexCode = ticketVo.getPatient().getPatientSex();
        Byte patientIsMarriageCode = ticketVo.getPatient().getPatientIsMarriage();
        if (patientName == null) {
            patientName = "暂未设置姓名";
        }
        if (patientSexCode == null) {
            patientSexCode = 0;
        }
        if (patientIsMarriageCode == null) {
            patientIsMarriageCode = 0;
        }
        String patientSex = MyUtil.changeSex(patientSexCode);
        String patientIsMarriage = MyUtil.changeMarriage(patientIsMarriageCode);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String dateTime = dtf.format(now);
        HashMap<String, String> map = new HashMap<>(8);
        map.put("doctorName", doctorName);
        map.put("departmentName", departmentName);
        map.put("patientName", patientName);
        if (patientAge == null) {
            map.put("patientAge", "暂未设置年龄");
        } else {
            map.put("patientAge", patientAge + "岁");
        }
        map.put("patientSex", patientSex);
        map.put("patientIsMarriage", patientIsMarriage);
        map.put("dateTime", dateTime);
        StringWriter result = new StringWriter();
        String templateString = null;
        List<TemplateVo> allTemplates = templateService.getAllTemplates();
        for (TemplateVo allTemplate : allTemplates) {
            if (allTemplate.getType() != null && allTemplate.getType().equals(ticketVo.getType())) {
                templateString = allTemplate.getContent();
            }
        }
        if (templateString == null) {
            return ServerResponseVO.error(ServerResponseEnum.DOCTOR_MEDICAL_TEMPLATE_LIST_FAIL);
        }
        try {
            Template t = new Template("电子病历", new StringReader(templateString));
            t.process(map, result);
            String content = result.toString();
            //将这张票的状态变成就诊中
            DoctorTicket ticketById = ticketService.getTicketById(ticketVo.getTicketId());
            ticketById.setStatus(5);
            ticketService.updateTicketInfo(ticketById);
            return MyUtil.cast(ServerResponseVO.success(content));
        } catch (Exception e) {
            log.error("模板渲染出现异常", e);
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
    }

    @PostMapping("/endSeeDoctor")
    public <T> ServerResponseVO<T> endSeeDoctor(@RequestParam("uid") Long uid, @RequestParam(value = "doctorTicketId", required = false) Long doctorTicketId) {
        if (doctorTicketId == null) {
            if (uid == null) {
                return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
            }
            //如果为空,没有就诊中的患者，那么直接获取该医生最早的就诊中的患者
            String doctorId = userService.getLoginEntityId(uid);
            //将最近的就诊中的挂号状态置为已完成
            DoctorTicket doctorTicket = doctorService.usedRecentVisitingDoctorTicket(doctorId);
            return MyUtil.cast(ServerResponseVO.success(doctorTicket));
        }
        DoctorTicket doctorTicket = doctorService.endSeeDoctor(doctorTicketId);
        //将当前订单置为已完成
        return MyUtil.cast(ServerResponseVO.success(doctorTicket));
    }

    @PostMapping("/saveCaseHistory")
    public <T> ServerResponseVO<T> saveCaseHistory( @RequestParam(value = "doctorTicketId") Long doctorTicketId, @RequestParam(value = "content") String content) {
        if (doctorTicketId == null ||  content == null) {
                return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        doctorService.saveCaseHistory(doctorTicketId,content);
        return MyUtil.cast(ServerResponseVO.success());
    }


    @DeleteMapping
    public <T> ServerResponseVO<T> deleteDoctorAndUserByDoctorId(@RequestParam(name = "doctorId") Long doctorId,
                                                                 @RequestParam(name = "userId") Long userId) {
        try {
            doctorService.deleteDoctorAndUser(doctorId, userId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.USER_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public <T> ServerResponseVO<T> deleteDoctorsAndUsersByIds(@RequestBody List<DoctorVo> doctorVos) {
        List<Doctor> doctors = new ArrayList<>(16);
        List<User> users = new ArrayList<>(16);
        for (DoctorVo doctorVo : doctorVos) {
            Doctor doctor = new Doctor();
            doctor.setDoctorId(doctorVo.getDoctorId());
            doctors.add(doctor);
            users.add(doctorVo.getUser());
        }
        Integer a = doctorService.deleteDoctorAndUserBatch(doctors, users);
        //正常情况应该删除n个医生就有n个对应的用户也删除
        int b = 2;
        if (doctorVos.size() * b == a) {
            return ServerResponseVO.success();
        }
        return ServerResponseVO.error(ServerResponseEnum.DOCTOR_DELETE_FAIL);
    }


}
