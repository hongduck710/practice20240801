package org.zerock.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component
/* @Component는 스프링에게 해당 클래스가 스프링에서 관리해야 할 대상임을 표시하는 어노테이션 */
@Data 
/* Lombok의 setter를 생성하는 기능과 생성자, toString() 등을 자동으로 생성하도록 @Data 어노테이션을 이용 */
public class Restaurant {
	
	 @Setter(onMethod_ = @Autowired) /* Setter는 자동으로 setChef() 컴파일 시 생성,
	 Setter에 사용된 onMethod 속성은 생성되는 setChef()에 @Autowired 어노테이션 추가하도록 함.  */
	 private Chef chef;
	 
}
