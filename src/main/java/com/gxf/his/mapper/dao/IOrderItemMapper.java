package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.OrderItemMapper;
import com.gxf.his.po.generate.OrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 订单项的DAO接口
 */
public interface IOrderItemMapper extends OrderItemMapper {
    /**
     * 根据订单ID删除订单项
     *
     * @param orderId 订单ID
     * @return 影响的行数
     */
    @Delete({
            "delete from entity_order_item",
            "where order_id = #{orderId,jdbcType=BIGINT}"
    })
    int deleteByOrderId(Long orderId);

    /**
     * 根据订单ID查找订单项
     *
     * @param orderId 订单ID
     * @return 订单项列表
     */
    @Select({
            "select",
            "order_item_id, order_id, drug_id, drug_quantities, order_item_total, doctor_advice,ticket_resource_id,check_item_id",
            "from entity_order_item",
            "where order_id = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "order_item_id", property = "orderItemId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "drug_id", property = "drugId", jdbcType = JdbcType.BIGINT),
            @Result(column = "drug_quantities", property = "drugQuantities", jdbcType = JdbcType.INTEGER),
            @Result(column = "order_item_total", property = "orderItemTotal", jdbcType = JdbcType.DECIMAL),
            @Result(column = "doctor_advice", property = "doctorAdvice", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_resource_id", property = "ticketResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "check_item_id", property = "checkItemId", jdbcType = JdbcType.BIGINT)
    })
    List<OrderItem> findOrderItemsByOrderId(Long orderId);

}