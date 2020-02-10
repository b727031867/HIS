package com.gxf.his.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/10 14:57
 * 公共Controller
 */
@Controller
@Slf4j
public class BaseController {

    /**
     * 查询参数的通用设置方法
     * @param attribute 查询的属性名，与POJO属性名相同 比如查询患者名称 patientName
     * @param isAccurate 是否精确查询
     * @param value 查询的值
     * @return 参数是否正常
     */
    public Boolean searchParamCheck(String attribute,Boolean isAccurate,String value){
        return attribute != null && attribute.trim().length() != 0 && value != null && value.trim().length() != 0 && isAccurate != null;
    }
}
