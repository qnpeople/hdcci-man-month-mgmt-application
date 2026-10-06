package com.qnpeople.rnd.pms.apis.service.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.service.service.domain.request.ServiceMgmtRequest;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceDto;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceSC;
import com.qnpeople.rnd.pms.apis.service.service.service.ServiceMgmtService;
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
import kr.co.sbn.platformhub.framework.core.types.SBNUseYnType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.category.controller
 * @Filename		: SrvcCategoryMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.30.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 서비스 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	01. 서비스_신규 서비스 코드 중복 체크
 *  	02. 서비스_서비스 등록
 *  	03. 서비스_서비스 수정
 *  	04. 서비스_서비스 삭제
 *  
 *  [ 서비스 ]
 *  	05.서비스_서비스 목록 조회
 *  	06.서비스_서비스 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/service")
@Slf4j
public class ServiceMgmtController extends SBNWebBaseController {

	/* 실질적인 시스템 지원 서비스 관리 수행 서비스 인터페이스 객체 */
	@Autowired
	private ServiceMgmtService serviceMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////	
	/**
	 * [ 01. 서비스_신규 서비스 코드 중복 체크 ]
	 * 클라이언트의 신규 등록 대상 서비스 코드에 대한 중복 여부 체크 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceMgmtRequest	클라이언트의 신규 등록 대상 서비스 코드 중복 체크 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/mgmt/checkServiceKeyDuplication", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> checkServiceKeyDuplication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceMgmtRequest serviceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceSC serviceSC = null;
		Boolean resultFlag = false;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkServiceKeyDuplication() serviceMgmtRequest={}", serviceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceMgmtRequest.getTxId())) {
				txId = serviceMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			serviceSC = new ServiceSC();
			if(!SBNUtils.isNull(serviceMgmtRequest.getSrvcCtgryCd())) {
				serviceSC.setSrvcCtgryCd(serviceMgmtRequest.getSrvcCtgryCd());
			}
			if(!SBNUtils.isNull(serviceMgmtRequest.getSrvcCd())) {
				serviceSC.setSrvcCd(serviceMgmtRequest.getSrvcCd());
			}
			log.debug("checkServiceKeyDuplication() serviceSC={}", serviceSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 서비스 카테고리 및 서비스 코드 정보에 대한 중복 여부를 체크한다 
			resultFlag = serviceMgmtService.isServiceKeyDuplicated(serviceSC);
			
			//	3. 중복 여부에 대한 결과에 따라 작업을 수행한다
			if(resultFlag) {
				//	3.1. 서비스 카테고리별 서비스 코드 정보가 중복 시, 중복 오류 응답 메시지를 구축하여 전달 후 작업을 종료한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 카테고리별 중복된 서비스 코드 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 서비스 카테고리별 서비스 코드가 사용 가능한 경우(중복이 아닌 경우)에 대한 결과를 전달 후 작업을 종료한다
				String successMessage = "서비스 카테고리별[ ".concat(serviceSC.getSrvcCtgryCd()).concat(" ] 사용 가능 서비스 코드[ ").concat(serviceSC.getSrvcCd()).concat(" ] 입니다.");
				responseData = new QNPResponseData(successMessage);
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
	 * [ 02. 서비스_서비스 등록 ]
	 * 클라이언트의 신규 서비스 등록 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceMgmtRequest	클라이언트의 신규 서비스 정보 등록 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/mgmt/registerService", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> registerService(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceMgmtRequest serviceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceDto registerServiceDto = null;
		Boolean resultFlag = false;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerService() serviceMgmtRequest={}", serviceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceMgmtRequest.getTxId())) {
				txId = serviceMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 정보 전달 객체를 구축한다
			registerServiceDto = new ServiceDto();
			if(!SBNUtils.isNull(serviceMgmtRequest.getServiceDto())) {
				registerServiceDto = serviceMgmtRequest.getServiceDto();
			}
			log.debug("registerService() registerServiceDto={}", registerServiceDto.toStringInfo());
			
			//	2. 클라이언트로부터 전달된 신규 등록 서비스 정보를 이용하여 서비스 신규 등록 작업을 수행한다
			resultFlag = serviceMgmtService.registerNewService(registerServiceDto);
			
			//	3. 신규 서비스 정보 등록 요청 수행 결과에 따라 작업을 수행한다
			if(!resultFlag) {
				//	3.1. 서비스 등록 작업 수행 실패 시, 오류 메시지를 구성하여 오류 응답 처리를 수행한다
				reason = QNPReasonCode.REGISTRATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 서비스 정보 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 서비스 정보 정상 등록 시, 등록된 정보를 추출하여 결과 정보로 전달한다
				responseData = new QNPResponseData(registerServiceDto);
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
	 * [ 03. 서비스_서비스 수정 ]
	 * 클라이언트의 신규 서비스 변경 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceMgmtRequest	클라이언트의 신규 서비스 정보 변경 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/mgmt/updateService", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> updateService(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceMgmtRequest serviceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceDto updateServiceDto = null;
		Boolean resultFlag = false;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateService() serviceMgmtRequest={}", serviceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceMgmtRequest.getTxId())) {
				txId = serviceMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 정보 전달 객체를 구축한다
			updateServiceDto = new ServiceDto();
			if(!SBNUtils.isNull(serviceMgmtRequest.getServiceDto())) {
				updateServiceDto = serviceMgmtRequest.getServiceDto();
			}
			log.debug("updateService() updateServiceDto={}", updateServiceDto.toStringInfo());
			
			//	2. 클라이언트로부터 전달된 서비스 정보에 대한 변경 작업을 수행한다
			resultFlag = serviceMgmtService.updateService(updateServiceDto);
			
			//	3. 서비스 정보 변경 요청 수행 결과에 따라 작업을 수행한다
			if(!resultFlag) {
				//	3.1. 서비스 변경 작업 수행 실패 시, 오류 메시지를 구성하여 오류 응답 처리를 수행한다
				reason = QNPReasonCode.MODIFICATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 정보 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 서비스 정보 정상 변경 시, 변경된 정보를 추출하여 결과 정보로 전달한다
				responseData = new QNPResponseData(updateServiceDto);
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
	 * [ 04. 서비스_서비스 삭제 ]
	 * 클라이언트의 신규 서비스 삭제 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceMgmtRequest	클라이언트의 신규 서비스 정보 삭제 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/mgmt/deleteService", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> deleteService(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceMgmtRequest serviceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceSC serviceSC = null;
		Boolean resultFlag = false;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteService() serviceMgmtRequest={}", serviceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceMgmtRequest.getTxId())) {
				txId = serviceMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 정보 전달 객체를 구축한다
			//	삭제 처리는 USE_YN 을 'N' 설정으로 처리한다
			serviceSC = new ServiceSC();
			serviceSC.setUseYn(SBNUseYnType.N.getTypeCode());
			if(!SBNUtils.isNull(serviceMgmtRequest.getSrvcSeq()) && (serviceMgmtRequest.getSrvcSeq() > 0L)) {
				serviceSC.setSrvcSeq(serviceMgmtRequest.getSrvcSeq());
			}
			log.debug("deleteService() serviceSC={}", serviceSC.toStringInfo());
			
			//	2. 클라이언트로부터 전달된 서비스 정보에 대한 삭제 작업을 수행한다
			resultFlag = serviceMgmtService.deleteService(serviceSC);
			
			//	3. 서비스 정보 삭제 요청 수행 결과에 따라 작업을 수행한다
			if(!resultFlag) {
				//	3.1. 서비스 삭제 작업 수행 실패 시, 오류 메시지를 구성하여 오류 응답 처리를 수행한다
				reason = QNPReasonCode.MODIFICATION_EXEC_FAILURE_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 정보 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 서비스 정보 정상 삭제 시, 삭제 작업 정상 수행 메시지 정보를 전달 후 작업을 종료한다
				String resultMessage = "요청 하신 서비스 정보 삭제 작업을 정상 수행 하였습니다.";
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
	 * [ 05.서비스_서비스 목록 조회 ]
	 * 클라이언트의 서비스 목록 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceMgmtRequest	클라이언트의 사용 가능 서비스 목록 조회 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getServiceList", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getServiceList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceMgmtRequest serviceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceSC serviceSC = null;
		List<ServiceDto> serviceList = new ArrayList<ServiceDto>();
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getServiceList() serviceMgmtRequest={}", serviceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceMgmtRequest.getTxId())) {
				txId = serviceMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			serviceSC = new ServiceSC();
			if(!SBNUtils.isNull(serviceMgmtRequest.getUseYn())) {
				serviceSC.setUseYn(serviceMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(serviceMgmtRequest.getSrvcTp())) {
				serviceSC.setSrvcTp(serviceMgmtRequest.getSrvcTp());
			}
			if(!SBNUtils.isNull(serviceMgmtRequest.getSrvcCtgryCd())) {
				serviceSC.setSrvcCtgryCd(serviceMgmtRequest.getSrvcCtgryCd());
			}
			if(!SBNUtils.isNull(serviceMgmtRequest.getSrvcNm())) {
				serviceSC.setSrvcNm(serviceMgmtRequest.getSrvcNm());
			}
			log.debug("getServiceList() serviceSC={}", serviceSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 Tree 구조의 목록 객체를 추출한다 
			serviceList = serviceMgmtService.getServiceList(serviceSC);
			
			//	3. 요청 수행 결과 목록 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(serviceList)) {
				//	3.1. 요청 수행 결과 목록 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 서비스 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 목록 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(serviceList);
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
	 * [ 06.서비스_서비스 상세 조회 ]
	 * 클라이언트의 서비스 상세 조회 요청에 대한 수행 결과를 JSON 포맷의 ResponseEntity 객체로 전달하는 메소드
	 *  
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	httpRequest				클라이언트의 요청 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse				클라이언트의 요청 수행 결과 응답 정보를 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceMgmtRequest	클라이언트의 사용 가능 서비스 상세 조회 요청 수행 결과에 대한 JSON 결과 정보를 전달하는 객체
	 * @return		클라이언트 요청에 대한 수행 결과 응답 ResponseEntity 객체
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업을 수행하는 Exception 객체
	 */
	@RequestMapping(value="/srvc/getServiceDetail", method= { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> getServiceDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceMgmtRequest serviceMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceSC serviceSC = null;
		ServiceDto serviceDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getServiceDetail() serviceMgmtRequest={}", serviceMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceMgmtRequest.getTxId())) {
				txId = serviceMgmtRequest.getTxId();
			}
			//	1. 전달된 요청 정보에 대한 수행 조건 정보 전달 객체를 구축한다
			serviceSC = new ServiceSC();
			if(!SBNUtils.isNull(serviceMgmtRequest.getSrvcSeq()) && (serviceMgmtRequest.getSrvcSeq() > 0)) {
				serviceSC.setSrvcSeq(serviceMgmtRequest.getSrvcSeq());
			}
			if(!SBNUtils.isNull(serviceMgmtRequest.getSrvcCd())) {
				serviceSC.setSrvcCd(serviceMgmtRequest.getSrvcCd());
			}
			log.debug("getServiceDetail() serviceSC={}", serviceSC.toStringInfo());
			
			//	2. 구축된 조건 정보 객체를 이용하여 사용 가능한 공통 그룹 코드 정보에 대한 Tree 구조의 목록 객체를 추출한다 
			serviceDetail = serviceMgmtService.getServiceDetail(serviceSC);
			
			//	3. 요청 수행 결과 상세 정보 객체 존재 여부에 따른 전달 수행 결과 응답 객체를 구축한다
			if(SBNUtils.isNull(serviceDetail)) {
				//	3.1. 요청 수행 결과 상세 정보 객체 미 존재 시, 결과 데이터 미 존재 오류 메시지 전달 응답 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "등록된 사용 가능 서비스 정보가 존재하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 요청 수행 결과 상세 정보 객체 존재 시, 결과 데이터에 대한 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(serviceDetail);
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
