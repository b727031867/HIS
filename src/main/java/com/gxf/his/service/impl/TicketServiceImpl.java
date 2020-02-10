package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.TicketException;
import com.gxf.his.mapper.dao.ITicketMapper;
import com.gxf.his.po.generate.Ticket;
import com.gxf.his.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public int insertTicket(Ticket ticket) {
        try{
            return iTicketMapper.insert(ticket);
        }catch (Exception e){
            log.error("插入挂号信息失败",e);
            throw new TicketException(ServerResponseEnum.TICKET_SAVE_FAIL);
        }
    }

    @Override
    public List<Ticket> getTicketByPatientId(Long patientId) {
        try{
            return iTicketMapper.getTicketByPatientId(patientId);
        }catch (Exception e){
            log.error("获取挂号信息失败",e);
            throw new TicketException(ServerResponseEnum.TICKET_LIST_FAIL);
        }
    }

    @Override
    public List<Ticket> getDidNotUsePatientTicket(Long patientId) {
        try{
            return iTicketMapper.getTicketByPatientId(patientId);
        }catch (Exception e){
            log.error("获取挂号信息失败",e);
            throw new TicketException(ServerResponseEnum.TICKET_LIST_FAIL);
        }
    }

    @Override
    public int updateTicketInfo(Ticket ticket) {
        return 0;
    }

    @Override
    public int deleteTicketByPrimaryKey(Long ticketId) {
        return 0;
    }
}
