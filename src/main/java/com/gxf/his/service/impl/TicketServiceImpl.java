package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.TicketException;
import com.gxf.his.mapper.dao.ITicketMapper;
import com.gxf.his.mapper.dao.ITicketResourceMapper;
import com.gxf.his.po.generate.DoctorTicket;
import com.gxf.his.po.vo.TicketVo;
import com.gxf.his.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/10 16:48
 */
@Service
@Slf4j
public class TicketServiceImpl implements TicketService {
    @Resource
    private ITicketMapper iTicketMapper;
    @Resource
    private ITicketResourceMapper iTicketResourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean doQueue(Long doctorTicketId) {
        //将票务资源变成排队状态并且设置排队时间
        DoctorTicket doctorTicket = iTicketMapper.selectByPrimaryKey(doctorTicketId);
        if (doctorTicket == null) {
            return false;
        }
        //计算排队名次
        TicketVo ticketVo = iTicketMapper.countTicketQueue(doctorTicket.getRegisteredResourceId());
        if (ticketVo == null) {
            doctorTicket.setTicketNumber(1);
        } else {
            doctorTicket.setTicketNumber(ticketVo.getRank() + 1);
        }
        doctorTicket.setStatus(4);
        doctorTicket.setActiveTime(new Date());
        iTicketMapper.updateByPrimaryKey(doctorTicket);
        return true;
    }

    @Override
    public void checkExpiredTicket() {
        List<DoctorTicket> tickets = iTicketMapper.getExpiredAndUnusedTicket();
        if (null != tickets) {
            for (DoctorTicket ticket : tickets) {
                ticket.setStatus(3);
                iTicketMapper.updateByPrimaryKey(ticket);
            }
        }
    }

    @Override
    public int insertTicket(DoctorTicket ticket) {
        try {
            return iTicketMapper.insert(ticket);
        } catch (Exception e) {
            log.error("插入挂号信息失败", e);
            throw new TicketException(ServerResponseEnum.TICKET_SAVE_FAIL);
        }
    }

    @Override
    public List<DoctorTicket> getTicketByPatientId(Long patientId) {
        try {
            return iTicketMapper.getTicketByPatientId(patientId);
        } catch (Exception e) {
            log.error("获取挂号信息失败", e);
            throw new TicketException(ServerResponseEnum.TICKET_LIST_FAIL);
        }
    }

    @Override
    public DoctorTicket getTicketById(Long doctorTicketId) {
        try {
            return iTicketMapper.selectByPrimaryKey(doctorTicketId);
        } catch (Exception e) {
            log.error("获取挂号信息失败", e);
            throw new TicketException(ServerResponseEnum.TICKET_LIST_FAIL);
        }
    }

    @Override
    public List<DoctorTicket> getDidNotUsePatientTicket(Long patientId) {
        try {
            return iTicketMapper.getTicketByPatientId(patientId);
        } catch (Exception e) {
            log.error("获取挂号信息失败", e);
            throw new TicketException(ServerResponseEnum.TICKET_LIST_FAIL);
        }
    }

    @Override
    public int updateTicketInfo(DoctorTicket ticket) {
        try {
            return iTicketMapper.updateByPrimaryKey(ticket);
        } catch (Exception e) {
            log.error("更新挂号信息失败", e);
            throw new TicketException(ServerResponseEnum.TICKET_UPDATE_FAIL);
        }
    }

    @Override
    public int deleteTicketByPrimaryKey(Long ticketId) {
        try {
            return iTicketMapper.deleteByPrimaryKey(ticketId);
        } catch (Exception e) {
            log.error("删除挂号信息失败", e);
            throw new TicketException(ServerResponseEnum.TICKET_DELETE_FAIL);
        }
    }
}
