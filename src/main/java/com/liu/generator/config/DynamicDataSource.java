package com.liu.generator.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 暂时不使用,后期加上
 * 动态数据源实现类
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 数据源路由，此方用于产生要选取的数据源逻辑名称
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //从共享线程中获取数据源名称
        return DBContextHolder.getDataSource();
    }
}