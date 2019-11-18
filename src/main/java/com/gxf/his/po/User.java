package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class User implements Serializable {
    private Long userId;

    private String userName;

    private String userSalt;

    private String userPassword;

    private Byte userStatus;

    private Date userCreateDate;

    private static final long serialVersionUID = 1L;

}