package com.gxf.his.mapper.generate;

import com.gxf.his.po.generate.DrugToxicology;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface DrugToxicologyMapper {
    @Delete({
        "delete from entity_drug_toxicology",
        "where toxicology_id = #{toxicologyId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long toxicologyId);

    @Insert({
        "insert into entity_drug_toxicology (toxicology_id, toxicology_name, ",
        "toxicology_code)",
        "values (#{toxicologyId,jdbcType=BIGINT}, #{toxicologyName,jdbcType=VARCHAR}, ",
        "#{toxicologyCode,jdbcType=VARCHAR})"
    })
    int insert(DrugToxicology record);

    @Select({
        "select",
        "toxicology_id, toxicology_name, toxicology_code",
        "from entity_drug_toxicology",
        "where toxicology_id = #{toxicologyId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="toxicology_id", property="toxicologyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="toxicology_name", property="toxicologyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="toxicology_code", property="toxicologyCode", jdbcType=JdbcType.VARCHAR)
    })
    DrugToxicology selectByPrimaryKey(Long toxicologyId);

    @Select({
        "select",
        "toxicology_id, toxicology_name, toxicology_code",
        "from entity_drug_toxicology"
    })
    @Results({
        @Result(column="toxicology_id", property="toxicologyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="toxicology_name", property="toxicologyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="toxicology_code", property="toxicologyCode", jdbcType=JdbcType.VARCHAR)
    })
    List<DrugToxicology> selectAll();

    @Update({
        "update entity_drug_toxicology",
        "set toxicology_name = #{toxicologyName,jdbcType=VARCHAR},",
          "toxicology_code = #{toxicologyCode,jdbcType=VARCHAR}",
        "where toxicology_id = #{toxicologyId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DrugToxicology record);
}