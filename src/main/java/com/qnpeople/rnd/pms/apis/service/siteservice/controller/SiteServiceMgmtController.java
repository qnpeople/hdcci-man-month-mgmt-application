package com.qnpeople.rnd.pms.apis.service.siteservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.service.siteservice.domain.request.SiteServiceMgmtRequest;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceDto;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceSC;
import com.qnpeople.rnd.pms.apis.service.siteservice.service.SiteServiceMgmtService;
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
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.category.controller
 * @Filename		: SrvcCategoryMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.30.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 사이트 서비스 매핑 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	서비스_사이트 서비스 등록
 *  	서비스_사이트 서비스 변경
 *  	서비스_사이트 서비스 삭제
 *  
 *  [ 서비스 ]
 *  	서비스_사이트 서비스 목록 조회
 *  	서비스_사이트 서비스 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/siteservice")
@Slf4j
public class SiteServiceMgmtController extends QNPWebBaseController {

	/* 사이트 서비스 관련 클라이언트 요청 수행을 위한 SQL 쿼리 수행 매핑 인터페이스 객체 */
	@Autowired
	private SiteServiceMgmtService siteServiceMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 서비스_사이트 서비스 등록 ]
	 * 클라이언트의 신규 사이트 서비스 등록 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest					클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	siteServiceMgmtRequest	클라이언트의 신규 사이트 서비스 등록 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException			클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@PostMapping(value="/mgmt/registerNewSiteService")
	public ResponseEntity<?> registerSiteService(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody SiteServiceMgmtRequest siteServiceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		SiteServiceDto siteServiceDto = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerSiteService() siteServiceMgmtRequest={}", siteServiceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getTxId())) {
				txId = siteServiceMgmtRequest.getTxId();
			}
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getSiteService())) {
				siteServiceDto = siteServiceMgmtRequest.getSiteService();
				log.debug("registerSiteService() siteServiceDto={}", siteServiceDto.toStringInfo());
			}
			//
			reason = QNPReasonCode.SERVICE_PREPARING_ERROR;
			errorCode = reason.getReasonCode();
			errorMessage = "요청하신 서비스는 준비중 입니다.";			
			errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
			responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
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
	 * [ 서비스_사이트 서비스 변경 ]
	 * 클라이언트의 사이트 서비스 변경 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest					클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	siteServiceMgmtRequest	클라이언트의 사이트 서비스 변경 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException			클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@PostMapping(value="/mgmt/updateSiteService")
	public ResponseEntity<?> updateSiteService(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody SiteServiceMgmtRequest siteServiceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		SiteServiceDto siteServiceDto = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateSiteService() siteServiceMgmtRequest={}", siteServiceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getTxId())) {
				txId = siteServiceMgmtRequest.getTxId();
			}
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getSiteService())) {
				siteServiceDto = siteServiceMgmtRequest.getSiteService();
				log.debug("updateSiteService() siteServiceDto={}", siteServiceDto.toStringInfo());
			}
			//	
			reason = QNPReasonCode.SERVICE_PREPARING_ERROR;
			errorCode = reason.getReasonCode();
			errorMessage = "요청하신 서비스는 준비중 입니다.";			
			errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
			responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
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
	 * [ 서비스_사이트 서비스 삭제 ]
	 * 클라이언트의 사이트 서비스 변경 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest					클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	siteServiceMgmtRequest	클라이언트의 사이트 서비스 변경 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException			클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@PostMapping(value="/mgmt/deleteSiteService")
	public ResponseEntity<?> deleteSiteService(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody SiteServiceMgmtRequest siteServiceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		SiteServiceSC siteServiceSC = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSiteServiceList() siteServiceMgmtRequest={}", siteServiceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getTxId())) {
				txId = siteServiceMgmtRequest.getTxId();
			}
			//	
			reason = QNPReasonCode.SERVICE_PREPARING_ERROR;
			errorCode = reason.getReasonCode();
			errorMessage = "요청하신 서비스는 준비중 입니다.";			
			errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
			responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			//	4. 최종 구성된 수행 결과 응답 객체를 ResponseEntity 객체로 구축 및 전달 후 작업을 종료한다
			return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			
			
