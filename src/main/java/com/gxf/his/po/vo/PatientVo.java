package com.gxf.his.po.vo;

import com.gxf.his.po.generate.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 患者业务类
 *
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PatientVo extends Patient implements Serializable {

    private User user;

    private PatientFile patientFile;

    /**
     * 关联的处方单列表
     */
    private List<Prescription> prescriptions;

    /**
     * 关联的检查单列表
     */
    private List<CheckItemInfo> checkItemInfos;

    /**
     * 关联的电子病历列表
     */
    private List<PatientMedicalRecord> patientMedicalRecords;

    /**
     * 父类对象
     */
    private Patient patient;

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
