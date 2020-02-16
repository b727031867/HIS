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
     * 一对一关联部门编号
     */
    public final static String ONE_DEPARTMENT = "com.gxf.his.mapper.dao.IDepartmentMapper.selectByDepartmentCode";

    /**
     * 一对一关联订单的挂号信息
     */
    public final static String ONE_ORDER_DOCTOR_TICKET = "com.gxf.his.mapper.dao.ITicketMapper.selectByOrderId";

    /**
     * 一对一关联票务信息的医生
     */
    public final static String ONE_TICKET_DOCTOR = "com.gxf.his.mapper.dao.IDoctorMapper.selectByPrimaryKey";

    /**
     * 一对一关联票务信息的医生的所有信息
     */
    public final static String ONE_TICKET_DOCTOR_ALL = "com.gxf.his.mapper.dao.IDoctorMapper.selectByPrimaryKeyRelated";

    /**
     * 一对一关联票务信息的患者
     */
    public final static String ONE_TICKET_PATIENT = "com.gxf.his.mapper.dao.IPatientMapper.selectByPrimaryKey";

    //**************************************一对多关联方法**************************************

    /**
     * 一对多关联订单的订单项
     */
    public final static String MANY_ORDER_ITEM = "com.gxf.his.mapper.dao.IOrderItemMapper.findOrderItemsByOrderId";

    /**
     * 一对多关联订单的订单项
     * 获取所有关联信息
     */
    public final static String MANY_ORDER_ITEM_ALL = "com.gxf.his.mapper.dao.IOrderItemMapper.findOrderItemsByOrderIdRelated";
}
