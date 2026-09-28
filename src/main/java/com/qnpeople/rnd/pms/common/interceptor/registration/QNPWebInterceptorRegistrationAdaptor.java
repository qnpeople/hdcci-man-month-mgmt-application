package com.qnpeople.rnd.pms.common.interceptor.registration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.qnpeople.rnd.pms.common.interceptor.executor.QNPWebControllerInterceptorImpl;
import com.qnpeople.rnd.pms.common.interceptor.executor.QNPWebLoggingInterceptorImpl;

import kr.co.sbn.platformhub.framework.core.common.web.common.interceptor.common.registration.SBNWebInterceptorRegistration;
import kr.co.sbn.platformhub.framework.core.common.web.common.interceptor.common.registration.SBNWebInterceptorRegistrationAdaptor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.interceptor.registration
 * @Filename		: QNPWebInterceptorRegistrationAdaptor.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션의 클라이언트 요청에 대한 인터셉터 모듈 자동 생성 및 Bean 등록 작업을 수행하는 최상위 클래스 
 * =================================================================================
 */
@Configuration
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class QNPWebInterceptorRegistrationAdaptor extends SBNWebInterceptorRegistrationAdaptor implements SBNWebInterceptorRegistration {

	/**
	 * 프레임워크 내부 제공 클라이언트 웹 요청 수행 시 인터셉터 수행 객체 등록 작업을 하는 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 */
	public QNPWebInterceptorRegistrationAdaptor() {
		super();
	}
	
	/**
	 * 프레임워크 내부 제공 WEB 어플리케이션 인터셉터 등록 대상 객체를 등록하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	webInterceptorRegistry 	프레임워크 내부 제공 WEB 어플리케이션 수행 인터셉터 모듈 등록 및 관리 객체
	 */
	@Override
	public void addInterceptors(InterceptorRegistry webInterceptorRegistry) {
		webInterceptorRegistry.addInterceptor(new QNPWebControllerInterceptorImpl())
			.order(1)																					//	우선 순위 설정
			.addPathPatterns("/**")																//	인터셉터 적용 범위(전체 범위)
		    .excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**");		//	수행 제외 처리 범위 정의
		//
		webInterceptorRegistry.addInterceptor(new QNPWebLoggingInterceptorImpl())
		.order(2)																					//	우선 순위 설정
		.addPathPatterns("/**")																//	인터셉터 적용 범위(전체 범위)
	    .excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**");		//	수행 제외 처리 범위 정의
	}
}
