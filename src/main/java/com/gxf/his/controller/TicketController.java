package com.gxf.his.controller;

import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.po.vo.TicketVo;
import com.gxf.his.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 龚秀峰
 * @date 2019-10-29
 */
@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketController {
    @Resource
    private TicketService ticketService;
    @PostMapping
    public <T>ServerResponseVO<T> addTicket(@RequestBody TicketVo ticketVo){
        //TODO
        return ServerResponseVO.success();
    }

}
