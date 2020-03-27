package com.gxf.his.po.vo;

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
}
