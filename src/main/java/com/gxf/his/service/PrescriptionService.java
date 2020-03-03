package com.gxf.his.service;

import com.gxf.his.po.vo.PrescriptionVo;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 23:09
 * 处方单接口
 */
public interface PrescriptionService {

    /**
     * 根据挂号信息ID查找对应的处方单
     * @param doctorTicketId 挂号信息ID
     * @return 处方单
     */
    PrescriptionVo getPrescriptionByDoctorTicketId(Long doctorTicketId);

    /**
     * 保存处方单并且返回自增主键
     * @param prescriptionVo 处方单的业务类
     * @return 新增处方单的主键
     */
    PrescriptionVo savePrescription(PrescriptionVo prescriptionVo);
}
