package com.gxf.his.po.vo;

import com.gxf.his.po.generate.*;
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
    private PrescriptionInfo prescriptionInfo;
    private PrescriptionExtraCost prescriptionExtraCost;
    private DoctorTicketResource  doctorTicketResource;
    private CheckItem checkItem;
}
