package com.qnpeople.rnd.pms.apis.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.company.domain.request.CompanyMgmtRequest;
import com.qnpeople.rnd.pms.apis.company.domain.request.ServiceCompanyMgmtRequest;
import com.qnpeople.rnd.pms.apis.company.model.CompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanySC;
import com.qnpeople.rnd.pms.apis.company.service.CompanyMgmtService;
import com.qnpeople.rnd.pms.apis.system.menu.domain.request.MenuMgmtRequest;
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
 * @Package		: com.qnpeople.rnd.pms.apis.company.controller
 * @Filename		: CompanyMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 업체 영역의 업체 정보 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	업체_업체 등록
 *  	업체_업체 변경
 *  	업체_업체 삭제
 *  	업체_업체 서비스 지정
 *  	업체_업체 서비스 해제
 *  [ 서비스 ]
 *  	업체_업체 코드 중복 여부 체크
 *  	업체_업체 목록 조회
 *  	업체_업체 상세 조회
 *  	업체_서비스 업체 목록 조회
 *  	업체_서비스 업체 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/corp/company")
@Slf4j
public class CompanyMgmtController extends QNPWebBaseController {

	/* 실질적인 업체 관리 작업을 수행하는 인터페이스 객체 */
	@Autowired
	private CompanyMgmtService companyMgmtService;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	//		업체_업체 등록
	//  	업체_업체 변경
	//  	업체_업체 삭제
	//  	업체_업체 서비스 지정
	//  	업체_업체 서비스 해제
	/////////////////////////////////////////////////////////////////		
	/**
	 * [ 업체_업체 등록 ]
	 * 전달된 클라이언트의 신규 업체 등록 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyMgmtRequest		클라이언트로부터 요청된 신규 등록 대상 업체의 신규 등록 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/registerNewCompany", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> registerNewCompany(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyMgmtRequest companyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		CompanyDto companyDto = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("registerNewCompany() companyMgmtRequest={}", companyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyMgmtRequest.getTxId())) {
				txId = companyMgmtRequest.getTxId();
			}
			
			//	1. 신규 등록 대상 업체 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(companyMgmtRequest.getCompany())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 업체 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			//	2. 전달된 요청 정보로부터 신규 등록 대상 업체 정보를 추출한다
			companyDto = companyMgmtRequest.getCompany();
			companyDto.setRgstSeq(adminAuthData.getClientSeq());
			log.debug("registerNewCompany() companyDto={}", companyDto.toStringInfo());
			
			//	3. 전달된 업체 등록 작업 수행 결과를 추출한다
			resultFlag = companyMgmtService.registerNewCompany(companyDto);
			
			//	3. 업체 등록 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 업체 등록 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 업체 등록 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 업체 등록 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "신규 업체 등록 작업을 정상 수행 했습니다.";
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
	 * [ 업체_업체 변경 ]
	 * 전달된 클라이언트의 업체 변경 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyMgmtRequest		클라이언트로부터 요청된 변경 대상 업체의 변경 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/updateCompany", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> updateCompany(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyMgmtRequest companyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		CompanyDto companyDto = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("updateCompany() companyMgmtRequest={}", companyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyMgmtRequest.getTxId())) {
				txId = companyMgmtRequest.getTxId();
			}
			
			//	1. 변경 대상 업체 정보가 미 전달 시, 예외 처리 후 작업을 종료한다.
			if(SBNUtils.isNull(companyMgmtRequest.getCompany())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 업체 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			
			// 2. 전달된 요청 정보로부터 변경 대상 업체 정보를 추출한다
			companyDto = companyMgmtRequest.getCompany();
			companyDto.setUpdtSeq(adminAuthData.getClientSeq());
			log.debug("registerNewCompany() companyDto={}", companyDto.toStringInfo());
			
			//	3. 전달된 업체 정보에 대한 변경 작업 수행 결과를 추출한다
			resultFlag = companyMgmtService.updateCompany(companyDto);
			
			//	4. 업체 변경 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 업체 변경 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 변경 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 변경 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "업체 변경 작업을 성공적으로 수행 했습니다.";
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
	 * [ 업체_업체 삭제 ]
	 * 전달된 클라이언트의 업체 삭제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest					클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse					클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	companyMgmtRequest		클라이언트로부터 요청된 삭제 대상 업체의 삭제 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException			클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/deleteCompany", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> deleteCompany(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyMgmtRequest companyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		CompanySC companySC = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("deleteCompany() companyMgmtRequest={}", companyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyMgmtRequest.getTxId())) {
				txId = companyMgmtRequest.getTxId();
			}
			
			//	1. 업체 삭제 작업을 위한 조건 정보 전달 객체를 구축한다
			companySC = new CompanySC();
			if((SBNUtils.isNull(companyMgmtRequest.getCompSeq()) || (companyMgmtRequest.getCompSeq() <= 0)) && 
				SBNUtils.isNull(companyMgmtRequest.getCompCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 삭제 수행 조건 키 정보 미 전달 오류.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			companySC.setCompSeq(companyMgmtRequest.getCompSeq());
			companySC.setCompCd(companyMgmtRequest.getCompCd());
			
			//	2. 전달된 업체 삭제 작업 수행 결과를 추출한다
			resultFlag = companyMgmtService.deleteCompany(companySC);
			
			//	3. 업체 삭제 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 업체 삭제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 삭제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 삭제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "업체 삭제 작업을 정상 수행 했습니다.";
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
	 * [ 업체_업체 서비스 지정 ]
	 * 전달된 클라이언트의 업체 서비스 지정 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest								클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse								클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceCompanyMgmtRequest		클라이언트로부터 요청된 업체의 서비스 지정 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException						클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/assignServiceCompany", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> assignServiceCompany(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceCompanyMgmtRequest serviceCompanyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceCompanySC serviceCompanySC = null;
		List<ServiceCompanyDto> serviceCompanyList = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("assignServiceCompany() serviceCompanyMgmtRequest={}", serviceCompanyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getTxId())) {
				txId = serviceCompanyMgmtRequest.getTxId();
			}
			
			//	1. 서비스 업체 지정 작업을 위한 조건 정보 전달 객체를 구축한다
			if(SBNUtils.isNull(serviceCompanyMgmtRequest.getServiceCompanyList())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 업체 매핑 지정 대상 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			serviceCompanyList = serviceCompanyMgmtRequest.getServiceCompanyList();
			for(int i = 0; i < serviceCompanyList.size(); i++) {
				serviceCompanyList.get(i).setRgstSeq(adminAuthData.getClientSeq());
				log.debug("assignServiceCompany() serviceCompanyList[{}]={}", i, serviceCompanyList.get(i).toStringInfo());
			}
						
			//	2. 전달된 서비스 업체 지정 작업 수행 결과를 추출한다
			resultFlag = companyMgmtService.assignServiceCompany(serviceCompanyList);
			
			//	3. 서비스 업체 지정 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 서비스 업체 지정 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 업체 지정 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 서비스 업체 지정 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "서비스 업체 지정 작업을 정상적으로 수행 했습니다.";
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
	 * [ 업체_업체 서비스 해제 ]
	 * 전달된 클라이언트의 업체 서비스 지정 해제 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceCompanyMgmtRequest	클라이언트로부터 요청된 업체의 서비스 지정 해제 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/mgmt/releaseServiceCompany", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> releaseServiceCompany(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceCompanyMgmtRequest serviceCompanyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		List<ServiceCompanyDto> serviceCompanyKeyList = null;
		Boolean resultFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("releaseServiceCompany() companyMgmtRequest={}", serviceCompanyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getTxId())) {
				txId = serviceCompanyMgmtRequest.getTxId();
			}
			
			//	1. 서비스 업체 지정 해제 작업을 위한 조건 정보 전달 객체를 구축한다
			if(SBNUtils.isNull(serviceCompanyMgmtRequest.getServiceCompanyList())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 업체 매핑 지정 해제 대상 정보가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			serviceCompanyKeyList = serviceCompanyMgmtRequest.getServiceCompanyList();
			for(int i = 0; i < serviceCompanyKeyList.size(); i++) {
				log.debug("releaseServiceCompany() serviceCompanyKeyList[{}]={}", i, serviceCompanyKeyList.get(i).toStringInfo());
			}
			
			//	2. 전달된 서비스 업체 지정 해제 작업 수행 결과를 추출한다			
			resultFlag = companyMgmtService.releaseServiceCompany(serviceCompanyKeyList);
			
			//	3. 서비스 업체 지정 해제 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(!resultFlag) {
				//	3.1. 서비스 업체 지정 해제 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 지정 업체 해제 작업을 실패 했습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 서비스 업체 지정 해제 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "서비스 지정 업체 해제 작업을 정상적으로 수행 했습니다.";
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
	//		업체_업체 코드 중복 여부 체크
	//		업체_업체 목록 조회
	//  	업체_업체 상세 조회
	//  	업체_서비스 업체 목록 조회
	//  	업체_서비스 업체 상세 조회
	/////////////////////////////////////////////////////////////////	
	/**
	 * [ 업체_업체 코드 중복 여부 체크 ]
	 * 전달된 클라이언트의 신규 등록 대상 업체 코드 중복 여부 체크 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceCompanyMgmtRequest	클라이언트로부터 요청된 신규 등록 대상 업체의 업체 코드 중복 여부 체크 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/checkCompanyCdDuplication", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkCompanyCdDuplication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyMgmtRequest companyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		CompanySC companySC = null;
		Boolean duplicationFlag = Boolean.FALSE;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("checkCompanyCdDuplication() companyMgmtRequest={}", companyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyMgmtRequest.getTxId())) {
				txId = companyMgmtRequest.getTxId();
			}
			
			//	1. 신규 등록 대상 업체 코드 중복 여부 체크 작업을 위한 조건 정보 전달 객체를 구축한다
			companySC = new CompanySC();			
			if(SBNUtils.isNull(companyMgmtRequest.getCompCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "중복 여부 체크 대상 업체 코드가 전달되지 않았습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
				return QNPResponseEntityUtils.buildResponseEntityPacket(responsePacketEntity);
			}
			companySC.setCompCd(companyMgmtRequest.getCompCd());
			log.debug("checkCompanyCdDuplication() companySC={}", companySC.toStringInfo());
			
			//	2. 전달된 신규 등록 대상 업체 코드 중복 여부 체크 작업 수행 결과를 추출한다
			duplicationFlag = companyMgmtService.checkCompanyCdDuplication(companySC);
			
			//	3. 신규 등록 대상 업체 코드 중복 여부 체크 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(duplicationFlag) {
				//	3.1. 업체 목록 조회 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "이미 사용 중인 중복된 업체 코드 정보 입니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 신규 등록 대상 업체 코드 중복 여부 체크 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				String resultMessage = "사용 가능 업체 코드 정보 입니다.";
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
	 * [ 업체_업체 목록 조회 ]
	 * 전달된 클라이언트의 업체 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceCompanyMgmtRequest	클라이언트로부터 요청된 업체의 목록 조회 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getCompanyList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCompanyList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyMgmtRequest companyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		CompanySC companySC = null;
		List<CompanyDto> companyList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCompanyList() companyMgmtRequest={}", companyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyMgmtRequest.getTxId())) {
				txId = companyMgmtRequest.getTxId();
			}
			
			//	1. 업체 목록 조회 작업을 위한 조건 정보 전달 객체를 구축한다
			companySC = new CompanySC();
			if(!SBNUtils.isNull(companyMgmtRequest.getUseYn())) {
				companySC.setUseYn(companyMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(companyMgmtRequest.getSubscrStatTp())) {
				companySC.setSubscrStatTp(companyMgmtRequest.getSubscrStatTp());
			}
			if(!SBNUtils.isNull(companyMgmtRequest.getCntrctStatTp())) {
				companySC.setCntrctStatTp(companyMgmtRequest.getCntrctStatTp());
			}
			if(!SBNUtils.isNull(companyMgmtRequest.getCompNm())) {
				companySC.setCompNm(companyMgmtRequest.getCompNm());
			}
			if(!SBNUtils.isNull(companyMgmtRequest.getCompSeq())) {
				companySC.setCompSeq(companyMgmtRequest.getCompSeq());
			}
			if(!SBNUtils.isNull(companyMgmtRequest.getCompCd())) {
				companySC.setCompCd(companyMgmtRequest.getCompCd());
			}
			if(!SBNUtils.isNull(companyMgmtRequest.getBrn())) {
				companySC.setBrn(companyMgmtRequest.getBrn());
			}
			log.debug("getCompanyList() companySC={}", companySC.toStringInfo());
			
			//	2. 전달된 업체 목록 조회 작업 수행 결과를 추출한다
			companyList = companyMgmtService.getCompanyList(companySC);
			
			//	3. 업체 목록 조회 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(companyList)) {
				//	3.1. 업체 목록 조회 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 목록 조회 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(companyList);
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
	 * [ 업체_업체 상세 조회 ]
	 * 전달된 클라이언트의 업체 상세 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceCompanyMgmtRequest	클라이언트로부터 요청된 업체의 상세 조회 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getCompanyDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCompanyDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody CompanyMgmtRequest companyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		CompanySC companySC = null;
		CompanyDto companyDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getCompanyDetail() companyMgmtRequest={}", companyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(companyMgmtRequest.getTxId())) {
				txId = companyMgmtRequest.getTxId();
			}
			
			//	1. 업체 상세 조회 작업을 위한 조건 정보 전달 객체를 구축한다
			companySC = new CompanySC();
			if(!SBNUtils.isNull(companyMgmtRequest.getCompSeq())) {
				companySC.setCompSeq(companyMgmtRequest.getCompSeq());
			}
			if(!SBNUtils.isNull(companyMgmtRequest.getCompCd())) {
				companySC.setCompCd(companyMgmtRequest.getCompCd());
			}
			log.debug("getCompanyDetail() companySC={}", companySC.toStringInfo());
			
			//	2. 전달된 업체 상세 조회 작업 수행 결과를 추출한다
			companyDetail = companyMgmtService.getCompanyDetail(companySC);
			
			//	3. 업체 상세 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(companyDetail)) {
				//	3.1. 업체 상세 조회 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "업체 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 업체 상세 조회 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(companyDetail);
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
	 * [ 업체_서비스 업체 목록 조회 ]
	 * 전달된 클라이언트의 서비스 업체 목록 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceCompanyMgmtRequest	클라이언트로부터 요청된 서비스 업체의 목록 조회 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getServiceCompanyList", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getServiceCompanyList(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceCompanyMgmtRequest serviceCompanyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceCompanySC serviceCompanySC = null;
		List<ServiceCompanyDto> serviceCompanyList = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getServiceCompanyList() serviceCompanyMgmtRequest={}", serviceCompanyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getTxId())) {
				txId = serviceCompanyMgmtRequest.getTxId();
			}
			
			//	1. 서비스 업체 목록 조회 작업을 위한 조건 정보 전달 객체를 구축한다
			serviceCompanySC = new ServiceCompanySC();
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getUseYn())) {
				serviceCompanySC.setUseYn(serviceCompanyMgmtRequest.getUseYn());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getChnlSiteSeq()) && (serviceCompanyMgmtRequest.getChnlSiteSeq() > 0L)) {
				serviceCompanySC.setChnlSiteSeq(serviceCompanyMgmtRequest.getChnlSiteSeq());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getSrvcSeq()) && (serviceCompanyMgmtRequest.getSrvcSeq() > 0L)) {
				serviceCompanySC.setSrvcSeq(serviceCompanyMgmtRequest.getSrvcSeq());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getCompSeq()) && (serviceCompanyMgmtRequest.getCompSeq() > 0L)) {
				serviceCompanySC.setCompSeq(serviceCompanyMgmtRequest.getCompSeq());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getChnlCd())) {
				serviceCompanySC.setChnlCd(serviceCompanyMgmtRequest.getChnlCd());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getSiteCd())) {
				serviceCompanySC.setSiteCd(serviceCompanyMgmtRequest.getSiteCd());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getSrvcCd())) {
				serviceCompanySC.setSrvcCd(serviceCompanyMgmtRequest.getSrvcCd());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getCompCd())) {
				serviceCompanySC.setCompCd(serviceCompanyMgmtRequest.getCompCd());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getChnlNm())) {
				serviceCompanySC.setChnlNm(serviceCompanyMgmtRequest.getChnlNm());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getSiteNm())) {
				serviceCompanySC.setSiteNm(serviceCompanyMgmtRequest.getSiteNm());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getSrvcNm())) {
				serviceCompanySC.setSrvcNm(serviceCompanyMgmtRequest.getSrvcNm());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getCompNm())) {
				serviceCompanySC.setCompNm(serviceCompanyMgmtRequest.getCompNm());
			}
			log.debug("getServiceCompanyList()  serviceCompanySC={}", serviceCompanySC.toStringInfo());
			
			//	2. 전달된 서비스 업체 목록 조회 작업 수행 결과를 추출한다
			serviceCompanyList = companyMgmtService.getServiceCompanyList(serviceCompanySC);
			
			//	3. 서비스 업체 목록 조회 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(serviceCompanyList)) {
				//	3.1. 서비스 업체 목록 조회 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 업체 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 서비스 업체 목록 조회 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(serviceCompanyList);
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
	 * [ 업체_서비스 업체 상세 조회 ]
	 * 전달된 클라이언트의 서비스 업체 상세 조회 요청 전달 정보에 대한 작업 수행 후, 수행 결과 응답 패킷 데이터 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	httpRequest							클라이언트의 요청에 대한 정보를 전달하는 HttpServletRequest 인터페이스 객체
	 * @param 	httpResponse							클라이언트의 요청에 대한 수행 결과 응답을 전달하는 HttpServletResponse 인터페이스 객체
	 * @param 	serviceCompanyMgmtRequest	클라이언트로부터 요청된 서비스 업체 상세 조회 요청 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 클라이언트의 요청 전달 정보에 대한 작업 수행 결과 응답 패킷 객체
	 * @throws 	QNPWebException					클라이언트의 업체 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@RequestMapping(value="/srvc/getServiceCompanyDetail", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getServiceCompanyDetail(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody ServiceCompanyMgmtRequest serviceCompanyMgmtRequest) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.FRAMEWORK_BUSINESS_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	인증 및 세션 처리 객체
		QNPAuthData adminAuthData = new QNPAuthData();	//	세션으로부터 추출한 클라이언트 권한 정보 객체
		adminAuthData.setClientSeq(1L);									// 임시 테스트를 위한 정보 설정
		//	요청 수행 객체
		String txId = SBNUtils.createSystemRandomId(true);
		ServiceCompanySC serviceCompanySC = null;
		ServiceCompanyDto serviceCompanyDetail = null;
		//	응답 전달 객체
		QNPResponseData responseData = null;
		QNPResponseErrorData errorResponseData = null;
		QNPResponsePacketEntityData responsePacketEntity = null;
		try {
			log.debug("getServiceCompanyDetail() serviceCompanyMgmtRequest={}", serviceCompanyMgmtRequest.toStringInfo());
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getTxId())) {
				txId = serviceCompanyMgmtRequest.getTxId();
			}
			
			//	1. 서비스 업체 상세 조회 작업을 위한 조건 정보 전달 객체를 구축한다
			serviceCompanySC = new ServiceCompanySC();
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getChnlSiteSeq()) && (serviceCompanyMgmtRequest.getChnlSiteSeq() > 0L)) {
				serviceCompanySC.setChnlSiteSeq(serviceCompanyMgmtRequest.getChnlSiteSeq());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getSrvcSeq()) && (serviceCompanyMgmtRequest.getSrvcSeq() > 0L)) {
				serviceCompanySC.setSrvcSeq(serviceCompanyMgmtRequest.getSrvcSeq());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getCompSeq()) && (serviceCompanyMgmtRequest.getCompSeq() > 0L)) {
				serviceCompanySC.setCompSeq(serviceCompanyMgmtRequest.getCompSeq());
			}			
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getChnlCd())) {
				serviceCompanySC.setChnlCd(serviceCompanyMgmtRequest.getChnlCd());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getSiteCd())) {
				serviceCompanySC.setSiteCd(serviceCompanyMgmtRequest.getSiteCd());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getSrvcCd())) {
				serviceCompanySC.setSrvcCd(serviceCompanyMgmtRequest.getSrvcCd());
			}
			if(!SBNUtils.isNull(serviceCompanyMgmtRequest.getCompCd())) {
				serviceCompanySC.setCompCd(serviceCompanyMgmtRequest.getCompCd());
			}
			log.debug("getServiceCompanyDetail() serviceCompanySC={}", serviceCompanySC.toStringInfo());
			
			//	2. 전달된 서비스 업체 상세 조회 작업 수행 결과를 추출한다
			serviceCompanyDetail = companyMgmtService.getServiceCompanyDetail(serviceCompanySC);
			
			//	3. 서비스 업체 상세 조회 수행 결과에 대한 클라이언트로의 전달 응답 패킷 객체를 구축한다
			if(SBNUtils.isNull(serviceCompanyDetail)) {
				//	3.1. 서비스 업체 상세 조회 작업 실패 시, 오류 메시지 정의 후 클라이언트로 전달 할 오류 응답 패킷 객체를 구축한다
				reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 업체 정보가 존재 하지 않습니다.";
				errorResponseData = new QNPResponseErrorData(errorCode, errorMessage);
				//	오류 세부 상태 정보를 전달하기 위해 응답 상태를 200으로 하여 오류 메시지를 전달하는 방식으로 처리
				responsePacketEntity = new QNPResponsePacketEntityData(txId, HttpStatus.OK.value(), errorResponseData);
			} else {
				//	3.2. 서비스 업체 상세 조회 작업 정상 수행 시, 정상 메시지를 구성 후 전달 할 응답 패킷 객체를 구축한다
				responseData = new QNPResponseData(serviceCompanyDetail);
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
