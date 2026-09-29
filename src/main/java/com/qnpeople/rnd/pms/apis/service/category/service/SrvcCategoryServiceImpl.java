package com.qnpeople.rnd.pms.apis.service.category.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.service.category.mapper.SrvcCategoryMgmtMapper;
import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategoryDto;
import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategorySC;
import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategoryTreeDto;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.common.web.exceptions.SBNWebException;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.category.service
 * @Filename		: SrvcCategoryServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.29.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 서비스 영역의 서비스에 대한 분류 카테고리 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class SrvcCategoryServiceImpl extends QNPWebBaseServiceImpl implements SrvcCategoryService {

	/* 서비스 카테고리 관련 클라이언트 요청 수행을 위한 SQL 쿼리 수행 매핑 인터페이스 객체  */
	@Autowired
	private SrvcCategoryMgmtMapper srvcCategoryMgmtMapper;

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
	public List<SrvcCategoryTreeDto> getAvailableServiceCategoryTreeList(SrvcCategorySC srvcCategorySC) throws QNPWebException {
		List<SrvcCategoryTreeDto> availableSrvcCategoryTreeList = new ArrayList<SrvcCategoryTreeDto>();
		try {			
			availableSrvcCategoryTreeList = srvcCategoryMgmtMapper.selectAvailableServiceCategoryTreeList(srvcCategorySC);
			return availableSrvcCategoryTreeList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 사용 가능 서비스 카테고리 정보에 대한 상세 정보 객체를 추출하여 전달하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.29
	 * @param 	srvcCategorySC	서비스 카테고리에 대한 작업 수행 조건 정보 전달 객체
	 * @return		전달된 서비스 카테고리 추출 조건에 대한 서비스 카테고리 상세 정보 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public SrvcCategoryDto getServiceCategoryDetail(SrvcCategorySC srvcCategorySC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		SrvcCategoryDto serviceCategoryDetail = null;
		try {
			if(SBNUtils.isNull(srvcCategorySC)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 카테고리 상세 조회 요청 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			serviceCategoryDetail = srvcCategoryMgmtMapper.selectServiceCategoryDetail(srvcCategorySC);
			return serviceCategoryDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
