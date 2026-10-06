package com.qnpeople.rnd.pms.apis.system.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.system.authgrp.controller.AuthGroupMgmtController;
import com.qnpeople.rnd.pms.apis.system.authgrp.domain.request.AuthGrpMgmtRequest;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpSC;
import com.qnpeople.rnd.pms.apis.system.menu.domain.request.MenuMgmtRequest;
import com.qnpeople.rnd.pms.apis.system.menu.service.MenuMgmtService;
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
 * @Package		: com.qnpeople.rnd.pms.apis.system.menu.controller
 * @Filename		: MemuMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 영역의 시스템 메뉴 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	시스템_메뉴 등록
 *  	시스템_메뉴 변경
 *  	시스템_메뉴 삭제
 *  [ 서비스 ]
 *  	시스템_메뉴 코드 중복 여부 체크
 *  	시스템_메뉴 트리 목록 조회
 *  	시스템_메뉴 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/system/menu")
@Slf4j
public class MemuMgmtController extends QNPWebBaseController {

	/* 시스템 메뉴 관리를 위한 실질적인 작업 수행 인터페이스 객체 */
	@Autowired
	private MenuMgmtService menuMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	//	시스템_메뉴 등록
	//  시스템_메뉴 변경
	//	시스템_메뉴 삭제
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 시스템_메뉴 등록 ]
	 * 전달된 클라이언트의 시스템 메뉴 신규 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	menuMgmtRequest		클라이언트로부터 요청된 신규 등록 대상 시스템 메뉴의 신규 등록 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/registerNewSystemMenu", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> registerNewSystemMenu(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody MenuMgmtRequest menuMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerNewSystemMenu() menuMgmtRequest={}", menuMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(menuMgmtRequest.getTxId())) {
				txId = menuMgmtRequest.getTxId();
			}
			
			//	1. 시스템 신규 메뉴 등록 작업을 위한 조건 정보 전달 객체를 구축한다
			
			
			//	2. 전달된 시스템 메뉴 등록 작업 수행 결과를 추출한다
			
			
			//	3. 메뉴 등록 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(resultFlag) {
				//	3.1. 시스템 신규 메뉴 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 메뉴 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 시스템 신규 메뉴 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "시스템 메뉴 등록 작업을 실패 했습니다.";
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
	 * [ 시스템_메뉴 변경 ]
	 * 전달된 클라이언트의 시스템 메뉴 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	menuMgmtRequest		클라이언트로부터 요청된 신규 등록 대상 시스템 메뉴의 변경 작업 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/updateSystemMenu", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> updateSystemMenu(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody MenuMgmtRequest menuMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateSystemMenu() menuMgmtRequest={}", menuMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(menuMgmtRequest.getTxId())) {
				txId = menuMgmtRequest.getTxId();
			}
			
			//	1. 시스템 메뉴 변경 작업을 위한 조건 정보 전달 객체를 구축한다
			
			
			//	2. 전달된 시스템 메뉴 변경 작업 수행 결과를 추출한다
			
			
			//	3. 메뉴 변경 요청 작업 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(resultFlag) {
				//	3.1. 시스템 메뉴 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 메뉴 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 시스템 메뉴 변경 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "시스템 메뉴 변경 작업을 실패 했습니다.";
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
	 * [ 시스템_메뉴 삭제 ]
	 * 전달된 클라이언트의 시스템 메뉴 삭제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	menuMgmtRequest		클라이언트로부터 요청된 신규 등록 대상 시스템 메뉴의 삭제 작업 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/deleteSystemMenu", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> deleteSystemMenu(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody MenuMgmtRequest menuMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteSystemMenu() menuMgmtRequest={}", menuMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(menuMgmtRequest.getTxId())) {
				txId = menuMgmtRequest.getTxId();
			}
			
			//	1. 시스템 메뉴 삭제 작업을 위한 조건 정보 전달 객체를 구축한다
			
			
			//	2. 전달된 시스템 메뉴 삭제 작업 수행 결과를 추출한다
			
			
			//	3. 메뉴 삭제 요청 작업 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(resultFlag) {
				//	3.1. 시스템 메뉴 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 메뉴 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 시스템 메뉴 삭제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "시스템 메뉴 삭제 작업을 실패 했습니다.";
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
	
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	//	시스템_메뉴 코드 중복 여부 체크
	//	시스템_메뉴 트리 목록 조회
	//	시스템_메뉴 상세 조회
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 시스템_메뉴 코드 중복 여부 체크 ]
	 * 전달된 클라이언트의 시스템 메뉴 신규 등록을 위한 메뉴 코드 중복 여부 체크 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	menuMgmtRequest		클라이언트로부터 요청된 신규 등록 대상 시스템 메뉴의 메뉴 코드 중복 여부 체크 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/checkSystemMenuCodeDuplication", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkSystemMenuCodeDuplication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody MenuMgmtRequest menuMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		Boolean duplicationCheckFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkSystemMenuCodeDuplication() menuMgmtRequest={}", menuMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(menuMgmtRequest.getTxId())) {
				txId = menuMgmtRequest.getTxId();
			}
			
			//	1. 시스템 메뉴 코드 중복 여부 체크를 위한 조건 정보 전달 객체를 구축한다
			
			
			//	2. 전달된 시스템 메뉴 코드에 대한 중복 여부 체크 수행 결과를 추출한다
			
			
			//	3. 추출된 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(duplicationCheckFlag) {
				//	3.1. 추출한 시스템 권한 그룹 코드가 중복시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "중복된 시스템 메뉴 코드 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 추출한 시스템 권한 그룹 코드가 사용 가능한 경우, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "사용 가능한 시스템 메뉴 코드 입니다.";
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
	 * [ 시스템_메뉴 트리 목록 조회 ]
	 * 전달된 클라이언트의 시스템 메뉴 트리 목록 추출 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	menuMgmtRequest		클라이언트로부터 요청된 신규 등록 대상 시스템 메뉴의 전체 메뉴 트리 목록 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getSystemTotalMenuTreeList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getSystemTotalMenuTreeList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody MenuMgmtRequest menuMgmtRequest) throws QNPWebException {
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
			log.debug("getSystemTotalMenuTreeList() menuMgmtRequest={}", menuMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(menuMgmtRequest.getTxId())) {
				txId = menuMgmtRequest.getTxId();
			}
			
			//	1. 시스템 전체 메뉴 트리 목록 추출 작업를 위한 조건 정보 전달 객체를 구축한다
			
			
			//	2. 전달된 시스템 전체 메뉴 트리 목록 추출 작업을 수행한다
			
			
			//	3. 추출된 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			
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
	 * [ 시스템_메뉴 상세 조회 ]
	 * 전달된 클라이언트의 시스템 메뉴 상세 정보 추출 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	menuMgmtRequest		클라이언트로부터 요청된 신규 등록 대상 시스템 메뉴의 메뉴 상세 정보 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 권한 그룹 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getSystemMenuDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getSystemMenuDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody MenuMgmtRequest menuMgmtRequest) throws QNPWebException {
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
			log.debug("getSystemMenuDetail() menuMgmtRequest={}", menuMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(menuMgmtRequest.getTxId())) {
				txId = menuMgmtRequest.getTxId();
			}
			
			//	1. 시스템 메뉴 상세 정보 추출 작업를 위한 조건 정보 전달 객체를 구축한다
			
			
			//	2. 전달된 시스템 메뉴 상세 정보 추출 작업을 수행한다
			
			
			//	3. 추출된 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			
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
