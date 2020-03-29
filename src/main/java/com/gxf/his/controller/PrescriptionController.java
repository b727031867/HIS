package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.PrescriptionException;
import com.gxf.his.po.generate.PrescriptionRefundInfo;
import com.gxf.his.po.vo.CashierVo;
import com.gxf.his.po.vo.PrescriptionVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.PrescriptionService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 22:36
 */
@RestController
@RequestMapping("/prescription")
@Slf4j
public class PrescriptionController extends BaseController {
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

    @GetMapping("/prescriptionId")
    public <T> ServerResponseVO<T> getPrescriptionByPrescriptionId(Long prescriptionId) {
        if (prescriptionId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PrescriptionVo prescriptionVo = prescriptionService.getPrescriptionByPrescriptionId(prescriptionId);
        return MyUtil.cast(ServerResponseVO.success(prescriptionVo));
    }

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getPrescriptionsByAttribute(@RequestParam(value = "prescriptionId", required = false) Long prescriptionId, @RequestParam(value = "attribute", defaultValue = "patientName", required = false) String attribute
            , @RequestParam(value = "isAccurate") Boolean isAccurate, @RequestParam(value = "value", required = false) String value, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size",
            defaultValue = "5", required = false) Integer size) {
        //不精确查询才检查模糊查询参数列表
        if (!isAccurate) {
            if (!searchParamCheck(attribute, false, value)) {
                return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
            }
            PageHelper.startPage(page, size);
            List<PrescriptionVo> prescriptionVos = prescriptionService.selectPrescriptionVosByAttribute(false, attribute, value);
            PageInfo<PrescriptionVo> pageInfo = PageInfo.of(prescriptionVos);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        } else {
            return MyUtil.cast(ServerResponseVO.success(prescriptionService.getPrescriptionByPrescriptionId(prescriptionId)));
        }
    }

    @GetMapping("/timeArea")
    public <T> ServerResponseVO<T> getPrescriptionListByTimeArea(@RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        //不精确查询才检查模糊查询参数列表
        if (startDate == null || endDate == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        } else {
            LocalDateTime now = LocalDateTime.now();
            long timestamp = Timestamp.valueOf(now).getTime();
            long endDateTimestamp = endDate.getTime();
            //如果endDate是今天，则按照当前时间查询
            long oneDateTime = 24 * 60 * 60 * 1000;
            if (timestamp - endDateTimestamp < oneDateTime) {
                endDate = new Date();
            }
            return MyUtil.cast(ServerResponseVO.success(prescriptionService.getPrescriptionListByTimeArea(startDate, endDate)));
        }
    }


    @PostMapping("/savePatientPrescription")
    public <T> ServerResponseVO<T> savePrescription(@RequestBody PrescriptionVo prescriptionVo) {
        if (prescriptionVo == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PrescriptionVo prescriptionVoData = prescriptionService.savePrescription(prescriptionVo);
        return MyUtil.cast(ServerResponseVO.success(prescriptionVoData));
    }

    @PostMapping("/refundPrescription")
    public <T> ServerResponseVO<T> addRefundPrescription(@RequestParam("prescriptionId") Long prescriptionId,
                                                         @RequestParam("reason") String reason, @RequestParam("operateId") Long operateId) {
        if (prescriptionId == null || reason == null || operateId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PrescriptionRefundInfo prescriptionRefundInfo = new PrescriptionRefundInfo();
        prescriptionRefundInfo.setOperateId(operateId);
        prescriptionRefundInfo.setReason(reason);
        prescriptionRefundInfo.setPrescriptionId(prescriptionId);
        prescriptionRefundInfo.setCreateTime(new Date());
        prescriptionRefundInfo.setStatus(0);
        prescriptionService.addRefundPrescription(prescriptionRefundInfo);
        return ServerResponseVO.success();
    }

}
