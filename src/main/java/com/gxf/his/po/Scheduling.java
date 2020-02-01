package com.gxf.his.po;

import lombok.Data;

import java.io.Serializable;
/**
 * @author GXF
 * @date 2019-10-13 19:04:23
 **/
@Data
public class Scheduling implements Serializable {
    private Long schedulingId;

    private String schedulingType;

    private String schedulingTime;

    private String schedulingRoom;

    private static final long serialVersionUID = 1L;

}