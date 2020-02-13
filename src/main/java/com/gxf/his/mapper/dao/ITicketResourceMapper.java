package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.generate.DoctorTicketResourceMapper;
import com.gxf.his.po.generate.DoctorTicketResource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * 票务资源的DAO接口
 */
public interface ITicketResourceMapper extends DoctorTicketResourceMapper {

    /**
     * 根据医生ID和有效日期删除票务资源
     *
     * @param doctorId      医生ID
     * @param availableDate 有效日期
     * @return 影响的行数
     */
    @Delete({
            "delete from entity_doctor_ticket_resource",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND available_date = #{availableDate,jdbcType=DATE}"
    })
    int deleteByDoctorIdAndAvailableDate(Long doctorId, Date availableDate);

    /**
     * 获取某位医生未过期的票务资源
     *
     * @param doctorId           医生ID
     * @param availableDateStart 放票时间
     * @param availableDateEnd   过期时间
     * @return 票务资源列表
     */
    @Select({
            "select",
            "registered_resource_id, doctor_id, `day`, ticket_last_number,available_date",
            "from entity_doctor_ticket_resource",
            "where doctor_id = #{doctorId,jdbcType=BIGINT} AND (available_date >= #{availableDateStart,jdbcType=DATE} AND available_date <= #{availableDateEnd,jdbcType=DATE} )"
    })
    @Results({
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "day", property = "day", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_last_number", property = "ticketLastNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "available_date", property = "availableDate", jdbcType = JdbcType.DATE)
    })
    List<DoctorTicketResource> selectByDoctorIdAndAvailableDate(Long doctorId, Date availableDateStart, Date availableDateEnd);

    /**
     * 获取当前票务资源中最长的有效期
     * 比如 今天周一00：00：00放票 则当前最长的有效期为周日23：59：59
     * 因为最多只能提前七天订票
     *
     * @return 票务资源
     */
    @Select({
            "select",
            "available_date",
            "from entity_doctor_ticket_resource",
            "order by available_date DESC limit 1"
    })
    @Results({
            @Result(column = "registered_resource_id", property = "registeredResourceId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "doctor_id", property = "doctorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "day", property = "day", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ticket_last_number", property = "ticketLastNumber", jdbcType = JdbcType.INTEGER),
            @Result(column = "available_date", property = "availableDate", jdbcType = JdbcType.DATE)
    })
    DoctorTicketResource selectByMaxAvailableDate();

}