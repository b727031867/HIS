package com.gxf.his.controller;

import com.gxf.his.Const;
import com.gxf.his.po.generate.Order;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.TicketResource;
import com.gxf.his.po.vo.OrderVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.OrderService;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.TicketResourceService;
import com.gxf.his.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    @Resource
    private TicketResourceService ticketResourceService;
    @Resource
    private PatientService patientService;

    /**
     * 挂号接口
     *
     * @param uid 患者用户ID
     * @param resourceId 票务资源ID
     * @param startTime 票务有效开始日期
     * @param endTime 票务有效结束日期
     * @return 统一响应类
     */
    @PostMapping("/registered")
    public <T> ServerResponseVO<T> saveOrder(@NotNull Long uid,@NotNull Long resourceId, @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @NotNull Date endTime) {
        //获取患者
        Patient patient = patientService.getPatientByUid(uid);
        //获取票务资源
        TicketResource ticketResource = ticketResourceService.getTicketResourceById(resourceId);
        ArrayList<TicketResource> ticketResources = new ArrayList<>(16);
        ticketResources.add(ticketResource);
        //设置订单信息
        OrderVo orderVo = new OrderVo();
        orderVo.setPatientId(patient.getPatientId());
        orderVo.setTicketResourceList(ticketResources);
        orderVo.setOrderStatus("0");
        orderVo.setOrderCreateTime(new Date());
        //n分钟后过期
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(Const.ORDER_EXPIRED_TIME);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        orderVo.setOrderExpireTime(Date.from(zdt.toInstant()));
        orderService.addRegisterOrder(orderVo);
        return ServerResponseVO.success();
    }
}
