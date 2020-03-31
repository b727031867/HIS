package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DrugCheckInfo implements Serializable {
    private Long checkInfoId;

    private Integer lossAmount;

    private Integer stockQuantity;

    private String remark;

    private Long checkerId;

    private Date createTime;

    private Long drugId;

    private Long inventoryBatchesId;

    private Long inventoryRefBatchesId;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", checkInfoId=").append(checkInfoId);
        sb.append(", lossAmount=").append(lossAmount);
        sb.append(", stockQuantity=").append(stockQuantity);
        sb.append(", remark=").append(remark);
        sb.append(", checkerId=").append(checkerId);
        sb.append(", createTime=").append(createTime);
        sb.append(", drugId=").append(drugId);
        sb.append(", inventoryBatchesId=").append(inventoryBatchesId);
        sb.append(", inventoryRefBatchesId=").append(inventoryRefBatchesId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}