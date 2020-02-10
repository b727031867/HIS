package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.Scheduling;
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
        "where scheduling_id = #{schedulingId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long schedulingId);

    @Insert({
        "insert into entity_scheduling (scheduling_id, scheduling_type, ",
        "scheduling_time, scheduling_room)",
        "values (#{schedulingId,jdbcType=BIGINT}, #{schedulingType,jdbcType=VARCHAR}, ",
        "#{schedulingTime,jdbcType=VARCHAR}, #{schedulingRoom,jdbcType=VARCHAR})"
    })
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