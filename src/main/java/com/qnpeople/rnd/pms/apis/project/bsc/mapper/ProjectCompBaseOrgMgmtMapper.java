package com.qnpeople.rnd.pms.apis.project.bsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.mapper
 * @Filename		: ProjectCompBaseOrgMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.15.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 수행 업체의 기본 조직 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface ProjectCompBaseOrgMgmtMapper extends QNPWebBaseMapper {

	///////////////////////////////////////////////////////////////////////////////////
	//	관리
	///////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 정보에 대한 신규 프로젝트 수행 업체 기본 조직 정보를 DB에 등록 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgDto		신규 등록 대상 프로젝트 수행 업체의 기본 조직 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 정보에 대한 프로젝트 수행 업체 기본 조직 정보 등록 수행 갯수. 정상 처리 시 처리 갯수. 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertProjectCompanyBaseOrganization(ProjectCompBaseOrgDto projectCompBaseOrgDto) throws QNPWebException;
	
	/**
	 * 전달된 변경 대상 프로젝트 수행 업체의 기본 조직 정보에 대한 프로젝트 수행 업체 기본 조직 정보에 대해 DB 변경 작업 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgDto		변경 대상 프로젝트 수행 업체의 기본 조직 정보 전달 객체
	 * @return		전달된 변경 대상 프로젝트 수행 업체의 기본 조직 정보에 대한 프로젝트 수행 업체 기본 조직 정보 변경 수행 갯수. 정상 처리 시 처리 갯수. 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateProjectCompanyBaseOrganization(ProjectCompBaseOrgDto projectCompBaseOrgDto) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 정보 DB 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgSC		프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 정보 삭제 수행 결과 갯수. 정상 처리 시 처리 갯수. 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteProjectCompanyBaseOrganization(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 지정 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapDto	프로젝트에 지정할 프로젝트 수행 업체 기본 조직 매핑 정보 전달 객체
	 * @return		전달된 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 지정 작업 수행 결과 갯수. 정상 수행 시, 수행 결과 갯수. 그렇지 않으면 0 전달
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertProjectCompanyOrgizationMap(ProjectCompExecuteOrgMapDto projectCompExecuteOrgMapDto) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapDto	프로젝트에 지정할 프로젝트 수행 업체 기본 조직 매핑 변경 정보 전달 객체
	 * @return		전달된 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 변경 작업 수행 결과 갯수. 정상 수행 시, 수행 결과 갯수. 그렇지 않으면 0 전달
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateProjectCompanyOrgizationMap(ProjectCompExecuteOrgMapDto projectCompExecuteOrgMapDto) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 조건 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 키 정보에 대한 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapSC		프로젝트로부터 지정 해제할 프로젝트 수행 업체 기본 조직 매핑 키 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 프로젝트 수행 업체 기본 조직 매핑 키 정보에 대한 삭제 작업 수행 결과 갯수. 수행 결과 갯수. 그렇지 않으면 0 전달
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteProjectCompanyOrgizationMap(ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC) throws QNPWebException;
	
	///////////////////////////////////////////////////////////////////////////////////
	//	서비스
	///////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 목록 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgSC		프로젝트 수행 업체의 기본 조직 목록 조회 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 목록 객체
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectCompBaseOrgDto> selectProjectCompanyBaseOrganizationList(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException;
	
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 상세 정보 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgSC		프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 상세 정보 객체.
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectCompBaseOrgDto selectProjectCompanyBaseOrganizationDetail(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 조건에 대한 프로젝트에 매핑되어 있는 프로젝트 수행 업체 기본 조직의 매핑 정보에 대한 목록 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapSC		프로젝트에 대한 프로젝트 수행 업체 기본 조직의 매핑 목록 추출 조건 정보 전달 객체
	 * @return		전달된 조건에 대해 추출된 프로젝트 수행 업체 기본 조직 매핑 목록 정보 객체
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectCompExecuteOrgMapDto> selectAssignedProjectCompanyOrganizationMapList(ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC) throws QNPWebException;
	
	/**
	 * [ 프로젝트 수행 업체 조직 매핑 ]
	 * 전달된 조건에 대한 프로젝트에 매핑되어 있는 프로젝트 수행 업체 기본 조직의 매핑 정보에 대한 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectCompExecuteOrgMapSC		프로젝트에 대한 프로젝트 수행 업체 기본 조직의 매핑 상세 추출 조건 정보 전달 객체
	 * @return		전달된 조건에 대해 추출된 프로젝트 수행 업체 기본 조직 매핑 상세 정보 객체
	 * @throws 	QNPWebException						프로젝트 수행 업체 기본 조직에 대한 프로젝트 매핑 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectCompExecuteOrgMapDto selectAssignedProjectCompanyOrganizationMapDetail(ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC) throws QNPWebException;
}
