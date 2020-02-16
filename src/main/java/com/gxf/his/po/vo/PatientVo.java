package com.gxf.his.po.vo;

import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.PatientFile;
import com.gxf.his.po.generate.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 患者业务类
 * @author 龚秀峰
 * @date 2019-10-13
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PatientVo extends Patient implements Serializable {

    private User user;

    private PatientFile patientFile;

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
