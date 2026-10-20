package com.qnpeople.rnd.pms.apis.project.bsc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.project.bsc.mapper.ProjectExecuteOrgMgmtMapper;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseErrorData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponsePacketEntityData;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;
import com.qnpeople.rnd.pms.utils.QNPResponseEntityUtils;

import kr.co.sbn.platformhub.framework.core.types.SBNUseYnType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.service
 * @Filename		: ProjectExecuteOrgMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.16.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 수행 업체별 수행 조직도 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 *  를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class ProjectExecuteOrgMgmtServiceImpl extends QNPWebBaseServiceImpl implements ProjectExecuteOrgMgmtService {

	/* 프로젝트 수행 업체 조직도 정보 관리를 위한 DB 처리 수행 인터페이스 객체 */
	@Autowired
	private ProjectExecuteOrgMgmtMapper projectExecuteOrgMgmtMapper;

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
	public Boolean registerNewProjectExecutionOrganization(ProjectExecuteOrgDto projectExecuteOrgDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer insertCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 전달된 신규 등록 대상 프로젝트 수행 업체 조직도 정보에 대한 유효성 체크 작업을 수행하고, 유효하지 않은 경우 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectExecuteOrgDto)) {
				errorMessage = "신규 등록 대상 프로젝트 수행 조직도 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgDto.getCompOrgSeq()) || (projectExecuteOrgDto.getCompOrgSeq() <= 0L))) {				
				errorMessage = "신규 등록 대상 프로젝트 수행 조직도 기본 조직 식별자 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectExecuteOrgDto.getPrjctOrgCd())) {
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 조직도 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgDto.getPrjctOrgLvl()) || (projectExecuteOrgDto.getPrjctOrgLvl() <= 0L))) {
				errorMessage = "신규 등록 대상 프로젝트 수행 조직도 업체 기본 조직도 레벨 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectExecuteOrgDto.getPrjctOrgNm())) {
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 조직도 명 정보가 전달되지 않았습니다.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("registerNewProjectExecutionOrganization() projectExecuteOrgDto={}", projectExecuteOrgDto.toStringInfo());
			
			//	2. 전달된 유효한 신규 등록 대상 프로젝트 수행 업체 조직도 DB 등록 작업 후, 수행 결과 갯수를 전달 받는다
			insertCount = projectExecuteOrgMgmtMapper.insertProjectExecuteOrganization(projectExecuteOrgDto);
			if(insertCount > 0) {
				//	2.1. 수행 결과 갯수가 0 보다 큰 경우(정상 수행), 수행 결과 Flag 를 true 로 설정한다
				resultFlag = true;
			}
			
			//	3. 최종 신규 등록 대상 프로젝트 수행 업체 조직도 정보 등록 작업 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 키 정보에 대한 프로젝트 수행 업체 조직도 정보 변경 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.19
	 * @param 	projectExecuteOrgDto		변경 대상 프로젝트 수행 업체 조직도 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 정보에 대한 프로젝트 수행 업체 조직도 정보 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateProjectExecutionOrganization(ProjectExecuteOrgDto projectExecuteOrgDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer updateCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 전달된 변경 대상 프로젝트 수행 업체 조직도 정보에 대한 유효성 체크 작업을 수행하고, 유효하지 않은 경우 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectExecuteOrgDto)) {
				errorMessage = "변경 대상 프로젝트 수행 업체 조직도 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgDto.getPrjctOrgSeq()) || (projectExecuteOrgDto.getPrjctOrgSeq() <= 0L)) &&
			    SBNUtils.isNull(projectExecuteOrgDto.getPrjctOrgCd())) {				
				errorMessage = "변경 대상 프로젝트 수행 조직도 식별 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgDto.getCompOrgSeq()) || (projectExecuteOrgDto.getCompOrgSeq() <= 0L))) {
				errorMessage = "변경 대상 프로젝트 수행 조직도 기본 조직 식별자 정보가 전달되지 않았습니다.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("updateProjectExecutionOrganization() projectExecuteOrgDto={}", projectExecuteOrgDto.toStringInfo());
			
			//	2. 전달된 유효한 변경 대상 프로젝트 수행 업체 조직도 DB 변경 작업 후, 수행 결과 갯수를 전달 받는다
			//	추후 순서 정보 변경 시 순서 변경 작업 추가 예정
			updateCount = projectExecuteOrgMgmtMapper.updateProjectExecuteOrganization(projectExecuteOrgDto);
			if(updateCount > 0) {
				//	2.1. 수행 결과 갯수가 0 보다 큰 경우(정상 수행), 수행 결과 Flag 를 true 로 설정한다
				resultFlag = true;
			}
			
			//	3. 최종 변경 대상 프로젝트 수행 업체 조직도 정보 변경 작업 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 키 정보에 대한 프로젝트 수행 업체 조직도 정보 삭제 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		삭제 대상 프로젝트 수행 업체 조직도 키 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 키 정보에 대한 프로젝트 수행 업체 조직도 정보 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteProjectExecutionOrganization(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		ProjectExecuteOrgSC checkProjectExecuteOrgSC = null;
		Integer deleteAllAssignedWorkerCount = 0;
		Integer deleteProjectExecOranizationCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectExecuteOrgSC)) {
				errorMessage = "삭제 대상 프로젝트 수행 업체 조직도 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgSC.getPrjctOrgSeq()) || (projectExecuteOrgSC.getPrjctOrgSeq() <= 0L)) &&
			    SBNUtils.isNull(projectExecuteOrgSC.getPrjctOrgCd())) {				
				errorMessage = "삭제 대상 프로젝트 수행 조직도 식별 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgSC.getCompOrgSeq()) || (projectExecuteOrgSC.getCompOrgSeq() <= 0L))) {
				errorMessage = "삭제 대상 프로젝트 수행 조직도 기본 조직 식별자 정보가 전달되지 않았습니다.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgSC.getPrjctOrgSeq()) || (projectExecuteOrgSC.getPrjctOrgSeq() <= 0L))) {
				checkProjectExecuteOrgSC = new ProjectExecuteOrgSC();
				checkProjectExecuteOrgSC.setCompOrgSeq(projectExecuteOrgSC.getCompOrgSeq());
				checkProjectExecuteOrgSC.setPrjctOrgCd(projectExecuteOrgSC.getPrjctOrgCd());
				ProjectExecuteOrgDto detailProjectExecOrgDetail = projectExecuteOrgMgmtMapper.selectProjectExecuteOrganizationDetail(checkProjectExecuteOrgSC);
				if(SBNUtils.isNull(detailProjectExecOrgDetail)) {
					errorMessage = "삭제 대상 프로젝트 수행 조직도 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				log.debug("deleteProjectExecutionOrganization() detailProjectExecOrgDetail={}", detailProjectExecOrgDetail.toStringInfo());		
				projectExecuteOrgSC.setPrjctOrgSeq(detailProjectExecOrgDetail.getPrjctOrgSeq());
			}
			log.debug("deleteProjectExecutionOrganization() projectExecuteOrgSC={}", projectExecuteOrgSC.toStringInfo());			
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2. 전달된 삭제 대상 프로젝트 수행 조직도 정보 삭제 작업을 수행한다
			//	2.1. 삭제 대상 프로젝트 수행 조직도에 지정되어 있는 투입 근무자를 삭제 처리 한다
			//	2.2. 삭제 대상 프로젝트 수행 조직도 삭제 작업을 수행 후, 수행 결과 갯수를 전달 받는다
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2.1. 삭제 대상 프로젝트 수행 조직도에 지정되어 있는 투입 근무자를 삭제 처리 한다
			deleteAllAssignedWorkerCount = projectExecuteOrgMgmtMapper.deleteAllProjectExecutorOrganizationWorkers(projectExecuteOrgSC);
			log.debug("deleteProjectExecutionOrganization() deleteAllAssignedWorkerCount={}", deleteAllAssignedWorkerCount);	
			//	2.2. 삭제 대상 프로젝트 수행 조직도 삭제 작업을 수행 후, 수행 결과 갯수를 전달 받는다
			deleteProjectExecOranizationCount = projectExecuteOrgMgmtMapper.deleteProjectExecuteOrganization(projectExecuteOrgSC);
			if(deleteProjectExecOranizationCount > 0) {
				//	2.2.1. 수행 결과 갯수가 0 보다 큰 경우, 수행 결과 Flag 를 true 로 설정한다
				resultFlag = true;
			}
			
			//	3. 최종 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
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
	public Boolean checkProjectExecutionOrganizationCodeDuplication(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectExecuteOrgSC)) {
				errorMessage = "프로젝트 수행 업체 조직도 코드 중복 여부 체크 수행 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgSC.getCompOrgSeq()) || (projectExecuteOrgSC.getCompOrgSeq() <= 0L))) {
				errorMessage = "프로젝트 수행 업체 조직도 코드 중복 여부 체크 수행 업체 조직 식별자 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectExecuteOrgSC.getPrjctOrgCd())) {
				errorMessage = "프로젝트 수행 업체 조직도 코드 중복 여부 체크 수행 조건 조직도 코드 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 
			resultFlag = projectExecuteOrgMgmtMapper.selectProjectExecuteOrganizationCodeDuplication(projectExecuteOrgSC);
			
			//	3. 
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 트리 목록 정보 추출 조건에 대한 프로젝트 수행 업체 조직도 트리 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		프로젝트 수행 업체 조직도 트리 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 트리 목록 정보 추출 조건에 대한 프로젝트 수행 업체 조직도 트리 목록 객체
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectExecuteOrgDto> getProjectExecutionOrganizationTreeList(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		SBNUseYnType useYnType = null;
		List<ProjectExecuteOrgDto> projectExecutionOrgTreeList = new ArrayList<ProjectExecuteOrgDto>();
		try {
			//	1. 전달된 프로젝트 수행 업체 조직도 트리 목록 객체 추출을 위한 조건 정보 전달 객체에 대한 유효성 체크 작업을 수행하고, 유효하지 않은 경우 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectExecuteOrgSC)) {
				errorMessage = "프로젝트 수행 업체 조직도 트리 목록 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.1. 전달된 사용 여부 조건 정보에 대한 전체 또는 사용 가능 프로젝트 수행 업체 조직도 트리 목록 여부를 결정한다
			if(!SBNUtils.isNull(projectExecuteOrgSC.getUseYn()) && SBNUseYnType.isValid(projectExecuteOrgSC.getUseYn())) {
				useYnType = SBNUseYnType.getUseYnType(projectExecuteOrgSC.getUseYn());
			}
			//	1.2. 프로젝트 수행 업체의 조직도 트리 목록 객체 추출을 위한 업체 조직도 식별자 정보에 대한 유효성 체크를 수행하고, 유효하지 않은 경우 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectExecuteOrgSC.getCompOrgSeq()) || (projectExecuteOrgSC.getCompOrgSeq() <= 0))) {
				errorMessage = "프로젝트 수행 업체 조직도 트리 목록 추출 업체 조직 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("getProjectExecutionOrganizationTreeList() useYnType={}", useYnType);
			log.debug("getProjectExecutionOrganizationTreeList() projectExecuteOrgSC={}", projectExecuteOrgSC.toStringInfo());
			
			//	2. 구축된 조건 정보를 이용하여 프로젝트 수행 업체 조직도 트리 목록 추출 작업을 수행한다
			if(!SBNUtils.isNull(useYnType) && SBNUseYnType.Y.equals(useYnType)) {
				log.debug("getProjectExecutionOrganizationTreeList() 사용가능 프로젝트 수행 업체 조직도 트리 목록 추출 수행..");
				projectExecutionOrgTreeList = projectExecuteOrgMgmtMapper.selectAvailableProjectExecuteOrganizationTreeList(projectExecuteOrgSC);
			} else {
				log.debug("getProjectExecutionOrganizationTreeList() 전체 프로젝트 수행 업체 조직도 트리 목록 추출 수행..");
				projectExecutionOrgTreeList = projectExecuteOrgMgmtMapper.selectTotalProjectExecuteOrganizationTreeList(projectExecuteOrgSC);
			}
			
			//	3. 추출된 최종 프로젝트 수행 업체 조직도 트리 목록 객체를 전달 후 작업을 종료한다
			return projectExecutionOrgTreeList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 수행 업체 조직도 상세 정보 추출 조건에 대한 프로젝트 수행 업체 조직도 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.18
	 * @param 	projectExecuteOrgSC		프로젝트 수행 업체 조직도 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체 조직도 상세 정보 추출 조건에 대한 프로젝트 수행 업체 조직도 상세 정보 객체
	 * @throws 	QNPWebException			프로젝트 수행 업체 조직도 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectExecuteOrgDto getProjectExecutionOrganizationDetail(ProjectExecuteOrgSC projectExecuteOrgSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		ProjectExecuteOrgDto projectExecutionOrgDetail = null;
		try {
			//	1. 
			if(SBNUtils.isNull(projectExecuteOrgSC)) {
				errorMessage = "프로젝트 수행 업체 조직도 상세 정보 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgSC.getPrjctOrgSeq()) || (projectExecuteOrgSC.getPrjctOrgSeq() <= 0)) &&
			    SBNUtils.isNull(projectExecuteOrgSC.getPrjctOrgCd())) {
				errorMessage = "프로젝트 수행 업체 조직도 상세 정보 추출 조건 프로젝트 조직 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectExecuteOrgSC.getCompOrgSeq()) || (projectExecuteOrgSC.getCompOrgSeq() <= 0L))) {
				errorMessage = "프로젝트 수행 업체 조직도 상세 정보 추출 조건 업체 조직 식별자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 
			projectExecutionOrgDetail = projectExecuteOrgMgmtMapper.selectProjectExecuteOrganizationDetail(projectExecuteOrgSC);
			
			//	3. 
			return projectExecutionOrgDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
