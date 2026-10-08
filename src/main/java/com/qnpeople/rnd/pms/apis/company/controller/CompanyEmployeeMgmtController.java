package com.qnpeople.rnd.pms.apis.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.company.domain.request.CompanyEmployeeMgmtRequest;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeDto;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeSC;
import com.qnpeople.rnd.pms.apis.company.service.CompanyEmployeeMgmtService;
import com.qnpeople.rnd.pms.common.auth.QNPAuthData;
import com.qnpeople.rnd.pms.common.domain.executor.controller.QNPWebBaseController;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseErrorData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponsePacketEntityData;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.constant.QNPSecurityConstant;
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
 * @Package		: com.qnpeople.rnd.pms.apis.company.controller
 * @Filename		: CompanyEmployeeMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 업체 영역의 업체 근무자 정보 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	업체_업체 근무자 등록
 *  	업체_업체 근무자 변경
 *  	업체_업체 근무자 삭제	=> 리팩토링 예정
 *  [ 서비스 ]
 *  	업체_업체 근무자 목록 조회
 *  	업체_업체 근무자 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/corp/employee")
@Slf4j
public class CompanyEmployeeMgmtController extends QNPWebBaseController {

	/* 실질적인 업체 근무자 관리 작업을 수행하는 인터페이스 객체 */
	@Autowired
	private CompanyEmployeeMgmtService companyEmployeeMgmtService;
		
