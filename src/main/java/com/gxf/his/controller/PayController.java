package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.OrderService;
import com.gxf.his.service.PatientService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/10 17:19
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController extends BaseController {
    @Resource
    private OrderService orderService;
    @Resource
    private PatientService patientService;

    @GetMapping("/checkItemOrderUnPayList")
    public <T> ServerResponseVO<T> loadCheckItemOrders(Long uid) {
        if (null == uid) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Patient patient = patientService.getPatientByUid(uid);
        if (patient == null) {
            return ServerResponseVO.error(ServerResponseEnum.PATIENT_NO_EXITS);
        }
        return MyUtil.cast(ServerResponseVO.success(orderService.getUnPayOrdersByPatientId(patient.getPatientId(),2)));
    }

    @GetMapping("/prescriptionOrderUnPayList")
    public <T> ServerResponseVO<T> loadPrescriptionOrders(Long uid) {
        if (null == uid) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Patient patient = patientService.getPatientByUid(uid);
        if (patient == null) {
            return ServerResponseVO.error(ServerResponseEnum.PATIENT_NO_EXITS);
        }
        return MyUtil.cast(ServerResponseVO.success(orderService.getUnPayOrdersByPatientId(patient.getPatientId(),1)));
    }

    @GetMapping("/registerOrderUnPayList")
    public <T> ServerResponseVO<T> loadRegisterOrders(Long uid) {
        if (null == uid) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        Patient patient = patientService.getPatientByUid(uid);
        if (patient == null) {
            return ServerResponseVO.error(ServerResponseEnum.PATIENT_NO_EXITS);
        }
        return MyUtil.cast(ServerResponseVO.success(orderService.getUnPayOrdersByPatientId(patient.getPatientId(),0)));
    }

    /**
     * 挂号与处方单的付款接口
     */
    @PostMapping("/register")
    public <T> ServerResponseVO<T> payRegister(Long orderId,Integer orderType) {
        log.info("订单ID：" + orderId);
        if (null == orderId ||  null == orderType) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if(orderType == 0){
            if (orderService.payRegisterOrder(orderId)) {
                return ServerResponseVO.success();
            }
        }else if(orderType == 1){
            if (orderService.payPrescription(orderId)) {
                return ServerResponseVO.success();
            }
        }else {
            log.warn("未知的订单类型");
        }

        return ServerResponseVO.error(ServerResponseEnum.ORDER_PAY_FAIL);
    }

}

