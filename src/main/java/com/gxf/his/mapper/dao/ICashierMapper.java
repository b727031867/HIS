package com.gxf.his.mapper.dao;

import com.gxf.his.mapper.Batch;
import com.gxf.his.mapper.MapperConst;
import com.gxf.his.mapper.generate.CashierMapper;
import com.gxf.his.po.generate.Cashier;
import com.gxf.his.po.generate.Patient;
import com.gxf.his.po.vo.CashierVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author 龚秀峰
 * 收银员Mapper
 */
public interface ICashierMapper extends CashierMapper {

    /**
     * 根据UID查询对应收银员的信息
     * @param uid 用户ID
     * @return 收银员对象
     */
    @Select({
            "select",
            "*",
            "from entity_cashier",
            "where user_id = #{uid,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="cashier_id", property="cashierId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.DATE),
            @Result(column="department_code", property="departmentCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
            @Result(column="user_id", property="user", jdbcType=JdbcType.BIGINT,one = @One(select = MapperConst.ONE_GENERATE_USER)),
    })
    CashierVo selectByUid(Long uid);

    /**
     * 批量删除收银员
     *
     * @param cashiers 收银员列表
     * @return 影响的行数
     */
    @DeleteProvider(type = Batch.class, method = "batchCashierDelete")
    Integer batchCashierDelete(List<Cashier> cashiers);

    /**
     * 根据不同属性模糊查询收银员，比如姓名、手机号
     *
     * @param cashierVo 收银员业务类
     * @return 收银员列表
     */
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
            @Result(column = "cashier_id", property = "cashierId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "entry_date", property = "entryDate", jdbcType = JdbcType.DATE),
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR, one = @One(select = MapperConst.ONE_DEPARTMENT_BY_DEPARTMENT_CODE)),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER))
    })
    List<CashierVo> selectCashierByAttribute(CashierVo cashierVo);

    /**
     * 根据不同属性精确查询收银员，比如姓名、手机号
     *
     * @param cashierVo 收银员业务类
     * @return 收银员列表
     */
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
            @Result(column = "cashier_id", property = "cashierId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "entry_date", property = "entryDate", jdbcType = JdbcType.DATE),
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR, one = @One(select = MapperConst.ONE_DEPARTMENT_BY_DEPARTMENT_CODE)),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER))
    })
    List<CashierVo> selectCashierByAccurateAttribute(CashierVo cashierVo);

    /**
     * 查询所有收银员的所有信息
     *
     * @return 收银员业务类列表
     */
    @Select({
            "select",
            "cashier_id, `name`, phone, entry_date, department_code, user_id",
            "from entity_cashier"
    })
    @Results({
            @Result(column = "cashier_id", property = "cashierId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "entry_date", property = "entryDate", jdbcType = JdbcType.DATE),
            @Result(column = "department_code", property = "department", jdbcType = JdbcType.VARCHAR, one = @One(select = MapperConst.ONE_DEPARTMENT_BY_DEPARTMENT_CODE)),
            @Result(column = "user_id", property = "user", jdbcType = JdbcType.BIGINT, one = @One(select = MapperConst.ONE_GENERATE_USER))
    })
    List<CashierVo> selectAllCashierInfo();
}