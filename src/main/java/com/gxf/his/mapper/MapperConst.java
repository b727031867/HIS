package com.gxf.his.mapper;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/10 12:48
 * DAO中的一些常量，包括关联方法名等等
 */
public class MapperConst {
    //**************************************一对一关联方法**************************************

    /**
     * 一对一关联排班信息
     */
    public final static String ONE_GENERATE_SCHEDULING = "com.gxf.his.mapper.generate.DoctorSchedulingMapper.selectByPrimaryKey";

    /**
     * 一对一关联药品毒理信息
     */
    public final static String ONE_GENERATE_DRUG_TOXICOLOGY = "com.gxf.his.mapper.generate.DrugToxicologyMapper.selectByPrimaryKey";

    /**
     * 一对一关联药品信息
     */
    public final static String ONE_GENERATE_DRUG = "com.gxf.his.mapper.generate.DrugMapper.selectByPrimaryKey";

    /**
     * 一对一关联病人档案信息
     */
    public final static String ONE_GENERATE_PATIENT_FILE = "com.gxf.his.mapper.generate.PatientFileMapper.selectByPrimaryKey";

    /**
     * 一对一关联用户信息
     */
    public final static String ONE_GENERATE_USER = "com.gxf.his.mapper.generate.UserMapper.selectByPrimaryKey";

    /**
     * 一对一关联票务资源
     */
    public final static String ONE_GENERATE_TICKET_RESOURCE = "com.gxf.his.mapper.generate.DoctorTicketResourceMapper.selectByPrimaryKey";

    /**
     * 一对一关联检查项
     */
    public final static String ONE_GENERATE_CHECK_ITEM = "com.gxf.his.mapper.generate.CheckItemMapper.selectByPrimaryKey";

    /**
     * 一对一关联挂号信息
     */
    public final static String ONE_GENERATE_DOCTOR_TICKET = "com.gxf.his.mapper.generate.DoctorTicketMapper.selectByPrimaryKey";

    /**
     * 一对一关联订单信息
     */
    public final static String ONE_GENERATE_ORDER = "com.gxf.his.mapper.generate.OrderMapper.selectByPrimaryKey";

    /**
     * 一对一关联额外收费
     */
    public final static String ONE_GENERATE_PRESCRIPTION_EXTRA_COST = "com.gxf.his.mapper.dao.PrescriptionExtraCostMapper.selectByPrimaryKey";

    /**
     * 一对一关联处方项
     */
    public final static String ONE_GENERATE_PRESCRIPTION_INFO = "com.gxf.his.mapper.dao.PrescriptionInfoMapper.selectByPrimaryKey";

    /**
     * 通过挂号信息ID
     * 一对一关联病历信息
     */
    public final static String ONE_PATIENT_MEDICAL_RECORD_BY_TICKET_ID = "com.gxf.his.mapper.dao.IPatientMedicalRecordMapper.selectPatientMedicalRecordByTicketId";

    /**
     * 通过部门编号
     * 一对一关联部门
     */
    public final static String ONE_DEPARTMENT_BY_DEPARTMENT_CODE = "com.gxf.his.mapper.dao.IDepartmentMapper.selectByDepartmentCode";

    /**
     * 一对一关联挂号信息
     */
    public final static String ONE_ORDER_DOCTOR_TICKET = "com.gxf.his.mapper.dao.ITicketMapper.selectByOrderId";

    /**
     * 一对一关联票务信息的医生的所有信息
     */
    public final static String ONE_TICKET_DOCTOR_ALL = "com.gxf.his.mapper.dao.IDoctorMapper.selectByPrimaryKeyRelated";

    /**
     * 一对一关联票务信息的患者
     */
    public final static String ONE_TICKET_PATIENT = "com.gxf.his.mapper.dao.IPatientMapper.selectByPrimaryKey";


    /**
     * 一对一关联药品业务类
     */
    public final static String ONE_DRUG_VO = "com.gxf.his.mapper.dao.IDrugMapper.selectDrugByDrugId";
    /**
     * 一对一关联药品库存
     */
    public final static String ONE_DRUG_STORE = "com.gxf.his.mapper.dao.IDrugStoreMapper.selectDrugStoreByDrugId";

    /**
     * 通过处方单ID
     * 一对一关联额外收费
     */
    public final static String ONE_PRESCRIPTION_EXTRA_COST = "com.gxf.his.mapper.dao.IPrescriptionExtraCostMapper.selectByPrescriptionId";

    /**
     * 通过处方单ID
     * 一对一关联订单
     */
    public final static String ONE_ORDER_BY_PRESCRIPTION_ID_ = "com.gxf.his.mapper.dao.IOrderMapper.selectOrderByPrescriptionId";

    //**************************************一对多关联方法**************************************

    /**
     * 一对多关联订单的订单项
     * 不关联查询
     */
    public final static String MANY_ORDER_ITEM_UNRELATED = "com.gxf.his.mapper.dao.IOrderItemMapper.findOrderItemsByOrderIdNoRelated";

    /**
     * 一对多关联处方单的处方项
     * 获取所有关联信息
     */
    public final static String MANY_DRUG_ITEM_ALL = "com.gxf.his.mapper.dao.IPrescriptionInfoMapper.selectPrescriptionInfosByPrescriptionId";

}
