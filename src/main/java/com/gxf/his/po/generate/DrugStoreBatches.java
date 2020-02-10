package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DrugStoreBatches implements Serializable {
    private Long inventoryBatchesId;

    private Long supplierId;

    private Long inventoryBatchesNumber;

    private Long purchasingAgentId;

    private BigDecimal totalMoney;

    private Date createDate;

    private String status;

    private Long verifierId;

    private Date verifierDate;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", inventoryBatchesId=").append(inventoryBatchesId);
        sb.append(", supplierId=").append(supplierId);
        sb.append(", inventoryBatchesNumber=").append(inventoryBatchesNumber);
        sb.append(", purchasingAgentId=").append(purchasingAgentId);
        sb.append(", totalMoney=").append(totalMoney);
        sb.append(", createDate=").append(createDate);
        sb.append(", status=").append(status);
        sb.append(", verifierId=").append(verifierId);
        sb.append(", verifierDate=").append(verifierDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}