package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.SchedulingException;
import com.gxf.his.mapper.SchedulingMapper;
import com.gxf.his.po.Scheduling;
import com.gxf.his.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 龚秀峰
 * @date 2019-10-29
 */
@Service
public class SchedulingServiceImpl implements SchedulingService {

    private Logger logger = LoggerFactory.getLogger(SchedulingServiceImpl.class);

    @Resource
    private SchedulingMapper schedulingMapper;

    @Override
    public Long addScheduling(Scheduling scheduling) {
        try {
            schedulingMapper.insert(scheduling);
            return scheduling.getSchedulingId();
        } catch (Exception e) {
            logger.error("排班信息插入失败！" + e.getMessage());
            throw new SchedulingException(ServerResponseEnum.SCHEDULING_SAVE_FAIL);
        }
    }

    @Override
    public void deleteScheduling(Long schedulingId) {
        try {
            schedulingMapper.deleteByPrimaryKey(schedulingId);
        } catch (Exception e) {
            logger.error("删除排班信息失败！信息为：" + e.getMessage());
            throw new SchedulingException(ServerResponseEnum.SCHEDULING_DELETE_FAIL);
        }
    }

    @Override
    public void updateScheduling(Scheduling scheduling) {
        try {
            schedulingMapper.updateByPrimaryKey(scheduling);
        } catch (Exception e) {
            logger.error("更新排班信息失败！信息为：" + e.getMessage());
            throw new SchedulingException(ServerResponseEnum.SCHEDULING_UPDATE_FAIL);
        }
    }

    @Override
    public Scheduling selectSchedulingById(Long id) {
        Scheduling scheduling;
        try {
            scheduling = schedulingMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error("查找排班信息失败！信息为：" + e.getMessage());
            throw new SchedulingException(ServerResponseEnum.SCHEDULING_SELECT_FAIL);
        }
        return scheduling;
    }
}
