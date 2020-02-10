package com.gxf.his.service.impl;

import com.gxf.his.Const;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.OrderException;
import com.gxf.his.mapper.dao.IDoctorMapper;
import com.gxf.his.mapper.dao.IDrugMapper;
import com.gxf.his.mapper.dao.IOrderItemMapper;
import com.gxf.his.mapper.dao.IOrderMapper;
import com.gxf.his.po.vo.OrderVo;
import com.gxf.his.po.generate.*;
import com.gxf.his.service.OrderService;
import com.gxf.his.service.TicketResourceService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private IDoctorMapper iDoctorMapper;
    @Resource
    private IDrugMapper iDrugMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getAndRemoveExpireOrders() {
        try {
            List<Order> orders = iOrderMapper.selectExpireOrder();
            for (Order order : orders) {
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
            }
        } catch (Exception e) {
            log.error("查询并且删除过期订单失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_LIST_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(OrderVo order) {
        try {
            List<OrderItem> orderItemList = order.getOrderItemList();
            Long orderId = order.getOrderId();
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderItem orderItem : orderItemList) {
                //处方单
                if (null != orderItem.getDrugId()) {
                    //计算总价
                    Drug drug = iDrugMapper.selectByPrimaryKey(orderItem.getDrugId());
                    //TODO
//                    totalPrice = totalPrice.add(drug.get.multiply(new BigDecimal(orderItem.getDrugQuantities())));
                }  else if (null != orderItem.getCheckItemId()) {
                    //检查单 TODO
                } else {
                    log.warn("未知的订单项");
                    throw new Exception("未知的订单项");
                }
                orderItem.setOrderId(orderId);
                orderItem.setOrderItemTotal(totalPrice);
                iOrderItemMapper.insert(orderItem);
                //根据不同的订单项，锁定不同的资源
                lockResources(orderItem);
            }
            order.setOrderTotal(totalPrice);
            iOrderMapper.insert(order);
        } catch (Exception e) {
            log.error("订单添加失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_SAVE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRegisterOrder(OrderVo order) {
        //插入订单并且获取订单ID
        int insert = iOrderMapper.insert(order);
        Long orderId = order.getOrderId();
        if(insert<1){
            //本次提交将回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        List<TicketResource> ticketResourceList = order.getTicketResourceList();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (TicketResource ticketResource : ticketResourceList) {
            //根据票务资源生成订单项
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            //挂号价格从医生中获取
            Doctor doctor = iDoctorMapper.selectByPrimaryKey(ticketResource.getDoctorId());
            totalPrice = totalPrice.add(doctor.getTicketPrice());
            orderItem.setOrderItemTotal(doctor.getTicketPrice());
            orderItem.setTicketResourceId(ticketResource.getRegisteredResourceId());
            iOrderItemMapper.insert(orderItem);
        }
        //更新总价
        order.setOrderTotal(totalPrice);
        iOrderMapper.updateByPrimaryKey(order);
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
    public <T> T getResourceByOrderTypeAndId(Integer orderType, Long resourceId) {
        //挂号单
        if (Const.GH.equals(orderType)) {
            return MyUtil.cast(ticketResourceService.getTicketResourceById(resourceId));
        }
        //检查单
        if (Const.CF.equals(orderType)) {
            //TODO
        }
        //处方单
        if (Const.JC.equals(orderType)) {
            //TODO
        }
        log.warn("未知的订单类型:" + orderType);
        return null;
    }

    private void releaseResources(OrderItem orderItem) {
        //归还药物资源
        if (null != orderItem.getDrugId()) {
            //TODO
        }
        //归还挂号资源
        if (null != orderItem.getTicketResourceId()) {
            TicketResource ticketResource = ticketResourceService.getTicketResourceById(orderItem.getTicketResourceId());
            ticketResource.setTicketLastNumber(ticketResource.getTicketLastNumber() + 1);
            ticketResourceService.updateTicketResource(ticketResource);
        }
    }

    private void lockResources(OrderItem orderItem) throws Exception {
        //减少药物资源
        if (null != orderItem.getDrugId()) {
            //TODO
        }
        //减少挂号资源
        if (null != orderItem.getTicketResourceId()) {
            TicketResource ticketResource = ticketResourceService.getTicketResourceById(orderItem.getTicketResourceId());
            //库存小于零不给下单
            if (ticketResource.getTicketLastNumber() - 1 < 0) {
                throw new Exception("库存小于零不给下单");
            }
            ticketResource.setTicketLastNumber(ticketResource.getTicketLastNumber() - 1);
            ticketResourceService.updateTicketResource(ticketResource);
        }
    }
}
