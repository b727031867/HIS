package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DrugExpiredInfo implements Serializable {
    private Long drugExpiredId;

    private Long drugId;

    private Long inventoryRefBatchesId;

    private String processingMethod;

    private Integer number;

    private Long operateId;

    private Date createDate;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", drugExpiredId=").append(drugExpiredId);
        sb.append(", drugId=").append(drugId);
        sb.append(", inventoryRefBatchesId=").append(inventoryRefBatchesId);
        sb.append(", processingMethod=").append(processingMethod);
        sb.append(", number=").append(number);
        sb.append(", operateId=").append(operateId);
        sb.append(", createDate=").append(createDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}