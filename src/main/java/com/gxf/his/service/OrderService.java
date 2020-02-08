package com.gxf.his.service;

import com.gxf.his.po.Order;

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

    /**
     * 根据订单类型和资源ID获取订单资源
     * @param orderType 订单类型编号
     * @param resourceId 资源ID
     * @return 具体的资源对象
     */
    <T> T getResourceByOrderTypeAndId(Integer orderType,Long resourceId);
}
