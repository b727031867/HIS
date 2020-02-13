package com.gxf.his.po.generate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DoctorScheduling implements Serializable {
    private Long schedulingId;

    private String schedulingType;

    private String schedulingTime;

    private String schedulingRoom;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", schedulingId=").append(schedulingId);
        sb.append(", schedulingType=").append(schedulingType);
        sb.append(", schedulingTime=").append(schedulingTime);
        sb.append(", schedulingRoom=").append(schedulingRoom);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}