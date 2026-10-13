package com.qnpeople.rnd.pms.apis.project.bsc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.company.domain.request.CompanyMgmtRequest;
import com.qnpeople.rnd.pms.apis.company.model.CompanyDto;
import com.qnpeople.rnd.pms.apis.project.bsc.domain.request.ProjectRoleMgmtRequest;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleSC;
import com.qnpeople.rnd.pms.apis.project.bsc.service.ProjectRoleMgmtService;
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
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.controller
 * @Filename		: ProjectRoleMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.08.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 업체별 프로젝트 수행 역할에 대한 기본 정보 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	프로젝트_업체별 프로젝트 수행 역할 정보 등록
 *  	프로젝트_업체별 프로젝트 수행 역할 정보 변경
 *  	프로젝트_업체별 프로젝트 수행 역할 정보 삭제
 *  [ 서비스 ]
 *  	프로젝트_업체별 프로젝트 수행 역할 정보 코드 중복 체크 
 *  	프로젝트_업체별 프로젝트 수행 역할 정보 목록 조회
 *  	프로젝트_업체별 프로젝트 수행 역할 정보 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/project/role")
@Slf4j
public class ProjectRoleMgmtController extends QNPWebBaseController {

	/** 업체별 프로젝트 수행 역할 관리를 위한 실질적인 작업 수행 인터페이스 객체 */
	@Autowired
	private ProjectRoleMgmtService projectRoleMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 프로젝트_업체별 프로젝트 수행 역할 정보 등록 ]
	 * 전달된 클라이언트의 업체별 프로젝트 수행 역할 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectRoleMgmtRequest	클라이언트로부터 요청된 업체 프로젝트 수행 역할 정보 등록 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/registerNewCompanyProjectRole")
	public ResponseEntity<?> registerNewCompanyProjectRole(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectRoleMgmtRequest projectRoleMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectRoleDto registerCompanyProjectRole = null;
		Boolean registerResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerNewCompanyProjectRole() projectRoleMgmtRequest={}", projectRoleMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getTxId())) {
				txId = projectRoleMgmtRequest.getTxId();
			}
			
			//	1. 전달된 업체 프로젝트 수행 역할 신규 등록 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			if(SBNUtils.isNull(projectRoleMgmtRequest.getCompProjectRole())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 업체 프로젝트 수행 역할 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			registerCompanyProjectRole = projectRoleMgmtRequest.getCompProjectRole();
			registerCompanyProjectRole.setRgstSeq(adminAuthData.getClientSeq());
			registerCompanyProjectRole.setRgstNm(adminAuthData.getClientNm());
			log.debug("registerNewCompanyProjectRole() registerCompanyProjectRole={}", registerCompanyProjectRole.toStringInfo());
			
			//	2. 전달된 신규 등록 대상 업체 프로젝트 수행 역할 정보 등록 작업을 수행 후, 수행 결과를 전달 받는다
			registerResultFlag = projectRoleMgmtService.registerNewProjectRole(registerCompanyProjectRole);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!registerResultFlag) {
				//	3.1. 업체 프로젝트 수행 역할 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 프로젝트 수행 역할 정보 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 프로젝트 수행 역할 정보 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "업체 프로젝트 수행 역할 정보를 정상적으로 등록 했습니다.";
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
	 * [ 프로젝트_업체별 프로젝트 수행 역할 정보 변경 ]
	 * 전달된 클라이언트의 업체별 프로젝트 수행 역할 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectRoleMgmtRequest	클라이언트로부터 요청된 업체 프로젝트 수행 역할 코드 정보 변경 수행 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateCompanyProjectRole")
	public ResponseEntity<?> updateCompanyProjectRole(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectRoleMgmtRequest projectRoleMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectRoleDto updateCompanyProjectRole = null;
		Boolean updateResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateCompanyProjectRole() projectRoleMgmtRequest={}", projectRoleMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getTxId())) {
				txId = projectRoleMgmtRequest.getTxId();
			}
			
			//	1. 전달된 업체 프로젝트 수행 역할 변경 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			if(SBNUtils.isNull(projectRoleMgmtRequest.getCompProjectRole())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 업체 프로젝트 수행 역할 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			updateCompanyProjectRole = projectRoleMgmtRequest.getCompProjectRole();
			updateCompanyProjectRole.setUpdtSeq(adminAuthData.getClientSeq());
			updateCompanyProjectRole.setUpdtNm(adminAuthData.getClientNm());
			if(SBNUtils.isNull(updateCompanyProjectRole.getCompSeq()) || (updateCompanyProjectRole.getCompSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 업체 프로젝트 수행 역할 업체 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(updateCompanyProjectRole.getPrjctRoleCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 업체 프로젝트 수행 역할 코드 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			log.debug("updateCompanyProjectRole() updateCompanyProjectRole={}", updateCompanyProjectRole.toStringInfo());
			
			//	2. 전달된 변경 대상 업체 프로젝트 수행 역할 정보 변경 작업을 수행 후, 수행 결과를 전달 받는다
			updateResultFlag = projectRoleMgmtService.updateProjectRole(updateCompanyProjectRole);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!updateResultFlag) {
				//	3.1. 업체 프로젝트 수행 역할 정보 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 프로젝트 수행 역할 정보 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 프로젝트 수행 역할 정보 변경 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "업체 프로젝트 수행 역할 정보를 정상적으로 변경 했습니다.";
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
	 * [ 프로젝트_업체별 프로젝트 수행 역할 정보 삭제 ]
	 * 전달된 클라이언트의 업체별 프로젝트 수행 역할 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectRoleMgmtRequest	클라이언트로부터 요청된 업체 프로젝트 수행 역할 코드 정보 변경 수행 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/deleteCompanyProjectRole")
	public ResponseEntity<?> deleteCompanyProjectRole(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectRoleMgmtRequest projectRoleMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectRoleSC projectRoleSC = null;
		Boolean deleteCompanyProjectRoleFlag = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteCompanyProjectRole() projectRoleMgmtRequest={}", projectRoleMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getTxId())) {
				txId = projectRoleMgmtRequest.getTxId();
			}
			
			//	1. 전달된 업체 프로젝트 수행 역할 삭제 정보에 대한 유효성 체크 작업을 수행하고, 유효하지 않은 경우, 오류 응답 메시지 전달 후 작업을 종료한다
			if((SBNUtils.isNull(projectRoleMgmtRequest.getCompSeq()) || (projectRoleMgmtRequest.getCompSeq() <= 0L)) && SBNUtils.isNull(projectRoleMgmtRequest.getCompCd()) ) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 업체 프로젝트 수행 역할 업체 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(projectRoleMgmtRequest.getPrjctRoleCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 업체 프로젝트 수행 역할 코드 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	2. 전달된 업체 프로젝트 수행 역할 삭제 요청 정보로부터 삭제 조건 전달 객체를 구축한다
			projectRoleSC = new ProjectRoleSC();
			projectRoleSC.setDeltSeq(adminAuthData.getClientSeq());
			projectRoleSC.setDeltNm(adminAuthData.getClientNm());
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getCompSeq()) && (projectRoleMgmtRequest.getCompSeq() > 0L)) {
				projectRoleSC.setCompSeq(projectRoleMgmtRequest.getCompSeq());
			}
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getCompCd())) {
				projectRoleSC.setCompCd(projectRoleMgmtRequest.getCompCd());
			}
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getPrjctRoleCd())) {
				projectRoleSC.setPrjctRoleCd(projectRoleMgmtRequest.getPrjctRoleCd());
			}
			
			//	3. 전달된 삭제 대상 업체 프로젝트 수행 역할 정보 삭제 작업을 수행 후, 수행 결과를 전달 받는다
			deleteCompanyProjectRoleFlag = projectRoleMgmtService.deleteProjectRole(projectRoleSC);
			
			//	4. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!deleteCompanyProjectRoleFlag) {
				//	3.1. 업체 프로젝트 수행 역할 정보 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 프로젝트 수행 역할 정보 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 프로젝트 수행 역할 정보 삭제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "업체 프로젝트 수행 역할 정보를 정상적으로 삭제 했습니다.";
				responseData = new QNPResponseData(resultMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), responseData);
			}			
			
			//	5. 최종 구성된 수행 결과 응답 객체를 ResponseEntity 객체로 구축 및 전달 후 작업을 종료한다
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
	 * [ 프로젝트_업체별 프로젝트 수행 역할 정보 코드 중복 체크  ]
	 * 전달된 클라이언트의 업체별 프로젝트 수행 역할 코드 중복 여부 체크 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectRoleMgmtRequest	클라이언트로부터 요청된 업체 프로젝트 수행 역할 코드 정보 중복 체크 수행 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/checkProjectRoleCodeDuplication", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkCompanyProjectRoleCodeDuplicationt(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectRoleMgmtRequest projectRoleMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectRoleSC projectRoleSC = null;
		Boolean duplicationResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkCompanyProjectRoleCodeDuplicationt() projectRoleMgmtRequest={}", projectRoleMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getTxId())) {
				txId = projectRoleMgmtRequest.getTxId();
			}
			
			//	1. 신규 등록 대상 업체 프로젝트 수행 역할 코드 중복 여부 체크 작업을 위한 조건 정보 전달 객체를 구축한다
			projectRoleSC = new ProjectRoleSC();
			if((SBNUtils.isNull(projectRoleMgmtRequest.getCompSeq()) || (projectRoleMgmtRequest.getCompSeq() <= 0L)) &&
				SBNUtils.isNull(projectRoleMgmtRequest.getCompCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "중복 여부 체크 대상 업체 식별자가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}			
			if(SBNUtils.isNull(projectRoleMgmtRequest.getPrjctRoleCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "중복 여부 체크 대상 프로젝트 역할 코드가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	
			projectRoleSC.setCompSeq(projectRoleMgmtRequest.getCompSeq());
			projectRoleSC.setCompCd(projectRoleMgmtRequest.getCompCd());
			projectRoleSC.setPrjctRoleCd(projectRoleMgmtRequest.getPrjctRoleCd());
			log.debug("checkCompanyProjectRoleCodeDuplicationt() projectRoleSC={}", projectRoleSC.toStringInfo());
			
			//	2. 전달된 신규 등록 대상 업체별 수행 역할 코드 중복 여부 체크 작업 수행 결과를 추출한다
			duplicationResultFlag = projectRoleMgmtService.checkProjectRoleCodeDuplication(projectRoleSC);
			
			//	3. 신규 등록 대상 업체 코드 중복 여부 체크 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(duplicationResultFlag) {
				//	3.1. 업체별 프로젝트 수행 역할 코드가 중복 결과 상태 인 경우, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEOWRK_DATABASE_DUPLICATION_KEY_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "이미 사용 중인 중복된 업체별 프로젝트 수행 역할 코드 정보 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 등록 대상 업체별 프로젝트 수행 역할 코드 중복 여부 체크 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "사용 가능 업체별 프로젝트 수행 역할 코드 정보 입니다.";
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
	 * [ 프로젝트_업체별 프로젝트 수행 역할 정보 목록 조회 ]
	 * 전달된 클라이언트의 업체별 프로젝트 수행 역할 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectRoleMgmtRequest	클라이언트로부터 요청된 업체 프로젝트 수행 역할 목록 조회 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getCompanyProjectRoleList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCompanyProjectRoleList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectRoleMgmtRequest projectRoleMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectRoleSC projectRoleSC = null;
		List<ProjectRoleDto> projectRoleList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCompanyProjectRoleList() projectRoleMgmtRequest={}", projectRoleMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getTxId())) {
				txId = projectRoleMgmtRequest.getTxId();
			}
			
			//	1. 업체 프로젝트 수행 역할 목록 정보 추출 요청 수행을 위한 조건 정보전달 객체를 구축한다
			projectRoleSC = new ProjectRoleSC();
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getUseYn())) {
				projectRoleSC.setUseYn(projectRoleMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getCompSeq()) && (projectRoleMgmtRequest.getCompSeq() > 0L)) {
				projectRoleSC.setCompSeq(projectRoleMgmtRequest.getCompSeq());
			}
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getCompCd())) {
				projectRoleSC.setCompCd(projectRoleMgmtRequest.getCompCd());
			}
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getPrjctRoleNm())) {
				projectRoleSC.setPrjctRoleNm(projectRoleMgmtRequest.getPrjctRoleNm());
			}
			log.debug("getCompanyProjectRoleList() projectRoleSC={}", projectRoleSC.toStringInfo());
			
			//	2. 구축된 업체 프로젝트 수행 역할 목록 추출 조건 정보를 이용하여 업체 프로젝트 수행 역할 목록 객체를 추출한다
			projectRoleList = projectRoleMgmtService.getProjectRoleList(projectRoleSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(projectRoleList)) {
				//	3.1. 업체 프로젝트 수행 역할 목록 조회 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 프로젝트 수행 역할 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 프로젝트 수행 역할 목록 조회 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(projectRoleList);
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
	 * [ 프로젝트_업체별 프로젝트 수행 역할 정보 상세 조회 ]
	 * 전달된 클라이언트의 업체별 프로젝트 수행 역할 상세 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectRoleMgmtRequest	클라이언트로부터 요청된 업체 프로젝트 수행 역할 상세 조회 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getCompanyProjectRoleDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCompanyProjectRoleDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectRoleMgmtRequest projectRoleMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectRoleSC projectRoleSC = null;
		ProjectRoleDto projectRoleDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCompanyProjectRoleDetail() projectRoleMgmtRequest={}", projectRoleMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectRoleMgmtRequest.getTxId())) {
				txId = projectRoleMgmtRequest.getTxId();
			}
			
			//	1. 업체 프로젝트 수행 역할 상세 정보 추출 수행을 위한 요청 정보로부터 수행 조건 정보에 대한 유효성 체크 및 조건 정보 구축 작업을 수행한다
			projectRoleSC = new ProjectRoleSC();
			if((SBNUtils.isNull(projectRoleMgmtRequest.getCompSeq()) || (projectRoleMgmtRequest.getCompSeq() <= 0L)) &&
				SBNUtils.isNull(projectRoleMgmtRequest.getCompCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 역할 업체 식별자 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(projectRoleMgmtRequest.getPrjctRoleCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 프로젝트 수행 역할 식별자 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			projectRoleSC.setCompSeq(projectRoleMgmtRequest.getCompSeq());
			projectRoleSC.setCompCd(projectRoleMgmtRequest.getCompCd());
			projectRoleSC.setPrjctRoleCd(projectRoleMgmtRequest.getPrjctRoleCd());
			log.debug("getCompanyProjectRoleDetail() projectRoleSC={}", projectRoleSC.toStringInfo());
			
			//	2. 구축된 업체 프로젝트 수행 역할 상세 정보 조회 조건 객체를 이용하여 업체 프로젝트 수행 역할 상세 정보를 추출한다
			projectRoleDetail = projectRoleMgmtService.getProjectRoleDetail(projectRoleSC);
			
			//	3. 최종 수행 결과에 대한 클라이언트로 전달할 수행 결과 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(projectRoleDetail)) {
				//	3.1. 업체 프로젝트 수행 역할 상세 조회 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 프로젝트 수행 역할 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 프로젝트 수행 역할 상세 조회 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(projectRoleDetail);
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
