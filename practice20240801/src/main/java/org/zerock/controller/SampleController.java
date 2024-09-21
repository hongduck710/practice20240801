package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

// @RestController /* 스프링 MVC에서 사용하는 어노테이션을 적용 */ /* 20240921 - @RestController와 @Controller의 중복사용을 피하기 위해 주석처리(authentication 구현을 위해서 @Controller만 필요. 실제로 @RestController를 적용하면 화면에 아무것도 나오지 않는 현상 발생) */
@RequestMapping("/sample/*")
@Log4j
@Controller
public class SampleController {
	
	@GetMapping("/all")
	public void doAll() {
		log.info("모든 액세스를 매일 수행한다.(do all can access everyday)");
	}
	
	@GetMapping("/member")
	public void doMember() {
		log.info("로그인 한 회원(logined member)");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("관리자 전용(admin only)");
	}
	
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert......ticket" + ticket);
		return ticket;
	}
	
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(
			@PathVariable("cat") String cat,
			@PathVariable("pid") Integer pid) {
		return new String[] {"category: " + cat, "productif: " + pid};
	}
	
	@GetMapping(value = "/check", params = { "height", "weight" })
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		
		SampleVO vo = new SampleVO(0, "" + height, "" + weight);
		
		ResponseEntity<SampleVO> result = null;
		
		if (height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		} else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	@GetMapping(value = "/getMap") //맵은 '키'와 '값'을 가지는 하나의 객체로 간주된다.
	public Map<String, SampleVO> getMap(){
		
		Map<String, SampleVO> map = new HashMap<>();
		map.put("첫번째", new SampleVO(1004, "박", "미달🪐🪐"));
		
		return map;
		
	}
	
	
	@GetMapping(value = "/getList")
	public List<SampleVO> getList() { /* 내부적으로 1부터 10미만 까지의 루프를 처리하면서 SampleVO 객체를 만들어서 List<SampleVO>로 만들어냄 */
		
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i + "First", i + "Last")).collect(Collectors.toList());
		
	}
	
	@GetMapping(value = "/getSample",
			produces = { MediaType.APPLICATION_JSON_VALUE,
										MediaType.APPLICATION_XML_VALUE })
	public SampleVO getSample() {
		return new SampleVO(1004 , "요정", "컴미🔮🔮");
	}
	
	@GetMapping(value="/getSample2")
	public SampleVO getSample2() {
		return new SampleVO(10041004, "슈퍼 🔮", "컴나라 🔮");
	}
	
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8") //브라우저 창의 네트워크 탭의 응답헤더 부분에서 Content-Type이 text/plain;charset=UTF-8임을 확인할 수 있음
	public String getText() {
		
		log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요? 컴미에요 ㅎㅎ🪐✨🪐✨";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("")
	public void basic() {
		log.info("✨🎉🎊🎉✨🎉🎊🎉basic......");
	}
	
	@RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
	public void basicGet() {
		log.info("✨🎉🎊🎉✨🎉🎊🎉basic get......");
	}
	
	@GetMapping("/basicOnlyGet/")
	public void basicGet2() {
		log.info("✨🎉🎊🎉✨🎉🎊🎉basic get only get......");
	}
	
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("" + dto);
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name: "+name);
		log.info("age: "+age);
		return "ex02";
	}
	
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("array ids: " + Arrays.toString(ids));
		
		return "ex02Array";
	}
	
	
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		
		log.info("list dtos: " + list);
		
		return "ex02Bean";
	}
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo: " + todo);
		return "ex03";
	}
	
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		
		log.info("dto: " + dto);
		log.info("page: " + page);
		
		return "/sample/ex04";
	}
	
	@GetMapping("/ex05")
	public void ex05() {
		log.info("시리우스 별에서 온 슈퍼왕자에요....");
	}
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("여기는 ex06..........");
		SampleDTO dto = new SampleDTO();
		dto.setAge(11);
		dto.setName("슈퍼컴미");
		
		return dto;
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("/ex07☆☆☆☆☆☆☆☆☆☆");
		// {"name" : "컴미"}
		String msg = "{\"name\": \"컴미\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "application/json;charset=UTF-8");
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload..........");
	}
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> {
			log.info("----🎈🎈🎈🎈🎈🎈🎈🎈🎈🎈🎈🎈🎈🎈----");
			log.info("name: " + file.getOriginalFilename());
			log.info("size: " + file.getSize());
		});
	}
	
}
