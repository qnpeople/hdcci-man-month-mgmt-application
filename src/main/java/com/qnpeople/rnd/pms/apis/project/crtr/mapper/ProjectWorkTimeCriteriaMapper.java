package com.qnpeople.rnd.pms.apis.project.crtr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaDto;
import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.crtr.mapper
 * @Filename		: ProjectWorkTimeCriteriaMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.13.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 근무 시간 기준 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface ProjectWorkTimeCriteriaMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////			
	/**
	 * 전달된 신규 등록 대상 프로젝트 근무 시간 기준 정보에 대한 등록 작업 수행 후. 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaDto		신규 등록 대상 프로젝트 근무 시간 기준 정보 전달 객체
	 * @return		전달된 신규 등록 대상 프로젝트 근무 시간 기준 정보에 대한 등록 작업 수행 결과 갯수. 정상 등록 시 등록 갯수. 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertProjectWorkTimeCriteria(WorkTimeCriteriaDto workTimeCriteriaDto) throws QNPWebException;
	
	/**
	 * 전달된 변경할 프로젝트 근무 시간 기준 정보에 대한 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaDto		프로젝트 근무 시간 기준 정보 변경 정보 전달 객체
	 * @return		전달된 변경할 프로젝트 근무 시간 기준 정보에 대한 변경 작업 수행 결과 갯수. 정상 변경 시 변경 갯수. 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateProjectWorkTimeCriteria(WorkTimeCriteriaDto workTimeCriteriaDto) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보에 대한 프로젝트 근무 시간 기준 정보 삭제 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 정보 삭제 수행 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 프로젝트 근무 시간 기준 정보 삭제 수행 결과 갯수. 정상 삭제 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteProjectWorkTimeCriteria(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////	
	
	/**
	 * 전달된 조건 정보에 대한 프로젝트 근무 시간 기준 코드 정보 중복 여부 체크 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 코드 정보 중복 여부 체크 수행 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 프로젝트 근무 시간 기준 코드 정보 중복 여부 체크 결과 Flag. 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean selectProjectWorkTimeCodeDuplication(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보에 대한 프로젝트 근무 시간 기준 목록 정보 객체를 DB 로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 정보 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 프로젝트 근무 시간 기준 목록 정보 객체
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<WorkTimeCriteriaDto> selectProjectWorkTimeCriteriaList(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보에 대한 프로젝트 근무 시간 기준 상세 정보 객체를 DB 로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 정보 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 프로젝트 근무 시간 기준 상세 정보 객체
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public WorkTimeCriteriaDto selectProjectWorkTimeCriteriaDetail(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException;
}
