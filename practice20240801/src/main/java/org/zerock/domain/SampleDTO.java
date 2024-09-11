package org.zerock.domain;

import lombok.Data;

@Data /* SampleDTO 클래스는 Lombok의 @Data 어노테이션을 이용해서 처리.-
setter/getter, equals(), toString() 등의 메서드를 자동 생성 */
public class SampleDTO {
	private String name;
	private int age;
}
