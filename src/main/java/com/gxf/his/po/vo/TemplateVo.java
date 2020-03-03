package com.gxf.his.po.vo;

import com.gxf.his.po.generate.MedicalTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/21 14:41
 * 模板的业务类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateVo extends MedicalTemplate {
    /**
     * 导出PDF的html字符串内容
     */
    private String html;

}
