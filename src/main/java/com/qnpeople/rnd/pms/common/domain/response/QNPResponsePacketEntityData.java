package com.qnpeople.rnd.pms.common.domain.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kr.co.sbn.platformhub.framework.core.common.base.SBNReasonInterface;
import kr.co.sbn.platformhub.framework.core.common.data.entities.SBNResponseData;
import kr.co.sbn.platformhub.framework.core.common.data.entities.SBNResponseErrorData;
import kr.co.sbn.platformhub.framework.core.common.data.entities.SBNResponsePacketEntityData;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.EqualsAndHashCode;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.response
 * @Filename		: QNPResponsePacketEntityData.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.03.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트 요청에 대한 수행 결과 응답 데이터 속성 객체를 전달하기 위한 패킷 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class QNPResponsePacketEntityData extends SBNResponsePacketEntityData implements QNPResponsePacketEntity {

	/**
	 * 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 */
	public QNPResponsePacketEntityData() {
		this(SBNUtils.createSystemTransactionId(true), null);
	}
		
	/**
	 * 클라이언트의 요청에 대한 수행 오류 발생시, 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 			클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	error			클라이언트의 요청에 대한 수행 오류 발생시, 프레임워크 내부 어플리케이션 수행 오류 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, SBNResponseErrorData error) {
		super(txId, error);
	}
	
	/**
	 * 클라이언트의 요청에 대해 정상 수행 시, 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 			클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	reason		프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 인터페이스 객체
	 * @param 	response	클라이언트의 요청에 대해 정상 수행 시, 프레임워크 내부 어플리케이션 수행 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, SBNReasonInterface reason, SBNResponseData response) {
		this(txId, reason, response, null);
	}
	
	/**
	 * 클라이언트의 요청에 대한 수행 오류 발생시, 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 			클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	reason		프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 인터페이스 객체
	 * @param 	error			클라이언트의 요청에 대한 수행 오류 발생시, 프레임워크 내부 어플리케이션 수행 오류 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, SBNReasonInterface reason, SBNResponseErrorData error) {
		this(txId, reason, null, error);
	}
	
	/**
	 * 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 			클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	reason		프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 인터페이스 객체
	 * @param 	response	프레임워크 내부 어플리케이션 수행 결과 응답 데이터 객체
	 * @param 	error			프레임워크 내부 어플리케이션 수행 오류 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, SBNReasonInterface reason, SBNResponseData response, SBNResponseErrorData error) {
		super(txId, reason, response, error);
	}
	
	/**
	 * 클라이언트의 요청에 대해 정상 수행 시, 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 			클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	status			프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 유형 값 정보
	 * @param 	response	클라이언트의 요청에 대해 정상 수행 시, 프레임워크 내부 어플리케이션 수행 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, Integer status, SBNResponseData response) {
		this(txId, status, response, null);
	}
	
	/**
	 * 클라이언트의 요청에 대한 수행 오류 발생시, 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 			클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	status			프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 유형 값 정보
	 * @param 	error			클라이언트의 요청에 대한 수행 오류 발생시, 프레임워크 내부 어플리케이션 수행 오류 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, Integer status, SBNResponseErrorData error) {
		this(txId, status, null, error);
	}
	
	/**
	 * 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 			클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	status			프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 유형 값 정보
	 * @param 	response	프레임워크 내부 어플리케이션 수행 결과 응답 데이터 객체
	 * @param 	error			프레임워크 내부 어플리케이션 수행 오류 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, Integer status, SBNResponseData response, SBNResponseErrorData error) {
		super(txId, status, response, error);
	}
	
	/**
	 * 클라이언트의 요청에 대해 정상 수행 시, 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 						클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	responseHttpStatus	프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 유형 객체
	 * @param 	response				클라이언트의 요청에 대해 정상 수행 시, 프레임워크 내부 어플리케이션 수행 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, HttpStatus responseHttpStatus, SBNResponseData response) {
		this(txId, responseHttpStatus, response, null);
	}
	
	/**
	 * 클라이언트의 요청에 대한 수행 오류 발생시, 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 						클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	responseHttpStatus	프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 유형 객체
	 * @param 	error						클라이언트의 요청에 대한 수행 오류 발생시, 프레임워크 내부 어플리케이션 수행 오류 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, HttpStatus responseHttpStatus, SBNResponseErrorData error) {
		this(txId, responseHttpStatus, null, error);
	}
	
	/**
	 * 프레임워크 내부 기본 제공 프레임워크 내부 수행 결과 응답 패킷 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	txId 						클라이언트 수행 요청의 수행 결과에 대한 고유 트랜잭션 ID 정보
	 * @param 	responseHttpStatus	프레임워크 내부 어플리케이션 수행 WEB 응답 결과 상태 유형 객체
	 * @param 	response				프레임워크 내부 어플리케이션 수행 결과 응답 데이터 객체
	 * @param 	error						프레임워크 내부 어플리케이션 수행 오류 결과 응답 데이터 객체
	 */
	public QNPResponsePacketEntityData(String txId, HttpStatus responseHttpStatus, SBNResponseData response, SBNResponseErrorData error) {
		super(txId, responseHttpStatus, response, error);		
	}
}
