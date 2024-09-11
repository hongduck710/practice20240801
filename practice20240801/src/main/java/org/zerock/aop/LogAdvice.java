package org.zerock.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import lombok.extern.log4j.Log4j;

@Aspect // Aspect어노테이션: 해당 클래스의 객체가 Aspect를 구현한 것임을 나타내기 위해 사용
@Log4j 
@Component // 스프링에서 빈으로 인식하기 위해 @Component 어노테이션 사용
public class LogAdvice {
	@Before( "execution(* org.zerock.service.SampleService*.*(..))") //Befofe어노테이션의 문자열은 AsprctJ의 표현식
	//  @After, @AfterReturning, @AfterThrowing, @Around 역시 동일한 방식으로 적용
	public void logBefore() {
		log.info("🍀🍀🍀🍀🍀🍀🍀🍀🌷🌷🌷🌷🌷🌷");
	}
	
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1: " + str1);
		log.info("str2: " + str2);
	}
	
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		log.info("Exception...!!!!!!");
		log.info("exception: " + exception);
	}
	
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		
		long start = System.currentTimeMillis();
		
		log.info("타겟(Target): " + pjp.getTarget());
		log.info("파람(Param): " + Arrays.toString(pjp.getArgs()));
		
		// invoke method
		Object result = null;
		
		try {
			result = pjp.proceed();
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("타임(TIME): " + (end - start));
		
		return result;
	}
	
	
	
	
	
}
