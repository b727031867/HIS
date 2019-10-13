package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class Role implements Serializable {
    private Integer roleId;

    private String roleName;

    private String createByName;

    private Integer createById;

    private Date createTime;

    private String updateByName;

    private Integer updateById;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

}