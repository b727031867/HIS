package com.gxf.his.po.vo;

import com.gxf.his.po.generate.Drug;
import com.gxf.his.po.generate.DrugStore;
import com.gxf.his.po.generate.DrugToxicology;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 12:24
 * 药品的业务类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DrugVo extends Drug {

    /**
     * 药品关联药品药品库存
     */
    private DrugStore drugStore;

    /**
     * 关联的药品毒理信息
     */
    private DrugToxicology drugToxicology;

    /**
     * 购买这药品的数量
     * 初始值为0
     */
    private Integer number = 1;
}
