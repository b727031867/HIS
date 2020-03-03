package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.CheckItemInfoMapper;
import com.gxf.his.po.generate.CheckItemInfo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/21 20:32
 */
public interface ICheckItemInfoMapper extends CheckItemInfoMapper {

    /**
     * 根据医生Id和患者Id查询某段时间内的处方单信息
     *
     * @param doctorId  医生ID
     * @param patientId 患者ID
     * @param start     开始时间
     * @param end       结束时间
     * @return 检查项列表
     */
    @Select({
            "select",
            "*",
            "from entity_check_item_info",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND patient_id = #{patientId,jdbcType=BIGINT} AND  ",
            "create_time < #{end,jdbcType=TIMESTAMP} AND create_time > #{start,jdbcType=TIMESTAMP} ORDER BY create_time "
    })
    @Results({
            @Result(column = "check_item_info_id", property = "checkItemInfoId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "check_item_id", property = "checkItemId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT),
            @Result(column = "operate_id", property = "operateId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "content", property = "content", jdbcType = JdbcType.LONGVARCHAR)
    })
    List<CheckItemInfo> selectCheckItemInfoByDoctorIdAndPatientIdAndRange(Long doctorId, Long patientId, Date start, Date end);

    /**
     * 根据挂号信息ID查询某次挂号开具的检查项列表
     *
     * @param ticketId  挂号信息ID
     * @return 检查项列表
     */
    @Select({
            "select",
            "*",
            "from entity_check_item_info",
            "where ticket_id = #{ticketId,jdbcType=BIGINT} "
    })
    @Results({
            @Result(column = "check_item_info_id", property = "checkItemInfoId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "check_item_id", property = "checkItemId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT),
            @Result(column = "operate_id", property = "operateId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "content", property = "content", jdbcType = JdbcType.LONGVARCHAR)
    })
    List<CheckItemInfo> selectCheckItemInfosByTicketId(Long ticketId);
}
