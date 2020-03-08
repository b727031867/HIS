package com.gxf.his.service;

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
     * @return 药品列表，关联了药品库存
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
}
