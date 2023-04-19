package com.tenco.bank.handler.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.handler.exception.CustomPageException;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.repository.model.user.User;
import com.tenco.bank.utils.Define;

@Component  // IoC 등록 (Bean으로 등록) 
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		User principal = (User)session.getAttribute(Define.PRINCIPAL);

		if (principal == null) {
			// 인증되지 않는 사용자일 경우 로그인 페이지로 이동
			// sendRedirect path 기반
			// response.sendRedirect("/user/sign-in");
			throw new CustomRestfullException("인증된 사용자가 아닙니다", HttpStatus.UNAUTHORIZED);
			//return false; // controller 들어가는거 막기
		}
		return true; // controller로 진행 
	}
	
	
	// 뷰(View)가 렌더링되기 전에 호출되는 메서드입니다.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	// 요청 처리가 완료된 후, 즉 뷰 렌더링이 완료된 후에 호출되는 메서드입니다.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}
