package org.zerock.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/* JAVA 설정은 학습용으로 작성. 실제로 쓰이지 않음. 불필요시 삭제 가능 */
@Configuration
@ComponentScan(basePackages = {"org.zerock.service"})
@ComponentScan(basePackages = "org.zerock.aop")
// @ComponentScan(basePackages = "org.zerock.task")
@EnableAspectJAutoProxy
@EnableScheduling
@EnableTransactionManagement

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
