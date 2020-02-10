package com.gxf.his.po.generate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Department implements Serializable {
    private Long departmentId;

    private String departmentCode;

    private String departmentName;

    private String departmentIntroduction;

    private Long departmentParentId;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", departmentId=").append(departmentId);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", departmentIntroduction=").append(departmentIntroduction);
        sb.append(", departmentParentId=").append(departmentParentId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}