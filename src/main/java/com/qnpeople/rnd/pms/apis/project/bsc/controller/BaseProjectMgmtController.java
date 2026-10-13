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

import com.qnpeople.rnd.pms.apis.project.bsc.domain.request.BaseProjectMgmtRequest;

import javax.sql.DataSource;
import com.qnpeople.rnd.pms.apis.project.bsc.domain.request.ProjectRoleMgmtRequest;
import com.qnpeople.rnd.pms.apis.project.bsc.domain.request.ProjectSiteMgmtRequest;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectSC;
import com.qnpeople.rnd.pms.apis.project.bsc.service.BaseProjectMgmtService;
import com.qnpeople.rnd.pms.common.auth.QNPAuthData;
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
import kr.co.sbn.platformhub.framework.core.common.web.modules.controller.SBNWebBaseController;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.controller
 * @Filename		: BaseProjectMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.10.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 기본 정보에 대한 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	프로젝트_프로젝트 기본 정보 등록
 *  	프로젝트_프로젝트 기본 정보 변경
 *  	프로젝트_프로젝트 기본 정보 삭제
 *  	프로젝트_사이트 매핑 정보 관리
 *  		프로젝트_사이트 매핑 지정
 *  		프로젝트_사이트 매핑 해제
 *  [ 서비스 ]
 *  	프로젝트_프로젝트 기본 정보 코드 중복 체크 
 *  	프로젝트_프로젝트 기본 정보 목록 조회
 *  	프로젝트_프로젝트 기본 정보 상세 조회
 *  	프로젝트_사이트 매핑 정보 서비스
 *  		프로젝트-사이트 지정 목록 조회
 *  		프로젝트-사이트 지정 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/project/base")
@Slf4j
public class BaseProjectMgmtController extends SBNWebBaseController {

	/** 프로젝트 기본 정보 관리를 위한 실질적인 작업을 수행하는 서비스 인터페이스 객체 */
	@Autowired
	private BaseProjectMgmtService baseProjectMgmtService;
	

