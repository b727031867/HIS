package com.gxf.his.service;

import com.gxf.his.po.generate.PrescriptionRefundInfo;
import com.gxf.his.po.vo.PrescriptionVo;

import java.util.Date;
import java.util.List;

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

    /**
     * 根据处方单ID查询处方单，并且关联查询订单
     * @param prescriptionId 处方单ID
     * @return 处方单以及关联信息
     */
    PrescriptionVo getPrescriptionByPrescriptionId(Long prescriptionId);

    /**
     * 根据属性查询处方单列表
     * @param isAccurate 是否精确查询
     * @param attribute 查询的属性
     * @param value 查询的值
     * @return 处方单业务类列表
     */
    List<PrescriptionVo> selectPrescriptionVosByAttribute(Boolean isAccurate,String attribute,String value);

    /**
     * 根据时间范围查询处方单
     * @param startDate 日期开始
     * @param endDate 日期结束
     * @return 处方单关联信息列表
     */
    List<PrescriptionVo> getPrescriptionListByTimeArea(Date startDate, Date endDate);

    /**
     * 添加一个处方退款申请
     * @param prescriptionRefundInfo 处方退款申请信息
     */
    void addRefundPrescription(PrescriptionRefundInfo prescriptionRefundInfo);

    /**
     * 根据处方单id查询已付款的处方单
     * @param prescriptionId 处方单id
     * @return 处方单信息
     */
    PrescriptionVo getPayedPrescriptionByPrescriptionId(Long prescriptionId);
}
