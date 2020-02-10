package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.SchedulingMapper;
import com.gxf.his.po.generate.Scheduling;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author 龚秀峰
 * 排班模块的DAO接口
 */
public interface ISchedulingMapper extends SchedulingMapper {

    /**
     * 插入排班信息
     * 插入数据后获取自增长的主键值自动注入到record对象中
     *
     * @param record 排班信息
     * @return 影响的行数 注入主键
     */
    @Insert({
            "insert into entity_scheduling (scheduling_id, scheduling_type, ",
            "scheduling_time, scheduling_room)",
            "values (#{schedulingId,jdbcType=BIGINT}, #{schedulingType,jdbcType=VARCHAR}, ",
            "#{schedulingTime,jdbcType=VARCHAR}, #{schedulingRoom,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "schedulingId", keyColumn = "scheduling_id")
    int insertAndInjectThePrimaryKey(Scheduling record);

}