package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.domain.BoardAttachVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service /* 계층 구조 상 비즈니스 영역을 담당하는 객체임을 표시하기 위해 사용하는 어노테이션 */
@AllArgsConstructor
public class BoardServiceImple implements BoardService{
	
	// spring 4.3 이상에서 자동 처리
	@Setter(onMethod_= @Autowired) /* 2개의 Mapper를 주입 받아야 하기 때문에 자동 주입 대신에 Setter메소드를 이용 */
	private BoardMapper mapper;
	
	@Setter(onMethod_= @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Override
	public void register(BoardVO board) {
		log.info("등록(register✨✨✨✨)" + board);
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
		
	}
	
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("첨부파일리스트 해당 게시물 번호(get Attach list by bno)" + bno);
		return attachMapper.findByBno(bno);
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

	@Transactional /* 첨부파일 삭제와 실제 게시물의 삭제가 같이 처리되도록 트랜잭션 하에서 BoardAttachMapper의 deleteAll()을 호출하도록 수정 */ 
	@Override
	public boolean remove(Long bno) {
		
		log.info("삭🎈제🎈"+ bno);
		attachMapper.deleteAll(bno);
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
