package com.qnpeople.rnd.pms;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.client.reactive.ClientHttpConnectorAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.DispatcherServlet;

import com.qnpeople.rnd.pms.common.domain.application.QNPEmbededTomcatApplication;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import kr.co.sbn.platformhub.framework.core.common.annotation.SBNExcludeAnnotation;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import kr.co.sbn.platformhub.framework.core.common.beans.generator.SBNBeanNameGeneratorAdaptor;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms
 * @Filename		: HdcciManMonthMgmtApplication.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션에 대한 프레임워크 내부 기본 제공 내장 톰캣 기반 어플리케이션 구축 및 구동 작업을 위한 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@EnableEncryptableProperties
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ClientHttpConnectorAutoConfiguration.class})
@ComponentScan(basePackages = {"kr.co.sbn.platformhub.framework", "com.qnpeople.rnd.pms"},
	excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = SBNExcludeAnnotation.class)
  )
public class HdcciManMonthMgmtApplication extends QNPEmbededTomcatApplication {

	/** 어플리케이션 구동을 위한 application.yml 프로퍼티 설정 경로 배열 객체 */
	private static final String[] APPLICATION_PROPERTIES_PATHS = new String[]{			
			"spring.config.name:application",
			"spring.config.location=optional:classpath:/config/application/"
	};
	
	/**
	 * 현대 CCI 협력사 공수 관리 시스템 어플리케이션 구동 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 */
	public HdcciManMonthMgmtApplication() {
		super("");
	}
	
	/**
	 * 현대 CCI 협력사 공수 관리 시스템 어플리케이션 구동 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	webApplicationName	현대 CCI 협력사 공수 관리 시스템 어플리케이션 명 정보
	 */
	public HdcciManMonthMgmtApplication(String webApplicationName) {
		super(webApplicationName);
	}
	
	/**
	 * 현대 CCI 협력사 공수 관리 시스템 어플리케이션 구동 작업을 수행하는 메인 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	args		어플리케이션 구동 인자 문자열 배열 객체
	 */
	public static void main(String[] args) {
		QNPReasonInterface errorReason = QNPReasonCode.FRAMEWORK_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//	
		String tomcatPlatformApplicationName = "HD CCI Man Month Management Web Application";
		HdcciManMonthMgmtApplication tomcatPlatformApplication = null;
		SBNBeanNameGeneratorAdaptor beanNameGenerator = null;
		DispatcherServlet dispatcherServlet = null;
    	ConfigurableApplicationContext applicationContext = null;    	
		try {
			//	1. 구동할 어플리케이션 객체를 생성 및 구축한다
			tomcatPlatformApplication = new HdcciManMonthMgmtApplication(tomcatPlatformApplicationName);
			//	2. 구동할 어플리케이션의 자동 Bean 생성 및 구축을 위한 객체를 생성하고, 기본 패키이 정보를 설정한다
			beanNameGenerator = new SBNBeanNameGeneratorAdaptor(tomcatPlatformApplication.getModuleName());
			beanNameGenerator.addBaseBeanPackage("com.qnpeople.rnd.pms.apis.*.controller");
			//	3. 구동할 어플리케이션 객체를 구축하고 구동 및 수행 설정 객체를 설정한다
			applicationContext = new SpringApplicationBuilder(HdcciManMonthMgmtApplication.class)
	    			.properties(APPLICATION_PROPERTIES_PATHS)
	    			.beanNameGenerator(beanNameGenerator)
	    			.build()
	    			.run(args);
			if(SBNUtils.isNull(applicationContext)) {
				errorMessage = "Application Configuration Context 객체 구축 오류.";
				throw new QNPWebException(errorReason, errorCode, errorMessage);
			}
			// 4. 어플리케이션의 WEB 수행을 위한 Context 객체를 구축한다	
			dispatcherServlet = (DispatcherServlet) applicationContext.getBean("dispatcherServlet");
			if(SBNUtils.isNull(dispatcherServlet)) {
				errorMessage = "Application 구축 DispatcherServlet 객체 구축 오류.";
				throw new QNPWebException(errorReason, errorCode, errorMessage);
			}
			log.info("==================================================");
			log.info("                   {} Starting...                           ", tomcatPlatformApplicationName);			
			log.info("             	      "+getApplicationProfileInfo()+"                 		  ");
			log.info("==================================================");
		} catch(QNPWebException webException) {
			log.error("main() webException={}", webException.getException());
		} catch(Exception exception) {
			if(!"org.springframework.boot.devtools.restart.SilentExitExceptionHandler$SilentExitException".equals(exception.toString())) {
    			//	실제 오류가 발생한 경우에만 오류 로그 작성
    			log.error("main() exception={}", exception.toString());
    		}
		}
	}
}

