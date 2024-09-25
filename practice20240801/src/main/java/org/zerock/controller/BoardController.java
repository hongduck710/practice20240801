package org.zerock.controller;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
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
	@PreAuthorize("isAuthenticated()") // isAuthenticated(): 로그인한 사용자만이 해당 기능을 사용할 수 있도록 처리
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
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		log.info("첨부파일삭제(delete attach files........................)");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("C:\\zzz\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName()); 	/* 20240920 - 업로드 폴더 경로를 "C:\\zzz\\upload\\" 라고 작성해야 하는데 "C:\\zzz\\upload" 라고 작성이 되어서 게시글 삭제할 때 파일 삭제가 정상적으로 이루어지지 않았음. 전부터 여러 번 하던 실수이므로 경로에 \가 누락되었는지 여부 항상 확인하기. */
				
				Files.deleteIfExists(file); /* 20240920 -  해당 코드를 작성하지 않았더니 업로드 폴더에 있는 파일들이 삭제가 되지 않음(데이터베이스에서는 삭제가 정상적으로 됨을 확인) */
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\zzz\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());
					Files.delete(thumbNail);
				}
			} catch(Exception e) {
				log.error("첨부파일 삭제 에러(delete file error): " + e.getMessage());
			} // try catch 닫음
		}); // froEach 닫음
	} // deleteFiles 닫음
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, /* @ModelAttribute("cri") */ Criteria cri ,RedirectAttributes rttr) {
		
		log.info("remove..." + bno);
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		if(service.remove(bno)) {
			// 첨부파일 삭제(delete Attach Files)
			deleteFiles(attachList);
			
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
