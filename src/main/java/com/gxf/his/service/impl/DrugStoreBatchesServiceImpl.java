package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DrugStoreBatchesException;
import com.gxf.his.mapper.dao.IDrugStoreBatchesMapper;
import com.gxf.his.mapper.generate.StoreBatchesMapper;
import com.gxf.his.po.vo.DrugStoreBatchesVo;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.po.vo.StoreBatchesVo;
import com.gxf.his.service.DrugStoreBatchesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/28 00:38
 * 药品库存批次信息接口实现类
 */
@Service
@Slf4j
public class DrugStoreBatchesServiceImpl implements DrugStoreBatchesService {
    @Resource
    private StoreBatchesMapper storeBatchesMapper;
    @Resource
    private IDrugStoreBatchesMapper iDrugStoreBatchesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDrugStoreBatches(DrugStoreBatchesVo drugStoreBathesVo) {
        //插入采购计划
        drugStoreBathesVo.setCreateDate(new Date());
        drugStoreBathesVo.setStatus("0");
        iDrugStoreBatchesMapper.insertAndInjectId(drugStoreBathesVo);
        //插入采购项
        List<StoreBatchesVo> storeBatchesList = drugStoreBathesVo.getStoreBatchesList();
        if(storeBatchesList == null || storeBatchesList.size() <1){
            throw new DrugStoreBatchesException(ServerResponseEnum.DRUG_STORE_BATCHES_ITEM_NONE_ERROR);
        }
        //添加批次库存项
        for (StoreBatchesVo storeBatchesVo : storeBatchesList) {
            storeBatchesVo.setDrugId(storeBatchesVo.getDrugVo().getDrugId());
            storeBatchesVo.setInventoryBatchesId(drugStoreBathesVo.getInventoryBatchesId());
            storeBatchesVo.setInventoryId(storeBatchesVo.getDrugVo().getDrugStore().getInventoryId());
            storeBatchesVo.setIsnoticed(0);
            storeBatchesMapper.insert(storeBatchesVo);
        }
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
