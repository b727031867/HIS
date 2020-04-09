package com.gxf.his.service.impl;

import com.gxf.his.Const;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.BaseBusinessException;
import com.gxf.his.exception.PrescriptionException;
import com.gxf.his.mapper.dao.*;
import com.gxf.his.po.generate.DrugDistribution;
import com.gxf.his.po.generate.Prescription;
import com.gxf.his.po.generate.PrescriptionExtraCost;
import com.gxf.his.po.generate.PrescriptionRefundInfo;
import com.gxf.his.po.vo.*;
import com.gxf.his.service.OrderService;
import com.gxf.his.service.PrescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    @Resource
    private IPrescriptionRefundInfoMapper iPrescriptionRefundInfoMapper;
    @Resource
    private OrderService orderService;
    @Resource
    private IOrderMapper iOrderMapper;
    @Resource
    private IPatientMapper iPatientMapper;
    @Resource
    private IDoctorMapper iDoctorMapper;
    @Resource
    private IDrugDistributionMapper iDrugDistributionMapper;

    @Override
    public PrescriptionVo getPrescriptionByDoctorTicketId(Long doctorTicketId) {
        try {
            PrescriptionVo prescriptionVo = iPrescriptionMapper.getPrescriptionByDoctorTicketId(doctorTicketId);
            if (prescriptionVo != null) {
                //查询设置日期格式
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String s = sdf.format(prescriptionVo.getCreateDatetime());
                    prescriptionVo.setCreateDatetime(sdf.parse(s));
                } catch (ParseException e) {
                    log.warn("日期格式转换失败", e);
                }
            }
            return prescriptionVo;
        } catch (Exception e) {
            log.error("根据票务信息ID处方单查询失败", e);
            throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_LIST_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PrescriptionVo savePrescription(PrescriptionVo prescriptionVo) {
        List<PrescriptionInfoVo> prescriptionInfoVos = new ArrayList<>(8);
        List<OrderItemVo> orderItemVos = new ArrayList<>(8);
        //保存处方单详情
        List<DrugVo> drugVos = prescriptionVo.getDrugVos();
        //处方单是否有药品
        boolean flag = true;
        //计算总价
        HashMap<String, BigDecimal> map;
        if (drugVos == null || drugVos.size() == 0) {
            flag = false;
            map = getSpends(drugVos, 2, prescriptionVo.getPrescriptionExtraCost().getAmount());
        } else {
            map = getSpends(drugVos, 1, prescriptionVo.getPrescriptionExtraCost().getAmount());
        }
        //保存处方单
        Prescription prescription = new Prescription();
        prescription.setTotalSpend(map.get("totalSpend"));
        prescription.setDoctorAdvice(prescriptionVo.getDoctorAdvice());
        prescription.setPatientId(prescriptionVo.getPatientVo().getPatientId());
        prescription.setTicketId(prescriptionVo.getDoctorTicketId());
        prescription.setDoctorId(prescriptionVo.getDoctorVo().getDoctorId());
        prescription.setCreateDatetime(new Date());
        iPrescriptionMapper.insertAndInjectId(prescription);
        if (flag) {
            int i = 0;
            for (DrugVo drugVo : drugVos) {
                PrescriptionInfoVo prescriptionInfoVo = new PrescriptionInfoVo();
                OrderItemVo orderItemVo = new OrderItemVo();
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
                iPrescriptionInfoMapper.insertAndInjectId(prescriptionInfoVo);
                orderItemVo.setPrescriptionInfo(prescriptionInfoVo);
                i++;
                prescriptionInfoVos.add(prescriptionInfoVo);
                orderItemVos.add(orderItemVo);
            }
        }

        //保存额外收费
        PrescriptionExtraCost prescriptionExtraCost = new PrescriptionExtraCost();
        prescriptionExtraCost.setPrescriptionId(prescription.getPrescriptionId());
        prescriptionExtraCost.setOperateId(prescriptionVo.getDoctorVo().getUser().getUserId());
        prescriptionExtraCost.setAmount(prescriptionVo.getPrescriptionExtraCost().getAmount());
        prescriptionExtraCost.setRemark(prescriptionVo.getPrescriptionExtraCost().getRemark());
        prescriptionExtraCost.setCreateTime(new Date());
        iPrescriptionExtraCostMapper.insertAndInjectId(prescriptionExtraCost);
        //添加额外收费到订单项中
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setPrescriptionExtraCost(prescriptionExtraCost);
        orderItemVos.add(orderItemVo);
        prescriptionVo.setPrescriptionId(prescription.getPrescriptionId());
        prescriptionVo.setPrescriptionInfoVos(prescriptionInfoVos);
        prescriptionVo.setPrescriptionExtraCost(prescriptionExtraCost);
        prescriptionVo.setPrescription(prescription);
        //添加订单以及订单项
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderVoItemList(orderItemVos);
        orderVo.setDoctorVo(prescriptionVo.getDoctorVo());
        orderVo.setOrderType(1);
        orderVo.setDoctorId(prescriptionVo.getDoctorVo().getDoctorId());
        orderVo.setPatientId(prescriptionVo.getPatientVo().getPatientId());
        orderVo.setOrderStatus("0");
        orderVo.setOrderTotal(map.get("totalSpend"));
        orderVo.setOrderCreateTime(new Date());
        //n分钟后过期
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(Const.PRESCRIPTION_ORDER_EXPIRED_TIME);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        orderVo.setOrderExpireTime(Date.from(zdt.toInstant()));
        orderVo.setPrescriptionId(prescription.getPrescriptionId());
        orderService.addOrderPrescription(orderVo);
        //查询设置日期格式
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            prescriptionVo.setCreateDatetime(sdf.parse(s));
        } catch (ParseException e) {
            log.warn("日期格式转换失败", e);
        }
        return prescriptionVo;
    }

    @Override
    public PrescriptionVo getPrescriptionByPrescriptionId(Long prescriptionId) {
        try {
            return iPrescriptionMapper.getPrescriptionByPrescriptionId(prescriptionId);
        } catch (Exception e) {
            log.error("根据处方单ID查询处方单失败", e);
            throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_LIST_FAIL);
        }
    }

    @Override
    public PrescriptionVo getPayedPrescriptionByPrescriptionId(Long prescriptionId) {
            OrderVo orderVo = iOrderMapper.selectOrderByPrescriptionId(prescriptionId);
            if(orderVo == null){
                throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_NO_EXITS);
            }
            if(null == orderVo.getOrderStatus() || "".equals(orderVo.getOrderStatus().trim())){
                throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_STATUS_EMPTY);
            }
        switch (orderVo.getOrderStatus()) {
            case "1":
                //检查是否已经分发过此处方单
                List<DrugDistribution> drugDistributions = iDrugDistributionMapper.getDistributionsByPrescriptionId(prescriptionId);
                if (drugDistributions.size() > 0) {
                    throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_DISTRIBUTED);
                }
                return iPrescriptionMapper.getPrescriptionByPrescriptionId(prescriptionId);
            case "2":
                throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_REFUNDED);
            case "3":
                throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_EXPIRED);
            default:
                throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_UNPAID);
        }
    }

    @Override
    public List<PrescriptionVo> selectPrescriptionVosByAttribute(Boolean isAccurate, String attribute, String value) {
        List<PrescriptionVo> prescriptionVos = new ArrayList<>(16);
        try {
            //根据不同的属性类型 模糊查询名称对应的id 在查询id对应的处方单
            String patientName = "patientName";
            String doctorName = "doctorName";
            if (!isAccurate) {
                if (patientName.equals(attribute)) {
                    //模糊查询姓名对应的患者ID列表
                    PatientVo patientVo = new PatientVo();
                    patientVo.setSearchAttribute(patientName);
                    patientVo.setValue(value);
                    List<PatientVo> patientVos = iPatientMapper.selectPatientByAttribute(patientVo);
                    for (PatientVo vo : patientVos) {
                        List<PrescriptionVo> prescriptionVoList = iPrescriptionMapper.selectPrescriptionVosByPatientId(vo.getPatientId());
                        prescriptionVos.addAll(prescriptionVoList);
                    }
                } else if (doctorName.equals(attribute)) {
                    DoctorVo doctorVo = new DoctorVo();
                    doctorVo.setDoctorName(value);
                    List<DoctorVo> doctorVos = iDoctorMapper.selectDoctorByAttribute(doctorVo);
                    for (DoctorVo vo : doctorVos) {
                        List<PrescriptionVo> vos = iPrescriptionMapper.selectPrescriptionVosByDoctorId(vo.getDoctorId());
                        prescriptionVos.addAll(vos);
                    }
                }
            } else {
                log.warn("当前是精确查询");
            }
        } catch (Exception e) {
            log.error("按属性查询处方单失败", e);
            throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_LIST_FAIL);
        }
        return prescriptionVos;
    }

    @Override
    public List<PrescriptionVo> getPrescriptionListByTimeArea(Date startDate, Date endDate) {
        try {
            return iPrescriptionMapper.selectPrescriptionsByRange(startDate, endDate);
        } catch (Exception e) {
            log.error("根据时间范围查询处方单失败", e);
            throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_LIST_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRefundPrescription(PrescriptionRefundInfo prescriptionRefundInfo) {
        //检查是否存在此处方单
        Prescription prescription = iPrescriptionMapper.selectByPrimaryKey(prescriptionRefundInfo.getPrescriptionId());
        if (prescription == null) {
            log.warn("当前处方单号没有对应的处方单");
            throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_NO_EXITS);
        }
        //检查此处方单是否已经存在申请退款
        PrescriptionRefundInfo refundInfo = iPrescriptionRefundInfoMapper.selectByPrescriptionId(prescription.getPrescriptionId());
        if (refundInfo != null) {
            log.warn("当前处方单号已经有对应的退款信息了");
            throw new PrescriptionException(ServerResponseEnum.PRESCRIPTION_REFUND_INFO_EXITS);
        }
        //插入申请的退款信息
        iPrescriptionRefundInfoMapper.insert(prescriptionRefundInfo);
        //修改处方单的订单状态
        OrderVo orderVo = iOrderMapper.selectOrderByPrescriptionId(prescriptionRefundInfo.getPrescriptionId());
        orderVo.setOrderStatus("2");
        iOrderMapper.updateByPrimaryKey(orderVo);
    }

    /**
     * 计算某个处方单的各类价格
     *
     * @return 各类价格 包括 单项总价 总价 以及 额外收费
     */
    private HashMap<String, BigDecimal> getSpends(List<DrugVo> drugVos, int type, BigDecimal extraCost) {
        HashMap<String, BigDecimal> map = new HashMap<>(16);
        BigDecimal total = BigDecimal.ZERO;
        if (type == 1) {
            //计算药品总价
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
            if (extraCost != null) {
                total = total.add(extraCost);
            }
            map.put("totalSpend", total);
        } else if (type == 2) {
            //计算额外附加收费总价
            if (extraCost != null) {
                total = total.add(extraCost);
            }
            map.put("totalSpend", total);
        } else {
            log.warn("未知的价格计算类型");
        }
        return map;
    }

}
