package com.gxf.his.service;

import com.gxf.his.po.generate.DoctorScheduling;

/**
 * @author 龚秀峰
 * @date 2019-10-29
 */
public interface SchedulingService {

    /**
     * 插入一条排班信息
     *
     * @param scheduling 排班信息类
     * @return 返回本次操作影响的行数
     */
    int addScheduling(DoctorScheduling scheduling);

    /**
     * 删除一条排班信息
     *
     * @param schedulingId 排班信息的ID
     */
    int deleteScheduling(Long schedulingId);

    /**
     * 更新排班信息
     *
     * @param scheduling 排班信息
     */
    int updateScheduling(DoctorScheduling scheduling);

    /**
     * 查找一条排班信息
     *
     * @param id 排班信息的ID
     * @return 排班信息实体
     */
    DoctorScheduling selectSchedulingById(Long id);
}
