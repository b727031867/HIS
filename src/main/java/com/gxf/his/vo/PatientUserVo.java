package com.gxf.his.vo;

import lombok.Data;

/**
 * 患者注册业务的数据类
 *
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Data
public class PatientUserVo {
    private Long patientId;

    private String patientName;

    private String patientCard;

    private Byte patientSex;

    private String patientMedicareCard;

    private Integer patientAge;

    private Byte patientIsMarriage;

    private String patientPhone;

    private Long userId;

    private String userName;

    private String userSalt;

    private String userPassword;

    private Byte userStatus;


}
