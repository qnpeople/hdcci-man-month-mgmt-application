package com.qnpeople.rnd.pms.apis.company.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qnpeople.rnd.pms.apis.common.channelsite.mapper.ChannelSiteMgmtMapper;
import com.qnpeople.rnd.pms.apis.common.channelsite.model.ChannelSiteDto;
import com.qnpeople.rnd.pms.apis.common.channelsite.model.ChannelSiteSC;
import com.qnpeople.rnd.pms.apis.company.mapper.CompanyEmployeeMgmtMapper;
import com.qnpeople.rnd.pms.apis.company.mapper.CompanyMgmtMapper;
import com.qnpeople.rnd.pms.apis.company.model.CompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanySC;
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
 * @Package		: com.qnpeople.rnd.pms.apis.company.service
 * @Filename		: CompanyMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 업체 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class CompanyMgmtServiceImpl extends QNPWebBaseServiceImpl implements CompanyMgmtService {

	/* 업체 관리 작업 수행을 위한 DB 수행 SQL 쿼리 매핑 작업을 처리하는 인터페이스 객체 */
	@Autowired
	private CompanyMgmtMapper companyMgmtMapper;
	
	/* 업체에 대한 근무자 관리 작업 수행을 위한 DB 수행 쿼리 매핑 작업 처리 인터페이스 객체 */
	@Autowired
	private CompanyEmployeeMgmtMapper companyEmployeeMgmtMapper;
	
	@Autowired
	private ChannelSiteMgmtMapper channelSiteMgmtMapper;
	
	@Autowired
	private ServiceMgmtMapper serviceMgmtMapper;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 신규 등록 대상 업체 정보에 대한 등록 작업을 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companyDto			신규 등록 대상 업체 정보 객체
	 * @return		전달된 신규 등록 대상 업체 정보에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 정보 등록 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerNewCompany(CompanyDto companyDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer insertCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 신규 등록 대상 업체 정보 전달 객체가 미 전달 시 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(companyDto)) {
				errorMessage = "신규 등록 대상 업체 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 전달된 신규 등록 대상 업체 정보 등록 작업을 수행한다
			insertCount = companyMgmtMapper.insertNewCompany(companyDto);
			if(insertCount > 0) {
				//	2.1. 등록 수행 결과 갯수가 0 보다 큰 경우(등록 성공), 결과 Flag 정보를 true 로 설정한다
				resultFlag = true;
			}
			
			//	3. 최종 신규 업체 등록 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 변경 대상 업체 정보에 대한 변경 작업을 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companyDto			변경 대상 업체 정보 전달 객체
	 * @return		전달된 변경 대상 업체 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 변경 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateCompany(CompanyDto companyDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer updateCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 변경 대상 업체 정보 전달 객체가 미 전달 시 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(companyDto)) {
				errorMessage = "변경 대상 업체 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 전달된 변경 대상 업체 정보 변경 작업을 수행한다
			updateCount = companyMgmtMapper.updateCompany(companyDto);
			if(updateCount > 0) {
				//	2.1. 변경 수행 결과 갯수가 0 보다 큰 경우(수정 성공), 결과 Flag 정보를 true 로 설정한다
				resultFlag = true;
			}
			
			//	3. 최종 업체 변경 작업 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 업체 삭제 조건 정보에 대한 업체 삭제 작업을 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC			업체 삭제 작업 수행 조건 정보 전달 객체
	 * @return		전달된 업체 삭제 조건 정보에 대한 업체 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 삭제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Transactional
	public Boolean deleteCompany(CompanySC companySC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		CompanyDto companyDto = null;
		Integer deleteCount = 0;
		Integer deleteServiceCompanyCount = 0;
		Integer deleteCompanyAllEmployeeCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 업체 정보 삭제 수행 조건 정보 전달 객체가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(companySC)) {
				errorMessage = "업체 정보 삭제 수행 조건 정보 전달 객체 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 전달된 삭제 조건 정보에서 업체 식별자와 업체 코드 정보에 대한 유효성 체크 수행 후, 모두 전달되지 않은 경우 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(companySC.getCompSeq()) || (companySC.getCompSeq() <= 0)) && 
				SBNUtils.isNull(companySC.getCompCd())) {
				errorMessage = "업체 정보 삭제 수행 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}			
			//	3. 업체 삭제 조건 전달 정보를 이용하여 삭제 대상 업체의 상세 정보를 추출하고, 미 존재 시 예외 처리 후 작업을 종료한다
			companyDto = companyMgmtMapper.selectCompanyDetail(companySC);
			if(SBNUtils.isNull(companyDto)) {
				errorMessage = "삭제 대상 업체가 존재하지 않습니다.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("deleteCompany() companyDto=[ {} ]", companyDto.toStringInfo());
			//	3.1. 삭제 조건 중 업체 식별자가 미 전달된 경우, 업체 상세 정보에서 업체 식별자 정보를 삭제 조건 정보에 설정한다
			if(SBNUtils.isNull(companySC.getCompSeq()) || (companySC.getCompSeq() <= 0)) {
				companySC.setCompSeq(companyDto.getCompSeq());
			}
			log.debug("deleteCompany() companySC=[ {} ]", companySC.toStringInfo());
			
			//	4. 최종 구축된 삭제 조건 정보 객체를 이용하여 업체 삭제 작업을 수행 후, 수행 결과 갯수를 전달 받는다
			deleteCount = companyMgmtMapper.deleteCompany(companySC);
			if(deleteCount > 0) {
				//	4.1. 정상 삭제 수행 시, 수행 결과 상태 Flag 를 true 로 설정한다
				resultFlag = true;
				
				//	4.2. 업체에 매핑되어 있는 서비스 업체 매핑 정보 삭제 작업을 수행한다
				deleteServiceCompanyCount = companyMgmtMapper.deleteAllServiceCompanyMapping(companySC);
				log.debug("deleteCompany() deleteServiceCompanyCount=[ {} ]", deleteServiceCompanyCount);
				
				//	4.3. 업체의 전체 근무자 정보를 삭제한다
				deleteCompanyAllEmployeeCount = companyEmployeeMgmtMapper.deleteAllCompanyEmployees(companySC);
				log.debug("deleteCompany() deleteCompanyAllEmployeeCount=[ {} ]", deleteCompanyAllEmployeeCount);
			}
			
			//	5. 최종 업체 삭제 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 서비스 업체 지정 대상 정보 목록 객체에 대한 서비스 업체 지정 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanyList		서비스 업체 지정 대상 정보 목록 객체
	 * @return		전달된 서비스 업체 지정 대상 정보 목록 객체에 대한 서비스 업체 지정 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	서비스 업체 지정 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignServiceCompany(List<ServiceCompanyDto> serviceCompanyList) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer totalServiceCompanyCount = 0;
		Integer insertCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(serviceCompanyList)) {
				errorMessage = "서비스 업체 신규 매핑 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			totalServiceCompanyCount = serviceCompanyList.size();
			log.debug("assignServiceCompany() 전체 등록 대상 서비스 업체 갯수=[ {} ]", totalServiceCompanyCount);
			//
			for(int i =0; i < totalServiceCompanyCount; i++) {
				//	채널 사이트에 대한 존재 여부의 유효성 체크 작업을 수행한다
				ChannelSiteSC chnlSiteSC = new ChannelSiteSC();
				chnlSiteSC.setUseYn("Y");
				chnlSiteSC.setChnlSeq(serviceCompanyList.get(i).getChnlSiteSeq());
				ChannelSiteDto channelSiteDto = channelSiteMgmtMapper.selectChannelSiteDetail(chnlSiteSC);
				if(SBNUtils.isNull(channelSiteDto)) {
					reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "채널 사이트 식별자 [ ".concat(serviceCompanyList.get(i).getChnlSiteSeq().toString()).concat(" ] 에 대한 채널 사이트 정보가 존재하지 않습니다.");
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				
				//	서비스에 대한 존재 여부의 유효성 체크 작업을 수행한다
				ServiceSC serviceSC = new ServiceSC();
				serviceSC.setUseYn("Y");
				serviceSC.setSrvcSeq(serviceCompanyList.get(i).getSrvcSeq());
				ServiceDto serviceDto = serviceMgmtMapper.selectServiceDetail(serviceSC);
				if(SBNUtils.isNull(serviceDto)) {
					reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "서비스 식별자 [ ".concat(serviceCompanyList.get(i).getSrvcSeq().toString()).concat(" ] 에 대한 서비스 정보가 존재하지 않습니다.");
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				
				//	업체에 대한 존재 여부의 유효성 체크 작업을 수행한다
				CompanySC companySC = new CompanySC();
				companySC.setUseYn("Y");
				companySC.setCompSeq(serviceCompanyList.get(i).getCompSeq());
				CompanyDto companyDto = companyMgmtMapper.selectCompanyDetail(companySC);
				if(SBNUtils.isNull(companyDto)) {
					reason = QNPReasonCode.FRAMEWORK_NO_DATA_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "업체 식별자 [ ".concat(serviceCompanyList.get(i).getCompSeq().toString()).concat(" ] 에 대한 업체 정보가 존재하지 않습니다.");
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	
				insertCount +=companyMgmtMapper.insertServiceCompanyMapping(serviceCompanyList.get(i));
			}
			log.debug("assignServiceCompany() insertCount=[ {} ]", insertCount);
			if(insertCount > 0) {
				resultFlag = true;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 서비스 업체 지정 해제 대상 정보 목록 객체에 대한 서비스 업체 지정 해제 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanySCList	서비스 업체 지정 해제 대상 키 정보 목록 객체
	 * @return		서비스 업체 지정 해제 작업 대상 키 정보 목록 객체에 대한 서비스 업체 지정 해제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		서비스 지정 업체 해제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseServiceCompany(List<ServiceCompanyDto> serviceCompanyList) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer totalServiceCompanyKeyCount = 0;
		Integer deleteCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(serviceCompanyList)) {
				errorMessage = "서비스 지정 업체 해제 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			totalServiceCompanyKeyCount = serviceCompanyList.size();
			log.debug("releaseServiceCompany() 전체 해제 대상 서비스 업체 갯수=[ {} ]", totalServiceCompanyKeyCount);
			for(int i =0; i < totalServiceCompanyKeyCount; i++) {
				//	
				deleteCount +=companyMgmtMapper.deleteServiceCompanyMapping(serviceCompanyList.get(i));
			}
			log.debug("releaseServiceCompany() deleteCount=[ {} ]", deleteCount);
			if(deleteCount > 0) {
				resultFlag = true;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 업체 코드 중복 여부 체크 조건 정보에 대한 업체 코드 중복 여부 체크 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC				업체 코드 중복 여부 체크 수행 조건 정보 전달 객체
	 * @return		전달된 업체 코드 중복 여부 체크 수행 조건 정보에 대한 업체 코드 중복 여부 체크 결과 Flag. 중복된 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		업체 코드 중복 체크 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean checkCompanyCdDuplication(CompanySC companySC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		CompanyDto companyDto = null;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 업체 코드 중복 여부 체크를 위한 수행 조건 정보 객체가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(companySC)) {
				errorMessage = "업체 코드 중복 체크 조건 정보 전달 객체 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 업체 코드 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(companySC.getCompCd())) {
				errorMessage = "업체 코드 중복 체크 조건 업체 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	3. 전달된 수행 조건 정보 전달 객체를 이용하여 업체 정보의 세부 정보를 추출하고, 존재 시, 업체 코드 중복 예외 처리 작업 후 작업을 종료한다
			companyDto = companyMgmtMapper.selectCompanyDetail(companySC);			
			if(!SBNUtils.isNull(companyDto)) {
				log.debug("checkCompanyCdDuplication() companyDto={}", companyDto.toStringInfo());
				errorMessage = "이미 사용 중인 중복된 업체 코드 정보 입니다.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	4. 전달된 업체 코드에 대한 기 등록된 정보가 미 존재 시, 중복 여부 체크 작업을 수행한다
			resultFlag = companyMgmtMapper.selectCompnayCodeDuplication(companySC);
			
			//	5. 업체 코드 중복 여부 체크 최종 결과를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 업체 목록 추출 조건 정보에 대한 추출 결과 업체 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC			업체 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 업체 목록 정보 추출 조건 정보에 대한 추출 결과 업체 목록 객체
	 * @throws 	QNPWebException	업체 목록 정보 추출 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<CompanyDto> getCompanyList(CompanySC companySC) throws QNPWebException {
		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
		try {
			companyList = companyMgmtMapper.selectCompanyList(companySC);
			return companyList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 업체 상세 정보 추출 조건 정보에 대한 추출 결과 업체 상세 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC			업체 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 업체 상세 정보 추출 조건 정보에 대한 추출 결과 업체 상세 정보 객체
	 * @throws 	QNPWebException	업체 상세 정보 추출 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public CompanyDto getCompanyDetail(CompanySC companySC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		CompanyDto companyDetail = null;
		try {
			//	1. 업체 상세 정보 추출 작업을 위한 추출 조건 정보 전달 객체 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(companySC)) {
				errorMessage = "업체 상세 정보 조회 조건 정보 전달 객체 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 업체 상세 정보 추출 작업을 위한 추출 조건 키 정보 객체가 모두(업체 식별자 or 업체 코드) 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(companySC.getCompSeq()) || (companySC.getCompSeq() <= 0)) &&
				SBNUtils.isNull(companySC.getCompCd())) {
				errorMessage = "업체 상세 정보 조회 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	3. 전달된 조건 정보 전달 객체를 이용하여 해당 업체의 상세 정보를 추출한다
			companyDetail = companyMgmtMapper.selectCompanyDetail(companySC);
			
			//	4. 최종 추출된 업체 상세 정보 객체를 전달 후 작업을 종료한다
			return companyDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 서비스 업체 목록 정보 추출 조건 정보에 대한 추출 결과 서비스 업체 목록 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanySC	서비스 업체 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 서비스 업체 목록 정보 추출 조건 정보에 대한 서비스 업체 목록 정보 객체
	 * @throws 	QNPWebException	서비스 업체 목록 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ServiceCompanyDto> getServiceCompanyList(ServiceCompanySC serviceCompanySC) throws QNPWebException {
		List<ServiceCompanyDto> serviceCompanyList = new ArrayList<ServiceCompanyDto>();
		try {
			serviceCompanyList = companyMgmtMapper.selectServiceCompanyList(serviceCompanySC);
			return serviceCompanyList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 서비스 업체 상세 정보 추출 조건 정보에 대한 서비스 업체 상세 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanySC	서비스 업체 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 서비스 업체 상세 정보 추출 조건 정보에 대한 서비스 업체 상세 정보 객체
	 * @throws 	QNPWebException	서비스 업체 상세 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public ServiceCompanyDto getServiceCompanyDetail(ServiceCompanySC serviceCompanySC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";		
		ServiceCompanyDto serviceCompanyDetail = null;
		try {
			//	1. 서비스 업체 매핑 상세 정보 추출을 위한 수행 조건 정보 전달 객체 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(serviceCompanySC)) {
				errorMessage = "서비스 매핑 업체 상세 정보 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2. 전달된 조건 정보에 대한 유효성 체크 및 수행 정보를 재 구성한다
			//	2.1. 전달 조건 정보에서 채널 사이트 식별자 정보가 미 전달 시, 채널 및 사이트 코드 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			//	2.2. 전달 조건 정보에서 서비스 식별자 정보가 미 전달 시, 서비스 코드 정보가 미 전달 인 경우 예외 처리 후 작업을 종료한다
			//	2.3. 전달 조건 정보에서 업체 식별자 정보가 미 전달 시, 업체 코드 정보가 미 전달 인 경우 예외 처리 후 작업을 종료한다
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//	2.1. 전달 조건 정보에서 채널 사이트 식별자 정보가 미 전달 시, 채널 및 사이트 코드 정보가 미 전달 인 경우 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(serviceCompanySC.getChnlSiteSeq()) || (serviceCompanySC.getChnlSiteSeq() <= 0L)) {
				//	2.1.1. 채널 사이트 식별자 정보 미 전달 상태에서, 채널 코드 및 사이트 코드 정보가 모두 미 전달 시, 예외 처리 후 작업을 종료한다
				if(SBNUtils.isNull(serviceCompanySC.getChnlCd()) || SBNUtils.isNull(serviceCompanySC.getSiteCd())) {
					errorMessage = "서비스 매핑 업체 상세 정보 추출 채널 사이트 식별자 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	2.1..2. 채널 및 사이트 코드가 전달 시, 전달된 정보를 이용하여 채널 사이트 상세 조회 조건 정보 객체를 구축한다
				ChannelSiteSC channelSiteSC = new ChannelSiteSC();
				channelSiteSC.setChnlCd(serviceCompanySC.getChnlCd());
				channelSiteSC.setSiteCd(serviceCompanySC.getSiteCd());
				//	2.1.3. 구축된 조건 정보 객체를 이용하여 채널 사이트 상세 정보를 추출한다
				ChannelSiteDto channelSiteDto = channelSiteMgmtMapper.selectChannelSiteDetail(channelSiteSC);
				if(SBNUtils.isNull(channelSiteDto)) {
					errorMessage = "요청 키[ chnlCd, siteCd ]에 대한 서비스 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	2.1.4. 추출된 채널 사이트 정보 객체로부터 채널 사이트 식별자 정보를 추출하여 서비스 업체 상세 조회 조건 정보 전달 객체에 설정한다
				serviceCompanySC.setChnlSiteSeq(channelSiteDto.getChnlSiteSeq());
			}
			//	2.2. 전달 조건 정보에서 서비스 식별자 정보가 미 전달 시, 서비스 코드 정보가 미 전달 인 경우 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(serviceCompanySC.getSrvcSeq()) || (serviceCompanySC.getSrvcSeq() <= 0L)) {
				//	2.2.1. 서비스 식별자 정보 미 전달 상태에서, 서비스 코드 정보가 모두 미 전달 시, 예외 처리 후 작업을 종료한다
				if(SBNUtils.isNull(serviceCompanySC.getSrvcCd())) {
					errorMessage = "서비스 매핑 업체 상세 정보 추출 서비스 식별자 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				// 2.2.2. 서비스 코드가 전달 시, 전달된 정보를 이용하여 서비스 상세 조회 조건 정보 객체를 구축한다
				ServiceSC serviceSC = new ServiceSC();
				serviceSC.setSrvcCd(serviceCompanySC.getSrvcCd());
				//	2.2.3. 구축된 조건 정보 객체를 이용하여 서비스 상세 정보를 추출한다
				ServiceDto serviceDto = serviceMgmtMapper.selectServiceDetail(serviceSC);
				if(SBNUtils.isNull(serviceDto)) {
					errorMessage = "요청 키[ srvcCd ]에 대한 서비스 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	2.2.4. 추출된 서비스 정보 객체로부터 서비스 식별자 정보를 추출하여 서비스 업체 상세 조회 조건 정보 전달 객체에 설정한다
				serviceCompanySC.setSrvcSeq(serviceDto.getSrvcSeq());
			}
			//	2.3. 전달 조건 정보에서 업체 식별자 정보가 미 전달 시, 업체 코드 정보가 미 전달 인 경우 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(serviceCompanySC.getCompSeq()) || (serviceCompanySC.getCompSeq() <= 0L)) {
				//	2.3.1. 업체 식별자 정보 미 전달 상태에서, 업체 코드 정보가 모두 미 전달 시, 예외 처리 후 작업을 종료한다
				if(SBNUtils.isNull(serviceCompanySC.getCompCd())) {
					errorMessage = "서비스 매핑 업체 상세 정보 추출 업체 식별자 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	2.3.2. 업체 코드가 전달 시, 전달된 정보를 이용하여 업체 상세 조회 조건 정보 객체를 구축한다
				CompanySC companySC = new CompanySC();
				companySC.setCompCd(serviceCompanySC.getCompCd());
				CompanyDto companyDto = companyMgmtMapper.selectCompanyDetail(companySC);
				if(SBNUtils.isNull(companyDto)) {
					errorMessage = "요청 키[ compCd ]에 대한 업체 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	2.3.4. 추출된 업체 정보 객체로부터 업체 식별자 정보를 추출하여 서비스 업체 상세 조회 조건 정보 전달 객체에 설정한다
				serviceCompanySC.setCompSeq(companyDto.getCompSeq());
			}
			
			//	3. 최종 구축된 서비스 업체 상세 정보 추출 조건 전달 객체를 이용하여 서비스 업체 상세 정보를 추출한다
			serviceCompanyDetail = companyMgmtMapper.selectServiceCompanyDetail(serviceCompanySC);
			
			//	4. 최종 추출된 서비스 업체 매핑 상세 정보 객체를 전달 후 작업을 종료한다
			return serviceCompanyDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
