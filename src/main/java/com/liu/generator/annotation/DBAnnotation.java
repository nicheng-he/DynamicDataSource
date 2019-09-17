package com.liu.generator.annotation;

import com.liu.generator.entity.DBTypeEnum;

import java.lang.annotation.*;

/**
 * 暂时不使用,后期加上
 * 自定义数据源切换注解
 * @author lyh
 */
@Target({ElementType.METHOD,ElementType.TYPE})// 定义注解的作用目标
@Retention(RetentionPolicy.RUNTIME)// 定义注解的保留策略
@Inherited //说明子类可以继承父类中的该注解
@Documented// 定义注解的保留策略
public @interface DBAnnotation {
    DBTypeEnum value() default DBTypeEnum.DB1;
}