package com.qnpeople.rnd.pms.apis.common.code.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.common.code.domain.request.CodeMgmtRequest;
import com.qnpeople.rnd.pms.apis.common.code.model.CodeDto;
import com.qnpeople.rnd.pms.apis.common.code.model.CodeSC;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeDto;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeSC;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeTreeDto;
import com.qnpeople.rnd.pms.apis.common.code.service.CodeMgmtService;
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
 * @Package		: com.qnpeople.rnd.pms.apis.common.code.controller
 * @Filename		: CodeMgmtContoller.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.21.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 코드 그룹 및 코드 정보 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	01.공통_전체 코드 그룹 목록 조회
 *  
 *  [ 서비스 ]
 *  	02.공통_사용 가능 그룹 코드 코드 트리 목록 조회
 *  	03.공통_공통 코드 그룹 상세 조회
 *  	04.공통_코드 그룹 별 코드 목록 조회
 *  	05.공통_공통 코드 상세  조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/common/code")
@Slf4j
public class CodeMgmtContoller extends QNPWebBaseController {

	/* 공통 코드 그룹 및 코드 관리 수행을 위한 서비스 인터페이스 객체 */
	@Autowired
	private CodeMgmtService codeMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////
	/**
	 * [ 01.공통_전체 코드 그룹 목록 조회 ]
	 * 클라이언트의 사용 가능 공통 그룹 코드 트리 구조의 목록 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	codeMgmtRequest		클라이언트의 JSON 요청에 대한 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/mgmt/getTotalCodeGroupTreeList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getTotalCodeGroupTree(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CodeMgmtRequest codeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		// GroupCodeSC groupCodeSC = null;
		// List<GroupCodeTreeDto> groupCodeTreeList = new ArrayList<GroupCodeTreeDto>();
		//	응답 전달 객체
		// QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getTotalCodeGroupTree() codeMgmtRequest={}", codeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(codeMgmtRequest.getTxId())) {
				txId = codeMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
//			groupCodeSC = new GroupCodeSC();
			
			// 	임시
			reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
			errorCode = reason.getReasonCode();
			errorMessage = "서비스 준비 중 API 입니다.";
			errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
			responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 Tree 구조의 목록 객체를 추출한다 
//			groupCodeTreeList = codeMgmtService.getTotalGroupCodeTreeList(groupCodeSC);
			
			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
//			if(SBNUtils.isNull(groupCodeTreeList)) {
//				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
//				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
//				errorCode = reason.getReasonCode();
//				errorMessage = "등록된 공통 그룹 코드 정보가 존재하지 않습니다.";
//				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
//				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
//				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
//			} else {
//				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
//				responseData = new QNPResponseData(groupCodeTreeList);
//				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), responseData);
//			}
			
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
	 * [ 02.공통_사용 가능 그룹 코드 코드 트리 목록 조회 ]
	 * 클라이언트의 사용 가능 공통 그룹 코드 트리 구조의 목록 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	codeMgmtRequest		클라이언트의 JSON 요청에 대한 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getAvailableGroupCodeTreeList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getAvailableCodeGroupTree(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CodeMgmtRequest codeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		GroupCodeSC groupCodeSC = null;
		List<GroupCodeTreeDto> groupCodeTreeList = new ArrayList<GroupCodeTreeDto>();
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCodeGroupTree() codeMgmtRequest={}", codeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(codeMgmtRequest.getTxId())) {
				txId = codeMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			groupCodeSC = new GroupCodeSC();
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 Tree 구조의 목록 객체를 추출한다 
			groupCodeTreeList = codeMgmtService.getAvailableGroupCodeTreeList(groupCodeSC);
			
			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(groupCodeTreeList)) {
				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 공통 그룹 코드 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				// responsePacketEntity = new QNPResponsePacketEntityData(txId, reason, errorResponseData);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(groupCodeTreeList);
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
	 * [ 03.공통_공통 코드 그룹 상세 조회 ]
	 * 클라이언트의 사용 가능 공통 그룹 코드 상세 정보 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	codeMgmtRequest		클라이언트의 JSON 요청에 대한 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getCodeGroupDetail", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getCodeGroupDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CodeMgmtRequest codeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = "";
		GroupCodeSC groupCodeSC = null;
		GroupCodeDto groupCodeDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCodeGroupDetail() codeMgmtRequest={}", codeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(codeMgmtRequest.getTxId())) {
				txId = codeMgmtRequest.getTxId();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			groupCodeSC = new GroupCodeSC();
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 세부 정보 객체를 추출한다 
			if(!SBNUtils.isNull(codeMgmtRequest.getGrpCd())) {
				groupCodeSC.setGrpCd(codeMgmtRequest.getGrpCd());
			}
			if(!SBNUtils.isNull(codeMgmtRequest.getPrntGrpCd())) {
				groupCodeSC.setPrntGrpCd(codeMgmtRequest.getPrntGrpCd());
			}
			
			//	3. 요청 수행 결과 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			groupCodeDetail = codeMgmtService.getGroupCodeDetail(groupCodeSC);
			if(SBNUtils.isNull(groupCodeDetail)) {
				//	3.1. 요청 수행 결과 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 공통 그룹 코드 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(groupCodeDetail);
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
	 * [ 04.공통_코드 그룹 별 코드 목록 조회 ]
	 * 클라이언트의 사용 가능 공통 코드 목록 정보 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	codeMgmtRequest		클라이언트의 JSON 요청에 대한 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getAvailableCodeList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getAvailableCodeList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CodeMgmtRequest codeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = "";
		CodeSC codeSC = null;
		List<CodeDto> availableCodeList = new ArrayList<CodeDto>();
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getAvailableCodeList() codeMgmtRequest={}", codeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(codeMgmtRequest.getTxId())) {
				txId = codeMgmtRequest.getTxId();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			codeSC = new CodeSC();
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 세부 정보 객체를 추출한다 
			if(!SBNUtils.isNull(codeMgmtRequest.getGrpCd())) {
				codeSC.setGrpCd(codeMgmtRequest.getGrpCd());
			}
			
			//	3. 요청 수행 결과 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			availableCodeList = codeMgmtService.getAvilableCodeList(codeSC);
			if(SBNUtils.isNull(availableCodeList)) {
				//	3.1. 요청 수행 결과 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 공통 그룹 코드에 대한 상세 코드 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(availableCodeList);
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
	 * [ 05.공통_공통 코드 상세  조회 ]
	 * 클라이언트의 사용 가능 공통 코드 상세 정보 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	codeMgmtRequest		클라이언트의 JSON 요청에 대한 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getCodeDetail", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getCodeDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CodeMgmtRequest codeMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = "";
		CodeSC codeSC = null;
		CodeDto codeDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCodeDetail() codeMgmtRequest={}", codeMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(codeMgmtRequest.getTxId())) {
				txId = codeMgmtRequest.getTxId();
			}
			
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			codeSC = new CodeSC();
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 세부 정보 객체를 추출한다 
			if(SBNUtils.isNull(codeMgmtRequest.getGrpCd())) {
				reason = QNPReasonCode.REQUEST_PARAM_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "공통 그룹 코드 필수 파라미터 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(codeMgmtRequest.getCd())) {
				reason = QNPReasonCode.REQUEST_PARAM_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "공통 코드 필수 파라미터 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			codeSC.setGrpCd(codeMgmtRequest.getGrpCd());
			codeSC.setCd(codeMgmtRequest.getCd());
			
			//	3. 요청 수행 결과 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			codeDetail = codeMgmtService.getCodeDetail(codeSC);
			if(SBNUtils.isNull(codeDetail)) {
				//	3.1. 요청 수행 결과 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 공통 코드에 대한 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(codeDetail);
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
