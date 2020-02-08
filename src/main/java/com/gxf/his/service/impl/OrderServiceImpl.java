package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.OrderException;
import com.gxf.his.mapper.OrderItemMapper;
import com.gxf.his.mapper.OrderMapper;
import com.gxf.his.po.Order;
import com.gxf.his.po.OrderItem;
import com.gxf.his.po.TicketResource;
import com.gxf.his.service.OrderService;
import com.gxf.his.service.TicketResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author GXF
 * 订单接口的实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

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
            logger.error("查询并且删除过期订单失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_LIST_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(Order order) {
        try {
            orderMapper.insert(order);
            List<OrderItem> orderItemList = order.getOrderItemList();
            Long orderId = order.getOrderId();
            for (OrderItem orderItem : orderItemList) {
                orderItem.setOrderId(orderId);
                orderItemMapper.insert(orderItem);
                //根据不同的订单项，锁定不同的资源
                lockResources(orderItem);
            }
        } catch (Exception e) {
            logger.error("订单添加失败", e);
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
            logger.error("订单删除失败", e);
            throw new OrderException(ServerResponseEnum.ORDER_DELETE_FAIL);
        }
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
