package com.gxf.his.po.vo;

import com.gxf.his.po.generate.*;
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

    private List<OrderItemVo> orderVoItemList;

    private List<DoctorTicketResource> ticketResourceList;

    private DoctorTicket doctorTicket;

    private DoctorVo doctorVo;

    private Date ticketStartTime;

    private Date ticketEndTime;

    private PrescriptionVo prescriptionVo;

    private static final long serialVersionUID = 1L;

}
