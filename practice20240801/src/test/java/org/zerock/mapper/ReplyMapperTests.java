package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

// Java Config
// @ContextConfigutation(classes = {org.zerock.config.RootConfig.class})

@Log4j
public class ReplyMapperTests {
	
	// 테스트 전에 해당 번호의 게시물이 존재하는 지 반드시 확인
	private Long[] bnoArr = {581722L, 581720L, 581719L, 581718L, 581717L};
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			
			ReplyVO vo = new ReplyVO();
			
			// 게시물 번호
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("replyer " + i);
			
			mapper.insert(vo);
			
		});
	}
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	@Test
	public void testRead() {
		Long targetRno = 5L;
		ReplyVO vo = mapper.read(targetRno);
		log.info(vo);
	}
	
	@Test
	public void testDelete() {
		Long targetRno = 1L;
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("댓글 수정 🔮🔮");
		int count = mapper.update(vo);
		log.info("업데이트 수🔮🔮: " + count);
	}
	
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		
		// 581722L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		replies.forEach(reply -> log.info(reply));
		
	}
	
	@Test
	public void testList2() {
		Criteria cri = new Criteria(2, 10);
		
		// 581722L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 581722L);
		replies.forEach(reply -> log.info(reply));
		
	}
	
}
