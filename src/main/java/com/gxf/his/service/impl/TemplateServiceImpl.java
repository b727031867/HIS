package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.TemplateException;
import com.gxf.his.mapper.dao.IMedicalTemplate;
import com.gxf.his.po.generate.MedicalTemplate;
import com.gxf.his.po.vo.TemplateVo;
import com.gxf.his.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/20 15:36
 */
@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService {
    @Resource
    private IMedicalTemplate iMedicalTemplate;

    @Override
    public List<TemplateVo> getAllTemplates() {
        try {
            return iMedicalTemplate.selectAllTemplates();
        } catch (Exception e) {
            log.error("模板查询失败！", e);
            throw new TemplateException(ServerResponseEnum.DOCTOR_MEDICAL_TEMPLATE_LIST_FAIL);
        }
    }

    @Override
    public int deleteTemplate(Long medicalTemplateId) {
        try {
            return iMedicalTemplate.deleteByPrimaryKey(medicalTemplateId);
        } catch (Exception e) {
            throw new TemplateException(ServerResponseEnum.DOCTOR_MEDICAL_TEMPLATE_DELETE_FAIL);
        }
    }

    @Override
    public Long saveOrUpdateTemplate(MedicalTemplate doctorMedicalTemplate) {
        try {
            if (doctorMedicalTemplate.getMedicalTemplateId() == null) {
                iMedicalTemplate.insertAndInjectId(doctorMedicalTemplate);
            } else {
                iMedicalTemplate.updateByPrimaryKey(doctorMedicalTemplate);
            }
            return doctorMedicalTemplate.getMedicalTemplateId();
        } catch (Exception e) {
            log.error("插入模板失败", e);
            throw new TemplateException(ServerResponseEnum.DOCTOR_MEDICAL_TEMPLATE_SAVE_FAIL);
        }

    }

    @Override
    public MedicalTemplate getMedicalTemplateById(Long medicalTemplateId) {
        try {
            return iMedicalTemplate.selectByPrimaryKey(medicalTemplateId);
        } catch (Exception e) {
            log.error("查询模板失败", e);
            throw new TemplateException(ServerResponseEnum.DOCTOR_MEDICAL_TEMPLATE_LIST_FAIL);
        }
    }
}
