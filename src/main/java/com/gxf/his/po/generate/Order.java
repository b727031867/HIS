package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderType=").append(orderType);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", patientId=").append(patientId);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", orderTotal=").append(orderTotal);
        sb.append(", orderCreateTime=").append(orderCreateTime);
        sb.append(", orderExpireTime=").append(orderExpireTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}