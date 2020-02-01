package com.gxf.his.mapper;

import com.gxf.his.po.Doctor;
import com.gxf.his.vo.DoctorUserVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


public interface DoctorMapper {
    @Delete({"delete from entity_doctor", "where doctor_id = #{doctorId,jdbcType=BIGINT}"})
    int deleteByPrimaryKey(Long doctorId);

    /**
     * 批量删除医生
     * @param doctors 医生列表
     * @return 影响的行数
     */
    @DeleteProvider(type = Batch.class, method = "batchDoctorDelete")
    Integer batchDoctorDelete(List<Doctor> doctors);

    @Insert({
            "insert into entity_doctor (doctor_id, employee_id, ", "doctor_name, doctor_professional_title, ",
            "doctor_introduction, department_code, ", "scheduling_id, user_id, ", "ticket_day_num)", "values " +
            "(#{doctorId,jdbcType=BIGINT}, #{employeeId,jdbcType=VARCHAR}, ", "#{doctorName,jdbcType=VARCHAR}, " +
            "#{doctorProfessionalTitle,jdbcType=VARCHAR}, ", "#{doctorIntroduction,jdbcType=VARCHAR}, " +
            "#{departmentCode,jdbcType=VARCHAR}, ", "#{schedulingId,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
            "#{ticketDayNum,jdbcType=INTEGER})"
    })
    int insert(Doctor record);

    @Select({"select", "doctor_id, employee_id, doctor_name, doctor_professional_title, doctor_introduction, ",
            "department_code, scheduling_id, user_id, ticket_day_num", "from entity_doctor", "where doctor_id = " +
            "#{doctorId,jdbcType=BIGINT}"})
    @Results({
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "employee_id", property = "employeeId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_name", property = "doctorName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_professional_title", property = "doctorProfessionalTitle", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_introduction", property = "doctorIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_code", property = "departmentCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "scheduling_id", property = "schedulingId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ticket_day_num", property = "ticketDayNum", jdbcType = JdbcType.INTEGER)
    })
    Doctor selectByPrimaryKey(Long doctorId);

    @Select({"select", "doctor_id, employee_id, doctor_name, doctor_professional_title, doctor_introduction, ",
            "department_code, scheduling_id, user_id, ticket_day_num", "from entity_doctor"})
    @Results({
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "employee_id", property = "employeeId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_name", property = "doctorName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_professional_title", property = "doctorProfessionalTitle", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_introduction", property = "doctorIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR,one = @One(select = "com.gxf.his.mapper.DepartmentMapper.selectByDepartmentCode")),
            @Result(column = "scheduling_id", property = "schedulingId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey")),
            @Result(column = "ticket_day_num", property = "ticketDayNum", jdbcType = JdbcType.INTEGER)
    })
    List<DoctorUserVo> selectAll();

    @Select("SELECT * " + "FROM entity_doctor " + "WHERE department_code = #{departmentCode,jdbcType=VARCHAR}")
    @Results({
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "employee_id", property = "employeeId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_name", property = "doctorName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_professional_title", property = "doctorProfessionalTitle", jdbcType = JdbcType.VARCHAR),
            @Result(column = "doctor_introduction", property = "doctorIntroduction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR,one = @One(select = "com.gxf.his.mapper.DepartmentMapper.selectByDepartmentCode")),
            @Result(column = "scheduling_id", property = "scheduling", jdbcType = JdbcType.BIGINT,one = @One(select = "com.gxf.his.mapper.SchedulingMapper.selectByPrimaryKey")),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT,one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey")),
            @Result(column = "ticket_day_num", property = "ticketDayNum", jdbcType = JdbcType.INTEGER)
    })
    List<DoctorUserVo> selectDoctorByDepartmentCode(String departmentCode);


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
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR,one = @One(select = "com.gxf.his.mapper.DepartmentMapper.selectByDepartmentCode")),
            @Result(column = "scheduling_id", property = "schedulingId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT,one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey")),
            @Result(column = "ticket_day_num", property = "ticketDayNum", jdbcType = JdbcType.INTEGER)
    })
    List<DoctorUserVo> selectDoctorByAtribute(DoctorUserVo doctorUserVo);


    @Update({"update entity_doctor", "set employee_id = #{employeeId,jdbcType=VARCHAR},", "doctor_name = " +
            "#{doctorName,jdbcType=VARCHAR},",
            "doctor_professional_title = #{doctorProfessionalTitle," + "jdbcType" + "=VARCHAR},",
            "doctor_introduction = #{doctorIntroduction,jdbcType=VARCHAR},", "department_code =" + " " +
            "#{departmentCode,jdbcType=VARCHAR},", "scheduling_id = #{schedulingId,jdbcType=BIGINT},",
            "user_id = " + "#{userId,jdbcType=BIGINT},", "ticket_day_num = #{ticketDayNum,jdbcType=INTEGER}", "where "
            + "doctor_id = " + "#{doctorId,jdbcType=BIGINT}"})
    int updateByPrimaryKey(Doctor record);
}