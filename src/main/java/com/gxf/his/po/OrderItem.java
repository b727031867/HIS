package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author GXF
 */
@Data
public class OrderItem implements Serializable {
    private Long orderItemId;

    private Long orderId;

    private Long drugId;

    private Integer drugQuantities;

    private BigDecimal orderItemTotal;

    private String doctorAdvice;

    private Long ticketResourceId;

    private Long checkItemId;

    private static final long serialVersionUID = 1L;


}