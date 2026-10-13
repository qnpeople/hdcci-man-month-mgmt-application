package com.qnpeople.rnd.pms.apis.project.bsc.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.service
 * @Filename		: ProjectRoleMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.09.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 업체별 프로젝트 수행 역할 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface ProjectRoleMgmtService extends QNPWebBaseService {

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
	public Boolean registerNewProjectRole(ProjectRoleDto projectRoleDto) throws QNPWebException; 
	
	/**
	 * 전달된 변경 대상 업체별 프로젝트 수행 역할 정보에 대한 변경 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleDto		변경 대상 업체별 프로젝트 수행 역할 정보 전달 객체 
	 * @return		전달된 변경 대상 업체별 프로젝트 수행 역할 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateProjectRole(ProjectRoleDto projectRoleDto) throws QNPWebException; 
	
	/**
	 * 전달된 삭제 대상 업체별 프로젝트 수행 역할 정보에 대한 삭제 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			삭제 대상 업체별 프로젝트 수행 역할 조건 정보 전달 객체 
	 * @return		전달된 삭제 대상 업체별 프로젝트 수행 역할 정보에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteProjectRole(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
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
	public Boolean checkProjectRoleCodeDuplication(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/**
	 * 	전달된 업체별 프로젝트 수행 역할 목록 추출 조건에 대한 업체별 프로젝트 수행 역할 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			업체별 프로젝트 수행 역할 목록 추출 조건 정보 전달 객체
	 * @return		전달된 업체별 프로젝트 수행 역할 목록 추출 조건 정보에 대한 프로젝트 역할 정보 목록 객체
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectRoleDto> getProjectRoleList(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/**
	 * 전달된 업체별 프로젝트 수행 역할 상세 정보 추출 조건 정보에 대한 프로젝트 수행 역할 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC		업체별 프로젝트 역할 목록 추출 조건 정보 전달 객체
	 * @return		전달된 업체별 프로젝트 수행 역할 상세 정보 추출 조건 정보에 대한 프로젝트 수행 역할 상세 정보 객체
	 * @throws 	QNPWebException	업체별 프로젝트 수행 역할 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectRoleDto getProjectRoleDetail(ProjectRoleSC projectRoleSC) throws QNPWebException;
}
