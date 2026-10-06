package com.qnpeople.rnd.pms.apis.system.admin.controller;

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

import com.qnpeople.rnd.pms.apis.system.admin.domain.request.AdminMgmtRequest;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpSC;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminSC;
import com.qnpeople.rnd.pms.apis.system.admin.service.AdminMgmtService;
import com.qnpeople.rnd.pms.common.auth.QNPAuthData;
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
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.system.admin.controller
 * @Filename		: AdminMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 영역의 시스템 관리자 정보 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	시스템_관리자 등록
 *  	시스템_관리자 변경
 *  	시스템_관리자 삭제
 *  	시스템_관리자 권한 그룹 매핑
 *  	시스템_관리자 권한 그룹 해제
 *  [ 서비스 ]
 *  	시스템_관리자 목록 조회
 *  	시스템_관리자 상세 조회
 *  	시스템_관리자 권한 그룹 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/system/admin")
@Slf4j
public class AdminMgmtController extends SBNWebBaseController {

	/* 시스템 관리자 관련 클라이언트 요청 수행을 위한 SQL 쿼리 수행 매핑 인터페이스 객체 */
	@Autowired
	private AdminMgmtService adminMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 시스템_관리자 등록 ]
	 * 시스템 관리자의 신규 등록 요청에 대한 작업 수행 후, 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	adminMgmtRequest		시스템 관리자 신규 등록 작업 수행을 위한 요청 정보 전달 객체
	 * @return		클라이언트로부터의 요청 작업 수행 결과에 대한 응답 패킷 전달 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 관리자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/registerNewSystemAdmin")
	public ResponseEntity<?> registerNewSystemAdmin(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AdminMgmtRequest adminMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(0L);									// 임시 테스트를 위한 정보 설정
		String txId = SBNUtils.createSystemRandomId(true);		
		AdminDto newSystemAdmin = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerNewSystemAdmin() adminMgmtRequest={}", adminMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(adminMgmtRequest.getTxId())) {
				txId = adminMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmin())) {
				newSystemAdmin = adminMgmtRequest.getAdmin();
			}
			newSystemAdmin.setRgstSeq(adminAuthData.getClientSeq());
			log.debug("getSystemAdminAuthGroupList() newSystemAdmin={}", newSystemAdmin.toStringInfo());
			
			//	2. 전달된 신규 등록 대상 시스템 관리자 정보를 등록하고 수행 결과 Flag 를 전달 받는다
			resultFlag = adminMgmtService.registerNewSystmAdmin(newSystemAdmin);
			log.debug("getSystemAdminAuthGroupList() resultFlag={}", resultFlag);
			//	3. 요청 수행 결과 목록 정보 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 요청 수행 결과 목록 정보 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 시스템 관리자 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 정보 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				//	3.2.1. 등록한 신규 시스템 관리자의 정보를 조회 한다
				AdminSC resultAdminSC = new AdminSC();
				resultAdminSC.setAdmLognId(newSystemAdmin.getAdmLognId());
				AdminDto resultAdminDto = adminMgmtService.getSysemAdminDetail(resultAdminSC);
				if(SBNUtils.isNull(resultAdminDto)) {
					reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "신규 시스템 관리자 등록 작업을 실패 했습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				}
				//	3.2.2. 조회된 신규 시스템 관리자의 정보를 전달하기 위해 응답 패킷 데이터로 설정한다
				responseData = new QNPResponseData(resultAdminDto);
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
	 * [ 시스템_관리자 변경 ]
	 * 시스템 관리자의 변경 요청에 대한 작업 수행 후, 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	adminMgmtRequest		시스템 관리자 변경 작업 수행을 위한 요청 정보 전달 객체
	 * @return		클라이언트로부터의 요청 작업 수행 결과에 대한 응답 패킷 전달 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 관리자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateSystemAdmin")
	public ResponseEntity<?> updateSystemAdmin(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AdminMgmtRequest adminMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		String txId = SBNUtils.createSystemRandomId(true);		
		AdminDto updateSystemAdmin = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateSystemAdmin() adminMgmtRequest={}", adminMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(adminMgmtRequest.getTxId())) {
				txId = adminMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			if(SBNUtils.isNull(adminMgmtRequest.getAdmin())) {
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 시스템 관리자 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			}
			updateSystemAdmin = adminMgmtRequest.getAdmin();
			if(SBNUtils.isNull(updateSystemAdmin.getAdmSeq()) || (updateSystemAdmin.getAdmSeq() <= 0)) {
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 시스템 관리자 키 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			}
			updateSystemAdmin.setUpdtSeq(adminAuthData.getClientSeq());
			log.debug("updateSystemAdmin() updateSystemAdmin={}", updateSystemAdmin.toStringInfo());
			
			//	2. 전달된 신규 등록 대상 시스템 관리자 정보를 등록하고 수행 결과 Flag 를 전달 받는다
			resultFlag = adminMgmtService.updateSystemAdmin(updateSystemAdmin);
			log.debug("updateSystemAdmin() resultFlag={}", resultFlag);
			
			//	3. 요청 수행 결과 목록 정보 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 요청 수행 결과 목록 정보 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {				
				//	3.2. 요청 수행 결과 목록 정보 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				//	3.2.1. 변경한 시스템 관리자의 정보를 조회 한다
				AdminSC resultAdminSC = new AdminSC();
				resultAdminSC.setAdmSeq(updateSystemAdmin.getAdmSeq());
				AdminDto resultAdminDto = adminMgmtService.getSysemAdminDetail(resultAdminSC);
				if(SBNUtils.isNull(resultAdminDto)) {
					reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "변경 시스템 관리자 등록 작업을 실패 했습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				}
				//	3.2.2. 조회된 신규 시스템 관리자의 정보를 전달하기 위해 응답 패킷 데이터로 설정한다
				responseData = new QNPResponseData(updateSystemAdmin);
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
	 * [ 시스템_관리자 삭제 ]
	 * 시스템 관리자의 삭제 요청에 대한 작업 수행 후, 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	adminMgmtRequest		시스템 관리자 삭제 작업 수행을 위한 요청 정보 전달 객체
	 * @return		클라이언트로부터의 요청 작업 수행 결과에 대한 응답 패킷 전달 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 관리자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/deleteSystemAdmin")
	public ResponseEntity<?> deleteSystemAdmin(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AdminMgmtRequest adminMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		String txId = SBNUtils.createSystemRandomId(true);		
		AdminSC adminSC = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteSystemAdmin() adminMgmtRequest={}", adminMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(adminMgmtRequest.getTxId())) {
				txId = adminMgmtRequest.getTxId();
			}
			
			//	1. 	전달된 시스템 관리자 삭제 수행 요청 정보에서 삭제 수행을 위한 키 정보를 추출하여 조건 정보를 구축한다
			adminSC = new AdminSC();
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmSeq())) {
				adminSC.setAdmSeq(adminMgmtRequest.getAdmSeq());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmLognId())) {
				adminSC.setAdmLognId(adminMgmtRequest.getAdmLognId());
			}
			log.debug("deleteSystemAdmin() adminSC={}", adminSC.toStringInfo());
			
			//	2. 전달된 삭제 대상 시스템 관리자 정보를 삭제 후 수행 결과 Flag 를 전달 받는다
			resultFlag = adminMgmtService.deleteSystemAdmin(adminSC);
			log.debug("updateSystemAdmin() resultFlag={}", resultFlag);
			
			//	3. 요청 수행 결과 목록 정보 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 요청 수행 결과 목록 정보 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {				
				//	3.2. 요청 수행 결과 목록 정보 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				String resultMessage = "요청 하신 시스템 관리자 삭제 작업을 정상으로 처리 하였습니다.";
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
	 * [ 시스템_관리자 권한 그룹 매핑 ]
	 * 시스템 관리자에 대한 관리자 권한 그룹 매핑 작업을 수행 후, 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	
	 * @return		
	 * @throws 	QNPWebException		클라이언트의 시스템 관리자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/assignSystemAdminAuthGroup")
	public ResponseEntity<?> assignSystemAdminAuthGroup(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AdminMgmtRequest adminMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AdminSC adminSC = null;
		List<AdminAuthGrpDto> adminAuthGrpList = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("assignSystemAdminAuthGroup() adminMgmtRequest={}", adminMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(adminMgmtRequest.getTxId())) {
				txId = adminMgmtRequest.getTxId();
			}
			//	1. 	전달된 시스템 관리자 권한 그룹 지정 수행 요청 정보에서 수행을 위한 유효성 확인 및 수행 정보를 추출하여 조건 정보를 구축한다
			if((SBNUtils.isNull(adminMgmtRequest.getAdmSeq()) || (adminMgmtRequest.getAdmSeq() <= 0)) && SBNUtils.isNull(adminMgmtRequest.getAdmLognId())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 권한 그룹 지정 관리자 키 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			}
			//	
			adminSC = new AdminSC();
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmLognId())) {
				adminSC.setAdmLognId(adminMgmtRequest.getAdmLognId());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmSeq()) && (adminMgmtRequest.getAdmSeq() > 0)) {
				adminSC.setAdmSeq(adminMgmtRequest.getAdmSeq());
			} else {
				AdminDto adminDetail = adminMgmtService.getSysemAdminDetail(adminSC);
				if(SBNUtils.isNull(adminDetail)) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "시스템 관리자 권한 그룹 지정 관리자 정보 미 존재 오류.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				}
				adminSC.setAdmSeq(adminDetail.getAdmSeq());
			}
			log.debug("assignSystemAdminAuthGroup() adminSC={}", adminSC.toStringInfo());
			
			if(SBNUtils.isNull(adminMgmtRequest.getAdminAuthGroupList())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 권한 그룹 지정 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			}
			adminAuthGrpList = adminMgmtRequest.getAdminAuthGroupList();
			for(int i = 0; i < adminAuthGrpList.size(); i++) {
				adminAuthGrpList.get(i).setAdmSeq(adminSC.getAdmSeq());
				adminAuthGrpList.get(i).setRgstSeq(adminAuthData.getClientSeq());
				log.debug("assignSystemAdminAuthGroup() adminAuthGrpList[{}]={}", i, adminAuthGrpList.get(i).toStringInfo());
			}
			
			//	2. 전달된 권한 그룹 지정 대상 시스템 관리자의 권한 그룹 지정 작업 수행 결과 Flag 를 전달 받는다
			resultFlag = adminMgmtService.assignSystemAdminAuthGroup(adminAuthGrpList);
			
			//	3. 관리자 권한 그룹 지정 요청 수행 결과에 따른 전달 수행 결과 응답 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 관리자 권한 지정 작업 실패 시, 수행 실패 응답 메시지 구성 및 전달 후 작업을 종료한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 권한 그룹 지정 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {				
				//	3.2. 관리자 권한 지정 작업 정상 수행 시, 정상 수행 응답 메시지 구성 및 전달 후 작업을 종료한다
				String resultMessage = "요청 하신 시스템 관리자 권한 그룹 지정 작업을 정상으로 처리 하였습니다.";
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
	 * [ 시스템_관리자 권한 그룹 매핑 ]
	 * 시스템 관리자에 대한 관리자 권한 그룹 매핑 작업을 수행 후, 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	
	 * @return		
	 * @throws 	QNPWebException		클라이언트의 시스템 관리자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/releaseSystemAdminAuthGroup")
	public ResponseEntity<?> releaseSystemAdminAuthGroup(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AdminMgmtRequest adminMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);		
		AdminSC adminSC = null;
		List<AdminAuthGrpDto> adminAuthGrpList = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("releaseSystemAdminAuthGroup() adminMgmtRequest={}", adminMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(adminMgmtRequest.getTxId())) {
				txId = adminMgmtRequest.getTxId();
			}
			//	1. 	전달된 시스템 관리자 권한 그룹 해지 수행 요청 정보에서 삭제 수행을 위한 키 정보를 추출하여 조건 정보를 구축한다
			if((SBNUtils.isNull(adminMgmtRequest.getAdmSeq()) || (adminMgmtRequest.getAdmSeq() <= 0)) && SBNUtils.isNull(adminMgmtRequest.getAdmLognId())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 권한 그룹 해지 관리자 키 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			}
			//	
			adminSC = new AdminSC();
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmLognId())) {
				adminSC.setAdmLognId(adminMgmtRequest.getAdmLognId());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmSeq()) && (adminMgmtRequest.getAdmSeq() > 0)) {
				adminSC.setAdmSeq(adminMgmtRequest.getAdmSeq());
			} else {
				AdminDto adminDetail = adminMgmtService.getSysemAdminDetail(adminSC);
				if(SBNUtils.isNull(adminDetail)) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "시스템 관리자 권한 그룹 해지 관리자 정보 미 존재 오류.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				}
				adminSC.setAdmSeq(adminDetail.getAdmSeq());
			}
			log.debug("releaseSystemAdminAuthGroup() adminSC={}", adminSC.toStringInfo());
			
			if(SBNUtils.isNull(adminMgmtRequest.getAdminAuthGroupList())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 권한 그룹 해지 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			}
			adminAuthGrpList = adminMgmtRequest.getAdminAuthGroupList();
			for(int i = 0; i < adminAuthGrpList.size(); i++) {
				adminAuthGrpList.get(i).setAdmSeq(adminSC.getAdmSeq());
				log.debug("releaseSystemAdminAuthGroup() adminAuthGrpList[{}]={}", i, adminAuthGrpList.get(i).toStringInfo());
			}
			
			//	2. 전달된 권한 그룹 해지 대상 시스템 관리자의 권한 그룹 해지 작업 수행 결과 Flag 를 전달 받는다
			resultFlag = adminMgmtService.releaseSystemAdminAuthGroup(adminAuthGrpList);
			
			//	3. 관리자 권한 그룹 해지 요청 수행 결과에 따른 전달 수행 결과 응답 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 관리자 권한 해지 작업 실패 시, 수행 실패 응답 메시지 구성 및 전달 후 작업을 종료한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 권한 그룹 해지 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {				
				//	3.2. 관리자 권한 해지 작업 정상 수행 시, 정상 수행 응답 메시지 구성 및 전달 후 작업을 종료한다
				String resultMessage = "요청 하신 시스템 관리자 권한 그룹 해지 작업을 정상으로 처리 하였습니다.";
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
	/////////////////////////////////////////////////////////////////	
	/**
	 * [ 시스템_관리자 목록 조회 ]
	 * 시스템 관리자의 목록 조회 요청에 대한 작업 수행 후, 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	adminMgmtRequest		시스템 관리자 목록 조회 작업 수행을 위한 요청 정보 전달 객체
	 * @return		클라이언트로부터의 요청 작업 수행 결과에 대한 응답 패킷 전달 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 관리자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getSystemAdminList", method={ RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSystemAdminList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AdminMgmtRequest adminMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		AdminSC adminSC = null;
		List<AdminDto> systemAdminList = new ArrayList<AdminDto>();
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSystemAdminList() adminMgmtRequest={}", adminMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(adminMgmtRequest.getTxId())) {
				txId = adminMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			adminSC = new AdminSC();
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmTp())) {
				adminSC.setAdmTp(adminMgmtRequest.getAdmTp());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmLognId())) {
				adminSC.setAdmLognId(adminMgmtRequest.getAdmLognId());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmNm())) {
				adminSC.setAdmNm(adminMgmtRequest.getAdmNm());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getInitPwdChngYn())) {
				adminSC.setInitPwdChngYn(adminMgmtRequest.getInitPwdChngYn());
			}
			log.debug("getSystemAdminList() adminSC={}", adminSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 시스템 관리자에 대한 목록 객체를 추출한다 
			systemAdminList = adminMgmtService.getSysemAdminList(adminSC);
			
			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(systemAdminList)) {
				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 시스템 관리자 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(systemAdminList);
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
	 * [ 시스템_관리자 상세 조회 ]
	 * 시스템 관리자의 상세 조회 요청에 대한 작업 수행 후, 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	adminMgmtRequest		시스템 관리자 상세 조회 작업 수행을 위한 요청 정보 전달 객체
	 * @return		클라이언트로부터의 요청 작업 수행 결과에 대한 응답 패킷 전달 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 관리자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getSystemAdminDetail", method={ RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSystemAdminDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AdminMgmtRequest adminMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		AdminSC adminSC = null;
		AdminDto systemAdminDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSystemAdminDetail() adminMgmtRequest={}", adminMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(adminMgmtRequest.getTxId())) {
				txId = adminMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			adminSC = new AdminSC();
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmSeq()) && (adminMgmtRequest.getAdmSeq() > 0)) {
				adminSC.setAdmSeq(adminMgmtRequest.getAdmSeq());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmLognId())) {
				adminSC.setAdmLognId(adminMgmtRequest.getAdmLognId());
			}
			log.debug("getSystemAdminList() adminSC={}", adminSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 특정 시스템 사용자에 대한 상세 정보 객체를 추출한다 
			systemAdminDetail = adminMgmtService.getSysemAdminDetail(adminSC);
			
			//	3. 요청 수행 결과 상세 정보 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(systemAdminDetail)) {
				//	3.1. 요청 수행 결과 상세 정보 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 시스템 관리자 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 상세 정보 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(systemAdminDetail);
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
	 * [ 시스템_관리자 권한 그룹 조회 ]
	 * 시스템 관리자의 상세 조회 요청에 대한 작업 수행 후, 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	httpRequest				클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	adminMgmtRequest		시스템 관리자 상세 조회 작업 수행을 위한 요청 정보 전달 객체
	 * @return		클라이언트로부터의 요청 작업 수행 결과에 대한 응답 패킷 전달 객체
	 * @throws 	QNPWebException		클라이언트의 시스템 관리자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getSystemAdminAuthGroupList", method={ RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getSystemAdminAuthGroupList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AdminMgmtRequest adminMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		AdminAuthGrpSC adminAuthGrpSC = null;
		List<AdminAuthGrpDto> adminAuthGroupList = new ArrayList<AdminAuthGrpDto>();
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getSystemAdminAuthGroupList() adminMgmtRequest={}", adminMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(adminMgmtRequest.getTxId())) {
				txId = adminMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			adminAuthGrpSC = new AdminAuthGrpSC();
			if(!SBNUtils.isNull(adminMgmtRequest.getUseYn())) {
				adminAuthGrpSC.setUseYn(adminMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmSeq())) {
				adminAuthGrpSC.setAdmSeq(adminMgmtRequest.getAdmSeq());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAuthGrpSeq())) {
				adminAuthGrpSC.setAuthGrpSeq(adminMgmtRequest.getAuthGrpSeq());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAdmTp())) {
				adminAuthGrpSC.setAdmTp(adminMgmtRequest.getAdmTp());
			}
			if(!SBNUtils.isNull(adminMgmtRequest.getAuthGrpTp())) {
				adminAuthGrpSC.setAuthGrpTp(adminMgmtRequest.getAuthGrpTp());
			}
			log.debug("getSystemAdminAuthGroupList() adminAuthGrpSC={}", adminAuthGrpSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 특정 시스템 사용자에 대한 지정된 권한 그룹 목록 정보 객체를 추출한다 
			adminAuthGroupList = adminMgmtService.getSystemAdminAuthGroupList(adminAuthGrpSC);
			
			//	3. 요청 수행 결과 목록 정보 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(adminAuthGroupList)) {
				//	3.1. 요청 수행 결과 목록 정보 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 시스템 관리자 지정 권한 그룹 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 정보 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(adminAuthGroupList);
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
