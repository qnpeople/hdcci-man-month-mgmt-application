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

import com.qnpeople.rnd.pms.apis.project.bsc.domain.request.ProjectExecuteOrgMgmtRequest;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgSC;
import com.qnpeople.rnd.pms.apis.project.bsc.service.ProjectExecuteOrgMgmtService;
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
import kr.co.sbn.platformhub.framework.core.types.SBNUseYnType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.controller
 * @Filename		: ProjectExecuteOrgMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.16.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 수행 업체 조직도 정보 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 연관 테이블 ] 
 *  	PRJ_PRJCT_COMP_ORG_BSC_INFO_TBL, PRJ_PRJCT_COMP_ORG_INFO_TBL
 *  [ 관리 ]
 *  	프로젝트 수행 업체 조직도 등록
 *  	프로젝트 수행 업체 조직도 변경
 *  	프로젝트 수행 업체 조직도 삭제
 *  [ 서비스 ]
 *  	프로젝트 수행 업체 전체 조직도 정보 트리 목록 조회
 *  	프로젝트 수행 업체  사용 가능 조직도 정보 트리 목록 조회
 *  	프로젝트 수행 업체 조직도 트리 구조 목록 조회
 *  	프로젝트 수행 업체 조직도 상세 정보 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/project/org")
@Slf4j
public class ProjectExecuteOrgMgmtController extends QNPWebBaseController {

