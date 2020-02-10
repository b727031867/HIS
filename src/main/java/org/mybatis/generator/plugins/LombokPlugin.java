package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/9 23:42
 * @description mybatis generator 整合 Lombok的插件 需要单独编译然后添加到mybatis-generator-core包中plugins内
 */
public class LombokPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //添加domain的import
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("lombok.Builder");
        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        topLevelClass.addImportedType("lombok.AllArgsConstructor");

        //添加domain的注解
        topLevelClass.addAnnotation("@Data");

        return true;
    }


    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成getter
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成setter
        return false;
    }

}
