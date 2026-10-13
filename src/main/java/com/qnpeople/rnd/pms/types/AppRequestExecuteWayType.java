package com.qnpeople.rnd.pms.types;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.types
 * @Filename		: AppRequestExecuteWayType.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.07.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 클라이언트 요청 수행을 위한 요청 수행 방식 유형을 정의한 열거형 클래스
 * =================================================================================
 */
public enum AppRequestExecuteWayType {

	/*@formatter:off */
	SINGLE("SNGL", "단건 수행", "단건 수행 방법 유형", Boolean.TRUE),
	MULTI("MLTI", "다중 수행", "다중 수행 방법 유형", Boolean.TRUE);
	/*@formatter:on */
	
	/* 클라이언트 요청 수행 방법 유형 코드 */
	private String executeTypeCode;
	/* 클라이언트 요청 수행 방법 유형 명 */
	private String executeTypeName;
	/* 클라이언트 요청 수행 방법 유형 설명 */
	private String executeTypeDesc;
	/* 클라이언트 요청 수행 방법 유형 사용 여부 Flag */
	private Boolean executeTypeUseFlag;
	
	/*
	 * 	클라이언트의 요청 수행 방법 유형에 대한 열거형 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 		executeTypeCode		클라이언트 요청 수행 방법 유형 코드
	 * @param 		executeTypeName		클라이언트 요청 수행 방법 유형 명
	 * @param 		executeTypeDesc			클라이언트 요청 수행 방법 유형 설명
	 * @param 		executeTypeUseFlag		클라이언트 요청 수행 방법 유형 사용 여부 Flag
	 */
	private AppRequestExecuteWayType(String executeTypeCode, String executeTypeName, String executeTypeDesc, Boolean executeTypeUseFlag) {
		this.executeTypeCode = executeTypeCode;
		this.executeTypeName = executeTypeName;
		this.executeTypeDesc = executeTypeDesc;
		this.executeTypeUseFlag = executeTypeUseFlag;
	}
	
	/**
	 * 클라이언트 요청 수행 방법 유형 코드를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @return		클라이언트 요청 수행 방법 유형 코드
	 */
	public String getExecuteTypeCode() {
		return executeTypeCode;
	}
	
	/**
	 * 클라이언트 요청 수행 방법 유형 명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @return		클라이언트 요청 수행 방법 유형 명
	 */
	public String getExecuteTypeName() {
		return executeTypeName;
	}
	
	/**
	 * 클라이언트 요청 수행 방법 유형 설명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @return		클라이언트 요청 수행 방법 유형 설명
	 */
	public String getExecuteTypeDesc() {
		return executeTypeDesc;
	}
	
	/**
	 * 클라이언트 요청 수행 방법 유형 사용 여부 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @return		클라이언트 요청 수행 방법 유형 사용 여부 Flag
	 */
	public Boolean isExecuteTypeUsable() {
		return executeTypeUseFlag;
	}
	
	/**
	 * 클라이언트 요청 수행 방식 유형의 세부 정보에 대한 문자열 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @return		클라이언트 요청 수행 방식 유형의 세부 정보에 대한 문자열 정보
	 */
	public String getRequestExecuteWayType() {
		StringBuilder info = new StringBuilder();
		info.append(AppRequestExecuteWayType.class.getSimpleName()).append("{ ");
		info.append("executeTypeCode=").append(executeTypeCode).append(", ");
		info.append("executeTypeName=").append(executeTypeName).append(", ");
		info.append("executeTypeDesc=").append(executeTypeDesc).append(", ");
		info.append("executeTypeUseFlag=").append(executeTypeUseFlag);
		info.append(" }");
		return info.toString();
	}
	
	/**
	 * 전달된 클라이언트 요청 수행 방법 유형 코드 정보에 대한 프레임워크 내부 정의 유형의 유효성 체크 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	executeTypeCode	클라이언트 요청 수행 방법 유형 코드
	 * @return		전달된 클라이언트 요청 수행 방법 유형 코드 정보에 대한 프레임워크 내부 정의 유형의 유효성 체크 결과 Flag. 유효한 경우 true 를, 그렇지 않은 경우 false 를 전달
	 */
	public static final Boolean isValid(final String executeTypeCode) {
		String tmpExecuteTypeCode ="";
		if(SBNUtils.isNull(executeTypeCode)) {
			return false;
		}
		tmpExecuteTypeCode = executeTypeCode.trim().toUpperCase();
		for(AppRequestExecuteWayType requestExecuteWayType : values()) {
			if(requestExecuteWayType.isExecuteTypeUsable() && requestExecuteWayType.getExecuteTypeCode().equals(tmpExecuteTypeCode)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 전달된 클라이언트 요청 수행 방법 유형 코드 정보에 대한 프레임워크 내부 정의 유형 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	executeTypeCode	클라이언트 요청 수행 방법 유형 코드
	 * @return		전달된 클라이언트 요청 수행 방법 유형 코드 정보에 대한 프레임워크 내부 정의 유형 객체. 유효하지 않은 경우 null 전달
	 */
	public static final AppRequestExecuteWayType getRequestExecuteWayType(final String executeTypeCode) {
		String tmpExecuteTypeCode ="";
		if(!isValid(executeTypeCode)) {
			return null;
		}
		tmpExecuteTypeCode = executeTypeCode.trim().toUpperCase();
		for(AppRequestExecuteWayType requestExecuteWayType : values()) {
			if(requestExecuteWayType.isExecuteTypeUsable() && requestExecuteWayType.getExecuteTypeCode().equals(tmpExecuteTypeCode)) {
				return requestExecuteWayType;
			}
		}
		return null;
	}
}
