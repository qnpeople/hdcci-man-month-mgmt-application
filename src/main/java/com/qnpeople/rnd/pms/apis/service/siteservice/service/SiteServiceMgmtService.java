package com.qnpeople.rnd.pms.apis.service.siteservice.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceDto;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.siteservice.service
 * @Filename		: SiteServiceMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.30.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 사이트 서비스 영역의 서비스 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface SiteServiceMgmtService extends QNPWebBaseService {

	/**
	 * 전달된 사이트 서비스 목록 추출 조건 정보에 대한 사이트 서비스 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	siteServiceSC			사이트 서비스 목록 추출 조건 정보 전달 객체
	 * @return		전달된 사이트 서비스 목록 추출 조건 정보에 대한 사이트 서비스 목록 결과 객체
	 * @throws 	QNPWebException	사이트 서비스 목록 추출 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<SiteServiceDto> getSiteServiceList(SiteServiceSC siteServiceSC) throws QNPWebException;
	
	/**
	 * 전달된 사이트 서비스 상세 정보 추출 조건 정보에 대한 사이트 서비스 매핑 정보의 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	siteServiceSC			사이트 서비스 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 사이트 서비스 상세 정보 추출 조건 정보에 대한 사이트 서비스 상세 정보 결과 객체
	 * @throws 	QNPWebException	사이트 서비스 상세 정보 추출 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public SiteServiceDto getSiteServiceDetail(SiteServiceSC siteServiceSC) throws QNPWebException;
}
