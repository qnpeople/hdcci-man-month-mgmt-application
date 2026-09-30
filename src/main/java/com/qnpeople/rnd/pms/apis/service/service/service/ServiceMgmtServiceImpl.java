package com.qnpeople.rnd.pms.apis.service.service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.service.service.mapper.ServiceMgmtMapper;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceDto;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.service.service
 * @Filename		: ServiceMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.30.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 서비스 영역의 서비스 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class ServiceMgmtServiceImpl extends QNPWebBaseServiceImpl implements ServiceMgmtService {

	/* 서비스 관련 클라이언트 요청 수행을 위한 SQL 쿼리 수행 매핑 인터페이스 객체 */
	@Autowired
	private ServiceMgmtMapper serviceMgmtMapper;

	/**
	 * 전달된 조건에 대한 서비스 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				서비스 목록 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 서비스 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<ServiceDto> getServiceList(ServiceSC serviceSC) throws QNPWebException {
		List<ServiceDto> serviceList = new ArrayList<ServiceDto>();
		try {
			serviceList = serviceMgmtMapper.selectServiceList(serviceSC);
			return serviceList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조건에 대한 서비스 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				서비스 상세 정보 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 서비스 상세 정보 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public ServiceDto getServiceDetail(ServiceSC serviceSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		ServiceDto serviceDto = null;
		try {
			if(SBNUtils.isNull(serviceSC.getSrvcSeq()) && SBNUtils.isNull(serviceSC.getSrvcCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 상세 조회 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			serviceDto = serviceMgmtMapper.selectServiceDetail(serviceSC);
			return serviceDto;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 신규 등록 대상 서비스 코드 정보에 대한 중복 여부 체크 작업을 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				신규 등록 대상 서비스 코드 정보에 대한 중복 여부 체크 조건 정보 전달 객체
	 * @return		전달된 신규 등록 서비스 키 정보에 대한 중복 여부 체크 수행 결과 Flag. 중복 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public Boolean isServiceKeyDuplicated(ServiceSC serviceSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Boolean isServiceKeyDuplicated = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(serviceSC) || SBNUtils.isNull(serviceSC.getSrvcCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "서비스 중복 여부 체크 대상 서비스 코드 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			isServiceKeyDuplicated = serviceMgmtMapper.selectServiceCdDuplicationFlag(serviceSC);
			return isServiceKeyDuplicated;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 	전달된 신규 서비스 정보에 대한 등록 작업을 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceDto				신규 등록 대상 서비스 정보 전달 객체
	 * @return		전달된 신규 서비스 정보에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public Boolean registerNewService(ServiceDto serviceDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		ServiceSC serviceSC = null;
		Boolean isServiceKeyDuplicated = Boolean.FALSE;
		Integer insertCount = 0;		
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(serviceDto)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 서비스 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	
			if(SBNUtils.isNull(serviceDto.getSrvcCtgryCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 서비스 카테고리 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(serviceDto.getSrvcCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 서비스 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//
			serviceSC = new ServiceSC();
			serviceSC.setSrvcCtgryCd(serviceDto.getSrvcCtgryCd());
			serviceSC.setSrvcCd(serviceDto.getSrvcCd());
			//	
			isServiceKeyDuplicated = serviceMgmtMapper.selectServiceCdDuplicationFlag(serviceSC);
			if(isServiceKeyDuplicated) {
				reason = QNPReasonCode.FRAMEOWRK_DATABASE_DUPLICATION_KEY_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 서비스 코드 정보 [".concat(serviceSC.getSrvcCd()).concat("] 중복 오류.");
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("registerNewService() isServiceKeyDuplicated={}", isServiceKeyDuplicated);
			//
			insertCount = serviceMgmtMapper.insertNewService(serviceDto);
			if(!SBNUtils.isNull(insertCount) && (insertCount > 0)) {
				resultFlag = Boolean.TRUE;
			}
			log.debug("registerNewService() insertCount=[ {} ]. resultFlag=[ {} ]", insertCount, resultFlag);
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 서비스 정보에 대한 변경 작업 수행을 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceDto				변경 대상 서비스 정보 전달 객체
	 * @return		전달된 서비스 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public Boolean updateService(ServiceDto serviceDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		ServiceSC serviceSC = null;
		Boolean isServiceKeyDuplicated = Boolean.FALSE;
		Integer updateCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(serviceDto)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 서비스 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	
			if(SBNUtils.isNull(serviceDto.getSrvcSeq()) || (serviceDto.getSrvcSeq() == 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 서비스 식별자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//
			serviceSC = new ServiceSC();
			serviceSC.setSrvcCtgryCd(serviceDto.getSrvcCtgryCd());
			serviceSC.setSrvcCd(serviceDto.getSrvcCd());
			//	
			isServiceKeyDuplicated = serviceMgmtMapper.selectServiceCdDuplicationFlag(serviceSC);
			if(isServiceKeyDuplicated) {
				reason = QNPReasonCode.FRAMEOWRK_DATABASE_DUPLICATION_KEY_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 서비스 코드 정보 [".concat(serviceSC.getSrvcCd()).concat("] 중복 오류.");
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("registerNewService() isServiceKeyDuplicated={}", isServiceKeyDuplicated);
			//	
			updateCount = serviceMgmtMapper.updateService(serviceDto);
			if(!SBNUtils.isNull(updateCount) && (updateCount > 0)) {
				resultFlag = Boolean.TRUE;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 삭제 대상 서비스 키 조건 정보에 대한 서비스 정보 삭제 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC					삭제 대상 서비스 키 정보 전달 객체
	 * @return		전달된 삭제 대상 서비스 키 조건 정보에 대한 서비스 정보 삭제 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public Boolean deleteService(ServiceSC serviceSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer deleteCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(serviceSC) || (SBNUtils.isNull(serviceSC.getSrvcSeq()) &&(serviceSC.getSrvcSeq() == 0))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 서비스 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			deleteCount = serviceMgmtMapper.deleteService(serviceSC);
			if(!SBNUtils.isNull(deleteCount) && (deleteCount > 0)) {
				resultFlag = Boolean.TRUE;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
