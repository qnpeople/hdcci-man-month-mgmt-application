package com.qnpeople.rnd.pms.exceptions;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import kr.co.sbn.platformhub.framework.securities.exceptions.SBNSecurityException;
import lombok.EqualsAndHashCode;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.exceptions
 * @Filename		: QNPSecurityException.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  3.0		2026.10.08.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  프레임워크 내부 기본 제공 웹 요청 클라이언트 요청 정보에 대한 보안 작업 수행 오류 발생 시 예외 처리를 위한 최상위 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class QNPSecurityException extends SBNSecurityException {

	/**
	 * QNP 어플리케이션 프레임워크 내부 수행 중 보안 관련 오류 발생 시 예외 처리를 위한 최상위 예외 처리 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 */
	public QNPSecurityException() {
		super();
		this.errorReason = QNPReasonCode.FRAMEWORK_SECURITY_ERROR;
		if(!SBNUtils.isNull(errorReason)) {
			this.errorHttpStatus = errorReason.getReasonHttpStaus();
			this.errorCode = errorReason.getReasonCode();
			this.errorMessage = errorReason.getReasonMessage();
		}
	}
	
	/**
	 * QNP 어플리케이션 프레임워크 내부 수행 중 보안 관련 오류 발생 시 예외 처리를 위한 최상위 예외 처리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	errorReason		오류 발생 시, 발생 오류 사유 유형 인터페이스 객체
	 * @param 	errorCode			오류 발생 시, 발생 오류 코드 정보
	 * @param 	errorMessage		오류 발생 시, 발생 오류 메시지 문자열 정보
	 */
	public QNPSecurityException(QNPReasonInterface errorReason, String errorCode, String errorMessage) {
		super(errorReason, errorCode, errorMessage);
	}
	
	/**
	 * QNP 어플리케이션 프레임워크 내부 수행 중 보안 관련 오류 발생 시 예외 처리를 위한 최상위 예외 처리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	errorHttpStatus	오류 발생 시, 발생 오류 WEB 수행 오류 응답 유형 객체
	 * @param 	errorCode			오류 발생 시, 발생 오류 코드 정보
	 * @param 	errorMessage		오류 발생 시, 발생 오류 메시지 문자열 정보
	 */
	public QNPSecurityException(HttpStatus errorHttpStatus, String errorCode, String errorMessage) {
		super(errorHttpStatus, errorCode, errorMessage);
	}
	
	/**
	 * QNP 어플리케이션 프레임워크 내부 수행 중 보안 관련 오류 발생 시 예외 처리를 위한 최상위 예외 처리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	errorCode			오류 발생 시, 발생 오류 코드 정보
	 * @param 	errorMessage		오류 발생 시, 발생 오류 메시지 정보
	 */
	public QNPSecurityException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	/**
	 * QNP 어플리케이션 프레임워크 내부 수행 중 보안 관련 오류 발생 시 예외 처리를 위한 최상위 예외 처리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	errorMessage		오류 발생 시, 발생 오류 메시지 정보
	 */
	public QNPSecurityException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * QNP 어플리케이션 프레임워크 내부 수행 중 보안 관련 오류 발생 시 예외 처리를 위한 최상위 예외 처리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	cause		오류 발생 시, 발생 오류 객체
	 */
	public QNPSecurityException(Throwable cause) {
		this(null, cause);
	}
	
	/**
	 * QNP 어플리케이션 프레임워크 내부 수행 중 보안 관련 오류 발생 시 예외 처리를 위한 최상위 예외 처리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	errorReason	오류 발생 시, 발생 오류 사유 인터페이스 객체
	 * @param 	cause				오류 발생 시, 발생 오류 객체
	 */
	public QNPSecurityException(QNPReasonInterface errorReason, Throwable cause) {
		super(errorReason, cause);
	}
}
