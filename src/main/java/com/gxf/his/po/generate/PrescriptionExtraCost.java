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
    private Long prescriptionExtrachargesId;

    private Long prescriptionId;

    private Long operaterId;

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
        sb.append(", prescriptionExtrachargesId=").append(prescriptionExtrachargesId);
        sb.append(", prescriptionId=").append(prescriptionId);
        sb.append(", operaterId=").append(operaterId);
        sb.append(", amount=").append(amount);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}