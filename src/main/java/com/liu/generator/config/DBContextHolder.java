package com.liu.generator.config;

import com.liu.generator.entity.DBTypeEnum;

/**
 * 暂时不使用,后期加上
 * 动态数据源持有者，负责利用ThreadLocal存取数据源名称
 */
public class DBContextHolder {
	/**
	 * 本地线程共享对象
	 */
    private static final ThreadLocal contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源
     * @param dbTypeEnum
     */
    public static void setDataSource(DBTypeEnum dbTypeEnum) {
        contextHolder.set(dbTypeEnum.getValue());
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDataSource() {
        return (String) contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear() {
        contextHolder.remove();
    }
}