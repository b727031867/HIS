package com.gxf.his.po.vo;

import com.gxf.his.po.generate.DrugStoreBatches;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/28 00:24
 * 药品库存表批次信息业务逻辑类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DrugStoreBatchesVo extends DrugStoreBatches {
    /**
     * 药品库存与批次信息的关联项
     */
    private List<StoreBatchesVo> storeBatchesList;

}
