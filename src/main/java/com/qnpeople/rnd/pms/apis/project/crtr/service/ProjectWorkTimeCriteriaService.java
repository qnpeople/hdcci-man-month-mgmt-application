package com.qnpeople.rnd.pms.apis.project.crtr.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaDto;
import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.crtr.service
 * @Filename		: ProjectWorkTimeCriteriaService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.13.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 근무 시간 기준 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface ProjectWorkTimeCriteriaService extends QNPWebBaseService {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 신규 등록 대상 프로젝트 근무 시간 기준 정보에 대한 등록 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaDto		신규 등록 대상 프로젝트 근무 시간 기준 정보 전달 객체
	 * @return		전달된 신규 등록 대상 프로젝트 근무 시간 기준 정보에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerNewProjectWorkTimeCriteria(WorkTimeCriteriaDto workTimeCriteriaDto) throws QNPWebException;
	
	/**
	 * 전달된 변경 대상 프로젝트 근무 시간 기준 정보에 대한 변경 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaDto		변경 대상 프로젝트 근무 시간 기준 정보 전달 객체
	 * @return		전달된 변경 대상 프로젝트 근무 시간 기준 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateProjectWorkTimeCriteria(WorkTimeCriteriaDto workTimeCriteriaDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 프로젝트 근무 시간 기준 키 정보에 대한 삭제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		삭제 대상 프로젝트 근무 시간 기준 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 프로젝트 근무 시간 기준 키 정보에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteProjectWorkTimeCriteria(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////	
	/**
	 * 전달된 프로젝트 근무 시간 중복 여부 체크 조건에 대한 중복 여부 체크 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 코드 중복 여부 체크 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 근무 시간 중복 여부 체크 조건에 대한 중복 여부 체크 결과 Flag. 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean checkProjectWorkTimeCriteriaCodeDuplication(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException;
	
	/**
	 * 전달된 조건에 대한 프로젝트 근무 시간 기준 정보 목록 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 정보 목록 추출 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 프로젝트 근무 시간 기준 정보 목록 정보 객체
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<WorkTimeCriteriaDto> getProjectWorkTimeCriteriaList(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException;
	
	/**
	 * 전달된 조건에 대한 프로젝트 근무 시간 기준 정보 상세 정보 객체를 추출 후, 추출 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 정보 상세 추출 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 프로젝트 근무 시간 기준 정보 상세 정보 객체
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public WorkTimeCriteriaDto getProjectWorkTimeCriteriaDetail(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException;
}
