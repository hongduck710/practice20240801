package org.zerock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.domain.BoardAttachVO;
import org.zerock.service.BoardService;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Controller /* 컨트롤러 어노테이션으로 스프링의 빈으로 인식할 수 있게 한다. */
@Log4j
@RequestMapping("/board/*") /*/board로 시작하는 모든 처리를 BoardController가 지정하도록 한다. */
@AllArgsConstructor
public class BoardController {
	
	private BoardService service;
/*	
	@GetMapping("/list")
	public void list(Model model) {
		
		log.info("list");
		model.addAttribute("list", service.getList());
		
	}
*/
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		log.info("list: " + cri);
		model.addAttribute("list", service.getList(cri));
		//model.addAttribute("pageMaker", new PageDTO(cri, 123));
		
		int total = service.getTotal(cri);
		
		log.info("총 게시글 갯수는?: " + total);
		
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	@GetMapping("/register") /* 입력페이지를 보여주는 역할만 하기 때문에 별도의 처리가 필요하지 않음 */
	public void register() {
		
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("---------------------------------------");
		
		log.info("register: " + board);
		log.info("글 제목: " + board.getTitle());
		log.info("글 내용: " + board.getContent());
		log.info("글쓴이: " + board.getWriter());
		
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		log.info("---------------------------------------");
		
		service.register(board); /* ✨✨✨ 2024년 08월 07일: 요 것을 빼먹어서 글쓰기 기능 구현이 되지 않았음 ✨✨✨ */
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";		
		/* 'redirect:' 접두어를 사용하면 스프링MVC가 내부적으로 response.sendRedirect()를 처리해줌 */
	}
	
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		/* 리퀘스트 파람 어노테이션: bno값을 좀 더 명시적으로 처리 */
		/* @ModelAttribute 어노테이션은 자동으로 Model에 데이터를 지정한 이름으로 담아줌. @ModelAttribute를 사용하지 않아도 Controller에서 화면으로 
		 * 파라미터가 된 객체는 전달이 되지만, 좀 더 명시적으로 이름을 지정하기 위해서 사용.*/
		
		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
		
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, /* @ModelAttribute("cri") */ Criteria cri ,RedirectAttributes rttr) {
		log.info("modify:" + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "성🎈공🎈");
		}
		/* UriComponetsBuilder 추가 이후로 주석처리
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("type", cri.getType());
		*/
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, /* @ModelAttribute("cri") */ Criteria cri ,RedirectAttributes rttr) {
		
		log.info("remove..." + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "성🎈공🎈");
		}
		
		/* UriComponetsBuilder 추가 이후로 주석처리
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		*/
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}
	
}
