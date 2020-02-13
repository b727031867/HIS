package com.gxf.his.service;

import com.gxf.his.po.generate.Order;
import com.gxf.his.po.vo.OrderVo;

import java.util.HashMap;

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
     * 检查当前是否重复挂号
     * @param patientId 患者ID
     * @param doctorId 医生ID
     * @param resourceId 票务资源ID
     * @return 是否重复挂号 是表示重复挂号
     */
    Boolean isRepeatRegisterOrder(Long patientId,Long doctorId,Long resourceId);

    /**
     * 添加一个处方单订单及其订单项
     *
     * @param order 要添加的订单
     */
    void addOrderPrescription(OrderVo order);

    /**
     * 添加一个挂号订单
     *
     * @param order 要添加的订单
     * @param ticketInfo 要添加的挂号信息
     * @return 新增订单的ID
     */
    Long addRegisterOrder(OrderVo order, HashMap<String,Object> ticketInfo);

    /**
     * 删除一个订单及其订单项
     *
     * @param orderId 要删除的订单ID
     */
    void deleteOrder(Long orderId);

    /**
     * 根据订单类型和资源ID获取订单资源
     *
     * @param orderType  订单类型编号
     * @param resourceId 资源ID
     * @return 具体的资源对象
     */
    <T> T getResourceByOrderTypeAndId(Integer orderType, Long resourceId);

    /**
     * 根据订单ID获取订单
     * @param orderId 订单ID
     * @return 订单
     */
    OrderVo getOrderByOrderId(Long orderId);

    /**
     * 挂号付款
     * @param orderId 订单ID
     * @return 是否成功付款
     */
    Boolean payRegisterOrder(Long orderId);

}
