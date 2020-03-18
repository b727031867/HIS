package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PrescriptionRefundInfo implements Serializable {
    private Long refundInfoId;

    private Date createTime;

    private Long prescriptionId;

    private Long operateId;

    private String reason;

    private Integer status;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", refundInfoId=").append(refundInfoId);
        sb.append(", createTime=").append(createTime);
        sb.append(", prescriptionId=").append(prescriptionId);
        sb.append(", operateId=").append(operateId);
        sb.append(", reason=").append(reason);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}