package com.qnpeople.rnd.pms.apis.service.siteservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.service.siteservice.mapper.SiteServiceMgmtMapper;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceDto;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.siteservice.service
 * @Filename		: SiteServiceMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.31.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 사이트 서비스 영역의 서비스 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class SiteServiceMgmtServiceImpl extends QNPWebBaseServiceImpl implements SiteServiceMgmtService {

	/* 사이트 서비스 관련 클라이언트 요청 수행을 위한 SQL 쿼리 수행 매핑 인터페이스 객체 */
	@Autowired
	private SiteServiceMgmtMapper siteServiceMgmtMapper;
	
	/**
	 * 전달된 사이트 서비스 목록 추출 조건 정보에 대한 사이트 서비스 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.31
	 * @param 	siteServiceSC			사이트 서비스 목록 추출 조건 정보 전달 객체
	 * @return		전달된 사이트 서비스 목록 추출 조건 정보에 대한 사이트 서비스 목록 결과 객체
	 * @throws 	QNPWebException	사이트 서비스 목록 추출 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<SiteServiceDto> getSiteServiceList(SiteServiceSC siteServiceSC) throws QNPWebException {
		List<SiteServiceDto> siteServiceList = new ArrayList<SiteServiceDto>();
		try {
			siteServiceList = siteServiceMgmtMapper.selectSiteServiceMappingList(siteServiceSC);			
			return siteServiceList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 사이트 서비스 상세 정보 추출 조건 정보에 대한 사이트 서비스 매핑 정보의 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	siteServiceSC			사이트 서비스 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 사이트 서비스 상세 정보 추출 조건 정보에 대한 사이트 서비스 상세 정보 결과 객체
	 * @throws 	QNPWebException	사이트 서비스 상세 정보 추출 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public SiteServiceDto getSiteServiceDetail(SiteServiceSC siteServiceSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		SiteServiceDto siteServiceDetail = null;
		try {
			if(SBNUtils.isNull(siteServiceSC) ||
			   ((SBNUtils.isNull(siteServiceSC.getChnlSiteSeq()) || (siteServiceSC.getChnlSiteSeq() <= 0L)) ||
				(SBNUtils.isNull(siteServiceSC.getSrvcSeq()) || (siteServiceSC.getSrvcSeq() <= 0L)))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 상세 조회 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			siteServiceDetail = siteServiceMgmtMapper.selectSiteServiceDetail(siteServiceSC);
			return siteServiceDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
