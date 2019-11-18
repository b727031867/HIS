package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author GXF
 * @date 2019-11-18 19:04:23
 **/
@Data
public class Resource implements Serializable {
    private Long resourceId;

    private String resourceName;

    private BigDecimal size;

    private Byte status;

    private String path;

    private String introduction;

    private Date uploadTime;

    private Long uploadBy;

    private static final long serialVersionUID = 1L;

}