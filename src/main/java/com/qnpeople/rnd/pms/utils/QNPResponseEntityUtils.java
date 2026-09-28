package com.qnpeople.rnd.pms.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qnpeople.rnd.pms.common.domain.response.QNPResponseData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseErrorData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponsePacketEntity;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.utils.SBNResponseEntityUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.utils
 * @Filename		: QNPResponseEntityUtils.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.02.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 웹 클라이언트 요청 수행 결과 응답 패킷 전달 작업을 위한 Response Entity 관련 유틸리티 모듈 제공 클래스
 * =================================================================================
 */
@Slf4j
public class QNPResponseEntityUtils {

	/**
	 * WEB 수행 결과 응답 상태 유형 객체와 수행 결과 응답 패킷 구성 객체를 전달 받아 클라이언트의 요청 수행 결과 응답 패킷 객체를 구성하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.02
	 * @param 	httpStatus					클라이언트 요청 수행 결과 WEB 응답 상태 유형 객체
	 * @return		전달된 WEB 수행 결과 응답 상태 유형 및 수행 결과 응답 패킷 구성 객체에 대한 최종 클라이언트로의 전달 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 수행 결과 응답 패킷 구축 작업 중 오류 발생 시 예외 처리 Exception	
	 */
	public static final ResponseEntity<?> buildResponseEntityPacket(final HttpStatus httpStatus) throws QNPWebException {
		return SBNResponseEntityUtils.buildResponseEntityPacket(httpStatus, null);
	}
	
	/**
	 * WEB 수행 결과 응답 상태 유형 객체와 수행 결과 응답 패킷 구성 객체를 전달 받아 클라이언트의 요청 수행 결과 응답 패킷 객체를 구성하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.02
	 * @param 	httpStatus					클라이언트 요청 수행 결과 WEB 응답 상태 유형 객체
	 * @param 	responsePacketEntity	클라이언트의 요청 수행 결과 응답 객체
	 * @return		전달된 WEB 수행 결과 응답 상태 유형 및 수행 결과 응답 패킷 구성 객체에 대한 최종 클라이언트로의 전달 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 수행 결과 응답 패킷 구축 작업 중 오류 발생 시 예외 처리 Exception	
	 */
	public static final ResponseEntity<?> buildResponseEntityPacket(final HttpStatus httpStatus, final Object responsePacketEntity) throws QNPWebException {
		return SBNResponseEntityUtils.buildResponseEntityPacket(httpStatus, responsePacketEntity);
	}
	
