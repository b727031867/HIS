package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DrugException;
import com.gxf.his.exception.DrugExpiredInfoException;
import com.gxf.his.exception.DrugToxicologyException;
import com.gxf.his.mapper.dao.*;
import com.gxf.his.po.generate.DrugDistribution;
import com.gxf.his.po.generate.DrugStore;
import com.gxf.his.po.generate.DrugToxicology;
import com.gxf.his.po.generate.StoreBatches;
import com.gxf.his.po.vo.DrugExpiredInfoVo;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.po.vo.StoreBatchesVo;
import com.gxf.his.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 15:27
 */
@Service
@Slf4j
public class DrugServiceImpl implements DrugService {
    @Resource
    private IDrugMapper iDrugMapper;
    @Resource
    private IDrugDistributionMapper iDrugDistributionMapper;
    @Resource
    private IDrugStoreMapper iDrugstoreMapper;
    @Resource
    private IDrugToxicologyMapper iDrugToxicologyMapper;
    @Resource
    private IStoreBatchesMapper iStoreBatchesMapper;
    @Resource
    private IDrugExpiredInfoMapper iDrugExpiredInfoMapper;

    @Override
    public List<DrugVo> getDrugByDrugName(String drugName, Integer drugAlias) {
        try {
            if (drugAlias == 1) {
                return iDrugMapper.selectDrugByDrugName(drugName);
            } else if (drugAlias == 2) {
                return iDrugMapper.selectDrugByDrugAlias(drugName);
            } else {
                log.warn("未知的药品搜索类型");
                throw new DrugException(ServerResponseEnum.DRUG_LIST_FAIL);
            }
        } catch (Exception e) {
            log.error("药品模糊查询失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_LIST_FAIL);
        }
    }

    @Override
    public List<DrugVo> selectDrugVosByAttribute(boolean isAccurate, String attribute, String value) {
        try {
            List<DrugVo> drugVos;
            if (isAccurate) {
                drugVos = iDrugMapper.selectDrugsByAccurateAttribute(attribute, value);
            } else {
                drugVos = iDrugMapper.selectDrugByAttribute(attribute, value);
            }
            return drugVos;
        } catch (Exception e) {
            log.error("根据药品属性查询药品失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_LIST_FAIL);
        }
    }

    @Override
    public List<DrugVo> getDrugVoByDrugId(Long drugId) {
        try {
            return iDrugMapper.selectByPrimaryKeyRelated(drugId);
        } catch (Exception e) {
            log.error("根据药品Id查询药品失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_LIST_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDrugDistributionsAndDecreaseStock(List<DrugDistribution> drugDistributions) {
        for (DrugDistribution drugDistribution : drugDistributions) {
            if (drugDistribution.getDrugId() != null && drugDistribution.getPrescriptionId() != null && drugDistribution.getNumber() != null) {
                //本次总共需要减少的药品数量
                int number = 0;
                DrugStore drugStore = iDrugstoreMapper.selectDrugStoreByDrugId(drugDistribution.getDrugId());
                //如果是拆卖
                if (drugDistribution.getIsInBulk() == 1) {
                    if(drugStore.getPackagingSpecifications() <= drugDistribution.getNumber()){
                        log.warn("药品拆卖分发数量不能大于等于包装数量！");
                        throw new DrugException(ServerResponseEnum.DRUG_STORE_TOO_MANY);
                    }
                    //并且是新拆，则库存减1
                    if (drugDistribution.getIsNewOne() == 1) {
                        if (drugStore.getInventoryNum() < 1) {
                            log.warn("药品库存不足1");
                            throw new DrugException(ServerResponseEnum.DRUG_STORE_NOT_ENOUGH);
                        }
                        drugStore.setInventoryNum(drugStore.getInventoryNum() - 1);
                        iDrugstoreMapper.updateByPrimaryKey(drugStore);
                        number = 1;
                    }
                } else {
                    //不是拆卖直接减少库存
                    if (drugStore.getInventoryNum() - drugDistribution.getNumber() < 0) {
                        log.warn("药品库存不足2");
                        throw new DrugException(ServerResponseEnum.DRUG_STORE_NOT_ENOUGH);
                    }
                    drugStore.setInventoryNum(drugStore.getInventoryNum() - drugDistribution.getNumber());
                    number = drugDistribution.getNumber();
                    iDrugstoreMapper.updateByPrimaryKey(drugStore);
                }
                //减少对应数量的批次药品项数量，优先减少快要过期的采购批次项的数量
                doBatchesReduce(number, drugDistribution.getDrugId());
                //插入信息
                iDrugDistributionMapper.insert(drugDistribution);
            } else {
                log.warn("不合法的DrugDistribution" + drugDistribution.toString());
                throw new DrugException(ServerResponseEnum.DRUG_DISTRIBUTION_SAVE_FAIL);
            }
        }
    }

    /**
     * 分批次减少药品库存
     *
     * @param number 减少的数量
     * @param drugId 药品ID
     */
    private void doBatchesReduce(int number, Long drugId) {
        for (; number > 0; ) {
            //获取某药品剩余保质期最短的采购项
            StoreBatchesVo storeBatchesVo = iStoreBatchesMapper.getDrugsThatWillExpireSoon(drugId);
            if (storeBatchesVo == null) {
                //只消耗初始化的库存，即没有入库记录的药品数量，所以不用查找并且减少采购项的数量
                break;
            }
            //如果这一批次足够减少,则直接减少
            if (storeBatchesVo.getBatchesTotal() - number >= 0) {
                storeBatchesVo.setBatchesTotal(storeBatchesVo.getBatchesTotal() - number);
                iStoreBatchesMapper.updateByPrimaryKey(storeBatchesVo);
                break;
            } else {
                //不够减少，则减少到0，并且进行下一批的减少
                number = number - storeBatchesVo.getBatchesTotal();
                storeBatchesVo.setBatchesTotal(0);
                iStoreBatchesMapper.updateByPrimaryKey(storeBatchesVo);
            }
        }
    }

    @Override
    public List<DrugToxicology> loadDrugToxicologyList() {
        try {
            return iDrugToxicologyMapper.selectAll();
        } catch (Exception e) {
            log.error("查询所有药品毒理信息失败", e);
            throw new DrugToxicologyException(ServerResponseEnum.DRUG_LIST_FAIL);
        }
    }

    @Override
    public void batchDeleteDrug(List<DrugVo> drugVos) {
        try {
            iDrugMapper.batchDrugDelete(drugVos);
        } catch (Exception e) {
            log.error("药品批量删除失败");
            throw new DrugException(ServerResponseEnum.DRUG_DELETE_FAIL);
        }

    }

    @Override
    public void deleteDrugByDrugId(Long drugId) {
        try {
            iDrugMapper.deleteByPrimaryKey(drugId);
        } catch (Exception e) {
            log.error("药品删除失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_DELETE_FAIL);
        }
    }

    @Override
    public void drugUpdate(DrugVo drugVo) {
        try {
            iDrugMapper.updateByPrimaryKey(drugVo);
        } catch (Exception e) {
            log.error("药品更新失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_UPDATE_FAIL);
        }
    }

    @Override
    public void addDrug(DrugVo drugVo) {
        try {
            iDrugMapper.insert(drugVo);
        } catch (Exception e) {
            log.error("药品添加失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_SAVE_FAIL);
        }
    }

    @Override
    public List<DrugVo> getDrugByCode(String code) {
        try {
            return iDrugMapper.selectDrugByCode(code);
        } catch (Exception e) {
            log.error("查询药品失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_LIST_FAIL);
        }
    }

    @Override
    public List<DrugVo> loadUnLinkDrugList() {
        return iDrugMapper.selectAllUnLinkDrug();
    }

    @Override
    public void markExpiredDrugs() {
        List<StoreBatchesVo> storeBatchesVos = iStoreBatchesMapper.getExpiredStoreBatchesDrugs();
        for (StoreBatchesVo storeBatchesVo : storeBatchesVos) {
            storeBatchesVo.setIsNoticed(1);
            iStoreBatchesMapper.updateByPrimaryKey(storeBatchesVo);
        }

    }

    @Override
    public List<StoreBatchesVo> getExpiringMedicines() {
        return iStoreBatchesMapper.getExpiringMedicines();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDrugExpiredInfo(DrugExpiredInfoVo drugExpiredInfoVo) {
        StoreBatches storeBatches = iStoreBatchesMapper.selectByPrimaryKey(drugExpiredInfoVo.getInventoryRefBatchesId());
        drugExpiredInfoVo.setInventoryRefBatchesId(drugExpiredInfoVo.getInventoryRefBatchesId());
        //过期数量应该等于那批次剩余的药品数量
        drugExpiredInfoVo.setNumber(storeBatches.getBatchesTotal());
        iDrugExpiredInfoMapper.insertAndInjectId(drugExpiredInfoVo);
        //减少总库存
        DrugStore drugStore = iDrugstoreMapper.selectByPrimaryKey(storeBatches.getInventoryId());
        if(drugStore.getInventoryNum() < storeBatches.getBatchesTotal()){
            //出现总库存小于过期批次数量的情况，数据异常
            log.warn("库存数据异常！总库存数量小于该批次过期的");
            throw new DrugExpiredInfoException(ServerResponseEnum.DRUG_EXPIRED_INFO_SAVE_FAIL);
        }
        drugStore.setInventoryNum(drugStore.getInventoryNum() - storeBatches.getBatchesTotal());
        //设置关联的药品采购项
        storeBatches.setDrugExpiredId(drugExpiredInfoVo.getDrugExpiredId());
        //那一批次剩余的药品应该全部过期
        storeBatches.setBatchesTotal(0);
        iStoreBatchesMapper.updateByPrimaryKey(storeBatches);
    }

    @Override
    public List<DrugVo> getAllDrug() {
        try {
            return iDrugMapper.selectAllDrug();
        } catch (Exception e) {
            log.error("查询所有药品失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_LIST_FAIL);
        }
    }
}
