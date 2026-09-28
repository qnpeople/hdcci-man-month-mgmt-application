package com.qnpeople.rnd.pms.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.sbn.platformhub.framework.core.common.aspect.domains.controller.module.SBNControllerAspectExecutor;
import kr.co.sbn.platformhub.framework.core.common.aspect.domains.controller.module.SBNControllerAspectExecutorAdaptor;
import kr.co.sbn.platformhub.framework.core.common.aspect.utils.SBNAspectUtil;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import kr.co.sbn.platformhub.framework.core.common.base.domain.SBNRequest;
import kr.co.sbn.platformhub.framework.core.common.base.domain.client.SBNClientRequest;
import kr.co.sbn.platformhub.framework.core.common.base.domain.client.SBNClientRequestAdaptor;
import kr.co.sbn.platformhub.framework.core.common.web.common.data.session.SBNWebClientUserSessionData;
import kr.co.sbn.platformhub.framework.core.constants.SBNWebConstant;
import kr.co.sbn.platformhub.framework.core.exceptions.SBNCommonException;
import kr.co.sbn.platformhub.framework.core.exceptions.SBNException;
import kr.co.sbn.platformhub.framework.core.exceptions.SBNRuntimeException;
import kr.co.sbn.platformhub.framework.core.types.SBNUseYnType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import kr.co.sbn.platformhub.framework.securities.constants.SBNSecurityConstant;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.common.config.SBNJwtConfig;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.common.module.SBNJwtTokenExecutor;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.data.SBNJwtAccessTokenData;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.types.SBNJwtTokenPayloadItemType;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.aspect
 * @Filename		: QNPControllerAspectExecutor.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.08.20.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션의 클라이언트 요청에 대한 작업 수행을 위한 Aspect 처리를 수행하는 최상위 인터페이스 클래스를 구현한
 *  구현 클래스
 * =================================================================================
 */
@Aspect
@Component
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
public class QNPControllerAspectExecutor extends SBNControllerAspectExecutorAdaptor implements SBNControllerAspectExecutor {

	/** 어플리케이션 클라이언트의 JWT 토큰 수행 인터페이스 객체 */
	@Autowired
	private SBNJwtTokenExecutor JwtTokenModuleExecutor;
	
	/**
	 * QNP 프레임워크 내부 제공 어플리케이션 수행 클라이언트의 WEB 요청에 대한 ASPECT 작업을 수행하는 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 */
	public QNPControllerAspectExecutor() {
		super();
	}
	
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청 처리 Controller 의 수행 Method 에 대한 AOP 적용 대상 요청 메소드 매핑을 적용하는 메소드
	 * - 하위 실질 구현 클래스에서 @Pointcut Annotation 을 적용하여 클라이언트로부터의 요청에 대한 허용 Method 적용
	 * - REQ		:	@RequestMapping
	 * - POST	:	@PostMapping
	 * - GET		:	@GetMapping
	 * - PUT		:	@PutMapping
	 * - DELETE	:	@DeleteMapping
	 * - OPTIONS: @Options
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 */
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || "
		+ "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
		+ "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
		+ "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
		+ "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public void requestMapping() {}
		
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청 처리 Controller 에서의 AOP 적용 클래스의 패턴을 적용하는 메소드
	 * - 하위 실질 구현 클래스에서 @Pointcut Annotation 을 적용하여 AOP 적용 클래스에 대한 패턴을 정의
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 */
	@Pointcut("execution(* com.qnpeople.rnd.pmp.*.*.controller.*.*Controller.*(..)) || "
		+ "execution(* com.qnpeople.rnd.pmp.*.*.*.controller.*.*.*Controller.*(..)) || "
		+ "execution(* com.qnpeople.rnd.pmp.*.*.*.*.controller.*.*.*Controller.*(..)) || "
		+ "execution(* com.qnpeople.rnd.pmp.*.*.*.*.controller.*.*.*.*Controller.*(..))")
	public void apiController() {}
	
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청 처리 Service 에서의 APO 적용 클래스의 패턴을 적용하는 메소드
	 * - 하위 실질 구현 클래스에서 @Pointcut Annotation 을 적용하여 AOP 적용 클래스에 대한 패턴을 적용
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 */
	@Pointcut("execution(* com.qnpeople.rnd.pmp.*.*.service.*Service.*(..)) || "
		+ "execution(* com.qnpeople.rnd.pmp.*.*.*.service.*Service.*(..)) ||"
		+ "execution(* com.qnpeople.rnd.pmp.*.*.*.*.service.*ServiceImpl.*(..)) ||"
		+ "execution(* com.qnpeople.rnd.pmp.*.*.*.*.service.*ServiceImpl.*(..))")
	public void apiService() {}
	
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청 처리에 대해 JPA 수행 Repository 클래스의 패턴을 적용하는 메소드
	 * - 하위 실질 구현 클래스에서의 @Pointcut Annotation 을 적용하여 AOP 적용 클래스에 대한 패턴을 적용
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 */
	@Pointcut("execution(* com.qnpeople.rnd.pmp.*.*.repository.*Repository.*(..))")
	public void apiRepository() {}
	
