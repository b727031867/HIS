package com.gxf.his.service;

import com.gxf.his.po.generate.MedicalTemplate;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/20 15:36
 */
public interface TemplateService {
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
