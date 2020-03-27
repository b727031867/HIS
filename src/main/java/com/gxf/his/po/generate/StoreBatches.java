package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StoreBatches implements Serializable {
    private Long inventoryRefBatchesId;

    private Long drugId;

    private Long inventoryId;

    private Long inventoryBatchesId;

    private BigDecimal tradePrice;

    private BigDecimal totalAmount;

    private Integer totalNumber;

    private Date productionDate;

    private Date expiredTime;

    private Integer isnoticed;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", inventoryRefBatchesId=").append(inventoryRefBatchesId);
        sb.append(", drugId=").append(drugId);
        sb.append(", inventoryId=").append(inventoryId);
        sb.append(", inventoryBatchesId=").append(inventoryBatchesId);
        sb.append(", tradePrice=").append(tradePrice);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", totalNumber=").append(totalNumber);
        sb.append(", productionDate=").append(productionDate);
        sb.append(", expiredTime=").append(expiredTime);
        sb.append(", isnoticed=").append(isnoticed);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}