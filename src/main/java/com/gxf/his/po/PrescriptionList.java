package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author GXF
 * @date 2019-11-18 19:04:23
 **/
@Data
public class PrescriptionList implements Serializable {
    private Long prescriptionListId;

    private BigDecimal totalSpend;

    private Long patientId;

    private Long doctorId;

    private Date createDatetime;

    private static final long serialVersionUID = 1L;

}