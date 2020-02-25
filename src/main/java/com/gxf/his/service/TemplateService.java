package com.gxf.his.service;

import com.gxf.his.po.generate.MedicalTemplate;
import com.gxf.his.po.vo.CashierVo;
import com.gxf.his.po.vo.TemplateVo;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/20 15:36
 */
public interface TemplateService {

    /**
     * 获取所有模板的列表
     *
     * @return 模板列表
     */
    List<TemplateVo> getAllTemplates();

    /**
     * 删除某个模板
     *
     * @param medicalTemplateId 模板对象的ID
     * @return 本次操作影响的行数
     */
    int deleteTemplate(Long medicalTemplateId);

    /**
     * 添加或更新模板
     * @param doctorMedicalTemplate 模板对象
     * @return 新增模板的ID或修改模板的ID
     */
    Long saveOrUpdateTemplate(MedicalTemplate doctorMedicalTemplate);

    /**
     * 根据ID获取模板
     * @param medicalTemplateId 模板ID
     * @return 模板对象
     */
    MedicalTemplate getMedicalTemplateById(Long medicalTemplateId);
}
