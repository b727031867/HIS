package com.gxf.his.uitls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author GXF
 * @version 1.0
 * 通用方法
 * @date 2020/2/9 01:18
 */
public class MyUtil {
    private static final Logger logger = LoggerFactory.getLogger(MyUtil.class);

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
            logger.error("类型转换出现异常！",e);
            throw new RuntimeException();
        }
    }
}
