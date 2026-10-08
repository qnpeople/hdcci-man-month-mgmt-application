package com.qnpeople.rnd.pms.apis.company.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.company.model.EmployeeDto;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.company.service
 * @Filename		: CompanyEmployeeMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 업체 근무자 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface CompanyEmployeeMgmtService extends QNPWebBaseService {
	
	/////////////////////////////////////////////////////////////////
	//	관리
	//		업체_업체 근무자 등록
	//  	업체_업체 근무자 변경
	//  	업체_업체 근무자 삭제	=> 리팩토링 예정
	/////////////////////////////////////////////////////////////////			
	/**
	 *	전달된 신규 등록 대상 근무자 정보에 대한 근무자 등록 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeDto			신규 등록 대상 업체 근무자 정보 전달 객체
	 * @return		전달된 신규 등록 대상 업체 근무자 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 근무자의 신규 등록 요청 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerNewCompanyEmployee(EmployeeDto employeeDto) throws QNPWebException;
	
	/**
	 * 	전달된 변경 대상 근무자 정보에 대한 근무자 변경 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeDto			변경 대상 업체 근무자 정보 전달 객체
	 * @return		전달된 변경 대상 업체 근무자 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 근무자의 변경 요청 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateCompanyEmployee(EmployeeDto employeeDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 근무자의 조건 정보에 대한 삭제 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			삭제 대상 업체 근무자 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 근무자의 조건 정보에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 근무자의 삭제 요청 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteCompanyEmployee(EmployeeSC employeeSC) throws QNPWebException; 
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	//		업체_업체 근무자 로그인 ID 중복 체크
	//		업체_업체 근무자 비밀번호 변경
	//		업체_업체 근무자 목록 조회
	//  	업체_업체 근무자 상세 조회
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 업체 근무자 로그인 ID 중복 체크 조건 정보에 대한 중복 체크 수행 작업 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC				업체 근무자의 로그인 ID 중복 체크 수행 조건 정보 전달 객체
	 * @return		전달된 업체 근무자 로그인 ID 중복 체크 조건 정보에 대한 중복 체크 수행 결과 Flag. 중복 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		시스템 전체 등록 근무자의 로그인 ID 중복 여부 체크 요청 작업 처리 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean checkEmployeeLoginIdDuplication(EmployeeSC employeeSC) throws QNPWebException;
	
	/**
	 * 전달된 업체 근무자 로그인 비밀번호 변경 작업 수행 후, 수행 결과를 전달하는 메소드
	 * - 비밀번호 초기 변경 작업 수행
	 * - 비밀번호 변경 작업 수행 
	 *  
	 * @param 	employeeSC			업체 근무자 로그인 비밀번호 변경 작업 수행 전달 정보 객체
	 * @return		전달된 업체 근무자 로그인 비밀번호 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 근무자 로그인 비밀번호 변경 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean changeCompanyEmployeeLoginPwd(EmployeeSC employeeSC) throws QNPWebException;
	
	/**
	 * 전달된 시스템 전체 등록 근무자의 목록 조회 요청에 대한 전체 근무자 목록 객체를 추출 후 목록 결과로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			시스템 전체 등록 근무자의 목록 조회 요청 수행 조건 정보 전달 객체
	 * @return		전달된 시스템 전체 등록 근무자의 목록 조회 요청에 대한 수행 결과 근무자 목록 객체
	 * @throws 	QNPWebException	시스템 전체 등록 근무자의 목록 정보 조회 요청 작업 처리 중 오류 발생 시 예외 처리 Exception
	 */
	public List<EmployeeDto> getTotalEmployeeList(EmployeeSC employeeSC) throws QNPWebException;

	/**
	 * 전달된 업체별 등록 근무자의 목록 조회 요청에 대한 전체 근무자 목록 객체를 추출 후 목록 결과로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			업체별 등록 근무자의 목록 조회 요청 수행 조건 정보 전달 객체
	 * @return		전달된 업체별 등록 근무자의 목록 조회 요청에 대한 수행 결과 근무자 목록 객체
	 * @throws 	QNPWebException	업체별 등록 근무자의 목록 정보 조회 요청 작업 처리 중 오류 발생 시 예외 처리 Exception
	 */
	public List<EmployeeDto> getCompanyEmployeeList(EmployeeSC employeeSC) throws QNPWebException;
	
	/**
	 * 전달된 근무자의 상세 정보 조회 요청에 대한 근무자 상세 정보 객체를 추출 후 결과로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			근무자의 상세 조회 요청 수행 조건 정보 전달 객체
	 * @return		전달된 근무자의 목록 조회 요청에 대한 수행 결과 근무자 목록 객체
	 * @throws 	QNPWebException	근무자의 목록 정보 조회 요청 작업 처리 중 오류 발생 시 예외 처리 Exception
	 */
	public EmployeeDto getCompanyEmployeeDetail(EmployeeSC employeeSC) throws QNPWebException;
}
