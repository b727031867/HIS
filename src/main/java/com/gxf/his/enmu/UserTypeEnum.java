package com.gxf.his.enmu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/4/2 17:25
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum {
    /**
     * 系统中存在得角色类型枚举
     */
    ADMINISTRATOR("系统管理员"),
    DRUG_MANAGER("药品管理员"),
    PATIENT("患者"),
    CASHIER("收银员"),
    DOCTOR("医生");

    String role;

    @Override
    public String toString() {
        return this.role;
    }

}
