package com.qnpeople.rnd.pms.utils;

import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPSecurityException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.utils
 * @Filename		: QNPAuthUtils.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.08.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 웹 클라이언트 개인 정보 인증 관련 유틸리티 모듈 제공 클래스
 * =================================================================================
 */
@Slf4j
public class QNPAuthUtils {

	/**
	 * 사용자의 로그인 비밀번호에 대한 비밀번호 생성 정책 및 규칙에 맞는 지의 여부와 유효성 체크 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	userLoginPwd					사용자의 로그인 비밀번호 정보
	 * @return		전달된 사용자의 비밀번호에 대한 정책 및 유효성 체크 수행 결과 Flag. 유효한 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPSecurityException		사용자의 비밀번호 생성 규칙 및 유효성 체크 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public static final Boolean checkUserLoginPwdValidation(String userLoginPwd) throws QNPSecurityException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_SECURITY_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";		
		try {
			//	비밀번호 생성 규칙에 적용하여 유효성 체크 작업 정의 필요 ( 정책 수립 후 반영 예정)
			return true;
		} catch(Exception exception) {
			throw new QNPSecurityException(exception);
		}
	}
	
	/**
	 * 	전달된 사용자의 이전 비밀번호 와 신규 비밀번호를 비교하여 정책 및 유효성 체크 작업 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	oldLoginPwd				이전 사용자의 로그인 비밀번호 정보
	 * @param 	newLoginPwd				변경 대상 사용자의 로그인 비밀번호 정보
	 * @return		전달된 사용자 로그인 비밀번호에 대한 유효성 체크 작업 수행 결과 Flag. 유효한 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPSecurityException	전달된 이전 및 신규 사용자 비밀번호 비교 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final Boolean compareUserLoginPwd(String oldLoginPwd, String newLoginPwd) throws QNPSecurityException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_SECURITY_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		try {
			if(SBNUtils.isNull(newLoginPwd)) {
				errorMessage = "변경할 신규 비밀번호 정보 미 전달 오류.";
				throw new QNPSecurityException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(oldLoginPwd)) {
				return true;
			}
			//	추가 비교 사항 존재 시, 추가 할 것
			return true;
		} catch(Exception exception) {
			throw new QNPSecurityException(exception);
		}
	}
}
