package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DrugDistribution implements Serializable {
    private Long drugDistributionId;

    private Integer number;

    private Integer isInBulk;

    private Integer isNewOne;

    private Long drugId;

    private Long prescriptionId;

    private Long operateId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", drugDistributionId=").append(drugDistributionId);
        sb.append(", number=").append(number);
        sb.append(", isInBulk=").append(isInBulk);
        sb.append(", isNewOne=").append(isNewOne);
        sb.append(", drugId=").append(drugId);
        sb.append(", prescriptionId=").append(prescriptionId);
        sb.append(", operateId=").append(operateId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}