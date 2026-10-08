package com.qnpeople.rnd.pms.apis.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeDto;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

@Mapper
public interface CompanyEmployeeMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 업체 관리 조건 정보에 대한 해당 업체의 전체 근무자 정보 DB 삭제 작업 후, 삭제 수행 결과 갯수를 전달하는 메소드
	 *
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	companySC			업체 관리 조건 정보 객체
	 * @return		전달된 업체 관리 조건 정보 객체에 대한 업체의 전체 근무자 삭제 작업 수행 결과 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteAllCompanyEmployees(CompanySC companySC) throws QNPWebException;
	
	/**
	 * 전달된 신규 등록 대상 업체 근무자 DB 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeDto			업체 신규 근무자 등록 정보 전달 객체
	 * @return		전달된 신규 등록 대상 업체 근무자 등록 작업 수행 결과 갯수. 정상 수행 시 등록 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertNewCompanyEmployee(EmployeeDto employeeDto) throws QNPWebException;
	
	/**
	 * 전달된 변경 대상 업체 근무자 DB 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeDto			변경 대상 업체 근무자 변경 정보 전달 객체
	 * @return		전달된 변경 대상 업체 근무자 변경 작업 수행 결과 갯수. 정상 수행 시 변경 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateCompanyEmployee(EmployeeDto employeeDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 업체 근무자 삭제 DB 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			삭제 대상 업체 근무자 삭제 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 업체 근무자 삭제 작업 수행 결과 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteCompanyEmployee(EmployeeSC employeeSC) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////		

	/**
	 * 전달된 근무자의 비밀번호 변경 작업 수행 정보에 대한 비밀번호 DB 변경 작업 수행 후 수행 결과 갯수를 전달하는 메소드
	 * - 비밀번호 초기 변경 작업 수행
	 * - 비밀번호 변경 작업 수행 
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			근무자의 비밀번호 변경 작업 수행을 위한 정보 전달 객체
	 * @return		전달된 근무자의 비밀번호 변경 작업 수행 정보에 대한 비밀번호 변경 작업 수행 결과 갯수. 정상 수행 시 수행 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateEmployeeLognPwd(EmployeeSC employeeSC) throws QNPWebException;
	
	/**
	 * 전달된 업체 근무자 로그인 ID 정보 중복 체크 수행 조건에 대한 DB 중복 여부 체크 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			업체 근무자의 로그인 ID 정보에 대한 중복 여부 체크 조건 정보 전달 객체
	 * @return		전달된 업체 근무자 로그인 ID 정보 중복 체크 수행 조건에 대한 중복 여부 체크 결과 Flag. 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean selectEmployeeLoginIdDuplicationFlag(EmployeeSC employeeSC) throws QNPWebException;
	
	/**
	 * 전체 근무자 대상 목록 추출 조건 정보에 대한 결과 목록 객체를 DB 로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			전체 근무자 대상 목록 추출 조건 정보 전달 객체
	 * @return		전체 근무자 대상 목록 추출 조건 정보에 대한 결과 목록 객체
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<EmployeeDto> selectTotalEmployeeList(EmployeeSC employeeSC) throws QNPWebException;

	/**
	 * 업체별 근무자 대상 목록 추출 조건 정보에 대한 결과 목록 객체를 DB 로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			업체별 근무자 대상 목록 추출 조건 정보 전달 객체
	 * @return		업체별 근무자 대상 목록 추출 조건 정보에 대한 결과 목록 객체
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<EmployeeDto> selectCompanyEmployeeList(EmployeeSC employeeSC) throws QNPWebException;
	
	/**
	 * 업체별 근무자 대상 상세 정보 추출 조건 정보에 대한 결과 상세 정보 객체를 DB 로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			업체별 근무자 대상 상세 정보 추출 조건 정보 전달 객체
	 * @return		업체별 근무자 대상 상세 정보 추출 조건 정보에 대한 결과 목록 객체
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public EmployeeDto selectCompanyEmployeeDetail(EmployeeSC employeeSC) throws QNPWebException;
}
