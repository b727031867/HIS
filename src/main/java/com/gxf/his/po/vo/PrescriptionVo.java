package com.gxf.his.po.vo;

import com.gxf.his.po.generate.DoctorTicket;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.generate.Prescription;
import com.gxf.his.po.generate.PrescriptionExtraCost;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 11:45
 * 处方单的业务类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PrescriptionVo extends Prescription {

    /**
     * 自己关联父级
     */
    private Prescription prescription;

    /**
     * 关联的处方单详情（药品列表）
     */
    private List<PrescriptionInfoVo> prescriptionInfoVos;

    /**
     * 关联的处方单详情（药品列表）
     */
    private List<DrugVo> drugVos;

    /**
     * 处方单关联的患者关联信息
     */
    private PatientVo patientVo;

    /**
     * 处方单关联的患者,不关联信息
     */
    private Patient patient;

    /**
     * 处方单关联的医生，关联信息
     */
    private DoctorVo doctorVo;

    /**
     * 处方单关联的挂号信息
     */
    private DoctorTicket doctorTicket;

    /**
     * 处方单关联的挂号信息ID
     */
    private Long doctorTicketId;

    /**
     * 关联处方单额外收费
     */
    private PrescriptionExtraCost prescriptionExtraCost;

}
