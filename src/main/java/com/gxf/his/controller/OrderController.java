package com.gxf.his.controller;

import com.gxf.his.Const;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.DoctorTicketResource;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.vo.OrderVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.OrderService;
import com.gxf.his.service.PatientService;
import com.gxf.his.service.TicketResourceService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author GXF
 * 订单控制类
 */
@RestController
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
     * 挂号接口 排队
     *
     * @param uid        患者用户ID
     * @param resourceId 票务资源ID
     * @return 统一响应类
     */
    @PostMapping("/registered")
    public <T> ServerResponseVO<T> saveRegisterOrder(Long uid, Long resourceId, String ticketType, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        if (null == uid || null == resourceId || null == ticketType || null == startTime || null == endTime) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        //获取患者
        Patient patient = patientService.getPatientByUid(uid);
        //获取票务资源
        DoctorTicketResource ticketResource = ticketResourceService.getTicketResourceById(resourceId);
        //判断是否重复挂号
        if (orderService.isRepeatRegisterOrder(patient.getPatientId(), ticketResource.getDoctorId(), resourceId)) {
            return ServerResponseVO.error(ServerResponseEnum.ORDER_REPEAT_FAIL);
        }
        ArrayList<DoctorTicketResource> ticketResources = new ArrayList<>(16);
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
        HashMap<String, Object> ticketInfo = new HashMap<>(5);
        ticketInfo.put("ticketType", ticketType);
        ticketInfo.put("endTime", endTime);
        ticketInfo.put("startTime", startTime);
        return MyUtil.cast(ServerResponseVO.success(orderService.addRegisterOrder(orderVo, ticketInfo)));
    }

    /**
     * 根据订单ID查询订单
     * @param orderId 订单ID
     * @param <T> 通用响应泛型
     * @return 订单信息
     */
    @GetMapping
    public <T> ServerResponseVO<T> getRegisterOrder(String orderId){
        if(orderId == null){
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        return MyUtil.cast(ServerResponseVO.success(orderService.getOrderByOrderId(Long.parseLong(orderId))));
    }

    /**
     * 开处方单接口
     *
     * @return 统一响应类
     */
    @PostMapping("/prescription")
    public <T> ServerResponseVO<T> savePrescriptionOrder() {
        return ServerResponseVO.success();
    }

    @PostMapping("/refund")
    public <T> ServerResponseVO<T> refundPrescriptionOrder(Long orderId) {
        if(orderId == null){
            return  ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        orderService.refundOrderById(orderId);
        return ServerResponseVO.success();
    }
}
