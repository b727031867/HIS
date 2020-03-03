package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DoctorException;
import com.gxf.his.exception.TicketException;
import com.gxf.his.mapper.dao.ITicketMapper;
import com.gxf.his.po.generate.DoctorTicket;
import com.gxf.his.po.vo.TicketVo;
import com.gxf.his.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
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
    private long dayMillisecond = 24 * 60 * 60 * 1000L;
    @Resource
    private ITicketMapper iTicketMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean doQueue(Long doctorTicketId) {
        //获取当前的票务信息
        DoctorTicket doctorTicket = iTicketMapper.selectByPrimaryKey(doctorTicketId);
        if (doctorTicket == null) {
            return false;
        }
        doctorTicket.setActiveTime(new Date());
        iTicketMapper.updateByPrimaryKey(doctorTicket);
        //计算今日某医生就诊中、就诊完毕、叫号中、错过叫号的总患者数
        Date start = getDateZero(0L);
        Date end = getDateZero(dayMillisecond);
        List<DoctorTicket> tickets = iTicketMapper.selectTicketByActiveTimeAndDoctorId(doctorTicket.getDoctorId(),start ,end);
        //计算当前挂号排队的排名
        if (tickets.size() == 0) {
            //今天是第一位就诊患者
            doctorTicket.setTicketNumber(1);
        } else {
            doctorTicket.setTicketNumber(tickets.size() + 1);
        }
        doctorTicket.setStatus(4);
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
    public void checkExpiredTicketForUnCalling() {
        List<DoctorTicket> tickets = iTicketMapper.getExpiredAndUnCallingTicket();
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
    public TicketVo getTicketById(Long doctorTicketId) {
        try {
            return iTicketMapper.selectByPrimaryKeyRelative(doctorTicketId);
        } catch (Exception e) {
            log.error("关联的获取挂号信息失败", e);
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

    @Override
    public Integer getCurrentDoctorRank(Long doctorId) {
        TicketVo nextUsingTicketRank = iTicketMapper.getNextUsingTicketRank(doctorId);
        return nextUsingTicketRank.getTicketNumber();
    }

    @Override
    public TicketVo getCurrentRankPatient(Long doctorId, Integer ticketNumber) {
        Date start = getDateZero(0L);
        Date end = getDateZero(dayMillisecond);
        TicketVo ticketVo = iTicketMapper.getQueuePatientByDoctorIdAndRank(doctorId, ticketNumber,start,end);
        //设置为叫号中
        ticketVo.setStatus(6);
        iTicketMapper.updateByPrimaryKey(ticketVo);
        //让此排名之前排队中的人错过叫号
        List<TicketVo> doctorTickets = iTicketMapper.getUsingTicketsByDoctorId(doctorId);
        for (TicketVo doctorTicket : doctorTickets) {
            if (doctorTicket.getTicketNumber() != null) {
                //让小于等于上一位rank并且在排队中的挂号者错过挂号
                if (doctorTicket.getTicketNumber() < ticketNumber) {
                    doctorTicket.setStatus(2);
                    doctorTicket.setPatientId(doctorTicket.getPatient().getPatientId());
                    iTicketMapper.updateByPrimaryKey(doctorTicket);
                }
            } else {
                log.error("数据异常！出现排队但是却没有计算排名的情况!");
                throw new DoctorException(ServerResponseEnum.DOCTOR_CALL_FAIL);
            }
        }
        return ticketVo;
    }


    /**
     * 获取今天凌晨或beforeMillisecond毫秒前的时间
     *
     * @param millisecond 传入null表示获取今天凌晨 传入beforeMillisecond 表示距离今天凌晨多少毫秒（+）前或（-）后
     * @return 日期对象
     */
    private Date getDateZero(Long millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        if (millisecond == null || millisecond == 0) {
            return start;
        } else if (millisecond > 0) {
            //返回距离今天凌晨多少毫秒后
            long end = start.getTime() + millisecond;
            return new Date(end);
        } else {
            //返回距离今天凌晨多少毫秒前
            long end = start.getTime() - millisecond;
            return new Date(end);
        }
    }
}
