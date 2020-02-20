package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.Batch;
import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.DoctorMapper;
import com.gxf.his.po.generate.Cashier;
import com.gxf.his.po.generate.Doctor;
import com.gxf.his.po.vo.DoctorVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 医生模块的DAO接口
 */
public interface IDoctorMapper extends DoctorMapper {

    /**
     * 根据UID查询对应医生的信息
     * @param uid 用户ID
     * @return 医生对象
     */
    @Select({
            "select",
            "*",
            "from entity_doctor",
            "where user_id = #{uid,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="employee_id", property="employeeId", jdbcType=JdbcType.VARCHAR),
            @Result(column="doctor_name", property="doctorName", jdbcType=JdbcType.VARCHAR),
            @Result(column="doctor_professional_title", property="doctorProfessionalTitle", jdbcType=JdbcType.VARCHAR),
            @Result(column="doctor_introduction", property="doctorIntroduction", jdbcType=JdbcType.VARCHAR),
            @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="scheduling_id", property="schedulingId", jdbcType=JdbcType.BIGINT),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
            @Result(column="ticket_day_num", property="ticketDayNum", jdbcType=JdbcType.INTEGER),
            @Result(column="ticket_price", property="ticketPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="ticket_current_num", property="ticketCurrentNum", jdbcType=JdbcType.INTEGER)
    })
    Doctor selectByUid(Long uid);

    /**
     * 关联查询医生的所有信息
     * @param doctorId 医生ID
     * @return 医生
     */
    @Select({
            "select",
            "doctor_id, employee_id, doctor_name, doctor_professional_title, doctor_introduction, ",
            "department_code, scheduling_id, user_id, ticket_day_num, ticket_price, ticket_current_num",
            "from entity_doctor",
            "where doctor_id = #{doctorId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="employee_id", property="employeeId", jdbcType=JdbcType.VARCHAR),
            @Result(column="doctor_name", property="doctorName", jdbcType=JdbcType.VARCHAR),
            @Result(column="doctor_professional_title", property="doctorProfessionalTitle", jdbcType=JdbcType.VARCHAR),
            @Result(column="doctor_introduction", property="doctorIntroduction", jdbcType=JdbcType.VARCHAR),
            @Result(column="department_code", property="department", jdbcType=JdbcType.VARCHAR,one = @One(select = MapperConst.ONE_DEPARTMENT)),
            @Result(column="scheduling_id", property="doctorScheduling", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_SCHEDULING)),
            @Result(column="user_id", property="user", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_USER)),
            @Result(column="ticket_day_num", property="ticketDayNum", jdbcType=JdbcType.INTEGER),
            @Result(column="ticket_price", property="ticketPrice", jdbcType=JdbcType.DECIMAL),
            @Result(column="ticket_current_num", property="ticketCurrentNum", jdbcType=JdbcType.INTEGER)
    })
    DoctorVo selectByPrimaryKeyRelated(Long doctorId);

    /**
     * 批量删除医生
     *
     * @param doctors 医生列表
     * @return 影响的行数
     */
    @DeleteProvider(type = Batch.class, method = "batchDoctorDelete")
    Integer batchDoctorDelete(List<Doctor> doctors);

    /**
     * 查询所有医生的所有信息
     *
     * @return 医生的业务类列表
     */
    @Select({"select", "doctor_id, employee_id, doctor_name, doctor_professional_title, doctor_introduction, ",
            "department_code, scheduling_id, user_id, ticket_day_num,ticket_price,ticket_current_num", "from entity_doctor"})
    @Results({
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "employee_id", property = "employeeId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_name", property = "doctorName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_professional_title", property = "doctorProfessionalTitle", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_introduction", property = "doctorIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR, one = @One(select = MapperConst.ONE_DEPARTMENT)),
            @Result(column = "scheduling_id", property = "doctorScheduling", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_SCHEDULING)),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER)),
            @Result(column = "ticket_day_num", property = "ticketDayNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_price", property = "ticketPrice", jdbcType = JdbcType.DECIMAL),
            @Result(column = "ticket_current_num", property = "ticketCurrentNum", jdbcType = JdbcType.INTEGER),
    })
    List<DoctorVo> selectAllDoctorsInfo();

    /**
     * 查询某个科室有哪些医生
     *
     * @param departmentCode 科室编号
     * @return 医生列表
     */
    @Select("SELECT * " + "FROM entity_doctor " + "WHERE department_code = #{departmentCode,jdbcType=VARCHAR}")
    @Results({
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "employee_id", property = "employeeId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_name", property = "doctorName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_professional_title", property = "doctorProfessionalTitle", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_introduction", property = "doctorIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR, one = @One(select = MapperConst.ONE_DEPARTMENT)),
            @Result(column = "scheduling_id", property = "doctorScheduling", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_SCHEDULING)),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER)),
            @Result(column = "ticket_day_num", property = "ticketDayNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_price", property = "ticketPrice", jdbcType = JdbcType.DECIMAL)
    })
    List<DoctorVo> selectDoctorByDepartmentCode(String departmentCode);

    /**
     * 根据不同属性模糊查询医生列表 使用并且查询 比如 %doctorName% AND %doctorProfessionalTitle%
     *
     * @param doctorVo 医生业务类
     * @return 医生业务类列表
     */
    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_doctor "
            + "<where> "
            + "<if test='doctorName != null'>"
            + " doctor_name like '%${doctorName}%' "
            + "</if> "
            + "<if test='departments != null'> "
            + " AND department_code in "
            + " <foreach item='department' index='index' collection='departments' open='(' separator=',' close=')'>"
            + " #{department.departmentCode}"
            + "</foreach>"
            + "</if>"
            + "<if test='doctorProfessionalTitle != null'> "
            + " AND doctor_professional_title like '%${doctorProfessionalTitle}%' "
            + "</if>"
            + "</where> "
            + "</script>")
    @Results({
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "employee_id", property = "employeeId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_name", property = "doctorName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_professional_title", property = "doctorProfessionalTitle", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_introduction", property = "doctorIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR, one = @One(select = MapperConst.ONE_DEPARTMENT)),
            @Result(column = "scheduling_id", property = "schedulingId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER)),
            @Result(column = "ticket_day_num", property = "ticketDayNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "ticket_price", property = "ticketPrice", jdbcType = JdbcType.DECIMAL)
    })
    List<DoctorVo> selectDoctorByAttribute(DoctorVo doctorVo);

}