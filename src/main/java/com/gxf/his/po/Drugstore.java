package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class Drugstore implements Serializable {
    private Long drugStoreId;

    private String drugStoreName;

    private Integer drugStoreNumber;

    private static final long serialVersionUID = 1L;

}