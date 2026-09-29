package com.qnpeople.rnd.pms.apis.common.channel.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.common.channel.domain.request.ChannelMgmtRequest;
import com.qnpeople.rnd.pms.apis.common.channel.model.ChannelDto;
import com.qnpeople.rnd.pms.apis.common.channel.model.ChannelSC;
import com.qnpeople.rnd.pms.apis.common.channel.service.ChannelMgmtService;
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
 * @Package		: com.qnpeople.rnd.pms.apis.common.channel.controller
 * @Filename		: ChannelMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.28.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 시스템 채널 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	
 *  
 *  [ 서비스 ]
 *  	01.채널_사용 가능 채널 목록 조회
 *  	02.채널_채널 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/common/channel")
@Slf4j
public class ChannelMgmtController extends QNPWebBaseController {

	/* 실질적인 시스템 지원 채널 관리 수행 서비스 인터페이스 객체 */ 
	@Autowired
	private ChannelMgmtService channelMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////

	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////	
	/**
	 * [ 01.채널_사용 가능 채널 목록 조회 ]
	 * 클라이언트의 사용 가능 공통 채널 정보 목록 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	channelMgmtRequest	클라이언트의 사용 가능 채널 목록 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getAvailableChannelList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getAvailableChannelList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ChannelMgmtRequest channelMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ChannelSC channelSC = null;
		List<ChannelDto> availableChannelList = new ArrayList<ChannelDto>();
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAvailableChannelList() channelMgmtRequest={}", channelMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(channelMgmtRequest.getTxId())) {
				txId = channelMgmtRequest.getTxId();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다			
			channelSC = new ChannelSC();
			channelSC.setUseYn(SBNUseYnType.Y.getTypeCode());			
			log.debug("getAvailableChannelList() channelSC={}", channelSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 채널 목록 정보에 대한 목록 객체를 추출한다 
			availableChannelList = channelMgmtService.getChannelList(channelSC);

			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(availableChannelList)) {
				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 채널 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(availableChannelList);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), responseData);
			}
			log.debug("getAvailableChannelList() responsePacketEntity={}", responsePacketEntity.toStringInfo());
			
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
	 * [ 02.채널_채널 상세 조회 ]
	 * 클라이언트의 채널 상세 정보 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	channelMgmtRequest	클라이언트의 채널 상세 정보 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getChannelDetail", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getChannelDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ChannelMgmtRequest channelMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		Long chnlSeq = null;
		String chnlCd = null;
		ChannelSC channelSC = null;
		ChannelDto channelDetail = null;		
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getChannelDetail() channelMgmtRequest={}", channelMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(channelMgmtRequest.getTxId())) {
				txId = channelMgmtRequest.getTxId();
			}
			if(!SBNUtils.isNull(channelMgmtRequest.getChnlSeq())) {
				chnlSeq = channelMgmtRequest.getChnlSeq();
			}
			if(!SBNUtils.isNull(channelMgmtRequest.getChnlCd())) {
				chnlCd = channelMgmtRequest.getChnlCd();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다			
			channelSC = new ChannelSC();
			if(!SBNUtils.isNull(chnlSeq)) {
				channelSC.setChnlSeq(chnlSeq);
			}
			if(!SBNUtils.isNull(chnlCd)) {
				channelSC.setChnlCd(chnlCd);
			}
			log.debug("getChannelDetail() channelSC={}", channelSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 채널 목록 정보에 대한 목록 객체를 추출한다 
			channelDetail = channelMgmtService.getChannelDetail(channelSC);

			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(channelDetail)) {
				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 채널 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(channelDetail);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), responseData);
			}
			log.debug("getChannelDetail() responsePacketEntity={}", responsePacketEntity.toStringInfo());
			
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
