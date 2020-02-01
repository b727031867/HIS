package com.gxf.his.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * 患者客户端挂号数据映射类
 * @Author GXF
 */
@Data
public class DepartmentVo {

    /**
     * 导航名称
     */
    @SerializedName("info")
    private String text;

    /**
     * 导航包含的子项
     */
    @SerializedName("children")
    private List<ChildrenBean> children;

    @SerializedName("className")
    private String className = "my-class";

    @Data
    public static class ChildrenBean {
        @SerializedName("text")
        private String text;
        @SerializedName("id")
        private String id;
    }
}
