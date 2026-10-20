package com.qnpeople.rnd.pms.apis.project.bsc.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.service
 * @Filename		: ProjectExecuteOrgMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.16.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 수행 업체별 수행 조직도 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface ProjectExecuteOrgMgmtService extends QNPWebBaseService {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 정보에 대한 프로젝트 수행 업체 조직도 정보 신규 등록 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgDto		신규 등록 대상 프로젝트 수행 업체 조직도 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 정보에 대한 프로젝트 수행 업체 조직도 정보 신규 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerNewProjectExecutionOrganization(ProjectExecuteOrgDto projectExecuteOrgDto) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 키 정보에 대한 프로젝트 수행 업체 조직도 정보 변경 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgDto		변경 대상 프로젝트 수행 업체 조직도 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 정보에 대한 프로젝트 수행 업체 조직도 정보 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateProjectExecutionOrganization(ProjectExecuteOrgDto projectExecuteOrgDto) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 키 정보에 대한 프로젝트 수행 업체 조직도 정보 삭제 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		삭제 대상 프로젝트 수행 업체 조직도 키 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 키 정보에 대한 프로젝트 수행 업체 조직도 정보 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteProjectExecutionOrganization(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////		
	
	/**
	 * 전달된 신규 등록 대상 프로젝트 업체 조직도 코드 정보에 대한 중복 여부 체크 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC	프로젝트 수행 업체 조직도 신규 등록 시, 조직도 코드에 대한 중복 여부 체크 조건 정보 전달 객체
	 * @return		전달된 신규 등록 대상 프로젝트 업체 조직도 코드 정보에 대한 중복 여부 체크 작업 수행 결과 Flag. 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean checkProjectExecutionOrganizationCodeDuplication(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 트리 목록 정보 추출 조건에 대한 프로젝트 수행 업체 조직도 트리 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		프로젝트 수행 업체 조직도 트리 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 트리 목록 정보 추출 조건에 대한 프로젝트 수행 업체 조직도 트리 목록 객체
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectExecuteOrgDto> getProjectExecutionOrganizationTreeList(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 상세 정보 추출 조건에 대한 프로젝트 수행 업체 조직도 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		프로젝트 수행 업체 조직도 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 상세 정보 추출 조건에 대한 프로젝트 수행 업체 조직도 상세 정보 객체
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectExecuteOrgDto getProjectExecutionOrganizationDetail(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
}
