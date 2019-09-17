package com.liu.generator.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.liu.generator.entity.MyBatisPlusConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyBatisPlusUtil {
    @Autowired
   MyBatisPlusConfigEntity myBatisPlusConfigEntity;

    //执行生成
    public void init(){
        autoGenerator().execute();
    }
    /**
     * 将以下配置进行整个
     * @return AutoGenerator
     */
    public AutoGenerator autoGenerator(){
        AutoGenerator autoGenerator=new AutoGenerator();
        // 注入全局设置
        autoGenerator.setGlobalConfig(setGlobalConfig());
        // 注入数据源配置
        autoGenerator.setDataSource(setDataSourceConfig());
        // 注入策略配置
        autoGenerator.setStrategy(setStrategyConfig());
        // 注入包名信息配置
        autoGenerator.setPackageInfo(setPackageConfig());
        // 注入自定义模板配置
//		autoGenerator.setTemplate(templateConfig());
        // 定义模板引擎
        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
        //注入自定义输出配置(3.1.2必须添加此配置)
        autoGenerator.setCfg(injectionConfig());
        return autoGenerator;
    }
    /**
     * 全局配置
     * @return GlobalConfig
     */
    private GlobalConfig setGlobalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();
        // 是否支ar模式
        globalConfig.setActiveRecord(myBatisPlusConfigEntity.getActiveRecord());
        // 作者信息
        globalConfig.setAuthor(myBatisPlusConfigEntity.getAuthor());
        // 生成路径
        globalConfig.setOutputDir(myBatisPlusConfigEntity.getProjectPath()+"src/main/java/");
        // 文件覆盖
        globalConfig.setFileOverride(myBatisPlusConfigEntity.getFileOverride());
        // 创建后是否打开目录
        globalConfig.setOpen(myBatisPlusConfigEntity.getOpen());
        // 是否创建Swagger文档
        globalConfig.setSwagger2(myBatisPlusConfigEntity.getSwagger2());
        //生成基本结果映射文件 resultMap
        globalConfig.setBaseResultMap(myBatisPlusConfigEntity.getBaseResultMap());
        //生成基本sql片段
        globalConfig.setBaseColumnList(myBatisPlusConfigEntity.getBaseColumnList());
        // XML 二级缓存
        globalConfig.setEnableCache(myBatisPlusConfigEntity.getEnableCache());
        // 主键id策略
        // globalConfig.setIdType(IdType.AUTO);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // 实体类名
		globalConfig.setEntityName("%s");
        // Controlller名
        globalConfig.setControllerName("%sController");
        // Service名
        globalConfig.setServiceName("%sService");
        // ServiceImpl名
        globalConfig.setServiceImplName("%sServiceImpl");
        // Mapper名
        globalConfig.setMapperName("%sMapper");
        // XML名
        globalConfig.setXmlName("%sMapper");
        return globalConfig;
    }
    /**
     *  数据源配置
     * @return DataSourceConfig
     */
    private DataSourceConfig setDataSourceConfig(){
        DataSourceConfig sourceConfig = new DataSourceConfig();
        sourceConfig .setDbType(DbType.MYSQL);
        //数据库
        sourceConfig.setDriverName(myBatisPlusConfigEntity.getDriverName());
        sourceConfig.setUsername(myBatisPlusConfigEntity.getUsername());
        sourceConfig.setPassword(myBatisPlusConfigEntity.getPassword());
        sourceConfig.setUrl(myBatisPlusConfigEntity.getUrl());
        return sourceConfig;
    }
    /**
     *  策略配置
     * @return StrategyConfig
     */
    private StrategyConfig setStrategyConfig(){
        StrategyConfig strategyConfig = new StrategyConfig();
        // 全局大写命名
        strategyConfig.setCapitalMode(true);
        // 表名生成策略(下划线转驼峰命名) 驼峰命名NamingStrategy.underline_to_camel
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 列名生成策略(下划线转驼峰命名)
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 是否启动Lombok配置
        strategyConfig.setEntityLombokModel(myBatisPlusConfigEntity.getEntityLombokModel());
        // 是否启动REST风格配置
        strategyConfig.setRestControllerStyle(myBatisPlusConfigEntity.getRestControllerStyle());
        // 应该是版本号
        strategyConfig.setVersionFieldName(myBatisPlusConfigEntity.getVersionFieldName());
        // 此处可以修改为您的表前缀
        strategyConfig.setTablePrefix(myBatisPlusConfigEntity.getTablePrefix());
        // 此处可以修改为您的表列前缀
        strategyConfig.setFieldPrefix(myBatisPlusConfigEntity.getFieldPrefix());
        // 生成实体时是否生成字段注解
        strategyConfig.setEntityTableFieldAnnotationEnable(myBatisPlusConfigEntity.getEntityTableFieldAnnotationEnable());
        // 需要生成的表
        strategyConfig.setInclude(myBatisPlusConfigEntity.getTables());
        return strategyConfig;
    }
    /**
     *  包设置
     * @return PackageConfig
     */
    private PackageConfig setPackageConfig(){
        PackageConfig packageConfig = new PackageConfig();
        //模块名
        packageConfig.setModuleName(myBatisPlusConfigEntity.getModuleName());
        //公共包名
        packageConfig.setParent(myBatisPlusConfigEntity.getPackageName());
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("mapper");
        packageConfig.setEntity("entity");
//		packageConfig.setXml("mapper.xml");
        return packageConfig;
    }

    /**
     *  设置自定义模板
     * @return TemplateConfig
     */
    private TemplateConfig templateConfig(){
        TemplateConfig templateConfig=new TemplateConfig();
        //.setXml(null)//指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
        // 注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
        templateConfig.setController("/templates/controller.java");
        templateConfig.setEntity("/templates/entity.java");
        templateConfig.setMapper("/templates/mapper.java");
        templateConfig.setXml("/templates/mapper.xml");
        templateConfig.setService("/templates/service.java");
        templateConfig.setServiceImpl("/templates/serviceImpl.java");
        return templateConfig;
    }

    /**
     *  自定义配置
     * @return InjectionConfig
     */
    private InjectionConfig injectionConfig(){
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//		String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return myBatisPlusConfigEntity.getProjectPath() + "src/main/resources/mapper/"
                        + myBatisPlusConfigEntity.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        return cfg;
    }
}
