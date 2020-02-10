package com.gxf.his.po.generate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Drug implements Serializable {
    private Long drugId;

    private String code;

    private String drugAlias;

    private String typeName;

    private String drugName;

    private String drugForm;

    private Long toxicologyType;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", drugId=").append(drugId);
        sb.append(", code=").append(code);
        sb.append(", drugAlias=").append(drugAlias);
        sb.append(", typeName=").append(typeName);
        sb.append(", drugName=").append(drugName);
        sb.append(", drugForm=").append(drugForm);
        sb.append(", toxicologyType=").append(toxicologyType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}