package com.gxf.his.po.vo;

import com.gxf.his.po.generate.DrugCheckInfo;
import com.gxf.his.po.generate.DrugStore;
import com.gxf.his.po.generate.DrugStoreBatches;
import com.gxf.his.po.generate.StoreBatches;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/27 15:32
 * 药品与采购项关联实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreBatchesVo extends StoreBatches {

    /**
     * 关联的药品信息
     */
    private DrugVo drugVo;

    /**
     * 采购项关联的药品清点信息
     */
    private DrugCheckInfo drugCheckInfo;

    /**
     * 一对一关联药品库存信息
     */
    private DrugStore drugStore;

    /**
     * 一对一关联药品采购批次（单）
     */
    private DrugStoreBatches inventoryBatches;

}