	/////////////////////////////////////////////////////////////////
	//	관리
	//		프로젝트_프로젝트 기본 정보 등록
	//  	프로젝트_프로젝트 기본 정보 변경
	//	  	프로젝트_프로젝트 기본 정보 삭제
	//		프로젝트_사이트 매핑 정보 관리
	//			프로젝트_사이트 매핑 지정
	//			프로젝트_사이트 매핑 해제
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 프로젝트_프로젝트 기본 정보 등록 ]
	 * 전달된 클라이언트의 기본 프로젝트 신규 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	baseProjectMgmtRequest	클라이언트로부터 요청된 기본 프로젝트 정보 신규 등록 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/registerNewBaseProject")
	public ResponseEntity<?> registerNewBaseProject(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody BaseProjectMgmtRequest baseProjectMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectDto registerBaseProject = null;
		Boolean registerResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerNewBaseProject() baseProjectMgmtRequest={}", baseProjectMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getTxId())) {
				txId = baseProjectMgmtRequest.getTxId();
			}
			
			//	1. 전달된 기본 프로젝트 신규 등록 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			if(SBNUtils.isNull(baseProjectMgmtRequest.getProject())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록 대상 프로젝트 정보 객체가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			registerBaseProject = baseProjectMgmtRequest.getProject();
			registerBaseProject.setRgstSeq(adminAuthData.getClientSeq());
			registerBaseProject.setRgstNm(adminAuthData.getClientNm());
			log.debug("registerNewBaseProject() registerBaseProject={}", registerBaseProject.toStringInfo());
			
			//	2. 전달된 신규 등록 대상 기본 프로젝트 정보 등록 작업을 수행 후, 수행 결과를 전달 받는다
			registerResultFlag = baseProjectMgmtService.registerNewBaseProject(registerBaseProject);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!registerResultFlag) {
				//	3.1. 기본 프로젝트 정보 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 정보 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 정보 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "기본 프로젝트 정보를 정상적으로 등록 했습니다.";
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
	 * [ 프로젝트_프로젝트 기본 정보 변경 ]
	 * 전달된 클라이언트의 기본 프로젝트 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	baseProjectMgmtRequest	클라이언트로부터 요청된 기본 프로젝트 정보 변경 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/updateBaseProject")
	public ResponseEntity<?> updateBaseProject(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody BaseProjectMgmtRequest baseProjectMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectDto updateBaseProject = null;
		Boolean updateResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateBaseProject() baseProjectMgmtRequest={}", baseProjectMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getTxId())) {
				txId = baseProjectMgmtRequest.getTxId();
			}
			//	1. 프로젝트 기본 정보 변경 작업을 위한 전달된 요청 정보에 대한 유효성 체크 작업을 수행하고, 유효하지 않은 경우 오류 응답을 전달 후 작업을 종료한다
			if(SBNUtils.isNull(baseProjectMgmtRequest.getProject())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			updateBaseProject = baseProjectMgmtRequest.getProject();
			updateBaseProject.setUpdtSeq(adminAuthData.getClientSeq());
			updateBaseProject.setUpdtNm(adminAuthData.getClientNm());
			
			//	2. 전달된 변경 대상 프로젝트 기본 정보에서, 프로젝트의 키 정보에 대한 유효성 체크 작업을 수행 후, 유효하지 않은 경우, 오류 응답을 전달 후 작업을 종료한다
			if((SBNUtils.isNull(updateBaseProject.getPrjctSeq()) || (updateBaseProject.getPrjctSeq() <= 0L)) && SBNUtils.isNull(updateBaseProject.getPrjctCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 프로젝트 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			log.debug("updateBaseProject() updateBaseProject={}", updateBaseProject.toStringInfo());
						
			//	2. 전달된 변경 대상 기본 프로젝트 정보 변경 작업을 수행 후, 수행 결과를 전달 받는다
			updateResultFlag = baseProjectMgmtService.updateBaseProject(updateBaseProject);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!updateResultFlag) {
				//	3.1. 기본 프로젝트 정보 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 정보 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 정보 변경 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "기본 프로젝트 정보를 정상적으로 변경 했습니다.";
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
	 * [ 프로젝트_프로젝트 기본 정보 삭제 ]
	 * 전달된 클라이언트의 기본 프로젝트 삭제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	baseProjectMgmtRequest	클라이언트로부터 요청된 기본 프로젝트 정보 삭제 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/deleteBaseProject")
	public ResponseEntity<?> deleteBaseProject(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody BaseProjectMgmtRequest baseProjectMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectSC deleteBaseProjectSC = null;
		Boolean deleteResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteBaseProject() baseProjectMgmtRequest={}", baseProjectMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getTxId())) {
				txId = baseProjectMgmtRequest.getTxId();
			}
			//	1. 
			if((SBNUtils.isNull(baseProjectMgmtRequest.getPrjctSeq()) || (baseProjectMgmtRequest.getPrjctSeq() <= 0L)) && 
				SBNUtils.isNull(baseProjectMgmtRequest.getPrjctCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 프로젝트 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 전달된 삭제 대상 기본 프로젝트 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			deleteBaseProjectSC = new ProjectSC();
			deleteBaseProjectSC.setDeltSeq(adminAuthData.getClientSeq());
			deleteBaseProjectSC.setDeltNm(adminAuthData.getClientNm());
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctSeq()) && (baseProjectMgmtRequest.getPrjctSeq() > 0)) {
				deleteBaseProjectSC.setPrjctSeq(baseProjectMgmtRequest.getPrjctSeq());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctCd())) {
				deleteBaseProjectSC.setPrjctCd(baseProjectMgmtRequest.getPrjctCd());
			}
			log.debug("deleteBaseProject() deleteBaseProjectSC={}", deleteBaseProjectSC.toStringInfo());
			
			//	3. 전달된 삭제 대상 기본 프로젝트 정보 삭제 작업을 수행 후, 수행 결과를 전달 받는다
			deleteResultFlag = baseProjectMgmtService.deleteBaseProject(deleteBaseProjectSC);
			
			//	4. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!deleteResultFlag) {
				//	4.1. 기본 프로젝트 정보 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 정보 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	4.2. 기본 프로젝트 정보 삭제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "기본 프로젝트 정보를 정상적으로 삭제 했습니다.";
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
	 * [ 프로젝트_사이트 매핑 지정 ]
	 * 전달된 클라이언트의 기본 프로게트에 대한 사이트 매핑 지정 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectSiteMgmtRequest	클라이언트로부터 요청된 기본 프로젝트에 대한 사이트 매핑 지정 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/assignBaseProjectToSite")
	public ResponseEntity<?> assignBaseProjectToSite(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectSiteMgmtRequest projectSiteMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		AppRequestExecuteWayType requestWayType = null;	//	클라이언트의 요청 수행 유형 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectChannelSiteDto registerProjectChannelSite = null;
		List<ProjectChannelSiteDto> registerProjectChannelSiteList = null;
		Boolean assignResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("assignBaseProjectToSite() projectSiteMgmtRequest={}", projectSiteMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getTxId())) {
				txId = projectSiteMgmtRequest.getTxId();
			}
			//	1. 전달된 프로젝트 정보에 대한 사이트 지정 요청 작업 수행을 위한 요청 정보의 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSite()) && SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSiteList())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 지정 대상 프로젝트 사이트 매핑 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSite()) && !SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSiteList())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 지정 대상 프로젝트 사이트 매핑 정보가 중복 전달 되었습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 프로젝트에 대한 사이트 지정 작업 수행 조건 정보 객체를 구축한다
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSite())) {
				//	2.1. 단건 프로젝트 사이트 매핑 정보 전달 시, 수행
				log.debug("assignBaseProjectToSite() 단건 프로젝트 사이트 매핑 작업 수행 시작");
				requestWayType = AppRequestExecuteWayType.SINGLE;
				registerProjectChannelSite = projectSiteMgmtRequest.getProjectChannelSite();
				//	
				if((SBNUtils.isNull(registerProjectChannelSite.getChnlSiteSeq()) || (registerProjectChannelSite.getChnlSiteSeq() <= 0L)) &&
					SBNUtils.isNull(registerProjectChannelSite.getChnlCd()) && SBNUtils.isNull(registerProjectChannelSite.getSiteCd())) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 키 정보가 전달 되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				if((SBNUtils.isNull(registerProjectChannelSite.getSrvcSeq()) || (registerProjectChannelSite.getSrvcSeq() <= 0L)) &&
					SBNUtils.isNull(registerProjectChannelSite.getSrvcCd())) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 서비스 키 정보가 전달 되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				if((SBNUtils.isNull(registerProjectChannelSite.getPrjctSeq()) || (registerProjectChannelSite.getPrjctSeq() <= 0L)) &&
					SBNUtils.isNull(registerProjectChannelSite.getPrjctCd())) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 프로젝트 키 정보가 전달 되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				registerProjectChannelSite.setRgstSeq(adminAuthData.getClientSeq());
				registerProjectChannelSite.setRgstNm(adminAuthData.getClientNm());
				log.debug("assignBaseProjectToSite() registerProjectChannelSite={}", registerProjectChannelSite.toStringInfo());
			} else {
				log.debug("assignBaseProjectToSite() 다중 프로젝트 사이트 매핑 작업 수행 시작");
				//	2.2. 다중 프로젝트 사이트 매핑 정보 전달 시, 수행
				requestWayType = AppRequestExecuteWayType.MULTI;
				registerProjectChannelSiteList = projectSiteMgmtRequest.getProjectChannelSiteList();
				for(int i = 0; i < registerProjectChannelSiteList.size(); i++) {
					if((SBNUtils.isNull(registerProjectChannelSiteList.get(i).getChnlSiteSeq()) || (registerProjectChannelSiteList.get(i).getChnlSiteSeq() <= 0L)) &&
						SBNUtils.isNull(registerProjectChannelSiteList.get(i).getChnlCd()) && SBNUtils.isNull(registerProjectChannelSiteList.get(i).getSiteCd())) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 키 정보가 전달 되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					if((SBNUtils.isNull(registerProjectChannelSiteList.get(i).getSrvcSeq()) || (registerProjectChannelSiteList.get(i).getSrvcSeq() <= 0L)) &&
						SBNUtils.isNull(registerProjectChannelSiteList.get(i).getSrvcCd())) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 서비스 키 정보가 전달 되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					if((SBNUtils.isNull(registerProjectChannelSiteList.get(i).getPrjctSeq()) || (registerProjectChannelSiteList.get(i).getPrjctSeq() <= 0L)) &&
						SBNUtils.isNull(registerProjectChannelSiteList.get(i).getPrjctCd())) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 프로젝트 키 정보가 전달 되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					registerProjectChannelSiteList.get(i).setRgstSeq(adminAuthData.getClientSeq());
					registerProjectChannelSiteList.get(i).setRgstNm(adminAuthData.getClientNm());
					log.debug("assignBaseProjectToSite() registerProjectChannelSiteList[{}]={}", i, registerProjectChannelSiteList.get(i).toStringInfo());
				}
			}
						
			//	3. 전달된 기본 프로젝트에 대한 사이트 지정 작업을 수행 후, 수행 결과를 전달 받는다
			if(AppRequestExecuteWayType.SINGLE.equals(requestWayType)) {
				assignResultFlag = baseProjectMgmtService.assignBaseProjectToSiteForIndividual(registerProjectChannelSite);
			} else {
				assignResultFlag = baseProjectMgmtService.assignBaseProjectToSiteForList(registerProjectChannelSiteList);
			}
			
			//	4. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!assignResultFlag) {
				//	4.1. 기본 프로젝트에 대한 사이트 지정 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 사이트 지정 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	4.2. 기본 프로젝트에 대한 사이트 지정 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트 수행 사이트 지정 작업을 정상적으로 수행 했습니다.";
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
	 * [ 프로젝트_사이트 매핑 해제 ]
	 * 전달된 클라이언트의 기본 프로젝트에 대한 지정 사이트 매핑 해제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectSiteMgmtRequest	클라이언트로부터 요청된 기본 프로젝트에 대한 지정된 사이트 매핑 해제 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@PostMapping(value="/mgmt/releaseBaseProjectFromSite")
	public ResponseEntity<?> releaseBaseProjectFromSite(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectSiteMgmtRequest projectSiteMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		AppRequestExecuteWayType requestWayType = null;	//	클라이언트의 요청 수행 유형 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectChannelSiteSC releaseProjectSiteKey = null;
		List<ProjectChannelSiteSC> releaseProjectSiteKeyList = null;
		Boolean releaseResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("releaseBaseProjectFromSite() projectSiteMgmtRequest={}", projectSiteMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getTxId())) {
				txId = projectSiteMgmtRequest.getTxId();
			}
			//	1. 전달된 프로젝트 정보에 대한 지정된 사이트 해제 요청 작업 수행을 위한 요청 정보의 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSiteKey()) && SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSiteKeyList())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "지정 해제 대상 프로젝트 사이트 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSiteKey()) && !SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSiteKeyList())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "지정 해제 대상 프로젝트 사이트 키 정보가 중복 전달 되었습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			//	2. 프로젝트에 대한 지정된 사이트 해제 작업 수행 조건 정보 객체를 구축한다
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getProjectChannelSiteKey())) {				
				//	2.1. 단건 프로젝트 사이트 지정 해제 키 정보 전달 시, 프로젝트 사이트 지정 해제 작업 수행 시작
				log.debug("releaseBaseProjectFromSite() 단건 프로젝트 사이트 지정 해제 작업 수행 시작");
				requestWayType = AppRequestExecuteWayType.SINGLE;
				releaseProjectSiteKey = projectSiteMgmtRequest.getProjectChannelSiteKey();
				log.debug("releaseBaseProjectFromSite() [ {} ] releaseProjectSiteKey={}", requestWayType, releaseProjectSiteKey);
				//	
				if((SBNUtils.isNull(releaseProjectSiteKey.getChnlSiteSeq()) || (releaseProjectSiteKey.getChnlSiteSeq() <= 0L)) &&
					SBNUtils.isNull(releaseProjectSiteKey.getChnlCd()) && SBNUtils.isNull(releaseProjectSiteKey.getSiteCd())) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "지정 해제 대상 프로젝트 단건 사이트 지정 해제 키 정보가 전달 되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				if((SBNUtils.isNull(releaseProjectSiteKey.getSrvcSeq()) || (releaseProjectSiteKey.getSrvcSeq() <= 0L)) &&
					SBNUtils.isNull(releaseProjectSiteKey.getSrvcCd())) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "지정 해제 대상 프로젝트 단건 사이트 지정 해제 서비스 키 정보가 전달 되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				if((SBNUtils.isNull(releaseProjectSiteKey.getPrjctSeq()) || (releaseProjectSiteKey.getPrjctSeq() <= 0L)) &&
					SBNUtils.isNull(releaseProjectSiteKey.getPrjctCd())) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "지정 해제 대상 프로젝트 단건 사이트 지정 해제 프로젝트 키 정보가 전달 되지 않았습니다.";
					errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
					responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
					return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
				}
				releaseProjectSiteKey.setDeltSeq(adminAuthData.getClientSeq());
				releaseProjectSiteKey.setDeltNm(adminAuthData.getClientNm());
				log.debug("assignBaseProjectToSite() releaseProjectSiteKey={}", releaseProjectSiteKey.toStringInfo());
			} else {				
				//	2.2. 다중 프로젝트 사이트 지정 해제 키 목록 정보 전달 시, 프로젝트 사이트 지정 해제 작업 수행 시작
				log.debug("releaseBaseProjectFromSite() 다중 프로젝트 사이트 지정 해제 작업 수행 시작");
				requestWayType = AppRequestExecuteWayType.MULTI;
				releaseProjectSiteKeyList = projectSiteMgmtRequest.getProjectChannelSiteKeyList();
				log.debug("releaseBaseProjectFromSite() [ {} ] releaseProjectSiteKeyList={}", requestWayType, releaseProjectSiteKeyList);
				for(int i = 0; i < releaseProjectSiteKeyList.size(); i++) {
					if((SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getChnlSiteSeq()) || (releaseProjectSiteKeyList.get(i).getChnlSiteSeq() <= 0L)) &&
						SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getChnlCd()) && SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getSiteCd())) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "지정 해제 대상 프로젝트 단건 사이트 지정 해제 키 정보가 전달 되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					if((SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getSrvcSeq()) || (releaseProjectSiteKeyList.get(i).getSrvcSeq() <= 0L)) &&
						SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getSrvcCd())) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "지정 해제 대상 프로젝트 단건 사이트 지정 해제 서비스 키 정보가 전달 되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					if((SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getPrjctSeq()) || (releaseProjectSiteKeyList.get(i).getPrjctSeq() <= 0L)) &&
						SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getPrjctCd())) {
						reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
						errorCode = reason.getReasonCode();
						errorMessage = "지정 해제 대상 프로젝트 단건 사이트 지정 해제 프로젝트 키 정보가 전달 되지 않았습니다.";
						errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
						responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
						return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
					}
					releaseProjectSiteKeyList.get(i).setDeltSeq(adminAuthData.getClientSeq());
					releaseProjectSiteKeyList.get(i).setDeltNm(adminAuthData.getClientNm());
					log.debug("assignBaseProjectToSite() releaseProjectSiteKeyList[{}]={}", i, releaseProjectSiteKeyList.get(i).toStringInfo());
				}
			}
			
			//	3. 전달된 기본 프로젝트에 대한 지정된 사이트해제 작업을 수행 후, 수행 결과를 전달 받는다
			if(AppRequestExecuteWayType.SINGLE.equals(requestWayType)) {
				releaseResultFlag = baseProjectMgmtService.releaseBaseProjectFromSiteForIndividual(releaseProjectSiteKey);
			} else {
				releaseResultFlag = baseProjectMgmtService.releaseBaseProjectFromSiteForList(releaseProjectSiteKeyList);
			}
			
			//	4. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(!releaseResultFlag) {
				//	4.1. 프로젝트에 대한 지정된 사이트 해제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트에 대한 지정 사이트 해제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	4.2. 프로젝트에 대한 지정된 상이트 해제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "프로젝트에 대한 지정 사이트 해제 작업을 정상적으로 수행 했습니다.";
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
	//		프로젝트_프로젝트 기본 정보 코드 중복 체크 
	//  	프로젝트_프로젝트 기본 정보 목록 조회
	//  	프로젝트_프로젝트 기본 정보 상세 조회
	//  	프로젝트_사이트 매핑 정보 서비스
	//	  		프로젝트_사이트 지정 목록 조회
	//	  		프로젝트_사이트 지정 상세 조회
	/////////////////////////////////////////////////////////////////		
	
	/**
	 * [ 프로젝트_프로젝트 기본 정보 삭제 ]
	 * 전달된 클라이언트의 기본 프로젝트 코드에 대한 중복 여부 체크 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	baseProjectMgmtRequest	클라이언트로부터 요청된 기본 프로젝트 코드 정보 중복 여부 체크 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/checkProjectCodeDuplication", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkProjectCodeDuplication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody BaseProjectMgmtRequest baseProjectMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectSC duplicationCheckBaseProjectSC = null;
		Boolean duplicationResultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkProjectCodeDuplication() baseProjectMgmtRequest={}", baseProjectMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getTxId())) {
				txId = baseProjectMgmtRequest.getTxId();
			}
			
			//	1. 전달된 중복 여부 체크 대상 기본 프로젝트 코드 정보 수행을 위한 요청 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			duplicationCheckBaseProjectSC = new ProjectSC();
			if(SBNUtils.isNull(baseProjectMgmtRequest.getPrjctCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "중복 여부 체크 대상 프로젝트 코드가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			duplicationCheckBaseProjectSC.setPrjctCd(baseProjectMgmtRequest.getPrjctCd());
			
			log.debug("checkProjectCodeDuplication() duplicationCheckBaseProjectSC={}", duplicationCheckBaseProjectSC.toStringInfo());
			
			//	2. 전달된 중복 여부 체크 대상 기본 프로젝트 코드 중복 체크 여부 작업을 수행 후, 수행 결과를 전달 받는다
			duplicationResultFlag = baseProjectMgmtService.checkBaseProjectCodeDuplication(duplicationCheckBaseProjectSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(duplicationResultFlag) {
				//	3.1. 기본 프로젝트 코드 중복 여부 체크 후, 이미 사용 중인 프로젝트 코드인 경우 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "이미 사용중인 기본 프로젝트 코드 정보 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 코드 정보 중복 체크 결과가 사용 가능한  정상 코드 인 경우, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "사용가능한 기본 프로젝트 코드 정보 입니다.";
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
	 * [ 프로젝트_프로젝트 기본 정보 목록 조회 ]
	 * 전달된 클라이언트의 기본 프로젝트 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	baseProjectMgmtRequest	클라이언트로부터 요청된 기본 프로젝트 목록 조회 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/selectBaseProjectList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> selectBaseProjectList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody BaseProjectMgmtRequest baseProjectMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectSC projectSC = null;
		List<ProjectDto> projectList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("selectBaseProjectList() baseProjectMgmtRequest={}", baseProjectMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getTxId())) {
				txId = baseProjectMgmtRequest.getTxId();
			}
			
			//	1. 전달된 기본 프로젝트 목록 정보 조회 수행을 위한 요청 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			projectSC = new ProjectSC();			
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getUseYn())) {
				projectSC.setUseYn(baseProjectMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctTp())) {
				projectSC.setPrjctTp(baseProjectMgmtRequest.getPrjctTp());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctStatTp())) {
				projectSC.setPrjctStatTp(baseProjectMgmtRequest.getPrjctStatTp());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctDplyTp())) {
				projectSC.setPrjctDplyTp(baseProjectMgmtRequest.getPrjctDplyTp());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctVrfcTp())) {
				projectSC.setPrjctVrfcTp(baseProjectMgmtRequest.getPrjctVrfcTp());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctCptbTp())) {
				projectSC.setPrjctCptbTp(baseProjectMgmtRequest.getPrjctCptbTp());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctNm())) {
				projectSC.setPrjctNm(baseProjectMgmtRequest.getPrjctNm());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctStrtDt())) {
				projectSC.setPrjctStrtDt(baseProjectMgmtRequest.getPrjctStrtDt());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctEndDt())) {
				projectSC.setPrjctEndDt(baseProjectMgmtRequest.getPrjctEndDt());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getEtc())) {
				projectSC.setEtc(baseProjectMgmtRequest.getEtc());
			}
			log.debug("selectBaseProjectList() projectSC={}", projectSC.toStringInfo());
			
			//	2. 전달된 기본 프로젝트 목록 정보 조회 작업을 수행 후, 수행 결과를 전달 받는다
			projectList = baseProjectMgmtService.getBaseProjectList(projectSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(projectList)) {
				//	3.1. 기본 프로젝트 목록 정보가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 목록 정보가 존재 시, 추출한 프로젝트 기본 정보 목록 객체를 응답 데이터 객체에 설정한다
				responseData = new QNPResponseData(projectList);
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
	 * [ 프로젝트_프로젝트 기본 정보 상세 조회 ]
	 * 전달된 클라이언트의 기본 프로젝트 상세 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	baseProjectMgmtRequest	클라이언트로부터 요청된 기본 프로젝트 상세 조회 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/selectBaseProjectDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> selectBaseProjectDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody BaseProjectMgmtRequest baseProjectMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectSC projectSC = null;
		ProjectDto projectDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("selectBaseProjectDetail() baseProjectMgmtRequest={}", baseProjectMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getTxId())) {
				txId = baseProjectMgmtRequest.getTxId();
			}
			
			//	1. 전달된 기본 프로젝트 상세 정보 조회 수행을 위한 요청 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			projectSC = new ProjectSC();
			if((SBNUtils.isNull(baseProjectMgmtRequest.getPrjctSeq()) || (baseProjectMgmtRequest.getPrjctSeq() <= 0L)) && 
			   SBNUtils.isNull(baseProjectMgmtRequest.getPrjctCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 상세 정보 추출 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctSeq()) && (baseProjectMgmtRequest.getPrjctSeq() >= 0L)) {
				projectSC.setPrjctSeq(baseProjectMgmtRequest.getPrjctSeq());
			}
			if(!SBNUtils.isNull(baseProjectMgmtRequest.getPrjctCd())) {
				projectSC.setPrjctCd(baseProjectMgmtRequest.getPrjctCd());
			}
			log.debug("selectBaseProjectDetail() projectSC={}", projectSC.toStringInfo());
			
			//	2. 전달된 기본 프로젝트 상세 정보 조회 작업을 수행 후, 수행 결과를 전달 받는다
			projectDetail = baseProjectMgmtService.getBaseProjectDetail(projectSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(projectDetail)) {
				//	3.1. 기본 프로젝트 상세 정보가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 상세 정보가 존재 시, 추출한 프로젝트 기본 정보 목록 객체를 응답 데이터 객체에 설정한다
				responseData = new QNPResponseData(projectDetail);
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
	 * [ 프로젝트_사이트 지정 목록 조회 ]
	 * 전달된 클라이언트의 프로젝트 지정 사이트 목록 조회 요청에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectSiteMgmtRequest	클라이언트로부터 요청된 프로젝트 지정 사이트 목록 조회 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getAssignedBaseProjectSiteList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getAssignedBaseProjectSiteList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectSiteMgmtRequest projectSiteMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectChannelSiteSC projectChannelSiteSC = null;
		List<ProjectChannelSiteDto> projectChannelSiteList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAssignedBaseProjectSiteList() projectSiteMgmtRequest={}", projectSiteMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getTxId())) {
				txId = projectSiteMgmtRequest.getTxId();
			}
			
			//	1. 전달된 기본 프로젝트 지정 사이트 목록 정보 조회 수행을 위한 요청 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			projectChannelSiteSC = new ProjectChannelSiteSC();
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getUseYn())) {
				projectChannelSiteSC.setUseYn(projectSiteMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getPrjctCd())) {
				projectChannelSiteSC.setPrjctCd(projectSiteMgmtRequest.getPrjctCd());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getChnlCd())) {
				projectChannelSiteSC.setChnlCd(projectSiteMgmtRequest.getChnlCd());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getSiteCd())) {
				projectChannelSiteSC.setSiteCd(projectSiteMgmtRequest.getSiteCd());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getSrvcCd())) {
				projectChannelSiteSC.setSrvcCd(projectSiteMgmtRequest.getSrvcCd());
			}			
			log.debug("getAssignedBaseProjectSiteList() projectChannelSiteSC={}", projectChannelSiteSC.toStringInfo());
			
			//	2. 전달된 기본 프로젝트 지정 사이트 목록 정보 조회 작업을 수행 후, 수행 결과를 전달 받는다
			projectChannelSiteList = baseProjectMgmtService.getAssignedBaseProjectSiteList(projectChannelSiteSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(projectChannelSiteList)) {
				//	3.1. 기본 프로젝트 지정 사이트 정보가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 지정 사이트 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 지정 사이트 정보가 존재 시, 추출한 프로젝트 기본 정보 지정 사이트 목록 객체를 응답 데이터 객체에 설정한다
				responseData = new QNPResponseData(projectChannelSiteList);
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
	 * [ 프로젝트_사이트 지정 상세 조회 ]
	 * 전달된 클라이언트의 프로젝트 지정 사이트 상세 조회 요청에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	projectSiteMgmtRequest	클라이언트로부터 요청된 프로젝트 지정 사이트 상세 조회 작업 수행 요청 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			업체별 프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getAssignedBaseProjectSiteDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getAssignedBaseProjectSiteDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ProjectSiteMgmtRequest projectSiteMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		adminAuthData.setClientNm("통합 관리자");					//	임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ProjectChannelSiteSC projectChannelSiteSC = null;
		ProjectChannelSiteDto projectChannelSiteDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAssignedBaseProjectSiteDetail() projectSiteMgmtRequest={}", projectSiteMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getTxId())) {
				txId = projectSiteMgmtRequest.getTxId();
			}
			
			//	1. 전달된 기본 프로젝트 지정 사이트 상세 정보 조회 수행을 위한 요청 정보에 대한 유효성 체크 작업 및 등록 정보 객체를 구축한다
			if((SBNUtils.isNull(projectSiteMgmtRequest.getPrjctSeq()) || (projectSiteMgmtRequest.getPrjctSeq() <= 0L)) &&
				SBNUtils.isNull(projectSiteMgmtRequest.getPrjctCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 사이트 지정 상세 정보 추출 프로젝트 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if((SBNUtils.isNull(projectSiteMgmtRequest.getSrvcSeq()) || (projectSiteMgmtRequest.getSrvcSeq() <= 0L)) &&
				SBNUtils.isNull(projectSiteMgmtRequest.getSrvcCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 사이트 지정 상세 정보 추출 서비스 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			if((SBNUtils.isNull(projectSiteMgmtRequest.getChnlSiteSeq()) || (projectSiteMgmtRequest.getChnlSiteSeq() <= 0L)) &&
				((SBNUtils.isNull(projectSiteMgmtRequest.getChnlCd())) || (SBNUtils.isNull(projectSiteMgmtRequest.getSiteCd())))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "기본 프로젝트 사이트 지정 상세 정보 추출 채널 사이트 키 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	
			projectChannelSiteSC = new ProjectChannelSiteSC();
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getPrjctSeq()) && (projectSiteMgmtRequest.getPrjctSeq() > 0L)) {
				projectChannelSiteSC.setPrjctSeq(projectSiteMgmtRequest.getPrjctSeq());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getPrjctCd())) {
				projectChannelSiteSC.setPrjctCd(projectSiteMgmtRequest.getPrjctCd());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getChnlSiteSeq()) && (projectSiteMgmtRequest.getChnlSiteSeq() > 0L)) {
				projectChannelSiteSC.setChnlSiteSeq(projectSiteMgmtRequest.getChnlSiteSeq());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getChnlCd())) {
				projectChannelSiteSC.setChnlCd(projectSiteMgmtRequest.getChnlCd());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getSiteCd())) {
				projectChannelSiteSC.setSiteCd(projectSiteMgmtRequest.getSiteCd());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getSrvcSeq()) && (projectSiteMgmtRequest.getSrvcSeq() > 0L)) {
				projectChannelSiteSC.setSrvcSeq(projectSiteMgmtRequest.getSrvcSeq());
			}
			if(!SBNUtils.isNull(projectSiteMgmtRequest.getSrvcCd())) {
				projectChannelSiteSC.setSrvcCd(projectSiteMgmtRequest.getSrvcCd());
			}			
			log.debug("getAssignedBaseProjectSiteDetail() projectChannelSiteSC={}", projectChannelSiteSC.toStringInfo());
			
			//	2. 전달된 기본 프로젝트 지정 사이트 상세 정보 조회 작업을 수행 후, 수행 결과를 전달 받는다
			projectChannelSiteDetail = baseProjectMgmtService.getAssignedBaseProjectSiteDetail(projectChannelSiteSC);
			
			//	3. 요청 수행 결과에 대한 클라이언트로 전달할 요청 수행 응답 데이터 객체를 구축한다
			if(SBNUtils.isNull(projectChannelSiteDetail)) {
				//	3.1. 기본 프로젝트 지정 사이트 정보가 미 존재 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 지정 사이트 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 기본 프로젝트 지정 사이트 정보가 존재 시, 추출한 프로젝트 기본 정보 지정 사이트 목록 객체를 응답 데이터 객체에 설정한다
				responseData = new QNPResponseData(projectChannelSiteDetail);
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
