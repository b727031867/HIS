package com.gxf.his.controller;

import com.gxf.his.po.vo.TemplateVo;
import com.gxf.his.uitls.HtmlToPdfUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/10 14:57
 * 公共Controller
 */
@Controller
@Slf4j
public class BaseController {

    /**
     * 查询参数的通用设置方法
     *
     * @param attribute  查询的属性名，与POJO属性名相同 比如查询患者名称 patientName
     * @param isAccurate 是否精确查询
     * @param value      查询的值
     * @return 参数是否正常
     */
    public Boolean searchParamCheck(String attribute, Boolean isAccurate, String value) {
        return attribute != null && attribute.trim().length() != 0 && value != null && value.trim().length() != 0 && isAccurate != null;
    }

    /**
     * 通用的html转pdf接口
     *
     * @param templateVo 模板对象
     * @return pdf文件
     */
    @RequestMapping(value = "/exportPdf", method = {RequestMethod.POST, RequestMethod.GET},
            produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity exportPdf(@RequestBody TemplateVo templateVo) {
        try {
            return HtmlToPdfUtil.export(templateVo.getHtml());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>("{ \"code\" : \"404\", \"message\" : \"not found\" }",
                headers, HttpStatus.NOT_FOUND);
    }
}
