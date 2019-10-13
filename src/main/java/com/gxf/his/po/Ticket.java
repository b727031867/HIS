package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class Ticket implements Serializable {
    private Integer ticketId;

    private Integer ticketNumber;

    private String ticketType;

    private String ticketTimeType;

    private Date ticketCreateTime;

    private Date ticketValidityStart;

    private Date ticketValidityEnd;

    private Integer doctorId;

    private Integer patientId;

    private static final long serialVersionUID = 1L;

}