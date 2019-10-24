package com.gxf.his.mapper;

import com.gxf.his.po.Doctor;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DoctorMapper {
    @Delete({
        "delete from entity_doctor",
        "where doctor_id = #{doctorId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer doctorId);

    @Insert({
        "insert into entity_doctor (doctor_id, employee_id, ",
        "doctor_name, doctor_professional_title, ",
        "doctor_introduction, department_code, ",
        "scheduling_id, user_id, ",
        "ticket_day_num)",
        "values (#{doctorId,jdbcType=INTEGER}, #{employeeId,jdbcType=VARCHAR}, ",
        "#{doctorName,jdbcType=VARCHAR}, #{doctorProfessionalTitle,jdbcType=VARCHAR}, ",
        "#{doctorIntroduction,jdbcType=VARCHAR}, #{departmentCode,jdbcType=VARCHAR}, ",
        "#{schedulingId,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{ticketDayNum,jdbcType=INTEGER})"
    })
    int insert(Doctor record);

    @Select({
        "select",
        "doctor_id, employee_id, doctor_name, doctor_professional_title, doctor_introduction, ",
        "department_code, scheduling_id, user_id, ticket_day_num",
        "from entity_doctor",
        "where doctor_id = #{doctorId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="employee_id", property="employeeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="doctor_name", property="doctorName", jdbcType=JdbcType.VARCHAR),
        @Result(column="doctor_professional_title", property="doctorProfessionalTitle", jdbcType=JdbcType.VARCHAR),
        @Result(column="doctor_introduction", property="doctorIntroduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_id", property="schedulingId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="ticket_day_num", property="ticketDayNum", jdbcType=JdbcType.INTEGER)
    })
    Doctor selectByPrimaryKey(Integer doctorId);

    @Select({
        "select",
        "doctor_id, employee_id, doctor_name, doctor_professional_title, doctor_introduction, ",
        "department_code, scheduling_id, user_id, ticket_day_num",
        "from entity_doctor"
    })
    @Results({
        @Result(column="doctor_id", property="doctorId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="employee_id", property="employeeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="doctor_name", property="doctorName", jdbcType=JdbcType.VARCHAR),
        @Result(column="doctor_professional_title", property="doctorProfessionalTitle", jdbcType=JdbcType.VARCHAR),
        @Result(column="doctor_introduction", property="doctorIntroduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_id", property="schedulingId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="ticket_day_num", property="ticketDayNum", jdbcType=JdbcType.INTEGER)
    })
    List<Doctor> selectAll();



    @Update({
        "update entity_doctor",
        "set employee_id = #{employeeId,jdbcType=VARCHAR},",
          "doctor_name = #{doctorName,jdbcType=VARCHAR},",
          "doctor_professional_title = #{doctorProfessionalTitle,jdbcType=VARCHAR},",
          "doctor_introduction = #{doctorIntroduction,jdbcType=VARCHAR},",
          "department_code = #{departmentCode,jdbcType=VARCHAR},",
          "scheduling_id = #{schedulingId,jdbcType=INTEGER},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "ticket_day_num = #{ticketDayNum,jdbcType=INTEGER}",
        "where doctor_id = #{doctorId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Doctor record);
}