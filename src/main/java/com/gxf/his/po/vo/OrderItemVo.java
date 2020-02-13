package com.gxf.his.po.vo;

import com.gxf.his.po.generate.CheckItem;
import com.gxf.his.po.generate.DoctorTicketResource;
import com.gxf.his.po.generate.OrderItem;
import com.gxf.his.po.generate.Prescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/14 03:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemVo extends OrderItem {
    private Prescription prescription;
    private DoctorTicketResource  doctorTicketResource;
    private CheckItem checkItem;
}
