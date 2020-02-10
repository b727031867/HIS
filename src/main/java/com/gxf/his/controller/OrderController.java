package com.gxf.his.controller;

import com.gxf.his.Const;
import com.gxf.his.po.vo.OrderVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author GXF
 * 订单控制类
 */
@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping
    public ServerResponseVO saveOrder(@Valid @RequestBody OrderVo orderVo) {
        orderVo.setOrderStatus("0");
        orderVo.setOrderCreateTime(new Date());
        //n分钟后过期
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(Const.ORDER_EXPIRED_TIME);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        orderVo.setOrderExpireTime(Date.from(zdt.toInstant()));
        orderService.addOrder(orderVo);
        return ServerResponseVO.success();
    }
}
