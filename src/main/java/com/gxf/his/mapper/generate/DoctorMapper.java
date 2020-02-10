package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.Doctor;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DoctorMapper {
    @Delete({
        "delete from entity_doctor",
        "where doctor_id = #{doctorId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long doctorId);

    @Insert({
        "insert into entity_doctor (doctor_id, employee_id, ",
        "doctor_name, doctor_professional_title, ",
        "doctor_introduction, department_code, ",
        "scheduling_id, user_id, ",
        "ticket_day_num, ticket_price, ",
        "ticket_current_num)",
        "values (#{doctorId,jdbcType=BIGINT}, #{employeeId,jdbcType=VARCHAR}, ",
        "#{doctorName,jdbcType=VARCHAR}, #{doctorProfessionalTitle,jdbcType=VARCHAR}, ",
        "#{doctorIntroduction,jdbcType=VARCHAR}, #{departmentCode,jdbcType=VARCHAR}, ",
        "#{schedulingId,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{ticketDayNum,jdbcType=INTEGER}, #{ticketPrice,jdbcType=DECIMAL}, ",
        "#{ticketCurrentNum,jdbcType=INTEGER})"
    })
    int insert(Doctor record);

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
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="scheduling_id", property="schedulingId", jdbcType=JdbcType.BIGINT),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="ticket_day_num", property="ticketDayNum", jdbcType=JdbcType.INTEGER),
        @Result(column="ticket_price", property="ticketPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="ticket_current_num", property="ticketCurrentNum", jdbcType=JdbcType.INTEGER)
    })
    Doctor selectByPrimaryKey(Long doctorId);

    @Select({
        "select",
        "doctor_id, employee_id, doctor_name, doctor_professional_title, doctor_introduction, ",
        "department_code, scheduling_id, user_id, ticket_day_num, ticket_price, ticket_current_num",
        "from entity_doctor"
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
    List<Doctor> selectAll();

    @Update({
        "update entity_doctor",
        "set employee_id = #{employeeId,jdbcType=VARCHAR},",
          "doctor_name = #{doctorName,jdbcType=VARCHAR},",
          "doctor_professional_title = #{doctorProfessionalTitle,jdbcType=VARCHAR},",
          "doctor_introduction = #{doctorIntroduction,jdbcType=VARCHAR},",
          "department_code = #{departmentCode,jdbcType=VARCHAR},",
          "scheduling_id = #{schedulingId,jdbcType=BIGINT},",
          "user_id = #{userId,jdbcType=BIGINT},",
          "ticket_day_num = #{ticketDayNum,jdbcType=INTEGER},",
          "ticket_price = #{ticketPrice,jdbcType=DECIMAL},",
          "ticket_current_num = #{ticketCurrentNum,jdbcType=INTEGER}",
        "where doctor_id = #{doctorId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Doctor record);
}