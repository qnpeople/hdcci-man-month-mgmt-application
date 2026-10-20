package com.qnpeople.rnd.pms.apis.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.auth.domain.request.AccessAuthRequest;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeDto;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeSC;
import com.qnpeople.rnd.pms.apis.company.service.CompanyEmployeeMgmtService;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpSC;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminSC;
import com.qnpeople.rnd.pms.apis.system.admin.service.AdminMgmtService;
import com.qnpeople.rnd.pms.common.auth.QNPAuth;
import com.qnpeople.rnd.pms.common.auth.QNPAuthData;
import com.qnpeople.rnd.pms.common.domain.executor.controller.QNPWebBaseController;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseErrorData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponsePacketEntityData;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.common.web.session.QNPWebClientUserSessionData;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;
import com.qnpeople.rnd.pms.types.AppAuthGroupType;
import com.qnpeople.rnd.pms.types.AppAuthGubunType;
import com.qnpeople.rnd.pms.utils.QNPResponseEntityUtils;
import com.qnpeople.rnd.pms.utils.QNPWebUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.sbn.platformhub.framework.core.types.SBNUseYnType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.auth.controller
 * @Filename		: WorkerAccessAuthController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.14.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 인증 영역의 시스템 접근 근무자의 인증 처리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/auth/worker")
@Slf4j
public class WorkerAccessAuthController extends QNPWebBaseController {
	
	/** 업체 근무자 인증 처리를 위한 업체 근무자 정보에 대한 실질적인 작업을 수행하는 서비스 인터페이스 객체 */ 
	@Autowired
	private CompanyEmployeeMgmtService companyEmployeeMgmtService;
	
