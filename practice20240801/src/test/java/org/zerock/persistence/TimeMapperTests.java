package org.zerock.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.mapper.TimeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

// Java 설정의 경우
// @ContextConfiguration({ org.zerock.config.RootConfig.class })

@Log4j
public class TimeMapperTests { //TimeMapper 클래스가 정장적으로 사용이 가능한지 알아보기 위한 테스트 코드
	// 코드가 정상적으로 존재한다면 스프링 내부에는 TimeMapper 타입으로 만들어진 스프링 객체(빈)가 존재한다는 뜻이다.
	
	@Setter(onMethod_ = @Autowired)
	private TimeMapper timeMapper;
	
	@Test
	public void testGetTime() {
		log.info("✨✨✨✨" + timeMapper.getClass().getName());/* 실제로 동작하는 클래스의 이름을 확인해줌 */
		log.info("✨✨✨✨ timeMapper.getTime은??: " + timeMapper.getTime());
	}
	
	@Test
	public void testGetTime2() {
		log.info("✨✨✨✨ getTime2");
		log.info("✨✨✨✨ timeMapper.getTime2()??: " + timeMapper.getTime2());
	}
	
}
