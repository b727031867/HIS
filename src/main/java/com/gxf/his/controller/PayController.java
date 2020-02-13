package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.OrderService;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 挂号付款接口
     */
    @PostMapping("/register")
    public <T> ServerResponseVO<T> payRegister(Long orderId){
        log.info("订单ID："+orderId);
        if(null == orderId){
            ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        if(orderService.payRegisterOrder(orderId)){
            return ServerResponseVO.success();
        }
        return ServerResponseVO.error(ServerResponseEnum.ORDER_PAY_FAIL);
    }

    /**
     * 处方付款接口
     */
    @PostMapping("/prescription")
    public <T> ServerResponseVO<T> payPrescription(Long orderId){
        return ServerResponseVO.success();
    }

    /**
     * 检查付款接口
     */
    @PostMapping("/checkItem")
    public <T> ServerResponseVO<T> payCheckItem(Long orderId){
        return ServerResponseVO.success();
    }
}

