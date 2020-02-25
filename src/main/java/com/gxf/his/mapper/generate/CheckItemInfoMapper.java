package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.CheckItemInfo;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface CheckItemInfoMapper {
    @Delete({
        "delete from entity_check_item_info",
        "where check_item_info_id = #{checkItemInfoId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long checkItemInfoId);

    @Insert({
        "insert into entity_check_item_info (check_item_info_id, check_item_id, ",
        "patient_id, doctor_id, ",
        "operate_id, create_time, ",
        "content)",
        "values (#{checkItemInfoId,jdbcType=BIGINT}, #{checkItemId,jdbcType=BIGINT}, ",
        "#{patientId,jdbcType=BIGINT}, #{doctorId,jdbcType=BIGINT}, ",
        "#{operateId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{content,jdbcType=LONGVARCHAR})"
    })
    int insert(CheckItemInfo record);

    @Select({
        "select",
        "check_item_info_id, check_item_id, patient_id, doctor_id, operate_id, create_time, ",
        "content",
        "from entity_check_item_info",
        "where check_item_info_id = #{checkItemInfoId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="check_item_info_id", property="checkItemInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="check_item_id", property="checkItemId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    CheckItemInfo selectByPrimaryKey(Long checkItemInfoId);

    @Select({
        "select",
        "check_item_info_id, check_item_id, patient_id, doctor_id, operate_id, create_time, ",
        "content",
        "from entity_check_item_info"
    })
    @Results({
        @Result(column="check_item_info_id", property="checkItemInfoId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="check_item_id", property="checkItemId", jdbcType=JdbcType.BIGINT),
        @Result(column="patient_id", property="patientId", jdbcType=JdbcType.BIGINT),
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT),
        @Result(column="operate_id", property="operateId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<CheckItemInfo> selectAll();

    @Update({
        "update entity_check_item_info",
        "set check_item_id = #{checkItemId,jdbcType=BIGINT},",
          "patient_id = #{patientId,jdbcType=BIGINT},",
          "doctor_id = #{doctorId,jdbcType=BIGINT},",
          "operate_id = #{operateId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where check_item_info_id = #{checkItemInfoId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CheckItemInfo record);
}