package com.qnpeople.rnd.pms.exceptions;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;

import kr.co.sbn.platformhub.framework.core.common.web.exceptions.SBNWebException;
import lombok.EqualsAndHashCode;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.exceptions
 * @Filename		: QNPWebException.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  3.0		2026.09.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 수행 오류 발생 시 예외 처리를 위한 최상위 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class QNPWebException extends SBNWebException {
	
	/**
	 * 프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 수행 오류 발생 시 예외 처리를 위한 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 */
	public QNPWebException() {
		super();
	}
	
	/**
	 * 프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 수행 오류 발생 시 예외 처리를 위한 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	errorReason		오류 발생 시, 발생 오류 사유 유형 인터페이스 객체
	 * @param 	errorCode			오류 발생 시, 발생 오류 코드 정보
	 * @param 	errorMessage		오류 발생 시, 발생 오류 메시지 문자열 정보
	 */
	public QNPWebException(QNPReasonInterface errorReason, String errorCode, String errorMessage) {
		super(errorReason, errorCode, errorMessage);
	}
	
	/**
	 * 프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 수행 오류 발생 시 예외 처리를 위한 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	errorHttpStatus	오류 발생 시, 발생 오류 WEB 수행 오류 응답 유형 객체
	 * @param 	errorCode			오류 발생 시, 발생 오류 코드 정보
	 * @param 	errorMessage		오류 발생 시, 발생 오류 메시지 문자열 정보
	 */
	public QNPWebException(HttpStatus errorHttpStatus, String errorCode, String errorMessage) {
		super(errorHttpStatus, errorCode, errorMessage);
	}
	
	/**
	 * 프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 수행 오류 발생 시 예외 처리를 위한 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	errorCode			오류 발생 시, 발생 오류 코드 정보
	 * @param 	errorMessage		오류 발생 시, 발생 오류 메시지 정보
	 */
	public QNPWebException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	/**
	 * 프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 수행 오류 발생 시 예외 처리를 위한 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	errorMessage		오류 발생 시, 발생 오류 메시지 정보
	 */
	public QNPWebException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * 프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 수행 오류 발생 시 예외 처리를 위한 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	cause		오류 발생 시, 발생 오류 객체
	 */
	public QNPWebException(Throwable cause) {
		this(null, cause);
	}
	
	/**
	 * 프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 수행 오류 발생 시 예외 처리를 위한 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	errorReason	오류 발생 시, 발생 오류 사유 인터페이스 객체
	 * @param 	cause				오류 발생 시, 발생 오류 객체
	 */
	public QNPWebException(QNPReasonInterface errorReason, Throwable cause) {
		super(errorReason, cause);
	}
}
