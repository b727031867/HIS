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

    private String supplierName;

    private String phone;

    private String supplierContactUser;

    private String contactPersonName;

    private String inventoryBatchesNumber;

    private Long purchasingAgentId;

    private BigDecimal totalMoney;

    private Date createDate;

    private String status;

    private Long verifierId;

    private Date verifierDate;

    private String remark;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", inventoryBatchesId=").append(inventoryBatchesId);
        sb.append(", supplierName=").append(supplierName);
        sb.append(", phone=").append(phone);
        sb.append(", supplierContactUser=").append(supplierContactUser);
        sb.append(", contactPersonName=").append(contactPersonName);
        sb.append(", inventoryBatchesNumber=").append(inventoryBatchesNumber);
        sb.append(", purchasingAgentId=").append(purchasingAgentId);
        sb.append(", totalMoney=").append(totalMoney);
        sb.append(", createDate=").append(createDate);
        sb.append(", status=").append(status);
        sb.append(", verifierId=").append(verifierId);
        sb.append(", verifierDate=").append(verifierDate);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}