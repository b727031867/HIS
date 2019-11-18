package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GXF
 * @date 2019-11-18 19:04:23
 **/
@Data
public class MedicalRecord implements Serializable {
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


}