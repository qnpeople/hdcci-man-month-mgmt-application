package com.qnpeople.rnd.pms.utils;

import com.qnpeople.rnd.pms.common.auth.QNPAuth;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.common.web.session.QNPWebClientUserSessionData;
import com.qnpeople.rnd.pms.constant.QNPWebConstant;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.utils
 * @Filename		: QNPWebUtils.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.14.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 웹 클라이언트 개인 정보 인증 관련 유틸리티 모듈 제공 클래스
 * =================================================================================
 */
@Slf4j
public class QNPWebUtils {

	/**
	 * 전달된 클라이언트 요청 전달 객체와 클라이언트 사용자 세션 정보 객체를 이용하여 서버의 세션에 등록 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	httpRequest
	 * @param 	webClientUserSessionData		서버 세션에 등록할 클라이언트에 대한 서버 세션 데이터 객체
	 * @return		전달된 클라이언트에 대한 서버 세션 데이터 객체에 대한 서버 세션 등록 작업 수행 결과 Flag. 정상 등록 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException				웹 어플리케이션의 세션 관련 처리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public static final Boolean registerWebClientSessionData(HttpServletRequest httpRequest, QNPWebClientUserSessionData webClientUserSessionData) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_AUTH_COMMON_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		HttpSession httpSession = null;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(httpRequest)) {
				errorMessage = "클라이언트 요청 전달 객체 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(webClientUserSessionData)) {
				errorMessage = "세션 등록 대상 클라이언트 세션 데이터 전달 객체 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	
			httpSession = httpRequest.getSession(true);
			if(SBNUtils.isNull(httpSession)) {
				errorMessage = "클라이언트에 대한 서버 세션 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//
			httpSession.setAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME, webClientUserSessionData);
			if(!SBNUtils.isNull(httpSession.getAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME)) && 
				(httpSession.getAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME) instanceof QNPWebClientUserSessionData)) {
				resultFlag = true;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	
	/**
	 * 전달된 클라이언트 요청 전달 객체로부터 세션에 설정 되어 있는 클라이언트에 대한 서버 세션 데이터 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	httpRequest				클라이언트 요청 전달 HttpServletRequest 인터페이스 객체
	 * @return		전달된 클라이언트 요청 전달 객체로부터 추출한 클라이언트에 대한 서버 세션 데이터 객체
	 * @throws 	QNPWebException		웹 어플리케이션의 세션 관련 처리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public static final QNPWebClientUserSessionData extractWebClientSessionData(HttpServletRequest httpRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_AUTH_COMMON_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		HttpSession httpSession = null;
		QNPWebClientUserSessionData webClientUserSessionData = null;
		try {
			if(SBNUtils.isNull(httpRequest)) {
				errorMessage = "클라이언트 요청 전달 객체 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			httpSession = httpRequest.getSession();
			if(SBNUtils.isNull(httpSession)) {
				errorMessage = "클라이언트에 대한 서버 세션 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(httpSession.getAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME)) || 
				!(httpSession.getAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME) instanceof QNPWebClientUserSessionData)) {
				errorMessage = "클라이언트에 대한 서버 세션 데이터 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			webClientUserSessionData = (QNPWebClientUserSessionData)httpSession.getAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME);
			if(SBNUtils.isNull(webClientUserSessionData)) {
				errorMessage = "클라이언트에 대한 서버 세션 데이터 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			return webClientUserSessionData;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 클라이언트 요청 전달 객체로부터 세션에 설정되어 있는 클라이언트에 대한 인증 데이터 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	httpRequest			클라이언트 요청 전달 HttpServletRequest 인터페이스 객체
	 * @return		전달된 클라이언트 요청 전달 객체로부터 추출한 클라이언트에 대한 인증 데이터 객체
	 * @throws 	QNPWebException	웹 어플리케이션의 세션 관련 처리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public static final QNPAuth<?> extractQNPAuthFromClientSession(HttpServletRequest httpRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_AUTH_COMMON_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		QNPWebClientUserSessionData webClientUserSessionData = null;
		QNPAuth<?> authData = null;
		try {
			webClientUserSessionData = extractWebClientSessionData(httpRequest);
			if(SBNUtils.isNull(webClientUserSessionData)) {
				errorMessage = "클라이언트에 대한 서버 세션 데이터 미 등록 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			authData = webClientUserSessionData.getSessionAuthData();
			if(SBNUtils.isNull(authData)) {
				errorMessage = "클라이언트에 대한 인증 데이터 객체 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//
			return authData;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 
	 * 
	 * @param httpRequest
	 * @return
	 * @throws QNPWebException
	 */
	public static final Boolean removeWebClientSession(HttpServletRequest httpRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_AUTH_COMMON_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		HttpSession httpSession = null;
		Boolean removeSessionFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(httpRequest)) {
				errorMessage = "클라이언트 요청 전달 객체 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}			
			//	
			httpSession = httpRequest.getSession(true);
			if(SBNUtils.isNull(httpSession)) {
				errorMessage = "클라이언트에 대한 서버 세션 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//
			if(!SBNUtils.isNull(httpSession.getAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME)) && 
			  (httpSession.getAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME) instanceof QNPWebClientUserSessionData)) {
				httpSession.removeAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME);
				if(SBNUtils.isNull(httpSession.getAttribute(QNPWebConstant.CLIENT_SESSION_KEY_NAME))) {
					removeSessionFlag = true;
				}
			}
			return removeSessionFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
