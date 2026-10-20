package com.qnpeople.rnd.pms.common.web.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.auth.QNPAuth;

import kr.co.sbn.platformhub.framework.core.common.web.common.data.session.SBNWebClientUserSession;
import kr.co.sbn.platformhub.framework.core.common.web.common.data.session.SBNWebClientUserSessionData;
import kr.co.sbn.platformhub.framework.core.constants.SBNDateFormatConstant;
import kr.co.sbn.platformhub.framework.core.utils.SBNDateUtils;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.EqualsAndHashCode;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.auth
 * @Filename		: QNPWebClientUserSessionData.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.14.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션 내부 기본 제공 WEB 어플리케이션 요청 클라이언트 사용자의 서버 수행 세션 정보를 관리하는 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class QNPWebClientUserSessionData extends SBNWebClientUserSessionData implements SBNWebClientUserSession {

	/**
	 * 웹 어플리케이션 내부 기본 제공 WEB 어플리케이션 요청 클라이언트 사용자의 서버 수행 세션 정보 관리 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 */
	public QNPWebClientUserSessionData() {
		super();
	}
	
	/**
	 * 웹 어플리케이션 내부 기본 제공 WEB 어플리케이션 요청 클라이언트 사용자의 서버 수행 세션 정보 관리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	sessionName		웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 구분 명 정보
	 */
	public QNPWebClientUserSessionData(String sessionName) {
		this(null, sessionName, null);
	}
	
	/**
	 * 웹 어플리케이션 내부 기본 제공 WEB 어플리케이션 요청 클라이언트 사용자의 서버 수행 세션 정보 관리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	sessionKey			웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 키 정보
	 * @param 	sessionName		웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 구분 명 정보
	 */
	public QNPWebClientUserSessionData(String sessionKey, String sessionName) {
		this(sessionKey, sessionName, null);
	}
	
	/**
	 * 웹 어플리케이션 내부 기본 제공 WEB 어플리케이션 요청 클라이언트 사용자의 서버 수행 세션 정보 관리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	sessionName		웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 구분 명 정보
	 * @param 	sessionAuthData	웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 사용자 인증 정보 객체
	 */
	public QNPWebClientUserSessionData(String sessionName, QNPAuth<?> sessionAuthData) {
		this(null, sessionName, sessionAuthData);
	}
	
	/**
	 * 웹 어플리케이션 내부 기본 제공 WEB 어플리케이션 요청 클라이언트 사용자의 서버 수행 세션 정보 관리 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	sessionKey			웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 키 정보
	 * @param 	sessionName		웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 구분 명 정보
	 * @param 	sessionAuthData	웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 사용자 인증 정보 객체
	 */
	public QNPWebClientUserSessionData(String sessionKey, String sessionName, QNPAuth<?> sessionAuthData) {
		super(null, sessionKey, sessionName, sessionAuthData);
	}
		
	/**
	 * 웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 키 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	sessionKey		웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 키 정보
	 */
	public void setSessionKey(String sessionKey) {
		if(!SBNUtils.isNull(sessionKey)) {
			this.sessionKey = sessionKey;
		}
	}
	
	/**
	 * 웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 키 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @return		웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 키 정보
	 */
	public String getSessionKey() {
		return sessionKey;
	}
	
	/**
	 * 웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 구분 명 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	sessionName	웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 구분 명 정보
	 */
	public void setSessionName(String sessionName) {
		if(!SBNUtils.isNull(sessionName)) {
			this.sessionName = sessionName;
		}
	}
	
	/**
	 * 웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 구분 명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @return		웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 세션 구분 명 정보
	 */
	public String getSessionName() {
		return sessionName;
	}
	
	/**
	 * 웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 사용자 인증 정보 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	sessionAuthData	웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 사용자 인증 정보 객체
	 */
	public void setSessionAuthData(QNPAuth<?> sessionAuthData) {
		if(!SBNUtils.isNull(sessionAuthData)) {
			this.sessionAuthData = sessionAuthData;
		}
	}
	
	/**
	 * 웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 사용자 인증 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @return		웹 어플리케이션 내부 WEB 수행 클라이언트 요청 수행 사용자 인증 정보 객체
	 */
	public QNPAuth<?> getSessionAuthData() {
		if(!SBNUtils.isNull(sessionAuthData)) {
			return (QNPAuth<?>)sessionAuthData;
		}
		return null;
	}
	
	/**
	 * 웹 어플리케이션 내부 제공 WEB 수행 클라이언트 세션 데이터 속성 객체의 세부 정보에 대한 문자열 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @return 	웹 어플리케이션 내부 제공 WEB 수행 클라이언트 세션 데이터 속성 객체의 세부 정보에 대한 문자열 정보
	 */
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(getCreateDateTime())) {
			info.append(", ");
			info.append("createTime=").append(SBNDateUtils.dateToString(getCreateDateTime(), SBNDateFormatConstant.DATE_FORMAT_3MS));
		}
		if(!SBNUtils.isNull(sessionKey)) {
			info.append(", ");
			info.append("sessionKey=").append(sessionKey);
		}
		if(!SBNUtils.isNull(sessionName)) {
			info.append(", ");
			info.append("sessionName=").append(sessionName);
		}
		if(!SBNUtils.isNull(sessionAuthData)) {
			info.append(", ");
			info.append("sessionAuthData=").append(((QNPAuth<?>)sessionAuthData).toStringInfo());
		}
		info.append(" }");
		return info.toString();
	}
}
