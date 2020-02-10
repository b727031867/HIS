package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderItemId=").append(orderItemId);
        sb.append(", orderId=").append(orderId);
        sb.append(", drugId=").append(drugId);
        sb.append(", drugQuantities=").append(drugQuantities);
        sb.append(", orderItemTotal=").append(orderItemTotal);
        sb.append(", doctorAdvice=").append(doctorAdvice);
        sb.append(", ticketResourceId=").append(ticketResourceId);
        sb.append(", checkItemId=").append(checkItemId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}