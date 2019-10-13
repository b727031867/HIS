package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class Permission implements Serializable {
    private Integer permissionId;

    private String permissionName;

    private String permission;

    private static final long serialVersionUID = 1L;

}