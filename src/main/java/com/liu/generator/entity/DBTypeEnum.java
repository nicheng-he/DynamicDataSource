package com.liu.generator.entity;

import lombok.Getter;

/**
 * 暂时不使用,后期加上
 * 目标数据源注解，注解在方法上指定数据源的名称
 */
@Getter
public enum DBTypeEnum {
    DB1("db1"),
    DB2("db2");

    DBTypeEnum(String value) {
        this.value = value;
    }

    private String value;
}