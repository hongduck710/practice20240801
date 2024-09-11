package org.zerock.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/* JAVA 설정은 학습용으로 작성. 실제로 쓰이지 않음. 불필요시 삭제 가능 */
@Configuration
@ComponentScan(basePackages = {"org.zerock.service"})
@ComponentScan(basePackages = "org.zerock.aop")
@EnableAspectJAutoProxy

@MapperScan(basePackages = {"org.zerock.mapper"})
public class RootConfig {
	
	/*
	 * @Bean public DataSource dataSource() { return "어쩌구"; }
	 * 
	 * @Bean public SqlSessionFactory sqlSessionFactory() throws Exception{
	 * 
	 * SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
	 * sqlSessionFactory.setDataSource(dataSource()); return "어쩌구"; }
	 */
}
