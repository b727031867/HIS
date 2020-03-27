package com.gxf.his.service;

import com.gxf.his.po.vo.DrugStoreBatchesVo;
import com.gxf.his.po.vo.DrugVo;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/28 00:30
 * 药品库存批次信息的接口
 */
public interface DrugStoreBatchesService {
    void addDrugStoreBatches(DrugStoreBatchesVo drugStoreBathesVo);

    void drugStoreBathesUpdate(DrugStoreBatchesVo drugStoreBathesVo);

    void deleteDrugStoreBatchesById(Long inventoryBatchesId);

    void batchDeleteDrugStoreBatches(List<DrugStoreBatchesVo> drugStoreBathesVos);

    List<DrugStoreBatchesVo> getAllDrugStoreBatches();

    List<DrugVo> selectDrugStoreBatchesVosByAttribute(Boolean isAccurate, String attribute, String value);
}
