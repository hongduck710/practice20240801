/*
 * package org.zerock.config;
 * 
 * import javax.sql.DataSource;
 * 
 * import org.apache.ibatis.session.SqlSessionFactory; import
 * org.mybatis.spring.SqlSessionFactoryBean; import
 * org.mybatis.spring.annotation.MapperScan; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.ComponentScan;
 * 
 * import com.zaxxer.hikari.HikariConfig; import
 * com.zaxxer.hikari.HikariDataSource;
 * 
 * import org.springframework.context.annotation.EnableAspectJAutoProxy; import
 * org.springframework.scheduling.annotation.EnableScheduling; import
 * org.springframework.transaction.annotation.EnableTransactionManagement;
 * import org.springframework.jdbc.datasource.DataSourceTransactionManager;
 * 
 * /* JAVA 설정은 학습용으로 작성. 실제로 쓰이지 않음. 불필요시 삭제 가능
 * 
 * @ComponentScan({"org.zerock.sample"})
 * 
 * @Configuration
 * 
 * @ComponentScan(basePackages = {"org.zerock.service"})
 * 
 * @ComponentScan(basePackages = "org.zerock.aop")
 * 
 * @ComponentScan(basePackages = "org.zerock.task")
 * 
 * @EnableAspectJAutoProxy
 * 
 * @EnableScheduling
 * 
 * @EnableTransactionManagement
 * 
 * @MapperScan(basePackages = {"org.zerock.mapper"}) public class RootConfig {
 * 
 * @Bean public DataSource dataSource() { HikariConfig hikariConfig = new
 * HikariConfig();
 * hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
 * hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:XE");
 * 
 * hikariConfig.setUsername("COMMI"); hikariConfig.setPassword("COMMI");
 * 
 * HikariDataSource dataSource = new HikariDataSource(hikariConfig);
 * 
 * return dataSource; } // dataSource닫음
 * 
 * @Bean public SqlSessionFactory sqlSessionFactory() throws Exception {
 * SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
 * sqlSessionFactory.setDataSource(dataSource()); return (SqlSessionFactory)
 * sqlSessionFactory.getObject(); } // sqlSessionFactory 닫음
 * 
 * @Bean public DataSourceTransactionManager txManager() { return new
 * DataSourceTransactionManager(dataSource()); }
 * 
 * }
 */