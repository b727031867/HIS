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
    @Resource
    private ITicketMapper iTicketMapper;
    @Resource
    private ITicketResourceMapper iTicketResourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean doQueue(Long doctorTicketId) {
        //获取当前的票务信息
        DoctorTicket doctorTicket = iTicketMapper.selectByPrimaryKey(doctorTicketId);
        if (doctorTicket == null) {
            return false;
        }
        long dayMillisecond = 24 * 60 * 60 * 1000L;
        //计算今日某医生就诊中或者就诊完毕患者的总数
        List<DoctorTicket> tickets = iTicketMapper.selectTicketByActiveTimeAndDoctorId(doctorTicket.getDoctorId(), getDateZero(0L), getDateZero(dayMillisecond));
        //获取还有多少位患者在前方
        TicketVo ticketVo = iTicketMapper.countTicketQueue(doctorTicket.getRegisteredResourceId());
        //计算本日排名
        if (tickets.size() == 0 && ticketVo.getRank() == 0) {
            //今天是第一位就诊患者
            doctorTicket.setTicketNumber(1);
        } else if (tickets.size() != 0 && ticketVo.getRank() == 0) {
            //没有人在你前面 所以排队号码为就诊完毕与就诊中的总数+1
            doctorTicket.setTicketNumber(tickets.size() + 1);
        } else if (tickets.size() != 0 && ticketVo.getRank() != 0) {
            //有人拍你前面，并且有已经就诊中或者就诊完毕的人，则应该为两方相加 + 1
            doctorTicket.setTicketNumber(tickets.size() + ticketVo.getRank() + 1);
        } else if (tickets.size() == 0 && ticketVo.getRank() != 0) {
            //有人排你前面，医生还没开始就诊，则直接排前面的人+1
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
