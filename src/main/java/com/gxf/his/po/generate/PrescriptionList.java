package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PrescriptionList implements Serializable {
    private Long prescriptionListId;

    private BigDecimal totalSpend;

    private Long patientId;

    private Long doctorId;

    private Date createDatetime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", prescriptionListId=").append(prescriptionListId);
        sb.append(", totalSpend=").append(totalSpend);
        sb.append(", patientId=").append(patientId);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", createDatetime=").append(createDatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}