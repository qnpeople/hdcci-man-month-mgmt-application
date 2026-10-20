package com.qnpeople.rnd.pms.apis.project.bsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.mapper
 * @Filename		: ProjectExecuteOrgMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.16.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 수행 업체별 프로젝트 수행 조직도 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 
 *  수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface ProjectExecuteOrgMgmtMapper extends QNPWebBaseMapper {


	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	
	/**
	 * 전달된 프로젝트 수행 업체의 프로젝트 수행 신규 조직도 정보에 대한 DB 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgDto		프로젝트 수행 업체의 프로젝트 수행을 위한 신규 조직도 등록 정보 전달 객체		
	 * @return		전달된 프로젝트 수행 업체의 프로젝트 수행 신규 조직도 정보 등록 작업 수행 결과 갯수. 정상 수행 시 수행 갯수, 그렇지 않으면 0 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertProjectExecuteOrganization(ProjectExecuteOrgDto projectExecuteOrgDto) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체의 프로젝트 수행 변경 대상 조직도 정보에 대한 DB 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgDto		프로젝트 수행 업체의 프로젝트 수행을 위한 변경 대상 조직도 변경 정보 전달 객체		
	 * @return		전달된 프로젝트 수행 업체의 프로젝트 수행 변경 대상 조직도 정보 변경 작업 수행 결과 갯수. 정상 수행 시 수행 갯수, 그렇지 않으면 0 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateProjectExecuteOrganization(ProjectExecuteOrgDto projectExecuteOrgDto) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체의 프로젝트 수행 조직도에 대한 조직도 삭제 조건 정보에 대한 DB 삭제 처리 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		프로젝트 수행 업체의 프로젝트 수행을 위한 조직도의 삭제 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 프로젝트 수행 조직도에 대한 조직도 삭제 조건 정보에 대한 삭제 처리 수행 결과 갯수. 정상 수행 시 수행 갯수, 그렇지 않으면 0 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteProjectExecuteOrganization(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도의 지정 투입 근무자 일괄 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		프로젝트 수행 업체 조직도에 지정되어 있는 투입 근무자 삭제 작업 수행 조건 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도의 지정 투입 근무자 일괄 삭제 작업 수행 결과 갯수, 정상 수행 시 수행 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteAllProjectExecutorOrganizationWorkers(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
	/**
	 * 	전달된 전체 프로젝트 수행 업체 조직도에 대한 트리 목록 정보 추출 조건에 대한 업체의 프로젝트 수행 조직도 트리 목록 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		전체 프로젝트 수행 업체의 조직도 매핑 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 전체 프로젝트 수행 업체 조직도에 대한 트리 목록 정보 추출 조건에 대한 업체의 프로젝트 수행 조직도 트리 목록 객체
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectExecuteOrgDto> selectTotalProjectExecuteOrganizationTreeList(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////			
	/**
	 * 	전달된 프로젝트 수행 업체 조직도에 대한 신규 조직도 등록을 위한 조직도 코드 중복 여부를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		전체 프로젝트 수행 업체의 조직도 코드 중복 여부 체크 수행 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 코드의 중복 여부 체크 수행 조건에 대한 업체의 프로젝트 수행 조직도 코드 중복 여부 체크 결과 Flag. 중복인 경우 true 를, 그렇지 않으면 false 를 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean selectProjectExecuteOrganizationCodeDuplication(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
	/**
	 * 	전달된 사용가능한 프로젝트 수행 업체 조직도에 대한 트리 목록 정보 추출 조건에 대한 업체의 프로젝트 수행 조직도 트리 목록 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		사용가능한 프로젝트 수행 업체의 조직도 매핑 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 사용가능한 프로젝트 수행 업체 조직도에 대한 트리 목록 정보 추출 조건에 대한 업체의 프로젝트 수행 조직도 트리 목록 객체
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectExecuteOrgDto> selectAvailableProjectExecuteOrganizationTreeList(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 상세 정보 추출 조건에 대한 조직도 상세 정보를 DB로부터 추출 후, 결과 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		프로젝트 수행 업체의 조직도의 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 상세 정보 추출 조건에 대한 조직도 상세 정보 객체
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectExecuteOrgDto selectProjectExecuteOrganizationDetail(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException;
	
}
