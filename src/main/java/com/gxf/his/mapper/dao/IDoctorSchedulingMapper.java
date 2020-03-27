package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.DoctorSchedulingMapper;
import com.gxf.his.po.generate.DoctorScheduling;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/19 15:38
 */
public interface IDoctorSchedulingMapper extends DoctorSchedulingMapper {

    /**
     * 插入一条排班信息并且注入主键
     * @param record 排班信息
     * @return 本次操作邮箱的行数
     */
    @Insert({
            "insert into entity_doctor_scheduling (scheduling_id, scheduling_type, ",
            "scheduling_time, scheduling_room)",
            "values (#{schedulingId,jdbcType=BIGINT}, #{schedulingType,jdbcType=VARCHAR}, ",
            "#{schedulingTime,jdbcType=VARCHAR}, #{schedulingRoom,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "schedulingId", keyColumn = "scheduling_id")
    int insertAndRejectId(DoctorScheduling record);
}
