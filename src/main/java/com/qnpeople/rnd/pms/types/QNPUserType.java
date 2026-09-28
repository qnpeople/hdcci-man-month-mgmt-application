package com.qnpeople.rnd.pms.types;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.auth
 * @Filename		: QNPUserType.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.07.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션 수행을 위한 시스템 접근 사용자의 유형을 정의한 열거형 클래스
 * =================================================================================
 */
public enum QNPUserType {

	/*@formatter:off */
	//	관리자
	SUPER_ADMIN("", QNPUserGubunType.ADM , "", "", Boolean.TRUE),
	SYSTEM_ADMIN("", QNPUserGubunType.ADM , "", "", Boolean.TRUE),
	SERVICE_ADMIN("", QNPUserGubunType.ADM , "", "", Boolean.TRUE),
	COMPANY_ADMIN("", QNPUserGubunType.ADM , "", "", Boolean.TRUE),
	PROJECT_ADMIN("", QNPUserGubunType.ADM , "", "", Boolean.TRUE),
	//	사용자
	
	ETC("", QNPUserGubunType.ETC, "", "", Boolean.TRUE);
	/*@formatter:on */
	
	/* 시스템 접근 사용자 유형 코드 정보 */
	private String userTypeCode;
	/* 시스템 접근 사용자 구분 유형 객체 */
	private QNPUserGubunType userGubunType;
	/* 시스템 접근 사용자 유형 명 정보 */
	private String userTypeName;
	/* 시스템 접근 사용자 유형 설명 정보 */
	private String userTypeDesc;
	/* 시스템 접근 사용자 유형 사용 여부 Flag */
	private Boolean userTypeFlag;
	
	/*
	 * 웹 어플리케이션 수행 사용자 유형에 대한 열거형 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 		userTypeCode		시스템 접근 사용자 유형 코드 정보
	 * @param 		userGubunType	시스템 접근 사용자 구분 유형 객체
	 * @param 		userTypeName	시스템 접근 사용자 유형 명 정보
	 * @param 		userTypeDesc		시스템 접근 사용자 유형 설명 정보
	 * @param 		userTypeFlag		시스템 접근 사용자 유형 사용 여부 Flag
	 */
	private QNPUserType(String userTypeCode, QNPUserGubunType userGubunType, String userTypeName, String userTypeDesc, Boolean userTypeFlag) {
		this.userTypeCode = userTypeCode;
		this.userGubunType = userGubunType;
		this.userTypeName = userTypeName;
		this.userTypeDesc = userTypeDesc;
		this.userTypeFlag = userTypeFlag;
	}
	
	/**
	 * 웹 어플리케이션 수행 시스템 접근 사용자 유형 코드 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		시스템 접근 사용자 유형 코드 정보
	 */
	public String getUserTypeCode() {
		return userTypeCode;
	}
	
	/**
	 * 웹 어플리케이션 수행 시스템 접근 사용자 구분 유형 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		시스템 접근 사용자 구분 유형 객체
	 */
	public QNPUserGubunType getUserGubunType() {
		return userGubunType;
	}
	
	/**
	 * 웹 어플리케이션 수행 시스템 접근 사용자 유형 명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		시스템 접근 사용자 유형 명 정보
	 */
	public String getUserTypeName() {
		return userTypeName;
	}
	
	/**
	 * 웹 어플리케이션 수행 시스템 접근 사용자 유형 설명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		시스템 접근 사용자 유형 설명 정보
	 */
	public String getUserTypeDesc() {
		return userTypeDesc;
	}
	
	/**
	 * 웹 어플리케이션 수행 시스템 접근 사용자 유형 사용 여부를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		시스템 접근 사용자 유형 사용 여부 Flag
	 */
	public Boolean isUserTypeUsable() {
		return userTypeFlag;
	}
	
	/**
	 * 시스템 접근 사용자 유형의 세부 정보에 대한 문자열 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		시스템 접근 사용자 유형의 세부 정보에 대한 문자열 정보
	 */
	public String getUserType() {
		StringBuilder info = new StringBuilder();
		info.append(QNPUserType.class.getSimpleName()).append("{ ");
		info.append("userTypeCode=").append(userTypeCode).append(", ");
		info.append("userGubunType=").append(userGubunType).append(", ");
		info.append("userTypeName=").append(userTypeName).append(", ");
		info.append("userTypeDesc=").append(userTypeDesc).append(", ");
		info.append("userTypeFlag=").append(userTypeFlag);
		info.append(" }");
		return info.toString();
	}
	
	/**
	 * 전달된 시스템 접근 사용자 유형 코드 정보에 대한 어플리케이션 내부 정의 사용자 유형의 유효성 체크 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 	userTypeCode		시스템 접근 사용자 유형 코드 정보
	 * @return		전달된 시스템 접근 사용자 유형 코드 정보에 대한 어플리케이션 내부 정의 사용자 유형의 유효성 체크 결과 Flag. 유효한 경우 true 를, 그렇지 않은 경우 false 를 전달
	 */
	public static final Boolean isValid(String userTypeCode) {
		String tmpUserTypeCode = "";
		if(SBNUtils.isNull(userTypeCode)) {
			return false;
		}
		tmpUserTypeCode = userTypeCode.trim().toUpperCase();
		for(QNPUserType userType : values()) {
			if(userType.isUserTypeUsable() && userType.getUserTypeCode().equals(tmpUserTypeCode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 전달된 시스템 접근 사용자 유형 코드 정보에 대한 어플리케이션 내부 정의 사용자 유형 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 	userTypeCode		시스템 접근 사용자 유형 코드 정보
	 * @return		전달된 시스템 접근 사용자 유형 코드 정보에 대한 어플리케이션 내부 정의 사용자 유형 객체, 유효하지 않은 경우 null 전달
	 */
	public static final QNPUserType getUserType(String userTypeCode) {
		String tmpUserTypeCode = "";
		if(!isValid(userTypeCode)) {
			return null;
		}
		tmpUserTypeCode = userTypeCode.trim().toUpperCase();
		for(QNPUserType userType : values()) {
			if(userType.isUserTypeUsable() && userType.getUserTypeCode().equals(tmpUserTypeCode)) {
				return userType;
			}
		}
		return null;
	}
}
