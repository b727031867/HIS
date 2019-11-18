package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class Department implements Serializable {
    private Long departmentId;

    private Integer departmentCode;

    private String departmentName;

    private String departmentIntroduction;

    private Long departmentParentId;

    private static final long serialVersionUID = 1L;

}