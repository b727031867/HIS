package com.gxf.his.mapper;

import com.gxf.his.po.Scheduling;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


public interface SchedulingMapper {
    @Delete({
        "delete from entity_scheduling",
        "where scheduling_id = #{schedulingId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long schedulingId);

    @Insert({
        "insert into entity_scheduling (scheduling_id, scheduling_type, ",
        "scheduling_time, scheduling_room)",
        "values (#{schedulingId,jdbcType=BIGINT}, #{schedulingType,jdbcType=VARCHAR}, ",
        "#{schedulingTime,jdbcType=VARCHAR}, #{schedulingRoom,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "schedulingId", keyColumn = "scheduling_id")
    int insert(Scheduling record);

    @Select({
        "select",
        "scheduling_id, scheduling_type, scheduling_time, scheduling_room",
        "from entity_scheduling",
        "where scheduling_id = #{schedulingId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="scheduling_id", property="schedulingId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="scheduling_type", property="schedulingType", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_time", property="schedulingTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_room", property="schedulingRoom", jdbcType=JdbcType.VARCHAR)
    })
    Scheduling selectByPrimaryKey(Long schedulingId);

    @Select({
        "select",
        "scheduling_id, scheduling_type, scheduling_time, scheduling_room",
        "from entity_scheduling"
    })
    @Results({
        @Result(column="scheduling_id", property="schedulingId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="scheduling_type", property="schedulingType", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_time", property="schedulingTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_room", property="schedulingRoom", jdbcType=JdbcType.VARCHAR)
    })
    List<Scheduling> selectAll();

    @Update({
        "update entity_scheduling",
        "set scheduling_type = #{schedulingType,jdbcType=VARCHAR},",
          "scheduling_time = #{schedulingTime,jdbcType=VARCHAR},",
          "scheduling_room = #{schedulingRoom,jdbcType=VARCHAR}",
        "where scheduling_id = #{schedulingId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Scheduling record);
}