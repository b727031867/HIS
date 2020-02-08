package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GXF
 * 某位医生一周内的挂号资源类
 */
@Data
public class TicketResource implements Serializable {
    private Long registeredResourceId;

    private Long doctorId;

    private String day;

    private Integer ticketLastNumber;

    private Date availableDate;

    private static final long serialVersionUID = 1L;

}