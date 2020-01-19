package com.gxf.his.vo;

import com.gxf.his.po.Department;
import com.gxf.his.po.User;
import lombok.Data;

import java.util.Date;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
@Data
public class CashierUserVo {
    private Long cashierId;
    private String name;
    private String phone;
    private Date entryDate;
    private Department department;
    private User user;
    private Boolean isAccurate;
    private String searchAttribute;
    private String value;
}
