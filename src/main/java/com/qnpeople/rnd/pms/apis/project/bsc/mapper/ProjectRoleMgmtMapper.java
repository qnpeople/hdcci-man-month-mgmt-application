package com.qnpeople.rnd.pms.apis.project.bsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.mapper
 * @Filename		: ProjectRoleMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.09.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 업체 프로젝트 수행 역할 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface ProjectRoleMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 신규 프로젝트 수행 역할 정보에 대한 DB 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRole				신규 등록 대상 프로젝트 수행 역할 정보 전달 객체 
	 * @return		전달된 신규 프로젝트 수행 역할 정보에 대한 DB 등록 작업 수행 결과 갯수. 정상 수행 시 등록 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertProjectRole(ProjectRoleDto projectRole) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 역할 정보에 대한 DB 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRole				변경 대상 프로젝트 수행 역할 정보 전달 객체 
	 * @return		전달된 프로젝트 수행 역할 정보에 대한 DB 변경 작업 수행 결과 갯수, 정상 수행 시 변경 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateProjectRole(ProjectRoleDto projectRole) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 역할 기본 정보 삭제 조건 정보에 대한 프로젝트 수행 역할 기본 정보 삭제 후, 삭제 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			프로젝트 수행 역할 기본 정보 삭제 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 역할 기본 정보 삭제 조건 정보에 대한 프로젝트 수행 역할 기본 정보 삭제 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteProjectRole(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 역할 수행 투입자 삭제 조건 전달 객체에 대한 투입자 삭제 작업 수행 후 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			프로젝트 역할 수행 투입자 삭제 조건 정보 전달 객체
	 * @return		전달된 프로젝트 역할 수행 투입자 삭제 조건에 대한 투입자 삭제 작업 수행 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteAllProjectRoleWorkers(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 역할 수행 투입자 출퇴근 현황 삭제 조건에 대한 투입자 출퇴근 내역 삭제 수행 후 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			프로젝트 역할 수행 투입자의 출퇴근 현황 삭제 조건 정보 전달 객체
	 * @return		전달된 프로젝트 역할 수행 투입자 출퇴근 현황 삭제 조건에 대한 투입자 출퇴근 내역 삭제 갯수, 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteAllProjectRoleWorkerClickInOuts(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 역할 수행 투입자의 권한 그룹 삭제 조건에 대한 투입자 권한 그룹 삭제 수행 후 삭제 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			프로젝트 수행 역할 투입자 권한 그룹 매핑 정보 삭제 조건 정보 전달 객체
	 * @return		전달된 프로젝트 역할 수행 투입자의 권한 그룹 삭제 조건에 대한 투입자 권한 그룹 삭제 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteAllProjectRoleWorkerAuthGroups(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 역할 근무자의 업무 기준 매핑 정보 삭제 조건에 대한 업무 기준 정보 매핑 삭제 수행 후 수행 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			프로젝트 수행 역할 근무자의 업무 기준 매핑 정보 삭제 조건 전달 객체
	 * @return		전달된 프로젝트 수행 역할 근무자의 업무 기준 매핑 정보 삭제 조건에 대한 업무 기준 정보 매핑 삭제 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 0 전달
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteAllProjectRoleWorkerCriteriaMapping(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 프로젝트 수행 역할 코드 중복 체크 조건에 대한 프로젝트 역할 코드 중복 여부 체크 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			프로젝트 수행 역할 코드 중복 체크 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 역할 코드 중복 체크 조건에 대한 프로젝트 역할 코드 중복 여부 체크 결과 Flag. 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean selectProjectRoleCodeDuplication(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 역할 기본 목록 정보 추출 조건 정보에 대한 프로젝트 수행 역할 정보 목록 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			프로젝트 수행 역할 기본 정보 목록 추출 조건 전달 객체
	 * @return		전달된 프로젝트 수행 역할 기본 목록 정보 추출 조건 정보에 대한 프로젝트 수행 역할 정보 목록 객체
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectRoleDto> selectProjectRoleList(ProjectRoleSC projectRoleSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 역할 기본 상세 정보 추출 조건 정보에 대한 프로젝트 수행 역할 상세 정보 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.09
	 * @param 	projectRoleSC			프로젝트 수행 역할 기본 정보에 대한 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 역할 기본 상세 정보 추출 조건에 대한 프로젝트 수행 역할 상세 정보 객체
	 * @throws 	QNPWebException	프로젝트 수행 역할 기본 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectRoleDto selectProjectRoleDetail(ProjectRoleSC projectRoleSC) throws QNPWebException;
}
