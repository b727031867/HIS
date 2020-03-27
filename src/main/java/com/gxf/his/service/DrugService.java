package com.gxf.his.service;

import com.gxf.his.po.generate.DrugDistribution;
import com.gxf.his.po.generate.DrugToxicology;
import com.gxf.his.po.vo.DrugVo;

import java.util.List;

/**
 * @author GXF
 * 药品模块的接口类
 */
public interface DrugService {

    /**
     * 根据药品名称模糊查询药品
     * @param drugName 药品名称
     * @param drugAlias 药品搜索的方式 1为使用学名搜索 2为使用别名搜索
     * @return 药品列表，关联了药品库存
     */
    List<DrugVo> getDrugByDrugName(String drugName,Integer drugAlias);

    /**
     * 分页查询所有药品
     * @return 药品列表，关联了药品库存、毒理信息
     */
    List<DrugVo> getAllDrug();

    /**
     * 根据属性查询关联药品信息
     * @param isAccurate 是否精确查询
     * @param attribute 属性名称
     * @param value 值
     * @return 药品关联信息列表
     */
    List<DrugVo> selectDrugVosByAttribute(boolean isAccurate, String attribute, String value);


    /**
     * 根据属性药品ID关联药品信息
     * @param drugId 药品ID
     * @return 药品关联信息
     */
    List<DrugVo> getDrugVoByDrugId(Long drugId);

    /**
     * 保存分发的药品信息，并且减少对应的库存
     * @param drugDistributions 药品分发信息的列表
     */
    void saveDrugDistributionsAndDecreaseStock(List<DrugDistribution> drugDistributions);

    /**
     * 查询所有的毒理信息
     * @return 毒理信息列表
     */
    List<DrugToxicology> loadDrugToxicologyList();

    /**
     * 批量删除药品信息
     * @param drugVos 药品信息列表
     */
    void batchDeleteDrug(List<DrugVo> drugVos);

    /**
     * 根据药品ID删除某个药品
     * @param drugId 药品ID
     */
    void deleteDrugByDrugId(Long drugId);

    /**
     * 更新某药品的信息
     * @param drugVo 药品的业务逻辑类
     */
    void drugUpdate(DrugVo drugVo);

    /**
     * 添加药品信息
     * @param drugVo 药品的业务逻辑类
     */
    void addDrug(DrugVo drugVo);

    /**
     * 根据国家药品编号查询药品
     * @param code 国家药品编号
     * @return 药品信息
     */
    List<DrugVo> getDrugByCode(String code);
}
