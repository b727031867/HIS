package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CheckItemInfo implements Serializable {
    private Long checkItemInfoId;

    private Long checkItemId;

    private Long patientId;

    private Long doctorId;

    private Long ticketId;

    private Long operateId;

    private Date createTime;

    private String content;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", checkItemInfoId=").append(checkItemInfoId);
        sb.append(", checkItemId=").append(checkItemId);
        sb.append(", patientId=").append(patientId);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", ticketId=").append(ticketId);
        sb.append(", operateId=").append(operateId);
        sb.append(", createTime=").append(createTime);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}