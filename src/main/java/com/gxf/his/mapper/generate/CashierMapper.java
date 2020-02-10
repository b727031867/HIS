package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.Cashier;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface CashierMapper {
    @Delete({
        "delete from entity_cashier",
        "where cashier_id = #{cashierId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long cashierId);

    @Insert({
        "insert into entity_cashier (cashier_id, `name`, ",
        "phone, entry_date, department_code, ",
        "user_id)",
        "values (#{cashierId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{phone,jdbcType=VARCHAR}, #{entryDate,jdbcType=DATE}, #{departmentCode,jdbcType=VARCHAR}, ",
        "#{userId,jdbcType=BIGINT})"
    })
    int insert(Cashier record);

    @Select({
        "select",
        "cashier_id, `name`, phone, entry_date, department_code, user_id",
        "from entity_cashier",
        "where cashier_id = #{cashierId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="cashier_id", property="cashierId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.DATE),
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT)
    })
    Cashier selectByPrimaryKey(Long cashierId);

    @Select({
        "select",
        "cashier_id, `name`, phone, entry_date, department_code, user_id",
        "from entity_cashier"
    })
    @Results({
        @Result(column="cashier_id", property="cashierId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.DATE),
        @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT)
    })
    List<Cashier> selectAll();

    @Update({
        "update entity_cashier",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "entry_date = #{entryDate,jdbcType=DATE},",
          "department_code = #{departmentCode,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=BIGINT}",
        "where cashier_id = #{cashierId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Cashier record);
}