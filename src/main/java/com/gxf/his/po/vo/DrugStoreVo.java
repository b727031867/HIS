package com.gxf.his.po.vo;

import com.gxf.his.po.generate.Drug;
import com.gxf.his.po.generate.DrugStore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.annotations.Param;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/26 17:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DrugStoreVo extends DrugStore {
    /**
     * 一对一库存关联的药品信息
     */
    private Drug drug;

    /**
     * 查询的属性
     */
    private String attribute;
    /**
     * 查询的值
     */
    private String value;
    /**
     * 是否精确查询
     */
    private String isAccurate;
}
