package com.gxf.his.po.vo;

import com.gxf.his.po.generate.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorVo extends Doctor implements Serializable {

    private Department department;

    private Scheduling scheduling;

    private User user;

    private List<TicketResource> ticketResources;

    private List<Department> departments;
}
