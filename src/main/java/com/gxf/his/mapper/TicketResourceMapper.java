package com.gxf.his.mapper;

import com.gxf.his.po.TicketResource;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface TicketResourceMapper {
    @Delete({
            "delete from entity_ticket_resource",
            "where registered_resource_id = #{registeredResourceId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long registeredResourceId);

    @Delete({
            "delete from entity_ticket_resource",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND available_date = #{availableDate,jdbcType=DATE}"
    })
    int deleteByDoctorIdAndAvailableDate(Long doctorId, Date availableDate);

    @Insert({
            "insert into entity_ticket_resource (registered_resource_id, doctor_id, ",
            "`day`, ticket_last_number,available_date)",
            "values (#{registeredResourceId,jdbcType=BIGINT}, #{doctorId,jdbcType=BIGINT}, ",
            "#{day,jdbcType=VARCHAR}, #{ticketLastNumber,jdbcType=INTEGER},#{availableDate,jdbcType=DATE})"
    })
    int insert(TicketResource record);

    @Select({
            "select",
            "registered_resource_id, doctor_id, `day`, ticket_last_number,available_date",
            "from entity_ticket_resource",
            "where registered_resource_id = #{registeredResourceId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "day", property = "day", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_last_number", property = "ticketLastNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "available_date", property = "availableDate", jdbcType = JdbcType.DATE)
    })
    TicketResource selectByPrimaryKey(Long registeredResourceId);

    @Select({
            "select",
            "registered_resource_id, doctor_id, `day`, ticket_last_number,available_date",
            "from entity_ticket_resource",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND (available_date >= #{availableDateStart,jdbcType=DATE} AND available_date <= #{availableDateEnd,jdbcType=DATE} )"
    })
    @Results({
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "day", property = "day", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_last_number", property = "ticketLastNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "available_date", property = "availableDate", jdbcType = JdbcType.DATE)
    })
    List<TicketResource> selectByDoctorIdAndAvailableDate(Long doctorId, Date availableDateStart, Date availableDateEnd);

    @Select({
            "select",
            "available_date",
            "from entity_ticket_resource",
            "order by available_date DESC limit 1"
    })
    @Results({
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "day", property = "day", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_last_number", property = "ticketLastNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "available_date", property = "availableDate", jdbcType = JdbcType.DATE)
    })
    TicketResource selectByMaxAvailableDate();


    @Select({
            "select",
            "registered_resource_id, doctor_id, `day`, ticket_last_number,available_date",
            "from entity_ticket_resource"
    })
    @Results({
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "day", property = "day", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_last_number", property = "ticketLastNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "available_date", property = "availableDate", jdbcType = JdbcType.DATE)
    })
    List<TicketResource> selectAll();

    @Update({
            "update entity_ticket_resource",
            "set doctor_id = #{doctorId,jdbcType=BIGINT},",
            "`day` = #{day,jdbcType=VARCHAR},",
            "ticket_last_number = #{ticketLastNumber,jdbcType=INTEGER},available_date = #{availableDate,jdbcType=DATE}",
            "where registered_resource_id = #{registeredResourceId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TicketResource record);
}