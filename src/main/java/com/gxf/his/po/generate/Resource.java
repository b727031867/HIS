package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Resource implements Serializable {
    private Long resourceId;

    private String resourceName;

    private BigDecimal size;

    private Byte status;

    private String path;

    private String introduction;

    private Date uploadTime;

    private Long uploadBy;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", resourceId=").append(resourceId);
        sb.append(", resourceName=").append(resourceName);
        sb.append(", size=").append(size);
        sb.append(", status=").append(status);
        sb.append(", path=").append(path);
        sb.append(", introduction=").append(introduction);
        sb.append(", uploadTime=").append(uploadTime);
        sb.append(", uploadBy=").append(uploadBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}