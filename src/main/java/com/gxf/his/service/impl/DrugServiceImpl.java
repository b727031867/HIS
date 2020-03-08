package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.DrugException;
import com.gxf.his.mapper.dao.IDrugMapper;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.service.DrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
    public List<DrugVo> getAllDrug() {
        try {
            return iDrugMapper.selectAllDrug();
        } catch (Exception e) {
            log.error("查询所有药品失败", e);
            throw new DrugException(ServerResponseEnum.DRUG_LIST_FAIL);
        }
    }
}
