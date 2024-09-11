package org.zerock.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) /*현재 테스트 코드가 스프링을 실행하는 역할을 할 것이라는 것을 런위드 어노테이션으로 표시 */
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
/* 컨텍스트 컨피겨레이션 \: 지정된 클래스나 문자열을 이용해서 필요한 객체들을 스프링 내에 객체로 등록함:스프링의 빈으로 등록한다고 표현*/
@Log4j
public class SampleTests {
	
	@Setter(onMethod_ = { @Autowired })
	private Restaurant restaurant;
	
	@Test
	public void testExist() {
		assertNotNull(restaurant); /* restaurant 변수가 null이 아니어야만 테스트가 성공한다는 것을 의미함. */
		
		log.info(restaurant);
		log.info("✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨");
		log.info(restaurant.getChef());
	
	}

}
