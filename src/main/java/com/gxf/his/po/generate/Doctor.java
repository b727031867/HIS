package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Doctor implements Serializable {
    private Long doctorId;

    private String employeeId;

    private String doctorName;

    private String doctorProfessionalTitle;

    private String doctorIntroduction;

    private String departmentCode;

    private Long schedulingId;

    private Long userId;

    private Integer ticketDayNum;

    private BigDecimal ticketPrice;

    private Integer ticketCurrentNum;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", doctorId=").append(doctorId);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", doctorName=").append(doctorName);
        sb.append(", doctorProfessionalTitle=").append(doctorProfessionalTitle);
        sb.append(", doctorIntroduction=").append(doctorIntroduction);
        sb.append(", departmentCode=").append(departmentCode);
        sb.append(", schedulingId=").append(schedulingId);
        sb.append(", userId=").append(userId);
        sb.append(", ticketDayNum=").append(ticketDayNum);
        sb.append(", ticketPrice=").append(ticketPrice);
        sb.append(", ticketCurrentNum=").append(ticketCurrentNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}