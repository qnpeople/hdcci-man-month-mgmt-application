package com.qnpeople.rnd.pms.common.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kr.co.sbn.platformhub.framework.core.common.base.domain.client.SBNClientRequestAdaptor;
import kr.co.sbn.platformhub.framework.core.utils.SBNDateUtils;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.data.SBNJwtAccessToken;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.request
 * @Filename		: QNPWebClientRequestWrapper.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.08.20.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트 요청 정보를 관리하고 전달하는 작업을 수행하는 최상위 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
public class QNPWebClientRequestWrapper extends SBNClientRequestAdaptor implements QNPWebClientRequest {

	/** 클라이언트로부터 전달된 JWT 토큰 접근 객체 */ 
	protected SBNJwtAccessToken clientJwtAccessToken;
	/** 목록 조회 요청 페이지 번호 정보 */
	protected Integer pageNo;
	
	/**
	 * QNP 어플리케이션 웹 클라이언트 수행 요청 정보를 전달하는 작업을 수행하는 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 */
	public QNPWebClientRequestWrapper() {
		super("");
	}
	
	/**
	 * QNP 어플리케이션 웹 클라이언트 수행 요청 정보를 전달하는 작업을 수행하는 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	txId		클라이언트 요청에 대한 수행 추적 고유 트랜잭션 ID 정보
	 */
	public QNPWebClientRequestWrapper(String txId) {
		this(txId, null);
	}
		
	/**
	 * 웹 클라이언트 수행 요청 정보를 전달하는 작업을 수행하는 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	txId						클라이언트 요청에 대한 수행 추적 고유 트랜잭션 ID 정보
	 * @param 	requestStartTime		클라이언트 요청 수행 시작 시간 정보
	 * @param 	clientJwtToken			클라이언트의 인증 수행을 위한 JWT 인증 토큰 인터페이스 객체
	 */
	public QNPWebClientRequestWrapper(String txId, Long requestStartTime) {
		super(txId, requestStartTime);
	}
	
	/**
	 * 전달된 클라이언트로부터 전달된 JWT 토큰 접근 객체를 설정하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	clientJwtAccessToken	클라이언트로부터 전달된 JWT 토큰 접근 객체
	 */
	public void setClientJwtAccessToken(SBNJwtAccessToken clientJwtAccessToken) {
		if(!SBNUtils.isNull(clientJwtAccessToken)) {
			this.clientJwtAccessToken = clientJwtAccessToken;
		}
	}
	
	/**
	 * 클라이언트로부터 전달된 JWT 토큰 접근 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		클라이언트로부터 전달된 JWT 토큰 접근 객체
	 */
	public SBNJwtAccessToken getClientJwtAccessToken() {
		return clientJwtAccessToken;
	}
	
	/**
	 * 목록 조회 요청 페이지 번호 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	pageNo		목록 조회 요청 페이지 번호 정보
	 */
	public void setPageNo(Integer pageNo) {
		if(!SBNUtils.isNull(pageNo) && (pageNo > 0)) {
			this.pageNo = pageNo;
		}
	}
	
	/**
	 * 목록 조회 요청 페이지 번호 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		목록 조회 요청 페이지 번호 정보
	 */
	public Integer getPageNo() {
		return pageNo;
	}
	
	/**
	 * 프레임워크 내부 기본 제공 요청 정보의 데이터 속성 객체의 세부 정보에 대한 문자열 정보를 구성하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		프레임워크 내부 기본 제공 요청 정보의 데이터 속성 객체의 세부 정보에 대한 문자열 정보
	 */
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(txId)) {
			info.append(", ");
			info.append("txId=").append(txId);
		}
		if(!SBNUtils.isNull(getCreateDateTime())) {
			info.append(", ");
			info.append("createTime=").append(SBNDateUtils.dateToString(getCreateDateTime()));
		}
		if(!SBNUtils.isNull(clientUserSession)) {
			info.append(", ");
			info.append("clientUserSession=").append(clientUserSession.toStringInfo());
		}
		if(!SBNUtils.isNull(clientJwtAccessToken)) {
			info.append(", ");
			info.append("clientJwtAccessToken=").append(clientJwtAccessToken.toStringInfo());
		}
		if(!SBNUtils.isNull(pageNo) && (pageNo > 0)) {
			info.append(", ");
			info.append("pageNo=").append(pageNo);
		}		
		if(!SBNUtils.isNull(getRequestStartDateTime())) {
			info.append(", ");
			info.append("requestStartTime=").append(SBNDateUtils.dateToString(getRequestStartDateTime()));
		}
		if(!SBNUtils.isNull(getRequestEndDateTime())) {
			info.append(", ");
			info.append("requestEndTime=").append(SBNDateUtils.dateToString(getRequestEndDateTime()));
		}
		if(!SBNUtils.isNull(getRequestDurationTime())) {
			info.append(", ");
			info.append("requesDurationTime=").append(getRequestDurationTime()).append("ms");
		}
		info.append(" }");
		return info.toString();
	}
}
