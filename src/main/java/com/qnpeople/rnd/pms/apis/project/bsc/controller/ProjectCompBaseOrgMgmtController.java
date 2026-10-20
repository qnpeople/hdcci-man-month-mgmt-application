package com.qnpeople.rnd.pms.apis.project.bsc.controller;

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

import com.qnpeople.rnd.pms.apis.project.bsc.domain.request.ProjectCompBaseOrgMgmtRequest;
import com.qnpeople.rnd.pms.apis.project.bsc.domain.request.ProjectExecuteCompOrgMapRequest;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapSC;
import com.qnpeople.rnd.pms.apis.project.bsc.service.ProjectCompBaseOrgMgmtService;
import com.qnpeople.rnd.pms.common.auth.QNPAuthData;
import com.qnpeople.rnd.pms.common.domain.executor.controller.QNPWebBaseController;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseErrorData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponsePacketEntityData;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;
import com.qnpeople.rnd.pms.types.AppRequestExecuteWayType;
import com.qnpeople.rnd.pms.utils.QNPResponseEntityUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.controller
 * @Filename		: ProjectCompBaseOrgMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.15.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 수행 업체 조직도 기본 정보 관리 및 프로젝트와 업체 수행 조직도 매핑 및 해지 등의 
 *  관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 연관 테이블 ] 
 *  	PRJ_PRJCT_COMP_ORG_BSC_INFO_TBL, COM_COMP_INFO_TBL, PRJ_PRJCT_EXEC_COMP_ORG_MAP
 *  [ 관리 ]
 *  	프로젝트 수행 업체 기본 조직 등록
 *  	프로젝트 수행 업체 기본 조직 변경
 *  	프로젝트 수행 업체 기본 조직 삭제
 *  	프로젝트 수행 업체 조직 매핑
 *  		프로젝트 수행 업체 조직 매핑 지정
 *  		프로젝트 수행 업체 조직 매핑 변경	=> 보류
 *  		프로젝트 수행 업체 조직 매핑 해제
 *  [ 서비스 ]
 *  	프로젝트 수행 업체 기본 조직 목록 조회
 *  	프로젝트 수행 업체 기본 조직 상세 정보 조회
 *  	프로젝트 수행 업체 조직 매핑
 *  		프로젝트 수행 업체 조직 매핑 정보 목록 조회
 *  		프로젝트 수행 업체 조직 매핑 정보 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/project/base/comp/org")
@Slf4j
public class ProjectCompBaseOrgMgmtController extends QNPWebBaseController {