	/**
	 * 프레임워크 내부 제공 클라이언트의 요처에 대한 전/후 처리 작업을 수행하는 메소드
	 * [ 요청 작업 수행 중 오류 발생 시, 예외 처리 로직을 정의하기 위한 작업을 수행 ] 
	 * [ onBeforeControllerHandler() 수행 전 ~ onAroundProcessHandler() 수행 후까지의 처리 수행 ]
	 * - onBeforeControllerHandler(JoinPoint joinPoint)
	 * - onAroundProcessHandler(ProceedingJoinPoint joinPoint)
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	joinPoint										클라이언트로부터 전달된 Aspect 반영 포인트 컷 객체
	 * @throws 	SBNFrameworkCommonException	작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Around("apiController()")
	public Object onAroundProcessHandler(ProceedingJoinPoint joinPoint) throws SBNCommonException {
		return commonAroundProcessHandler(joinPoint);
	}
	
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청에 대한 전 처리 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	joinPoint										클라이언트로부터 전달된 Aspect 반영 포인트 컷 객체
	 * @throws 	SBNFrameworkCommonException	작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public void onBeforeControllerHandler(JoinPoint joinPoint) throws SBNCommonException {
		commonBeforeControllerHandler(joinPoint);
	}
		
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청에 대한 후 처리 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	joinPoint										클라이언트로부터 전달된 Aspect 반영 포인트 컷 객체
	 * @param 	returnObj									클라이언트로부터 전달된 요청 처리 수행 결과 응답 전달 객체		
	 * @throws 	SBNFrameworkCommonException	작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public void onAfterControllerHandler(JoinPoint joinPoint, Object returnObj) throws SBNCommonException {
		commonAfterControllerHandler(joinPoint, returnObj);
	}
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	1. 프레임워크 내부에서 실제 AOP 작업 수행 메소드 정의
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 프레임워크 내부 제공 클라이언트의 요처에 대한 전/후 처리 작업을 수행하는 메소드
	 * - 기본 로직 변경 또는 재 정의 시, 하위 상속 클래스에서 재 정의 필요
	 * - 클라이언트의 요청 허용 컨트롤러에 대한 필터링 작업을 수행
	 * - 하위 상속 클래스에서 commonBusinessAroundProcess() 메소드를 구현 해야 함
	 * [ 요청 작업 수행 중 오류 발생 시, 예외 처리 로직을 정의하기 위한 작업을 수행 ] 
	 * [ onBeforeControllerHandler() 수행 전 ~ onAroundProcessHandler() 수행 후까지의 처리 수행 ]
	 * - onBeforeControllerHandler(JoinPoint joinPoint)
	 * - onAroundProcessHandler(ProceedingJoinPoint joinPoint)
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	joinPoint			클라이언트로부터 전달된 Aspect 반영 포인트 컷 객체
	 * @return		클라이언트의 요청 작업 수행 결과 전달 응답 데이터 객체
	 * @throws 	SBNFrameworkCommonException	작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	protected Object commonAroundProcessHandler(ProceedingJoinPoint joinPoint) throws SBNCommonException {
		QNPReasonInterface errorReason = QNPReasonCode.FRAMEWORK_WEB_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//	클라이언트의 요청 수행 결과 응답 객체
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		SBNClientRequest tmpClientRequest = null;
		SBNClientRequestAdaptor clientRequest = null;
		//
		String tmpTxId = SBNUtils.createSystemTransactionId(true);
		Boolean validateResultFlag = false;
		Object resultObject = null;
		try {
			// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	1. 전달된 Aspect 반영 포인트 컷 객체를 이용하여 작업에 필요한 정보를 추출
			//	1.1.전달된 포인트 컷 객체로부터 클라이언트의 요청 전달 HttpServletRequest 인터페이스 객체를 추출
			//	1.2. 전달된 포인트 컷 객체로부터 클라이언트의 요청 수행 결과 응답 전달 HttpServletResponse 인터페이스 객체를 추출
			//	1.3. 전달된 포인터 컷 객체로부터 클라이언트의 요청 전달 객체를 추출 후, 트랜잭션 ID 정의 여부를 체크 후 설정
			//	1.3.1. 클라이언트로부터 전달된 요청 객체가 존재 시, 수행 추적 트랜잭션 ID 정보가 미 정의 시, 신규 트랜잭션 ID 정보를 설정
			//	1.3.2. 클라이언트로부터 전달된 요청 객체가 존재 시, 요청 시작 시간 정보를 설정
			// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			try {
				//	1.1.전달된 포인트 컷 객체로부터 클라이언트의 요청 전달 HttpServletRequest 인터페이스 객체를 추출
				httpServletRequest = SBNAspectUtil.extractHttpServletRequestFromJoinPoint(joinPoint);
				//	1.2. 전달된 포인트 컷 객체로부터 클라이언트의 요청 수행 결과 응답 전달 HttpServletResponse 인터페이스 객체를 추출
				httpServletResponse =  SBNAspectUtil.extractHttpServletResponseFromJoinPoint(joinPoint);
				//	1.3. 전달된 포인터 컷 객체로부터 클라이언트의 요청 전달 객체를 추출 후, 트랜잭션 ID 정의 여부를 체크 후 설정
				tmpClientRequest = SBNAspectUtil.extractClientRequestDataFromJointPoint(joinPoint);
				if(!SBNUtils.isNull(tmpClientRequest)) {
					//	1.3.1. 클라이언트로부터 전달된 요청 객체가 존재 시, 수행 추적 트랜잭션 ID 정보가 미 정의 시, 신규 트랜잭션 ID 정보를 설정
					clientRequest = (SBNClientRequestAdaptor)tmpClientRequest;
					if(SBNUtils.isNull(clientRequest.getTxId())) {
						clientRequest.setTxId(tmpTxId);
					}
					//	1.3.2. 클라이언트로부터 전달된 요청 객체가 존재 시, 요청 시작 시간 정보를 설정
					clientRequest.setRequestStartTime(System.currentTimeMillis());
				}
			} catch(SBNCommonException commonException) {
				throw commonException;
			} catch(SBNRuntimeException runtimeException) {
				errorReason = (QNPReasonCode)runtimeException.getErrorReason();
				errorCode = errorReason.getReasonCode();
				errorMessage = runtimeException.getErrorMessage();
				throw new SBNCommonException(errorReason, errorCode, errorMessage);
			} catch(Exception exception) {
				if(!SBNUtils.isNull(exception.getMessage())) {
					errorMessage = exception.getMessage(); 
				} else {
					errorMessage = exception.toString();
				}
				throw new SBNCommonException(errorReason, errorCode, errorMessage);
			}
			log.debug("commonAroundProcessHandler() joinPoint={}", joinPoint);
			// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2. 클라이언트의 요청 정보를 이용하여 클라이언트 인증 작업을 수행
			// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			try {
				validateResultFlag = validateFrameworkClientAuthentication(httpServletRequest, httpServletResponse, clientRequest);
				if(!validateResultFlag) {
					errorMessage = "클라이언트의 인증 수행 실패 오류";
					throw new SBNCommonException(errorReason, errorCode, errorMessage);
				}
			} catch(SBNCommonException commonException) {
				throw commonException;
			} catch(Exception exception) {
				if(!SBNUtils.isNull(exception.getMessage())) {
					errorMessage = exception.getMessage();
				} else {
					errorMessage = exception.toString();
				}
				throw new SBNCommonException(errorReason, errorCode, errorMessage);
			}
			
			// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	3. 클라이언트의 요청 메소드를 수행하고 수행 결과 객체를 전달 받아, 결과에 따라 작업을 수행한다
			//	3.1. 작업 수행 중 예외 발생 시, 예외 처리 작업을 수행 후 작업을 종료한다
			// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			try {
				resultObject = joinPoint.proceed();	
			} catch(SBNCommonException sbnCommonException) {
				throw sbnCommonException;
			} catch(SBNRuntimeException sbnRuntimeException) {
				errorReason = (QNPReasonCode)sbnRuntimeException.getErrorReason();
				errorCode = errorReason.getReasonCode();
				errorMessage = sbnRuntimeException.getErrorMessage();
				throw new SBNCommonException(errorReason, errorCode, errorMessage);
			} catch(SBNException sbnException) {
				errorReason = (QNPReasonCode)sbnException.getErrorReason();
				errorCode = errorReason.getReasonCode();
				errorMessage = sbnException.getErrorMessage();
				throw new SBNCommonException(errorReason, errorCode, errorMessage);
			} catch(Exception exception) {
				if(!SBNUtils.isNull(exception.getMessage())) {
					errorMessage = exception.getMessage();
				} else {
					errorMessage = exception.toString();
				}
				throw new SBNCommonException(errorReason, errorCode, errorMessage);
			} catch(Throwable throwable) {
				if(!SBNUtils.isNull(throwable.getMessage())) {
					errorMessage = throwable.getMessage();
				} else {
					errorMessage = throwable.toString();
				}
				throw new SBNCommonException(errorReason, errorCode, errorMessage);
			}
			
			// //////////////////////////////////////////////////////////////////////////////////////////
			//	4. 클라이언트의 요청 정상 수행 시, 수행 결과를 전달 후 작업을 종료한다
			// //////////////////////////////////////////////////////////////////////////////////////////
			return resultObject;
		} catch(SBNCommonException sbnCommonException) {
			throw (SBNCommonException)sbnCommonException;
		} catch(RuntimeException runtimeException) {
			throw new SBNCommonException(errorReason, errorCode, errorMessage);
		} catch(Exception exception) {
			throw new SBNCommonException(errorReason, errorCode, errorMessage);
		}
	}	
	
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청에 대한 전 처리 작업을 수행하는 메소드
	 * : 클라이언트의 요청에 대해 요청 매핑으로 정의된 메소드를 수행하기 직전에 선 처리 작업을 수행
	 * - 기본 로직 변경 또는 재 정의 시, 하위 상속 클래스에서 재 정의 필요
	 * - 클라이언트의 요청 허용 메소드 체크 및 허용 컨트롤러에 대한 필터링 작업을 수행
	 *
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	joinPoint										클라이언트로부터 전달된 Aspect 반영 포인트 컷 객체
	 * @throws 	SBNFrameworkCommonException	작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public void commonBeforeControllerHandler(JoinPoint joinPoint) throws SBNCommonException {}
	
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청에 대한 후 처리 작업을 수행하는 메소드
	 * : 클라이언트의 요청에 대해 요청 매핑으로 정의된 메소드 수행 직후에 후 처리 작업을 수행
	 * - 기본 로직 변경 또는 재 정의 시, 하위 상속 클래스에서 재 정의 필요
	 * - 클라이언트의 요청 허용 메소드 및 허용 컨트롤러에 대한 필터링 작업 및 요청 수행 결과를 정의
	 * - 하위 상속 클래스에서 commonBusinessAfterProcess() 메소드를 구현해야 함
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	joinPoint										클라이언트로부터 전달된 Aspect 반영 포인트 컷 객체
	 * @param 	returnObj									클라이언트로부터 전달된 요청 처리 수행 결과 응답 전달 객체		
	 * @throws 	SBNFrameworkCommonException	작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public void commonAfterControllerHandler(JoinPoint joinPoint, Object returnObj) throws SBNCommonException {	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	3. 하위 상속 클래스에서 재 정의해야할 세부 로직 수행 모듈 정의 부분
	//	3.1. 클라이언트의 사용자 인증 수행 모듈 정의
	////////////////////////////////////////////////////////////////////////////////////////////////////////////			
	/**
	 * 전달된 정보를 이용한 클라이언트의 요청에 대한 클라이언트에 대한 인증 작업을 수행하는 메소드
	 * - 요청 클라이언트의 JWT 토큰 적용 시, JWT 토큰 인증 처리 작업
	 * - 요청 클라이언트에 대한 인증 작업 수행
	 * - 요청 클라이언트에 대한 세션 사용 시, 세션 수행 처리 작업
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	httpRequest					클라이언트로부터 전달된 요청 전달 HttpServletRequest 객체
	 * @param 	httpResponse					클라이언트로부터 전달된 요청 전달 HttpServletResponse 객체
	 * @param 	clientRequest					클라이언트로부터 전달된 사용자 정의 요청 데이터 객체
	 * @return		전달된 정보를 이용한 클라이언트의 요청에 대한 클라이언트의 인증 작업 수행 결과 Flag. 정상인 경우 true를, 그렇지 않은 경우 false를 전달
	 * @throws 	SBNCommonException		작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	protected Boolean validateFrameworkClientAuthentication(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse, SBNRequest clientRequest) throws SBNCommonException {
		QNPReasonInterface errorReason = QNPReasonCode.FRAMEWORK_AUTH_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//	
		String txId = "";
		//	클라이언트의 세션 정보 객체 추출 및 설정을 위한 객체 정의
		SBNWebClientUserSessionData clientUserSession = null;
		//	클라이언트의 JWT 토큰 수행 설정 정보 객체
		SBNJwtConfig jwtTokenConifg = null;
		//	클라이언트의 요청 URI 정보
		String clientRequestUri = "";
		//	클라이언트의 JWT 토큰 인증 적용 여부 Flag
		Boolean jwtTokenAdaptFlag = false;
		Boolean clientRequestJwtAuthExcludeRequestUri = false;
		// 클라이언트로부터 전달된 JWT 토큰 정보 추출을 위한 객체 정의
		String authorizationHeaderValue = "";
		SBNJwtAccessTokenData jwtAccessToken = null;
		try {			
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	1. 
			//	1.1. 클라이언트로부터 전달된 개별 요청 전달 객체가 전달 시, 요청 전달 객체에서 수행 추적을 위한 고유 트랜잭션 ID 정보를 추출한다
			//	1.2. JWT 토큰 인증 여부 체크 및 JWT 토큰 접근 객체 구축을 위한 JWT 토큰 수행 설정 객체를 추출한다
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	1.1. 클라이언트로부터 전달된 개별 요청 전달 객체가 전달 시, 요청 전달 객체에서 수행 추적을 위한 고유 트랜잭션 ID 정보를 추출한다
			if(!SBNUtils.isNull(clientRequest)) {
				txId = clientRequest.getTxId();
			}
			//	1.2. JWT 토큰 인증 여부 체크 및 JWT 토큰 접근 객체 구축을 위한 JWT 토큰 수행 설정 객체를 추출한다
			jwtTokenConifg = JwtTokenModuleExecutor.getJwtTokenExecutorModuleConfig();			
			SBNUseYnType jwtTokenAdaptYnType = SBNUseYnType.getUseYnType(jwtTokenConifg.getJwtTokenAdaptYn());
			log.debug("validateFrameworkClientAuthentication() jwtTokenAdaptYn={}, jwtTokenAdaptYnType={}", jwtTokenConifg.getJwtTokenAdaptYn(), jwtTokenAdaptYnType.name());
			if(SBNUseYnType.Y.equals(jwtTokenAdaptYnType)) {
				jwtTokenAdaptFlag = true;
			}
			log.debug("validateFrameworkClientAuthentication() jwtTokenAdaptFlag={}", jwtTokenAdaptFlag);
			//	1.3. 클라이언트 Http 요청 객체로부터 클라이언트의 요청 URI 정보를 추출한다 
			clientRequestUri = httpRequest.getRequestURI();
			
			clientRequestJwtAuthExcludeRequestUri = jwtTokenConifg.isJwtTokenExcludeUrl(clientRequestUri);
			
			//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2. 클라이언트의 세션 정보 객체 추출 및 설정을 위한 객체 정의
			//	2.1. HttpServletRequest 객체로부터 세션 객체를 추출한다
			//	22. 세션 객체에 클라이언트 세션 키 명(USER_SESSION)에 대한 정보가 존재 시, 해당 정보가 SBNWebClientUserSession 유형 인 경우, 클라이언트 세션 객체를 추출한다
			//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2.1. HttpServletRequest 객체로부터 세션 객체를 추출한다
			HttpSession clientHttpSession = httpRequest.getSession();
			//	2.2. 세션 객체에 클라이언트 세션 키 명(USER_SESSION)에 대한 정보가 존재 시, 해당 정보가 SBNWebClientUserSession 유형 인 경우, 클라이언트 세션 객체를 추출한다
			if(!SBNUtils.isNull(clientHttpSession.getAttribute(SBNWebConstant.CLIENT_SESSION_KEY_NAME))) {
				if((clientHttpSession.getAttribute(SBNWebConstant.CLIENT_SESSION_KEY_NAME) instanceof SBNWebClientUserSessionData)) {
					clientUserSession = (SBNWebClientUserSessionData)clientHttpSession.getAttribute(SBNWebConstant.CLIENT_SESSION_KEY_NAME);
				} else {
					errorMessage = "클라이언트 세션 데이터 타입 오류";
					throw new SBNCommonException(errorReason, errorCode, errorMessage);
				}
			}
			
			//	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	3. 클라이언트로부터 전달된 JWT 토큰 정보 추출을 위한 객체 정의
			//	3.1. 클라이언트 요청의 Authorization Header 로 전달된 JWT 토큰 인증 정보를 추출한다
			//	3.2. 추출한 Authorization Header 정보에서 bearer 정보를 제거한 나머지 전달 값을 추출한다
			//	33. 추출한 원본 JWT 토큰 문자열 정보를 이용하여 클라이언트의 JWT 토큰 객체를 구축한다
			//	3.4. 클라이언트 요청 전달 객체에 구축한 클라이언트의 서버 세션 객체와 JWT 토큰 객체를 설정한다
			//	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			if(jwtTokenAdaptFlag && (!clientRequestJwtAuthExcludeRequestUri)) {
				//	3.1. 클라이언트 요청의 Authorization Header 로 전달된 JWT 토큰 인증 정보를 추출한다 ( bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYnM5NTE4IiwiaXNzIjoic2JuLmNvLm... )
				authorizationHeaderValue = httpRequest.getHeader(SBNSecurityConstant.JWT_HEADER_AUTHORIZATION_KEY);
				if(SBNUtils.isNull(authorizationHeaderValue)) {
					errorMessage = "요청 Header Authorization JWT 토큰 인증 정보 미 전달 오류";
					throw new SBNCommonException(errorReason, errorCode, errorMessage);
				}
				//	3.2. 추출한 Authorization Header 정보에서 bearer 정보를 제거한 나머지 전달 값을 추출한다
				authorizationHeaderValue = authorizationHeaderValue.substring(SBNSecurityConstant.JWT_HEADER_AITHORIZATION_VALUE_PREFIX.length()).trim();
				if(SBNUtils.isNull(authorizationHeaderValue)) {
					errorMessage = "요청 Header Authorization JWT 토큰 인증 값 정보 미 전달 오류";
					throw new SBNCommonException(errorReason, errorCode, errorMessage);
				}
				//	3.3. 추출한 원본 JWT 토큰 문자열 정보를 이용하여 클라이언트의 JWT 토큰 객체를 구축한다				
				jwtAccessToken =(SBNJwtAccessTokenData)JwtTokenModuleExecutor.extractJwtAccessToken(txId, authorizationHeaderValue);
			}
			//	3.4. 클라이언트 요청 전달 객체에 구축한 클라이언트의 서버 세션 객체와 JWT 토큰 객체를 설정한다
			if(clientRequest instanceof QNPWebClientRequestWrapper) {
				((QNPWebClientRequestWrapper)clientRequest).setClientUserSession(clientUserSession);
				((QNPWebClientRequestWrapper)clientRequest).setClientJwtAccessToken(jwtAccessToken);				
			}
			
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	4. 클라이언트의 세션 정보와 JWT 토큰 정보가 존재 시, 키 값을 이용하여 키가 동일하지 않은 경우, 예외 처리 후 작업을 종료한다
			//	세션 정보의 sessionKey 와 JWT 토큰의 PAYLOAD CLAIMS의 sub 정보를 비교한다
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			if(!SBNUtils.isNull(clientUserSession) && !SBNUtils.isNull(jwtAccessToken)) {
				if(!clientUserSession.getSessionKey().equals(jwtAccessToken.getJwtTokenPayload().getJwtTokenClaimValue(SBNJwtTokenPayloadItemType.CLAIMS_SUBJECT_TYPE.getJwtClaimsItemKeyCode()))) {
					throw new SBNCommonException(errorReason, errorCode, errorMessage);
				}
			}
			
			// ////////////////////////////////////////////////////////////////////////////////////////////////////
			//	4. 클라이언트 요청에 대한 인증 작업 정상 수행 시 true를 전달 후 작업을 종료한다
			// ////////////////////////////////////////////////////////////////////////////////////////////////////
			return true;
		} catch(SBNCommonException commonException) {
			throw commonException;
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
				errorMessage = exception.getMessage();
			} else {
				errorMessage = exception.toString();
			}
			throw new SBNCommonException(errorReason, errorCode, errorMessage);
		}
	}
}
