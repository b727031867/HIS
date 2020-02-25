package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.PrescriptionMapper;
import com.gxf.his.po.generate.Prescription;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
            "prescription_id, total_spend, patient_id, doctor_advice, doctor_id, create_datetime",
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
}