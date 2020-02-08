package com.gxf.his.controller;

import com.gxf.his.po.Order;
import com.gxf.his.service.OrderService;
import com.gxf.his.vo.ServerResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author GXF
 * 订单控制类
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Resource
    private OrderService orderService;

    @PostMapping
    public ServerResponseVO saveOrder(@RequestBody Order order){

        orderService.addOrder(order);
        return ServerResponseVO.success();
    }
}
