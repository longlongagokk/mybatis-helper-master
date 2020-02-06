package com.vitily.order.api.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;

/**
 * creator : whh-lether
 * date    : 2019/6/26 18:37
 * desc    :
 **/
@Configuration
@EnableAsync
@ComponentScan({"com.vitily.*.service"})
@MapperScan(basePackages = {"com.vitily.*.mapper"})
public class OrderConfiguration {
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(datasource);
        return sessionFactory.getObject();
    }
}
