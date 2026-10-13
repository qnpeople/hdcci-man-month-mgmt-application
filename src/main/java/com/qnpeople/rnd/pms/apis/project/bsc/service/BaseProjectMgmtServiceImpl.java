package com.qnpeople.rnd.pms.apis.project.bsc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.project.bsc.mapper.BaseProjectMgmtMapper;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteSC;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectSC;
import com.qnpeople.rnd.pms.apis.service.service.mapper.ServiceMgmtMapper;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceDto;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceSC;
import com.qnpeople.rnd.pms.apis.service.siteservice.mapper.SiteServiceMgmtMapper;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceDto;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.project.bsc.service
 * @Filename		: BaseProjectMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.10.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 프로젝트 영역의 프로젝트 기본 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class BaseProjectMgmtServiceImpl extends QNPWebBaseServiceImpl implements BaseProjectMgmtService {

	/** 프로젝트 기본 정보 관리를 위한 DB 처리 수행 인터페이스 객체 */
	@Autowired
	private BaseProjectMgmtMapper baseProjectMgmtMapper;
	
	/* 사이트 서비스 정보를 위한 DB 처리 수행 인터페이스 객체 */
	@Autowired
	private SiteServiceMgmtMapper siteServiceMgmtMapper;
	
	/* 서비스 정보를 위한 DB 처리 수행 인터페이스 객체 */
	@Autowired
	private ServiceMgmtMapper serviceMgmtMapper;
	
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
	public Boolean registerNewBaseProject(ProjectDto projectDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		ProjectSC projectSC = null;
		Boolean projectCdDuplicationFlag = Boolean.FALSE;
		Integer projectRegisterCount = 0;
		Boolean baseProjectRegisterFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectDto)) {
				errorMessage = "신규 등록 대상 프로젝트 기본 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectDto.getPrjctCd())) {
				errorMessage = "신규 등록 대상 프로젝트 코드 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	2. 
			projectSC = new ProjectSC();
			projectSC.setPrjctCd(projectDto.getPrjctCd());
			projectCdDuplicationFlag = baseProjectMgmtMapper.selectProjectCodeDuplication(projectSC);
			if(projectCdDuplicationFlag) {
				errorMessage = "신규 등록 대상 프로젝트 코드 중복 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	3. 
			projectRegisterCount = baseProjectMgmtMapper.insertNewProject(projectDto);
			if(projectRegisterCount > 0) {
				baseProjectRegisterFlag = true;
			}
			
			//	4. 
			return baseProjectRegisterFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}

	/**
	 * 전달된 변경 대상 프로젝트 기본 정보에 대한 프로젝트 기본 정보 변경 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectDto				변경 대상 프로젝트 기본 정보 전달 객체
	 * @return		전달된 변경 대상 프로젝트 기본 정보에 대한 프로젝트 기본 정보 변경 작업 수행 결과 Flag. 정상 변경 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateBaseProject(ProjectDto projectDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer projectUpdateCount = 0;
		Boolean baseProjectUpdateFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectDto)) {
				errorMessage = "변경 대상 프로젝트 기본 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectDto.getPrjctSeq()) || (projectDto.getPrjctSeq() <= 0L)) && SBNUtils.isNull(projectDto.getPrjctCd())) {
				errorMessage = "변경 대상 프로젝트 키 정보가 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	2. 
			if(SBNUtils.isNull(projectDto.getPrjctSeq()) || (projectDto.getPrjctSeq() <= 0L)) {
				ProjectSC projectSC = new ProjectSC();
				projectSC.setPrjctCd(projectDto.getPrjctCd());
				ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
				if(SBNUtils.isNull(projectDetail)) {
					errorMessage = "변경 대상 프로젝트 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectDto.setPrjctSeq(projectDetail.getPrjctSeq());
			}
			log.debug("updateBaseProject() projectDto={}", projectDto.toStringInfo());
			
			//	3. 
			projectUpdateCount = baseProjectMgmtMapper.updateProject(projectDto);
			if(projectUpdateCount > 0) {
				baseProjectUpdateFlag = true;
			}
			
			//	4. 
			return baseProjectUpdateFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 삭제 대상 프로젝트 조건 정보에 대한 프로젝트 기본 정보 삭제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					프로젝트 기본 정보 삭제 수행 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 프로젝트 조건 정보에 대한 프로젝트 기본 정보 삭제 작업 수행 결과 Flag. 정상 삭제 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteBaseProject(ProjectSC projectSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer projectDeleteCount = 0;
		Boolean baseProjectDeleteFlag = Boolean.FALSE;
		try {
			//	1. 
			if(SBNUtils.isNull(projectSC)) {
				errorMessage = "프로젝트 기본 정보 삭제 수행 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 
			if((SBNUtils.isNull(projectSC.getPrjctSeq()) || (projectSC.getPrjctSeq() <= 0L)) && 
				SBNUtils.isNull(projectSC.getPrjctCd())) {
				errorMessage = "프로젝트 기본 정보 삭제 수행 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectSC.getPrjctSeq()) || (projectSC.getPrjctSeq() <= 0L)) {
				ProjectDto projcetDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
				if(SBNUtils.isNull(projcetDetail)) {
					errorMessage = "삭제 대상 프로젝트 기본 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectSC.setPrjctSeq(projcetDetail.getPrjctSeq());
			}
			
			//	3. 
			projectDeleteCount = baseProjectMgmtMapper.deleteProject(projectSC);
			if(projectDeleteCount > 0) {
				baseProjectDeleteFlag = true;
			}			
			return baseProjectDeleteFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 신규 사이트 개별 지정 작업 수행 목록에 대한 지정 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	releaseProjectSiteList		프로젝트 신규 사이트 개별 지정 수행 목록 객체
	 * @return		전달된 프로젝트 신규 사이트 개별 지정 작업 수행 목록에 대한 지정 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 기본 정보 신규 사이트 개별 지정 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignBaseProjectToSiteForIndividual(ProjectChannelSiteDto projectChannelSiteDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer projectSiteAssignCount = 0;
		Boolean projectSiteAssignFlag = Boolean.FALSE;
		try {
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	1. 전달된 프로젝트 사이트 지정 수행 정보에 대한 유효성 체크 작업을 수행한다
			//	1.1. 프로젝트 사이트 단건 지정 수행 전달 객체가 미 전달 시, 예외 처리 후 작업을 종료한다 
			//	1.2. 프로젝트 사이트 지정 대상 채널 사이트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			//	1.3. 프로젝트 사이트 지정 대상 서비스 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			//	1.4. 프로젝트 사이트 지정 대상 프로젝트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//	1.1. 프로젝트 사이트 단건 지정 수행 전달 객체가 미 전달 시, 예외 처리 후 작업을 종료한다 
			if(SBNUtils.isNull(projectChannelSiteDto)) {
				errorMessage = "단건 프로젝트 사이트 지정 수행 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.2. 프로젝트 사이트 지정 대상 채널 사이트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectChannelSiteDto.getChnlSiteSeq()) || (projectChannelSiteDto.getChnlSiteSeq() <= 0L)) &&
				SBNUtils.isNull(projectChannelSiteDto.getChnlCd()) && SBNUtils.isNull(projectChannelSiteDto.getSiteCd())) {
				errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.3. 프로젝트 사이트 지정 대상 서비스 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectChannelSiteDto.getSrvcSeq()) || (projectChannelSiteDto.getSrvcSeq() <= 0L)) &&
				SBNUtils.isNull(projectChannelSiteDto.getSrvcCd())) {
				errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 서비스 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.4. 프로젝트 사이트 지정 대상 프로젝트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectChannelSiteDto.getPrjctSeq()) || (projectChannelSiteDto.getPrjctSeq() <= 0L)) &&
				SBNUtils.isNull(projectChannelSiteDto.getPrjctCd())) {
				errorMessage = "신규 지정 대상 프로젝트 단건 사이트 지정 프로젝트 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2. 전달된 프로젝트 사이트 지정 수행 정보를 이용하여 실제 프로젝트 사이트 지정 작업 수행 키 정보를 구축한다
			//	2.1. 서비스 식별자 정보가 미 전달이고, 서비스 코드가 전달된 경우, 서비스 코드를 이용하여 서비스 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
			//	2.2. 채널 사이트 식별자 정보가 미 전달 시, 전달된 채널 코드 및 사이트 코드 정보를 이용하여 채널 사이트 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
			//	2.3. 프로젝트 식별자가 미 전달 시, 전달된 프로젝트 코드 정보를 이용하여 프로젝트 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2.1. 서비스 식별자 정보가 미 전달이고, 서비스 코드가 전달된 경우, 서비스 코드를 이용하여 서비스 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
			if(SBNUtils.isNull(projectChannelSiteDto.getSrvcSeq()) || (projectChannelSiteDto.getSrvcSeq() <= 0L)) {
				//	2.1.1. 서비스 코드 정보가 전달된 경우, 서비스 코드를 이용하여 해당 서비스 상세 정보를 추출 후, 서비스 식별자 정보를 추출한다
				ServiceSC serviceSC = new ServiceSC();
				serviceSC.setSrvcCd(projectChannelSiteDto.getSrvcCd());
				ServiceDto serviceDetail = serviceMgmtMapper.selectServiceDetail(serviceSC);
				if(SBNUtils.isNull(serviceDetail)) {
					errorMessage = "프로젝트 지정 서비스 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteDto.setSrvcSeq(serviceDetail.getSrvcSeq());
			}
			//	2.2. 채널 사이트 식별자 정보가 미 전달 시, 전달된 채널 코드 및 사이트 코드 정보를 이용하여 채널 사이트 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
			if(SBNUtils.isNull(projectChannelSiteDto.getChnlSiteSeq()) || (projectChannelSiteDto.getChnlSiteSeq() <= 0L )) {
				//	2.2.1 채널 코드 및 사이트 코드 정보를 이용하여 채널 서비스 매핑 상세 정보를 추출 후, 채널 사이트 식별자 정보를 추출한다
				SiteServiceSC siteServiceSC = new SiteServiceSC();
				siteServiceSC.setChnlCd(projectChannelSiteDto.getChnlCd());
				siteServiceSC.setSiteCd(projectChannelSiteDto.getSiteCd());
				siteServiceSC.setSrvcCd(projectChannelSiteDto.getSrvcCd());
				SiteServiceDto siteServiceDetail = siteServiceMgmtMapper.selectSiteServiceDetailByCode(siteServiceSC);
				if(SBNUtils.isNull(siteServiceDetail)) {
					errorMessage = "프로젝트 지정 채널 사이트 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteDto.setChnlSiteSeq(siteServiceDetail.getChnlSiteSeq());
			}
			//	2.3. 프로젝트 식별자가 미 전달 시, 전달된 프로젝트 코드 정보를 이용하여 프로젝트 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
			if(SBNUtils.isNull(projectChannelSiteDto.getPrjctSeq()) || (projectChannelSiteDto.getPrjctSeq() <= 0L)) {
				//	2.3.1. 프로젝트 코드 정보를 이용하여 프로젝트 상세 정보를 추출 후, 프로젝트 식별자 정보를 추출한다
				ProjectSC projectSC = new ProjectSC();
				projectSC.setPrjctCd(projectChannelSiteDto.getPrjctCd());
				ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
				if(SBNUtils.isNull(projectDetail)) {
					errorMessage = "프로젝트 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteDto.setPrjctSeq(projectDetail.getPrjctSeq());
			}
			log.debug("assignBaseProjectToSiteForIndividual() projectChannelSiteDto={}", projectChannelSiteDto.toStringInfo());
			
			//	3. 최종 구축된 단건 프로젝트 사이트 지정 수행 정보를 이용하여 프로젝트 사이트 지정 작업을 수행 후, 수행 결과 갯수를 전달 받는다
			projectSiteAssignCount = baseProjectMgmtMapper.insertNewProjectAssignSite(projectChannelSiteDto);
			if(projectSiteAssignCount > 0) {
				//	3.1. 수행 결과 갯수가 0 보다 큰 경우, 수행 결과 Flag 를 true 로 설정한다
				projectSiteAssignFlag = true;
			}
			
			//	4. 최종 단건 프로젝트 사이트 지정 수행 결과 Flag 를 전달 후 작업을 종료한다
			return projectSiteAssignFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 신규 사이트 다중 지정 작업 수행 목록에 대한 지정 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	assignProjectSiteList			프로젝트 신규 사이트 다중 지정 수행 목록 객체
	 * @return		전달된 프로젝트 신규 사이트 다중 지정 작업 수행 목록에 대한 지정 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 기본 정보 신규 사이트 다중 지정 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignBaseProjectToSiteForList(List<ProjectChannelSiteDto> assignProjectSiteList) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer assigningProjectSiteCount = 0;
		Integer projectSiteAssignCount = 0;
		Integer totalResultCount = 0;
		Boolean projectSiteAssignFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(assignProjectSiteList)) {
				errorMessage = "다중 프로젝트 사이트 지정 수행 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			assigningProjectSiteCount = assignProjectSiteList.size();
			log.debug("assignBaseProjectToSiteForList() assigningProjectSiteCount=[ {} ]", assigningProjectSiteCount);
			for(int i = 0; i < assigningProjectSiteCount; i++) {				
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//	1. 전달된 프로젝트 사이트 지정 수행 정보에 대한 유효성 체크 작업을 수행한다
				//	1.1. 프로젝트 사이트 지정 대상 채널 사이트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				//	1.2. 프로젝트 사이트 지정 대상 서비스 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				//	1.3. 프로젝트 사이트 지정 대상 프로젝트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
				//	1.1. 프로젝트 사이트 지정 대상 채널 사이트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				if((SBNUtils.isNull(assignProjectSiteList.get(i).getChnlSiteSeq()) || (assignProjectSiteList.get(i).getChnlSiteSeq() <= 0L)) &&
					SBNUtils.isNull(assignProjectSiteList.get(i).getChnlCd()) && SBNUtils.isNull(assignProjectSiteList.get(i).getSiteCd())) {
					errorMessage = "신규 지정 대상 프로젝트 다중 사이트 지정 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	1.2. 프로젝트 사이트 지정 대상 서비스 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				if((SBNUtils.isNull(assignProjectSiteList.get(i).getSrvcSeq()) || (assignProjectSiteList.get(i).getSrvcSeq() <= 0L)) &&
					SBNUtils.isNull(assignProjectSiteList.get(i).getSrvcCd())) {
					errorMessage = "신규 지정 대상 프로젝트 다중 사이트 지정 서비스 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	1.3. 프로젝트 사이트 지정 대상 프로젝트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				if((SBNUtils.isNull(assignProjectSiteList.get(i).getPrjctSeq()) || (assignProjectSiteList.get(i).getPrjctSeq() <= 0L)) &&
					SBNUtils.isNull(assignProjectSiteList.get(i).getPrjctCd())) {
					errorMessage = "신규 지정 대상 프로젝트 다중 사이트 지정 프로젝트 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//	2. 전달된 프로젝트 사이트 지정 수행 정보를 이용하여 실제 프로젝트 사이트 지정 작업 수행 키 정보를 구축한다
				//	2.1. 서비스 식별자 정보가 미 전달이고, 서비스 코드가 전달된 경우, 서비스 코드를 이용하여 서비스 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
				//	2.2. 채널 사이트 식별자 정보가 미 전달 시, 전달된 채널 코드 및 사이트 코드 정보를 이용하여 채널 사이트 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
				//	2.3. 프로젝트 식별자가 미 전달 시, 전달된 프로젝트 코드 정보를 이용하여 프로젝트 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//	2.1. 서비스 식별자 정보가 미 전달이고, 서비스 코드가 전달된 경우, 서비스 코드를 이용하여 서비스 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
				if(SBNUtils.isNull(assignProjectSiteList.get(i).getSrvcSeq()) || (assignProjectSiteList.get(i).getSrvcSeq() <= 0L)) {
					//	2.1.1. 서비스 코드 정보가 전달된 경우, 서비스 코드를 이용하여 해당 서비스 상세 정보를 추출 후, 서비스 식별자 정보를 추출한다
					ServiceSC serviceSC = new ServiceSC();
					serviceSC.setSrvcCd(assignProjectSiteList.get(i).getSrvcCd());
					ServiceDto serviceDetail = serviceMgmtMapper.selectServiceDetail(serviceSC);
					if(SBNUtils.isNull(serviceDetail)) {
						errorMessage = "프로젝트 지정 서비스 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					assignProjectSiteList.get(i).setSrvcSeq(serviceDetail.getSrvcSeq());
				}
				//	2.2. 채널 사이트 식별자 정보가 미 전달 시, 전달된 채널 코드 및 사이트 코드 정보를 이용하여 채널 사이트 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
				if(SBNUtils.isNull(assignProjectSiteList.get(i).getChnlSiteSeq()) || (assignProjectSiteList.get(i).getChnlSiteSeq() <= 0L )) {
					//	2.2.1 채널 코드 및 사이트 코드 정보를 이용하여 채널 서비스 매핑 상세 정보를 추출 후, 채널 사이트 식별자 정보를 추출한다
					SiteServiceSC siteServiceSC = new SiteServiceSC();
					siteServiceSC.setChnlCd(assignProjectSiteList.get(i).getChnlCd());
					siteServiceSC.setSiteCd(assignProjectSiteList.get(i).getSiteCd());
					siteServiceSC.setSrvcCd(assignProjectSiteList.get(i).getSrvcCd());
					SiteServiceDto siteServiceDetail = siteServiceMgmtMapper.selectSiteServiceDetailByCode(siteServiceSC);
					if(SBNUtils.isNull(siteServiceDetail)) {
						errorMessage = "프로젝트 지정 채널 사이트 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					assignProjectSiteList.get(i).setChnlSiteSeq(siteServiceDetail.getChnlSiteSeq());
				}
				//	2.3. 프로젝트 식별자가 미 전달 시, 전달된 프로젝트 코드 정보를 이용하여 프로젝트 식별자 정보를 추출하여 프로젝트 사이트 지정 수행 객체에 설정한다
				if(SBNUtils.isNull(assignProjectSiteList.get(i).getPrjctSeq()) || (assignProjectSiteList.get(i).getPrjctSeq() <= 0L)) {
					//	2.3.1. 프로젝트 코드 정보를 이용하여 프로젝트 상세 정보를 추출 후, 프로젝트 식별자 정보를 추출한다
					ProjectSC projectSC = new ProjectSC();
					projectSC.setPrjctCd(assignProjectSiteList.get(i).getPrjctCd());
					ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
					if(SBNUtils.isNull(projectDetail)) {
						errorMessage = "프로젝트 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					assignProjectSiteList.get(i).setPrjctSeq(projectDetail.getPrjctSeq());
				}
				log.debug("assignBaseProjectToSiteForList() assignProjectSiteList[ {} ]={}", i, assignProjectSiteList.get(i).toStringInfo());
				
				//	3. 최종 구축된 다중 프로젝트 사이트 지정 수행 정보를 이용하여 프로젝트 사이트 지정 작업을 수행 후, 수행 결과 갯수를 전달 받는다
				projectSiteAssignCount = baseProjectMgmtMapper.insertNewProjectAssignSite(assignProjectSiteList.get(i));
				totalResultCount += projectSiteAssignCount;
			}
			
			log.debug("assignBaseProjectToSiteForList()  assigningProjectSiteCount=[ {} ]. totalResultCount=[ {} ]", assigningProjectSiteCount, totalResultCount);
			if(assigningProjectSiteCount == totalResultCount) {
				projectSiteAssignFlag = true;
			}
			return projectSiteAssignFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 신규 사이트 개별 지정 해제 작업 수행 목록에 대한 지정 해제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC		프로젝트 신규 사이트 개별 지정 해제 수행 목록 객체
	 * @return		전달된 프로젝트 신규 사이트 개별 지정 해제 작업 수행 목록에 대한 지정 해제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException			프로젝트 기본 정보 신규 사이트 개별 지정 해제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseBaseProjectFromSiteForIndividual(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer projectSiteReleaseCount = 0;
		Boolean projectSiteReleaseFlag = Boolean.FALSE;
		try {
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	1. 전달된 프로젝트 사이트 지정 해제 수행 정보에 대한 유효성 체크 작업을 수행한다
			//	1.1. 프로젝트 사이트 단건 지정 해제 수행 전달 객체가 미 전달 시, 예외 처리 후 작업을 종료한다 
			//	1.2. 프로젝트 사이트 지정 해제 대상 채널 사이트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			//	1.3. 프로젝트 사이트 지정 해제 대상 서비스 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			//	1.4. 프로젝트 사이트 지정 해제 대상 프로젝트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//	1.1. 프로젝트 사이트 단건 지정 해제 수행 전달 객체가 미 전달 시, 예외 처리 후 작업을 종료한다 
			if(SBNUtils.isNull(projectChannelSiteSC)) {
				errorMessage = "단건 프로젝트 사이트 지정 해제 수행 키 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.2. 프로젝트 사이트 지정 해제 대상 채널 사이트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectChannelSiteSC.getChnlSiteSeq()) || (projectChannelSiteSC.getChnlSiteSeq() <= 0L)) &&
				SBNUtils.isNull(projectChannelSiteSC.getChnlCd()) && SBNUtils.isNull(projectChannelSiteSC.getSiteCd())) {
				errorMessage = "지정 해제 대상 프로젝트 단건 지정 해제 채널 사이트 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.3. 프로젝트 사이트 지정 해제 대상 서비스 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectChannelSiteSC.getSrvcSeq()) || (projectChannelSiteSC.getSrvcSeq() <= 0L)) &&
				SBNUtils.isNull(projectChannelSiteSC.getSrvcCd())) {
				errorMessage = "지정 해제 대상 프로젝트 지정 해제 단건 서비스 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.4. 프로젝트 사이트 지정 해제 대상 프로젝트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
			if((SBNUtils.isNull(projectChannelSiteSC.getPrjctSeq()) || (projectChannelSiteSC.getPrjctSeq() <= 0L)) &&
				SBNUtils.isNull(projectChannelSiteSC.getPrjctCd())) {
				errorMessage = "지정 해제 대상 프로젝트 단건 지정 해제 프로젝트 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2. 전달된 프로젝트 사이트 지정 해제 수행 정보를 이용하여 실제 프로젝트 사이트 지정 작업 수행 조건 정보를 구축한다
			//	2.1. 서비스 식별자 정보가 미 전달이고, 서비스 코드가 전달된 경우, 서비스 코드를 이용하여 서비스 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
			//	2.2. 채널 사이트 식별자 정보가 미 전달 시, 전달된 채널 코드 및 사이트 코드 정보를 이용하여 채널 사이트 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
			//	2.3. 프로젝트 식별자가 미 전달 시, 전달된 프로젝트 코드 정보를 이용하여 프로젝트 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	2.1. 서비스 식별자 정보가 미 전달이고, 서비스 코드가 전달된 경우, 서비스 코드를 이용하여 서비스 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
			if(SBNUtils.isNull(projectChannelSiteSC.getSrvcSeq()) || (projectChannelSiteSC.getSrvcSeq() <= 0L)) {
				//	2.1.1. 서비스 코드 정보가 전달된 경우, 서비스 코드를 이용하여 해당 서비스 상세 정보를 추출 후, 서비스 식별자 정보를 추출한다
				ServiceSC serviceSC = new ServiceSC();
				serviceSC.setSrvcCd(projectChannelSiteSC.getSrvcCd());
				ServiceDto serviceDetail = serviceMgmtMapper.selectServiceDetail(serviceSC);
				if(SBNUtils.isNull(serviceDetail)) {
					errorMessage = "프로젝트 지정 해제 대상 서비스 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteSC.setSrvcSeq(serviceDetail.getSrvcSeq());
			}
			//	2.2. 채널 사이트 식별자 정보가 미 전달 시, 전달된 채널 코드 및 사이트 코드 정보를 이용하여 채널 사이트 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
			if(SBNUtils.isNull(projectChannelSiteSC.getChnlSiteSeq()) || (projectChannelSiteSC.getChnlSiteSeq() <= 0L )) {
				//	2.2.1 채널 코드 및 사이트 코드 정보를 이용하여 채널 서비스 매핑 상세 정보를 추출 후, 채널 사이트 식별자 정보를 추출한다
				SiteServiceSC siteServiceSC = new SiteServiceSC();
				siteServiceSC.setChnlCd(projectChannelSiteSC.getChnlCd());
				siteServiceSC.setSiteCd(projectChannelSiteSC.getSiteCd());
				siteServiceSC.setSrvcCd(projectChannelSiteSC.getSrvcCd());
				SiteServiceDto siteServiceDetail = siteServiceMgmtMapper.selectSiteServiceDetailByCode(siteServiceSC);
				if(SBNUtils.isNull(siteServiceDetail)) {
					errorMessage = "프로젝트 지정 해제 대상 채널 사이트 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteSC.setChnlSiteSeq(siteServiceDetail.getChnlSiteSeq());
			}
			//	2.3. 프로젝트 식별자가 미 전달 시, 전달된 프로젝트 코드 정보를 이용하여 프로젝트 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
			if(SBNUtils.isNull(projectChannelSiteSC.getPrjctSeq()) || (projectChannelSiteSC.getPrjctSeq() <= 0L)) {
				//	2.3.1. 프로젝트 코드 정보를 이용하여 프로젝트 상세 정보를 추출 후, 프로젝트 식별자 정보를 추출한다
				ProjectSC projectSC = new ProjectSC();
				projectSC.setPrjctCd(projectChannelSiteSC.getPrjctCd());
				ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
				if(SBNUtils.isNull(projectDetail)) {
					errorMessage = "지정 해제 대상 프로젝트 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteSC.setPrjctSeq(projectDetail.getPrjctSeq());
			}
			log.debug("releaseBaseProjectFromSiteForIndividual() projectChannelSiteSC={}", projectChannelSiteSC.toStringInfo());
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	3. 최종 구축된 단건 프로젝트 사이트 지정 해제 수행 정보를 이용하여 프로젝트 사이트 지정 해제 작업을 수행 후, 수행 결과 갯수를 전달 받는다
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			projectSiteReleaseCount = baseProjectMgmtMapper.deleteProjectAssignedSite(projectChannelSiteSC);
			if(projectSiteReleaseCount > 0) {
				//	3.1. 프로젝트 사이트 지정 해제 작업 수행 결과가 0 보다 큰 경우, 프로젝트 사이트 지정 해제 결과 Flag 를 true 로 설정한다 
				projectSiteReleaseFlag = true;
			}
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//	4. 최종 단건 프로젝트 사이트 지정 해제 수행 결과 Flag 를 전달 후 작업을 종료한다
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			return projectSiteReleaseFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 지정 사이트 다중 해제 수행 조건 목록에 대한 지정 사이트 다중 해제 작업 수행 후, 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	releaseProjectSiteKeyList		프로젝트 지정 사이트 다중 해제 수행 조건 목록 객체
	 * @return		전달된 프로젝트 지정 사이트 다중 해제 수행 조건 목록에 대한 사이트 지정 해제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException				프로젝트 기본 정보 지정 사이트 다중 해제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseBaseProjectFromSiteForList(List<ProjectChannelSiteSC> releaseProjectSiteKeyList) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Integer releaseProjectSiteCount = 0;
		Integer projectSiteReleaseCount = 0;
		Integer totalResultCount = 0;
		Boolean projectSiteReleaseFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(releaseProjectSiteKeyList)) {
				errorMessage = "다중 프로젝트 사이트 지정 해제 수행 정보 미 전달 오류";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			releaseProjectSiteCount = releaseProjectSiteKeyList.size();
			log.debug("releaseBaseProjectFromSiteForList() releaseProjectSiteCount=[ {} ]", releaseProjectSiteCount);
			for(int i = 0; i < releaseProjectSiteCount; i++) {
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//	1. 전달된 프로젝트 사이트 지정 수행 정보에 대한 유효성 체크 작업을 수행한다
				//	1.1. 프로젝트 사이트 지정 해제 대상 채널 사이트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				//	1.2. 프로젝트 사이트 지정 해제 대상 서비스 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				//	1.3. 프로젝트 사이트 지정 해제 대상 프로젝트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
				//	1.1. 프로젝트 사이트 지정 해제 대상 채널 사이트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				if((SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getChnlSiteSeq()) || (releaseProjectSiteKeyList.get(i).getChnlSiteSeq() <= 0L)) &&
					SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getChnlCd()) && SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getSiteCd())) {
					errorMessage = "지정 해제 대상 다중 프로젝트 사이트 지정 해제 채널 사이트 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	1.2. 프로젝트 사이트 지정 해제 대상 서비스 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				if((SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getSrvcSeq()) || (releaseProjectSiteKeyList.get(i).getSrvcSeq() <= 0L)) &&
					SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getSrvcCd())) {
					errorMessage = "지정 해제 대상 다중 프로젝트 사이트 지정 해제 서비스 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				//	1.3. 프로젝트 사이트 지정 해제 대상 프로젝트 키 정보 미 전달 시, 예외 처리 후 작업을 종료한다
				if((SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getPrjctSeq()) || (releaseProjectSiteKeyList.get(i).getPrjctSeq() <= 0L)) &&
					SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getPrjctCd())) {
					errorMessage = "지정 해제 대상 다중 프로젝트 사이트 지정 해제 프로젝트 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//	2. 전달된 프로젝트 사이트 지정 해제 수행 정보를 이용하여 실제 프로젝트 사이트 지정 해제 작업 수행 키 정보를 구축한다
				//	2.1. 서비스 식별자 정보가 미 전달이고, 서비스 코드가 전달된 경우, 서비스 코드를 이용하여 서비스 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
				//	2.2. 채널 사이트 식별자 정보가 미 전달 시, 전달된 채널 코드 및 사이트 코드 정보를 이용하여 채널 사이트 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
				//	2.3. 프로젝트 식별자가 미 전달 시, 전달된 프로젝트 코드 정보를 이용하여 프로젝트 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
				//	2.4. 최종 구축된 다중 프로젝트 사이트 지정 해제 수행 조건 정보를 이용하여 프로젝트 사이트 지정 해제 작업을 수행 후, 수행 결과 갯수를 전달 받는다
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//	2.1. 서비스 식별자 정보가 미 전달이고, 서비스 코드가 전달된 경우, 서비스 코드를 이용하여 서비스 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
				if(SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getSrvcSeq()) || (releaseProjectSiteKeyList.get(i).getSrvcSeq() <= 0L)) {
					//	2.1.1. 서비스 코드 정보가 전달된 경우, 서비스 코드를 이용하여 해당 서비스 상세 정보를 추출 후, 서비스 식별자 정보를 추출한다
					ServiceSC serviceSC = new ServiceSC();
					serviceSC.setSrvcCd(releaseProjectSiteKeyList.get(i).getSrvcCd());
					ServiceDto serviceDetail = serviceMgmtMapper.selectServiceDetail(serviceSC);
					if(SBNUtils.isNull(serviceDetail)) {
						errorMessage = "프로젝트 지정 해제 서비스 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					releaseProjectSiteKeyList.get(i).setSrvcSeq(serviceDetail.getSrvcSeq());
				}
				//	2.2. 채널 사이트 식별자 정보가 미 전달 시, 전달된 채널 코드 및 사이트 코드 정보를 이용하여 채널 사이트 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
				if(SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getChnlSiteSeq()) || (releaseProjectSiteKeyList.get(i).getChnlSiteSeq() <= 0L )) {
					//	2.2.1 채널 코드 및 사이트 코드 정보를 이용하여 채널 서비스 매핑 상세 정보를 추출 후, 채널 사이트 식별자 정보를 추출한다
					SiteServiceSC siteServiceSC = new SiteServiceSC();
					siteServiceSC.setChnlCd(releaseProjectSiteKeyList.get(i).getChnlCd());
					siteServiceSC.setSiteCd(releaseProjectSiteKeyList.get(i).getSiteCd());
					siteServiceSC.setSrvcCd(releaseProjectSiteKeyList.get(i).getSrvcCd());
					SiteServiceDto siteServiceDetail = siteServiceMgmtMapper.selectSiteServiceDetailByCode(siteServiceSC);
					if(SBNUtils.isNull(siteServiceDetail)) {
						errorMessage = "프로젝트 지정 해제 채널 사이트 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					releaseProjectSiteKeyList.get(i).setChnlSiteSeq(siteServiceDetail.getChnlSiteSeq());
				}				
				//	2.3. 프로젝트 식별자가 미 전달 시, 전달된 프로젝트 코드 정보를 이용하여 프로젝트 식별자 정보를 추출하여 프로젝트 사이트 지정 해제 수행 조건 객체에 설정한다
				if(SBNUtils.isNull(releaseProjectSiteKeyList.get(i).getPrjctSeq()) || (releaseProjectSiteKeyList.get(i).getPrjctSeq() <= 0L)) {
					//	2.3.1. 프로젝트 코드 정보를 이용하여 프로젝트 상세 정보를 추출 후, 프로젝트 식별자 정보를 추출한다
					ProjectSC projectSC = new ProjectSC();
					projectSC.setPrjctCd(releaseProjectSiteKeyList.get(i).getPrjctCd());
					ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
					if(SBNUtils.isNull(projectDetail)) {
						errorMessage = "지정 해제 대상 프로젝트 정보 미 존재 오류.";
						throw new QNPWebException(reason, errorCode, errorMessage);
					}
					releaseProjectSiteKeyList.get(i).setPrjctSeq(projectDetail.getPrjctSeq());
				}
				log.debug("releaseBaseProjectFromSiteForList() releaseProjectSiteKeyList[ {} ]={}", i, releaseProjectSiteKeyList.get(i).toStringInfo());
				
				//	2.4. 최종 구축된 다중 프로젝트 사이트 지정 해제 수행 조건 정보를 이용하여 프로젝트 사이트 지정 해제 작업을 수행 후, 수행 결과 갯수를 전달 받는다
				projectSiteReleaseCount = baseProjectMgmtMapper.deleteProjectAssignedSite(releaseProjectSiteKeyList.get(i));
				totalResultCount += projectSiteReleaseCount;
			}
			
			log.debug("releaseBaseProjectFromSiteForList()  releaseProjectSiteCount=[ {} ]. totalResultCount=[ {} ]", releaseProjectSiteCount, totalResultCount);
			if(releaseProjectSiteCount == totalResultCount) {
				projectSiteReleaseFlag = true;
			}
			return projectSiteReleaseFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	
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
	public Boolean checkBaseProjectCodeDuplication(ProjectSC projectSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		Boolean baseProjectCodeDuplicationFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(projectSC)) {
				errorMessage = "기본 프로젝트 코드 중복 여부 체크 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(projectSC.getPrjctCd())) {
				errorMessage = "기본 프로젝트 코드 중복 여부 체크 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	
			baseProjectCodeDuplicationFlag = baseProjectMgmtMapper.selectProjectCodeDuplication(projectSC);
			
			//	
			return baseProjectCodeDuplicationFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 목록 추출 조건 정보에 대한 프로젝트 목록 정보 추출 작업 수행 후, 추출 프로젝트 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					기본 프로젝트 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 목록 추출 조건 정보에 대한 프로젝트 목록 정보 추출 결과 목록 객체
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectDto> getBaseProjectList(ProjectSC projectSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		List<ProjectDto> baseProjectList = new ArrayList<ProjectDto>();
		try {
			//	1. 
			if(SBNUtils.isNull(projectSC)) {
				errorMessage = "기본 프로젝트 목록 정보 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 
			baseProjectList = baseProjectMgmtMapper.selectProjectList(projectSC);
			
			//	3. 
			return baseProjectList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 상세 정보 추출 조건 정보에 대한 프로젝트 상세 정보 추출 작업 수행 후, 추출 프로젝트 상세 정보 결과 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.10
	 * @param 	projectSC					기본 프로젝트 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 상세 정보 추출 조건 정보에 대한 프로젝트 상세 정보 추출 결과 객체
	 * @throws 	QNPWebException		프로젝트 기본 정보 관리 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectDto getBaseProjectDetail(ProjectSC projectSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		ProjectDto baseProjectDetail = null;
		try {
			//	1. 
			if(SBNUtils.isNull(projectSC)) {
				errorMessage = "기본 프로젝트 상세 정보 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(projectSC.getPrjctSeq()) || (projectSC.getPrjctSeq() <= 0L)) && SBNUtils.isNull(projectSC.getPrjctCd())) {
				errorMessage = "기본 프로젝트 상세 정보 추출 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}			
			
			//	2. 
			baseProjectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
			
			//	3. 
			return baseProjectDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 지정 사이트 목록 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 목록 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC	프로젝트 지정 사이트 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 지정 사이트 목록 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 목록 정보 객체
	 * @throws 	QNPWebException		프로젝트 기본 정보 지정 사이트 상세 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ProjectChannelSiteDto> getAssignedBaseProjectSiteList(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		List<ProjectChannelSiteDto> projectAssignedSiteList = new ArrayList<ProjectChannelSiteDto>();
		try {
			//	1. 
			if(SBNUtils.isNull(projectChannelSiteSC)) {
				errorMessage = "프로젝트 지정 사이트 목록 정보 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}

			//	2. 
			projectAssignedSiteList = baseProjectMgmtMapper.selectAssignedBaseProjectSiteList(projectChannelSiteSC);
			
			//	3. 
			return projectAssignedSiteList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 프로젝트 지정 사이트 상세 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.12
	 * @param 	projectChannelSiteSC		프로젝트 지정 사이트 상세 정보 추출 조건 정보 전달 객체
	 * @return		전달된 프로젝트 지정 사이트 상세 정보 추출 조건 전달 정보에 대한 프로젝트 사이트 지정 상세 정보 객체
	 * @throws 	QNPWebException			프로젝트 기본 정보 지정 사이트 상세 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public ProjectChannelSiteDto getAssignedBaseProjectSiteDetail(ProjectChannelSiteSC projectChannelSiteSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		ProjectChannelSiteDto projectAssignedSiteDetail = null;
		try {
			//	1. 
			if(SBNUtils.isNull(projectChannelSiteSC)) {
				errorMessage = "프로젝트 지정 사이트 상세 정보 추출 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	
			if(SBNUtils.isNull(projectChannelSiteSC.getSrvcSeq()) || (projectChannelSiteSC.getSrvcSeq() <= 0L)) {
				if(SBNUtils.isNull(projectChannelSiteSC.getSrvcCd())) {
					errorMessage = "프로젝트 지정 서비스 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				ServiceSC serviceSC = new ServiceSC();
				serviceSC.setSrvcCd(projectChannelSiteSC.getSrvcCd());
				ServiceDto serviceDetail = serviceMgmtMapper.selectServiceDetail(serviceSC);
				if(SBNUtils.isNull(serviceDetail)) {
					errorMessage = "프로젝트 지정 서비스 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteSC.setSrvcSeq(serviceDetail.getSrvcSeq());
			}
			//	
			if(SBNUtils.isNull(projectChannelSiteSC.getChnlSiteSeq()) || (projectChannelSiteSC.getChnlSiteSeq() <= 0L )) {
				if(SBNUtils.isNull(projectChannelSiteSC.getChnlCd()) || SBNUtils.isNull(projectChannelSiteSC.getSiteCd())) {
					errorMessage = "프로젝트 지정 채널 사이트 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				SiteServiceSC siteServiceSC = new SiteServiceSC();
				siteServiceSC.setChnlCd(projectChannelSiteSC.getChnlCd());
				siteServiceSC.setSiteCd(projectChannelSiteSC.getSiteCd());
				siteServiceSC.setSrvcCd(projectChannelSiteSC.getSrvcCd());
				SiteServiceDto siteServiceDetail = siteServiceMgmtMapper.selectSiteServiceDetailByCode(siteServiceSC);
				if(SBNUtils.isNull(siteServiceDetail)) {
					errorMessage = "프로젝트 지정 채널 사이트 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteSC.setChnlSiteSeq(siteServiceDetail.getChnlSiteSeq());
			}

			//	
			if(SBNUtils.isNull(projectChannelSiteSC.getPrjctSeq()) || (projectChannelSiteSC.getPrjctSeq() <= 0L)) {
				if(SBNUtils.isNull(projectChannelSiteSC.getPrjctCd())) {
					errorMessage = "프로젝트 키 정보 미 전달 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				ProjectSC projectSC = new ProjectSC();
				projectSC.setPrjctCd(projectChannelSiteSC.getPrjctCd());
				ProjectDto projectDetail = baseProjectMgmtMapper.selectProjectDetail(projectSC);
				if(SBNUtils.isNull(projectDetail)) {
					errorMessage = "프로젝트 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				projectChannelSiteSC.setPrjctSeq(projectDetail.getPrjctSeq());
			}
			log.debug("getAssignedBaseProjectSiteDetail() projectChannelSiteSC={}", projectChannelSiteSC.toStringInfo());
			
			//	2. 
			projectAssignedSiteDetail = baseProjectMgmtMapper.selectAssignedBaseProjectSiteDetail(projectChannelSiteSC);
			
			//	3. 
			return projectAssignedSiteDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
