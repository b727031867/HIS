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
}
