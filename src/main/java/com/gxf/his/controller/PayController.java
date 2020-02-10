package com.gxf.his.controller;

import com.gxf.his.po.vo.ServerResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/10 17:19
 */
@RestController
@Slf4j
@RequestMapping("/pay")
public class PayController extends BaseController {

    /**
     * 挂号付款接口
     */
    @PostMapping("/register")
    public <T> ServerResponseVO<T> payRegister(Long orderId){
        return ServerResponseVO.success();
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
