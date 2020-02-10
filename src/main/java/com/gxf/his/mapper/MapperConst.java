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
     * 一对一关联部门编号
     */
     public final static String ONE_DEPARTMENT = "com.gxf.his.mapper.dao.IDepartmentMapper.selectByDepartmentCode";

    /**
     * 一对一关联排班信息
     */
    public final static String ONE_GENERATE_SCHEDULING = "com.gxf.his.mapper.generate.SchedulingMapper.selectByPrimaryKey";

    /**
     * 一对一关联用户信息
     */
    public final static String ONE_GENERATE_USER = "com.gxf.his.mapper.generate.UserMapper.selectByPrimaryKey";

    //**************************************一对多关联方法**************************************

    /**
     * 一对多关联订单的订单项
     */
    public final static String MANY_ORDER_ITEM = "com.gxf.his.mapper.dao.IOrderItemMapper.findOrderItemsByOrderId";
}
