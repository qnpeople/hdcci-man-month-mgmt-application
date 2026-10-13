package com.qnpeople.rnd.pms.apis.project.bsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.mapper
 * @Filename		: BaseProjectMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.10.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 기본 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface BaseProjectMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 프로젝트 기본 정보 전달 객체에 대한 신규 프로젝트 기본 정보 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectDto					신규 등록 대상 프로젝트 기본 정보 전달 객체
	 * @return		전달된 프로젝트 기본 정보 전달 객체에 대한 신규 프로젝트 기본 정보 등록 작업 수행 갯수. 정상 등록 시 등록 결과 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertNewProject(ProjectDto projectDto) throws QNPWebException;

	/**
	 * 전달된 프로젝트 기본 정보 전달 객체에 대한 프로젝트 기본 정보 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectDto					변경 대상 프로젝트 기본 정보 전달 객체
	 * @return		전달된 프로젝트 기본 정보 전달 객체에 대한 프로젝트 기본 정보 변경 작업 수행 갯수. 정상 변경 시 변경 결과 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateProject(ProjectDto projectDto) throws QNPWebException;

	/**
	 * 전달된 프로젝트 기본 정보 조건 정보 전달 객체에 대한 프로젝트 기본 정보 삭제 작업 수행 후, 삭제 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					삭제 대상 프로젝트 기본 정보에 대한 조건 정보 전달 객체
	 * @return		전달된 프로젝트 기본 정보 조건 정보 전달 객체에 대한 프로젝트 기본 정보 삭제 작업 수행 갯수. 정상 삭제 시 삭제 결과 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteProject(ProjectSC projectSC) throws QNPWebException; 
	
	/**
	 * 전달된 기본 프로젝트의 신규 사이트 지정 조건에 대한 프로젝트 사이트 지정 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteDto		기본 프로젝트의 신규 사이트 지정 정보 전달 객체
	 * @return		전달된 기본 프로젝트의 신규 사이트 지정 정보에 대한 프로젝트 사이트 지정 작업 수행 결과 갯수. 정상 수행 시 지정 결과 갯수. 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException			기본 프로젝트 사이트 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertNewProjectAssignSite(ProjectChannelSiteDto projectChannelSiteDto) throws QNPWebException;
	
	/**
	 * 전달된 기본 프로젝트의 지정된 사이트 해제 조건에 대한 프로젝트 사이트 해제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC	기본 프로젝트의 지정 사이트 해제 조건 정보 전달 객체
	 * @return		전달된 기본 프로젝트의 지정 사이트 해제 조건 정보에 대한 프로젝트 사이트 지정 해제 작업 수행 결과 갯수. 정상 수행 시 해제 결과 갯수. 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException		기본 프로젝트 사이트 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteProjectAssignedSite(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////		
	/**
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					프로젝트 코드 중복 여부 체크 조건 정보 전달 객체
	 * @return
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean selectProjectCodeDuplication(ProjectSC projectSC) throws QNPWebException;
	
	/**
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC
	 * @return
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectDto> selectProjectList(ProjectSC projectSC) throws QNPWebException;
	
	/**
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC
	 * @return
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectDto selectProjectDetail(ProjectSC projectSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 지정 사이트 목록 정보 추출 조건 전달 정보에 대한 DB 작업 수행 후, 프로젝트 사이트 지정 목록 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC	프로젝트 지정 사이트 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 지정 사이트 목록 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 목록 정보 객체
	 * @throws 	QNPWebException		기본 프로젝트 사이트 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectChannelSiteDto> selectAssignedBaseProjectSiteList(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 지정 사이트 상세 정보 추출 조건 전달 정보에 대한 DB 작업 수행 후, 프로젝트 사이트 지정 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC		프로젝트 지정 사이트 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 지정 사이트 상세 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 상세 정보 객체
	 * @throws 	QNPWebException			기본 프로젝트 사이트 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectChannelSiteDto selectAssignedBaseProjectSiteDetail(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException;
}
