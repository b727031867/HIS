package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Role implements Serializable {
    private Long roleId;

    private String roleName;

    private String createByName;

    private Long createById;

    private Date createTime;

    private String updateByName;

    private Long updateById;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", createByName=").append(createByName);
        sb.append(", createById=").append(createById);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateByName=").append(updateByName);
        sb.append(", updateById=").append(updateById);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}