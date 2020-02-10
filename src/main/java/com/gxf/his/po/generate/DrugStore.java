package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DrugStore implements Serializable {
    private Long inventoryId;

    private Long drugId;

    private Integer inventoryNum;

    private String inventoryUnit;

    private Integer packagingSpecifications;

    private String smallestUnit;

    private BigDecimal minPrice;

    private BigDecimal prescriptionPrice;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", inventoryId=").append(inventoryId);
        sb.append(", drugId=").append(drugId);
        sb.append(", inventoryNum=").append(inventoryNum);
        sb.append(", inventoryUnit=").append(inventoryUnit);
        sb.append(", packagingSpecifications=").append(packagingSpecifications);
        sb.append(", smallestUnit=").append(smallestUnit);
        sb.append(", minPrice=").append(minPrice);
        sb.append(", prescriptionPrice=").append(prescriptionPrice);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}