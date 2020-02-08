package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author GXF
 */
@Data
public class Order implements Serializable {
    private Long orderId;

    private Integer orderType;

    private Long doctorId;

    private Long patientId;

    private String orderStatus;

    private BigDecimal orderTotal;

    private Date orderCreateTime;

    private Date orderExpireTime;

    private List<OrderItem> orderItemList;

    private static final long serialVersionUID = 1L;


}