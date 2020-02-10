package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.PrescriptionList;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PrescriptionListMapper {
    @Delete({
        "delete from entity_prescription_list",
        "where prescription_list_id = #{prescriptionListId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long prescriptionListId);

    @Insert({
        "insert into entity_prescription_list (prescription_list_id, total_spend, ",
        "patient_id, doctor_id, ",
        "create_datetime)",
        "values (#{prescriptionListId,jdbcType=BIGINT}, #{totalSpend,jdbcType=DECIMAL}, ",
        "#{patientId,jdbcType=BIGINT}, #{doctorId,jdbcType=BIGINT}, ",
        "#{createDatetime,jdbcType=TIMESTAMP})"
    })
    int insert(PrescriptionList record);

    @Select({
        "select",
        "prescription_list_id, total_spend, patient_id, doctor_id, create_datetime",
        "from entity_prescription_list",
        "where prescription_list_id = #{prescriptionListId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="prescription_list_id", property="prescriptionListId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="total_spend", property="totalSpend", jdbcType=JdbcType.DECIMAL),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP)
    })
    PrescriptionList selectByPrimaryKey(Long prescriptionListId);

    @Select({
        "select",
        "prescription_list_id, total_spend, patient_id, doctor_id, create_datetime",
        "from entity_prescription_list"
    })
    @Results({
        @Result(column="prescription_list_id", property="prescriptionListId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="total_spend", property="totalSpend", jdbcType=JdbcType.DECIMAL),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_datetime", property="createDatetime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PrescriptionList> selectAll();

    @Update({
        "update entity_prescription_list",
        "set total_spend = #{totalSpend,jdbcType=DECIMAL},",
          "patient_id = #{patientId,jdbcType=BIGINT},",
          "doctor_id = #{doctorId,jdbcType=BIGINT},",
          "create_datetime = #{createDatetime,jdbcType=TIMESTAMP}",
        "where prescription_list_id = #{prescriptionListId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PrescriptionList record);
}