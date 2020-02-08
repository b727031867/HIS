package com.gxf.his.vo;

import com.gxf.his.po.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author GXF
 * @version 1.0
 * @date 2020/2/9 00:36
 */
@Data
public class OrderVo implements Serializable {
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
