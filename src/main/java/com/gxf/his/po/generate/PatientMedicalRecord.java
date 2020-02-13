package com.gxf.his.po.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PatientMedicalRecord implements Serializable {
    private Long medicalRecordId;

    private String narrator;

    private String chiefComplaint;

    private String pastHistory;

    private String currentMedicalHistory;

    private String auxiliaryInspection;

    private String diagnosis;

    private Long doctorId;

    private Long patientId;

    private Date createDatetime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", medicalRecordId=").append(medicalRecordId);
        sb.append(", narrator=").append(narrator);
        sb.append(", chiefComplaint=").append(chiefComplaint);
        sb.append(", pastHistory=").append(pastHistory);
        sb.append(", currentMedicalHistory=").append(currentMedicalHistory);
        sb.append(", auxiliaryInspection=").append(auxiliaryInspection);
        sb.append(", diagnosis=").append(diagnosis);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", patientId=").append(patientId);
        sb.append(", createDatetime=").append(createDatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}