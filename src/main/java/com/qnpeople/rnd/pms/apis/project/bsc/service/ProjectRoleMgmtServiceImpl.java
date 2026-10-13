package com.qnpeople.rnd.pms.apis.project.bsc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.company.mapper.CompanyMgmtMapper;
import com.qnpeople.rnd.pms.apis.company.model.CompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.apis.project.bsc.mapper.ProjectRoleMgmtMapper;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.service
 * @Filename		: ProjectRoleMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.09.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 업체별 프로젝트 수행 역할 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class ProjectRoleMgmtServiceImpl extends QNPWebBaseServiceImpl implements ProjectRoleMgmtService {

	/* 업체별 프로젝트 수행 역할 정보 관리를 위한 DB 처리 수행 인터페이스 객체 */
	@Autowired
	private ProjectRoleMgmtMapper projectRoleMgmtMapper;
	
	/* 업체 정보 관리를 위한 DB 처리 수행 인터페이스 객체 */
	@Autowired
	private CompanyMgmtMapper companyMgmtMapper;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 	전달된 신규 업체별 프로젝트 수행 역할 정보에 대한 등록 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleDto		신규 등록 대상 업체별 프로젝트 수행 역할 정보 전달 객체
	 * @return		전달된 신규 업체별 프로젝트 수행 역할 정보에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerNewProjectRole(ProjectRoleDto projectRoleDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer insertCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 신규 등록 대상 업체 프로젝트 수행 역할 정보 등록 작업을 위한 전달 정보 객체에 대한 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(projectRoleDto)) {
				errorMessage = "업체 프로젝트 수행 역할 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleDto.getCompSeq()) || (projectRoleDto.getCompSeq() <= 0L)) {
				errorMessage = "등록 대상 프로젝트 수행 역할 업체 키 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleDto.getPrjctRoleCd())) {
				errorMessage = "등록 대상 프로젝트 수행 역할 코드 키 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleDto.getPrjctRoleNm())) {
				errorMessage = "등록 대상 프로젝트 수행 역할 명 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 전달된 신규 등록 대상 프로젝트 수행 역할 정보 등록 작업을 수행한다
			insertCount = projectRoleMgmtMapper.insertProjectRole(projectRoleDto);
			if(insertCount > 0) {
				//	2.1. 등록 작업 수행 후 등록 결과 갯수가 0 보다 큰 경우(등록 성공), 결과 Flag 를 true 로 설정한다
				resultFlag = true;
			}
			
			//	3. 최종 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 변경 대상 업체별 프로젝트 수행 역할 정보에 대한 변경 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleDto		변경 대상 업체별 프로젝트 수행 역할 정보 전달 객체 
	 * @return		전달된 변경 대상 업체별 프로젝트 수행 역할 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateProjectRole(ProjectRoleDto projectRoleDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer updateCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 변경 대상 업체 프로젝트 수행 역할 정보 변경 작업을 위한 전달 정보 객체에 대한 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(projectRoleDto)) {
				errorMessage = "업체 프로젝트 수행 역할 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleDto.getCompSeq()) || (projectRoleDto.getCompSeq() <= 0L)) {
				errorMessage = "변경 대상 프로젝트 수행 역할 업체 키 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleDto.getPrjctRoleCd())) {
				errorMessage = "변경 대상 프로젝트 수행 역할 코드 키 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleDto.getPrjctRoleNm())) {
				errorMessage = "변경 대상 프로젝트 수행 역할 명 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 전달된 변경 대상 프로젝트 수행 역할 정보 변경 작업을 수행한다
			updateCount = projectRoleMgmtMapper.updateProjectRole(projectRoleDto);
			if(updateCount > 0) {
				//	2.1. 변경 작업 수행 후 변경 결과 갯수가 0 보다 큰 경우(변경 성공), 결과 Flag 를 true 로 설정한다
				resultFlag = true;
			}
			
			//	3. 최종 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 삭제 대상 업체별 프로젝트 수행 역할 정보에 대한 삭제 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			삭제 대상 업체별 프로젝트 수행 역할 조건 정보 전달 객체 
	 * @return		전달된 삭제 대상 업체별 프로젝트 수행 역할 정보에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteProjectRole(ProjectRoleSC projectRoleSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer deleteCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectRoleSC)) {
				errorMessage = "업체 프로젝트 수행 역할 정보 삭제 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectRoleSC.getCompSeq()) || (projectRoleSC.getCompSeq() <= 0L)) && SBNUtils.isNull(projectRoleSC.getCompCd())) {
				errorMessage = "업체 프로젝트 수행 역할 정보 삭제 업체 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleSC.getPrjctRoleCd())) {
				errorMessage = "업체 프로젝트 수행 역할 정보 삭제 역할 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	2. 
			if((SBNUtils.isNull(projectRoleSC.getCompSeq()) || (projectRoleSC.getCompSeq() <= 0L))) {
				CompanySC companySC = new CompanySC();
				companySC.setCompCd(projectRoleSC.getCompCd());
				CompanyDto companyDetail =  companyMgmtMapper.selectCompanyDetail(companySC);
				if(SBNUtils.isNull(companyDetail)) {
					errorMessage = "업체 프로젝트 수행 역할 업체 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectRoleSC.setCompSeq(companyDetail.getCompSeq());
			}
			log.debug("deleteProjectRole() projectRoleSC={}", projectRoleSC.toStringInfo());
			
			//	3. 전달된 삭제 대상 프로젝트 수행 역할 정보 삭제 작업을 수행한다
			deleteCount = projectRoleMgmtMapper.deleteProjectRole(projectRoleSC);
			if(deleteCount > 0) {
				//	3.1. 삭제 작업 수행 후 삭제 결과 갯수가 0 보다 큰 경우(삭제 성공), 결과 Flag 를 true 로 설정한다
				resultFlag = true;
			}
			
			//	4. 최종 수행 결과 Flag 를 전달 후 작업을 종료한다			
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////			
	/**
	 * 전달된 업체별 프로젝트 수행 역할 코드에 대한 중복 여부 체크 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			업체별 프로젝트 수행 역할 코드 중복 여부 체크 조건 정보 전달 객체
	 * @return		전달된 업체별 프로젝트 수행 역할 코드에 대한 중복 여부 체크 결과 Flag. 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean checkProjectRoleCodeDuplication(ProjectRoleSC projectRoleSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Boolean duplicationFlag =  Boolean.FALSE;
		try {
			//	1. 전달된 업체 프로젝트 수행 역할 코드 중복 체크 작업 수행을 위한 조건 정보에 대한 유효성 체크 및 조건 정보를 재 구성한다
			if(SBNUtils.isNull(projectRoleSC)) {				
				errorMessage = "업체별 프로젝트 역할 코드 중복 체크 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectRoleSC.getCompSeq()) || (projectRoleSC.getCompSeq() <= 0L)) &&
				SBNUtils.isNull(projectRoleSC.getCompCd())) {
				errorMessage = "업체별 프로젝트 역할 코드 중복 체크 업체 식별자 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleSC.getPrjctRoleCd())) {
				errorMessage = "업체별 프로젝트 역할 코드 중복 체크 프로젝트 역할 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleSC.getCompSeq())) {
				CompanySC companySC = new CompanySC();
				companySC.setCompCd(projectRoleSC.getCompCd());
				CompanyDto companyDto = companyMgmtMapper.selectCompanyDetail(companySC);
				if(SBNUtils.isNull(companyDto)) {
					errorMessage = "업체 프로젝트 수행 역할 상세 정보 추출 수행 업체 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectRoleSC.setCompSeq(companyDto.getCompSeq());
			}
			
			//	2. 재 구성된 업체 프로젝트 수행 역할 코드 중복 체크 조건 정보를 이용하여 업체 프로젝트 수행 역할 코드 중복 여부 체크 작업을 수행한다
			duplicationFlag = projectRoleMgmtMapper.selectProjectRoleCodeDuplication(projectRoleSC);
			
			//	3. 최종 수행 결과를 전달 후 작업을 종료한다
			return duplicationFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 	전달된 업체별 프로젝트 수행 역할 목록 추출 조건에 대한 업체별 프로젝트 수행 역할 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			업체별 프로젝트 수행 역할 목록 추출 조건 정보 전달 객체
	 * @return		전달된 업체별 프로젝트 수행 역할 목록 추출 조건 정보에 대한 프로젝트 역할 정보 목록 객체
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectRoleDto> getProjectRoleList(ProjectRoleSC projectRoleSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		List<ProjectRoleDto> projectRoleList = new ArrayList<ProjectRoleDto>();
		try {
			//	1. 업체 프로젝트 수행 역할 목록 조회 수행을 위한 조건 정보 전달 객체에 대한 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(projectRoleSC)) {
				errorMessage = "업체 프로젝트 수행 역할 목록 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(!SBNUtils.isNull(projectRoleSC.getCompCd())) {
				CompanySC companySC = new CompanySC();
				companySC.setCompCd(projectRoleSC.getCompCd());
				CompanyDto companyDto = companyMgmtMapper.selectCompanyDetail(companySC);
				if(SBNUtils.isNull(companyDto)) {
					errorMessage = "업체 프로젝트 수행 역할 상세 정보 추출 수행 업체 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectRoleSC.setCompSeq(companyDto.getCompSeq());
			}
			
			//	2. 전달된 업체 프로젝트 수행 역할 목록 조회 조건 정보를 이용하여 프로젝트 수행 역할 목록 객체를 추출한다
			projectRoleList = projectRoleMgmtMapper.selectProjectRoleList(projectRoleSC);
			
			//	3. 추출된 결과 업체 프로젝트 수행 역할 목록 객체를 전달 후 작업을 종료한다
			return projectRoleList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 업체별 프로젝트 수행 역할 상세 정보 추출 조건 정보에 대한 프로젝트 수행 역할 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC		업체별 프로젝트 역할 목록 추출 조건 정보 전달 객체
	 * @return		전달된 업체별 프로젝트 수행 역할 상세 정보 추출 조건 정보에 대한 프로젝트 수행 역할 상세 정보 객체
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectRoleDto getProjectRoleDetail(ProjectRoleSC projectRoleSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		ProjectRoleDto projectRoleDetail = null;
		try {
			//	1. 업체 프로젝트 수행 역할 상세 정보 조회 수행을 위한 조건 정보에 대한 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(projectRoleSC)) {
				errorMessage = "업체 프로젝트 수행 역할 상세 정보 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectRoleSC.getCompSeq()) || (projectRoleSC.getCompSeq() <= 0L)) && 
				SBNUtils.isNull(projectRoleSC.getCompCd())) {
				errorMessage = "업체 프로젝트 수행 역할 상세 정보 추출 수행 업체 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleSC.getPrjctRoleCd())) {
				errorMessage = "업체 프로젝트 수행 역할 상세 정보 추출 수행 역할 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectRoleSC.getCompSeq())) {
				CompanySC companySC = new CompanySC();
				companySC.setCompCd(projectRoleSC.getCompCd());
				CompanyDto companyDto = companyMgmtMapper.selectCompanyDetail(companySC);
				if(SBNUtils.isNull(companyDto)) {
					errorMessage = "업체 프로젝트 수행 역할 상세 정보 추출 수행 업체 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectRoleSC.setCompSeq(companyDto.getCompSeq());
			}
			
			//	2. 구축된 업체 프로젝트 수행 역할 목록 추출 조건 정보를 이용하여 업체 프로젝트 수행 역할 목록 객체를 추출한다
			projectRoleDetail = projectRoleMgmtMapper.selectProjectRoleDetail(projectRoleSC);
			
			//	3. 최종 추출된 업체 프로젝트 수행 역할 목록 객체를 전달 후 작업을 종료한다
			return projectRoleDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
