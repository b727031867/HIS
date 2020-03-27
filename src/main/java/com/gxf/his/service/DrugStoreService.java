package com.gxf.his.service;

import com.gxf.his.po.vo.DrugStoreVo;

import java.util.List;

/**
 * @author GXF
 * 药库模块的接口
 */
public interface DrugStoreService {

    /**
     * 查询所有药品库存信息,关联查询药品信息
     * @return 药品库存信息
     */
    List<DrugStoreVo> getAllDrugStore();

    /**
     * 批量删除药品库存信息
     * @param drugStoreVos 药品库存信息列表
     */
    void batchDeleteDrugStore(List<DrugStoreVo> drugStoreVos);

    /**
     * 根据ID删除药品库存信息
     * @param inventoryId 药品库存ID
     */
    void deleteDrugStoreByInventoryId(Long inventoryId);

    /**
     * 根据属性查询药品库存以及关联的药品信息
     * @param value 值
     * @param attribute 字段名
     * @param isAccurate 是否精确查询
     * @return 药品库存列表
     */
    List<DrugStoreVo> getDrugStoreVoByAttribute(String value,String attribute,Boolean isAccurate);

    /**
     * 修改某药品库存信息
     * @param drugStoreVo 药品库存信息
     */
    void drugStoreUpdate(DrugStoreVo drugStoreVo);

    /**
     * 根据国家药品编码查询药品库存
     * @param code 国家药品编码
     * @return 药品库存
     */
    List<DrugStoreVo> getDrugStoreByCode(String code);

    /**
     * 添加一种药品的库存信息
     * @param drugStoreVo 药品库存信息
     */
    void addDrugStore(DrugStoreVo drugStoreVo);
}
