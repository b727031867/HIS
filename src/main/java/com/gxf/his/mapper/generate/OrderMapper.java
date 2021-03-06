package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.Order;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface OrderMapper {
    @Delete({
        "delete from entity_order",
        "where order_id = #{orderId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long orderId);

    @Insert({
        "insert into entity_order (order_id, order_type, ",
        "prescription_id, doctor_id, ",
        "patient_id, order_status, ",
        "order_total, order_create_time, ",
        "order_expire_time)",
        "values (#{orderId,jdbcType=BIGINT}, #{orderType,jdbcType=INTEGER}, ",
        "#{prescriptionId,jdbcType=BIGINT}, #{doctorId,jdbcType=BIGINT}, ",
        "#{patientId,jdbcType=BIGINT}, #{orderStatus,jdbcType=VARCHAR}, ",
        "#{orderTotal,jdbcType=DECIMAL}, #{orderCreateTime,jdbcType=TIMESTAMP}, ",
        "#{orderExpireTime,jdbcType=TIMESTAMP})"
    })
    int insert(Order record);

    @Select({
        "select",
        "order_id, order_type, prescription_id, doctor_id, patient_id, order_status, ",
        "order_total, order_create_time, order_expire_time",
        "from entity_order",
        "where order_id = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="order_type", property="orderType", jdbcType=JdbcType.INTEGER),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="order_status", property="orderStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="order_total", property="orderTotal", jdbcType=JdbcType.DECIMAL),
        @Result(column="order_create_time", property="orderCreateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="order_expire_time", property="orderExpireTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Order selectByPrimaryKey(Long orderId);

    @Select({
        "select",
        "order_id, order_type, prescription_id, doctor_id, patient_id, order_status, ",
        "order_total, order_create_time, order_expire_time",
        "from entity_order"
    })
    @Results({
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="order_type", property="orderType", jdbcType=JdbcType.INTEGER),
        @Result(column="prescription_id", property="prescriptionId", jdbcType=JdbcType.BIGINT),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="order_status", property="orderStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="order_total", property="orderTotal", jdbcType=JdbcType.DECIMAL),
        @Result(column="order_create_time", property="orderCreateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="order_expire_time", property="orderExpireTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Order> selectAll();

    @Update({
        "update entity_order",
        "set order_type = #{orderType,jdbcType=INTEGER},",
          "prescription_id = #{prescriptionId,jdbcType=BIGINT},",
          "doctor_id = #{doctorId,jdbcType=BIGINT},",
          "patient_id = #{patientId,jdbcType=BIGINT},",
          "order_status = #{orderStatus,jdbcType=VARCHAR},",
          "order_total = #{orderTotal,jdbcType=DECIMAL},",
          "order_create_time = #{orderCreateTime,jdbcType=TIMESTAMP},",
          "order_expire_time = #{orderExpireTime,jdbcType=TIMESTAMP}",
        "where order_id = #{orderId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Order record);
}