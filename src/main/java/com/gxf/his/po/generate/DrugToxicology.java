package com.gxf.his.po.generate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DrugToxicology implements Serializable {
    private Long toxicologyId;

    private String toxicologyName;

    private String toxicologyCode;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", toxicologyId=").append(toxicologyId);
        sb.append(", toxicologyName=").append(toxicologyName);
        sb.append(", toxicologyCode=").append(toxicologyCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}