//			if(SBNUtils.isNull(siteServiceMgmtRequest.getChnlSiteSeq()) || (siteServiceMgmtRequest.getChnlSiteSeq() <= 0L)) {
//				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
//				errorCode = reason.getReasonCode();
//				errorMessage = "작업 수행 필수 키[ CHNL_SITE_SEQ ] 정보 미 전달 오류.";
//				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
//				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
//				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
//				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
//			}
//			if(SBNUtils.isNull(siteServiceMgmtRequest.getSrvcSeq()) || (siteServiceMgmtRequest.getSrvcSeq() <= 0L)) {
//				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
//				errorCode = reason.getReasonCode();
//				errorMessage = "작업 수행 필수 키[ SITE_SEQ ] 정보 미 전달 오류.";
//				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
//				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
//				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
//			}
			//
//			siteServiceSC = new SiteServiceSC();
//			siteServiceSC.setChnlSiteSeq(siteServiceMgmtRequest.getChnlSiteSeq());
//			siteServiceSC.setSrvcSeq(siteServiceMgmtRequest.getSrvcSeq());
//			log.debug("getSiteServiceList() siteServiceSC={}", siteServiceSC.toStringInfo());

			//	4. 최종 구성된 수행 결과 응답 객체를 ResponseEntity 객체로 구축 및 전달 후 작업을 종료한다
//			return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
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
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////
	/**
	 * [ 서비스_사이트 서비스 목록 조회 ]
	 * 클라이언트의 사이트 서비스 목록 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	httpRequest					클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	siteServiceMgmtRequest	클라이언트의 사이트 서비스 목록 조회 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException			클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getSiteServiceList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSiteServiceList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody SiteServiceMgmtRequest siteServiceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		SiteServiceSC siteServiceSC = null;
		List<SiteServiceDto> siteServiceList = new ArrayList<SiteServiceDto>();
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSiteServiceList() siteServiceMgmtRequest={}", siteServiceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getTxId())) {
				txId = siteServiceMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			siteServiceSC = new SiteServiceSC();
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getUseYn())) {
				siteServiceSC.setUseYn(siteServiceMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getChnlCd())) {
				siteServiceSC.setChnlCd(siteServiceMgmtRequest.getChnlCd());
			}
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getSiteCd())) {
				siteServiceSC.setSiteCd(siteServiceMgmtRequest.getSiteCd());
			}
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getSrvcCd())) {
				siteServiceSC.setSrvcCd(siteServiceMgmtRequest.getSrvcCd());
			}
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getSiteSrvcNm())) {
				siteServiceSC.setSiteSrvcNm(siteServiceMgmtRequest.getSiteSrvcNm());
			}
			log.debug("getSiteServiceList() siteServiceSC={}", siteServiceSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 사이트 서비스 매핑 정보에 대한 목록 객체를 추출한다 
			siteServiceList = siteServiceMgmtService.getSiteServiceList(siteServiceSC);
			
			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(siteServiceList)) {
				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 사이트 서비스 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(siteServiceList);
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
	 * [ 서비스_사이트 서비스 상세 조회 ]
	 * 클라이언트의 사이트 서비스 상세 정보 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	httpRequest					클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	siteServiceMgmtRequest	클라이언트의 사이트 서비스 상세 정보 조회 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException			클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getSiteServiceDetail", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSiteServiceDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody SiteServiceMgmtRequest siteServiceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		SiteServiceSC siteServiceSC = null;
		SiteServiceDto siteServiceDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSiteServiceDetail() siteServiceMgmtRequest={}", siteServiceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(siteServiceMgmtRequest.getTxId())) {
				txId = siteServiceMgmtRequest.getTxId();
			}
			if(SBNUtils.isNull(siteServiceMgmtRequest.getChnlSiteSeq()) || (siteServiceMgmtRequest.getChnlSiteSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "작업 수행 필수 키[ CHNL_SITE_SEQ ] 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(siteServiceMgmtRequest.getSrvcSeq()) || (siteServiceMgmtRequest.getSrvcSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "작업 수행 필수 키[ SITE_SEQ ] 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			siteServiceSC = new SiteServiceSC();
			siteServiceSC.setChnlSiteSeq(siteServiceMgmtRequest.getChnlSiteSeq());
			siteServiceSC.setSrvcSeq(siteServiceMgmtRequest.getSrvcSeq());
			log.debug("getSiteServiceDetail() siteServiceSC={}", siteServiceSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 사이트 서비스 매핑 정보에 대한 목록 객체를 추출한다 
			siteServiceDetail = siteServiceMgmtService.getSiteServiceDetail(siteServiceSC);
			
			//	3. 요청 수행 결과 상세 정보 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(siteServiceDetail)) {
				//	3.1. 요청 수행 결과 상세 정보 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 사이트 서비스 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 상세 정보 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(siteServiceDetail);
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
