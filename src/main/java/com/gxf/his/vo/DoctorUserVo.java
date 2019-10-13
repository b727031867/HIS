package com.gxf.his.vo;

import lombok.Data;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Data
public class DoctorUserVo {
    private Integer doctorId;

    private String employeeId;

    private String doctorName;

    private String doctorProfessionalTitle;

    private String doctorIntroduction;

    private String departmentCode;

    private Integer schedulingId;

    private Integer userId;

    private Integer ticketDayNum;

    private String userName;

    private String userSalt;

    private String userPassword;

    private Byte userStatus;
}
