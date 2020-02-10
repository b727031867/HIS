package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TicketResource implements Serializable {
    private Long registeredResourceId;

    private Long doctorId;

    private String day;

    private Integer ticketLastNumber;

    private Date availableDate;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", registeredResourceId=").append(registeredResourceId);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", day=").append(day);
        sb.append(", ticketLastNumber=").append(ticketLastNumber);
        sb.append(", availableDate=").append(availableDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}