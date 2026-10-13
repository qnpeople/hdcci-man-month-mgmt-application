package com.qnpeople.rnd.pms.types;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.types
 * @Filename		: AppAuthGubunType.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.13.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션 수행을 위한 접근 시스템 구분 유형을 정의한 열거형 클래스
 * =================================================================================
 */
public enum AppAuthGubunType {

	/*@formatter:off */
	ADMIN("ADM", "관리자 인증 구분", "관리자 인증 구분 유형", Boolean.TRUE),
	WRKR("WKR", "근무자 인증 구분", "근무자 인증 구분 유형", Boolean.TRUE);
	/*@formatter:on */
	
	/* 시스템 접근 인증 구분 유형 코드 정보 */
	private String authGubunTypeCode;
	/* 시스템 접근 인증 구분 유형 코드 명 */
	private String authGubunTypeName;
	/* 시스템 접근 인증 구분 유형 설명 정보 */
	private String authGrubunTypeDesc;
	/* 시스템 접근 인증 구분 유형 사용 여부 Flag */
	private Boolean authGubunTypeUseFlag;
	
	/*
	 * 웹 어플리케이션에서 수행을 위한 시스템 접근 인증 구분 유형에 대한 열거형 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 		authGubunTypeCode		시스템 접근 인증 구분 유형 코드 정보
	 * @param 		authGubunTypeName		시스템 접근 인증 구분 유형 코드 명
	 * @param 		authGrubunTypeDesc		시스템 접근 인증 구분 유형 설명 정보
	 * @param 		authGubunTypeUseFlag	시스템 접근 인증 구분 유형 사용 여부 Flag
	 */
	private AppAuthGubunType(String authGubunTypeCode, String authGubunTypeName, String authGrubunTypeDesc, Boolean authGubunTypeUseFlag) {
		this.authGubunTypeCode = authGubunTypeCode;
		this.authGubunTypeName = authGubunTypeName;
		this.authGrubunTypeDesc = authGrubunTypeDesc;
		this.authGubunTypeUseFlag = authGubunTypeUseFlag;
	}
	
	/**
	 * 시스템 접근 인증 구분 유형 코드 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @return		시스템 접근 인증 구분 유형 코드 정보
	 */
	public String getAuthGubunTypeCode() {
		return authGubunTypeCode;
	}
	
	/**
	 * 시스템 접근 인증 구분 유형 코드 명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @return		시스템 접근 인증 구분 유형 코드 명
	 */
	public String getAuthGubunTypeName() {
		return authGubunTypeName;
	}
	
	/**
	 * 시스템 접근 인증 구분 유형 설명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @return		시스템 접근 인증 구분 유형 설명 정보
	 */
	public String getAuthGrubunTypeDesc() {
		return authGrubunTypeDesc;
	}
	
	/**
	 * 시스템 접근 인증 구분 유형 사용 여부 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @return		시스템 접근 인증 구분 유형 사용 여부 Flag
	 */
	public Boolean isAuthGubunTypeUsable() {
		return authGubunTypeUseFlag;
	}
	
	/**
	 * 시스템 접근 인증 구분 유형 객체의 세부 정보에 대한 문자열 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @return		시스템 접근 인증 구분 유형 객체의 세부 정보에 대한 문자열 정보
	 */
	public String getAppAuthGubunType() {
		StringBuilder info = new StringBuilder();
		info.append(AppAuthGubunType.class.getSimpleName()).append("{ ");
		info.append("authGubunTypeCode=").append(authGubunTypeCode).append(", ");
		info.append("authGubunTypeName=").append(authGubunTypeName).append(", ");
		info.append("authGrubunTypeDesc=").append(authGrubunTypeDesc).append(", ");
		info.append("authGubunTypeUseFlag=").append(authGubunTypeUseFlag);
		info.append(" }");
		return info.toString();
	}
	
	/**
	 * 전달된 시스템 접근 인증 구분 유형 코드 정보에 대한 시스템 정의 시스템 접근 인증 구분 유형의 유효성 체크 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	authGubunTypeCode	시스템 접근 인증 구분 유형 코드 정보
	 * @return		전달된 시스템 접근 인증 구분 유형 코드 정보에 대한 시스템 정의 시스템 접근 인증 구분 유형의 유효성 체크 수행 결과 Flag. 유효한 경우 true 를, 그렇지 않은 경우 false 를 전달
	 */
	public static final Boolean isValid(final String authGubunTypeCode) {
		String tmpAuthGubunTypeCode = "";
		if(SBNUtils.isNull(authGubunTypeCode)) {
			return false;
		}
		tmpAuthGubunTypeCode = authGubunTypeCode.trim().toUpperCase();
		for(AppAuthGubunType appAuthGubunType : values()) {
			if(appAuthGubunType.isAuthGubunTypeUsable() && appAuthGubunType.getAuthGubunTypeCode().equals(tmpAuthGubunTypeCode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 전달된 시스템 접근 인증 구분 유형 코드 정보에 대한 시스템 정의 시스템 접근 인증 구분 유형 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	authGubunTypeCode	시스템 접근 인증 구분 유형 코드 정보
	 * @return		전달된 시스템 접근 인증 구분 유형 코드 정보에 대한 시스템 정의 시스템 접근 인증 구분 유형 객체, 유효하지 않은 경우 null 전달
	 */
	public static final AppAuthGubunType getAppAuthGubunType(final String authGubunTypeCode) {
		String tmpAuthGubunTypeCode = "";
		if(!isValid(authGubunTypeCode)) {
			return null;
		}
		tmpAuthGubunTypeCode = authGubunTypeCode.trim().toUpperCase();
		for(AppAuthGubunType appAuthGubunType : values()) {
			if(appAuthGubunType.isAuthGubunTypeUsable() && appAuthGubunType.getAuthGubunTypeCode().equals(tmpAuthGubunTypeCode)) {
				return appAuthGubunType;
			}
		}
		return null;
	}
}
