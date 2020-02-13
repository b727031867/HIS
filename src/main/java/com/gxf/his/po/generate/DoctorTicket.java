package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DoctorTicket implements Serializable {
    private Long ticketId;

    private Integer ticketNumber;

    private String ticketType;

    private String ticketTimeType;

    private Date ticketCreateTime;

    private Date activeTime;

    private Date ticketValidityStart;

    private Date ticketValidityEnd;

    private Long doctorId;

    private Long patientId;

    private Long registeredResourceId;

    private Long orderId;

    private Integer status;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ticketId=").append(ticketId);
        sb.append(", ticketNumber=").append(ticketNumber);
        sb.append(", ticketType=").append(ticketType);
        sb.append(", ticketTimeType=").append(ticketTimeType);
        sb.append(", ticketCreateTime=").append(ticketCreateTime);
        sb.append(", activeTime=").append(activeTime);
        sb.append(", ticketValidityStart=").append(ticketValidityStart);
        sb.append(", ticketValidityEnd=").append(ticketValidityEnd);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", patientId=").append(patientId);
        sb.append(", registeredResourceId=").append(registeredResourceId);
        sb.append(", orderId=").append(orderId);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}