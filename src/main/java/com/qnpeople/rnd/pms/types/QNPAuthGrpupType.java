package com.qnpeople.rnd.pms.types;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.auth
 * @Filename		: QNPAuthGrpupType.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.07.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션 수행을 위한 시스템 접근 사용자의 접근 권한에 대한 유형을 정의한 열거형 클래스
 * =================================================================================
 */
public enum QNPAuthGrpupType {

	/*@formatter:off */
	SPR_AUTH_GRP("SPR_AUTH_GRP", "통합 권한 그룹", "시스템 전체 관리 최상위 통합 권한 그룹", Boolean.TRUE),
	SYS_AUTH_GRP("SYS_AUTH_GRP", "시스템 권한 그룹", "시스템 영역 관리 권한 그룹", Boolean.TRUE),
	SRVC_AUTH_GRP("SRVC_AUTH_GRP", "서비스 권한 그룹", "서비스 영역 관리 권한 그룹", Boolean.TRUE),
	COMP_AUTH_GRP("COMP_AUTH_GRP", "업체 권한 그룹", "업체 영역 관리 권한 그룹", Boolean.TRUE),
	PRJ_ADM_AUTH_GRP("PRJ_ADM_AUTH_GRP", "프로젝트 권한 그룹", "프로젝트 통합 관리 권한 그룹", Boolean.TRUE),
	PRJ_HCCI_ADM_AUTH_GRP("PRJ_HCCI_ADM_AUTH_GRP", "프로젝트 자사 통합 권한 그룹", "프로젝트 현대 자동차 자사 프로젝트 통합 관리 권한 그룹", Boolean.TRUE),
	PRJ_COMP_ADM_AUTH_GRP("PRJ_COMP_ADM_AUTH_GRP", "프로젝트 업체 통합 권한 그룹", "프로젝트 협력사 프로젝트 통합 관리 권한 그룹", Boolean.TRUE),
	PRJ_COMP_MGR_AUTH_GRP("PRJ_COMP_MGR_AUTH_GRP", "프로젝트 업체 중간 관리자 권한 그룹", "프로젝트 통합 중간 관리자 관리 권한 그룹", Boolean.TRUE),
	PRJ_COMP_WRK_AUTH_GRP("PRJ_COMP_WRK_AUTH_GRP", "프로젝트 업체 근무자 권한 그룹", "프로젝트 업체 근무자 권한 그룹", Boolean.TRUE),
	NONE_AUTH_GRP("NONE_AUTH_GRP", "미 정의 권한 그룹", "예외 및 기타 처리용 미 정의 권한 그룹", Boolean.TRUE);
	/*@formatter:on */
	
	/* 권한 그룹 유형 코드 정보 */
	private String authGrpTypeCode;
	/* 권한 그룹 유형 명 정보 */
	private String authGrpTypeName;
	/* 권한 그룹 유형 설명 정보 */
	private String authGrpTypeDesc;
	/* 권한 그룹 유형 사용 여부 Flag  */
	private Boolean authGrpTypeUseFlag;
	
	/*
	 * 웹 어플리케이션 수행을 위한 시스템 이용 및 관리 권한 그룹 유형에 대한 열거형 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 		authGrpTypeCode		권한 그룹 유형 코드 정보
	 * @param 		authGrpTypeName		권한 그룹 유형 명 정보
	 * @param 		authGrpTypeDesc		권한 그룹 유형 설명 정보
	 * @param 		authGrpTypeUseFlag	권한 그룹 유형 사용 여부 Flag
	 */
	private QNPAuthGrpupType(String authGrpTypeCode, String authGrpTypeName, String authGrpTypeDesc, Boolean authGrpTypeUseFlag) {
		this.authGrpTypeCode = authGrpTypeCode;
		this.authGrpTypeName = authGrpTypeName;
		this.authGrpTypeDesc = authGrpTypeDesc;
		this.authGrpTypeUseFlag = authGrpTypeUseFlag;
	}
	
