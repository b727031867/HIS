package com.gxf.his.mapper;

import com.gxf.his.po.Cashier;
import com.gxf.his.vo.CashierUserVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface CashierMapper {
    @Delete({
        "delete from entity_cashier",
        "where cashier_id = #{cashierId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long cashierId);

    /**
     * 批量删除收银员
     * @param cashiers 收银员列表
     * @return 影响的行数
     */
    @DeleteProvider(type = Batch.class, method = "batchCashierDelete")
    Integer batchCashierDelete(List<Cashier> cashiers);


    @Insert({
        "insert into entity_cashier (cashier_id, `name`, ",
        "phone, entry_date, ",
        "department_code, user_id)",
        "values (#{cashierId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{phone,jdbcType=VARCHAR}, #{entryDate,jdbcType=TIMESTAMP}, ",
        "#{departmentCode,jdbcType=VARCHAR}, #{userId,jdbcType=BIGINT})"
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
        @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.TIMESTAMP),
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
            @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="department", property="departmentCode", jdbcType=JdbcType.VARCHAR, one = @One(select = "com.gxf.his.mapper.DepartmentMapper.selectByDepartmentCode")),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey"))
    })
    List<CashierUserVo> selectAll();

    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_cashier "
            + "<where> "
            + "<if test='searchAttribute == \"name\" '>"
            + " name like CONCAT('%',#{value},'%')  "
            + "</if> "
            + "<if test='searchAttribute == \"phone\" '> "
            + " AND phone like  CONCAT('%',#{value},'%') "
            + "</if>"
            + "</where> "
            + "</script>")
    @Results({
            @Result(column="cashier_id", property="cashierId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="department_code", property="department", jdbcType=JdbcType.VARCHAR, one = @One(select = "com.gxf.his.mapper.DepartmentMapper.selectByDepartmentCode")),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey"))
    })
    List<CashierUserVo> selectCashierByAttribute(CashierUserVo cashierUserVo);

    @Select("<script>"
            + "SELECT "
            + " * "
            + "FROM entity_cashier "
            + "<where> "
            + "<if test='searchAttribute == \"name\" '>"
            + " name = #{value} "
            + "</if> "
            + "<if test='searchAttribute == \"phone\" '> "
            + " AND phone = #{value} "
            + "</if>"
            + "</where> "
            + "</script>")
    @Results({
            @Result(column="cashier_id", property="cashierId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="department_code", property="department", jdbcType=JdbcType.VARCHAR, one = @One(select = "com.gxf.his.mapper.DepartmentMapper.selectByDepartmentCode")),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = "com.gxf.his.mapper.UserMapper.selectByPrimaryKey"))
    })
    List<CashierUserVo> selectCashierByAccurateAttribute(CashierUserVo cashierUserVo);

    @Update({
        "update entity_cashier",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "entry_date = #{entryDate,jdbcType=TIMESTAMP},",
          "department_code = #{departmentCode,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=BIGINT}",
        "where cashier_id = #{cashierId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Cashier record);
}