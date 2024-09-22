package org.zerock.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("액세스 제한됨(access denied): " + auth);
		model.addAttribute("msg", "액세스 제한됨(access denied)");
	}
	
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		log.info("에러(error): " + error);
		log.info("로그아웃(logout): " + logout);
		
		if(error != null) {
			model.addAttribute("error", "로그인 에러. 계정을 확인하세요.(Log in Error Check Your Account)");
		}
		if(logout != null) {
			model.addAttribute("logout", "로그아웃!(Logout!)");
		}
	} // loginInput 닫음
	
	@GetMapping("/customLogout")
	public void logoutGET() {
		log.info("로그아웃(custom logout)");
	} // logoutGET 닫음
}
