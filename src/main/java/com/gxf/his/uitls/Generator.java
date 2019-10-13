package com.gxf.his.uitls;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mybaties逆向改成生成类
 * @author GXF
 * @date 2019-10-13
 **/
public class Generator {
    public static void main(String[] args){
        List<String> warnings = new ArrayList<>();
        String genCfg = "/generatorConfig.xml";
        File configFile = new File(Generator.class.getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException | XMLParserException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = null;
        try {
            if(config!=null){
                myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            }else {
                System.out.println("config文件读取失败");
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            if(myBatisGenerator!=null){
                myBatisGenerator.generate(null);
            }else {
                System.out.println("MyBatisGenerator初始化失败");
            }
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}