package com.qnpeople.rnd.pms.apis.project.crtr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.project.crtr.domain.request.ProjectWorkTimeCrtrRequest;
import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaDto;
import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaSC;
import com.qnpeople.rnd.pms.apis.project.crtr.service.ProjectWorkTimeCriteriaService;
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
 * @Package		: com.qnpeople.rnd.pms.apis.project.crtr.controller
 * @Filename		: ProjectWorkTimeCrtrController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.08.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 근무 시간 기준 정보 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	프로젝트_근무 시간 기준 정보 등록
 *  	프로젝트_근무 시간 기준 정보 수정
 *  	프로젝트_근무 시간 기준 정보 삭제
 *  [ 서비스 ]
 *  	프로젝트_근무 시간 기준 코드 중복 체크
 *  	프로젝트_근무 시간 기준 정보 목록 조회
 *  	프로젝트_근무 시간 기준 정보 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/project/crtr/worktime")
@Slf4j
public class ProjectWorkTimeCrtrController extends QNPWebBaseController {

	/* 프로젝트 근무 시간 기준 정보를 실질적으로 관리하는 서비스 인터페이스 객체 */
	@Autowired
	private ProjectWorkTimeCriteriaService projectWorkTimeCriteriaService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	//		프로젝트_근무 시간 기준 정보 등록
	//		프로젝트_근무 시간 기준 정보 수정
	//		프로젝트_근무 시간 기준 정보 삭제
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 프로젝트_근무 시간 기준 정보 등록 ]
	 * 전달된 클라이언트의 기본 프로젝트 근무 시간 기준 정보 신규 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectWorkTimeCrtrRequest		클라이언트로부터 요청된 프로젝트 근무 시간 기준 정보 신규 등록 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/registerNewProjectWorkTimeCriteria")
	public ResponseEntity<?> registerNewProjectWorkTimeCriteria(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectWorkTimeCrtrRequest projectWorkTimeCrtrRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		WorkTimeCriteriaDto registerWorkTimeCriteriaDto = null;
		Boolean registerResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerNewProjectWorkTimeCriteria() projectWorkTimeCrtrRequest={}", projectWorkTimeCrtrRequest.toStringInfo());
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getTxId())) {
				txId = projectWorkTimeCrtrRequest.getTxId();
			}
			//	1. 전달된 기본 프로젝트 근무 시간 기준 정보 신규 등록 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			if(SBNUtils.isNull(projectWorkTimeCrtrRequest.getWorkTimeCriteria())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 기본 프로젝트 근무 시간 기준 정보 신규 등록 정보를 요청 정보로부터 추출하여 유효성 체크 작업을 수행한다
			registerWorkTimeCriteriaDto = projectWorkTimeCrtrRequest.getWorkTimeCriteria();			
			if(SBNUtils.isNull(registerWorkTimeCriteriaDto.getWrkTmCrtrCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 코드 정보가 미 정의 오류 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(registerWorkTimeCriteriaDto.getWrkTmMeasCrtrTp())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 정보 유형이 미 정의 오류 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(registerWorkTimeCriteriaDto.getWrkTmMeasCrtrNm())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 정보 명이 미 정의 오류 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			registerWorkTimeCriteriaDto.setRgstSeq(adminAuthData.getClientSeq());
			registerWorkTimeCriteriaDto.setRgstNm(adminAuthData.getClientNm());
			
			//	3. 전달된 신규 등록 대상 기본 프로젝트 근무 시간 기준 정보 등록 작업을 수행 후, 수행 결과를 전달 받는다
			registerResultFlag = projectWorkTimeCriteriaService.registerNewProjectWorkTimeCriteria(registerWorkTimeCriteriaDto);
			
			//	4. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!registerResultFlag) {
				//	4.1. 기본 프로젝트 근무 시간 기준 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 근무 시간 기준 정보 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	4.2. 기본 프로젝트 근무 시간 기준 정보 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "기본 프로젝트 근무 시간 기준 정보를 정상적으로 등록 했습니다.";
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
	
	/**
	 * [ 프로젝트_근무 시간 기준 정보 수정 ]
	 * 전달된 클라이언트의 기본 프로젝트 근무 시간 기준 정보 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectWorkTimeCrtrRequest		클라이언트로부터 요청된 프로젝트 근무 시간 기준 정보 변경 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateProjectWorkTimeCriteria")
	public ResponseEntity<?> updateProjectWorkTimeCriteria(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectWorkTimeCrtrRequest projectWorkTimeCrtrRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		WorkTimeCriteriaDto updateWorkTimeCriteriaDto = null;
		Boolean updateResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateProjectWorkTimeCriteria() projectWorkTimeCrtrRequest={}", projectWorkTimeCrtrRequest.toStringInfo());
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getTxId())) {
				txId = projectWorkTimeCrtrRequest.getTxId();
			}
			//	1. 전달된 프로젝트 근무 시간 기준 정보 변경 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			if(SBNUtils.isNull(projectWorkTimeCrtrRequest.getWorkTimeCriteria())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 근무 시간 기준 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 프로젝트 근무 시간 기준 정보 변경 정보를 요청 정보로부터 추출하여 유효성 체크 작업을 수행한다
			updateWorkTimeCriteriaDto = projectWorkTimeCrtrRequest.getWorkTimeCriteria();			
			if((SBNUtils.isNull(updateWorkTimeCriteriaDto.getWrkTmCrtrSeq()) || (updateWorkTimeCriteriaDto.getWrkTmCrtrSeq()) <= 0L) &&
				SBNUtils.isNull(updateWorkTimeCriteriaDto.getWrkTmCrtrCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 근무 시간 기준 정보 키 정보가 미 정의 오류 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(updateWorkTimeCriteriaDto.getWrkTmMeasCrtrTp())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 정보 유형이 미 정의 오류 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(SBNUtils.isNull(updateWorkTimeCriteriaDto.getWrkTmMeasCrtrNm())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 정보 명이 미 정의 오류 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			updateWorkTimeCriteriaDto.setUpdtSeq(adminAuthData.getClientSeq());
			updateWorkTimeCriteriaDto.setUpdtNm(adminAuthData.getClientNm());
			
			//	2. 전달된 신규 등록 대상 기본 프로젝트 근무 시간 기준 정보 변경 작업을 수행 후, 수행 결과를 전달 받는다
			updateResultFlag = projectWorkTimeCriteriaService.updateProjectWorkTimeCriteria(updateWorkTimeCriteriaDto);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!updateResultFlag) {
				//	3.1. 기본 프로젝트 근무 시간 기준 정보 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 근무 시간 기준 정보 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 근무 시간 기준 정보 변경 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "기본 프로젝트 근무 시간 기준 정보를 정상적으로 변경 했습니다.";
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
	 * [ 프로젝트_근무 시간 기준 정보 수정 ]
	 * 전달된 클라이언트의 기본 프로젝트 근무 시간 기준 정보 삭제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectWorkTimeCrtrRequest		클라이언트로부터 요청된 프로젝트 근무 시간 기준 정보 삭제 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/deleteProjectWorkTimeCriteria")
	public ResponseEntity<?> deleteProjectWorkTimeCriteria(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectWorkTimeCrtrRequest projectWorkTimeCrtrRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		WorkTimeCriteriaSC workTimeCriteriaSC = null;
		Boolean deleteResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteProjectWorkTimeCriteria() projectWorkTimeCrtrRequest={}", projectWorkTimeCrtrRequest.toStringInfo());
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getTxId())) {
				txId = projectWorkTimeCrtrRequest.getTxId();
			}
			
			//	1. 전달된 기본 프로젝트 근무 시간 기준 정보 삭제 키 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			if((SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrSeq()) || (projectWorkTimeCrtrRequest.getWrkTmCrtrSeq()) <= 0L) &&
				SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 프로젝트 근무 시간 기준 정보 키 정보가 미 정의 오류 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	
			workTimeCriteriaSC = new WorkTimeCriteriaSC();
			workTimeCriteriaSC.setDeltSeq(adminAuthData.getClientSeq());
			workTimeCriteriaSC.setDeltNm(adminAuthData.getClientNm());
			if((!SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrSeq()) && (projectWorkTimeCrtrRequest.getWrkTmCrtrSeq()) > 0L)) {
				workTimeCriteriaSC.setWrkTmCrtrSeq(projectWorkTimeCrtrRequest.getWrkTmCrtrSeq());
			}
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrCd())) {
				workTimeCriteriaSC.setWrkTmCrtrCd(projectWorkTimeCrtrRequest.getWrkTmCrtrCd());
			}
			log.debug("deleteProjectWorkTimeCriteria() workTimeCriteriaSC={}", workTimeCriteriaSC.toStringInfo());
			
			//	2. 전달된 신규 등록 대상 기본 프로젝트 근무 시간 기준 정보 삭제 작업을 수행 후, 수행 결과를 전달 받는다
			deleteResultFlag = projectWorkTimeCriteriaService.deleteProjectWorkTimeCriteria(workTimeCriteriaSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!deleteResultFlag) {
				//	3.1. 기본 프로젝트 근무 시간 기준 정보 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 근무 시간 기준 정보 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 근무 시간 기준 정보 삭제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "기본 프로젝트 근무 시간 기준 정보를 정상적으로 삭제 했습니다.";
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
	//		프로젝트_근무 시간 기준 코드 중복 체크
	//		프로젝트_근무 시간 기준 정보 목록 조회
	//		프로젝트_근무 시간 기준 정보 상세 조회
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 프로젝트_근무 시간 기준 코드 중복 체크 ]
	 * 전달된 클라이언트의 기본 프로젝트 근무 시간 기준 코드에 대한 중복 여부 체크 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectWorkTimeCrtrRequest		클라이언트로부터 요청된 프로젝트 근무 시간 기준 코드 중복 여부 체크 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/checkProjectWorkTimeCriteriaCodeDuplication", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkProjectWorkTimeCriteriaCodeDuplication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectWorkTimeCrtrRequest projectWorkTimeCrtrRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		WorkTimeCriteriaSC workTimeCriteriaSC = null;
		Boolean duplicationResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkProjectWorkTimeCriteriaCodeDuplication() projectWorkTimeCrtrRequest={}", projectWorkTimeCrtrRequest.toStringInfo());
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getTxId())) {
				txId = projectWorkTimeCrtrRequest.getTxId();
			}
			
			//	1. 전달된 프로젝트 근무 시간 기준 코드 중복 체크 조건 전달 정보에 대한 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "중복 체크 대상 프로젝트 근무 시간 기준 코드 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	2. 전달된 프로젝트 근무 시간 기준 코드 중복 체크 수행을 위한 조건 정보 전달 객체를 구축한다
			workTimeCriteriaSC = new WorkTimeCriteriaSC();
			workTimeCriteriaSC.setWrkTmCrtrCd(projectWorkTimeCrtrRequest.getWrkTmCrtrCd());			
			log.debug("checkProjectWorkTimeCriteriaCodeDuplication() workTimeCriteriaSC={}", workTimeCriteriaSC.toStringInfo());
			
			//	3. 전달된 신규 등록 대상 기본 프로젝트 근무 시간 기준 코드 중복 체크 작업을 수행 후, 수행 결과를 전달 받는다
			duplicationResultFlag = projectWorkTimeCriteriaService.checkProjectWorkTimeCriteriaCodeDuplication(workTimeCriteriaSC);
			log.debug("checkProjectWorkTimeCriteriaCodeDuplication() [ {} ] duplicationResultFlag=[ {} ]", workTimeCriteriaSC.getWrkTmCrtrCd(), duplicationResultFlag);
			
			//	4. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(duplicationResultFlag) {
				//	4.1. 기본 프로젝트 근무 시간 기준 코드 중복인 경우, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 근무 시간 기준 정보 코드가 사용중 인 코드 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	4.2. 기본 프로젝트 근무 시간 기준 코드가 미 중복 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "사용 가능한 프로젝트 근무 시간 기준 코드 입니다.";
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
	
	/**
	 * [ 프로젝트_근무 시간 기준 정보 목록 조회 ]
	 * 전달된 클라이언트의 기본 프로젝트 근무 시간 기준 목록 정보 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectWorkTimeCrtrRequest		클라이언트로부터 요청된 프로젝트 근무 시간 기준 목록 정보 조회 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getProjectWorkTimeCriteriaList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getProjectWorkTimeCriteriaList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectWorkTimeCrtrRequest projectWorkTimeCrtrRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		WorkTimeCriteriaSC workTimeCriteriaSC = null;
		List<WorkTimeCriteriaDto> resultProjectWorkTimeCriteriaList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getProjectWorkTimeCriteriaList() projectWorkTimeCrtrRequest={}", projectWorkTimeCrtrRequest.toStringInfo());
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getTxId())) {
				txId = projectWorkTimeCrtrRequest.getTxId();
			}
			
			//	1. 전달된 기본 프로젝트 근무 시간 기준 정보 목록 조회 조건 전달 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			workTimeCriteriaSC  = new WorkTimeCriteriaSC();
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getUseYn())) {
				workTimeCriteriaSC.setUseYn(projectWorkTimeCrtrRequest.getUseYn());
			}
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmMeasCrtrTp())) {
				workTimeCriteriaSC.setWrkTmMeasCrtrTp(projectWorkTimeCrtrRequest.getWrkTmMeasCrtrTp());
			}
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmMeasCrtrNm())) {
				workTimeCriteriaSC.setWrkTmMeasCrtrNm(projectWorkTimeCrtrRequest.getWrkTmMeasCrtrNm());
			}
			log.debug("getProjectWorkTimeCriteriaList() workTimeCriteriaSC={}", workTimeCriteriaSC.toStringInfo());
			
			//	2. 전달된 프로젝트 근무 시간 기준 목록 정보 추출 작업을 수행 후, 수행 결과를 전달 받는다
			resultProjectWorkTimeCriteriaList = projectWorkTimeCriteriaService.getProjectWorkTimeCriteriaList(workTimeCriteriaSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(resultProjectWorkTimeCriteriaList)) {
				//	3.1. 프로젝트 근무 시간 기준 목록 정보가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 근무 시간 기준 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 근무 시간 기준 목록 정보가 존재 시, 결과 목록 객체를 전달 후 작업을 종료한다
				responseData = new QNPResponseData(resultProjectWorkTimeCriteriaList);
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
	 * [ 프로젝트_근무 시간 기준 정보 목록 조회 ]
	 * 전달된 클라이언트의 기본 프로젝트 근무 시간 기준 상세 정보 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectWorkTimeCrtrRequest		클라이언트로부터 요청된 프로젝트 근무 시간 기준 상세 정보 조회 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getProjectWorkTimeCriteriaDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getProjectWorkTimeCriteriaDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectWorkTimeCrtrRequest projectWorkTimeCrtrRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		WorkTimeCriteriaSC workTimeCriteriaSC = null;
		WorkTimeCriteriaDto resultProjectWorkTimeCriteriaDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getProjectWorkTimeCriteriaDetail() projectWorkTimeCrtrRequest={}", projectWorkTimeCrtrRequest.toStringInfo());
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getTxId())) {
				txId = projectWorkTimeCrtrRequest.getTxId();
			}
			
			//	1. 전달된 기본 프로젝트 근무 시간 기준 정보 상세 조회 조건 전달 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			if((SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrSeq()) || (projectWorkTimeCrtrRequest.getWrkTmCrtrSeq() <= 0L)) &&
				SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 근무 시간 기준 상세 정보 추출 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	
			workTimeCriteriaSC = new WorkTimeCriteriaSC();
			if((!SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrSeq()) && (projectWorkTimeCrtrRequest.getWrkTmCrtrSeq() > 0L))) {
				workTimeCriteriaSC.setWrkTmCrtrSeq(projectWorkTimeCrtrRequest.getWrkTmCrtrSeq());
			}
			if(!SBNUtils.isNull(projectWorkTimeCrtrRequest.getWrkTmCrtrCd())) {
				workTimeCriteriaSC.setWrkTmCrtrCd(projectWorkTimeCrtrRequest.getWrkTmCrtrCd());
			}
			log.debug("getProjectWorkTimeCriteriaDetail() workTimeCriteriaSC={}", workTimeCriteriaSC.toStringInfo());
			
			//	2. 전달된 프로젝트 근무 시간 기준 상세 정보 추출 작업을 수행 후, 수행 결과를 전달 받는다
			resultProjectWorkTimeCriteriaDetail = projectWorkTimeCriteriaService.getProjectWorkTimeCriteriaDetail(workTimeCriteriaSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(resultProjectWorkTimeCriteriaDetail)) {
				//	3.1. 프로젝트 근무 시간 기준 상세 정보가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 근무 시간 기준 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 프로젝트 근무 시간 기준 상세 정보가 존재 시, 결과 목록 객체를 전달 후 작업을 종료한다
				responseData = new QNPResponseData(resultProjectWorkTimeCriteriaDetail);
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
