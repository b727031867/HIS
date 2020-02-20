package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MedicalTemplate implements Serializable {
    private Long medicalTemplateId;

    private Integer type;

    private Integer status;

    private Long uploadId;

    private Integer uploaderType;

    private Date updateDatetime;

    private Date createDatetime;

    private String content;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", medicalTemplateId=").append(medicalTemplateId);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", uploadId=").append(uploadId);
        sb.append(", uploaderType=").append(uploaderType);
        sb.append(", updateDatetime=").append(updateDatetime);
        sb.append(", createDatetime=").append(createDatetime);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}