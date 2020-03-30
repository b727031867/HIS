package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DrugStoreBatchesException;
import com.gxf.his.exception.DrugStoreException;
import com.gxf.his.mapper.dao.IDrugStoreBatchesMapper;
import com.gxf.his.mapper.generate.StoreBatchesMapper;
import com.gxf.his.po.generate.DrugStoreBatches;
import com.gxf.his.po.vo.DrugStoreBatchesVo;
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
        if (storeBatchesList == null || storeBatchesList.size() < 1) {
            throw new DrugStoreBatchesException(ServerResponseEnum.DRUG_STORE_BATCHES_ITEM_NONE_ERROR);
        }
        //添加批次库存项
        for (StoreBatchesVo storeBatchesVo : storeBatchesList) {
            storeBatchesVo.setDrugId(storeBatchesVo.getDrugVo().getDrugId());
            storeBatchesVo.setInventoryBatchesId(drugStoreBathesVo.getInventoryBatchesId());
            storeBatchesVo.setInventoryId(storeBatchesVo.getDrugVo().getDrugStore().getInventoryId());
            storeBatchesVo.setIsNoticed(0);
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
        return iDrugStoreBatchesMapper.selectAllBatches();
    }

    @Override
    public List<DrugStoreBatchesVo> selectDrugStoreBatchesVosByAttribute(Boolean isAccurate, String attribute, String value) {
        try {
            DrugStoreBatchesVo drugStoreBatchesVo = new DrugStoreBatchesVo();
            drugStoreBatchesVo.setIsAccurate(isAccurate.toString());
            drugStoreBatchesVo.setValue(value);
            drugStoreBatchesVo.setAttribute(attribute);
            return iDrugStoreBatchesMapper.selectDrugStoresByAttribute(drugStoreBatchesVo);
        } catch (Exception e) {
            log.error("根据药品库存属性查询库存信息失败", e);
            throw new DrugStoreException(ServerResponseEnum.DRUG_STORE_LIST_FAIL);
        }
    }

    @Override
    public void reviewDrugStoreBatches(Long inventoryBatchesId, Long verifierId) {
        DrugStoreBatches drugStoreBatches = iDrugStoreBatchesMapper.selectByPrimaryKey(inventoryBatchesId);
        drugStoreBatches.setVerifierId(verifierId);
        drugStoreBatches.setVerifierDate(new Date());
        drugStoreBatches.setStatus("1");
        iDrugStoreBatchesMapper.updateByPrimaryKey(drugStoreBatches);
    }

    @Override
    public void antiReviewDrugStoreBatches(Long inventoryBatchesId, Long verifierId) {
        DrugStoreBatches drugStoreBatches = iDrugStoreBatchesMapper.selectByPrimaryKey(inventoryBatchesId);
        drugStoreBatches.setVerifierId(verifierId);
        drugStoreBatches.setVerifierDate(new Date());
        drugStoreBatches.setStatus("2");
        iDrugStoreBatchesMapper.updateByPrimaryKey(drugStoreBatches);
    }

    @Override
    public void batchReviewDrugStoreBatches(List<DrugStoreBatchesVo> drugStoreBathesVos) {
        for (DrugStoreBatchesVo drugStoreBathesVo : drugStoreBathesVos) {
            DrugStoreBatches drugStoreBatches = iDrugStoreBatchesMapper.selectByPrimaryKey(drugStoreBathesVo.getInventoryBatchesId());
            drugStoreBatches.setVerifierId(drugStoreBathesVo.getVerifierId());
            drugStoreBatches.setVerifierDate(new Date());
            drugStoreBatches.setStatus("1");
            iDrugStoreBatchesMapper.updateByPrimaryKey(drugStoreBatches);
        }
    }

    @Override
    public void batchAntiReviewDrugStoreBatches(List<DrugStoreBatchesVo> drugStoreBathesVos) {
        for (DrugStoreBatchesVo drugStoreBathesVo : drugStoreBathesVos) {
            DrugStoreBatches drugStoreBatches = iDrugStoreBatchesMapper.selectByPrimaryKey(drugStoreBathesVo.getInventoryBatchesId());
            drugStoreBatches.setVerifierId(drugStoreBathesVo.getVerifierId());
            drugStoreBatches.setVerifierDate(new Date());
            drugStoreBatches.setStatus("2");
            iDrugStoreBatchesMapper.updateByPrimaryKey(drugStoreBatches);
        }
    }
}
