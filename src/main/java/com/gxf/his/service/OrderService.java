package com.gxf.his.service;

import com.gxf.his.po.Order;

import java.util.List;

/**
 * @author GXF
 * 订单服务类
 */
public interface OrderService {

    /**
     * 获取并且删除过期未付款的订单
     */
    void getAndRemoveExpireOrders();

    /**
     * 添加一个订单及其订单项
     * @param order 要添加的订单
     */
    void addOrder(Order order);

    /**
     * 删除一个订单及其订单项
     * @param orderId 要删除的订单ID
     */
    void deleteOrder(Long orderId);
}
