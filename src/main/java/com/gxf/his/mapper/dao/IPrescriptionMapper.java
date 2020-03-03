package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.PrescriptionMapper;
import com.gxf.his.po.generate.Prescription;
import com.gxf.his.po.vo.PrescriptionVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * 处方单模块的DAO接口
 */
public interface IPrescriptionMapper extends PrescriptionMapper {

    /**
     * 根据医生Id和患者Id查询某段时间内的处方单信息
     *
     * @param doctorId  医生ID
     * @param patientId 患者ID
     * @param start     开始时间
     * @param end       结束时间
     * @return 处方单列表
     */
    @Select({
            "select",
            "*",
            "from entity_prescription ",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND patient_id = #{patientId,jdbcType=BIGINT} AND ",
            "create_datetime < #{end,jdbcType=TIMESTAMP} AND create_datetime > #{start,jdbcType=TIMESTAMP} ORDER BY create_datetime "
    })
    @Results({
            @Result(column = "prescription_id", property = "prescriptionId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "total_spend", property = "totalSpend", jdbcType = JdbcType.DECIMAL),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "doctor_advice", property = "doctorAdvice", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_datetime", property = "createDatetime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<Prescription> selectPrescriptionByDoctorIdAndPatientIdAndRange(Long doctorId, Long patientId, Date start, Date end);

    /**
     * 根据挂号信息的ID查询某次挂号开具的处方单信息
     *
     * @param ticketId 挂号信息的ID
     * @return 处方单列表
     */
    @Select({
            "select",
            "*",
            "from entity_prescription ",
            "where ticket_id = #{ticketId,jdbcType=BIGINT} "
    })
    @Results({
            @Result(column = "prescription_id", property = "prescriptionId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "total_spend", property = "totalSpend", jdbcType = JdbcType.DECIMAL),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "doctor_advice", property = "doctorAdvice", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_datetime", property = "createDatetime", jdbcType = JdbcType.TIMESTAMP)
    })
    Prescription selectPrescriptionByTicketId(Long ticketId);

    /**
     * 插入一条处方单信息，并且注入主键
     *
     * @param record 处方单
     * @return 本次操作影响的行数
     */
    @Insert({
            "insert into entity_prescription (prescription_id, total_spend, ",
            "doctor_advice, patient_id, ",
            "ticket_id, doctor_id, ",
            "create_datetime)",
            "values (#{prescriptionId,jdbcType=BIGINT}, #{totalSpend,jdbcType=DECIMAL}, ",
            "#{doctorAdvice,jdbcType=VARCHAR}, #{patientId,jdbcType=BIGINT}, ",
            "#{ticketId,jdbcType=BIGINT}, #{doctorId,jdbcType=BIGINT}, ",
            "#{createDatetime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "prescriptionId", keyColumn = "prescription_id")
    int insertAndInjectId(Prescription record);

    /**
     * 根据挂号信息ID关联查询处方单信息
     *
     * @param doctorTicketId 挂号信息ID
     * @return 关联查询的处方单信息
     */
    @Select({
            "select",
            "*",
            "from entity_prescription",
            "where ticket_id = #{ticketId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "prescription_id", property = "prescriptionId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "total_spend", property = "totalSpend", jdbcType = JdbcType.DECIMAL),
            @Result(column = "doctor_advice", property = "doctorAdvice", jdbcType = JdbcType.VARCHAR),
            @Result(column = "patient_id", property = "patientId", jdbcType = JdbcType.BIGINT),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ticket_id", property = "ticketId", jdbcType = JdbcType.BIGINT),
            @Result(column = "patient_id", property = "patient", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_PATIENT)),
            @Result(column = "doctor_id", property = "doctorVo", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_TICKET_DOCTOR_ALL)),
            @Result(column = "ticket_id", property = "doctorTicket", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_DOCTOR_TICKET)),
            @Result(column = "prescription_id", property = "prescriptionInfoVos", jdbcType = JdbcType.BIGINT, many = @Many(select = MapperConst.MANY_DRUG_ITEM_ALL)),
            @Result(column = "prescription_id", property = "prescriptionExtraCost", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_PRESCRIPTION_EXTRA_COST)),
            @Result(column = "create_datetime", property = "createDatetime", jdbcType = JdbcType.TIMESTAMP)
    })
    PrescriptionVo getPrescriptionByDoctorTicketId(Long doctorTicketId);
}