	/* 프로젝트 수행 업체 조직도 정보 관리를 위한 실질적인 작업을 수행하는 인터페이스 개겣 */
	@Autowired
	private ProjectExecuteOrgMgmtService projectExecuteOrgMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	//		프로젝트 수행 업체 조직도 등록
	//		프로젝트 수행 업체 조직도 변경
	//		프로젝트 수행 업체 조직도 삭제
	//		프로젝트 수행 업체 전체 조직도 정보 트리 목록 조회
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 프로젝트 수행 업체 조직도 등록 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직도 정보 신규 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.15
	 * @param 	httpRequest								클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse								클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectExecuteOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 조직도 정보 신규 등록 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException						클라이언트의 프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/registerNewProjectExecutionOrganization")
	public ResponseEntity<?> registerNewProjectCompanyBaseOrganization(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteOrgMgmtRequest projectExecuteOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectExecuteOrgDto registerProjectExecuteOrganization = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerNewProjectExecutionOrganization() projectExecuteOrgMgmtRequest={}", projectExecuteOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getTxId())) {
				txId = projectExecuteOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectExecuteOrgMgmtRequest.getClientAuthData();
			
			//	1. 신규 등록 대상 프로젝트 수행 업체 조직도 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectExecuteOrgMgmtRequest.getProjectExecuteOrganization())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 조직도 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			registerProjectExecuteOrganization = projectExecuteOrgMgmtRequest.getProjectExecuteOrganization();
			//	
			if((SBNUtils.isNull(registerProjectExecuteOrganization.getCompOrgSeq()) || (registerProjectExecuteOrganization.getCompOrgSeq() <= 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 수행 조직도 기본 조직 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(registerProjectExecuteOrganization.getPrjctOrgCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 조직도 코드 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if((SBNUtils.isNull(registerProjectExecuteOrganization.getPrjctOrgLvl()) || (registerProjectExecuteOrganization.getPrjctOrgLvl() <= 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 수행 조직도 업체 기본 조직도 레벨 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(registerProjectExecuteOrganization.getPrjctOrgNm())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 조직도 명 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 신규 등록 대상 프로젝트 수행 업체 조직도 정보를 구성한다
			registerProjectExecuteOrganization.setRgstSeq(adminAuthData.getClientSeq());
			registerProjectExecuteOrganization.setRgstNm(adminAuthData.getClientNm());
			log.debug("registerNewProjectCompanyBaseOrganization() registerProjectExecuteOrganization={}", registerProjectExecuteOrganization.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 신규 조직도 정보 등록 작업 수행 결과를 추출한다
			resultFlag = projectExecuteOrgMgmtService.registerNewProjectExecutionOrganization(registerProjectExecuteOrganization);
			
			//	3. 프로젝트 수행 업체 조직도 정보 등록 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 프로젝트 수행 업체 조직도 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 조직도 정보 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 프로젝트 수행 업체 조직도 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 업체 조직도 정보 등록 작업을 정상 수행 했습니다.";
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
	 * [ 프로젝트 수행 업체 조직도 변경 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직도 정보 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.15
	 * @param 	httpRequest								클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse								클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectExecuteOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 조직도 정보 변경 작업 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException						클라이언트의 프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateProjectExecutionOrganization")
	public ResponseEntity<?> updateProjectExecutionOrganization(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteOrgMgmtRequest projectExecuteOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectExecuteOrgDto updateProjectExecuteOrganization = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateProjectExecutionOrganization() projectExecuteOrgMgmtRequest={}", projectExecuteOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getTxId())) {
				txId = projectExecuteOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectExecuteOrgMgmtRequest.getClientAuthData();
			
			//	1. 변경 대상 프로젝트 수행 업체 조직도 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectExecuteOrgMgmtRequest.getProjectExecuteOrganization())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 수행 업체 조직도 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			updateProjectExecuteOrganization = projectExecuteOrgMgmtRequest.getProjectExecuteOrganization();
			//	
			if((SBNUtils.isNull(updateProjectExecuteOrganization.getPrjctOrgSeq()) || (updateProjectExecuteOrganization.getPrjctOrgSeq() <= 0L)) && 
				SBNUtils.isNull(updateProjectExecuteOrganization.getPrjctOrgCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 수행 조직도 식별 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if((SBNUtils.isNull(updateProjectExecuteOrganization.getCompOrgSeq()) || (updateProjectExecuteOrganization.getCompOrgSeq() <= 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 수행 조직도 기본 조직 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 변경 대상 프로젝트 수행 업체 조직도 정보를 구축한다
			updateProjectExecuteOrganization.setUpdtSeq(adminAuthData.getClientSeq());
			updateProjectExecuteOrganization.setUpdtNm(adminAuthData.getClientNm());
			log.debug("updateProjectExecutionOrganization() updateProjectExecuteOrganization={}", updateProjectExecuteOrganization.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 조직도 정보 변경 작업 수행 결과를 추출한다
			resultFlag = projectExecuteOrgMgmtService.updateProjectExecutionOrganization(updateProjectExecuteOrganization);
			
			//	3. 프로젝트 수행 업체 조직도 변경 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 프로젝트 수행 업체 조직도 정보 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 조직도 정보 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 변경 대상 프로젝트 수행 업체 조직도 변경 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 업체 조직도 정보 변경 작업을 정상 수행 했습니다.";
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
	 * [ 프로젝트 수행 업체 조직도 삭제 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직도 정보 삭제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.15
	 * @param 	httpRequest								클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse								클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectExecuteOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 조직도 정보 삭제 작업 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException						클라이언트의 프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/deleteProjectExecutionOrganization")
	public ResponseEntity<?> deleteProjectExecutionOrganization(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteOrgMgmtRequest projectExecuteOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectExecuteOrgSC deleteProjectExecuteOrganizationSC = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteProjectExecutionOrganization() projectExecuteOrgMgmtRequest={}", projectExecuteOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getTxId())) {
				txId = projectExecuteOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectExecuteOrgMgmtRequest.getClientAuthData();
			
			//	1.  프로젝트 수행 업체 조직도 삭제 수행 조건 정보에 대한 유효성 작업 수행 후, 유효하지 않은 경우 예외 처리를 위해 오류 응답 전달 후 작업을 종료한다
			if((SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgSeq()) || (projectExecuteOrgMgmtRequest.getPrjctOrgSeq() <= 0L)) && 
				SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 프로젝트 수행 조직도 식별 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if((SBNUtils.isNull(projectExecuteOrgMgmtRequest.getCompOrgSeq()) || (projectExecuteOrgMgmtRequest.getCompOrgSeq() <= 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 프로젝트 수행 조직도 기본 조직 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 프로젝트 수행 업체 조직도 삭제 작업 조건 정보 객체를 구축한다
			deleteProjectExecuteOrganizationSC = new ProjectExecuteOrgSC();
			deleteProjectExecuteOrganizationSC.setDeltSeq(adminAuthData.getClientSeq());
			deleteProjectExecuteOrganizationSC.setDeltNm(adminAuthData.getClientNm());
			if((!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgSeq()) && (projectExecuteOrgMgmtRequest.getPrjctOrgSeq() > 0L))) {
				deleteProjectExecuteOrganizationSC.setPrjctOrgSeq(projectExecuteOrgMgmtRequest.getPrjctOrgSeq());
			}
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgCd())) {
				deleteProjectExecuteOrganizationSC.setPrjctOrgCd(projectExecuteOrgMgmtRequest.getPrjctOrgCd());
			}
			if((!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getCompOrgSeq()) && (projectExecuteOrgMgmtRequest.getCompOrgSeq() > 0L))) {
				deleteProjectExecuteOrganizationSC.setCompOrgSeq(projectExecuteOrgMgmtRequest.getCompOrgSeq());
			}			
			log.debug("deleteProjectExecutionOrganization() deleteProjectExecuteOrganizationSC={}", deleteProjectExecuteOrganizationSC.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 조직도 정보 삭제 작업 수행 결과를 추출한다
			resultFlag = projectExecuteOrgMgmtService.deleteProjectExecutionOrganization(deleteProjectExecuteOrganizationSC);
			
			//	3. 프로젝트 수행 업체 조직도 삭제 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 프로젝트 수행 업체 조직도 정보 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 조직도 정보 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 삭제 대상 프로젝트 수행 업체 조직도 삭제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 업체 조직도 정보 삭제 작업을 정상 수행 했습니다.";
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
	 * [ 프로젝트 수행 업체 전체 조직도 정보 트리 목록 조회 ]
	 * 전달된 클라이언트의 전체 프로젝트 수행 업체 조직도 정보 트리 목록 정보 추출 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	httpRequest								클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse								클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectExecuteOrgMgmtRequest	클라이언트로부터 요청된 전체 프로젝트 수행 업체 조직도 정보 트리 목록 정보 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException						클라이언트의 프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/getTotalProjectExecutionOrganizationTreeList", method= {RequestMethod.GET, RequestMethod.PUT})
	public ResponseEntity<?> getTotalProjectExecutionOrganizationTreeList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteOrgMgmtRequest projectExecuteOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectExecuteOrgSC projectExecuteOrgSC = null;
		List<ProjectExecuteOrgDto> projectExecuteOrgTreeList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getTotalProjectExecutionOrganizationTreeList() projectExecuteOrgMgmtRequest={}", projectExecuteOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getTxId())) {
				txId = projectExecuteOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectExecuteOrgMgmtRequest.getClientAuthData();
			log.debug("getTotalProjectExecutionOrganizationTreeList() adminAuthData={}", adminAuthData.toStringInfo());
			
			//	1. 신규 등록 대상 프로젝트 수행 업체 기본 조직 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectExecuteOrgMgmtRequest.getCompOrgSeq()) || (projectExecuteOrgMgmtRequest.getCompOrgSeq() <= 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "전체 프로젝트 수행 업체 조직도 트리 목록 정보 추출 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 신규 등록 대상 업체 정보를 추출한다
			projectExecuteOrgSC = new ProjectExecuteOrgSC();
			projectExecuteOrgSC.setUseYn(projectExecuteOrgMgmtRequest.getUseYn());
			projectExecuteOrgSC.setPrjctOrgSeq(projectExecuteOrgMgmtRequest.getPrjctOrgSeq());
			projectExecuteOrgSC.setCompOrgSeq(projectExecuteOrgMgmtRequest.getCompOrgSeq());
			log.debug("getTotalProjectExecutionOrganizationTreeList() projectExecuteOrgSC={}", projectExecuteOrgSC.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보 등록 작업 수행 결과를 추출한다
			projectExecuteOrgTreeList = projectExecuteOrgMgmtService.getProjectExecutionOrganizationTreeList(projectExecuteOrgSC);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보 등록 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(projectExecuteOrgTreeList)) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "전체 프로젝트 수행 업체 조직도 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 업체 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(projectExecuteOrgTreeList);
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
	//		프로젝트 수행 업체 조직도 코드 중복 여부 체크
	//  	프로젝트 수행 업체 사용 가능 조직도 정보 트리 목록 조회
	//  	프로젝트 수행 업체  조직도 정보 트리 목록 조회
	//  	프로젝트 수행 업체 조직도 상세 정보 조회
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 프로젝트 수행 업체 조직도 코드 중복 여부 체크 ]
	 * 전달된 클라이언트의 사용 가능 프로젝트 수행 업체 조직도 코드 정보 중복 체크 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	httpRequest								클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse								클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectExecuteOrgMgmtRequest	클라이언트로부터 요청된 사용 가능 프로젝트 수행 업체 조직도 코드 정보 중복 체크 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException						클라이언트의 프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/checkProjectExecutionOrganizationCodeDuplication", method= {RequestMethod.GET, RequestMethod.PUT})
	public ResponseEntity<?> checkProjectExecutionOrganizationCodeDuplication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteOrgMgmtRequest projectExecuteOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectExecuteOrgSC projectExecuteOrgSC = null;
		Boolean projectCodeDuplicationFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkProjectExecutionOrganizationCodeDuplication() projectExecuteOrgMgmtRequest={}", projectExecuteOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getTxId())) {
				txId = projectExecuteOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectExecuteOrgMgmtRequest.getClientAuthData();
			log.debug("checkProjectExecutionOrganizationCodeDuplication() adminAuthData={}", adminAuthData.toStringInfo());
			
			//	1. 신규 등록 대상 프로젝트 수행 업체 기본 조직 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectExecuteOrgMgmtRequest.getCompOrgSeq()) || (projectExecuteOrgMgmtRequest.getCompOrgSeq() <= 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "사용가능 프로젝트 수행 업체 조직도 코드 정보 중복 체크 수행 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "사용가능 프로젝트 수행 업체 조직도 코드 정보 중복 체크 수행  키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 신규 등록 대상 업체 정보를 추출한다
			projectExecuteOrgSC = new ProjectExecuteOrgSC();
			projectExecuteOrgSC.setCompOrgSeq(projectExecuteOrgMgmtRequest.getCompOrgSeq());
			projectExecuteOrgSC.setPrjctOrgCd(projectExecuteOrgMgmtRequest.getPrjctOrgCd());
			
			log.debug("checkProjectExecutionOrganizationCodeDuplication() projectExecuteOrgSC={}", projectExecuteOrgSC.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보 등록 작업 수행 결과를 추출한다
			projectCodeDuplicationFlag = projectExecuteOrgMgmtService.checkProjectExecutionOrganizationCodeDuplication(projectExecuteOrgSC);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보 등록 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(projectCodeDuplicationFlag) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "사용 중인 프로젝트 수행 업체 조직도 코드 정보가 존재합니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 업체 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "사용 가능 프로젝트 수행 업체 조직도 코드 입니다.";
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
	 * [ 프로젝트 수행 업체  사용 가능 조직도 정보 트리 목록 조회 ]
	 * 전달된 클라이언트의 사용 가능 프로젝트 수행 업체 조직도 정보 트리 목록 정보 추출 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	httpRequest								클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse								클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectExecuteOrgMgmtRequest	클라이언트로부터 요청된 사용 가능 프로젝트 수행 업체 조직도 정보 트리 목록 정보 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException						클라이언트의 프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getAvailableProjectExecutionOrganizationTreeList", method= {RequestMethod.GET, RequestMethod.PUT})
	public ResponseEntity<?> getAvailableProjectExecutionOrganizationTreeList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteOrgMgmtRequest projectExecuteOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectExecuteOrgSC projectExecuteOrgSC = null;
		List<ProjectExecuteOrgDto> projectExecuteOrgTreeList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAvailableProjectExecutionOrganizationTreeList() projectExecuteOrgMgmtRequest={}", projectExecuteOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getTxId())) {
				txId = projectExecuteOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectExecuteOrgMgmtRequest.getClientAuthData();
			log.debug("getAvailableProjectExecutionOrganizationTreeList() adminAuthData={}", adminAuthData.toStringInfo());
			
			//	1. 신규 등록 대상 프로젝트 수행 업체 기본 조직 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectExecuteOrgMgmtRequest.getCompOrgSeq()) || (projectExecuteOrgMgmtRequest.getCompOrgSeq() <= 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "사용가능 프로젝트 수행 업체 조직도 트리 목록 정보 추출 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 신규 등록 대상 업체 정보를 추출한다
			projectExecuteOrgSC = new ProjectExecuteOrgSC();
			projectExecuteOrgSC.setUseYn(SBNUseYnType.Y.getTypeCode());
			projectExecuteOrgSC.setPrjctOrgSeq(projectExecuteOrgMgmtRequest.getPrjctOrgSeq());
			projectExecuteOrgSC.setCompOrgSeq(projectExecuteOrgMgmtRequest.getCompOrgSeq());
			log.debug("getAvailableProjectExecutionOrganizationTreeList() projectExecuteOrgSC={}", projectExecuteOrgSC.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보 등록 작업 수행 결과를 추출한다
			projectExecuteOrgTreeList = projectExecuteOrgMgmtService.getProjectExecutionOrganizationTreeList(projectExecuteOrgSC);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보 등록 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(projectExecuteOrgTreeList)) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "사용가능 프로젝트 수행 업체 조직도 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 업체 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(projectExecuteOrgTreeList);
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
	 * [ 프로젝트 수행 업체 조직도 상세 정보 조회 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직도에 대한 상세 정보 추출 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	httpRequest								클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse								클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectExecuteOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 조직도의 상세 정보 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException						클라이언트의 프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getProjectExecutionOrganizationDetail", method= {RequestMethod.GET, RequestMethod.PUT})
	public ResponseEntity<?> getProjectExecutionOrganizationDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteOrgMgmtRequest projectExecuteOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectExecuteOrgSC projectExecuteOrgSC = null;
		ProjectExecuteOrgDto projectExecuteOrganizationDetail= null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getProjectExecutionOrganizationDetail() projectExecuteOrgMgmtRequest={}", projectExecuteOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getTxId())) {
				txId = projectExecuteOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectExecuteOrgMgmtRequest.getClientAuthData();
			log.debug("getProjectExecutionOrganizationDetail() adminAuthData={}", adminAuthData.toStringInfo());
			
			//	1. 프로젝트 수행 업체 조직도 정보 상세 조회 요청 수행 조건 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgSeq()) || (projectExecuteOrgMgmtRequest.getPrjctOrgSeq() <= 0L)) &&
				SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 조직도 상세 정보 추출 조건 프로젝트 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if((SBNUtils.isNull(projectExecuteOrgMgmtRequest.getCompOrgSeq()) || (projectExecuteOrgMgmtRequest.getCompOrgSeq() <= 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 조직도 상세 정보 추출 조건 업체 조직 식별자 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 프로젝트 수행 업체 조직도 상세 정보 추출 조건을 추출하여 조건 객체를 구축한다
			projectExecuteOrgSC = new ProjectExecuteOrgSC();
			projectExecuteOrgSC.setUseYn(SBNUseYnType.Y.getTypeCode());
			projectExecuteOrgSC.setCompOrgSeq(projectExecuteOrgMgmtRequest.getCompOrgSeq());
			if((!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgSeq()) && (projectExecuteOrgMgmtRequest.getPrjctOrgSeq() > 0L))) {
				projectExecuteOrgSC.setPrjctOrgSeq(projectExecuteOrgMgmtRequest.getPrjctOrgSeq());
			}
			if(!SBNUtils.isNull(projectExecuteOrgMgmtRequest.getPrjctOrgCd())) {
				projectExecuteOrgSC.setPrjctOrgCd(projectExecuteOrgMgmtRequest.getPrjctOrgCd());
			}
			log.debug("getProjectExecutionOrganizationDetail() projectExecuteOrgSC={}", projectExecuteOrgSC.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체의 조직도 상세 정보 추출 작업을 수행한다
			projectExecuteOrganizationDetail = projectExecuteOrgMgmtService.getProjectExecutionOrganizationDetail(projectExecuteOrgSC);
			
			//	3. 프로젝트 수행 업체 조직도 상세 정보 추출 작업 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(projectExecuteOrganizationDetail)) {
				//	3.1. 프로젝트 수행 업체 조직도 상세 정보 추출 결과 가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 조직도 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 조직도 상세 정보 추출 결과 존재 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(projectExecuteOrganizationDetail);
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