	/**
	 * 웹 어플리케이션 수행 권한 그룹 유형의 권한 그룹 유형 코드 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		권한 그룹 유형 코드 정보
	 */
	public String getAuthGrpTypeCode() {
		return authGrpTypeCode;
	}
	
	/**
	 * 웹 어플리케이션 수행 권한 그룹 유형의 권한 그룹 유형 명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		권한 그룹 유형 명 정보
	 */
	public String getAuthGrpTypeName() {
		return authGrpTypeName;
	}
	
	/**
	 * 웹 어플리케이션 수행 권한 그룹 유형의 권한 그룹 유형 설명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		권한 그룹 유형 설명 정보
	 */
	public String getAuthGrpTypeDesc() {
		return authGrpTypeDesc;
	}
	
	/**
	 * 웹 어플리케이션 수행 권한 그룹 유형의 사용 여부에 대한 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		권한 그룹 유형 사용 여부 Flag
	 */
	public Boolean isAuthGrpTypeUsable() {
		return authGrpTypeUseFlag;
	}
	
	/**
	 * 웹 어플리케이션 수행 권한 그룹 유형의 세부 정보에 대한 문자열 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @return		웹 어플리케이션 수행 권한 그룹 유형의 세부 정보에 대한 문자열 정보
	 */
	public String getAuthGrpType() {
		StringBuilder info = new StringBuilder();
		info.append(QNPAuthGrpupType.class.getSimpleName()).append("{ ");
		info.append("authGrpTypeCode=").append(authGrpTypeCode).append(", ");
		info.append("authGrpTypeName=").append(authGrpTypeName).append(", ");
		info.append("authGrpTypeDesc=").append(authGrpTypeDesc).append(", ");
		info.append("authGrpTypeUseFlag=").append(authGrpTypeUseFlag);
		info.append(" }");
		return info.toString();
	}
	
	/**
	 * 전달된 권한 그룹 유형 코드 정보에 대한 어플리케이션 내부 기본 제공 유형의 유효성 체크 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 	authGrpTypeCode	권한 그룹 유형 코드 정보
	 * @return		전달된 권한 그룹 유형 코드 정보에 대한 어플리케이션 내부 기본 제공 권한 그룹 유형의 유효성 체크 수행 결과 Flag. 유효한 경우 true 를, 그렇지 않은 경우 false 를 전달
	 */
	public static final Boolean isValid(String authGrpTypeCode) {
		String tmpAuthGrpTypeCode = "";
		if(SBNUtils.isNull(authGrpTypeCode)) {
			return false;
		}
		tmpAuthGrpTypeCode = authGrpTypeCode.trim().toUpperCase();
		for(QNPAuthGrpupType authGrpupType : values()) {
			if(authGrpupType.isAuthGrpTypeUsable() && authGrpupType.getAuthGrpTypeCode().equals(tmpAuthGrpTypeCode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 전달된 권한 그룹 유형 코드 정보에 대한 어플리케이션 내부 기본 제공 권한 그룹 유형 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.07
	 * @param 	authGrpTypeCode	권한 그룹 유형 코드 정보
	 * @return		전달된 권한 그룹 유형 코드 정보에 대한 어플리케이션 내부 기본 제공 권한 그룹 유형 객체. 유효하지 않은 경우 null 전달
	 */
	public static final QNPAuthGrpupType getAuthGrpupType(String authGrpTypeCode) {
		String tmpAuthGrpTypeCode = "";
		if(!isValid(authGrpTypeCode)) {
			return NONE_AUTH_GRP;
		}
		tmpAuthGrpTypeCode = authGrpTypeCode.trim().toUpperCase();
		for(QNPAuthGrpupType authGrpupType : values()) {
			if(authGrpupType.isAuthGrpTypeUsable() && authGrpupType.getAuthGrpTypeCode().equals(tmpAuthGrpTypeCode)) {
				return authGrpupType;
			}
		}
		return NONE_AUTH_GRP;
	}
}
