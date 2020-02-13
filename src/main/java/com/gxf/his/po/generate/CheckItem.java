package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CheckItem implements Serializable {
    private Long checkItemId;

    private String name;

    private String type;

    private BigDecimal price;

    private BigDecimal cost;

    private Date createTime;

    private Date updateTime;

    private Long operaterId;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", checkItemId=").append(checkItemId);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", price=").append(price);
        sb.append(", cost=").append(cost);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", operaterId=").append(operaterId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}