package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service /* 계층 구조 상 비즈니스 영역을 담당하는 객체임을 표시하기 위해 사용하는 어노테이션 */
@AllArgsConstructor
public class BoardServiceImple implements BoardService{
	
	// spring 4.3 이상에서 자동 처리
	private BoardMapper mapper;
	
	@Override
	public void register(BoardVO board) {
		log.info("register✨✨✨✨" + board);
		mapper.insertSelectKey(board);
	}
	
	@Override
	public BoardVO get(Long bno) {

		log.info("get🧵🧵🧵🧵🧵" + bno);
		
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		
		log.info("modify⛲⛲⛲⛲⛲⛲" + board);
		return mapper.update(board) == 1;
		
		/* 정상적으로 수정과 삭제가 이루어지면 1이라는 값이 반환되기 때문에
		==연산자를 이용해서 true/false 처리 할 수 있음 */
	}

	@Override
	public boolean remove(Long bno) {
		
		log.info("삭🎈제🎈"+ bno);
		return mapper.delete(bno) == 1;
		
	}
/*
	@Override
	public List<BoardVO> getList() {

		log.info("getList✨✨✨✨");
		
		return mapper.getList();
	}
*/
	
	@Override
	public List<BoardVO> getList(Criteria cri){
		
		log.info("get List with criteria: " + cri);
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		
		log.info("게시글 총 갯수는 ??");
		return mapper.getTotalCount(cri);
	}

	
}
