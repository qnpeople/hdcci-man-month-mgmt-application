package com.qnpeople.rnd.pms.apis.common.channelsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.common.channelsite.domain.request.ChannelSiteMgmtRequest;
import com.qnpeople.rnd.pms.apis.common.channelsite.model.ChannelSiteDto;
import com.qnpeople.rnd.pms.apis.common.channelsite.model.ChannelSiteSC;
import com.qnpeople.rnd.pms.apis.common.channelsite.service.ChannelSiteMgmtService;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseErrorData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponsePacketEntityData;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;
import com.qnpeople.rnd.pms.utils.QNPResponseEntityUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.sbn.platformhub.framework.core.common.web.modules.controller.SBNWebBaseController;
import kr.co.sbn.platformhub.framework.core.types.SBNUseYnType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.common.channelsite.controller
 * @Filename		: ChannelSiteMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.28.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 시스템 채널에 대한 사이트 매핑 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	
 *  
 *  [ 서비스 ]
 *  	01.채널:사이트_사용 가능 채널별 사이트 목록 조회
 *  	02.채널:사이트_채널별 사이트 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/common/channelsite")
@Slf4j
public class ChannelSiteMgmtController extends SBNWebBaseController {

	/** 실질적인 시스템 지원 채널별 사이트 관리 수행 서비스 인터페이스 객체 */
	@Autowired
	private ChannelSiteMgmtService channelSiteMgmtService;
	
	/**
	 * [ 01.채널:사이트_사용 가능 채널별 사이트 목록 조회 ]
	 * 클라이언트의 사용 가능 공통 채널별 사이트 정보 목록 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	channelMgmtRequest
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getAvailableChannelSiteList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getAvailableChannelSiteList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ChannelSiteMgmtRequest channelSiteMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ChannelSiteSC channelSiteSC = null;
		List<ChannelSiteDto> availableChannelSiteList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAvailableChannelSiteList() channelSiteMgmtRequest={}", channelSiteMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getTxId())) {
				txId = channelSiteMgmtRequest.getTxId();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			channelSiteSC = new ChannelSiteSC();
			channelSiteSC.setUseYn(SBNUseYnType.Y.getTypeCode());
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getChnlSiteSeq())) {
				channelSiteSC.setChnlSiteSeq(channelSiteMgmtRequest.getChnlSiteSeq());
			}
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getChnlSeq())) {
				channelSiteSC.setChnlSeq(channelSiteMgmtRequest.getChnlSeq());
			}
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getSiteSeq())) {
				channelSiteSC.setSiteSeq(channelSiteMgmtRequest.getSiteSeq());
			}
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getChnlCd())) {
				channelSiteSC.setChnlCd(channelSiteMgmtRequest.getChnlCd());
			}
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getSiteCd())) {
				channelSiteSC.setSiteCd(channelSiteMgmtRequest.getSiteCd());
			}
			log.debug("getAvailableChannelSiteList() channelSiteSC={}", channelSiteSC.toStringInfo());
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 Tree 구조의 목록 객체를 추출한다 
			availableChannelSiteList = channelSiteMgmtService.getChannelSiteList(channelSiteSC);
			
			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(availableChannelSiteList)) {
				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 채널별 사이트 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(availableChannelSiteList);
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
	 * [ 02.채널:사이트_채널별 사이트 상세 조회 ]
	 * 클라이언트의 사용 가능 공통 채널별 사이트 상세 정보 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.29
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	channelMgmtRequest
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getChannelSiteDetail", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getChannelSiteDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ChannelSiteMgmtRequest channelSiteMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ChannelSiteSC channelSiteSC = null;
		ChannelSiteDto channelSiteDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getChannelSiteDetail() channelSiteMgmtRequest={}", channelSiteMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getTxId())) {
				txId = channelSiteMgmtRequest.getTxId();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			channelSiteSC = new ChannelSiteSC();
			channelSiteSC.setUseYn(SBNUseYnType.Y.getTypeCode());
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getChnlSiteSeq())) {
				channelSiteSC.setChnlSiteSeq(channelSiteMgmtRequest.getChnlSiteSeq());
			}
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getChnlSeq())) {
				channelSiteSC.setChnlSeq(channelSiteMgmtRequest.getChnlSeq());
			}
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getSiteSeq())) {
				channelSiteSC.setSiteSeq(channelSiteMgmtRequest.getSiteSeq());
			}
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getChnlCd())) {
				channelSiteSC.setChnlCd(channelSiteMgmtRequest.getChnlCd());
			}
			if(!SBNUtils.isNull(channelSiteMgmtRequest.getSiteCd())) {
				channelSiteSC.setSiteCd(channelSiteMgmtRequest.getSiteCd());
			}
			log.debug("getChannelSiteDetail() channelSiteSC={}", channelSiteSC.toStringInfo());
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 Tree 구조의 목록 객체를 추출한다 
			channelSiteDetail = channelSiteMgmtService.getChannelSiteDetail(channelSiteSC);
			
			//	3. 요청 수행 결과 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(channelSiteDetail)) {
				//	3.1. 요청 수행 결과 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 채널별 사이트 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(channelSiteDetail);
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
