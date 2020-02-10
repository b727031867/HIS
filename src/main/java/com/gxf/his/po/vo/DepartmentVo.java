package com.gxf.his.po.vo;

import com.google.gson.annotations.SerializedName;
import com.gxf.his.po.generate.Department;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 科室业务类
 * @author 龚秀峰
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentVo extends Department implements Serializable {

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