	/**
	 * [ 로그인 ]
	 * 시스템 관리자의 로그인 요청에 대한 작업 수행 후 요청 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	accessAuthRequest		클라이언트의 관리자 로그인 작업 수행 요청 정보 전달 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@PostMapping(value="/login")
	@SuppressWarnings("rawtypes")
	public ResponseEntity<?> login(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AccessAuthRequest accessAuthRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		AppAuthGubunType authGubunType = null;
		EmployeeSC employeeSC = null;
		EmployeeDto employeeDetail = null;
		QNPAuth<?> employeeAuth = null;
		QNPWebClientUserSessionData webClientUserSessionData = null;
		Boolean sessionRegisterFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("login() accessAuthRequest={}", accessAuthRequest.toStringInfo());
			if(!SBNUtils.isNull(accessAuthRequest.getTxId())) {
				txId = accessAuthRequest.getTxId();
			}
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	1. 전달된 관리자 로그인 요청 정보에 대한 유효성 체크 작업을 수행한다
			//	1.1. 업체 근무자의 인증 구분 유형 정보 미 전달 시, 오류 메시지 구성 후 오류 응답 전달 후 작업을 종료한다
			//	1.2. 업체 근무자의 로그인 ID 미 전달 시, 오류 메시지 구성 후 오류 응답 전달 후 작업을 종료한다
			//	1.3. 업체 근무자의 로그인 비밀번호 미 전달 시, 오류 메시지 구성 후 오류 응답 전달 후 작업을 종료한다
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 1.1. 업체 근무자의 인증 구분 유형 정보 미 전달 시, 오류 메시지 구성 후 오류 응답 전달 후 작업을 종료한다
			if(SBNUtils.isNull(accessAuthRequest.getAuthGubunType())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 인증 구분 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			authGubunType = AppAuthGubunType.getAppAuthGubunType(accessAuthRequest.getAuthGubunType());			
			//	1.2. 업체 근무자의 로그인 ID 미 전달 시, 오류 메시지 구성 후 오류 응답 전달 후 작업을 종료한다
			if(SBNUtils.isNull(accessAuthRequest.getEmpleLognId())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 로그인 ID 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	1.3. 업체 근무자의 로그인 비밀번호 미 전달 시, 오류 메시지 구성 후 오류 응답 전달 후 작업을 종료한다
			if(SBNUtils.isNull(accessAuthRequest.getEmpleLognPwd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 로그인 비밀번호 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	1.4. 업체 식별자 또는 업체 코드 정보 미 전달 시, 오류 메시지 구성 후 오류 응답 전달 후 작업을 종료한다
			if((SBNUtils.isNull(accessAuthRequest.getCompSeq()) || (accessAuthRequest.getCompSeq() <= 0)) &&
				SBNUtils.isNull(accessAuthRequest.getCompCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			log.debug("login() authGubunType={}", authGubunType);
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2. 업체 근무자의 인증 처리를 위한 작업을 수행한다 
			//	2.1. 전달된 업체 근무자의 로그인 ID 정보를 이용하여 관리자 상세 정보를 추출하고, 해당 로그인 ID에 대한 관리자 미 존재 시, 
			//		  정보 없음 오류 메시지 구성 후, 예외 처리 후 작업을 종료한다
			//	2.2. 업체 근무자의 최초 로그인 비밀번호 변경 여부가 '미변경' 인 경우, 비밀번호 변경을 위한 오류 처리를 수행한다
			//	2.3. 업체 근무자의 비밀번호가 동일하지 않은 경우, 비밀번호 확인 메시지 구성 후, 예외 처리 후 작업을 종료한다
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2.1. 전달된 업체 근무자의 로그인 ID 정보를 이용하여 관리자 상세 정보를 추출하고, 해당 로그인 ID에 대한 관리자 미 존재 시, 정보 없음 오류 메시지 구성 후, 예외 처리 후 작업을 종료한다
			employeeSC = new EmployeeSC();
			employeeSC.setCompSeq(accessAuthRequest.getCompSeq());
			employeeSC.setCompCd(accessAuthRequest.getCompCd());
			employeeSC.setEmpleLognId(accessAuthRequest.getEmpleLognId());
			employeeDetail = companyEmployeeMgmtService.getCompanyEmployeeDetail(employeeSC);
			if(SBNUtils.isNull(employeeDetail)) {
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 로그인 ID에 대한 근무자가 존재하지 않습니다. 로그인 ID를 다시 확인해 주십시요.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			log.debug("login() employeeDetail={}", employeeDetail.toStringInfo());
			//	2.2. 업체 근무자의 최초 로그인 비밀번호 변경 여부가 '미변경' 인 경우, 비밀번호 변경을 위한 오류 처리를 수행한다
			if(SBNUseYnType.N.getTypeCode().equals(employeeDetail.getInitPwdChngYn()) && SBNUtils.isNull(employeeDetail.getInitPwdChngDt())) {
				reason = QNPReasonCode.NONE_INIT_PWD_CHANGE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자의 초기 로그인 비밀번호를 변경해 주십시요.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	2.3. 업체 근무자의 비밀번호가 동일하지 않은 경우, 비밀번호 확인 메시지 구성 후, 예외 처리 후 작업을 종료한다
			if(!employeeDetail.getEmpleLognPwd().equals(accessAuthRequest.getEmpleLognPwd())) {
				reason = QNPReasonCode.NONE_INIT_PWD_CHANGE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자의 로그인 비밀번호가 일치하지 않습니다. 비밀번호를 확인해 주세요.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			log.debug("login() 정상적인 업체 근무자 로그인 수행 작업 진행..");
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	3. 업체 근무자의 그룹 권한 정보를 추출하여, 관리자 권한 그룹 유형 정보를 구축한다
			//	3.1. 업체 근무자의 정보를 이용하여 관리자에 지정되어 있는 그룹 권한 정보를 추출하고, 지정된 권한 그룹이 미 존재 시, 오류 메시지 구성 후, 예외 처리 후 작업을 종료한다
			//	3.2. 업체 근무자의 지정된 권한 그룹이 존재 시, 권한 그룹 유형 객체를 구성한다
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	4. 업체 근무자가 이용 가능한 메뉴 목록 객체를 추출한다 ( 추후 작업 예정 )
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	5. 최종 인증 수행 정보 결과 데이터 객체를 구축한다
			//	5.1. 관리자 인증 정보 관리 데이터 객체를 구축한다
			//	5.2. 구축된 관리자 인증 정보 데이터 객체를 이용하여 관리자 서버 세션 데이터 객체를 구축한다
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	5.1. 관리자 인증 정보 관리 데이터 객체를 구축한다
			employeeAuth = new QNPAuthData();
			employeeAuth.setClientSeq(employeeDetail.getEmpleSeq());
			employeeAuth.setClientLoginId(employeeDetail.getEmpleLognId());
			employeeAuth.setClientNm(employeeDetail.getEmpleNm());			
			employeeAuth.setAppAuthGubunType(authGubunType);
			// employeeAuth.setAuthGroupTypeList(authGroupTypeList);
			//	5.2. 구축된 관리자 인증 정보 데이터 객체를 이용하여 관리자 서버 세션 데이터 객체를 구축한다
			webClientUserSessionData = new QNPWebClientUserSessionData("Employee Session", employeeAuth);
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	6. 구축한 관리자 인증 수행 객체를 세션에 등록한다
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sessionRegisterFlag = QNPWebUtils.registerWebClientSessionData(httpRequest, webClientUserSessionData);
			if(!sessionRegisterFlag) {
				//	3.1. 관리자의 로그인 처리 작업 및 관리자 세션 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 로그인 수행을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 관리자의 로그인 처리 작업 및 관리자 세션 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "업체 근무자 로그인 작업을 정상 수행 했습니다";
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
	 * [ 로그아웃 ]
	 * 시스템 관리자의 로그아웃 요청에 대한 작업 수행 후 요청 수행 결과 응답 패킷을 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.14
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	accessAuthRequest		클라이언트의 관리자 로그인 작업 수행 요청 정보 전달 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@PostMapping(value="/logout")
	public ResponseEntity<?> logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AccessAuthRequest accessAuthRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		Boolean removeSessionFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("logout() accessAuthRequest={}", accessAuthRequest.toStringInfo());
			if(!SBNUtils.isNull(accessAuthRequest.getTxId())) {
				txId = accessAuthRequest.getTxId();
			}
			
			//	
			removeSessionFlag = QNPWebUtils.removeWebClientSession(httpRequest);
			if(!removeSessionFlag) {
				//	3.1. 관리자의 로그아웃 처리 작업 및 관리자 세션 제거 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "관리자 로그아웃 수행을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 관리자의 로그아웃 처리 작업 및 관리자 세션 제거 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "관리자 로그아웃 작업을 정상 수행 했습니다";
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
}
