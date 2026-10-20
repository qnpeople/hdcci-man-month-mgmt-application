package com.qnpeople.rnd.pms.apis.project.bsc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.company.mapper.CompanyMgmtMapper;
import com.qnpeople.rnd.pms.apis.company.model.CompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.apis.project.bsc.mapper.BaseProjectMgmtMapper;
import com.qnpeople.rnd.pms.apis.project.bsc.mapper.ProjectCompBaseOrgMgmtMapper;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponseErrorData;
import com.qnpeople.rnd.pms.common.domain.response.QNPResponsePacketEntityData;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;
import com.qnpeople.rnd.pms.utils.QNPResponseEntityUtils;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.service
 * @Filename		: ProjectCompBaseOrgMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.16.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 수행 업체 기본 조직 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 *  를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class ProjectCompBaseOrgMgmtServiceImpl extends QNPWebBaseServiceImpl implements ProjectCompBaseOrgMgmtService {

	/* 프로젝트 수행 업체 기본 조직 관리를 위한 DB 작업 수행 인터페이스 객체 */
	@Autowired
	private ProjectCompBaseOrgMgmtMapper projectCompBaseOrgMgmtMapper;
	
	/* 프로젝트 수행 업체 정보 관리를 위한 DB 작업 수행 인터페이스 객체 */
	@Autowired
	private CompanyMgmtMapper companyMgmtMapper;

	/* 프로젝트 기본 정보 관리를 위한 DB 작업 수행 인터페이스 객체 */
	@Autowired
	private BaseProjectMgmtMapper baseProjectMgmtMapper;

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
	public Boolean registerProjectCompanyBaseOrganization(ProjectCompBaseOrgDto projectCompBaseOrgDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer insertCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectCompBaseOrgDto)) {
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 기본 조직 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode,errorMessage);
			}
			//	
			if((SBNUtils.isNull(projectCompBaseOrgDto.getCompSeq()) || (projectCompBaseOrgDto.getCompSeq() < 0L)) && 
					SBNUtils.isNull(projectCompBaseOrgDto.getCompCd())) {
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 기본 조직 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode,errorMessage);
			}
			if(SBNUtils.isNull(projectCompBaseOrgDto.getCompOrgNm())) {
				errorMessage = "신규 등록 대상 프로젝트 수행 업체 기본 조직 명 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode,errorMessage);
			}
			if((SBNUtils.isNull(projectCompBaseOrgDto.getCompSeq()) || (projectCompBaseOrgDto.getCompSeq() < 0L)) || 
				SBNUtils.isNull(projectCompBaseOrgDto.getCompNm())) {
				CompanySC companySC = new CompanySC();
				companySC.setCompSeq(projectCompBaseOrgDto.getCompSeq());
				companySC.setCompCd(projectCompBaseOrgDto.getCompCd());
				CompanyDto companyDetail = companyMgmtMapper.selectCompanyDetail(companySC);
				if(SBNUtils.isNull(companyDetail)) {
					errorMessage = "신규 등록 대상 프로젝트 수행 업체 정보가 존재하지 않습니다.";
					throw new QNPWebException(reason, errorCode,errorMessage);
				}
				projectCompBaseOrgDto.setCompSeq(companyDetail.getCompSeq());
				if(SBNUtils.isNull(projectCompBaseOrgDto.getCompCd())) {
					projectCompBaseOrgDto.setCompCd(companyDetail.getCompCd());
				}
				if(SBNUtils.isNull(projectCompBaseOrgDto.getCompNm())) {
					projectCompBaseOrgDto.setCompNm(companyDetail.getCompNm());
				}
			}			
			log.debug("registerProjectCompanyBaseOrganization() projectCompBaseOrgDto={}", projectCompBaseOrgDto.toStringInfo());
			
			//	2. 
			insertCount = projectCompBaseOrgMgmtMapper.insertProjectCompanyBaseOrganization(projectCompBaseOrgDto);
			if(insertCount > 0) {
				resultFlag = true;
			}
			
			//	3. 
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 변경 대상 프로젝트 수행 업체의 기본 조직 정보에 대한 프로젝트 수행 업체 기본 조직 정보 변경 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgDto		변경 대상 프로젝트 수행 업체의 기본 조직 정보 전달 객체
	 * @return		전달된 변경 대상 프로젝트 수행 업체의 기본 조직 정보에 대한 프로젝트 수행 업체 기본 조직 정보 변경 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateProjectCompanyBaseOrganization(ProjectCompBaseOrgDto projectCompBaseOrgDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer updateCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectCompBaseOrgDto)) {
				errorMessage = "변경 대상 프로젝트 수행 업체 기본 조직 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectCompBaseOrgDto.getCompOrgSeq()) || (projectCompBaseOrgDto.getCompOrgSeq() < 0L))) {
				errorMessage = "변경 대상 프로젝트 수행 업체 키 정보가 전달되지 않았습니다.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("updateProjectCompanyBaseOrganization() projectCompBaseOrgDto={}", projectCompBaseOrgDto.toStringInfo());
			
			//	2. 
			updateCount = projectCompBaseOrgMgmtMapper.updateProjectCompanyBaseOrganization(projectCompBaseOrgDto);
			if(updateCount > 0) {
				resultFlag = true;
			}
			
			//	3. 
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 정보 삭제 작업 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgSC		프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 삭제 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 정보 삭제 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteProjectCompanyBaseOrganization(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer deleteCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectCompBaseOrgSC)) {
				errorMessage = "변경 대상 프로젝트 수행 업체 기본 조직 삭제 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectCompBaseOrgSC.getCompOrgSeq()) || (projectCompBaseOrgSC.getCompOrgSeq() < 0L))) {
				errorMessage = "삭제 대상 프로젝트 수행 기본 조직 업체 키 정보가 전달되지 않았습니다.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("deleteProjectCompanyBaseOrganization() projectCompBaseOrgSC={}", projectCompBaseOrgSC.toStringInfo());
			
			//	2. 
			deleteCount = projectCompBaseOrgMgmtMapper.deleteProjectCompanyBaseOrganization(projectCompBaseOrgSC);
			if(deleteCount > 0) {
				resultFlag = true;
			}
			
			//	3. 
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	
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
	public Boolean assignProjectCompanyOrganizationMap(List<ProjectCompExecuteOrgMapDto> projectCompExecuteOrgMapList) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer totalProjectCompExecuteOrgMapCount = 0;		
		Integer totalProjectCompExecuteMapInsertCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(projectCompExecuteOrgMapList)) {
				errorMessage = "프로젝트 지정 업체 수행 조직 매핑 목록 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			totalProjectCompExecuteOrgMapCount = projectCompExecuteOrgMapList.size();
			//	
			for(int i = 0; i < projectCompExecuteOrgMapList.size(); i++) {				
				if((SBNUtils.isNull(projectCompExecuteOrgMapList.get(i).getCompOrgSeq()) || (projectCompExecuteOrgMapList.get(i).getCompOrgSeq() <= 0L))) {
					errorMessage = "프로젝트 지정 대상 업체 수행 조직 식별자 정보 미 정의 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				if((SBNUtils.isNull(projectCompExecuteOrgMapList.get(i).getPrjctSeq()) || (projectCompExecuteOrgMapList.get(i).getPrjctSeq() <= 0L)) &&
					SBNUtils.isNull(projectCompExecuteOrgMapList.get(i).getPrjctCd())) {
					errorMessage = "지정 대상 프로젝트 키 정보 미 정의 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	부가 정보 구축 작업을 수행한다
				if((SBNUtils.isNull(projectCompExecuteOrgMapList.get(i).getCompSeq()) || (projectCompExecuteOrgMapList.get(i).getCompSeq() <= 0L)) ||
				   (SBNUtils.isNull(projectCompExecuteOrgMapList.get(i).getCompCd()))) {
					//	프로젝트 수행 업체 기본 조직 정보에서 업체 코드 및 업체 명 추출 작업 후, 맵핑 객체에 설정
					ProjectCompBaseOrgSC projectCompBaseOrgSC = new ProjectCompBaseOrgSC();
					projectCompBaseOrgSC.setCompOrgSeq(projectCompExecuteOrgMapList.get(i).getCompOrgSeq());;
					ProjectCompBaseOrgDto companyBaseOrgDetail = projectCompBaseOrgMgmtMapper.selectProjectCompanyBaseOrganizationDetail(projectCompBaseOrgSC);
					if(SBNUtils.isNull(companyBaseOrgDetail)) {
						errorMessage = "프로젝트 수행 업체 기본 조직 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					projectCompExecuteOrgMapList.get(i).setCompSeq(companyBaseOrgDetail.getCompSeq());
					projectCompExecuteOrgMapList.get(i).setCompCd(companyBaseOrgDetail.getCompCd());					
					projectCompExecuteOrgMapList.get(i).setCompNm(companyBaseOrgDetail.getCompNm());
				}
				if(SBNUtils.isNull(projectCompExecuteOrgMapList.get(i).getPrjctCd()) || SBNUtils.isNull(projectCompExecuteOrgMapList.get(i).getPrjctNm())) {
					//	프로젝트 기본 정보에서 프로젝트 코드 및 프로젝트 명 추출 작업 후, 맵핑 객체에 설정
					ProjectSC projectSC = new ProjectSC();
					projectSC.setPrjctSeq(projectCompExecuteOrgMapList.get(i).getPrjctSeq());
					projectSC.setPrjctCd(projectCompExecuteOrgMapList.get(i).getPrjctCd());
					ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
					if(SBNUtils.isNull(projectDetail)) {
						errorMessage = "프로젝트 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					projectCompExecuteOrgMapList.get(i).setPrjctSeq(projectDetail.getPrjctSeq());
					projectCompExecuteOrgMapList.get(i).setPrjctCd(projectDetail.getPrjctCd());
					projectCompExecuteOrgMapList.get(i).setPrjctNm(projectDetail.getPrjctNm());					
				}				
				//	
				Integer projectCompExecuteOrgMapInsertCount = projectCompBaseOrgMgmtMapper.insertProjectCompanyOrgizationMap(projectCompExecuteOrgMapList.get(i));
				if(projectCompExecuteOrgMapInsertCount > 0) {
					totalProjectCompExecuteMapInsertCount += projectCompExecuteOrgMapInsertCount;
				}
			}
			//
			if(totalProjectCompExecuteOrgMapCount == totalProjectCompExecuteMapInsertCount) {
				resultFlag = true;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
		
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
	public Boolean updateAssignedProjectCompanyOrganizationMap(ProjectCompExecuteOrgMapDto projectCompExecuteOrgMapDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer updateCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	TBD
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
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
	public Boolean releaseAssignedProjectCompanyOrganizationMap(List<ProjectCompExecuteOrgMapSC> projectCompExecuteOrgMapSCList) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer totalProjectCompExecuteOrgMapCount = 0;		
		Integer totalProjectCompExecuteMapDeleteCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 프로젝트 지정 해제 대상 업체 수행 조직 매핑 목록 키 조건 목록 객체가 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectCompExecuteOrgMapSCList)) {
				errorMessage = "프로젝트 지정 해제 업체 수행 조직 매핑 목록 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.1. 프로젝트 지정 해제 대상 업체 수행 조직 매핑 키 조건 목록 객체가 전달 시, 전달된 수행 전체 갯수 정보를 설정한다
			totalProjectCompExecuteOrgMapCount = projectCompExecuteOrgMapSCList.size();
			
			//	2. 전달된 프로젝트 지정 해제 대상 업체 수행 조직 매핑 키 조건 목록 객체에서 전달된 키 정보에 대한 유효성 체크 작업을 수행한다
			for(int i = 0; i < projectCompExecuteOrgMapSCList.size(); i++) {
				//	2.1. 프로젝트 해제 대상 업체 수행 조직 식별자 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
				if((SBNUtils.isNull(projectCompExecuteOrgMapSCList.get(i).getCompOrgSeq()) || (projectCompExecuteOrgMapSCList.get(i).getCompOrgSeq() <= 0L))) {					
					errorMessage = "프로젝트 지정 대상 업체 수행 조직 식별자 정보 미 정의 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	2.2. 프로젝트 해제 대상 프로젝트 키 조건 정보가 미 전달 시, 예외 처리 후 작업을 종료한다
				if((SBNUtils.isNull(projectCompExecuteOrgMapSCList.get(i).getPrjctSeq()) || (projectCompExecuteOrgMapSCList.get(i).getPrjctSeq() <= 0L)) &&
					SBNUtils.isNull(projectCompExecuteOrgMapSCList.get(i).getPrjctCd())) {
					errorMessage = "지정 대상 프로젝트 키 정보 미 정의 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	2.3. 해제 수행 프로젝트 식별자 정보가 미 전달 시, 프로젝트 코드 정보를 이용하여 프로젝트 상세 정보를 추출한다
				if((SBNUtils.isNull(projectCompExecuteOrgMapSCList.get(i).getPrjctSeq()) || (projectCompExecuteOrgMapSCList.get(i).getPrjctSeq() <= 0L))) {
					//	프로젝트 기본 정보에서 프로젝트 코드 및 프로젝트 명 추출 작업 후, 맵핑 객체에 설정
					ProjectSC projectSC = new ProjectSC();
					projectSC.setPrjctSeq(projectCompExecuteOrgMapSCList.get(i).getPrjctSeq());
					projectSC.setPrjctCd(projectCompExecuteOrgMapSCList.get(i).getPrjctCd());
					ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
					if(SBNUtils.isNull(projectDetail)) {
						errorMessage = "프로젝트 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					projectCompExecuteOrgMapSCList.get(i).setPrjctSeq(projectDetail.getPrjctSeq());
					projectCompExecuteOrgMapSCList.get(i).setPrjctCd(projectDetail.getPrjctCd());			
				}
				//	2.4. 최종 구축된 프로젝트 해제 대상 업체 수행 조직 매핑 키 개별 객체를 이용하여 프로젝트 지정 해제 작업을 수행한다
				Integer projectCompExecuteOrgMapDeleteCount = projectCompBaseOrgMgmtMapper.deleteProjectCompanyOrgizationMap(projectCompExecuteOrgMapSCList.get(i));
				if(projectCompExecuteOrgMapDeleteCount > 0) {
					//	2.4.1. 해제 작업 정상 수행 시, 전체 해제 수행 갯수를 재 설정한다
					totalProjectCompExecuteMapDeleteCount += projectCompExecuteOrgMapDeleteCount;
				}
			}
			
			//	3. 요청 전달 프로젝트 해제 대상 키 목록 갯수와 최종 해제 수행 전체 갯수 정보가 일치 시, 결과 Flag 를 true 로 설정한다
			if(totalProjectCompExecuteOrgMapCount == totalProjectCompExecuteMapDeleteCount) {
				resultFlag = true;
			}
			
			//	4. 최종 프로젝트 해제 수행 작업 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	
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
	public List<ProjectCompBaseOrgDto> getProjectCompanyBaseOrganizationList(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		List<ProjectCompBaseOrgDto> resultList = null;
		try {
			//	1. 
			if(SBNUtils.isNull(projectCompBaseOrgSC)) {
				errorMessage = "프로젝트 수행 업체 기본 조직 목록 조회 수행 조건 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 
			resultList = projectCompBaseOrgMgmtMapper.selectProjectCompanyBaseOrganizationList(projectCompBaseOrgSC);
			
			//	3. 
			return resultList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 상세 정보 객체  추출 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.16
	 * @param 	projectCompBaseOrgSC		프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보 전달 객체
	 * @return		전달된 프로젝트 수행 업체의 기본 조직 상세 조회 수행 조건 정보에 대한 프로젝트 수행 업체 기본 조직 상세 정보 객체.
	 * @throws 	QNPWebException				프로젝트 수행 업체 기본 조직 정보 관리 작업 작업을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectCompBaseOrgDto getProjectCompanyBaseOrganizationDetail(ProjectCompBaseOrgSC projectCompBaseOrgSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		ProjectCompBaseOrgDto resultDetail = null;
		try {
			//	1. 
			if(SBNUtils.isNull(projectCompBaseOrgSC)) {
				errorMessage = "프로젝트 수행 업체 기본 조직 상세 조회 수행 조건 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	
			if(SBNUtils.isNull(projectCompBaseOrgSC.getCompOrgSeq())) {
				errorMessage = "프로젝트 수행 업체 기본 조직 상세 조회 수행 조건 키 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
						
			//	2. 
			resultDetail = projectCompBaseOrgMgmtMapper.selectProjectCompanyBaseOrganizationDetail(projectCompBaseOrgSC);
			
			//	3. 
			return resultDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
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
	public List<ProjectCompExecuteOrgMapDto> getAssignedProjectCompanyOrganizationMapList(ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		List<ProjectCompExecuteOrgMapDto> projectCompExecuteOrgMapList = new ArrayList<ProjectCompExecuteOrgMapDto>();
		try {
			//	1. 프로젝트에 대한 프로젝트 수행 업체 기본 조직 매핑 목록 추출 조건 전달 객체에 대한 유효성 체크를 수행하고, 유효하지 않은 경우 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectCompExecuteOrgMapSC)) {
				errorMessage = "프로젝트 수행 업체 기본 조직 매핑 목록 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.1. 업체별로 목록 조회를 한정하기 위해, 업체 조직 식별자 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectCompExecuteOrgMapSC.getCompOrgSeq()) || (projectCompExecuteOrgMapSC.getCompOrgSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "프로젝트 수행 업체 기본 조직 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("getAssignedProjectCompanyOrganizationMapList() projectCompExecuteOrgMapSC={}", projectCompExecuteOrgMapSC.toStringInfo());
			
			//	2. 전달된 조건 정보에 대한 프로젝트 수행 업체 기본 구조 매핑 목록 정보를 추출한다
			projectCompExecuteOrgMapList = projectCompBaseOrgMgmtMapper.selectAssignedProjectCompanyOrganizationMapList(projectCompExecuteOrgMapSC);
			
			//	3. 최종 추출된 결과 객체를 전달 후 작업을 종료한다
			return projectCompExecuteOrgMapList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
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
	public ProjectCompExecuteOrgMapDto getAssignedProjectCompanyOrganizationMapDetail(ProjectCompExecuteOrgMapSC projectCompExecuteOrgMapSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		ProjectCompExecuteOrgMapDto projectCompExecuteOrgMapDetail = null;
		try {
			//	1. 프로젝트 수행 업체 기본 조직 매핑 프로젝트 수행 조직도 맵핑 상세 정보 추출 조건 정보에 대한 유효성 체크 후 유효하지 않은 경우, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(projectCompExecuteOrgMapSC)) {
				errorMessage = "프로젝트 수행 업체 기본 조직 매핑 상세 조회 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectCompExecuteOrgMapSC.getCompOrgSeq()) || (projectCompExecuteOrgMapSC.getCompOrgSeq() <= 0L)) {				
				errorMessage = "프로젝트 수행 업체 기본 조직 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	
			if((SBNUtils.isNull(projectCompExecuteOrgMapSC.getPrjctSeq()) || (projectCompExecuteOrgMapSC.getPrjctSeq() <= 0L)) && 
			    SBNUtils.isNull(projectCompExecuteOrgMapSC.getPrjctCd())) {
				errorMessage = "프로젝트 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectCompExecuteOrgMapSC.getPrjctSeq()) || (projectCompExecuteOrgMapSC.getPrjctSeq() <= 0L))) {
				ProjectSC projectSC = new ProjectSC();
				projectSC.setPrjctCd(projectCompExecuteOrgMapSC.getPrjctCd());
				ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
				if(SBNUtils.isNull(projectDetail)) {
					errorMessage = "프로젝트 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				log.debug("getAssignedProjectCompanyOrganizationMapDetail() projectDetail={}", projectDetail.toStringInfo());
				projectCompExecuteOrgMapSC.setPrjctSeq(projectDetail.getPrjctSeq());
			}
			log.debug("getAssignedProjectCompanyOrganizationMapDetail() projectCompExecuteOrgMapSC={}", projectCompExecuteOrgMapSC.toStringInfo());
			
			//	2. 구축된 프로젝트 수행 업체 기본 조직 매핑 프로젝트 수행 조직도 맵핑 상세 정보 추출 조건 정보를 이용하여 매핑 상세 정보 객체를 추출한다
			projectCompExecuteOrgMapDetail = projectCompBaseOrgMgmtMapper.selectAssignedProjectCompanyOrganizationMapDetail(projectCompExecuteOrgMapSC);
			
			//	3. 추출된 결과를 전달 후 작업을 종료한다
			return projectCompExecuteOrgMapDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
