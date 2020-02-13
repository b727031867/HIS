package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.OrderMapper;
import com.gxf.his.po.generate.Order;
import com.gxf.his.po.vo.OrderVo;
import org.apache.ibatis.annotations.*;
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
    List<OrderVo> selectExpireOrder();

    /**
     * 查询某位用户某种订单类型所有的订单历史信息
     * @param patientId 患者ID
     * @param orderType 订单类型
     * @return 订单历史信息列表
     */
    @Select({
            "select",
            "order_id, order_type, doctor_id, patient_id, order_status, order_total, order_create_time,order_expire_time",
            "from entity_order",
            "where patient_id = #{patientId,jdbcType=BIGINT} AND order_type = #{orderType,jdbcType=INTEGER}"
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
            @Result(column = "order_id", property = "orderVoItemList", many = @Many(select = MapperConst.MANY_ORDER_ITEM_ALL))
    })
    List<OrderVo> selectOrdersByPatientIdAndOrderType(Long patientId,Integer orderType);


    /**
     * 根据订单ID关联查询订单和订单项
     * @param orderId 订单ID
     * @return 订单及订单项
     */
    @Select({
            "select",
            "order_id, order_type, doctor_id, patient_id, order_status, order_total, order_create_time,order_expire_time",
            "from entity_order",
            "where order_id = #{orderId,jdbcType=BIGINT}"
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
            @Result(column = "order_id", property = "orderItemList", many = @Many(select = MapperConst.MANY_ORDER_ITEM)),
            @Result(column = "order_id", property = "doctorTicket", one = @One(select = MapperConst.ONE_ORDER_DOCTOR_TICKET))
    })
    OrderVo selectRegisterOrderByOrderId(Long orderId);

    /**
     * 根据患者ID和医生ID
     * 查询未付款的订单
     *
     * @param patientId 患者ID
     * @param doctorId  医生ID
     * @return 订单业务类
     */
    @Select({
            "select",
            "order_id",
            "from entity_order",
            "where order_status = 0 AND doctor_id = #{doctorId,jdbcType=BIGINT} AND patient_id = #{patientId,jdbcType=BIGINT} "
    })
    @Results({
            @Result(column = "order_id", property = "orderId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "order_id", property = "orderItemList", many = @Many(select = MapperConst.MANY_ORDER_ITEM))
    })
    List<OrderVo> selectRepeatRegisterOrder(Long patientId, Long doctorId);

    /**
     * 插入一条订单记录并且注入自增主键到参数中
     *
     * @param order 订单
     * @return 本次操作影响的行数
     */
    @Insert({
            "insert into entity_order (order_id, order_type, ",
            "doctor_id, patient_id, ",
            "order_status, order_total, ",
            "order_create_time, order_expire_time)",
            "values (#{orderId,jdbcType=BIGINT}, #{orderType,jdbcType=INTEGER}, ",
            "#{doctorId,jdbcType=BIGINT}, #{patientId,jdbcType=BIGINT}, ",
            "#{orderStatus,jdbcType=VARCHAR}, #{orderTotal,jdbcType=DECIMAL}, ",
            "#{orderCreateTime,jdbcType=TIMESTAMP}, #{orderExpireTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "order_id")
    int insertAndInjectId(Order order);

}