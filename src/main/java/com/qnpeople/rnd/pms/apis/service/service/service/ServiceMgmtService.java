package com.qnpeople.rnd.pms.apis.service.service.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.service.service.model.ServiceDto;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceSC;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.common.web.modules.service.SBNWebBaseService;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.service.service
 * @Filename		: ServiceMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.30.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 서비스 영역의 서비스 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface ServiceMgmtService extends SBNWebBaseService {

	/**
	 * 전달된 조건에 대한 서비스 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				서비스 목록 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 서비스 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<ServiceDto> getServiceList(ServiceSC serviceSC) throws QNPWebException;
	
	/**
	 * 전달된 조건에 대한 서비스 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				서비스 상세 정보 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 서비스 상세 정보 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public ServiceDto getServiceDetail(ServiceSC serviceSC) throws QNPWebException;
	
	/**
	 * 전달된 신규 등록 대상 서비스 코드 정보에 대한 중복 여부 체크 작업을 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				신규 등록 대상 서비스 코드 정보에 대한 중복 여부 체크 조건 정보 전달 객체
	 * @return		전달된 신규 등록 서비스 키 정보에 대한 중복 여부 체크 수행 결과 Flag. 중복 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public Boolean isServiceKeyDuplicated(ServiceSC serviceSC) throws QNPWebException;
	
	/**
	 * 	전달된 신규 서비스 정보에 대한 등록 작업을 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceDto				신규 등록 대상 서비스 정보 전달 객체
	 * @return		전달된 신규 서비스 정보에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public Boolean registerNewService(ServiceDto serviceDto) throws QNPWebException;
	
	/**
	 * 전달된 서비스 정보에 대한 변경 작업 수행을 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceDto				변경 대상 서비스 정보 전달 객체
	 * @return		전달된 서비스 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public Boolean updateService(ServiceDto serviceDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 서비스 키 조건 정보에 대한 서비스 정보 삭제 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC					삭제 대상 서비스 키 정보 전달 객체
	 * @return		전달된 삭제 대상 서비스 키 조건 정보에 대한 서비스 정보 삭제 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public Boolean deleteService(ServiceSC serviceSC) throws QNPWebException;
}
