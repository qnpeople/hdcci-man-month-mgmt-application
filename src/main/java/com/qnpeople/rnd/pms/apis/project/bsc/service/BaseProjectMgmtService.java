package com.qnpeople.rnd.pms.apis.project.bsc.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.service
 * @Filename		: BaseProjectMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.10.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 기본 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface BaseProjectMgmtService extends QNPWebBaseService {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 신규 프로젝트 기본 정보에 대한 프로젝트 기본 정보 등록 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectDto				신규 등록 대상 프로젝트 기본 정보 전달 객체
	 * @return		전달된 신규 프로젝트 기본 정보에 대한 프로젝트 기본 정보 등록 작업 수행 결과 Flag. 정상 등록 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerNewBaseProject(ProjectDto projectDto) throws QNPWebException;

	/**
	 * 전달된 변경 대상 프로젝트 기본 정보에 대한 프로젝트 기본 정보 변경 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectDto				변경 대상 프로젝트 기본 정보 전달 객체
	 * @return		전달된 변경 대상 프로젝트 기본 정보에 대한 프로젝트 기본 정보 변경 작업 수행 결과 Flag. 정상 변경 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateBaseProject(ProjectDto projectDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 프로젝트 조건 정보에 대한 프로젝트 기본 정보 삭제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					프로젝트 기본 정보 삭제 수행 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 프로젝트 조건 정보에 대한 프로젝트 기본 정보 삭제 작업 수행 결과 Flag. 정상 삭제 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteBaseProject(ProjectSC projectSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 신규 사이트 개별 지정 작업 수행 목록에 대한 지정 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	releaseProjectSiteList		프로젝트 신규 사이트 개별 지정 수행 목록 객체
	 * @return		전달된 프로젝트 신규 사이트 개별 지정 작업 수행 목록에 대한 지정 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 기본 정보 신규 사이트 개별 지정 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignBaseProjectToSiteForIndividual(ProjectChannelSiteDto projectChannelSiteDto) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 신규 사이트 다중 지정 작업 수행 목록에 대한 지정 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	assignProjectSiteList			프로젝트 신규 사이트 다중 지정 수행 목록 객체
	 * @return		전달된 프로젝트 신규 사이트 다중 지정 작업 수행 목록에 대한 지정 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 기본 정보 신규 사이트 다중 지정 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignBaseProjectToSiteForList(List<ProjectChannelSiteDto> assignProjectSiteList) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 신규 사이트 개별 지정 해제 작업 수행 목록에 대한 지정 해제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC		프로젝트 신규 사이트 개별 지정 해제 수행 목록 객체
	 * @return		전달된 프로젝트 신규 사이트 개별 지정 해제 작업 수행 목록에 대한 지정 해제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 기본 정보 신규 사이트 개별 지정 해제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseBaseProjectFromSiteForIndividual(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 지정 사이트 다중 해제 수행 조건 목록에 대한 지정 사이트 다중 해제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	releaseProjectSiteKeyList		프로젝트 지정 사이트 다중 해제 수행 조건 목록 객체
	 * @return		전달된 프로젝트 지정 사이트 다중 해제 수행 조건 목록에 대한 사이트 지정 해제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException				프로젝트 기본 정보 지정 사이트 다중 해제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseBaseProjectFromSiteForList(List<ProjectChannelSiteSC> releaseProjectSiteKeyList) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 프로젝트 코드 중복 여부 체크 조건 정보에 대한 프로젝트 코드 중복 체크 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					기본 프로젝트 코드 중복 여부 체크 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 코드 중복 여부 체크 조건 정보에 대한 프로젝트 코드 중복 체크 수행 결과 Flag. 프로젝트 코드 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean checkBaseProjectCodeDuplication(ProjectSC projectSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 목록 추출 조건 정보에 대한 프로젝트 목록 정보 추출 작업 수행 후, 추출 프로젝트 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					기본 프로젝트 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 목록 추출 조건 정보에 대한 프로젝트 목록 정보 추출 결과 목록 객체
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectDto> getBaseProjectList(ProjectSC projectSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 상세 정보 추출 조건 정보에 대한 프로젝트 상세 정보 추출 작업 수행 후, 추출 프로젝트 상세 정보 결과 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					기본 프로젝트 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 상세 정보 추출 조건 정보에 대한 프로젝트 상세 정보 추출 결과 객체
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectDto getBaseProjectDetail(ProjectSC projectSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 지정 사이트 목록 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 목록 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC	프로젝트 지정 사이트 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 지정 사이트 목록 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 목록 정보 객체
	 * @throws 	QNPWebException		프로젝트 기본 정보 지정 사이트 상세 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectChannelSiteDto> getAssignedBaseProjectSiteList(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 지정 사이트 상세 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC		프로젝트 지정 사이트 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 지정 사이트 상세 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 상세 정보 객체
	 * @throws 	QNPWebException			프로젝트 기본 정보 지정 사이트 상세 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectChannelSiteDto getAssignedBaseProjectSiteDetail(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException;
}
