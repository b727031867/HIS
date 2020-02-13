package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.SchedulingException;
import com.gxf.his.mapper.dao.ISchedulingMapper;
import com.gxf.his.po.generate.DoctorScheduling;
import com.gxf.his.service.SchedulingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 龚秀峰
 * @date 2019-10-29
 */
@Service
@Slf4j
public class SchedulingServiceImpl implements SchedulingService {

    @Resource
    private ISchedulingMapper iSchedulingMapper;

    @Override
    public int addScheduling(DoctorScheduling scheduling) {
        try {
            return iSchedulingMapper.insertAndInjectThePrimaryKey(scheduling);
        } catch (Exception e) {
            log.error("排班信息插入失败！" + e.getMessage());
            throw new SchedulingException(ServerResponseEnum.SCHEDULING_SAVE_FAIL);
        }
    }

    @Override
    public int deleteScheduling(Long schedulingId) {
        try {
            return iSchedulingMapper.deleteByPrimaryKey(schedulingId);
        } catch (Exception e) {
            log.error("删除排班信息失败！信息为：" + e.getMessage());
            throw new SchedulingException(ServerResponseEnum.SCHEDULING_DELETE_FAIL);
        }
    }

    @Override
    public int updateScheduling(DoctorScheduling scheduling) {
        try {
            return iSchedulingMapper.updateByPrimaryKey(scheduling);
        } catch (Exception e) {
            log.error("更新排班信息失败！信息为：" + e.getMessage());
            throw new SchedulingException(ServerResponseEnum.SCHEDULING_UPDATE_FAIL);
        }
    }

    @Override
    public DoctorScheduling selectSchedulingById(Long id) {
        DoctorScheduling scheduling;
        try {
            scheduling = iSchedulingMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.error("查找排班信息失败！信息为：" + e.getMessage());
            throw new SchedulingException(ServerResponseEnum.SCHEDULING_SELECT_FAIL);
        }
        return scheduling;
    }
}
