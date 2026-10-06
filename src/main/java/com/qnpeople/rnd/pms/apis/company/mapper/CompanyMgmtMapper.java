package com.qnpeople.rnd.pms.apis.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.company.model.CompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanySC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.company.mapper;
 * @Filename		: CompanyMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 업체 영역의 업체 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface CompanyMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 업체 정보에 대한 DB 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companyDto			신규 등록 대상 업체 정보 전달 객체
	 * @return		전달된 업체 정보에 대한 DB 등록 작업 수행 결과 갯수. 정상 수행 시 등록 갯수를, 그렇지 않은 경우 0 을 전달
	 * @throws 	QNPWebException	업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public Integer insertNewCompany(CompanyDto companyDto) throws QNPWebException;
	
	/**
	 * 전달된 업체 정보에 대한 DB 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companyDto			변경 대상 업체 정보 전달 객체
	 * @return		전달된 업체 정보에 대한 DB 변경 작업 수행 결과 갯수. 정상 수행 시 변경 갯수, 그렇지 않은 경우 0 을 전달
	 * @throws 	QNPWebException	업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public Integer updateCompany(CompanyDto companyDto) throws QNPWebException;
	
	/**
	 * 전달된 업체 키 정보에 대한 DB 로부터 의 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC			삭제 대상 업체 키 정보 전달 객체
	 * @return		전달된 업체 키 정보에 대한 DB로부터의 삭제 작업 수행 결과 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 을 전달
	 * @throws 	QNPWebException	업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteCompany(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 서비스 지정 업체 정보 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanyDto		서비스 지정 업체 정보 전달 객체
	 * @return		전달된 서비스 업체 지정 업체 정보에 대한 서비스 업체 정보 등록 처리 갯수 정보, 정상 수행 시 등록 갯수, 그렇지 않은 경우 0 을 전달
	 * @throws 	QNPWebException		업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public Integer insertServiceCompanyMapping(ServiceCompanyDto serviceCompanyDto) throws QNPWebException;
	
	/**
	 * 전달된 서비스 해제 업체 정보 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanyDto		서비스 해제 업체 정보 전달 객체
	 * @return		전달된 서비스 해제 업체 정보에 대한 서비스 업체 정보 삭제 처리 갯수 정보, 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 을 전달
	 * @throws 	QNPWebException		업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteServiceCompanyMapping(ServiceCompanyDto serviceCompanyDto) throws QNPWebException; 
	
	/**
	 * 전달된 업체 서비스 해제 작업 수행 조건 정보에 대한 일괄 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC				서비스 업체 해제 키 정보 전달 객체
	 * @return		전달된 업체 서비스 해제 작업 수행 조건 정보에 대한 일괄 삭제 작업 처리 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 을 전달
	 * @throws 	QNPWebException		업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteAllServiceCompanyMapping(CompanySC companySC) throws QNPWebException; 
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 업체 정보의 업체 코드 정보에 대한 중복 여부 체크 수행 조건 정보에 대한 업체 코드 중복 여부 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC				업체 정보의 업체 코드 정보에 대한 중복 여부 체크 수행 조건 정보 전달 객체
	 * @return		전달된 업체 정보의 업체 코드 정보에 대한 중복 여부 체크 수행 조건 정보에 대한 업체 코드 중복 여부 Flag. 중복 업체 코드 인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public Boolean selectCompnayCodeDuplication(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 업체 정보 목록 정보 추출을 위한 조건 정보에 대한 업체 목록 추출 작업 수행 후, 추출 목록 결과 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC				업체 정보 목록 정보 추출을 위한 조건 정보 전달 객체
	 * @return		전달된 업체 정보 목록 정보 추출을 위한 조건 정보에 대한 업체 목록 추출 결과 객체
	 * @throws 	QNPWebException		업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public List<CompanyDto> selectCompanyList(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 업체 정보 상세 정보 추출을 위한 조건 정보에 대한 업체 상세 정보 추출 작업 수행 후, 추출 상세 결과 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	companySC				업체 정보 상세 정보 추출을 위한 조건 정보 전달 객체
	 * @return		전달된 업체 정보 상세 정보 추출을 위한 조건 정보에 대한 업체 상세 정보 결과 객체
	 * @throws 	QNPWebException		업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public CompanyDto selectCompanyDetail(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 서비스 업체 지정 목록 추출 조건을 위한 조건 정보에 대한 서비스 업체 목록 정보 추출 작업 수행 후, 추출 목록 결과 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanySC		서비스 업체 지정 목록 추출 조건을 위한 조건 정보 전달 객체
	 * @return		전달된 서비스 업체 지정 목록 추출 조건을 위한 조건 정보에 대한 서비스 업체 목록 결과 객체
	 * @throws 	QNPWebException		서비스 업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public List<ServiceCompanyDto> selectServiceCompanyList(ServiceCompanySC serviceCompanySC) throws QNPWebException;
	
	/**
	 * 전달된 서비스 업체 지정 상세 정보 추출을 위한 정보에 대한 서비스 업체 상세 결과 객체 추출 수행 후, 추출 상세 결과 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.04
	 * @param 	serviceCompanySC		서비스 업체 지정 상세 정보 추출을 위한 조건 정보 전달 객체
	 * @return		전달된 서비스 업체 지정 상세 정보 추출을 위한 정보에 대한 서비스 업체 상세 결과 객체
	 * @throws 	QNPWebException		서비스 업체 정보 관리를 위한 DB 작업 수행 중  오류 발생 시 예외 처리 Exception
	 */
	public ServiceCompanyDto selectServiceCompanyDetail(ServiceCompanySC serviceCompanySC) throws QNPWebException;
}
