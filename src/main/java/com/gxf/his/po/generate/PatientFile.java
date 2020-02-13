package com.gxf.his.po.generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PatientFile implements Serializable {
    private Long patientFile;

    private Long patientId;

    private String emergencyContactName;

    private String emergencyPhone;

    private String emergencyRelation;

    private Integer rightEarHearing;

    private Integer leftEarHearing;

    private BigDecimal rightVision;

    private BigDecimal leftVision;

    private BigDecimal height;

    private BigDecimal weight;

    private String bloodType;

    private String personalInfo;

    private String familyInfo;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", patientFile=").append(patientFile);
        sb.append(", patientId=").append(patientId);
        sb.append(", emergencyContactName=").append(emergencyContactName);
        sb.append(", emergencyPhone=").append(emergencyPhone);
        sb.append(", emergencyRelation=").append(emergencyRelation);
        sb.append(", rightEarHearing=").append(rightEarHearing);
        sb.append(", leftEarHearing=").append(leftEarHearing);
        sb.append(", rightVision=").append(rightVision);
        sb.append(", leftVision=").append(leftVision);
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", bloodType=").append(bloodType);
        sb.append(", personalInfo=").append(personalInfo);
        sb.append(", familyInfo=").append(familyInfo);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}