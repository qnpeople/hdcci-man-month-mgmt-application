package com.qnpeople.rnd.pms.apis.system.authgrp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.system.authgrp.domain.request.AuthGrpMgmtRequest;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpMenuDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpMenuSC;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpSC;
import com.qnpeople.rnd.pms.apis.system.authgrp.service.AuthGrpMgmtService;
import com.qnpeople.rnd.pms.common.auth.QNPAuthData;
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
 * @Package		: com.qnpeople.rnd.pms.apis.system.authgrp.controller
 * @Filename		: AuthGroupMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.02.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 영역의 시스템 권한 그룹 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	시스템_권한 그룹 등록
 *  	시스템_권한 그룹 변경
 *  	시스템_권한 그룹 삭제
 *  	시스템_권한 그룹 그룹 사용 메뉴 매핑
 *  	시스템_권한 그룹 메뉴 사용 해제
 *  [ 서비스 ]
 *  	시스템_권한 그룹 코드 중복 여부 체크
 *  	시스템_권한 그룹 목록 조회
 *  	시스템_권한 그룹 상세 조회
 *  	시스템_권한 그룹 메뉴 트리 목록 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/system/authgrp")
@Slf4j
public class AuthGroupMgmtController extends QNPWebBaseController {
	
	/* 시스템 권한 그룹 관련 실질적인 작업 수행을 위한 인터페이스 객체 */
	@Autowired
	private AuthGrpMgmtService authGrpMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	//	시스템_권한 그룹 등록
	//  시스템_권한 그룹 변경
	//	시스템_권한 그룹 삭제
	//	시스템_권한 그룹 그룹 사용 메뉴 매핑
	//	시스템_권한 그룹 메뉴 사용 해제
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 시스템_권한 그룹 등록 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 신규 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest	클라이언트로부터 요청된 시스템 권한 그룹 신규 등록을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/registerSystemAuthGroup")
	public ResponseEntity<?> registerSystemAuthGroup(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AuthGrpDto registerAuthGroup = null;
		Boolean resultFlag = false;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerSystemAuthGroup() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			
			//	1. 클라이언트로부터 전달된 요청 전달 객체로부터 전달된 등록 대상 그룹 권한 정보가 존재 시, 등록할 그룹 권한 정보를 추출한다
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGroup())) {
				registerAuthGroup = authGrpMgmtRequest.getAuthGroup();
				registerAuthGroup.setRgstSeq(adminAuthData.getClientSeq());
			}
			log.debug("registerSystemAuthGroup() registerAuthGroup={}", registerAuthGroup.toStringInfo());
			
			//	2. 전달된 신규 등록 대상 그룹 권한 정보를 등록 후, 등록 수행 결과를 전달 받는다
			resultFlag = authGrpMgmtService.registerSystemAuthGroup(registerAuthGroup);
			log.debug("registerSystemAuthGroup() resultFlag={}", resultFlag);
			
			//	3. 신규 시스템 권한 그룹 정보 등록 작업 수행 결과에 대한 작업을 수행한다
			if(!resultFlag) {
				//	3.1. 신규 시스템 권한 그룹 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 시스템 권한 그룹 정보 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 시스템 권한 그룹 정보 등록 작업 정상 수행 시, 정상 수행 결과에 대한 클라이언트로 전달할 응답 패킷 객체를 구축한다
				String resultMessage = "신규 시스템 권한 그룹 정보 등록 작업을 정상 수행했습니다.";
				responseData = new QNPResponseData(resultMessage);
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
	 * [ 시스템_권한 그룹 변경 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest	클라이언트로부터 요청된 시스템 권한 그룹 변경을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateSystemAuthGroup")
	public ResponseEntity<?> updateSystemAuthGroup(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AuthGrpDto updateAuthGroup = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateSystemAuthGroup() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			
			//	1. 클라이언트로부터 전달된 요청 전달 객체로부터 전달된 변경 대상 그룹 권한 정보가 존재 시, 변경할 그룹 권한 정보를 추출한다
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGroup())) {
				updateAuthGroup = authGrpMgmtRequest.getAuthGroup();
				updateAuthGroup.setUpdtSeq(adminAuthData.getClientSeq());
			}
			log.debug("registerSystemAuthGroup() updateAuthGroup={}", updateAuthGroup.toStringInfo());
			
			//	2. 전달된 변경 대상 그룹 권한 정보를 갱신 후, 갱신 수행 결과를 전달 받는다
			resultFlag = authGrpMgmtService.updateSystemAuthGroup(updateAuthGroup);
			log.debug("registerSystemAuthGroup() resultFlag={}", resultFlag);
			
			//	3. 시스템 권한 그룹 정보 뱐걍 작업 수행 결과에 대한 작업을 수행한다
			if(!resultFlag) {
				//	3.1. 시스템 권한 그룹 정보 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 정보 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 시스템 권한 그룹 정보 변경 작업 정상 수행 시, 수행 결과 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				String resultMessage = "시스템 권한 그룹 정보 변경 작업을 정상 수행했습니다.";
				responseData = new QNPResponseData(resultMessage);
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
	 * [ 시스템_권한 그룹 삭제 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 삭제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest	클라이언트로부터 요청된 시스템 권한 그룹 삭제를 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/deleteSystemAuthGroup")
	public ResponseEntity<?> deleteSystemAuthGroup(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AuthGrpSC authGrpSC = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteSystemAuthGroup() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			if(SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpSeq()) || (authGrpMgmtRequest.getAuthGrpSeq() <= 0) &&
			   SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제할 시스템 권한 그룹 키 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			}
			
			//	1. 클라이언트로부터 전달된 요청 전달 객체로부터 전달된 식제 대상 그룹 권한 정보가 존재 시, 변경할 그룹 권한 정보를 추출하여 조건 정보 객체를 구축한다
			authGrpSC = new AuthGrpSC();
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpSeq()) && (authGrpMgmtRequest.getAuthGrpSeq() > 0)) {
				authGrpSC.setAuthGrpSeq(authGrpMgmtRequest.getAuthGrpSeq());
			}
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpCd())) {
				authGrpSC.setAuthGrpCd(authGrpMgmtRequest.getAuthGrpCd());
			}
			log.debug("deleteSystemAuthGroup() authGrpSC={}", authGrpSC.toStringInfo());
			
			//	2. 
			resultFlag = authGrpMgmtService.deleteSystemAuthGroup(authGrpSC);
			
			//	3. 
			if(!resultFlag) {
				//	3.1. 시스템 권한 그룹 정보 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 정보 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 시스템 권한 그룹 정보 삭제 작업 정상 수행 시, 수행 결과 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				String resultMessage = "시스템 권한 그룹 정보 삭제 작업을 정상 수행했습니다.";
				responseData = new QNPResponseData(resultMessage);
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
	 * [ 시스템_권한 그룹 그룹 사용 메뉴 매핑 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 메뉴 지정 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest	클라이언트로부터 요청된 시스템 권한 그룹 메뉴 지정을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/assignSystemAuthGroupMenu")
	public ResponseEntity<?> assignSystemAuthGroupMenu(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("assignSystemAuthGroupMenu() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			
			//	1. 
			
			
			//	2. 
			
			
			//	3. 
			reason = QNPReasonCode.FRAMEWORK_NETWORK_SERVICE_NOT_IMPLEMENT_ERROR;
			errorCode = reason.getReasonCode();
			String resultMessage = "서비스 준비중 입니다.";
			responseData = new QNPResponseData(resultMessage);
			responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), responseData);
			
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
	 * [ 시스템_권한 그룹 메뉴 사용 해제 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 메뉴 해제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest	클라이언트로부터 요청된 시스템 권한 그룹 메뉴 해제를 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/releaseSystemAuthGroupMenu")
	public ResponseEntity<?> releaseSystemAuthGroupMenu(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("releaseSystemAuthGroupMenu() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			
			//	1. 
			
			
			//	2. 
			
			
			//	3. 
			reason = QNPReasonCode.FRAMEWORK_NETWORK_SERVICE_NOT_IMPLEMENT_ERROR;
			errorCode = reason.getReasonCode();
			String resultMessage = "서비스 준비중 입니다.";
			responseData = new QNPResponseData(resultMessage);
			responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), responseData);
			
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
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	//	시스템_권한 그룹 코드 중복 여부 체크
	//	시스템_권한 그룹 목록 조회
	//	시스템_권한 그룹 상세 조회
	//	시스템_권한 그룹 메뉴 트리 목록 조회
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 시스템_권한 그룹 코드 중복 여부 체크 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 신규 등록을 위한 권한 그룹 코드 중복 여부 체크 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.03
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest	클라이언트로부터 요청된 신규 등록 대상 시스템 권한 그룹의 권한 그룹 코드 중복 여부 체크 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/checkSystemAuthGroupCodeDuplication", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkSystemAuthGroupCodeDuplication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AuthGrpSC authGrpSC = null;
		Boolean duplicationCheckFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkSystemAuthGroupCodeDuplication() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			
			//	1. 시스템 권한 그룹 코드 중복 여부 체크를 위한 조건 정보 전달 객체를 구축한다
			authGrpSC = new AuthGrpSC();
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpCd())) {
				authGrpSC.setAuthGrpCd(authGrpMgmtRequest.getAuthGrpCd());
			}
			log.debug("checkSystemAuthGroupCodeDuplication() authGrpSC={}", authGrpSC.toStringInfo());
			
			//	2. 전달된 시스템 권한 그룹 코드에 대한 중복 여부 체크 수행 결과를 추출한다
			duplicationCheckFlag = authGrpMgmtService.isSystemAuthGroupCdDuplicated(authGrpSC);
			
			//	3. 추출된 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(duplicationCheckFlag) {
				//	3.1. 추출한 시스템 권한 그룹 코드가 중복시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "중복된 시스템 권한 그룹 코드 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 추출한 시스템 권한 그룹 코드가 사용 가능한 경우, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "사용 가능한 시스템 권한 그룹 코드 입니다.";
				responseData = new QNPResponseData(resultMessage);
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
	 * [ 시스템_권한 그룹 목록 조회 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest	클라이언트로부터 요청된 시스템 권한 그룹 목록 정보 추출을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getSystemAuthGroupList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSystemAuthGroupList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AuthGrpSC authGrpSC = null;
		List<AuthGrpDto> authGroupList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSystemAuthGroupList() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			
			//	1. 클라이언트의 시스템 권한 그룹 목록 조회 요청으로 전달된 조회 조건 정보를 구성한다
			authGrpSC = new AuthGrpSC();
			if(!SBNUtils.isNull(authGrpMgmtRequest.getUseYn())) {
				authGrpSC.setUseYn(authGrpMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpTp())) {
				authGrpSC.setAuthGrpTp(authGrpMgmtRequest.getAuthGrpTp());
			}
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpNm())) {
				authGrpSC.setAuthGrpNm(authGrpMgmtRequest.getAuthGrpNm());
			}
			log.debug("getSystemAuthGroupList() authGrpSC={}", authGrpSC.toStringInfo());			
			
			//	2. 클라이언트로 전달된 요청 수행 조건 정보를 이용하여 시스템 권한 그룹 목록 객체를 추출한다
			authGroupList = authGrpMgmtService.getSystemAuthGroupList(authGrpSC);
			
			//	3. 추출한 시스템 권한 그룹 목록 객체의 유무에 따른 작업을 수행한다
			if(SBNUtils.isNull(authGroupList)) {
				//	3.1. 추출한 시스템 권한 목록 객체가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 추출한 시스템 권한 그룹 목록 객체가 존재 시, 목록 객체를 이용하여 클라이언트로 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(authGroupList);
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
	 * [ 시스템_권한 그룹 상세 조회 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 상세 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest		클라이언트로부터 요청된 시스템 권한 그룹 상세 정보 추출을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getSystemAuthGroupDetail", method={ RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSystemAuthGroupDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AuthGrpSC authGrpSC = null;
		AuthGrpDto authGroupDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSystemAuthGroupDetail() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			
			//	1. 클라이언트의 시스템 권한 그룹 상세 조회 요청으로 전달된 조회 조건 정보를 구성한다
			authGrpSC = new AuthGrpSC();
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpSeq())) {
				authGrpSC.setAuthGrpSeq(authGrpMgmtRequest.getAuthGrpSeq());
			}
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpCd())) {
				authGrpSC.setAuthGrpCd(authGrpMgmtRequest.getAuthGrpCd());
			}
			log.debug("getSystemAuthGroupDetail() authGrpSC={}", authGrpSC.toStringInfo());
			
			//	2. 클라이언트로 전달된 요청 수행 조건 정보를 이용하여 시스템 권한 그룹 상세 객체를 추출한다
			authGroupDetail = authGrpMgmtService.getSystemAuthGroupDetail(authGrpSC);
			
			//	3. 추출한 시스템 권한 그룹 상세 정보 객체의 유무에 따른 작업을 수행한다
			if(SBNUtils.isNull(authGroupDetail)) {
				//	3.1. 추출한 시스템 권한 상세 정보 객체가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 추출한 시스템 권한 그룹 상세 정보 객체가 존재 시, 목록 객체를 이용하여 클라이언트로 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(authGroupDetail);
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
	 * [ 시스템_권한 그룹 메뉴 트리 목록 조회 ]
	 * 전달된 클라이언트의 시스템 권한 그룹 지정 사용 메뉴 트리 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	authGrpMgmtRequest		클라이언트로부터 요청된 시스템 권한 그룹 지정 사용 메뉴 트리 목록 정보 추출을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getSystemAuthGroupMenuTreeList", method={ RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSystemAuthGroupMenuTreeList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthGrpMgmtRequest authGrpMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AuthGrpSC authGrpMenuSC = null;
		List<AuthGrpMenuDto> authGroupMenuTreeList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSystemAuthGroupMenuTreeList() authGrpMgmtRequest={}", authGrpMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(authGrpMgmtRequest.getTxId())) {
				txId = authGrpMgmtRequest.getTxId();
			}
			
			//	1. 클라이언트의 시스템 권한 그룹 지정 서비스 메뉴 트리 목록 조회 요청으로 전달된 조회 조건 정보를 구성한다
			authGrpMenuSC = new AuthGrpSC();
			if(!SBNUtils.isNull(authGrpMgmtRequest.getUseYn())) {
				authGrpMenuSC.setUseYn(authGrpMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpSeq())) {
				authGrpMenuSC.setAuthGrpSeq(authGrpMgmtRequest.getAuthGrpSeq());
			}
			if(!SBNUtils.isNull(authGrpMgmtRequest.getAuthGrpCd())) {
				authGrpMenuSC.setAuthGrpCd(authGrpMgmtRequest.getAuthGrpCd());
			}
			log.debug("getSystemAuthGroupMenuTreeList() authGrpMenuSC={}", authGrpMenuSC.toStringInfo());
			
			//	2. 클라이언트로 전달된 요청 수행 조건 정보를 이용하여 시스템 권한 그룹 지정 서비스 메뉴 Tree 목록 객체를 추출한다
			authGroupMenuTreeList = authGrpMgmtService.getSystemAuthGroupMenuTreeList(authGrpMenuSC);
			
			//	3. 추출된 권한 그룹 지정 메뉴 Tree 목록 결과 객체에 따라 작업을 수행한다
			if(SBNUtils.isNull(authGroupMenuTreeList)) {
				//	3.1. 추출한 시스템 권한 그룹 지정 메뉴 Tree 목록 객체가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 정보 지정 메뉴 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 추출한 시스템 권한 그룹 지정 메뉴 Tree 목록 객체가 존재 시, 목록 객체를 이용하여 클라이언트로 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(authGroupMenuTreeList);
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
