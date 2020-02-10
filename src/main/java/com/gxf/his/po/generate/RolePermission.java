package com.gxf.his.po.generate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RolePermission implements Serializable {
    private Long rolePermissionId;

    private Long roleId;

    private Long permissionId;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rolePermissionId=").append(rolePermissionId);
        sb.append(", roleId=").append(roleId);
        sb.append(", permissionId=").append(permissionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}