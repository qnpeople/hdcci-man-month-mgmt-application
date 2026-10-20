package com.qnpeople.rnd.pms.apis.project.bsc.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.service
 * @Filename		: ProjectCompBaseOrgMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.16.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 수행 업체 기본 조직 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface ProjectCompBaseOrgMgmtService extends QNPWebBaseService {

	////////////////////////////////////////////////////////////////////////////////////////////
	//	관리
	////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 정보에 대한 신규 프로젝트 수행 업체 기본 조직 정보 등록 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgDto		신규 등록 대상 프로젝트 수행 업체의 기본 조직 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 정보에 대한 프로젝트 수행 업체 기본 조직 정보 등록 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerProjectCompanyBaseOrganization(ProjectCompBaseOrgDto projectCompBaseOrgDto) throws QNPWebException;
	
	/**
	 * 전달된 변경 대상 프로젝트 수행 업체의 기본 조직 정보에 대한 프로젝트 수행 업체 기본 조직 정보 변경 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgDto		변경 대상 프로젝트 수행 업체의 기본 조직 정보 전달 객체
	 * @return		전달된 변경 대상 프로젝트 수행 업체의 기본 조직 정보에 대한 프로젝트 수행 업체 기본 조직 정보 변경 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateProjectCompanyBaseOrganization(ProjectCompBaseOrgDto projectCompBaseOrgDto) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 정보 삭제 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgSC		프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 정보 삭제 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteProjectCompanyBaseOrganization(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 지정 정보에 대한 프로젝트 수행 업체 기본 정보 조직에 대한 프로젝트 매핑 지정 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapList		프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 지정 작업 수행 정보 목록 객체
	 * @return		전달된 지정 정보에 대한 프로젝트 수행 업체 기본 정보 조직에 대한 프로젝트 매핑 지정 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignProjectCompanyOrganizationMap(List<ProjectCompExecuteOrgMapDto> projectCompExecuteOrgMapList) throws QNPWebException;
		
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 지정 정보에 대한 프로젝트 수행 업체 기본 정보 조직에 대한 프로젝트 매핑 변경 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapDto	프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 변경 작업 수행 정보 전달 객체
	 * @return		전달된 변경 정보에 대한 프로젝트 수행 업체 기본 정보 조직에 대한 프로젝트 매핑 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateAssignedProjectCompanyOrganizationMap(ProjectCompExecuteOrgMapDto projectCompExecuteOrgMapDto) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 지정 정보에 대한 프로젝트 수행 업체 기본 정보 조직에 대한 프로젝트 매핑 해제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapSCList		프로젝트 수행 업체 기본 조직에 대한 프로젝트 지정 해제 작업 수행 조건 목록 전달 객체
	 * @return		전달된 조건 정보에 대한 프로젝트 수행 업체 기본 정보 조직에 대한 프로젝트 지정 해제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException							프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseAssignedProjectCompanyOrganizationMap(List<ProjectCompExecuteOrgMapSC> projectCompExecuteOrgMapSCList) throws QNPWebException;
	
	////////////////////////////////////////////////////////////////////////////////////////////
	//	서비스
	////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 목록 객체 추출 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgSC		프로젝트 수행 업체의 기본 조직 목록 조회 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 목록 객체
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectCompBaseOrgDto> getProjectCompanyBaseOrganizationList(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 상세 정보 객체  추출 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgSC		프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 상세 정보 객체.
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectCompBaseOrgDto getProjectCompanyBaseOrganizationDetail(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 조건 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 목록 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapSC		프로젝트별 지정된 프로젝트 수행 업체 기본 조직 매핑 목록 정보 추출 조건 전달 객체
	 * @return		전달된 조건 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 목록 정보 객체
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectCompExecuteOrgMapDto> getAssignedProjectCompanyOrganizationMapList(ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 조건 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapSC		프로젝트별 지정된 프로젝트 수행 업체 기본 조직 매핑 상세 정보 추출 조건 전달 객체
	 * @return		전달된 조건 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 상세 정보 객체
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectCompExecuteOrgMapDto getAssignedProjectCompanyOrganizationMapDetail(ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC) throws QNPWebException;
}
