package com.gxf.his.uitls;

import lombok.extern.slf4j.Slf4j;

/**
 * @author GXF
 * @version 1.0
 * 通用方法
 * @date 2020/2/9 01:18
 */
@Slf4j
public class MyUtil {

    /**
     * 通用类型转换，不报警
     *
     * @param obj 要转换的对象
     * @param <T> 包括的泛型集合
     * @return 转换后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        try {
            return (T) obj;
        } catch (Exception e) {
            log.error("类型转换出现异常！",e);
            throw new RuntimeException();
        }
    }

    /**
     * 将性别代号转化成性别类型
     * @param patientSexCode 性别编号 0 未知 1 男性 2 女性
     * @return 性别
     */
    public static String changeSex(Byte patientSexCode){
        if(patientSexCode == 0){
            return "未知";
        }else if(patientSexCode == 1){
            return "男性";
        }else if(patientSexCode == 2){
            return "女性";
        }else{
            log.error("不是合法的性别标识");
            return null;
        }
    }

    /**
     * 将婚姻状态代号转化成婚姻状态
     * @param marriageCode 婚姻状态编号 0 未知 1 已婚 2 未婚
     * @return 婚姻状态
     */
    public static String changeMarriage(Byte marriageCode){
        if(marriageCode == 0){
            return "未知";
        }else if(marriageCode == 1){
            return "已婚";
        }else if(marriageCode == 2){
            return "未婚";
        }else{
            log.error("不是合法的婚姻状态标识");
            return null;
        }
    }
}
