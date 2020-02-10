package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.CheckItem;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface CheckItemMapper {
    @Delete({
        "delete from entity_check_item",
        "where check_item_id = #{checkItemId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long checkItemId);

    @Insert({
        "insert into entity_check_item (check_item_id, `name`, ",
        "`type`, price, cost)",
        "values (#{checkItemId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, #{cost,jdbcType=DECIMAL})"
    })
    int insert(CheckItem record);

    @Select({
        "select",
        "check_item_id, `name`, `type`, price, cost",
        "from entity_check_item",
        "where check_item_id = #{checkItemId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="check_item_id", property="checkItemId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="cost", property="cost", jdbcType=JdbcType.DECIMAL)
    })
    CheckItem selectByPrimaryKey(Long checkItemId);

    @Select({
        "select",
        "check_item_id, `name`, `type`, price, cost",
        "from entity_check_item"
    })
    @Results({
        @Result(column="check_item_id", property="checkItemId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="cost", property="cost", jdbcType=JdbcType.DECIMAL)
    })
    List<CheckItem> selectAll();

    @Update({
        "update entity_check_item",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "`type` = #{type,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DECIMAL},",
          "cost = #{cost,jdbcType=DECIMAL}",
        "where check_item_id = #{checkItemId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CheckItem record);
}