	/////////////////////////////////////////////////////////////////
	//	관리
	//		업체_업체 근무자 등록
	//  	업체_업체 근무자 변경
	//  	업체_업체 근무자 삭제	=> 리팩토링 예정
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 업체_업체 근무자 등록 ]
	 * 전달된 클라이언트의 신규 업체 근로자 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyEmployeeMgmtRequest		클라이언트로부터 전달된 신규 등록 대상 업체 근로자의 등록 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 근무자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/registerCompanyEmployee")
	public ResponseEntity<?> registerCompanyEmployee(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyEmployeeMgmtRequest companyEmployeeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String companyEmployeeInitLoginPwd = QNPSecurityConstant.INIT_SYSTEM_USER_LOGIN_PWD;
		String txId = SBNUtils.createSystemRandomId(true);
		EmployeeDto registerEmployee = null;
		Boolean registerResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerCompanyEmployee() companyEmployeeMgmtRequest={}", companyEmployeeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getTxId())) {
				txId = companyEmployeeMgmtRequest.getTxId();
			}
			
			//	1. 업체 근무자 정보 등록 요청 수행을 위한 정보를 구축한다
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getEmployee())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 업체 근무자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			registerEmployee = companyEmployeeMgmtRequest.getEmployee();
			registerEmployee.setRgstSeq(adminAuthData.getClientSeq());
			registerEmployee.setEmpleLognPwd(companyEmployeeInitLoginPwd);			
			log.debug("registerCompanyEmployee() registerEmployee={}", registerEmployee.toStringInfo());
						
			//	2. 추출한 신규 등록 대상 업체 근무자 정보를 이용하여 업체 신규 근무자 등록 작업을 수행한다
			registerResultFlag = companyEmployeeMgmtService.registerNewCompanyEmployee(registerEmployee);
			
			//	3. 신규 업체 근무자 등록 작업 수행 결과를 이용하여 작업 요청 클라이언트로의 전달 응답 데이터 객체를 구축한다
			if(!registerResultFlag) {
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				String resultMessage = "업체 근무자를 정상 등록 했습니다.";
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
	 * [ 업체_업체 근무자 변경 ]
	 * 전달된 클라이언트의 업체 근로자 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyEmployeeMgmtRequest		클라이언트로부터 전달된 변경 대상 업체 근로자의 변경 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 근무자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateCompanyEmployee")
	public ResponseEntity<?> updateCompanyEmployee(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyEmployeeMgmtRequest companyEmployeeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		EmployeeDto updateEmployee = null;
		Boolean updateResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateCompanyEmployee() companyEmployeeMgmtRequest={}", companyEmployeeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getTxId())) {
				txId = companyEmployeeMgmtRequest.getTxId();
			}
			
			//	1. 업체 근무자 정보 변경 요청 수행을 위한 정보를 구축한다
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getEmployee())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 업체 근무자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			updateEmployee = companyEmployeeMgmtRequest.getEmployee();
			updateEmployee.setUpdtSeq(adminAuthData.getClientSeq());	
			log.debug("updateCompanyEmployee() updateEmployee={}", updateEmployee.toStringInfo());
			
			//	2. 추출한 변경 등록 대상 업체 근무자 정보를 이용하여 업체 변경 대상자 근무자 변경 작업을 수행한다
			updateResultFlag = companyEmployeeMgmtService.updateCompanyEmployee(updateEmployee);
			
			//	3. 업체 근무자 변경 작업 수행 결과를 이용하여 작업 요청 클라이언트로의 전달 응답 데이터 객체를 구축한다
			if(!updateResultFlag) {
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				String resultMessage = "업체 근무자를 정상 변경 했습니다.";
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
	 * [ 업체_업체 근무자 삭제	=> 리팩토링 예정 ]
	 * 전달된 클라이언트의 업체 근로자 삭제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyEmployeeMgmtRequest		클라이언트로부터 전달된 삭제 대상 업체 근로자의 삭제 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 근무자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/deleteCompanyEmployee")
	public ResponseEntity<?> deleteCompanyEmployee(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyEmployeeMgmtRequest companyEmployeeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		EmployeeSC deleteEmployeeSC = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteCompanyEmployee() companyEmployeeMgmtRequest={}", companyEmployeeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getTxId())) {
				txId = companyEmployeeMgmtRequest.getTxId();
			}
			
			//	1. 전달된 업체 근무자 삭제 요청에 대한 작업 수행을 위한 유효성 체크 및 수행 조건 전달 객체 구축 작업을 수행한다
			deleteEmployeeSC = new EmployeeSC();
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getCompSeq()) || (companyEmployeeMgmtRequest.getCompSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 업체 근무자에 대한 업체 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleSeq()) || (companyEmployeeMgmtRequest.getEmpleSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 업체 근무자에 대한 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			deleteEmployeeSC.setDeltYn(SBNUseYnType.Y.getTypeCode());
			deleteEmployeeSC.setCompSeq(companyEmployeeMgmtRequest.getCompSeq());
			deleteEmployeeSC.setEmpleSeq(companyEmployeeMgmtRequest.getEmpleSeq());
			deleteEmployeeSC.setDeltSeq(adminAuthData.getClientSeq());
			log.debug("deleteCompanyEmployee() deleteEmployeeSC={}", deleteEmployeeSC.toStringInfo());
			
			//	2. 구축된 업체 근무자 삭제 요청에 대한 개별 근무자 삭제 처리 작업을 수행한다
			resultFlag = companyEmployeeMgmtService.deleteCompanyEmployee(deleteEmployeeSC);
			
			//	3. 수행 결과에 대해 클라이언트로의 응답 데이터 객체를 구축한다
			if(!resultFlag) {
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				String resultMessage = "업체 근무자 삭제 작업을 정상 수행 했습니다.";
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
	//		업체_근무자 로그인 ID 중복 체크
	//		업체_근무자 비밀번호 변경
	//		업체_전체 근무자 목록 조회
	//		업체_업체 근무자 목록 조회
	//  	업체_업체 근무자 상세 조회
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 업체_근무자 로그인 ID 중복 체크 ]
	 * 전달된 클라이언트의 근무자 로그인 ID 중복 체크 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyEmployeeMgmtRequest		클라이언트로부터 전달된 전체 근무자 로그인 ID 중복 체크 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 근무자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/checkEmployeeLoginIdDuplication", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkEmployeeLoginIdDuplication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyEmployeeMgmtRequest companyEmployeeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		EmployeeSC companyEmployeeSC = null;
		Boolean duplicationFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkEmployeeLoginIdDuplication() companyEmployeeMgmtRequest={}", companyEmployeeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getTxId())) {
				txId = companyEmployeeMgmtRequest.getTxId();
			}
			
			//	1. 근무자 로그인 ID 중복 체크 수행 요청을 위한 조건 정보 객체 구축 작업을 수행한다
			companyEmployeeSC = new EmployeeSC();
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getCompSeq())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "근무자 업체 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			companyEmployeeSC.setCompSeq(companyEmployeeMgmtRequest.getCompSeq());
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleLognId())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "근무자 로그인 ID 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			companyEmployeeSC.setEmpleLognId(companyEmployeeMgmtRequest.getEmpleLognId());
			log.debug("checkEmployeeLoginIdDuplication() companyEmployeeSC={}", companyEmployeeSC.toStringInfo());
			
			//	2. 구축한 근무자 로그인 ID 중복 체크 수행 조건 객체를 이용하여 근무자 로그인 ID 중복 체크 작업을 수행한다
			duplicationFlag = companyEmployeeMgmtService.checkEmployeeLoginIdDuplication(companyEmployeeSC);
			
			//	3. 요청 수행 결과 정보에 대한 응답 처리 작업을 수행한다
			if(duplicationFlag) {
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "이미 사용중인 근무자 로그인 ID 정보가 존재 합니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				String resultMessage = "사용 가능 근무자 로그인 ID 정보 입니다.";
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
	 * [ 업체_근무자 비밀번호 변경 ]
	 * 전달된 클라이언트의 업체 근로자 비밀번호 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.08
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyEmployeeMgmtRequest		클라이언트로부터 전달된 업체 근로자의 비밀번호 변경 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 근무자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/srvc/changeEmployeeLoginPwd")
	public ResponseEntity<?> changeEmployeeLoginPwd(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyEmployeeMgmtRequest companyEmployeeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		EmployeeSC changeEmployeePwdSC = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("changeEmployeeLoginPwd() companyEmployeeMgmtRequest={}", companyEmployeeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getTxId())) {
				txId = companyEmployeeMgmtRequest.getTxId();
			}
			
			//	1. 근무자의 비밀번호 변경 작업 수행을 위해 클라이언트로부터 전달된 요청 정보에 대한 유효성 체크 작업을 수행한다
			changeEmployeePwdSC = new EmployeeSC();
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getCompSeq()) || (companyEmployeeMgmtRequest.getCompSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "근무자 업체 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}			
			if((SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleSeq()) || (companyEmployeeMgmtRequest.getEmpleSeq() <= 0L)) &&
				SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleLognId())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "근무자 요청 수행 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getOldEmpleLognPwd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "근무자 이전 비밀번호 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getNewEmpleLognPwd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "근무자 변경할 비밀번호 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			changeEmployeePwdSC.setUpdtSeq(adminAuthData.getClientSeq());
			changeEmployeePwdSC.setCompSeq(companyEmployeeMgmtRequest.getCompSeq());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleSeq())) {
				changeEmployeePwdSC.setEmpleSeq(companyEmployeeMgmtRequest.getEmpleSeq());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleLognId())) {
				changeEmployeePwdSC.setEmpleLognId(companyEmployeeMgmtRequest.getEmpleLognId());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getOldEmpleLognPwd())) {
				changeEmployeePwdSC.setOldEmpleLognPwd(companyEmployeeMgmtRequest.getOldEmpleLognPwd());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getNewEmpleLognPwd())) {
				changeEmployeePwdSC.setNewEmpleLognPwd(companyEmployeeMgmtRequest.getNewEmpleLognPwd());
			}
			log.debug("changeEmployeeLoginPwd() changeEmployeePwdSC={}", changeEmployeePwdSC.toStringInfo());
			
			//	2. 구축한 근무자 비밀번호 변경 수행 조건 정보를 이용하여 해당 업체 근무자의 비밀번호 변경 작업을 수행한다
			resultFlag = companyEmployeeMgmtService.changeCompanyEmployeeLoginPwd(changeEmployeePwdSC);
			
			//	3. 수행 결과를 이용하여 요청 클라이언트로 전달할 응답 데이터 객체를 구축한다
			if(!resultFlag) {
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "근무자 비밀번호 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				String resultMessage = "근무자 비밀번호 변경 작업을 성공적으로 수행 했습니다.";
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
	 * [ 업체_전체 근무자 목록 조회 ]
	 * 전달된 클라이언트의 전체 근무자 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyEmployeeMgmtRequest		클라이언트로부터 전달된 전체 근무자의 목록 조회 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 근무자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getTotalEmployeeList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getTotalEmployeeList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyEmployeeMgmtRequest companyEmployeeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		EmployeeSC companyEmployeeSC = null;
		List<EmployeeDto> totalEmployeeList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getTotalEmployeeList() companyEmployeeMgmtRequest={}", companyEmployeeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getTxId())) {
				txId = companyEmployeeMgmtRequest.getTxId();
			}
			
			//	1. 전달된 수행 요청 정보로부터 전체 근무자 정보 목록 추출 작업을 위한 조건 정보 전달 객체를 구축한다
			companyEmployeeSC = new EmployeeSC();
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getUseYn())) {
				companyEmployeeSC.setUseYn(companyEmployeeMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getInitPwdChngYn())) {
				companyEmployeeSC.setInitPwdChngYn(companyEmployeeMgmtRequest.getInitPwdChngYn());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleTp())) {
				companyEmployeeSC.setEmpleTp(companyEmployeeMgmtRequest.getEmpleTp());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleStatTp())) {
				companyEmployeeSC.setEmpleStatTp(companyEmployeeMgmtRequest.getEmpleStatTp());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleDgrTp())) {
				companyEmployeeSC.setEmpleDgrTp(companyEmployeeMgmtRequest.getEmpleDgrTp());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleNm())) {
				companyEmployeeSC.setEmpleNm(companyEmployeeMgmtRequest.getEmpleNm());
			}
			log.debug("getTotalEmployeeList() companyEmployeeSC={}", companyEmployeeSC.toStringInfo());
			
			//	2. 구축된 근무자 목록 추출 조건에 대한 전체 근무자 목록 객체를 추출한다
			totalEmployeeList = companyEmployeeMgmtService.getTotalEmployeeList(companyEmployeeSC);
			
			//	3. 추출 결과에 대한 전달 응답 객체를 구축하여 전달한다
			if(SBNUtils.isNull(totalEmployeeList)) {
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "근무자 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				responseData = new QNPResponseData(totalEmployeeList);
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
	 * [ 업체_업체 근무자 목록 조회 ]
	 * 전달된 클라이언트의 업체 근무자 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyEmployeeMgmtRequest		클라이언트로부터 전달된 업체 근무자의 목록 조회 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 근무자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getCompanyEmployeeList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCompanyEmployeeList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyEmployeeMgmtRequest companyEmployeeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		EmployeeSC companyEmployeeSC = null;
		List<EmployeeDto> companyEmployeeList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCompanyEmployeeList() companyEmployeeMgmtRequest={}", companyEmployeeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getTxId())) {
				txId = companyEmployeeMgmtRequest.getTxId();
			}
			
			//	1. 전달된 수행 요청 정보로부터 업체별 근무자 정보 목록 추출 작업을 위한 조건 정보 전달 객체를 구축한다
			companyEmployeeSC = new EmployeeSC();
			if((SBNUtils.isNull(companyEmployeeMgmtRequest.getCompSeq()) || (companyEmployeeMgmtRequest.getCompSeq() <= 0L)) &&
				SBNUtils.isNull(companyEmployeeMgmtRequest.getCompCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getCompSeq()) && companyEmployeeMgmtRequest.getCompSeq() > 0L) {
				companyEmployeeSC.setCompSeq(companyEmployeeMgmtRequest.getCompSeq());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getCompCd())) {
				companyEmployeeSC.setCompCd(companyEmployeeMgmtRequest.getCompCd());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getUseYn())) {
				companyEmployeeSC.setUseYn(companyEmployeeMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getInitPwdChngYn())) {
				companyEmployeeSC.setInitPwdChngYn(companyEmployeeMgmtRequest.getInitPwdChngYn());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleTp())) {
				companyEmployeeSC.setEmpleTp(companyEmployeeMgmtRequest.getEmpleTp());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleStatTp())) {
				companyEmployeeSC.setEmpleStatTp(companyEmployeeMgmtRequest.getEmpleStatTp());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleDgrTp())) {
				companyEmployeeSC.setEmpleDgrTp(companyEmployeeMgmtRequest.getEmpleDgrTp());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleNm())) {
				companyEmployeeSC.setEmpleNm(companyEmployeeMgmtRequest.getEmpleNm());
			}
			log.debug("getCompanyEmployeeList() companyEmployeeSC={}", companyEmployeeSC.toStringInfo());
			
			//	2. 구축된 근무자 목록 추출 조건에 대한 전체 근무자 목록 객체를 추출한다
			companyEmployeeList = companyEmployeeMgmtService.getCompanyEmployeeList(companyEmployeeSC);
			
			//	3. 추출 결과에 대한 전달 응답 객체를 구축하여 전달한다
			if(SBNUtils.isNull(companyEmployeeList)) {
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				responseData = new QNPResponseData(companyEmployeeList);
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
	 * [ 업체_업체 근무자 상세 조회 ]
	 * 전달된 클라이언트의 업체 근무자 상세 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyEmployeeMgmtRequest		클라이언트로부터 전달된 업체 근무자의 상세 조회 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 근무자 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getCompanyEmployeeDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCompanyEmployeeDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyEmployeeMgmtRequest companyEmployeeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		EmployeeSC companyEmployeeSC = null;
		EmployeeDto companyEmployeeDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCompanyEmployeeDetail() companyEmployeeMgmtRequest={}", companyEmployeeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getTxId())) {
				txId = companyEmployeeMgmtRequest.getTxId();
			}
			
			//	1. 전달된 수행 요청 정보로부터 업체별 근무자 상세 정보 추출 작업을 위한 조건 정보 전달 객체를 구축한다
			companyEmployeeSC = new EmployeeSC();
			if(SBNUtils.isNull(companyEmployeeMgmtRequest.getEmpleSeq()) || (companyEmployeeMgmtRequest.getEmpleSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			companyEmployeeSC.setEmpleSeq(companyEmployeeMgmtRequest.getEmpleSeq());
			//	
			if((SBNUtils.isNull(companyEmployeeMgmtRequest.getCompSeq()) || (companyEmployeeMgmtRequest.getCompSeq() <= 0L)) &&
				SBNUtils.isNull(companyEmployeeMgmtRequest.getCompCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getCompSeq()) && (companyEmployeeMgmtRequest.getCompSeq() > 0L)) {
				companyEmployeeSC.setCompSeq(companyEmployeeMgmtRequest.getCompSeq());
			}
			if(!SBNUtils.isNull(companyEmployeeMgmtRequest.getCompCd())) {
				companyEmployeeSC.setCompCd(companyEmployeeMgmtRequest.getCompCd());
			}
			log.debug("getCompanyEmployeeDetail() companyEmployeeMgmtRequest={}", companyEmployeeSC.toStringInfo());
			
			//	2. 구성된 업체별 근무자 상세 조회 요청에 대한 작업을 수행하여 근무자 상세 정보 객체를 추출한다
			companyEmployeeDetail = companyEmployeeMgmtService.getCompanyEmployeeDetail(companyEmployeeSC);
			
			//	3. 근무자 상세 정보 객체 추출 작업 결과에 따라, 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(companyEmployeeDetail)) {
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 근무자 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				responseData = new QNPResponseData(companyEmployeeDetail);
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
