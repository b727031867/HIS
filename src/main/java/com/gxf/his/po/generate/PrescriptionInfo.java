package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PrescriptionInfo implements Serializable {
    private Long prescriptionInfoId;

    private Long prescriptionId;

    private Long drugId;

    private Integer status;

    private BigDecimal itemTotalPrice;

    private String unit;

    private Integer num;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", prescriptionInfoId=").append(prescriptionInfoId);
        sb.append(", prescriptionId=").append(prescriptionId);
        sb.append(", drugId=").append(drugId);
        sb.append(", status=").append(status);
        sb.append(", itemTotalPrice=").append(itemTotalPrice);
        sb.append(", unit=").append(unit);
        sb.append(", num=").append(num);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}