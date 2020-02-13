package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.TicketResourceException;
import com.gxf.his.mapper.dao.ITicketResourceMapper;
import com.gxf.his.po.generate.DoctorTicketResource;
import com.gxf.his.service.TicketResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 票务资源的服务类
 *
 * @author GXF
 */
@Service
@Slf4j
public class TicketResourceServiceImpl implements TicketResourceService {

    @Resource
    private ITicketResourceMapper iTicketResourceMapper;

    @Override
    public void addTicketResource(DoctorTicketResource ticketResource) {
        try {
            iTicketResourceMapper.insert(ticketResource);
        } catch (Exception e) {
            log.error("票务资源保存失败", e);
            throw new TicketResourceException(ServerResponseEnum.TICKET_RESOURCE_SAVE_FAIL);
        }
    }

    @Override
    public List<DoctorTicketResource> getTicketResourceByDoctorIdAndAvailableDate(Long doctorId, Date availableDateStart, Date availableDateEnd) {
        try {
            return iTicketResourceMapper.selectByDoctorIdAndAvailableDate(doctorId, availableDateStart, availableDateEnd);
        } catch (Exception e) {
            log.error("票务资源查询失败", e);
            throw new TicketResourceException(ServerResponseEnum.TICKET_RESOURCE_LIST_FAIL);
        }
    }

    @Override
    public DoctorTicketResource getTicketResourceById(Long ticketResourceId) {
        try {
            return iTicketResourceMapper.selectByPrimaryKey(ticketResourceId);
        } catch (Exception e) {
            log.error("票务资源查询失败", e);
            throw new TicketResourceException(ServerResponseEnum.TICKET_RESOURCE_LIST_FAIL);
        }
    }

    @Override
    public Date getTicketResourceMaxDate() {
        try {
            Date expirationDate = iTicketResourceMapper.selectByMaxAvailableDate().getAvailableDate();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(expirationDate);
            calendar.add(Calendar.DATE, 1);
            expirationDate = calendar.getTime();
            return expirationDate;
        } catch (Exception e) {
            log.error("票务资源过期日期查询失败", e);
            throw new TicketResourceException(ServerResponseEnum.TICKET_RESOURCE_EXPIRATION_DATE_FAIL);
        }
    }

    @Override
    public Date getTicketResourceMinDate() {
        try {
            Date startDate = iTicketResourceMapper.selectByMaxAvailableDate().getAvailableDate();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(startDate);
            //只能提前七天预定，所以预定票的第七天减少六天则为放票当天的日期
            calendar.add(Calendar.DATE, -6);
            startDate = calendar.getTime();
            return startDate;
        } catch (Exception e) {
            log.error("票务资源过期日期查询失败", e);
            throw new TicketResourceException(ServerResponseEnum.TICKET_RESOURCE_EXPIRATION_DATE_FAIL);
        }
    }

    @Override
    public int updateTicketResource(DoctorTicketResource ticketResource) {
        try {
            return iTicketResourceMapper.updateByPrimaryKey(ticketResource);
        } catch (Exception e) {
            log.error("票务资源更新失败", e);
            throw new TicketResourceException(ServerResponseEnum.TICKET_RESOURCE_UPDATE_FAIL);
        }
    }

    @Override
    public int deleteTicketResource(Date availableDate, Long doctorId) {
        try {
            return iTicketResourceMapper.deleteByDoctorIdAndAvailableDate(doctorId, availableDate);
        } catch (Exception e) {
            log.error("票务资源更新失败", e);
            throw new TicketResourceException(ServerResponseEnum.TICKET_RESOURCE_UPDATE_FAIL);
        }
    }
}
