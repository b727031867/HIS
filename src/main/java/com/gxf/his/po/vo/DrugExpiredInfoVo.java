package com.gxf.his.po.vo;

import com.gxf.his.po.generate.DrugExpiredInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/4/2 23:27
 * 药品过期处理信息业务员类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DrugExpiredInfoVo extends DrugExpiredInfo {
    /**
     * 关联的药品信息
     */
    private DrugVo drugVo;

    /**
     * 关联的采购项信息
     */
    private StoreBatchesVo inventoryRefBatches;

}
