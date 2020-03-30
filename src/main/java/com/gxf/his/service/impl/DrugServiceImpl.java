package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DrugException;
import com.gxf.his.exception.DrugToxicologyException;
import com.gxf.his.mapper.dao.IDrugDistributionMapper;
import com.gxf.his.mapper.dao.IDrugMapper;
import com.gxf.his.mapper.dao.IDrugStoreMapper;
import com.gxf.his.mapper.dao.IDrugToxicologyMapper;
import com.gxf.his.po.generate.DrugDistribution;
import com.gxf.his.po.generate.DrugStore;
import com.gxf.his.po.generate.DrugToxicology;
import com.gxf.his.po.vo.DrugVo;
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
                //如果是拆卖
                if (drugDistribution.getIsInBulk() == 1) {
                    //并且是新拆，则库存减1
                    if (drugDistribution.getIsNewOne() == 1) {
                        DrugStore drugStore = iDrugstoreMapper.selectDrugStoreByDrugId(drugDistribution.getDrugId());
                        drugStore.setInventoryNum(drugStore.getInventoryNum() - 1);
                        iDrugstoreMapper.updateByPrimaryKey(drugStore);
                    }
                } else {
                    //不是拆卖直接减少库存
                    DrugStore drugStore = iDrugstoreMapper.selectDrugStoreByDrugId(drugDistribution.getDrugId());
                    drugStore.setInventoryNum(drugStore.getInventoryNum() - drugDistribution.getNumber());
                    iDrugstoreMapper.updateByPrimaryKey(drugStore);
                }
                //插入信息
                iDrugDistributionMapper.insert(drugDistribution);
            } else {
                log.warn("不合法的DrugDistribution" + drugDistribution.toString());
                throw new DrugException(ServerResponseEnum.DRUG_DISTRIBUTION_SAVE_FAIL);
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
    public List<DrugVo> getAllDrug() {
        try {
            return iDrugMapper.selectAllDrug();
        } catch (Exception e) {
            log.error("查询所有药品失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_LIST_FAIL);
        }
    }
}