	/* 프로젝트 수행 업체의 기본 조직 관리를 위한 실질적인 작업을 수행하는 인터페이스 객체 */
	@Autowired
	private ProjectCompBaseOrgMgmtService projectCompBaseOrgMgmtService;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	관리
	//		프로젝트 수행 업체 기본 조직 등록
	//		프로젝트 수행 업체 기본 조직 변경
	//		프로젝트 수행 업체 기본 조직 삭제
	//		프로젝트 수행 업체 조직 매핑
	//			프로젝트 수행 업체 조직 매핑 지정
	//			프로젝트 수행 업체 조직 매핑 변경	=> 보류
	//			프로젝트 수행 업체 조직 매핑 해제
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * [ 프로젝트 수행 업체 기본 조직 등록 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보 신규 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.15
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectCompBaseOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보 신규 등록 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/registerNewProjectCompanyBaseOrganization")
	public ResponseEntity<?> registerNewProjectCompanyBaseOrganization(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectCompBaseOrgMgmtRequest projectCompBaseOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompBaseOrgDto registerCompanyBaseOrganization = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerNewProjectCompanyBaseOrganization() projectCompBaseOrgMgmtRequest={}", projectCompBaseOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getTxId())) {
				txId = projectCompBaseOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectCompBaseOrgMgmtRequest.getClientAuthData();
			
			//	1. 신규 등록 대상 프로젝트 수행 업체 기본 조직 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getCompanyBaseOrganization())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 기본 조직 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			registerCompanyBaseOrganization = projectCompBaseOrgMgmtRequest.getCompanyBaseOrganization();
			
			if((SBNUtils.isNull(registerCompanyBaseOrganization.getCompSeq()) || (registerCompanyBaseOrganization.getCompSeq() < 0L)) && 
				SBNUtils.isNull(registerCompanyBaseOrganization.getCompCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(registerCompanyBaseOrganization.getCompOrgNm())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 기본 조직 명 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 신규 등록 대상 업체 정보를 추출한다
			registerCompanyBaseOrganization = projectCompBaseOrgMgmtRequest.getCompanyBaseOrganization();
			registerCompanyBaseOrganization.setRgstSeq(adminAuthData.getClientSeq());
			registerCompanyBaseOrganization.setRgstNm(adminAuthData.getClientNm());
			log.debug("registerNewProjectCompanyBaseOrganization() registerCompanyBaseOrganization={}", registerCompanyBaseOrganization.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보 등록 작업 수행 결과를 추출한다
			resultFlag = projectCompBaseOrgMgmtService.registerProjectCompanyBaseOrganization(registerCompanyBaseOrganization);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보 등록 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 업체 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 업체 기본 조직 정보 등록 작업을 정상 수행 했습니다.";
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
	 * [ 프로젝트 수행 업체 기본 조직 변경 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectCompBaseOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보 변경 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateProjectCompanyBaseOrganization")
	public ResponseEntity<?> updateProjectCompanyBaseOrganization(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectCompBaseOrgMgmtRequest projectCompBaseOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompBaseOrgDto updateCompanyBaseOrganization = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateProjectCompanyBaseOrganization() projectCompBaseOrgMgmtRequest={}", projectCompBaseOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getTxId())) {
				txId = projectCompBaseOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectCompBaseOrgMgmtRequest.getClientAuthData();
			
			//	1. 변경 대상 프로젝트 수행 업체 기본 조직 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getCompanyBaseOrganization())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 수행 업체 기본 조직 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			updateCompanyBaseOrganization = projectCompBaseOrgMgmtRequest.getCompanyBaseOrganization();
			if((SBNUtils.isNull(updateCompanyBaseOrganization.getCompOrgSeq()) || (updateCompanyBaseOrganization.getCompOrgSeq() < 0L))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 수행 업체 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 신규 등록 대상 업체 정보를 추출한다
			updateCompanyBaseOrganization = projectCompBaseOrgMgmtRequest.getCompanyBaseOrganization();
			updateCompanyBaseOrganization.setUpdtSeq(adminAuthData.getClientSeq());
			updateCompanyBaseOrganization.setUpdtNm(adminAuthData.getClientNm());
			log.debug("updateProjectCompanyBaseOrganization() updateCompanyBaseOrganization={}", updateCompanyBaseOrganization.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보 변경 작업 수행 결과를 추출한다
			resultFlag = projectCompBaseOrgMgmtService.updateProjectCompanyBaseOrganization(updateCompanyBaseOrganization);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보 변경 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 기본 조직 정보 변경 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 업체 기본 조직 정보 변경 작업을 정상 수행 했습니다.";
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
	 * [ 프로젝트 수행 업체 기본 조직 삭제 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보 삭제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectCompBaseOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보 삭제 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/deleteProjectCompanyBaseOrganization")
	public ResponseEntity<?> deleteProjectCompanyBaseOrganization(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectCompBaseOrgMgmtRequest projectCompBaseOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompBaseOrgSC deleteCompanyBaseOrgKey = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteProjectCompanyBaseOrganization() projectCompBaseOrgMgmtRequest={}", projectCompBaseOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getTxId())) {
				txId = projectCompBaseOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectCompBaseOrgMgmtRequest.getClientAuthData();
			
			//	1. 변경 대상 프로젝트 수행 업체 기본 조직 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getCompOrgSeq())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 프로젝트 수행 업체 기본 조직 식별자 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
						
			//	2. 전달된 요청 정보로부터 삭제 대상 프로젝트  등록 대상 업체 정보를 추출한다
			deleteCompanyBaseOrgKey = new ProjectCompBaseOrgSC();
			deleteCompanyBaseOrgKey.setDeltSeq(adminAuthData.getClientSeq());
			deleteCompanyBaseOrgKey.setDeltNm(adminAuthData.getClientNm());
			deleteCompanyBaseOrgKey.setCompOrgSeq(projectCompBaseOrgMgmtRequest.getCompOrgSeq());
			log.debug("deleteProjectCompanyBaseOrganization() deleteCompanyBaseOrgKey={}", deleteCompanyBaseOrgKey.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보 삭제 작업 수행 결과를 추출한다
			resultFlag = projectCompBaseOrgMgmtService.deleteProjectCompanyBaseOrganization(deleteCompanyBaseOrgKey);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보 삭제 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 기본 조직 정보 삭제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 업체 기본 조직 정보 삭제 작업을 정상 수행 했습니다.";
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
	 * [ 프로젝트 수행 업체 조직 매핑:프로젝트 수행 업체 조직 매핑 지정 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보와 수행 프로젝트 지정 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	ProjectExecuteCompOrgMapRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보와 수행 프로젝트 지정 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/assignProjectCompanyOrganizationMap")
	public ResponseEntity<?> assignProjectCompanyOrganizationMap(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteCompOrgMapRequest projectExecuteCompOrgMapRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		AppRequestExecuteWayType requestExecuteWayType = null;
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompExecuteOrgMapDto assignProjectCompExecuteOrgMap = null;
		List<ProjectCompExecuteOrgMapDto> assignProjectCompExecuteOrgMapList = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("assignProjectCompanyOrganizationMap() ProjectExecuteCompOrgMapRequest={}", projectExecuteCompOrgMapRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getTxId())) {
				txId = projectExecuteCompOrgMapRequest.getTxId();
			}
			adminAuthData = projectExecuteCompOrgMapRequest.getClientAuthData();
			
			//	1. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 지정 요청으로 전달된 정보에 대한 유효성 체크 작업을 수행하고, 
			//	예외 발생 시, 오류 메시지 구성 후 오류 응답을 전달 후 작업을 종료한다
			if(SBNUtils.isNull(projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMapDataList()) && 
				SBNUtils.isNull(projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMap())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "지정 대상 프로젝트 수행 업체 기본 조직 맵 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMapDataList()) && 
				!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMap())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "지정 대상 프로젝트 수행 업체 기본 조직 맵 정보가 중복 전달되었습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	1.2. 전달된 요청 정보에 대한 요청 수행 작업을 구분한다
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMapDataList())) {
				requestExecuteWayType = AppRequestExecuteWayType.MULTI;
				assignProjectCompExecuteOrgMapList = projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMapDataList();
			} else {
				requestExecuteWayType = AppRequestExecuteWayType.SINGLE;
				assignProjectCompExecuteOrgMap = projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMap();				
			}
			
			//	2. 전달된 요청 정보로부터 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 지정 작업 수행을 위한 전달 객체를 구축한다
			if(AppRequestExecuteWayType.MULTI.equals(requestExecuteWayType)) {
				//	다중 매핑 지정 수행 
				for(int i =0; i < assignProjectCompExecuteOrgMapList.size(); i++) {
					if((SBNUtils.isNull(assignProjectCompExecuteOrgMapList.get(i).getCompOrgSeq()) || (assignProjectCompExecuteOrgMapList.get(i).getCompOrgSeq() <= 0L))) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "지정 대상 프로젝트 수행 업체 기본 조직 식별자 정보가 전달되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					if((SBNUtils.isNull(assignProjectCompExecuteOrgMapList.get(i).getPrjctSeq()) || (assignProjectCompExecuteOrgMapList.get(i).getPrjctSeq() <= 0L)) &&
						SBNUtils.isNull(assignProjectCompExecuteOrgMapList.get(i).getPrjctCd())) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "지정 대상 프로젝트 키 정보가 전달되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					assignProjectCompExecuteOrgMapList.get(i).setRgstSeq(adminAuthData.getClientSeq());
					assignProjectCompExecuteOrgMapList.get(i).setRgstNm(adminAuthData.getClientNm());
				}
			} else {
				//	단일 매핑 지정 수행
				assignProjectCompExecuteOrgMap.setRgstSeq(adminAuthData.getClientSeq());
				assignProjectCompExecuteOrgMap.setRgstNm(adminAuthData.getClientNm());
				//
				if((SBNUtils.isNull(assignProjectCompExecuteOrgMap.getCompOrgSeq()) || (assignProjectCompExecuteOrgMap.getCompOrgSeq() <= 0L))) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "지정 대상 프로젝트 수행 업체 기본 조직 식별자 정보가 전달되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				if((SBNUtils.isNull(assignProjectCompExecuteOrgMap.getPrjctSeq()) || (assignProjectCompExecuteOrgMap.getPrjctSeq() <= 0L) &&
					SBNUtils.isNull(assignProjectCompExecuteOrgMap.getPrjctCd()))) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "지정 대상 프로젝트 키 정보가 전달되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				assignProjectCompExecuteOrgMapList = new ArrayList<ProjectCompExecuteOrgMapDto>();
				assignProjectCompExecuteOrgMapList.add(assignProjectCompExecuteOrgMap);
			}

			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 지정 작업 수행 결과를 추출한다
			resultFlag = projectCompBaseOrgMgmtService.assignProjectCompanyOrganizationMap(assignProjectCompExecuteOrgMapList);
			
			//	4. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 지정 작업 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보에 대해 프로젝트 매핑 지정 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 업체 수행 조직 프로젝트 매핑 지정 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 기본 조직 정보에 대해 프로젝트 매핑 지정 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 업체 수행 조직 프로젝트 매핑 지정 작업을 정상 수행 했습니다.";
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
	 * [ 프로젝트 수행 업체 조직 매핑:프로젝트 수행 업체 조직 매핑 변경	=> 보류 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보와 수행 프로젝트 매핑 정보 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	ProjectExecuteCompOrgMapRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보와 수행 프로젝트 매핑 정보 변경 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateAssignedProjectCompanyOrganizationMap")
	public ResponseEntity<?> updateAssignedProjectCompanyOrganizationMap(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteCompOrgMapRequest projectExecuteCompOrgMapRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateAssignedProjectCompanyOrganizationMap() projectExecuteCompOrgMapRequest={}", projectExecuteCompOrgMapRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getTxId())) {
				txId = projectExecuteCompOrgMapRequest.getTxId();
			}
			adminAuthData = projectExecuteCompOrgMapRequest.getClientAuthData();
			
			//	1. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 변경 요청으로 전달된 정보에 대한 유효성 체크 작업을 수행하고, 
			//		예외 발생 시, 오류 메시지 구성 후 오류 응답을 전달 후 작업을 종료한다
			
			//	2. 전달된 요청 정보로부터 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 변경 작업 수행을 위한 조건 정보를 구축한다

			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 변경 작업 수행 결과를 추출한다
	
			//	3. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 변경 작업 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보에 대해 프로젝트 매핑 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보 프로젝트 매핑 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 기본 조직 정보에 대해 프로젝트 매핑 변경 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 업체 기본 조직 정보 프로젝트 매핑 변경 작업을 정상 수행 했습니다.";
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
	 * [ 프로젝트 수행 업체 조직 매핑:프로젝트 수행 업체 조직 매핑 해제 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보와 수행 프로젝트 매핑 해제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	ProjectExecuteCompOrgMapRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보와 수행 프로젝트 매핑 해제 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/releaseAssignedProjectCompanyOrganizationMap")
	public ResponseEntity<?> releaseAssignedProjectCompanyOrganizationMap(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteCompOrgMapRequest projectExecuteCompOrgMapRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		AppRequestExecuteWayType requestExecuteWayType = null;
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompExecuteOrgMapSC assignProjectCompExecuteOrgMapSC = null;
		List<ProjectCompExecuteOrgMapSC> assignProjectCompExecuteOrgMapSCList = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("releaseAssignedProjectCompanyOrganizationMap() projectExecuteCompOrgMapRequest={}", projectExecuteCompOrgMapRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getTxId())) {
				txId = projectExecuteCompOrgMapRequest.getTxId();
			}
			adminAuthData = projectExecuteCompOrgMapRequest.getClientAuthData();
			
			//	1. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 해제 요청으로 전달된 정보에 대한 유효성 체크 작업을 수행하고, 
			//		예외 발생 시, 오류 메시지 구성 후 오류 응답을 전달 후 작업을 종료한다
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMapSCList())) {
				requestExecuteWayType = AppRequestExecuteWayType.MULTI;
				assignProjectCompExecuteOrgMapSCList = projectExecuteCompOrgMapRequest.getProjectCompExecuteOrgMapSCList();
			} else {
				requestExecuteWayType = AppRequestExecuteWayType.SINGLE;
				assignProjectCompExecuteOrgMapSC = new ProjectCompExecuteOrgMapSC();
			}
			
			//	2. 전달된 요청 정보로부터 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 해제 작업 수행을 위한 조건 정보를 구축한다
			if(AppRequestExecuteWayType.MULTI.equals(requestExecuteWayType)) {
				//	다중 매핑 해제 조건 구축 수행
				for(int i = 0; i < assignProjectCompExecuteOrgMapSCList.size(); i++) {
					if((SBNUtils.isNull(assignProjectCompExecuteOrgMapSCList.get(i).getCompOrgSeq()) || (assignProjectCompExecuteOrgMapSCList.get(i).getCompOrgSeq() <= 0L))) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "해제 대상 프로젝트 수행 업체 기본 조직 식별자 정보가 전달되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					if((SBNUtils.isNull(assignProjectCompExecuteOrgMapSCList.get(i).getPrjctSeq()) || (assignProjectCompExecuteOrgMapSCList.get(i).getPrjctSeq() <= 0L)) &&
					   SBNUtils.isNull(assignProjectCompExecuteOrgMapSCList.get(i).getPrjctCd())) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "해제 대상 프로젝트 키 정보가 전달되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					assignProjectCompExecuteOrgMapSCList.get(i).setDeltSeq(adminAuthData.getClientSeq());
					assignProjectCompExecuteOrgMapSCList.get(i).setDeltNm(adminAuthData.getClientNm());
				}
			} else {
				//	단일 매핑 해제 조건 구축 수행
				assignProjectCompExecuteOrgMapSC.setDeltSeq(adminAuthData.getClientSeq());
				assignProjectCompExecuteOrgMapSC.setDeltNm(adminAuthData.getClientNm());
				
				if((SBNUtils.isNull(projectExecuteCompOrgMapRequest.getCompOrgSeq()) || (projectExecuteCompOrgMapRequest.getCompOrgSeq() <= 0L))) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "해제 대상 프로젝트 수행 업체 기본 조직 식별자 정보가 전달되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				if((SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctSeq()) || (projectExecuteCompOrgMapRequest.getPrjctSeq() <= 0L)) &&
				   SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctCd())) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "해제 대상 프로젝트 키 정보가 전달되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				
				assignProjectCompExecuteOrgMapSCList = new ArrayList<ProjectCompExecuteOrgMapSC>();
				assignProjectCompExecuteOrgMapSCList.add(assignProjectCompExecuteOrgMapSC);
			}
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 해제 작업 수행 결과를 추출한다
			resultFlag = projectCompBaseOrgMgmtService.releaseAssignedProjectCompanyOrganizationMap(assignProjectCompExecuteOrgMapSCList);
			
			//	4. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 해제 작업 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	4.1. 프로젝트 수행 업체 기본 조직 정보에 대해 프로젝트 매핑 해제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보 프로젝트 매핑 해제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	4.2. 프로젝트 수행 업체 기본 조직 정보에 대해 프로젝트 매핑 해제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 업체 기본 조직 정보 프로젝트 매핑 해제 작업을 정상 수행 했습니다.";
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	서비스
	//	프로젝트 수행 업체 기본 조직 목록 조회
	//  	프로젝트 수행 업체 기본 조직 상세 정보 조회
	//  	프로젝트 수행 업체 조직 매핑
	//			프로젝트 수행 업체 조직 매핑 정보 목록 조회
	//			프로젝트 수행 업체 조직 매핑 정보 상세 조회
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * [ 프로젝트 수행 업체 기본 조직 목록 조회 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectCompBaseOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보 목록 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getProjectCompanyBaseOrganizationList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getProjectCompanyBaseOrganizationList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectCompBaseOrgMgmtRequest projectCompBaseOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompBaseOrgSC companyBaseOrgKey = null;
		List<ProjectCompBaseOrgDto> companyBaseOrgList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getProjectCompanyBaseOrganizationList() projectCompBaseOrgMgmtRequest={}", projectCompBaseOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getTxId())) {
				txId = projectCompBaseOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectCompBaseOrgMgmtRequest.getClientAuthData();
			log.debug("getProjectCompanyBaseOrganizationList() adminAuthData={}", adminAuthData.toStringInfo());
			
			//	1. 전달된 요청 정보로부터 프로젝트 수행 업체 기본 조직 목록 추출 작업을 위한 조건 정보 객체를 구축한다
			companyBaseOrgKey = new ProjectCompBaseOrgSC();
			companyBaseOrgKey.setDeltSeq(adminAuthData.getClientSeq());
			companyBaseOrgKey.setDeltNm(adminAuthData.getClientNm());
			companyBaseOrgKey.setUseYn(projectCompBaseOrgMgmtRequest.getUseYn());
			companyBaseOrgKey.setCompSeq(projectCompBaseOrgMgmtRequest.getCompSeq());
			companyBaseOrgKey.setCompCd(projectCompBaseOrgMgmtRequest.getCompCd());
			companyBaseOrgKey.setCompOrgNm(projectCompBaseOrgMgmtRequest.getCompOrgNm());
			companyBaseOrgKey.setCompNm(projectCompBaseOrgMgmtRequest.getCompNm());
			log.debug("getProjectCompanyBaseOrganizationList() companyBaseOrgKey={}", companyBaseOrgKey.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보 목록 추출 작업 수행 결과를 추출한다
			companyBaseOrgList = projectCompBaseOrgMgmtService.getProjectCompanyBaseOrganizationList(companyBaseOrgKey);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보 목록 추출 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(companyBaseOrgList)) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 기본 조직 정보 목록 추출 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(companyBaseOrgList);
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
	 * [ 프로젝트 수행 업체 기본 조직 상세 조회 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보 상세 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectCompBaseOrgMgmtRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보 상세 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getProjectCompanyBaseOrganizationDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> selectProjectCompanyBaseOrganizationDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectCompBaseOrgMgmtRequest projectCompBaseOrgMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompBaseOrgSC companyBaseOrgKey = null;
		ProjectCompBaseOrgDto companyBaseOrgDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("selectProjectCompanyBaseOrganizationDetail() projectCompBaseOrgMgmtRequest={}", projectCompBaseOrgMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getTxId())) {
				txId = projectCompBaseOrgMgmtRequest.getTxId();
			}
			adminAuthData = projectCompBaseOrgMgmtRequest.getClientAuthData();
			
			//	1.  프로젝트 수행 업체 기본 조직 상세 추출 키 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectCompBaseOrgMgmtRequest.getCompOrgSeq())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 상세 정보 추출 조건 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
						
			//	2. 전달된 요청 정보로부터 프로젝트 수행 업체 기본 조직 상세 추출 작업을 위한 조건 정보 객체를 구축한다
			companyBaseOrgKey = new ProjectCompBaseOrgSC();
			companyBaseOrgKey.setDeltSeq(adminAuthData.getClientSeq());
			companyBaseOrgKey.setDeltNm(adminAuthData.getClientNm());
			companyBaseOrgKey.setCompOrgSeq(projectCompBaseOrgMgmtRequest.getCompOrgSeq());
			log.debug("selectProjectCompanyBaseOrganizationDetail() companyBaseOrgKey={}", companyBaseOrgKey.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보 상세 추출 작업 수행 결과를 추출한다
			companyBaseOrgDetail = projectCompBaseOrgMgmtService.getProjectCompanyBaseOrganizationDetail(companyBaseOrgKey);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보 상세 추출 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(companyBaseOrgDetail)) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보 상세 정보 추출 결과가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 기본 조직 정보 상세 추출 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(companyBaseOrgDetail);
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
	 * [ 프로젝트 수행 업체 조직 매핑:프로젝트 수행 업체 조직 매핑 정보 목록 조회 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보와 수행 프로젝트 매핑 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	ProjectExecuteCompOrgMapRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보와 수행 프로젝트 매핑 목록 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getAssignedProjectCompanyOrganizationMapList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getAssignedProjectCompanyOrganizationMapList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteCompOrgMapRequest projectExecuteCompOrgMapRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC = null;
		List<ProjectCompExecuteOrgMapDto> projectCompExecuteOrgMapList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAssignedProjectCompanyOrganizationMap() projectExecuteCompOrgMapRequest={}", projectExecuteCompOrgMapRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getTxId())) {
				txId = projectExecuteCompOrgMapRequest.getTxId();
			}
			adminAuthData = projectExecuteCompOrgMapRequest.getClientAuthData();
			
			//	1. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 목록 조회 요청으로 전달된 정보에 대한 유효성 체크 작업을 수행하고, 
			//		예외 발생 시, 오류 메시지 구성 후 오류 응답을 전달 후 작업을 종료한다
			//	1.1. 업체별로만 조회 할 수 있도록 정의 함
			if(SBNUtils.isNull(projectExecuteCompOrgMapRequest.getCompOrgSeq()) || (projectExecuteCompOrgMapRequest.getCompOrgSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
						
			//	2. 전달된 요청 정보로부터 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 목록 추출 작업 수행을 위한 조건 정보를 구축한다
			projectCompExecuteOrgMapSC = new ProjectCompExecuteOrgMapSC();
			projectCompExecuteOrgMapSC.setCompOrgSeq(projectExecuteCompOrgMapRequest.getCompOrgSeq());
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getUseYn())) {
				projectCompExecuteOrgMapSC.setUseYn(projectExecuteCompOrgMapRequest.getUseYn());
			}
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctSeq()) && (projectExecuteCompOrgMapRequest.getPrjctSeq() > 0L)) {
				projectCompExecuteOrgMapSC.setPrjctSeq(projectExecuteCompOrgMapRequest.getPrjctSeq());
			}
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctCd())) {
				projectCompExecuteOrgMapSC.setPrjctCd(projectExecuteCompOrgMapRequest.getPrjctCd());
			}
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctNm())) {
				projectCompExecuteOrgMapSC.setPrjctNm(projectExecuteCompOrgMapRequest.getPrjctNm());
			}
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getCompNm())) {
				projectCompExecuteOrgMapSC.setCompNm(projectExecuteCompOrgMapRequest.getCompNm());
			}
			log.debug("getAssignedProjectCompanyOrganizationMap() projectCompExecuteOrgMapSC={}", projectCompExecuteOrgMapSC.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 목록 추출 작업 수행 결과를 추출한다
			projectCompExecuteOrgMapList = projectCompBaseOrgMgmtService.getAssignedProjectCompanyOrganizationMapList(projectCompExecuteOrgMapSC);
				
			//	3. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 목록 조회 작업 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(projectCompExecuteOrgMapList)) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보에 대해 프로젝트 매핑 목록 추출 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보 프로젝트 매핑 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 기본 조직 정보에 대해 프로젝트 매핑 목록 추출 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다				
				responseData = new QNPResponseData(projectCompExecuteOrgMapList);
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
	 * [ 프로젝트 수행 업체 조직 매핑:프로젝트 수행 업체 조직 매핑 정보 상세 조회 ]
	 * 전달된 클라이언트의 프로젝트 수행 업체 조직 기본 정보와 수행 프로젝트 매핑 상세 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	httpRequest									클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse									클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	ProjectExecuteCompOrgMapRequest	클라이언트로부터 요청된 프로젝트 수행 업체 기본 조직 정보와 수행 프로젝트 매핑 상세 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException							클라이언트의 업체 조직도 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getAssignedProjectCompanyOrganizationMapDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getAssignedProjectCompanyOrganizationMapDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectExecuteCompOrgMapRequest projectExecuteCompOrgMapRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData<?> adminAuthData = null; 		//	세션으로부터 추출한 클라이언트 권한 정보 객체
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC = null;
		ProjectCompExecuteOrgMapDto projectCompExecuteOrgMapDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAssignedProjectCompanyOrganizationMapDetail() projectExecuteCompOrgMapRequest={}", projectExecuteCompOrgMapRequest.toStringInfo());
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getTxId())) {
				txId = projectExecuteCompOrgMapRequest.getTxId();
			}
			adminAuthData = projectExecuteCompOrgMapRequest.getClientAuthData();
			
			//	1. 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 상세 조회 요청으로 전달된 정보에 대한 유효성 체크 작업을 수행하고, 
			//		예외 발생 시, 오류 메시지 구성 후 오류 응답을 전달 후 작업을 종료한다
			//	1.1. 프로젝트 수행 업체의 기본 조직 식별자 정보에 대한 유효성 체크 작업을 수행 후 미 전달 시, 오류 메시지 구성 및 응답 데이터 전달 후 작업을 종료한다
			if(SBNUtils.isNull(projectExecuteCompOrgMapRequest.getCompOrgSeq()) || (projectExecuteCompOrgMapRequest.getCompOrgSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	
			if((SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctSeq()) || (projectExecuteCompOrgMapRequest.getPrjctSeq() <= 0L)) && 
			    SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 요청 정보로부터 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 상세 추출 작업 수행을 위한 조건 정보를 구축한다
			projectCompExecuteOrgMapSC = new ProjectCompExecuteOrgMapSC();
			projectCompExecuteOrgMapSC.setCompOrgSeq(projectExecuteCompOrgMapRequest.getCompOrgSeq());
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctSeq()) && (projectExecuteCompOrgMapRequest.getPrjctSeq() > 0L)) {
				projectCompExecuteOrgMapSC.setPrjctSeq(projectExecuteCompOrgMapRequest.getPrjctSeq());
			}
			if(!SBNUtils.isNull(projectExecuteCompOrgMapRequest.getPrjctCd())) {
				projectCompExecuteOrgMapSC.setPrjctCd(projectExecuteCompOrgMapRequest.getPrjctCd());
			}
			log.debug("getAssignedProjectCompanyOrganizationMapDetail() projectCompExecuteOrgMapSC={}", projectCompExecuteOrgMapSC.toStringInfo());
			
			//	3. 전달된 프로젝트 수행 업체 기본 조직 정보에 프로젝트 매핑 상세 추출 작업 수행 결과를 추출한다
			projectCompExecuteOrgMapDetail = projectCompBaseOrgMgmtService.getAssignedProjectCompanyOrganizationMapDetail(projectCompExecuteOrgMapSC);
			
			//	3. 프로젝트 수행 업체 기본 조직 정보에 대한 프로젝트 매핑 상세 조회 작업 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(projectCompExecuteOrgMapDetail)) {
				//	3.1. 프로젝트 수행 업체 기본 조직 정보에 대한 프로젝트 매핑 상세 추출 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 정보 프로젝트 매핑 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 수행 업체 기본 조직 정보에 대한 프로젝트 매핑 상세 추출 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다				
				responseData = new QNPResponseData(projectCompExecuteOrgMapDetail);
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
