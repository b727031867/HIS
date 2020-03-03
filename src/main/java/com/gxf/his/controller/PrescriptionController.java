package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.PrescriptionVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.PrescriptionService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 22:36
 */
@RestController
@RequestMapping("/prescription")
@Slf4j
public class PrescriptionController {
    @Resource
    private PrescriptionService prescriptionService;

    @GetMapping("/doctorTicketId")
    public <T> ServerResponseVO<T> getPrescriptionByDoctorTicketId(Long doctorTicketId) {
        if (doctorTicketId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PrescriptionVo prescriptionVo = prescriptionService.getPrescriptionByDoctorTicketId(doctorTicketId);
        return MyUtil.cast(ServerResponseVO.success(prescriptionVo));
    }


    @PostMapping("/savePatientPrescription")
    public <T> ServerResponseVO<T> savePrescription(@RequestBody PrescriptionVo prescriptionVo) {
        if (prescriptionVo == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PrescriptionVo prescriptionVoData = prescriptionService.savePrescription(prescriptionVo);
        return MyUtil.cast(ServerResponseVO.success(prescriptionVoData));
    }


}
