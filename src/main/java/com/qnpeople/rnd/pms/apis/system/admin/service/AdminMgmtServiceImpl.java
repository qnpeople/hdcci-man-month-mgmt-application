package com.qnpeople.rnd.pms.apis.system.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.system.admin.mapper.AdminMgmtMapper;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpSC;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.system.admin.service
 * @Filename		: AdminMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 관리자 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class AdminMgmtServiceImpl extends QNPWebBaseServiceImpl implements AdminMgmtService {

	/* 시스템 관리자 관리 작업을 위한 DB 작업 수행 쿼리 매핑 인터페이스 객체 */
	@Autowired
	private AdminMgmtMapper adminMgmtMapper;
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////	
	/**
	 * 전달된 시스템 관리자 목록 조회 추출 조건 정보에 대한 수행 결과 관리자 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminSC						시스템 관리자 목록 조회 추출 조건 정보 전달 객체
	 * @return		전달된 시스템 관리자 목록 조회 추출 조건 정보에 대한 수행 결과 관리자 목록 객체
	 * @throws 	QNPWebException		클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AdminDto> getSysemAdminList(AdminSC adminSC) throws QNPWebException {		
		List<AdminDto> systemAdminList = new ArrayList<AdminDto>();
		try {
			systemAdminList = adminMgmtMapper.selectSystemAdminList(adminSC);
			return systemAdminList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 시스템 관리자 상세 정보 조회 추출 조건 정보에 대한 수행 결과 관리자 상세 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminSC						시스템 관리자 상세 정보 조회 추출 조건 정보 전달 객체
	 * @return		전달된 시스템 관리자 상세 조회 추출 조건 정보에 대한 수행 결과 관리자 상세 정보 객체
	 * @throws 	QNPWebException		클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public AdminDto getSysemAdminDetail(AdminSC adminSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		AdminDto adminDto = null;
		try {
			if(SBNUtils.isNull(adminSC) ||
			   ((SBNUtils.isNull(adminSC.getAdmSeq()) || (adminSC.getAdmSeq() <= 0L)) &&
				(SBNUtils.isNull(adminSC.getAdmLognId())))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 상세 조회 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			adminDto = adminMgmtMapper.selectSystemAdminDetail(adminSC);			
			return adminDto;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 시스템 관리자 권한 그룹 목록 추출 조건 정보에 대한 권한 그룹 매핑 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpSC		시스템 관리자의 권한 그룹 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 시스템 관리자 권한 그룹 목록 추출 조건 정보에 대한 권한 그룹 매핑 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AdminAuthGrpDto> getSystemAdminAuthGroupList(AdminAuthGrpSC adminAuthGrpSC) throws QNPWebException {		
		List<AdminAuthGrpDto> adminAuthGroupList = new ArrayList<AdminAuthGrpDto>();
		try {
			adminAuthGroupList = adminMgmtMapper.selectSystemAdminAuthGroupList(adminAuthGrpSC);
			return adminAuthGroupList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 신규 등록 대상 시스템 관리자 정보 객체에 대한 등록 작업을 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminDto				신규 등록 대상 시스템 관리자 정보 전달 객체
	 * @return		전달된 신규 등록 대상 관리자 정보에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerNewSystmAdmin(AdminDto adminDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer insertCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(adminDto)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 시스템 관리자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			insertCount = adminMgmtMapper.insertNewSystemAdmin(adminDto);
			if(insertCount > 0) {
				resultFlag = Boolean.TRUE;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 시스템 관리자 정보에 대한 변경 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param		adminDto				변경 대상 시스템 관리자 정보 전달 객체
	 * @return		전달된 시스템 관리자 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateSystemAdmin(AdminDto adminDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		AdminSC adminSC = null;
		AdminDto updateAdminDetail = null;
		Integer updateCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 변경 할 시스템 관리자 정보에 대한 유효성 체크 작업을 수행한다
			if(SBNUtils.isNull(adminDto)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 시스템 관리자 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1.1. 변경 대상 시스템 관리자 정보의 관리자 식별자 정보가 미 정의 시, 예외 처리 후 작업을 종료한다
			if(SBNUtils.isNull(adminDto.getAdmSeq()) || (adminDto.getAdmSeq() <= 0L)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 시스템 관리자 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	2. 시스템 관리자 정보 변경 작업 수행을 위한 관리자 정보 존재 여부 체크 작업을 수행한다
			adminSC = new AdminSC();
			adminSC.setAdmSeq(adminDto.getAdmSeq());
			adminSC.setAdmLognId(adminDto.getAdmLognId());
			//	
			updateAdminDetail = adminMgmtMapper.selectSystemAdminDetail(adminSC);
			if(SBNUtils.isNull(updateAdminDetail)) {
				//	2.1. 변경 대상 시스템 관리자의 정보가 미 존재 시, 예외 처리 후 작업을 종료 한다
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "변경 대상 시스템 관리자 정보 미 존재 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			
			//	3. 변경 대상 시스템 관리자 정보 변경 작업을 수행한다
			updateCount = adminMgmtMapper.updateSystemAdmin(adminDto);
			if(updateCount > 0) {
				//	3.1. 변경 작업 정상 수행 시, 최종 수행 결과 Flag 를 true 로 설정한다
				resultFlag = Boolean.TRUE;
			}
			
			//	4. 최종 작업 수행 결과 Flag 를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 삭제 대상 시스템 관리자 조건 정보에 대한 시스템 관리자 삭제 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminSC					삭제 대상 시스템 관리자에 대한 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 시스템 관리자 조건 정보에 대한 시스템 관리자 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteSystemAdmin(AdminSC adminSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";		
		Integer deleteAdminCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			//	1. 시스템 관리자 삭제 작업을 위한 조건 정보 객체 및 조건 키 정보 전달 여부를 체크하여, 조건 정보가 미 전달 시, 예외 처리 수행 후 작업을 종료한다
			if(SBNUtils.isNull(adminSC)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 시스템 관리자 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(adminSC.getAdmSeq()) || (adminSC.getAdmSeq() <= 0L)) && SBNUtils.isNull(adminSC.getAdmLognId())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 시스템 관리자 조건 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(adminSC.getAdmSeq())) {
				//	1.1. 삭제 대상 관리자 식별자 정보가 미 전달 시, 관리자 정보 삭제 후, 지정 권한 그룹 매핑 정보 일괄 삭제를 위한 시스템 관리자 식별자 정보를 추출한다
				AdminDto deleteAdminDetail = adminMgmtMapper.selectSystemAdminDetail(adminSC);
				if(SBNUtils.isNull(deleteAdminDetail)) {
					reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
					errorCode = reason.getReasonCode();
					errorMessage = "삭제 대상 시스템 관리자 정보 미 존재 오류.";
					throw new QNPWebException(reason, errorCode, errorMessage);
				}
				adminSC.setAdmSeq(deleteAdminDetail.getAdmSeq());
			}
			log.debug("deleteSystemAdmin() adminSC=[ {} ]", adminSC.toStringInfo());
						
			//	2. 시스템 관리자 삭제 조건 정보 정상 전달 시, 해당 시스템 관리자 정보 삭제 처리를 수행한다
			deleteAdminCount = adminMgmtMapper.deleteSystemAdmin(adminSC);
			log.debug("deleteSystemAdmin() deleteAdminCount=[ {} ]", deleteAdminCount);
			
			//	3. 시스템 관리자 삭제 처리 작업이 정상 수행 시, 수행 결과 Flag 를 true 로 설정한다
			if(deleteAdminCount > 0) {
				resultFlag = Boolean.TRUE;
			}
			
			//	4. 삭제된 시스템 관리자가 존재 시(실제 삭제 처리 시), 삭제된 관리자에 지정된 권한 그룹 정보를 일괄 삭제 처리 한다
			if(deleteAdminCount > 0) {
				Integer deleteAuthGrpCount = adminMgmtMapper.deleteSystemAdminAssignedAllAuthGroup(adminSC);
				log.debug("deleteSystemAdmin() deleteAuthGrpCount=[ {} ]", deleteAuthGrpCount);
			}
			
			//	5. 최종 수행 결과 Flag 정보를 전달 후 작업을 종료한다
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 권한 그룹 지정 대상 시스템 관리자 키 목록 객체에 대한 시스템 권한 그룹 지정 수행 작업 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpList		권한 그룹 지정 대상 시스템 관리자 그룹 권한 목록 객체
	 * @return		전달된 권한 그룹 지정 대상 시스템 관리자의 그룹 권한 에 대한 시스템 권한 그룹 지정 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignSystemAdminAuthGroup(List<AdminAuthGrpDto> adminAuthGrpList) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer totAuthGrpCount = 0;
		//
		Integer insertCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(adminAuthGrpList)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 지정 대상 권한 그룹 키 목록 객체 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			totAuthGrpCount = adminAuthGrpList.size();
			for(int i = 0; i < adminAuthGrpList.size(); i++) {
				log.debug("assignSystemAdminAuthGroup() adminAuthGrpList[{}]={}", i, adminAuthGrpList.get(i).toStringInfo());
				if(!SBNUtils.isNull(adminAuthGrpList.get(i).getAdmSeq()) && (adminAuthGrpList.get(i).getAdmSeq() > 0)) {
					Integer tmpInsertCount = adminMgmtMapper.insertSysetmAdminAuthGroup(adminAuthGrpList.get(i));
					if(tmpInsertCount > 0) {
						insertCount += tmpInsertCount;
					}
				} else {
					log.debug("assignSystemAdminAuthGroup() 관리자 식별자 미 정의: {} ", adminAuthGrpList.get(i).toStringInfo());
				}
			}
			log.debug("assignSystemAdminAuthGroup() totAuthGrpCount=[ {} ]. insertCount=[ {} ]", totAuthGrpCount, insertCount);
			if(totAuthGrpCount == insertCount) {
				resultFlag = Boolean.TRUE;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 권한 그룹 해제 대상 시스템 관리자 키 목록 객체에 대한 시스템 권한 그룹 해제 수행 작업 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpList		권한 그룹 해제 대상 시스템 관리자 권한 그룹 목록 객체
	 * @return		전달된 권한 그룹 해제 대상 시스템 관리자에 대한 권한 그룹 목록 객체에 대한 시스템 권한 그룹 해제 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseSystemAdminAuthGroup(List<AdminAuthGrpDto> adminAuthGrpList) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Integer totAuthGrpCount = 0;
		//
		Integer deleteCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(adminAuthGrpList)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 관리자 해지 대상 권한 그룹 목록 객체 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			totAuthGrpCount = adminAuthGrpList.size();
			for(int i = 0; i < adminAuthGrpList.size(); i++) {
				log.debug("releaseSystemAdminAuthGroup() adminAuthGrpList[{}]={}", i, adminAuthGrpList.get(i).toStringInfo());
				if(!SBNUtils.isNull(adminAuthGrpList.get(i).getAdmSeq()) && (adminAuthGrpList.get(i).getAdmSeq() > 0)) {
					Integer tmpInsertCount = adminMgmtMapper.deleteSystemAdminAuthGroup(adminAuthGrpList.get(i));
					if(tmpInsertCount > 0) {
						deleteCount += tmpInsertCount;
					}
				} else {
					log.debug("releaseSystemAdminAuthGroup() 관리자 식별자 미 정의: {} ", adminAuthGrpList.get(i).toStringInfo());
				}
			}
			log.debug("releaseSystemAdminAuthGroup() totAuthGrpCount=[ {} ]. deleteCount=[ {} ]", totAuthGrpCount, deleteCount);
			if(totAuthGrpCount == deleteCount) {
				resultFlag = Boolean.TRUE;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
