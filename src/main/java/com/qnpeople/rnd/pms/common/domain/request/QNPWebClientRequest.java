package com.qnpeople.rnd.pms.common.domain.request;

import kr.co.sbn.platformhub.framework.core.common.base.domain.client.SBNClientRequest;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.data.SBNJwtAccessToken;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.request
 * @Filename		: QNPWebClientRequest.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.08.20.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트 요청 정보를 관리하고 전달하는 작업을 수행하는 최상위 인터페이스 클래스
 * =================================================================================
 */
public interface QNPWebClientRequest extends SBNClientRequest {

	/**
	 * 전달된 클라이언트로부터 전달된 JWT 토큰 접근 객체를 설정하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	clientJwtAccessToken	클라이언트로부터 전달된 JWT 토큰 접근 객체
	 */
	public void setClientJwtAccessToken(SBNJwtAccessToken clientJwtAccessToken);
	
	/**
	 * 클라이언트로부터 전달된 JWT 토큰 접근 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		클라이언트로부터 전달된 JWT 토큰 접근 객체
	 */
	public SBNJwtAccessToken getClientJwtAccessToken();
}
