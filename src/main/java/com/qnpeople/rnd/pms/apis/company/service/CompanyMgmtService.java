package com.qnpeople.rnd.pms.apis.company.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.company.model.CompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanySC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.company.service
 * @Filename		: CompanyMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 업체 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface CompanyMgmtService extends QNPWebBaseService {
	
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
	public Boolean registerNewCompany(CompanyDto companyDto) throws QNPWebException;
	
	/**
	 * 전달된 변경 대상 업체 정보에 대한 변경 작업을 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companyDto			변경 대상 업체 정보 전달 객체
	 * @return		전달된 변경 대상 업체 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 변경 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateCompany(CompanyDto companyDto) throws QNPWebException;
	
	/**
	 * 전달된 업체 삭제 조건 정보에 대한 업체 삭제 작업을 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC			업체 삭제 작업 수행 조건 정보 전달 객체
	 * @return		전달된 업체 삭제 조건 정보에 대한 업체 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 삭제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteCompany(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 서비스 업체 지정 대상 정보 목록 객체에 대한 서비스 업체 지정 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanyList		서비스 업체 지정 대상 정보 목록 객체
	 * @return		전달된 서비스 업체 지정 대상 정보 목록 객체에 대한 서비스 업체 지정 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	서비스 업체 지정 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignServiceCompany(List<ServiceCompanyDto> serviceCompanyList) throws QNPWebException;
	
	/**
	 * 전달된 서비스 업체 지정 해제 대상 정보 목록 객체에 대한 서비스 업체 지정 해제 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanyList		서비스 업체 지정 해제 대상 키 정보 목록 객체
	 * @return		서비스 업체 지정 해제 작업 대상 키 정보 목록 객체에 대한 서비스 업체 지정 해제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		서비스 지정 업체 해제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseServiceCompany(List<ServiceCompanyDto> serviceCompanyList) throws QNPWebException;
	
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
	public Boolean checkCompanyCdDuplication(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 업체 목록 추출 조건 정보에 대한 추출 결과 업체 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC			업체 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 업체 목록 정보 추출 조건 정보에 대한 추출 결과 업체 목록 객체
	 * @throws 	QNPWebException	업체 목록 정보 추출 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<CompanyDto> getCompanyList(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 업체 상세 정보 추출 조건 정보에 대한 추출 결과 업체 상세 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC			업체 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 업체 상세 정보 추출 조건 정보에 대한 추출 결과 업체 상세 정보 객체
	 * @throws 	QNPWebException	업체 상세 정보 추출 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public CompanyDto getCompanyDetail(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 서비스 업체 목록 정보 추출 조건 정보에 대한 추출 결과 서비스 업체 목록 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanySC	서비스 업체 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 서비스 업체 목록 정보 추출 조건 정보에 대한 서비스 업체 목록 정보 객체
	 * @throws 	QNPWebException	서비스 업체 목록 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ServiceCompanyDto> getServiceCompanyList(ServiceCompanySC serviceCompanySC) throws QNPWebException;
	
	/**
	 * 전달된 서비스 업체 상세 정보 추출 조건 정보에 대한 서비스 업체 상세 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanySC	서비스 업체 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 서비스 업체 상세 정보 추출 조건 정보에 대한 서비스 업체 상세 정보 객체
	 * @throws 	QNPWebException	서비스 업체 상세 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public ServiceCompanyDto getServiceCompanyDetail(ServiceCompanySC serviceCompanySC) throws QNPWebException;
}
