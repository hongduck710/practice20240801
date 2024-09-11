package org.zerock.sample;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;


@Component
@ToString
@Getter
public class SampleHotel {
	
	/* @Autowired 어노테이션이 없이 처리 */
	private Chef chef;
	public SampleHotel(Chef chef) {
		this.chef = chef;
	}
}
