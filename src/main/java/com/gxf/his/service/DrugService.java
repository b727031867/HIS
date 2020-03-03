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
}
