package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.OrderItemMapper;
import com.gxf.his.po.generate.OrderItem;
import com.gxf.his.po.vo.OrderItemVo;
import org.apache.ibatis.annotations.*;
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
            "order_item_id, order_id, ticket_resource_id,check_item_id",
            "from entity_order_item",
            "where order_id = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "order_item_id", property = "orderItemId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ticket_resource_id", property = "ticketResourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "check_item_id", property = "checkItemId", jdbcType = JdbcType.BIGINT)
    })
    List<OrderItem> findOrderItemsByOrderId(Long orderId);

    /**
     * 根据订单ID查找订单项
     * 查询完全关联信息
     * @param orderId 订单ID
     * @return 订单项列表
     */
    @Select({
            "select",
            "order_item_id, order_id,prescription_id, ticket_resource_id,check_item_id",
            "from entity_order_item",
            "where order_id = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "order_item_id", property = "orderItemId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT),
            @Result(column = "prescription_id", property = "prescription", jdbcType = JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_TICKET_RESOURCE)),
            @Result(column = "ticket_resource_id", property = "doctorTicketResource", jdbcType = JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_TICKET_RESOURCE)),
            @Result(column = "check_item_id", property = "checkItem", jdbcType = JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_CHECK_ITEM))
    })
    List<OrderItemVo> findOrderItemsByOrderIdRelated(Long orderId);

}