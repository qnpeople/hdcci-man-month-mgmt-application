package com.qnpeople.rnd.pms.apis.common.site.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.common.site.domain.request.SiteMgmtRequest;
import com.qnpeople.rnd.pms.apis.common.site.model.SiteDto;
import com.qnpeople.rnd.pms.apis.common.site.model.SiteSC;
import com.qnpeople.rnd.pms.apis.common.site.service.SiteMgmtService;
import com.qnpeople.rnd.pms.common.domain.executor.controller.QNPWebBaseController;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseErrorData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponsePacketEntityData;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;
import com.qnpeople.rnd.pms.utils.QNPResponseEntityUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.sbn.platformhub.framework.core.types.SBNUseYnType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.common.site.controller
 * @Filename		: SiteMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.28.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 사이트 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	
 *  
 *  [ 서비스 ]
 *  	01.사이트_사용 가능 사이트 목록 조회
 *  	02.사이트_사이트 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/common/site")
@Slf4j
public class SiteMgmtController extends QNPWebBaseController {

	/* 실질적인 시스템 지원 서비스 관리 수행 서비스 인터페이스 객체 */
	@Autowired
	private SiteMgmtService siteMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////

	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////	
	/**
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	channelMgmtRequest
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getAvailableSiteList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getAvailableSiteList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody SiteMgmtRequest siteMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		SiteSC siteSC = null;
		List<SiteDto> availableSiteList = new ArrayList<SiteDto>();
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAvailableSiteList() siteMgmtRequest={}", siteMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(siteMgmtRequest.getTxId())) {
				txId = siteMgmtRequest.getTxId();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			siteSC = new SiteSC();
			siteSC.setUseYn(SBNUseYnType.Y.getTypeCode());
			if(!SBNUtils.isNull(siteMgmtRequest.getSiteTp())) {
				siteSC.setSiteTp(siteMgmtRequest.getSiteTp());
			}
			if(!SBNUtils.isNull(siteMgmtRequest.getSiteNm())) {
				siteSC.setSiteNm(siteMgmtRequest.getSiteNm());
			}
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 Tree 구조의 목록 객체를 추출한다 
			availableSiteList = siteMgmtService.getSitelList(siteSC);
			
			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(availableSiteList)) {
				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 상이트 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(availableSiteList);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), responseData);
			}
			
			//	4. 최종 구성된 수행 결과 응답 객체를 ResponseEntity 객체로 구축 및 전달 후 작업을 종료한다
			return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
		} catch(QNPWebException webException) {
			throw webException; 
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
				errorMessage = exception.getMessage();
			} else {
				errorMessage = exception.toString();
			}
			errorResponseData = new QNPResponseErrorData();
			errorResponseData.setCode(errorCode);
			errorResponseData.setMessage(errorMessage);
			throw new QNPWebException(reason, errorCode, errorMessage);
		}
	}
	
	/**
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	channelMgmtRequest
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getSiteDetail", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSiteDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody SiteMgmtRequest siteMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		SiteSC siteSC = null;
		SiteDto siteDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSiteDetail() siteMgmtRequest={}", siteMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(siteMgmtRequest.getTxId())) {
				txId = siteMgmtRequest.getTxId();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			siteSC = new SiteSC();
			if(!SBNUtils.isNull(siteMgmtRequest.getSiteSeq())) {
				siteSC.setSiteSeq(siteMgmtRequest.getSiteSeq());
			}
			if(!SBNUtils.isNull(siteMgmtRequest.getSiteCd())) {
				siteSC.setSiteCd(siteMgmtRequest.getSiteCd());
			}
			if(!SBNUtils.isNull(siteMgmtRequest.getSiteTp())) {
				siteSC.setSiteTp(siteMgmtRequest.getSiteTp());
			}
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 서비스 목록 객체를 추출한다 
			siteDetail = siteMgmtService.getSiteDetail(siteSC);
			
			//	3. 요청 수행 상세 결과 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(siteDetail)) {
				//	3.1. 요청 수행 상세 결과 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 사이트 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 상세 결과 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(siteDetail);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), responseData);
			}
			
			//	4. 최종 구성된 수행 결과 응답 객체를 ResponseEntity 객체로 구축 및 전달 후 작업을 종료한다
			return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
		} catch(QNPWebException webException) {
			throw webException; 
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
				errorMessage = exception.getMessage();
			} else {
				errorMessage = exception.toString();
			}
			errorResponseData = new QNPResponseErrorData();
			errorResponseData.setCode(errorCode);
			errorResponseData.setMessage(errorMessage);
			throw new QNPWebException(reason, errorCode, errorMessage);
		}
	}
}
