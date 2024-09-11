package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 모든 속성을 상용하는 생성자를 위한
@NoArgsConstructor // 비어있는 생성자를 만들기 위한
public class SampleVO { //전달된 객체를 생성하기 위해서
	
	private Integer mno;
	private String firstName;
	private String lastName;

}
