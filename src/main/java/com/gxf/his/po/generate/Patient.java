package com.gxf.his.po.generate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Patient implements Serializable {
    private Long patientId;

    private Integer patientAge;

    private String patientName;

    private Byte patientIsMarriage;

    private String patientCard;

    private String patientPhone;

    private Byte patientSex;

    private String patientMedicareCard;

    private Long userId;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", patientId=").append(patientId);
        sb.append(", patientAge=").append(patientAge);
        sb.append(", patientName=").append(patientName);
        sb.append(", patientIsMarriage=").append(patientIsMarriage);
        sb.append(", patientCard=").append(patientCard);
        sb.append(", patientPhone=").append(patientPhone);
        sb.append(", patientSex=").append(patientSex);
        sb.append(", patientMedicareCard=").append(patientMedicareCard);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}