	/**
	 * 프레임워크 내부 제공 클라이언트의 요청에 대한 수행 결과 응답 패킷 구성 객체를 전달 받아 클라이언트의 요청 수행 결과 응답 패킷 객체를 구성하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.02
	 * @param 	responsePacketEntity	프레임워크 내부 제공 클라이언트의 요청 수행 결과 응답 객체
	 * @return		전달된 WEB 수행 결과 응답 상태 유형 및 수행 결과 응답 패킷 구성 객체에 대한 최종 클라이언트로의 전달 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 수행 결과 응답 패킷 구축 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final ResponseEntity<?> buildResponseEntityPacket(final QNPResponsePacketEntity responsePacketEntity) throws QNPWebException {
		return SBNResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
	}
	
	/**
	 * 전달된 정보를 이용하여 어플리케이션으로의 클라이언트 요청 수행 결과 응답 패킷 객체를 구축하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.02
	 * @param 	txId							클라이언트의 요청 수행 추적을 위한 고유 트랜잭션 ID 정보
	 * @param 	httpStatus					클라이언트의 요청 수행 결과 WEB 응답 상태 유형 객체
	 * @param 	responseData				클라이언트의 요청 수행 결과 응답 패킷 구성 세부 정보 객체
	 * @return		전달된 WEB 수행 결과 응답 상태 유형 및 수행 결과 응답 패킷 구성 객체에 대한 최종 클라이언트로의 전달 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 수행 결과 응답 패킷 구축 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final ResponseEntity<?> buildResponseEntityPacket(final String txId, final HttpStatus httpStatus, final Object responseData) throws QNPWebException {
		return SBNResponseEntityUtils.buildResponseEntityPacket(txId, httpStatus, responseData);
	}
	
	/**
	 * 전달된 정보를 이용하여 어플리케이션으로의 클라이언트 요청 수행 결과 응답 패킷 객체를 구축하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.02
	 * @param 	txId							클라이언트의 요청 수행 추적을 위한 고유 트랜잭션 ID 정보
	 * @param 	httpStatus					클라이언트의 요청 수행 결과 WEB 응답 상태 유형 객체
	 * @param 	responseData				프레임워크 내부 제공 클라이언트의 요청 수행 결과 응답 패킷 구성 세부 정보 객체
	 * @return		전달된 WEB 수행 결과 응답 상태 유형 및 수행 결과 응답 패킷 구성 객체에 대한 최종 클라이언트로의 전달 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 수행 결과 응답 패킷 구축 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final ResponseEntity<?> buildResponseEntityPacket(final String txId, final HttpStatus httpStatus, final QNPResponseData responseData) throws QNPWebException {
		return SBNResponseEntityUtils.buildResponseEntityPacket(txId, httpStatus, responseData);
	}
	
	/**
	 * 전달된 정보를 이용하여 어플리케이션으로의 클라이언트 요청 수행 결과 응답 패킷 객체를 구축하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.02
	 * @param 	txId							클라이언트의 요청 수행 추적을 위한 고유 트랜잭션 ID 정보
	 * @param 	httpStatus					클라이언트의 요청 수행 결과 WEB 응답 상태 유형 객체
	 * @param 	errorResponseData		프레임워크 내부 제공 클라이언트의 요청 수행 오류 결과 응답 패킷 구성 세부 정보 객체
	 * @return		전달된 WEB 수행 결과 응답 상태 유형 및 수행 결과 응답 패킷 구성 객체에 대한 최종 클라이언트로의 전달 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 수행 결과 응답 패킷 구축 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final ResponseEntity<?> buildResponseEntityPacket(final String txId, final HttpStatus httpStatus, final QNPResponseErrorData errorResponseData) throws QNPWebException {
		return SBNResponseEntityUtils.buildResponseEntityPacket(txId, httpStatus, errorResponseData);
	}
	
	/**
	 * 전달된 정보를 이용하여 어플리케이션으로의 클라이언트 요청 수행 결과 응답 패킷 객체를 구축하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.02
	 * @param 	txId							클라이언트의 요청 수행 추적을 위한 고유 트랜잭션 ID 정보
	 * @param 	httpStatus					클라이언트의 요청 수행 결과 WEB 응답 상태 유형 객체
	 * @param 	errorException				클라이언트의 요청 수행 중 발생 예외 처리 Exception 객체
	 * @return		전달된 WEB 수행 결과 응답 상태 유형 및 수행 결과 응답 패킷 구성 객체에 대한 최종 클라이언트로의 전달 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 수행 결과 응답 패킷 구축 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final ResponseEntity<?> buildResponseEntityPacket(final String txId, final HttpStatus httpStatus, final Exception errorException) throws QNPWebException {
		return SBNResponseEntityUtils.buildResponseEntityPacket(txId, httpStatus, errorException);
	}
	
	/**
	 * 전달된 정보를 이용하여 어플리케이션으로의 클라이언트 요청 수행 결과 응답 패킷 객체를 구축하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.02
	 * @param 	txId							클라이언트의 요청 수행 추적을 위한 고유 트랜잭션 ID 정보
	 * @param 	httpStatus					클라이언트의 요청 수행 결과 WEB 응답 상태 유형 객체
	 * @param 	errorException				클라이언트의 요청 수행 중 발생 프레임워크 내부 정의 예외 처리 Exception 객체
	 * @return		전달된 WEB 수행 결과 응답 상태 유형 및 수행 결과 응답 패킷 구성 객체에 대한 최종 클라이언트로의 전달 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 수행 결과 응답 패킷 구축 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public static final ResponseEntity<?> buildResponseEntityPacket(final String txId, final HttpStatus httpStatus, final QNPWebException errorException) throws QNPWebException {
		return SBNResponseEntityUtils.buildResponseEntityPacket(txId, httpStatus, errorException);
	}
}
