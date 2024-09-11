package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	
	private int init; /*2024. 08. 09 맨첫페이지 맨끝페이지를 위해 개인적으로 임의로 추가한 코드. - 문제 발생시 삭제*/
	private int fin; /*2024. 08. 09 맨첫페이지 맨끝페이지를 위해 개인적으로 임의로 추가한 코드. - 문제 발생시 삭제*/
	
	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		
		this.init = 1; /*2024. 08. 09 맨첫페이지 맨끝페이지를 위해 개인적으로 임의로 추가한 코드. - 문제 발생시 삭제*/
		this.fin = (int) (Math.ceil((total * 1.0) / cri.getAmount())); /*2024. 08. 09 맨첫페이지 맨끝페이지를 위해 개인적으로 임의로 추가한 코드. - 문제 발생시 삭제*/
		
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		
		this.startPage = this.endPage - 9;
		
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		
		this.next = this.endPage < realEnd;
		
	}
	
}
