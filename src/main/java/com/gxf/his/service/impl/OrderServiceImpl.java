package com.gxf.his.service.impl;

import com.gxf.his.Const;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.OrderException;
import com.gxf.his.mapper.OrderItemMapper;
import com.gxf.his.mapper.OrderMapper;
import com.gxf.his.po.Order;
import com.gxf.his.po.OrderItem;
import com.gxf.his.po.TicketResource;
import com.gxf.his.service.OrderService;
import com.gxf.his.service.TicketResourceService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private TicketResourceService ticketResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getAndRemoveExpireOrders() {
        try {
            List<Order> orders = orderMapper.selectExpireOrder();
            for (Order order : orders) {
                orderMapper.deleteByPrimaryKey(order.getOrderId());
                //删除订单项前归还订单中的资源
                List<OrderItem> orderItems = orderItemMapper.findOrderItemsByOrderId(order.getOrderId());
                for (OrderItem orderItem : orderItems) {
                    releaseResources(orderItem);
                }
                orderItemMapper.deleteByOrderId(order.getOrderId());
            }
        } catch (Exception e) {
            log.error("查询并且删除过期订单失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_LIST_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(Order order) {
        try {
            List<OrderItem> orderItemList = order.getOrderItemList();
            Long orderId = order.getOrderId();
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderItem orderItem : orderItemList) {
                orderItem.setOrderId(orderId);
                //计算总价
                totalPrice = totalPrice.add(orderItem.getOrderItemTotal().multiply(new BigDecimal(orderItem.getDrugQuantities())));
                orderItemMapper.insert(orderItem);
                //根据不同的订单项，锁定不同的资源
                lockResources(orderItem);
            }
            order.setOrderTotal(totalPrice);
            orderMapper.insert(order);
        } catch (Exception e) {
            log.error("订单添加失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_SAVE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(Long orderId) {
        try {
            orderMapper.deleteByPrimaryKey(orderId);
            orderItemMapper.deleteByOrderId(orderId);
        } catch (Exception e) {
            log.error("订单删除失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_DELETE_FAIL);
        }
    }

    @Override
    public <T> T getResourceByOrderTypeAndId(Integer orderType,Long resourceId) {
        //挂号单
        if(Const.GH.equals(orderType)){
            return MyUtil.cast(ticketResourceService.getTicketResourceById(resourceId));
        }
        //检查单
        if(Const.CF.equals(orderType)){
            //TODO
        }
        //处方单
        if(Const.JC.equals(orderType)){
            //TODO
        }
        log.warn("未知的订单类型:"+orderType);
        return null;
    }

    private void releaseResources(OrderItem orderItem){
        //归还药物资源
        if(null != orderItem.getDrugId()){
            //TODO
        }
        //归还挂号资源
        if(null != orderItem.getTicketResourceId()){
            TicketResource ticketResource = ticketResourceService.getTicketResourceById(orderItem.getTicketResourceId());
            ticketResource.setTicketLastNumber(ticketResource.getTicketLastNumber() + 1);
            ticketResourceService.updateTicketResource(ticketResource);
        }
    }

    private void lockResources(OrderItem orderItem) throws Exception {
        //减少药物资源
        if(null != orderItem.getDrugId()){
            //TODO
        }
        //减少挂号资源
        if(null != orderItem.getTicketResourceId()){
            TicketResource ticketResource = ticketResourceService.getTicketResourceById(orderItem.getTicketResourceId());
            //库存小于零不给下单
            if(ticketResource.getTicketLastNumber() -1 < 0){
                throw new Exception("库存小于零不给下单");
            }
            ticketResource.setTicketLastNumber(ticketResource.getTicketLastNumber() - 1);
            ticketResourceService.updateTicketResource(ticketResource);
        }
    }
}