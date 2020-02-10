package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.OrderMapper;
import com.gxf.his.po.generate.Order;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 订单模块的DAO接口
 */
public interface IOrderMapper extends OrderMapper {

    /**
     * 查询未付款并且过期的商品列表及其商品项
     *
     * @return 过期的商品列表及其商品项
     */
    @Select({
            "select",
            "order_id, order_type, doctor_id, patient_id, order_status, order_total, order_create_time,order_expire_time",
            "from entity_order",
            "where order_status = 0 AND order_expire_time <= NOW()"
    })
    @Results({
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "order_type", property = "orderType", jdbcType = JdbcType.INTEGER),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "order_status", property = "orderStatus", jdbcType = JdbcType.VARCHAR),
            @Result(column = "order_total", property = "orderTotal", jdbcType = JdbcType.DECIMAL),
            @Result(column = "order_create_time", property = "orderCreateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "order_expire_time", property = "orderExpireTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "order_id", property = "orderItemList", many = @Many(select = MapperConst.MANY_ORDER_ITEM))
    })
    List<Order> selectExpireOrder();

}