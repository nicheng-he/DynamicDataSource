package com.liu.generator.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.liu.generator.entity.DBTypeEnum;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 暂时不使用,后期加上
 * @author liuyonghe
 * @version 1.0
 * @date 2019/8/27 10:33
 */
@Configuration
public class DataSourceConfig {
	@Bean(name = "db1")
	@ConfigurationProperties(prefix = "spring.datasource.db1")
	public DataSource db1() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "db2")
	@ConfigurationProperties(prefix = "spring.datasource.db2")
	public DataSource db2() {
		return DruidDataSourceBuilder.create().build();
	}

//	@Bean(name = "db1")
//	@ConfigurationProperties(prefix="spring.datasource.db1")
//	public DataSource db1(){
//		return DataSourceBuilder.create().build();
//	}
//	@Bean(name = "db2")
//	@ConfigurationProperties(prefix="spring.datasource.db2")
//	public DataSource db2(){
//		return DataSourceBuilder.create().build();
//	}

	@Bean("multipleDataSource")
	@Primary
	public DataSource multipleDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		Map< Object, Object > targetDataSources = new HashMap<>();
		targetDataSources.put(DBTypeEnum.DB1.getValue(), db1());
		targetDataSources.put(DBTypeEnum.DB2.getValue(), db2());
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.setDefaultTargetDataSource(db1());
		return dynamicDataSource;
	}
	@Bean("sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(multipleDataSource());

		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setCacheEnabled(false);
		sqlSessionFactory.setConfiguration(configuration);

		sqlSessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver()
						.getResources("classpath*:/mapper/*Mapper.xml")
		);
		sqlSessionFactory.setTypeAliasesPackage("com.liu.entity");
		//PerformanceInterceptor(),OptimisticLockerInterceptor()
		//添加分页功能
		sqlSessionFactory.setPlugins(new Interceptor[]{
				new PaginationInterceptor()
		});
        sqlSessionFactory.setGlobalConfig(globalConfiguration());
		return sqlSessionFactory.getObject();
	}

	private GlobalConfig globalConfiguration(){
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setBanner(false);
		GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
		dbConfig.setIdType(IdType.AUTO);
		globalConfig.setDbConfig(dbConfig);

		return globalConfig;
	}
}