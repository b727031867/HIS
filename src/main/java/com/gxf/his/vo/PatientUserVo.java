package com.gxf.his.vo;

import com.gxf.his.po.User;
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

    private User user;

    /**
     * 查询对象的属性名称
     */
    private String searchAttribute;

    /**
     * 是否精确查询
     */
    private Boolean isAccurate;

    /**
     * 查询的值
     */
    private String value;
}
