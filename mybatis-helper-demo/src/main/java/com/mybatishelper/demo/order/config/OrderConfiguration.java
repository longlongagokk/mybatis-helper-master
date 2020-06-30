package com.mybatishelper.demo.order.config;

import com.mybatishelper.core.config.YourConfiguration;
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
 * creator : whh
 * date    : 2019/6/26 18:37
 * desc    :
 **/
@Configuration
@EnableAsync
@ComponentScan({"com.mybatishelper.demo.*.service"})
@MapperScan(basePackages = {"com.mybatishelper.demo.*.mapper"})
public class OrderConfiguration {
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(datasource);
        YourConfiguration conf = new YourConfiguration();
        conf.setUseGeneratedKeys(true);//使用自增长id
        sessionFactory.setConfiguration(conf);
        return sessionFactory.getObject();
    }
}
