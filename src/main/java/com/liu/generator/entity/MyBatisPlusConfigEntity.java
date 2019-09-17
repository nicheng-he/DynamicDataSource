package com.liu.generator.entity;

import com.liu.generator.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = "classpath:myBatisPlus.yml", encoding = "UTF-8", factory = YamlPropertySourceFactory.class)
@SuppressWarnings("all")
@ConfigurationProperties
public class MyBatisPlusConfigEntity {
    private transient final static String prefix="spring.datasource.db1.";

    @Value("${" + prefix + "driver-class-name}")
    private String driverName;
    @Value("${" + prefix + "username}")
    private String username;
    @Value("${" + prefix + "password}")
    private String password;
    @Value("${" + prefix + "url}")
    private String url;
    // 生成路径
    private String projectPath;
    // 作者
    private String author;
    // 模块名
    private String moduleName;
    // 版本号
    private String versionFieldName;
    // 是否支ar模式
    private Boolean activeRecord;
    // 是否覆盖文件
    private Boolean fileOverride;
    // 数据库表统一前缀
    private String[] tablePrefix;
    //数据库列统一前缀
    private String[] fieldPrefix;
    // 包名统一前缀
    private String packageName;
    // 要生成的表名
    private String[] tables;
    // 创建完成后是否打开目录
    private Boolean open;
    // 是否生成swagger文档注释
    private Boolean swagger2;
    // 是否生成基本结果映射 resultMap
    private Boolean baseResultMap;
    // 是否生成基本sql片段
    private Boolean baseColumnList;
    // 是否开启XML 二级缓存
    private Boolean enableCache;
    // 是否启动Lombok配置
    private Boolean entityLombokModel;
    // 是否启动REST风格配置
    private Boolean restControllerStyle;
    // 生成实体时是否生成字段注解
    private Boolean entityTableFieldAnnotationEnable;

}
