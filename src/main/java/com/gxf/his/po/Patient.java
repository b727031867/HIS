package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class Patient implements Serializable {
    private Long patientId;

    private String patientName;

    private String patientCard;

    private Byte patientSex;

    private String patientMedicareCard;

    private Integer patientAge;

    private Byte patientIsMarriage;

    private String patientPhone;

    private Long userId;

    private static final long serialVersionUID = 1L;

}