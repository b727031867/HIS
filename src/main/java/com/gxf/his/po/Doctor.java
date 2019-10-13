package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class Doctor implements Serializable {
    private Integer doctorId;

    private String employeeId;

    private String doctorName;

    private String doctorProfessionalTitle;

    private String doctorIntroduction;

    private String departmentCode;

    private Integer schedulingId;

    private Integer userId;

    private Integer ticketDayNum;

    private static final long serialVersionUID = 1L;

}