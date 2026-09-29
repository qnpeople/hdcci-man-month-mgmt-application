package com.qnpeople.rnd.pms.apis.service.category.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategoryDto;
import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategorySC;
import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategoryTreeDto;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.common.web.exceptions.SBNWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.category.service
 * @Filename		: SrvcCategoryService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.29.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 서비스 영역의 서비스에 대한 분류 카테고리 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface SrvcCategoryService extends QNPWebBaseService {

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	관리를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	서비스를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 사용 가능 서비스 카테고리 정보에 대한 Tree 구조의 목록 객체를 추출하여 전달하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.29
	 * @param 	srvcCategorySC		서비스 카테고리에 대한 작업 수행 조건 정보 전달 객체
	 * @return		전달된 서비스 카테고리에 대한 사용 가능 서비스 카테고리 정보에 대한 Tree 구조 목록 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<SrvcCategoryTreeDto> getAvailableServiceCategoryTreeList(SrvcCategorySC srvcCategorySC) throws QNPWebException;
	
	/**
	 * 전달된 사용 가능 서비스 카테고리 정보에 대한 상세 정보 객체를 추출하여 전달하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.29
	 * @param 	srvcCategorySC	서비스 카테고리에 대한 작업 수행 조건 정보 전달 객체
	 * @return		전달된 서비스 카테고리 추출 조건에 대한 서비스 카테고리 상세 정보 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public SrvcCategoryDto getServiceCategoryDetail(SrvcCategorySC srvcCategorySC) throws QNPWebException;
}
