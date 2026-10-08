package com.qnpeople.rnd.pms.apis.company.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.company.mapper.CompanyEmployeeMgmtMapper;
import com.qnpeople.rnd.pms.apis.company.mapper.CompanyMgmtMapper;
import com.qnpeople.rnd.pms.apis.company.model.CompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeDto;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;
import com.qnpeople.rnd.pms.utils.QNPAuthUtils;

import kr.co.sbn.platformhub.framework.core.types.SBNUseYnType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.company.service
 * @Filename		: CompanyEmployeeMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 업체 근무자 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class CompanyEmployeeMgmtServiceImpl extends QNPWebBaseServiceImpl implements CompanyEmployeeMgmtService {

	/* 업체 근무자 관리 작업 수행을 위한 DB 작업 수행 인터페이스 객체 */
	@Autowired
	private CompanyEmployeeMgmtMapper companyEmployeeMgmtMapper;
	
	/* 업체 관리 작업 수행을 위한 DB 작업 수행 인터페이스 객체 */
	@Autowired
	private CompanyMgmtMapper companyMgmtMapper;
	
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
	public Boolean registerNewCompanyEmployee(EmployeeDto employeeDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer registerCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 업체 신규 근무자 등록 작업을 위한 근무자 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(employeeDto)) {
				errorMessage = "신규 등록 대상 업체 근무자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 전달된 신규 등록 대상 업체 근무자 정보 객체를 이용하여 신규 근무자 등록 작업을 수행한다
			registerCount = companyEmployeeMgmtMapper.insertNewCompanyEmployee(employeeDto);
			if(registerCount > 0) {
				//	2.1. 등록 작업 수행 결과가 정상 인 경우, 수행 결과 Flag 를 true 로 설정한다
				resultFlag = true;
			}
			
			//	3. 최종 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 	전달된 변경 대상 근무자 정보에 대한 근무자 변경 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeDto			변경 대상 업체 근무자 정보 전달 객체
	 * @return		전달된 변경 대상 업체 근무자 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 근무자의 변경 요청 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateCompanyEmployee(EmployeeDto employeeDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		EmployeeSC employSC = null;
		EmployeeDto detailEmployeeDto = null;
		Integer updateCount = 0;
		Boolean updateResultFlag = Boolean.FALSE;
		try {
			//	1. 업체 변경 대상 근무자 변경 작업을 위한 근무자 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(employeeDto)) {
				errorMessage = "변경 대상 업체 근무자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 근무자 변경 상태 전에, 변경 대상 근무자의 초기 비밀번호 변경 여부가 '미변경' 상태 여부를 체크한다
			employSC = new EmployeeSC();
			employSC.setCompSeq(employeeDto.getCompSeq());
			employSC.setEmpleSeq(employeeDto.getEmpleSeq());
			detailEmployeeDto = companyEmployeeMgmtMapper.selectCompanyEmployeeDetail(employSC);
			if(SBNUtils.isNull(detailEmployeeDto)) {
				//	2.1. 변경 대상 업체 근무자 정보가 미 존재 시, 예외 처리 후 작업을 종료한다
				errorMessage = "변경 대상 업체 근무자 정보 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("updateCompanyEmployee() detailEmployeeDto={}", detailEmployeeDto.toStringInfo());
			
			//	3. 변경 대상 사용자의 초기 비밀번호 미 변경 상태 여부를 체크하고, 유효하지 않은 경우 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(detailEmployeeDto.getInitPwdChngYn())) {
				errorMessage = "업체 근무자 초기 비밀번호 변경 상태 미 정의 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(!SBNUseYnType.isValid(detailEmployeeDto.getInitPwdChngYn())) {
				errorMessage = "업체 근무자 초기 비밀번호 변경 여부 값 포맷 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUseYnType.N.getTypeCode().equals(detailEmployeeDto.getInitPwdChngYn())) {
				errorMessage = "업체 근무자 초기 비밀번호 미 변경 상태 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}			
			
			//	4. 전달된 변경 대상 업체 근무자 정보 객체를 이용하여 근무자 정보 변경 작업을 수행한다
			updateCount = companyEmployeeMgmtMapper.updateCompanyEmployee(employeeDto);
			if(updateCount > 0) {
				//	4.1. 변경 작업 수행 결과가 정상 인 경우, 수행 결과 Flag 를 true 로 설정한다
				updateResultFlag = true;
			}
			
			//	5. 최종 수행 결과 Flag 를 전달 후 작업을 종료한다
			return updateResultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 삭제 대상 근무자의 조건 정보에 대한 삭제 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			삭제 대상 업체 근무자 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 근무자의 조건 정보에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 근무자의 삭제 요청 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteCompanyEmployee(EmployeeSC employeeSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		EmployeeDto deletableEmployDetail = null;
		Integer employeeDeleteCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 업체 근무자 삭제 처리 작업을 위해 전달된 조건 정보에 대한 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(employeeSC)) {
				errorMessage = "업체 근무자 삭제 수행 조건 정보 객체 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(employeeSC.getCompSeq()) || (employeeSC.getCompSeq() <= 0L)) {
				errorMessage = "업체 근무자 삭제를 위한 업체 식별자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(employeeSC.getEmpleSeq()) || (employeeSC.getEmpleSeq() <= 0L)) {
				errorMessage = "업체 근무자 삭제를 위한 근무자 식별자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(employeeSC.getDeltYn()) || !SBNUseYnType.isValid(employeeSC.getDeltYn())) {
				errorMessage = "업체 근무자 삭제 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 전달된 조건에 대한 삭제 대상 업체 근무자에 대한 유효성 체크를 위한 근무자 정보를 추출한다
			deletableEmployDetail = companyEmployeeMgmtMapper.selectCompanyEmployeeDetail(employeeSC);
			if(SBNUtils.isNull(deletableEmployDetail)) {
				errorMessage = "삭제 대상 업체 근무자 정보 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("deleteCompanyEmployee() deletableEmployDetail={}", deletableEmployDetail);
			
			//	3. 전달된 조건 정보에 대한 업체 근무자 정보 삭제 작업을 수행한다
			employeeDeleteCount = companyEmployeeMgmtMapper.deleteCompanyEmployee(employeeSC);
			if(employeeDeleteCount > 0) {
				resultFlag = true;
			}
			
			//	4. 최종 업체 근무자 개별 삭제 수행 결과를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
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
	public Boolean checkEmployeeLoginIdDuplication(EmployeeSC employeeSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	
			if(SBNUtils.isNull(employeeSC)) {
				errorMessage = "업체 근무자 로그인 ID 중복 체크 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(employeeSC.getCompSeq()) || (employeeSC.getCompSeq() < 0)) {
				errorMessage = "업체 근무자 로그인 ID 중복 체크 조건 업체 식별자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(employeeSC.getEmpleLognId())) {
				errorMessage = "업체 근무자 중복 체크 대상 로그인 ID 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//
			resultFlag = companyEmployeeMgmtMapper.selectEmployeeLoginIdDuplicationFlag(employeeSC);
			//
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 업체 근무자 로그인 비밀번호 변경 작업 수행 후, 수행 결과를 전달하는 메소드
	 * - 비밀번호 초기 변경 작업 수행
	 * - 비밀번호 변경 작업 수행 
	 *  
	 * @param 	employeeSC			업체 근무자 로그인 비밀번호 변경 작업 수행 전달 정보 객체
	 * @return		전달된 업체 근무자 로그인 비밀번호 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체 근무자 로그인 비밀번호 변경 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean changeCompanyEmployeeLoginPwd(EmployeeSC employeeSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		EmployeeDto employeeDetail = null;
		Integer updateEmplyLognPwdCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {			
			//	1. 전달된 근무자 로그인 비밀번호 갱신 작업을 위한 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(employeeSC)) {
				errorMessage = "변경 대상 근무자 로그인 비밀번호 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(employeeSC.getCompSeq()) || (employeeSC.getCompSeq() <= 0L)) {
				errorMessage = "근무자 업체 식별자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(employeeSC.getEmpleSeq()) || (employeeSC.getEmpleSeq() <= 0L)) &&
				SBNUtils.isNull(employeeSC.getEmpleLognId())) {
				errorMessage = "근무자 식별자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(employeeSC.getNewEmpleLognPwd())) {
				errorMessage = "근무자 변경 로그인 비밀번호 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 전달된 이전 및 변경 비밀번호에 대한 유효성 체크 작업을 수행한다
			if(!QNPAuthUtils.checkUserLoginPwdValidation(employeeSC.getNewEmpleLognPwd())) {
				errorMessage = "변경 대상 근무자 로그인 비밀번호 생성 정책 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			employeeSC.setEmpleLognPwd(employeeSC.getNewEmpleLognPwd());
			
			//	3. 
			employeeDetail = companyEmployeeMgmtMapper.selectCompanyEmployeeDetail(employeeSC);
			if(SBNUtils.isNull(employeeDetail)) {
				errorMessage = "로그인 비밀번호 변경 근무자 정보 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("changeCompanyEmployeeLoginPwd() employeeDetail={}", employeeDetail.toStringInfo());
			if(employeeSC.getNewEmpleLognPwd().equals(employeeDetail.getEmpleLognPwd())) {
				errorMessage = "이전 근무자 비밀번호와 변경할 비밀번호 동일 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(employeeSC.getEmpleLognId())) {
				employeeSC.setEmpleLognId(employeeDetail.getEmpleLognId());
			}
			log.debug("changeCompanyEmployeeLoginPwd() employeeSC={}", employeeSC.toStringInfo());
			
			//	4. 전달된 근무자 로그인 비밀번호 갱신 작업을 수행한다
			updateEmplyLognPwdCount = companyEmployeeMgmtMapper.updateEmployeeLognPwd(employeeSC);
			if(updateEmplyLognPwdCount > 0) {
				resultFlag = true;
			}
			//	3. 최종 작업 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 시스템 전체 등록 근무자의 목록 조회 요청에 대한 전체 근무자 목록 객체를 추출 후 목록 결과로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			시스템 전체 등록 근무자의 목록 조회 요청 수행 조건 정보 전달 객체
	 * @return		전달된 시스템 전체 등록 근무자의 목록 조회 요청에 대한 수행 결과 근무자 목록 객체
	 * @throws 	QNPWebException	시스템 전체 등록 근무자의 목록 정보 조회 요청 작업 처리 중 오류 발생 시 예외 처리 Exception
	 */
	public List<EmployeeDto> getTotalEmployeeList(EmployeeSC employeeSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		List<EmployeeDto> totalEmployeeList = new ArrayList<EmployeeDto>();
		try {
			//	1. 전체 근무자 목록 정보 추출 작업을 위한 전달 조건 정보 객체 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(employeeSC)) {
				errorMessage = "전체 근무자 목록 추출 조건 전달 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 최종 구축된 전체 근무자 목록 정보 추출 조건 정보를 이용하여 업체별 근무자의 목록 정보를 추출한다
			totalEmployeeList = companyEmployeeMgmtMapper.selectTotalEmployeeList(employeeSC);
			
			//	3. 최종 추출 결과를 전달 후 작업을 종료한다
			return totalEmployeeList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}

	/**
	 * 전달된 업체별 등록 근무자의 목록 조회 요청에 대한 전체 근무자 목록 객체를 추출 후 목록 결과로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			업체별 등록 근무자의 목록 조회 요청 수행 조건 정보 전달 객체
	 * @return		전달된 업체별 등록 근무자의 목록 조회 요청에 대한 수행 결과 근무자 목록 객체
	 * @throws 	QNPWebException	업체별 등록 근무자의 목록 정보 조회 요청 작업 처리 중 오류 발생 시 예외 처리 Exception
	 */
	public List<EmployeeDto> getCompanyEmployeeList(EmployeeSC employeeSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";		
		List<EmployeeDto> companyEmployeeList = new ArrayList<EmployeeDto>();
		try {
			//	1. 업체 근무자 목록 정보 추출 작업을 위한 전달 조건 정보 객체에 대한 유효성 체크 및 추출 작업을 위한 조건 정보를 재 구축한다
			if(SBNUtils.isNull(employeeSC)) {
				//	1.1. 업체 근무자 목록 정보 추출 조건 정보 객체가 미 전달 시, 예외 처리 후 작업을 종료한다
				errorMessage = "업체 근무자 목록 정보 추출 조건 전달 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.2. 업체 근무자 상세 정보 추출을 위한 근무자 업체 키 정보에 대한 유효성 체크 및 추출 작업 수행 조건 정보를 재 구축한다
			if(SBNUtils.isNull(employeeSC.getCompSeq()) || (employeeSC.getCompSeq() <= 0)) {
				if(SBNUtils.isNull(employeeSC.getCompCd())) {
					//	1.2.1. 업체 근무자 상세 정보 추출을 위한 근무자 업체 식별자 정보 또는 업체 코드 정보가 모두 미 전달 시, 예외 처리 후 작업을 종료한다
					errorMessage = "업체 근무자 목록 정보 추출 업체 식별자 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);	
				}
				//	1.2.1. 업체 코드가 전달 시, 업체 코드를 이용하여 업체 상세 정보를 추출 후 추출된 업체 식별자 정보를 근무자 추출 조건 객체에 설정한다
				CompanySC companySC = new CompanySC();
				companySC.setCompCd(employeeSC.getCompCd());
				CompanyDto compayDto = companyMgmtMapper.selectCompanyDetail(companySC); 
				if(SBNUtils.isNull(compayDto)) {
					errorMessage = "업체 정보가 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);	
				}
				employeeSC.setCompSeq(compayDto.getCompSeq());
			}
			
			//	2. 최종 구축된 업체 근무자 목록 정보 추출 조건 정보를 이용하여 업체별 근무자의 목록 정보를 추출한다
			companyEmployeeList = companyEmployeeMgmtMapper.selectCompanyEmployeeList(employeeSC);
			
			//	3. 최종 추출된 업체 근무자 목록 정보 객체를 전달 후 작업을 종료한다
			return companyEmployeeList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 근무자의 상세 정보 조회 요청에 대한 근무자 상세 정보 객체를 추출 후 결과로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.07
	 * @param 	employeeSC			근무자의 상세 조회 요청 수행 조건 정보 전달 객체
	 * @return		전달된 근무자의 목록 조회 요청에 대한 수행 결과 근무자 목록 객체
	 * @throws 	QNPWebException	근무자의 목록 정보 조회 요청 작업 처리 중 오류 발생 시 예외 처리 Exception
	 */
	public EmployeeDto getCompanyEmployeeDetail(EmployeeSC employeeSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		EmployeeDto employeeDetail = null;
		try {
			//	1. 업체 근무자 상세 정보 추출 작업을 위한 전달 조건 정보 객체에 대한 유효성 체크 및 추출 작업을 위한 조건 정보를 재 구축한다
			if(SBNUtils.isNull(employeeSC)) {
				//	1.1. 업체 근무자 상세 정보 추출 조건 정보 객체가 미 전달 시, 예외 처리 후 작업을 종료한다
				errorMessage = "업체 근무자 상세 정보 추출 조건 전달 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.2. 업체 근무자 상세 정보 추출을 위한 근무자 식별자 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(employeeSC.getEmpleSeq()) || (employeeSC.getEmpleSeq() <= 0)) {
				errorMessage = "업체 근무자 상세 정보 추출 업체 근무자 식별자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.3. 업체 근무자 상세 정보 추출을 위한 근무자 업체 키 정보에 대한 유효성 체크 및 추출 작업 수행 조건 정보를 재 구축한다
			if(SBNUtils.isNull(employeeSC.getCompSeq()) || (employeeSC.getCompSeq() <= 0)) {
				if(SBNUtils.isNull(employeeSC.getCompCd())) {
					//	1.3.1. 업체 근무자 상세 정보 추출을 위한 근무자 업체 식별자 정보 또는 업체 코드 정보가 모두 미 전달 시, 예외 처리 후 작업을 종료한다
					errorMessage = "업체 근무자 상세 정보 추출 업체 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);	
				}
				//	1.3.2. 업체 코드가 전달 시, 업체 코드를 이용하여 업체 상세 정보를 추출 후 추출된 업체 식별자 정보를 근무자 추출 조건 객체에 설정한다
				CompanySC companySC = new CompanySC();
				companySC.setCompCd(employeeSC.getCompCd());
				CompanyDto compayDto = companyMgmtMapper.selectCompanyDetail(companySC); 
				if(SBNUtils.isNull(compayDto)) {
					errorMessage = "업체 정보가 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);	
				}
				employeeSC.setCompSeq(compayDto.getCompSeq());
			}
			
			//	2. 최종 구축된 업체 근무자 상세 정보 추출 조건 정보를 이용하여 근무자의 상세 정보를 추출한다
			employeeDetail = companyEmployeeMgmtMapper.selectCompanyEmployeeDetail(employeeSC);

			//	3. 최종 추출된 업체 근무자 상세 정보 객체를 전달 후 작업을 종료한다
			return employeeDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
