package com.qnpeople.rnd.pms.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPSecurityException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import kr.co.sbn.platformhub.framework.securities.domains.cypher.common.container.SBNCypherConfigPoolContainer;
import kr.co.sbn.platformhub.framework.securities.domains.cypher.common.container.SBNCypherExecutorPoolContainer;
import kr.co.sbn.platformhub.framework.securities.domains.cypher.common.module.aes.aes256.SBNAes256CypherExecutor;
import kr.co.sbn.platformhub.framework.securities.domains.cypher.common.module.md5.SBNMd5CypherExecutor;
import kr.co.sbn.platformhub.framework.securities.domains.cypher.common.module.sha.sha256.SBNSha256CypherExecutor;
import kr.co.sbn.platformhub.framework.securities.domains.cypher.common.module.sha.sha512.SBNSha512CypherExecutor;
import kr.co.sbn.platformhub.framework.securities.domains.cypher.types.SBNCypherMethodologyType;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.utils
 * @Filename		: QNPCypherUtils.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.08.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 웹 클라이언트 요청 수행 암호화 수행 관련 유틸리티 모듈 제공 클래스
 * =================================================================================
 */
@Slf4j
public class QNPCypherUtils {

	/* QNP 어플리케이션 프레임워크 내부 제공 암호화 모듈 수행 설정 Pool 관리 객체 */
	private static SBNCypherConfigPoolContainer<?> cypherConfigPoolContainer;
	/* QNP 어플리케이션 프레임워크 내부 제공 암호화 수행 모듈 Pool 관리 객체 */
	private static SBNCypherExecutorPoolContainer<?> cypherExecutorPoolContainer;
	
	/*
	 * QNP 어플리케이션 프레임워크 내부에서 관리 및 제공하는 보안 관련 암호화 수행 설정 Pool 관리 객체 및 수행 모듈 Pool 관리 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 		cypherConfigPoolContainer		프레임워크 내부 제공 암호화 모듈 수행 설정 Pool 관리 객체
	 * @param 		cypherExecutorPoolContainer		프레임워크 내부 제공 암호화 수행 모듈 Pool 관리 객체
	 */
	@Autowired
	private void setSBNCypherPoolContainer(SBNCypherConfigPoolContainer<?> cypherConfigPoolContainer, SBNCypherExecutorPoolContainer<?> cypherExecutorPoolContainer) {
		QNPCypherUtils.cypherConfigPoolContainer = cypherConfigPoolContainer;
		QNPCypherUtils.cypherExecutorPoolContainer = cypherExecutorPoolContainer;
	}
		
