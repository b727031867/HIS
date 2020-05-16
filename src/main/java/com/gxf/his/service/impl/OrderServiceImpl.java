package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.OrderException;
import com.gxf.his.mapper.dao.*;
import com.gxf.his.po.generate.*;
import com.gxf.his.po.vo.*;
import com.gxf.his.service.OrderService;
import com.gxf.his.service.TicketResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author GXF
 * 订单接口的实现类
 */
@Service
@Slf4j

public class OrderServiceImpl implements OrderService {


    @Resource
    private IOrderMapper iOrderMapper;
    @Resource
    private IOrderItemMapper iOrderItemMapper;
    @Resource
    private TicketResourceService ticketResourceService;
    @Resource
    private ITicketMapper iTicketMapper;
    @Resource
    private IPrescriptionInfoMapper iPrescriptionInfoMapper;
    @Resource
    private IPrescriptionMapper iPrescriptionMapper;
    @Resource
    private IPrescriptionExtraCostMapper iPrescriptionExtraCostMapper;
    @Resource
    private IPrescriptionRefundInfoMapper iPrescriptionRefundInfoMapper;
    @Resource
    private IDoctorMapper iDoctorMapper;
    @Resource
    private IPatientMapper iPatientMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getAndRemoveExpireDoctorTicketOrders() {
        try {
            //删除订单信息
            List<OrderVo> orders = iOrderMapper.selectExpireOrderByOrderType(0);
            for (OrderVo order : orders) {
                iOrderMapper.deleteByPrimaryKey(order.getOrderId());
                //删除订单项前归还订单中的资源
                List<OrderItem> orderItems = iOrderItemMapper.findOrderItemsByOrderIdNoRelated(order.getOrderId());
                for (OrderItem orderItem : orderItems) {
                    releaseResources(orderItem);
                }
                int i = iOrderItemMapper.deleteByOrderId(order.getOrderId());
                if (i == 0) {
                    log.warn("本次退订订单未找到订单项!");
                }
                //删除挂号信息
                List<DoctorTicket> tickets = iTicketMapper.selectUnPayExpireTicket(order.getOrderId());
                for (DoctorTicket ticket : tickets) {
                    iTicketMapper.deleteByPrimaryKey(ticket.getTicketId());
                }
            }
        } catch (Exception e) {
            log.error("查询并且删除过期订单失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_LIST_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getAndRemoveExpirePrescriptionOrders() {
        try {
            //订单状态置为已过期
            List<OrderVo> orders = iOrderMapper.selectExpireOrderByOrderType(1);
            for (OrderVo order : orders) {
                order.setOrderStatus("3");
                iOrderMapper.updateByPrimaryKey(order);
            }
        } catch (Exception e) {
            log.error("处方单过期失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_STATUS_CHANGE_FAIL);
        }
    }

    @Override
    public Boolean isRepeatRegisterOrder(Long patientId, Long doctorId, Long resourceId) {
        List<OrderVo> orderVos = iOrderMapper.selectRepeatRegisterOrder(patientId, doctorId);
        if (orderVos.size() == 0) {
            return false;
        }
        for (OrderVo orderVo : orderVos) {
            if (null == orderVo) {
                return false;
            }
            if (null != orderVo.getOrderItemList()) {
                return resourceId.equals(orderVo.getOrderItemList().get(0).getTicketResourceId());
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrderPrescription(OrderVo orderVo) {
        //插入订单并且获取订单ID
        iOrderMapper.insertAndInjectId(orderVo);
        //处方项以及额外收费项的列表
        List<OrderItemVo> orderVoItemList = orderVo.getOrderVoItemList();
        for (OrderItemVo orderItemVo : orderVoItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderVo.getOrderId());
            if (orderItemVo.getPrescriptionInfo() != null) {
                orderItem.setPrescriptionInfoId(orderItemVo.getPrescriptionInfo().getPrescriptionInfoId());
            }
            if (orderItemVo.getPrescriptionExtraCost() != null) {
                orderItem.setPrescriptionExtraCostId(orderItemVo.getPrescriptionExtraCost().getPrescriptionExtraCostId());
            }
            iOrderItemMapper.insert(orderItem);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addRegisterOrder(OrderVo order, HashMap<String, Object> ticketInfo) {
        //插入订单并且获取订单ID
        int insert = iOrderMapper.insertAndInjectId(order);
        Long orderId = order.getOrderId();
        if (insert < 1) {
            //本次提交将回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        List<DoctorTicketResource> ticketResourceList = order.getTicketResourceList();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (DoctorTicketResource ticketResource : ticketResourceList) {
            //根据票务资源生成订单项
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            //挂号价格从医生中获取
            Doctor doctor = iDoctorMapper.selectByPrimaryKey(ticketResource.getDoctorId());
            totalPrice = totalPrice.add(doctor.getTicketPrice());
            orderItem.setTicketResourceId(ticketResource.getRegisteredResourceId());
            iOrderItemMapper.insert(orderItem);
            //根据不同的订单项，锁定不同的资源
            lockResources(orderItem);
            //设置其他属性
            order.setDoctorId(doctor.getDoctorId());
            //挂号单
            order.setOrderType(0);
            //更新总价
            order.setOrderTotal(totalPrice);
            iOrderMapper.updateByPrimaryKey(order);
            //插入挂号信息
            DoctorTicket doctorTicket = new DoctorTicket();
            doctorTicket.setTicketType(ticketInfo.get("ticketType").toString());
            doctorTicket.setTicketCreateTime(new Date());
            doctorTicket.setTicketValidityStart((Date) ticketInfo.get("startTime"));
            doctorTicket.setTicketValidityEnd((Date) ticketInfo.get("endTime"));
            doctorTicket.setDoctorId(doctor.getDoctorId());
            doctorTicket.setPatientId(order.getPatientId());
            doctorTicket.setOrderId(orderId);
            doctorTicket.setRegisteredResourceId(ticketResource.getRegisteredResourceId());
            doctorTicket.setStatus(-1);
            iTicketMapper.insert(doctorTicket);
        }
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(Long orderId) {
        try {
            iOrderMapper.deleteByPrimaryKey(orderId);
            iOrderItemMapper.deleteByOrderId(orderId);
        } catch (Exception e) {
            log.error("订单删除失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_DELETE_FAIL);
        }
    }

    @Override
    public OrderVo getOrderByOrderId(Long orderId) {
        try {
            return iOrderMapper.selectRegisterOrderByOrderId(orderId);
        } catch (Exception e) {
            log.warn("根据ID查询挂号订单失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_LIST_FAIL);
        }
    }

    @Override
    public List<OrderVo> getUnPayOrdersByPatientId(Long patientId, Integer orderType) {
        List<OrderVo> orderVos = iOrderMapper.selectOrdersByPatientIdAndOrderType(patientId, orderType);
        List<OrderVo> unPayOrders = new ArrayList<>(16);
        for (OrderVo orderVo : orderVos) {
            if ("0".equals(orderVo.getOrderStatus())) {
                //关联查询处方项以及额外收费
                if(orderVo.getPrescriptionId() != null){
                    PrescriptionVo prescriptionVo = new PrescriptionVo();
                    Prescription prescription = iPrescriptionMapper.selectByPrimaryKey(orderVo.getPrescriptionId());
                    PrescriptionExtraCost prescriptionExtraCost = iPrescriptionExtraCostMapper.selectByPrescriptionId(orderVo.getPrescriptionId());
                    List<PrescriptionInfoVo> prescriptionInfoVos = iPrescriptionInfoMapper.selectPrescriptionInfosByPrescriptionId(orderVo.getPrescriptionId());
                    prescriptionVo.setPrescription(prescription);
                    prescriptionVo.setPrescriptionInfoVos(prescriptionInfoVos);
                    prescriptionVo.setPrescriptionExtraCost(prescriptionExtraCost);
                    //获取患者与医生信息
                    Patient patient = iPatientMapper.selectByPrimaryKey(patientId);
                    DoctorVo doctorVo = iDoctorMapper.selectByPrimaryKeyRelated(orderVo.getDoctorId());
                    prescriptionVo.setPatient(patient);
                    prescriptionVo.setDoctorVo(doctorVo);
                    orderVo.setPrescriptionVo(prescriptionVo);
                }
                unPayOrders.add(orderVo);
            }
        }
        return unPayOrders;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean payRegisterOrder(Long orderId) {
        Order order = iOrderMapper.selectByPrimaryKey(orderId);
        DoctorTicket doctorTicket = iTicketMapper.selectByOrderId(orderId);
        if (null != order && null != doctorTicket) {
            order.setOrderStatus("1");
            doctorTicket.setStatus(0);
            iOrderMapper.updateByPrimaryKey(order);
            iTicketMapper.updateByPrimaryKey(doctorTicket);
            return true;
        }
        return false;
    }

    @Override
    public Boolean payPrescription(Long orderId) {
        Order order = iOrderMapper.selectByPrimaryKey(orderId);
        if (null != order) {
            order.setOrderStatus("1");
            iOrderMapper.updateByPrimaryKey(order);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundOrderById(Long orderId) {
            Order order = iOrderMapper.selectByPrimaryKey(orderId);
            //检查处方订单是否存在退款申请
            if(order.getPrescriptionId() != null){
                PrescriptionRefundInfo prescriptionRefundInfo = iPrescriptionRefundInfoMapper.selectByPrescriptionId(order.getPrescriptionId());
                if(prescriptionRefundInfo == null){
                    throw new OrderException(ServerResponseEnum.PRESCRIPTION_REFUND_INFO_NO_EXITS);
                }
            }
            String payStatus = "1";
            if(payStatus.equals(order.getOrderStatus())){
                order.setOrderStatus("2");
                iOrderMapper.updateByPrimaryKey(order);
            }else {
                log.warn("只有已付款得订单才能退款");
                throw new OrderException(ServerResponseEnum.ORDER_STATUS_CHANGE_FAIL);
            }
    }

    private void releaseResources(OrderItem orderItem) {
        //归还挂号资源
        if (null != orderItem.getTicketResourceId()) {
            DoctorTicketResource ticketResource = ticketResourceService.getTicketResourceById(orderItem.getTicketResourceId());
            ticketResource.setTicketLastNumber(ticketResource.getTicketLastNumber() + 1);
            ticketResourceService.updateTicketResource(ticketResource);
        }
    }

    private void lockResources(OrderItem orderItem) {
        //减少挂号资源
        if (null != orderItem.getTicketResourceId()) {
            DoctorTicketResource ticketResource = ticketResourceService.getTicketResourceById(orderItem.getTicketResourceId());
            //库存小于零不给下单
            if (ticketResource.getTicketLastNumber() - 1 < 0) {
                log.warn("库存小于零不给下单");
                //本次提交将回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            ticketResource.setTicketLastNumber(ticketResource.getTicketLastNumber() - 1);
            ticketResourceService.updateTicketResource(ticketResource);
        }
    }
}
