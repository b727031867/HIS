package com.gxf.his.controller;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.po.generate.Scheduling;
import com.gxf.his.service.SchedulingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 龚秀峰
 * @date 2019-10-29
 */
@RestController
@RequestMapping("/scheduling")
@Slf4j
public class SchedulingController {
    private Logger logger = LoggerFactory.getLogger(SchedulingController.class);

    @Resource
    private SchedulingService schedulingService;

    @PostMapping
    public ServerResponseVO addScheduling(Scheduling scheduling) {
        logger.info("要添加的Scheduling信息为：" + scheduling.toString());
        if (StringUtils.isNotEmpty(scheduling.getSchedulingRoom()) && StringUtils.isNotEmpty(scheduling.getSchedulingTime())
                && scheduling.getSchedulingType() != null) {
            schedulingService.addScheduling(scheduling);
            ServerResponseVO.success();
        }
        return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
    }

    @GetMapping
    public ServerResponseVO getSchedulingById(Long schedulingId) {
        logger.info("获取到的SchedulingId为：" + schedulingId);
        if (null != schedulingId) {
            Scheduling scheduling = schedulingService.selectSchedulingById(schedulingId);
            return ServerResponseVO.success(scheduling);
        }
        return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
    }

    @DeleteMapping
    public ServerResponseVO deleteSchedulingById(Long schedulingId) {
        logger.info("获取到的SchedulingId为：" + schedulingId);
        if (null != schedulingId) {
            schedulingService.deleteScheduling(schedulingId);
            return ServerResponseVO.success();
        }
        return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
    }

    @PutMapping
    public ServerResponseVO updateSchedulingById(Scheduling scheduling) {
        logger.info("获取到的Scheduling信息为：" + scheduling.toString());
        schedulingService.updateScheduling(scheduling);
        return ServerResponseVO.success();
    }


}
