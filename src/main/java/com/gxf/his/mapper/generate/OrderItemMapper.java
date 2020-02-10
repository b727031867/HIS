package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.OrderItem;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface OrderItemMapper {
    @Delete({
        "delete from entity_order_item",
        "where order_item_id = #{orderItemId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long orderItemId);

    @Insert({
        "insert into entity_order_item (order_item_id, order_id, ",
        "drug_id, drug_quantities, ",
        "order_item_total, doctor_advice, ",
        "ticket_resource_id, check_item_id)",
        "values (#{orderItemId,jdbcType=BIGINT}, #{orderId,jdbcType=BIGINT}, ",
        "#{drugId,jdbcType=BIGINT}, #{drugQuantities,jdbcType=INTEGER}, ",
        "#{orderItemTotal,jdbcType=DECIMAL}, #{doctorAdvice,jdbcType=VARCHAR}, ",
        "#{ticketResourceId,jdbcType=BIGINT}, #{checkItemId,jdbcType=BIGINT})"
    })
    int insert(OrderItem record);

    @Select({
        "select",
        "order_item_id, order_id, drug_id, drug_quantities, order_item_total, doctor_advice, ",
        "ticket_resource_id, check_item_id",
        "from entity_order_item",
        "where order_item_id = #{orderItemId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="order_item_id", property="orderItemId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="drug_quantities", property="drugQuantities", jdbcType=JdbcType.INTEGER),
        @Result(column="order_item_total", property="orderItemTotal", jdbcType=JdbcType.DECIMAL),
        @Result(column="doctor_advice", property="doctorAdvice", jdbcType=JdbcType.VARCHAR),
        @Result(column="ticket_resource_id", property="ticketResourceId", jdbcType=JdbcType.BIGINT),
        @Result(column="check_item_id", property="checkItemId", jdbcType=JdbcType.BIGINT)
    })
    OrderItem selectByPrimaryKey(Long orderItemId);

    @Select({
        "select",
        "order_item_id, order_id, drug_id, drug_quantities, order_item_total, doctor_advice, ",
        "ticket_resource_id, check_item_id",
        "from entity_order_item"
    })
    @Results({
        @Result(column="order_item_id", property="orderItemId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
        @Result(column="drug_id", property="drugId", jdbcType=JdbcType.BIGINT),
        @Result(column="drug_quantities", property="drugQuantities", jdbcType=JdbcType.INTEGER),
        @Result(column="order_item_total", property="orderItemTotal", jdbcType=JdbcType.DECIMAL),
        @Result(column="doctor_advice", property="doctorAdvice", jdbcType=JdbcType.VARCHAR),
        @Result(column="ticket_resource_id", property="ticketResourceId", jdbcType=JdbcType.BIGINT),
        @Result(column="check_item_id", property="checkItemId", jdbcType=JdbcType.BIGINT)
    })
    List<OrderItem> selectAll();

    @Update({
        "update entity_order_item",
        "set order_id = #{orderId,jdbcType=BIGINT},",
          "drug_id = #{drugId,jdbcType=BIGINT},",
          "drug_quantities = #{drugQuantities,jdbcType=INTEGER},",
          "order_item_total = #{orderItemTotal,jdbcType=DECIMAL},",
          "doctor_advice = #{doctorAdvice,jdbcType=VARCHAR},",
          "ticket_resource_id = #{ticketResourceId,jdbcType=BIGINT},",
          "check_item_id = #{checkItemId,jdbcType=BIGINT}",
        "where order_item_id = #{orderItemId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(OrderItem record);
}