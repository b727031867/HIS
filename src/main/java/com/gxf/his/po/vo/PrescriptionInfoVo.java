package com.gxf.his.po.vo;

import com.gxf.his.po.generate.Drug;
import com.gxf.his.po.generate.PrescriptionInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 11:49
 * 处方单详情的业务类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PrescriptionInfoVo extends PrescriptionInfo {

    /**
     * 处方单信息关联的药品 一对一
     */
    private DrugVo drugVo;
}
