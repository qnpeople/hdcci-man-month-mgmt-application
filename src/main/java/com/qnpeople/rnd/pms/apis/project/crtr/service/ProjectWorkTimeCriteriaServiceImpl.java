package com.qnpeople.rnd.pms.apis.project.crtr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.project.crtr.mapper.ProjectWorkTimeCriteriaMapper;
import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaDto;
import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.crtr.service
 * @Filename		: ProjectWorkTimeCriteriaServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.13.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 근무 시간 기준 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class ProjectWorkTimeCriteriaServiceImpl extends QNPWebBaseServiceImpl implements ProjectWorkTimeCriteriaService {

	/* 프로젝트 근무 시간 기준 정보 관리를 위한 DB 작업을 수행하는 인터페이스 객체 */
	@Autowired
	private ProjectWorkTimeCriteriaMapper projectWorkTimeCriteriaMapper;
	
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
	public Boolean registerNewProjectWorkTimeCriteria(WorkTimeCriteriaDto workTimeCriteriaDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer insertCount = 0;
		Boolean insertResultFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(workTimeCriteriaDto)) {
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(workTimeCriteriaDto.getWrkTmCrtrCd())) {
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 코드 정보가 미 정의 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(workTimeCriteriaDto.getWrkTmMeasCrtrTp())) {
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 정보 유형이 미 정의 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(workTimeCriteriaDto.getWrkTmMeasCrtrNm())) {
				errorMessage = "신규 등록 대상 프로젝트 근무 시간 기준 정보 명이 미 정의 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("registerNewProjectWorkTimeCriteria() workTimeCriteriaDto={}", workTimeCriteriaDto.toStringInfo());
			
			//	2. 
			insertCount = projectWorkTimeCriteriaMapper.insertProjectWorkTimeCriteria(workTimeCriteriaDto);
			if(insertCount > 0) {
				insertResultFlag = true;
			}
			
			//	3. 
			return insertResultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 변경 대상 프로젝트 근무 시간 기준 정보에 대한 변경 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaDto		변경 대상 프로젝트 근무 시간 기준 정보 전달 객체
	 * @return		전달된 변경 대상 프로젝트 근무 시간 기준 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateProjectWorkTimeCriteria(WorkTimeCriteriaDto workTimeCriteriaDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer updateCount = 0;
		Boolean updateResultFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(workTimeCriteriaDto)) {
				errorMessage = "변경 대상 프로젝트 근무 시간 기준 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(workTimeCriteriaDto.getWrkTmCrtrSeq()) || (workTimeCriteriaDto.getWrkTmCrtrSeq() <= 0L)) &&
				SBNUtils.isNull(workTimeCriteriaDto.getWrkTmCrtrCd())) {
				errorMessage = "변경 대상 프로젝트 근무 시간 기준 코드 정보가 미 정의 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(workTimeCriteriaDto.getWrkTmMeasCrtrTp())) {
				errorMessage = "변경 대상 프로젝트 근무 시간 기준 정보 유형이 미 정의 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(workTimeCriteriaDto.getWrkTmMeasCrtrNm())) {
				errorMessage = "변경 대상 프로젝트 근무 시간 기준 정보 명이 미 정의 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("updateProjectWorkTimeCriteria() workTimeCriteriaDto={}", workTimeCriteriaDto.toStringInfo());
			
			//	2. 
			updateCount = projectWorkTimeCriteriaMapper.updateProjectWorkTimeCriteria(workTimeCriteriaDto);
			if(updateCount > 0) {
				updateResultFlag = true;
			}
			
			//	3. 
			return updateResultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 삭제 대상 프로젝트 근무 시간 기준 키 정보에 대한 삭제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		삭제 대상 프로젝트 근무 시간 기준 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 프로젝트 근무 시간 기준 키 정보에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteProjectWorkTimeCriteria(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer deleteCount = 0;
		Boolean deleteResultFlag = Boolean.FALSE;
		try {
			//	
			if(SBNUtils.isNull(workTimeCriteriaSC)) {
				errorMessage = "프로젝트 근무 시간 기준 코드 삭제 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(workTimeCriteriaSC.getWrkTmCrtrSeq()) || (workTimeCriteriaSC.getWrkTmCrtrSeq() <= 0L)) &&
			   SBNUtils.isNull(workTimeCriteriaSC.getWrkTmCrtrCd())) {
				errorMessage = "프로젝트 근무 시간 기준 코드 삭제 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	
			deleteCount = projectWorkTimeCriteriaMapper.deleteProjectWorkTimeCriteria(workTimeCriteriaSC);
			if(deleteCount > 0) {
				deleteResultFlag = true;
			}
			
			//	
			return deleteResultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
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
	public Boolean checkProjectWorkTimeCriteriaCodeDuplication(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Boolean duplicationResultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(workTimeCriteriaSC)) {
				errorMessage = "프로젝트 근무 시간 기준 코드 중복 체크 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(workTimeCriteriaSC.getWrkTmCrtrCd())) {
				errorMessage = "프로젝트 근무 시간 기준 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	
			duplicationResultFlag = projectWorkTimeCriteriaMapper.selectProjectWorkTimeCodeDuplication(workTimeCriteriaSC);
			
			//	
			return duplicationResultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조건에 대한 프로젝트 근무 시간 기준 정보 목록 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 정보 목록 추출 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 프로젝트 근무 시간 기준 정보 목록 정보 객체
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<WorkTimeCriteriaDto> getProjectWorkTimeCriteriaList(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		List<WorkTimeCriteriaDto> resultWorkTimeCriteriaList = new ArrayList<WorkTimeCriteriaDto>();
		try {
			if(SBNUtils.isNull(workTimeCriteriaSC)) {
				errorMessage = "프로젝트 근무 시간 기준 정보 목록 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	
			resultWorkTimeCriteriaList = projectWorkTimeCriteriaMapper.selectProjectWorkTimeCriteriaList(workTimeCriteriaSC);
			
			return resultWorkTimeCriteriaList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조건에 대한 프로젝트 근무 시간 기준 정보 상세 정보 객체를 추출 후, 추출 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.13
	 * @param 	workTimeCriteriaSC		프로젝트 근무 시간 기준 정보 상세 추출 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 프로젝트 근무 시간 기준 정보 상세 정보 객체
	 * @throws 	QNPWebException		프로젝트 근무 시간 기준 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public WorkTimeCriteriaDto getProjectWorkTimeCriteriaDetail(WorkTimeCriteriaSC workTimeCriteriaSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		WorkTimeCriteriaDto resultWorkTimeCriteriaDetail = null;
		try {
			//	
			if(SBNUtils.isNull(workTimeCriteriaSC)) {
				errorMessage = "프로젝트 근무 시간 기준 상세 정보 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(workTimeCriteriaSC.getWrkTmCrtrSeq()) || (workTimeCriteriaSC.getWrkTmCrtrSeq() <= 0L)) &&
				SBNUtils.isNull(workTimeCriteriaSC.getWrkTmCrtrCd())) {
				errorMessage = "프로젝트 근무 시간 기준 상세 정보 추출 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	
			resultWorkTimeCriteriaDetail = projectWorkTimeCriteriaMapper.selectProjectWorkTimeCriteriaDetail(workTimeCriteriaSC);
			
			//	
			return resultWorkTimeCriteriaDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
