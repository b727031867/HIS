package com.gxf.his.po.vo;

import com.gxf.his.po.generate.Order;
import com.gxf.his.po.generate.OrderItem;
import com.gxf.his.po.generate.TicketResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/9 00:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderVo extends Order implements Serializable {

    private List<OrderItem> orderItemList;

    private List<TicketResource> ticketResourceList;

    private Date ticketStartTime;

    private Date ticketEndTime;

    private static final long serialVersionUID = 1L;

}
