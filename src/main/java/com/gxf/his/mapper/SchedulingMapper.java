package com.gxf.his.mapper;

import com.gxf.his.po.Scheduling;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface SchedulingMapper {
    @Delete({
        "delete from entity_scheduling",
        "where scheduling_id = #{schedulingId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer schedulingId);

    @Insert({
        "insert into entity_scheduling (scheduling_id, scheduling_type, ",
        "scheduling_time, scheduling_room)",
        "values (#{schedulingId,jdbcType=INTEGER}, #{schedulingType,jdbcType=BIT}, ",
        "#{schedulingTime,jdbcType=VARCHAR}, #{schedulingRoom,jdbcType=VARCHAR})"
    })
    int insert(Scheduling record);

    @Select({
        "select",
        "scheduling_id, scheduling_type, scheduling_time, scheduling_room",
        "from entity_scheduling",
        "where scheduling_id = #{schedulingId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="scheduling_id", property="schedulingId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="scheduling_type", property="schedulingType", jdbcType=JdbcType.BIT),
        @Result(column="scheduling_time", property="schedulingTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_room", property="schedulingRoom", jdbcType=JdbcType.VARCHAR)
    })
    Scheduling selectByPrimaryKey(Integer schedulingId);

    @Select({
        "select",
        "scheduling_id, scheduling_type, scheduling_time, scheduling_room",
        "from entity_scheduling"
    })
    @Results({
        @Result(column="scheduling_id", property="schedulingId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="scheduling_type", property="schedulingType", jdbcType=JdbcType.BIT),
        @Result(column="scheduling_time", property="schedulingTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_room", property="schedulingRoom", jdbcType=JdbcType.VARCHAR)
    })
    List<Scheduling> selectAll();

    @Update({
        "update entity_scheduling",
        "set scheduling_type = #{schedulingType,jdbcType=BIT},",
          "scheduling_time = #{schedulingTime,jdbcType=VARCHAR},",
          "scheduling_room = #{schedulingRoom,jdbcType=VARCHAR}",
        "where scheduling_id = #{schedulingId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Scheduling record);
}