package com.qnpeople.rnd.pms.common.interceptor.executor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.sbn.platformhub.framework.core.common.web.common.interceptor.modules.controller.SBNWebControllerInterceptorExecutorAdaptor;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.interceptor.executor
 * @Filename		: QNPWebControllerInterceptorImpl.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션의 클라이언트 요청 수행을 위한 컨트롤러의 인터셉터 수행을 위한 최상위 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class QNPWebControllerInterceptorImpl extends SBNWebControllerInterceptorExecutorAdaptor implements QNPWebControllerInterceptor {
	
	/**
	 * 프레임워크 내부 제공 클라이언트 웹 요청 수행 시 인터셉터 작업 수행 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 */
	public QNPWebControllerInterceptorImpl() {
		super();
	}
	
	/**
	 * 프레임워크 내부 제공 WEB 어플리케이션 컨트롤러의 클라이언트 수행 요청에 대한 작업 수행 전, 선 처리 작업을 추가적으로 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	httpServletRequest		WEB 어플리케이션 컨트롤러의 클라이언트 요청 수행 전달 HttpServletRequest 인터페이스 객체
	 * @param 	httpServletResponse		WEB 어플리케이션 컨트롤러의 클라이언트 요청 수행 결과 응답 전달 HttpServletResponse 인터페이스 객체
	 * @param 	handler						WEB 어플리케이션 컨트롤러의 인터셉터 수행 핸들러 객체
	 * @return 	프레임워크 내부 제공 WEB 어플리케이션 컨트롤러의 클라이언트 수행 선 처리 작업 수행 결과 Flag. 정상 수행 시  true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	Exception					클라이언트의 WEB 어플리케이션 컨트롤러의 클라이언트 요청에 대한 인터셉터 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		try {
			log.info("preHandle() [ {} ] 어플리케이션 정의 WEB Controller 수행 선처리 작업 수행.", moduleName);
			return true;
		} catch(Exception exception) {
			log.error("preHandle() exception={}", exception.toString());
			return false;
		}
	}
	
	/**
	 * 프레임워크 내부 제공 WEB 어플리케이션 컨트롤러의 클라이언트 수행 요청에 대한 작업 수행 후, 후 처리 작업을 추가적으로 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	httpServletRequest		WEB 어플리케이션 컨트롤러의 클라이언트 요청 수행 전달 HttpServletRequest 인터페이스 객체
	 * @param 	httpServletResponse		WEB 어플리케이션 컨트롤러의 클라이언트 요청 수행 결과 응답 전달 HttpServletResponse 인터페이스 객체
	 * @param 	handler						WEB 어플리케이션 컨트롤러의 인터셉터 수행 핸들러 객체
	 * @param 	modelAndView			WEB 어플리케이션 컨트롤러의 수행 결과 전달 ModelAndView 객체
	 * @throws 	Exception					클라이언트의 WEB 어플리케이션 컨트롤러의 클라이언트 요청에 대한 인터셉터 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		
		try {
			log.info("postHandle() [ {} ] 어플리케이션 정의 WEB Controller 수행 후 처리 작업 수행.", moduleName);
		} catch(Exception exception) {
			log.error("postHandle() exception={}", exception.toString());
			return;
		}
	}
	
	/**
	 * 프레임워크 내부 제공 WEB 어플리케이션 컨트롤러의 클라이언트 수행 요청에 대한 작업 수행 후, 완료 처리 작업을 추가적으로 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	httpServletRequest		WEB 어플리케이션 컨트롤러의 클라이언트 요청 수행 전달 HttpServletRequest 인터페이스 객체
	 * @param 	httpServletResponse		WEB 어플리케이션 컨트롤러의 클라이언트 요청 수행 결과 응답 전달 HttpServletResponse 인터페이스 객체
	 * @param 	handler						WEB 어플리케이션 컨트롤러의 인터셉터 수행 핸들러 객체
	 * @param 	exception					WEB 어플리케이션 컨트롤러의 클라이언트 요청 수행 중 발생 예외 처리 Exception 객체
	 * @throws 	Exception					클라이언트의 WEB 어플리케이션 컨트롤러의 클라이언트 요청에 대한 인터셉터 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception errorException) throws Exception {
		
		try {
			if(!SBNUtils.isNull(errorException)) {
				log.error("afterCompletion() [ {} ] 어플리케이션 정의 WEB Controller 수행 오류.", errorException.toString());
			}
		} catch(Exception exception) {
			log.error("afterCompletion() exception={}", exception.toString());
			return;
		}
	}
}