	/**
	 * [ AES256 ]
	 * 
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	cypherMethodolgyTypeCode		프레임워크 내부 정의 보안 관련 암호화 수행 방법론 유형 코드 저보
	 * @return
	 * @throws 	QNPSecurityException				전달된 암호화 방법론 유형 코드에 대한 암호화 수행 설정 객체 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final SBNAes256CypherExecutor getAES256CypherExecutor() throws QNPSecurityException {
		QNPReasonInterface errorReason = QNPReasonCode.CYPHER_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		SBNCypherMethodologyType cypherMethodologyType = SBNCypherMethodologyType.AES_256;
		SBNAes256CypherExecutor aes256CypherExecutor = null;
		try {
			if(SBNUtils.isNull(cypherExecutorPoolContainer)) {
				errorMessage = "프레임워크 내부 제공 암호화 수행 모듈 Pool 관리 객체 미 구축 오류";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			log.info("getAES256CypherExecutor() cypherConfigPoolContainer={}", cypherConfigPoolContainer);
			log.info("getAES256CypherExecutor() cypherExecutorPoolContainer={}", cypherExecutorPoolContainer);			
			//
			if(!cypherExecutorPoolContainer.isContainerPoolModuleExisting(cypherMethodologyType.getCypherMethodolgyTypeCode())) {
				errorMessage = "미사용 설정 암호화 수행 모듈 요청 오류.";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			aes256CypherExecutor = (SBNAes256CypherExecutor)cypherExecutorPoolContainer.getContainerPoolModule(cypherMethodologyType.getCypherMethodolgyTypeCode());
			if(SBNUtils.isNull(aes256CypherExecutor)) {
				errorMessage = "요청 암호화 수행 모듈 추출 실패 오류.";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			return aes256CypherExecutor;
		} catch(QNPSecurityException securityException) {
			throw securityException;
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
				errorMessage = exception.getMessage();
			} else {
				errorMessage = exception.toString();
			}
			throw new QNPSecurityException(errorReason, errorCode, errorMessage);
		}
	}
		
	/**
	 * [ SHA256 ]
	 * 
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	cypherMethodolgyTypeCode		프레임워크 내부 정의 보안 관련 암호화 수행 방법론 유형 코드 저보
	 * @return
	 * @throws 	QNPSecurityException				전달된 암호화 방법론 유형 코드에 대한 암호화 수행 설정 객체 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final SBNSha256CypherExecutor getSHA256CypherExecutor() throws QNPSecurityException {
		QNPReasonInterface errorReason = QNPReasonCode.CYPHER_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		SBNCypherMethodologyType cypherMethodologyType = SBNCypherMethodologyType.SHA_256;
		SBNSha256CypherExecutor sha256CypherExecutor = null;
		try {
			if(SBNUtils.isNull(cypherExecutorPoolContainer)) {
				errorMessage = "프레임워크 내부 제공 암호화 수행 모듈 Pool 관리 객체 미 구축 오류";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			log.info("getAES256CypherExecutor() cypherConfigPoolContainer={}", cypherConfigPoolContainer);
			log.info("getAES256CypherExecutor() cypherExecutorPoolContainer={}", cypherExecutorPoolContainer);			
			//
			if(!cypherExecutorPoolContainer.isContainerPoolModuleExisting(cypherMethodologyType.getCypherMethodolgyTypeCode())) {
				errorMessage = "미사용 설정 암호화 수행 모듈 요청 오류.";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			sha256CypherExecutor = (SBNSha256CypherExecutor)cypherExecutorPoolContainer.getContainerPoolModule(cypherMethodologyType.getCypherMethodolgyTypeCode());
			if(SBNUtils.isNull(sha256CypherExecutor)) {
				errorMessage = "요청 암호화 수행 모듈 추출 실패 오류.";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			return sha256CypherExecutor;
		} catch(QNPSecurityException securityException) {
			throw securityException;
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
				errorMessage = exception.getMessage();
			} else {
				errorMessage = exception.toString();
			}
			throw new QNPSecurityException(errorReason, errorCode, errorMessage);
		}
	}
	
	/**
	 * [ SHA512 ]
	 * 
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	cypherMethodolgyTypeCode		프레임워크 내부 정의 보안 관련 암호화 수행 방법론 유형 코드 저보
	 * @return
	 * @throws 	QNPSecurityException				전달된 암호화 방법론 유형 코드에 대한 암호화 수행 설정 객체 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final SBNSha512CypherExecutor getSHA512CypherExecutor() throws QNPSecurityException {
		QNPReasonInterface errorReason = QNPReasonCode.CYPHER_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		SBNCypherMethodologyType cypherMethodologyType = SBNCypherMethodologyType.SHA_512;
		SBNSha512CypherExecutor sha512CypherExecutor = null;
		try {
			if(SBNUtils.isNull(cypherExecutorPoolContainer)) {
				errorMessage = "프레임워크 내부 제공 암호화 수행 모듈 Pool 관리 객체 미 구축 오류";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			log.info("getAES256CypherExecutor() cypherConfigPoolContainer={}", cypherConfigPoolContainer);
			log.info("getAES256CypherExecutor() cypherExecutorPoolContainer={}", cypherExecutorPoolContainer);			
			//
			if(!cypherExecutorPoolContainer.isContainerPoolModuleExisting(cypherMethodologyType.getCypherMethodolgyTypeCode())) {
				errorMessage = "미사용 설정 SHA512 단방향 해쉬 암호화 수행 모듈 요청 오류.";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			sha512CypherExecutor = (SBNSha512CypherExecutor)cypherExecutorPoolContainer.getContainerPoolModule(cypherMethodologyType.getCypherMethodolgyTypeCode());
			if(SBNUtils.isNull(sha512CypherExecutor)) {
				errorMessage = "요청 SHA512 단방향 해쉬 암호화 수행 모듈 추출 실패 오류.";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			return sha512CypherExecutor;
		} catch(QNPSecurityException securityException) {
			throw securityException;
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
				errorMessage = exception.getMessage();
			} else {
				errorMessage = exception.toString();
			}
			throw new QNPSecurityException(errorReason, errorCode, errorMessage);
		}
	}
	
	/**
	 * [ MD5 ]
	 * 
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	cypherMethodolgyTypeCode		프레임워크 내부 정의 보안 관련 암호화 수행 방법론 유형 코드 저보
	 * @return
	 * @throws 	QNPSecurityException				전달된 암호화 방법론 유형 코드에 대한 암호화 수행 설정 객체 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final SBNMd5CypherExecutor getMD5CypherExecutor() throws QNPSecurityException {
		QNPReasonInterface errorReason = QNPReasonCode.CYPHER_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		SBNCypherMethodologyType cypherMethodologyType = SBNCypherMethodologyType.MD5;
		SBNMd5CypherExecutor md5CypherExecutor = null;
		try {
			if(SBNUtils.isNull(cypherExecutorPoolContainer)) {
				errorMessage = "프레임워크 내부 제공 암호화 수행 모듈 Pool 관리 객체 미 구축 오류";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			log.info("getAES256CypherExecutor() cypherConfigPoolContainer={}", cypherConfigPoolContainer);
			log.info("getAES256CypherExecutor() cypherExecutorPoolContainer={}", cypherExecutorPoolContainer);			
			//
			if(!cypherExecutorPoolContainer.isContainerPoolModuleExisting(cypherMethodologyType.getCypherMethodolgyTypeCode())) {
				errorMessage = "미사용 설정 MD5 단방향 해쉬 암호화 수행 모듈 요청 오류.";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			md5CypherExecutor = (SBNMd5CypherExecutor)cypherExecutorPoolContainer.getContainerPoolModule(cypherMethodologyType.getCypherMethodolgyTypeCode());
			if(SBNUtils.isNull(md5CypherExecutor)) {
				errorMessage = "요청 MD5 단방향 해쉬 암호화 수행 모듈 추출 실패 오류.";
				throw new QNPSecurityException(errorReason, errorCode, errorMessage);
			}
			return md5CypherExecutor;
		} catch(QNPSecurityException securityException) {
			throw securityException;
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
				errorMessage = exception.getMessage();
			} else {
				errorMessage = exception.toString();
			}
			throw new QNPSecurityException(errorReason, errorCode, errorMessage);
		}
	}
	
	/**
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	encryptableString
	 * @throws 	QNPSecurityException
	 */
	public static final void testSha512Encrypt(String encryptableString) throws QNPSecurityException {
		
		//String encrytedString = "";
		try {			
			
		} catch (Exception exception) {
			throw new QNPSecurityException(exception);
		}
	}
}
