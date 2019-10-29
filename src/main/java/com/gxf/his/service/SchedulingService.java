package com.gxf.his.service;

import com.gxf.his.po.Scheduling;

/**
 * @author 龚秀峰
 * @date 2019-10-29
 */
public interface SchedulingService {
    /**
     * 插入一条排班信息
     * @param scheduling 排班信息类
     */
    Integer addScheduling(Scheduling scheduling);

    /**
     * 删除一条排班信息
     * @param schedulingId 排班信息的ID
     */
    void deleteScheduling(Integer schedulingId);

    /**
     * 更新排班信息
     * @param scheduling
     */
    void updateScheduling(Scheduling scheduling);
}