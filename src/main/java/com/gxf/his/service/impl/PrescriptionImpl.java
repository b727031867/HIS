package com.gxf.his.service.impl;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.PrescriptionException;
import com.gxf.his.mapper.dao.IPrescriptionExtraCostMapper;
import com.gxf.his.mapper.dao.IPrescriptionInfoMapper;
import com.gxf.his.mapper.dao.IPrescriptionMapper;
import com.gxf.his.po.generate.Prescription;
import com.gxf.his.po.generate.PrescriptionExtraCost;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.po.vo.PrescriptionInfoVo;
import com.gxf.his.po.vo.PrescriptionVo;
import com.gxf.his.service.PrescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 23:10
 * 处方单业务实现类
 */
@Service
@Slf4j
public class PrescriptionImpl implements PrescriptionService {
    @Resource
    private IPrescriptionMapper iPrescriptionMapper;
    @Resource
    private IPrescriptionExtraCostMapper iPrescriptionExtraCostMapper;
    @Resource
    private IPrescriptionInfoMapper iPrescriptionInfoMapper;

    @Override
    public PrescriptionVo getPrescriptionByDoctorTicketId(Long doctorTicketId) {
        try {
            return iPrescriptionMapper.getPrescriptionByDoctorTicketId(doctorTicketId);
        } catch (Exception e) {
            log.error("根据票务信息ID处方单查询失败", e);
            throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_LIST_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PrescriptionVo savePrescription(PrescriptionVo prescriptionVo) {
        //保存处方单详情
        List<DrugVo> drugVos = prescriptionVo.getDrugVos();
        //计算总价
        HashMap<String, BigDecimal> map = getSpends(drugVos);
        //保存处方单
        Prescription prescription = new Prescription();
        prescription.setTotalSpend(map.get("totalSpend"));
        prescription.setDoctorAdvice(prescriptionVo.getDoctorAdvice());
        prescription.setPatientId(prescriptionVo.getPatientVo().getPatientId());
        prescription.setTicketId(prescriptionVo.getDoctorTicketId());
        prescription.setDoctorId(prescriptionVo.getDoctorVo().getDoctorId());
        prescription.setCreateDatetime(new Date());
        iPrescriptionMapper.insertAndInjectId(prescription);
        int i = 0;
        List<PrescriptionInfoVo> prescriptionInfoVos = new ArrayList<>(8);
        for (DrugVo drugVo : drugVos) {
            PrescriptionInfoVo prescriptionInfoVo = new PrescriptionInfoVo();
            prescriptionInfoVo.setDrugVo(drugVo);
            prescriptionInfoVo.setPrescriptionId(prescription.getPrescriptionId());
            prescriptionInfoVo.setDrugId(drugVo.getDrugId());
            prescriptionInfoVo.setStatus(drugVo.getIsInBulk());
            prescriptionInfoVo.setNum(drugVo.getNumber());
            prescriptionInfoVo.setItemTotalPrice(map.get(i + ""));
            //是否散装
            if (drugVo.getIsInBulk() == 0) {
                //不散卖
                prescriptionInfoVo.setUnit(drugVo.getDrugStore().getInventoryUnit());
            } else {
                //散卖
                prescriptionInfoVo.setUnit(drugVo.getDrugStore().getSmallestUnit());
            }
            iPrescriptionInfoMapper.insert(prescriptionInfoVo);
            i++;
            prescriptionInfoVos.add(prescriptionInfoVo);
        }
        //保存额外收费
        PrescriptionExtraCost prescriptionExtraCost = new PrescriptionExtraCost();
        prescriptionExtraCost.setPrescriptionId(prescription.getPrescriptionId());
        prescriptionExtraCost.setOperateId(prescriptionVo.getDoctorVo().getUserId());
        prescriptionExtraCost.setAmount(prescriptionVo.getPrescriptionExtraCost().getAmount());
        prescriptionExtraCost.setRemark(prescriptionVo.getPrescriptionExtraCost().getRemark());
        prescriptionExtraCost.setCreateTime(new Date());
        iPrescriptionExtraCostMapper.insert(prescriptionExtraCost);
        prescriptionVo.setPrescriptionId(prescription.getPrescriptionId());
        prescriptionVo.setPrescriptionInfoVos(prescriptionInfoVos);
        prescriptionVo.setPrescriptionExtraCost(prescriptionExtraCost);
        prescriptionVo.setPrescription(prescription);
        // TODO 生成对应的订单
        return prescriptionVo;
    }

    /**
     * 计算某个处方单的各类价格
     *
     * @return 各类价格 包括 单项总价 总价 以及 额外收费
     */
    private HashMap<String, BigDecimal> getSpends(List<DrugVo> drugVos) {
        HashMap<String, BigDecimal> map = new HashMap<>(16);
        //计算药品总价
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < drugVos.size(); i++) {
            //散卖
            BigDecimal itemTotal;
            if (drugVos.get(i).getIsInBulk() == 1) {
                itemTotal = drugVos.get(i).getDrugStore().getMinPrice().multiply(new BigDecimal(String.valueOf(drugVos.get(i).getNumber())));
            } else {
                //不散卖
                itemTotal = drugVos.get(i).getDrugStore().getPrescriptionPrice().multiply(new BigDecimal(String.valueOf(drugVos.get(i).getNumber())));
            }
            map.put(i + "", itemTotal);
            total = total.add(itemTotal);
        }
        //计算额外附加收费总价

        map.put("totalSpend", total);
        return map;
    }

}
