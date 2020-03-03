package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PrescriptionExtraCost implements Serializable {
    private Long prescriptionExtraCostId;

    private Long prescriptionId;

    private Long operateId;

    private BigDecimal amount;

    private String remark;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", prescriptionExtraCostId=").append(prescriptionExtraCostId);
        sb.append(", prescriptionId=").append(prescriptionId);
        sb.append(", operateId=").append(operateId);
        sb.append(", amount=").append(amount);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}