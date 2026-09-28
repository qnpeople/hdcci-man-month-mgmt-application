package com.qnpeople.rnd.pms.common.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kr.co.sbn.platformhub.framework.core.common.data.entities.SBNResponseErrorData;
import lombok.EqualsAndHashCode;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.response
 * @Filename		: QNPResponseErrorData.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트 요청에 대한 수행 실패 또는 오류 결과 응답 데이터를 관리하고 전달하는 작업을 수행하는 데이터 속성 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class QNPResponseErrorData extends SBNResponseErrorData {

	/**
	 * 웹 클라이언트의 수행 요청에 대한 프레임워크 어플리케이션 수행 오류 결과에 대한 세부 정보를 관리하는 데이터 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 */
	public QNPResponseErrorData() {
		super();
	}
	
	/**
	 * 웹 클라이언트의 수행 요청에 대한 프레임워크 어플리케이션 수행 오류 결과에 대한 세부 정보를 관리하는 데이터 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	errorCode			프레임워크 내부 어플리케이션 수행 중 발생 오류 코드
	 * @param 	errorMessage		프레임워크 내부 어플리케이션 수행 중 발생 오류 메시지 정보
	 */
	public QNPResponseErrorData(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
		
	/**
	 * 웹 클라이언트의 수행 요청에 대한 프레임워크 어플리케이션 수행 오류 결과에 대한 세부 정보를 관리하는 데이터 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.03
	 * @param 	exception		작업 수행 중 발생하는 일반 오류 Exception 객체
	 */
	public QNPResponseErrorData(Exception exception) {
		super(exception);
	}
}
