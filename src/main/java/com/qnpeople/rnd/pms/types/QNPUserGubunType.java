package com.qnpeople.rnd.pms.types;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.auth
 * @Filename		: QNPUserGubunType.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.07.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션 수행을 위한 사용자의 구분 유형을 정의한 열거형 클래스
 * =================================================================================
 */
public enum QNPUserGubunType {

	/*@formatter:off */
	ADM("ADM", "관리자 유형", "시스템 관리자 유형", Boolean.TRUE),
	USR("USR", "사용자 유형", "시스템 사용자 유형", Boolean.TRUE),
	WKR("USR", "근무자 유형", "시스템 근무자 유형", Boolean.TRUE),
	ETC("ETC", "기타 근무자 유형", "기타 근무자 유형", Boolean.TRUE);
	/*@formatter:on */
	
	/* 사용자 구분 유형 코드 정보 */
	private String userGubunTypeCode;
	/* 사용자 구분 유형 명 정보 */
	private String userGubunTypeName;
	/* 사용자 구분 유형 설명 정보 */
	private String userGubunTypeDesc;
	/* 사용자 구분 유형 사용 여부 Flag */
	private Boolean userGubunTypeUseFlag;
	
	/*
	 * 앱 어플리케이션 수행 사용자의 구분 유형에 대한 열거형 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 		userGubunTypeCode		사용자 구분 유형 코드 정보
	 * @param 		userGubunTypeName		사용자 구분 유형 명 정보
	 * @param 		userGubunTypeDesc		사용자 구분 유형 설명 정보
	 * @param 		userGubunTypeUseFlag	사용자 구분 유형 사용 여부 Flag 
	 */
	private QNPUserGubunType(String userGubunTypeCode, String userGubunTypeName, String userGubunTypeDesc, Boolean userGubunTypeUseFlag) {
		this.userGubunTypeCode = userGubunTypeCode;
		this.userGubunTypeName = userGubunTypeName;
		this.userGubunTypeDesc = userGubunTypeDesc;
		this.userGubunTypeUseFlag = userGubunTypeUseFlag;
	}
	
	/**
	 * 웹 어플리케이션 수행 사용자 구분 유형 코드 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		사용자 구분 유형 코드 정보
	 */
	public String getUserGubunTypeCode() {
		return userGubunTypeCode;
	}
	
	/**
	 * 웹 어플리케이션 수행 사용자 구분 유형 명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		사용자 구분 유형 명 정보
	 */
	public String getUserGubunTypeName() {
		return userGubunTypeName;
	}
	
	/**
	 * 웹 어플리케이션 수행 사용자 구분 유형 설명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		사용자 구분 유형 설명 정보
	 */
	public String getUserGubunTypeDesc() {
		return userGubunTypeDesc;
	}
	
	/**
	 * 웹 어플리케이션 수행 사용자 구분 유형 사용 여부를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		사용자 구분 유형 사용 여부 Flag
	 */
	public Boolean isUserGubunTypeUsable() {
		return userGubunTypeUseFlag;
	}
	
	/**
	 * 웹 어플리케이션 수행 사용자 구분 유형의 세부 정보에 대한 문자열 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		웹 어플리케이션 수행 사용자 구분 유형의 세부 정보에 대한 문자열 정보
	 */
	public String getUserGubunType() {
		StringBuilder info = new StringBuilder();
		info.append(QNPUserGubunType.class.getSimpleName()).append("{ ");
		info.append("userGubunTypeCode=").append(userGubunTypeCode).append(", ");
		info.append("userGubunTypeName=").append(userGubunTypeName).append(", ");
		info.append("userGubunTypeDesc=").append(userGubunTypeDesc).append(", ");
		info.append("userGubunTypeUseFlag=").append(userGubunTypeUseFlag);
		info.append(" }");
		return info.toString();
	}
	
	/**
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 	userGubunTypeCode
	 * @return
	 */
	public static final Boolean isValid(String userGubunTypeCode) {
		String tmpUserGubunTypeCode = "";
		if(SBNUtils.isNull(userGubunTypeCode)) {
			return false;
		}
		tmpUserGubunTypeCode = userGubunTypeCode.trim().toUpperCase();
		for(QNPUserGubunType userGubunType : values()) {
			if(userGubunType.isUserGubunTypeUsable() && userGubunType.getUserGubunTypeCode().equals(tmpUserGubunTypeCode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 	userGubunTypeCode
	 * @return
	 */
	public static final QNPUserGubunType getUserGubunType(String userGubunTypeCode) {
		String tmpUserGubunTypeCode = "";
		if(!isValid(userGubunTypeCode)) {
			return ETC;
		}
		tmpUserGubunTypeCode = userGubunTypeCode.trim().toUpperCase();
		for(QNPUserGubunType userGubunType : values()) {
			if(userGubunType.isUserGubunTypeUsable() && userGubunType.getUserGubunTypeCode().equals(tmpUserGubunTypeCode)) {
				return userGubunType;
			}
		}
		return ETC;
	}
}
