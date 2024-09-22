package org.zerock.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, 
			HttpServletResponse response, AccessDeniedException accessException)
		throws IOException, ServletException {
		log.error("액세스 제한 핸들러(access denied handler)");
		log.error("리다이렉트(Redirectㅎㅎㅎㅎㅎ)");
		response.sendRedirect("/accessError");
	}
}
