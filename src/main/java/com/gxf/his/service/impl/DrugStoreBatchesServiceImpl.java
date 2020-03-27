package com.gxf.his.service.impl;

import com.gxf.his.po.vo.DrugStoreBatchesVo;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.service.DrugStoreBatchesService;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/28 00:38
 * 药品库存批次信息接口实现类
 */
public class DrugStoreBatchesServiceImpl implements DrugStoreBatchesService {
    @Override
    public void addDrugStoreBatches(DrugStoreBatchesVo drugStoreBathesVo) {

    }

    @Override
    public void drugStoreBathesUpdate(DrugStoreBatchesVo drugStoreBathesVo) {

    }

    @Override
    public void deleteDrugStoreBatchesById(Long inventoryBatchesId) {

    }

    @Override
    public void batchDeleteDrugStoreBatches(List<DrugStoreBatchesVo> drugStoreBathesVos) {

    }

    @Override
    public List<DrugStoreBatchesVo> getAllDrugStoreBatches() {
        return null;
    }

    @Override
    public List<DrugVo> selectDrugStoreBatchesVosByAttribute(Boolean isAccurate, String attribute, String value) {
        return null;
    }
}
