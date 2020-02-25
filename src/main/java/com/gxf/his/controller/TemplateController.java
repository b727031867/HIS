package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.MedicalTemplate;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.po.vo.TemplateVo;
import com.gxf.his.service.TemplateService;
import com.gxf.his.uitls.MyUtil;
import com.gxf.his.uitls.OfficeUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/20 15:31
 */
@RestController
@RequestMapping("/template")
@Slf4j
public class TemplateController extends BaseController {
    @Resource
    private TemplateService templateService;

    @GetMapping("/page")
    public <T> ServerResponseVO<T> getTemplates(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        PageHelper.startPage(page, size);
        List<TemplateVo> templateVos = templateService.getAllTemplates();
        PageInfo<TemplateVo> pageInfo = PageInfo.of(templateVos);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @GetMapping
    public <T> ServerResponseVO<T> getTemplateById(@RequestParam("medicalTemplateId") Long medicalTemplateId) {
        if (medicalTemplateId == null) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }

        return ServerResponseVO.success();
    }

    /**
     * 上传模板的接口
     *
     * @param uid          上传用户的ID
     * @param templateType 模板的类型
     * @param uploaderType 上传用户的类型
     * @param file         docx文档
     * @return docx文档转换成html后的内容
     */
    @PostMapping
    public <T> ServerResponseVO<T> uploadTemplate(@RequestParam("uid") Long uid, @RequestParam("templateType") Integer templateType, @RequestParam("description") String description, @RequestParam("title") String title, @RequestParam("uploaderType") Integer uploaderType, @RequestParam("file") MultipartFile file) {
        String htmls = null;
        Long id = null;
        HashMap<String, Object> data = new HashMap<>(16);
        if (!file.isEmpty() && (uid != null) && (templateType != null) && (uploaderType != null)) {
            try {
                htmls = OfficeUtil.docxToHtml(new ByteArrayInputStream(file.getBytes()));
                Document htmlDocument = Jsoup.parse(htmls);
                Element body = htmlDocument.body();
                log.info(body.html());
                htmls = body.html();
                id = getMedicalTemplateIncreaseId(uid, templateType, uploaderType, htmls,description,title);
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponseVO.error(ServerResponseEnum.WORD_CONVERSION_EXCEPTION);
            }
        } else {
            ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        data.put("content", htmls);
        data.put("templateId", id);
        return MyUtil.cast(ServerResponseVO.success(data));
    }

    @PostMapping("/addOrUpdate")
    public <T> ServerResponseVO<T> addTemplate(@RequestParam("uid") Long uid, @RequestParam("templateType") Integer templateType, @RequestParam("uploaderType") Integer uploaderType, @RequestParam("content") String content, @RequestParam("description") String description, @RequestParam("title") String title, @RequestParam(value = "medicalTemplateId", required = false) Long medicalTemplateId) {
        Long id = null;
        if ((content != null) && (uid != null) && (templateType != null) && (uploaderType != null)&& (title != null)&& (description != null)) {
            if (medicalTemplateId != null) {
                MedicalTemplate medicalTemplateById = templateService.getMedicalTemplateById(medicalTemplateId);
                MedicalTemplate doctorMedicalTemplate = new MedicalTemplate();
                doctorMedicalTemplate.setMedicalTemplateId(medicalTemplateId);
                doctorMedicalTemplate.setType(templateType);
                doctorMedicalTemplate.setDescription(description);
                doctorMedicalTemplate.setTitle(title);
                doctorMedicalTemplate.setStatus(1);
                doctorMedicalTemplate.setUploadId(uid);
                doctorMedicalTemplate.setUploaderType(uploaderType);
                doctorMedicalTemplate.setUpdateDatetime(new Date());
                doctorMedicalTemplate.setContent(content);
                doctorMedicalTemplate.setCreateDatetime(medicalTemplateById.getCreateDatetime());
                id = templateService.saveOrUpdateTemplate(doctorMedicalTemplate);
            } else {
                id = getMedicalTemplateIncreaseId(uid, templateType, uploaderType, content,description,title);
            }
        } else {
            ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        return MyUtil.cast(ServerResponseVO.success(id));
    }

    @DeleteMapping
    public <T> ServerResponseVO<T> deleteCashierAndUserByCashierId(@RequestParam(name = "medicalTemplateId") Long medicalTemplateId) {
        try {
            templateService.deleteTemplate(medicalTemplateId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.DOCTOR_MEDICAL_TEMPLATE_DELETE_FAIL);
        }
    }

    /**
     * 插入并且获取模板的自增ID
     *
     * @param uid          用户ID
     * @param templateType 模板类型
     * @param uploaderType 上传者的类型
     * @param html         模板内容
     * @return 插入新模板时，自增的模板ID
     */
    private Long getMedicalTemplateIncreaseId(Long uid, Integer templateType, Integer uploaderType, String html,  String description,  String title) {
        Long id;
        MedicalTemplate doctorMedicalTemplate = new MedicalTemplate();
        doctorMedicalTemplate.setType(templateType);
        doctorMedicalTemplate.setStatus(1);
        doctorMedicalTemplate.setUploadId(uid);
        doctorMedicalTemplate.setDescription(description);
        doctorMedicalTemplate.setTitle(title);
        doctorMedicalTemplate.setUploaderType(uploaderType);
        doctorMedicalTemplate.setUpdateDatetime(new Date());
        doctorMedicalTemplate.setCreateDatetime(new Date());
        doctorMedicalTemplate.setContent(html);
        id = templateService.saveOrUpdateTemplate(doctorMedicalTemplate);
        return id;
    }
}
