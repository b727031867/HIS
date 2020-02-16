package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.OrderException;
import com.gxf.his.mapper.dao.IDoctorMapper;
import com.gxf.his.mapper.dao.IOrderItemMapper;
import com.gxf.his.mapper.dao.IOrderMapper;
import com.gxf.his.mapper.dao.ITicketMapper;
import com.gxf.his.po.generate.*;
import com.gxf.his.po.vo.OrderVo;
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
    private IDoctorMapper iDoctorMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getAndRemoveExpireOrders() {
        try {
            //删除订单信息
            List<OrderVo> orders = iOrderMapper.selectExpireOrder();
            for (OrderVo order : orders) {
                iOrderMapper.deleteByPrimaryKey(order.getOrderId());
                //删除订单项前归还订单中的资源
                List<OrderItem> orderItems = iOrderItemMapper.findOrderItemsByOrderId(order.getOrderId());
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
    public void addOrderPrescription(OrderVo order) {
//        try {
//            Long orderId = order.getOrderId();
//            BigDecimal totalPrice = BigDecimal.ZERO;
//            for (OrderItem orderItem : orderItemList) {
//                //处方单
//                if (null != orderItem.getDrugId()) {
//                    //计算总价
//                    Drug drug = iDrugMapper.selectByPrimaryKey(orderItem.getDrugId());
//                    //TODO
////                    totalPrice = totalPrice.add(drug.get.multiply(new BigDecimal(orderItem.getDrugQuantities())));
//                }  else if (null != orderItem.getCheckItemId()) {
//                    //检查单 TODO
//                } else {
//                    log.warn("未知的订单项");
//                    throw new Exception("未知的订单项");
//                }
//                orderItem.setOrderId(orderId);
//                orderItem.setOrderItemTotal(totalPrice);
//                iOrderItemMapper.insert(orderItem);
//            }
//            order.setOrderTotal(totalPrice);
//            iOrderMapper.insert(order);
//        } catch (Exception e) {
//            log.error("订单添加失败", e);
//            throw new OrderException(ServerResponseEnum.ORDER_SAVE_FAIL);
//        }
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
    public List<OrderVo> getUnPayOrdersByPatientId(Long patientId,Integer orderType) {
        List<OrderVo> orderVos = iOrderMapper.selectOrdersByPatientIdAndOrderType(patientId, orderType);
        List<OrderVo> unPayOrders = new ArrayList<>(16);
        for (OrderVo orderVo : orderVos) {
            if ("0".equals(orderVo.getOrderStatus())) {
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
