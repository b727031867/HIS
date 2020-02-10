package com.gxf.his.po.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gxf.his.po.generate.Cashier;
import com.gxf.his.po.generate.Department;
import com.gxf.his.po.generate.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 龚秀峰
 * @date 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CashierVo extends Cashier implements Serializable {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date entryDate;
    private Department department;
    private User user;

    /**
     * 是否精确查询
     */
    private Boolean isAccurate;

    /**
     * 查询的属性
     */
    private String searchAttribute;

    /**
     *
     */
    private String value;
}
