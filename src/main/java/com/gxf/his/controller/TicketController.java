package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.PatientMedicalRecord;
import com.gxf.his.po.vo.DoctorVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.po.vo.TemplateVo;
import com.gxf.his.po.vo.TicketVo;
import com.gxf.his.service.DoctorService;
import com.gxf.his.service.TemplateService;
import com.gxf.his.service.TicketService;
import com.gxf.his.service.UserService;
import com.gxf.his.uitls.MyUtil;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-29
 */
@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketController {
    @Resource
    private UserService userService;
    @Resource
    private TicketService ticketService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private TemplateService templateService;

    @GetMapping("queue")
    public <T> ServerResponseVO<T> getCurrentDoctorRank(@RequestParam("doctorId") Long doctorId) {
        if (doctorId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Integer rank = ticketService.getCurrentDoctorRank(doctorId);
        return MyUtil.cast(ServerResponseVO.success(rank));
    }

    @GetMapping("ticketVo")
    public <T> ServerResponseVO<T> getDoctorTicketVoById(@RequestParam("doctorTicketId") Long doctorTicketId) {
        if (doctorTicketId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        TicketVo ticketVo = ticketService.getTicketById(doctorTicketId);
        return MyUtil.cast(ServerResponseVO.success(ticketVo));
    }

    @GetMapping("/callNextPatients")
    public <T> ServerResponseVO<T> getNextCallPatient(@RequestParam("doctorId") Long doctorId, @RequestParam(value = "ticketNumber") Integer ticketNumber) {
        if (doctorId == null || ticketNumber == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        TicketVo ticketVo = ticketService.getCurrentRankPatient(doctorId, ticketNumber);
        return MyUtil.cast(ServerResponseVO.success(ticketVo));
    }

    @PostMapping("/renderMedicalRecord")
    public <T> ServerResponseVO<T> renderMedicalRecord(@RequestBody TicketVo ticketVo) {
        if (ticketVo == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Long doctorId = ticketVo.getDoctorId();
        DoctorVo doctorVo = doctorService.getDoctorByDoctorId(doctorId);
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
        map.put("doctorName", doctorVo.getDoctorName());
        map.put("departmentName", doctorVo.getDepartment().getDepartmentName());
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
            return MyUtil.cast(ServerResponseVO.success(content));
        } catch (Exception e) {
            log.error("模板渲染出现异常", e);
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
    }

    @PostMapping("/saveCaseHistory")
    public <T> ServerResponseVO<T> saveCaseHistory(@RequestParam(value = "doctorTicketId") Long doctorTicketId, @RequestParam(value = "content") String content) {
        if (doctorTicketId == null || content == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PatientMedicalRecord patientMedicalRecord = doctorService.saveCaseHistory(doctorTicketId, content);
        return MyUtil.cast(ServerResponseVO.success(patientMedicalRecord));
    }


}
