package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DrugStoreException;
import com.gxf.his.mapper.dao.IDrugMapper;
import com.gxf.his.mapper.dao.IDrugStoreMapper;
import com.gxf.his.po.vo.DrugStoreVo;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.service.DrugStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/26 17:30
 */
@Service
@Slf4j
public class DrugStoreServiceImpl implements DrugStoreService {
    @Resource
    private IDrugStoreMapper iDrugStoreMapper;
    @Resource
    private IDrugMapper iDrugMapper;

    @Override
    public List<DrugStoreVo> getAllDrugStore() {
        return iDrugStoreMapper.selectAllDrugStoreAndDrug();
    }

    @Override
    public void batchDeleteDrugStore(List<DrugStoreVo> drugStoreVos) {
        try {
            iDrugStoreMapper.batchDrugStoreDelete(drugStoreVos);
        } catch (Exception e) {
            log.error("药品库存信息批量删除失败");
            throw new DrugStoreException(ServerResponseEnum.DRUG_STORE_DELETE_FAIL);
        }
    }

    @Override
    public void deleteDrugStoreByInventoryId(Long inventoryId) {
        try {
            iDrugStoreMapper.deleteByPrimaryKey(inventoryId);
        } catch (Exception e) {
            log.error("药品库存信息删除失败", e);
            throw new DrugStoreException(ServerResponseEnum.DRUG_STORE_DELETE_FAIL);
        }
    }

    @Override
    public List<DrugStoreVo> getDrugStoreVoByAttribute(String value, String attribute, Boolean isAccurate) {
        try {
            //如果根据国家药品编号查询
            String attr = "code";
            if(attr.equals(attribute)){
                List<DrugStoreVo> drugStores = new ArrayList<>(16);
                List<DrugVo> drugVos = iDrugMapper.selectDrugByCode(value);
                for (DrugVo drugVo : drugVos) {
                    DrugStoreVo drugStore = iDrugStoreMapper.selectDrugStoreByDrugId(drugVo.getDrugId());
                    drugStores.add(drugStore);
                }
                return drugStores;
            }else {
                DrugStoreVo drugStoreVo = new DrugStoreVo();
                drugStoreVo.setIsAccurate(isAccurate.toString());
                drugStoreVo.setValue(value);
                drugStoreVo.setAttribute(attribute);
                return iDrugStoreMapper.selectDrugStoresByAttribute(drugStoreVo);
            }
        } catch (Exception e) {
            log.error("根据药品库存属性查询库存信息失败", e);
            throw new DrugStoreException(ServerResponseEnum.DRUG_STORE_LIST_FAIL);
        }
    }

    @Override
    public void drugStoreUpdate(DrugStoreVo drugStoreVo) {
        try {
            iDrugStoreMapper.updateByPrimaryKey(drugStoreVo);
        } catch (Exception e) {
            log.error("药品库存信息更新失败", e);
            throw new DrugStoreException(ServerResponseEnum.DRUG_STORE_UPDATE_FAIL);
        }
    }

    @Override
    public List<DrugStoreVo> getDrugStoreByCode(String code) {
        List<DrugVo> drugVos = iDrugMapper.selectDrugByCode(code);
        List<DrugStoreVo> drugStoreVos = new ArrayList<>(2);
        DrugStoreVo drugStoreVo = new DrugStoreVo();
        drugStoreVo.setIsAccurate("true");
        drugStoreVo.setAttribute("drugId");
        for (DrugVo drugVo : drugVos) {
            drugStoreVo.setValue(String.valueOf(drugVo.getDrugId()));
            drugStoreVos = iDrugStoreMapper.selectDrugStoresByAttribute(drugStoreVo);
        }
        return drugStoreVos;
    }

    @Override
    public void addDrugStore(DrugStoreVo drugStoreVo) {
        try {
            iDrugStoreMapper.insertAndInjectID(drugStoreVo);
        } catch (Exception e) {
            log.error("药品库存信息添加失败", e);
            throw new DrugStoreException(ServerResponseEnum.DRUG_STORE_SAVE_FAIL);
        }
    }
}
