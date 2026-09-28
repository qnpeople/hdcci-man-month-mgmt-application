package com.qnpeople.rnd.pms.common.domain.application;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.sbn.platformhub.framework.core.common.base.application.tomcat.SBNEmbededTomcatPlatformApplication;
import kr.co.sbn.platformhub.framework.core.common.base.application.tomcat.SBNTomcatGracefulShutdown;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.application
 * @Filename		: QNPEmbededTomcatApplication.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.08.20.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션 구동을 위한 스프링부트 프레임워크 내부 기본 제공 내장 톰캣 에 대한 Graceful Shutdown 등의 추가 설정 
 *  작업을 수행하는 최상위 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@Configuration
@Slf4j
public class QNPEmbededTomcatApplication extends SBNEmbededTomcatPlatformApplication {

	/**
	 * QNP 프레임워크 내부 기본 제공 Apache 기반 내장 톰켓 플랫폼 웹 어플리케이션 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 */
	public QNPEmbededTomcatApplication() {
		this("");
	}
	
	/**
	 * QNP 프레임워크 내부 기본 제공 Apache 기반 내장 톰켓 플랫폼 웹 어플리케이션 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	applicationName		내장 톰캣 플랫폼 웹 어플리케이션 구분 명 정보
	 */
	public QNPEmbededTomcatApplication(String tomcatWebApplicationName) {
		super(tomcatWebApplicationName, null);
	}
	
	/**
	 * 내장 톰켓 웹 어플리케이션 생성 객체를 구추하여 전달하는 메소드
	 * 하위 상속 클래스에서 재 정의 시 @Bean 어노테이션을 정의하여 자동으로 Bean 객체 구축 필요
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	gracefulShutdown	내장 톰켓 웹 어플리케이션의 Graceful Shutdown 수행 객체
	 * @return		내장 톰켓 웹 어플리케이션 생성 객체
	 */
	@Bean
	public ConfigurableServletWebServerFactory getWebServerFactory(SBNTomcatGracefulShutdown gracefulShutdown) {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
			}
		};
		// Graceful shutdown 지원
		factory.addConnectorCustomizers(gracefulShutdown);
		return factory;
	}
}
