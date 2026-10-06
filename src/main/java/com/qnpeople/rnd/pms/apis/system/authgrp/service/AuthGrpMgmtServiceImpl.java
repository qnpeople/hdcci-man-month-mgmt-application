package com.qnpeople.rnd.pms.apis.system.authgrp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.system.authgrp.mapper.AuthGrpMgmtMapper;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpMenuDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.system.authgrp.service
 * @Filename		: AuthGrpMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.02.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 영역의 권한 그룹 정보 관리를 위한 실질적인 작업을 수행하기 위한 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class AuthGrpMgmtServiceImpl extends QNPWebBaseServiceImpl implements AuthGrpMgmtService {

	/* 시스템의 권한 그룹 관리를 위한 DB 작업 수행 SQL 쿼리 매핑 인터페이스 객체 */
	@Autowired
	private AuthGrpMgmtMapper authGrpMgmtMapper;
	

	//////////////////////////////////////////////////////////////////////////////
	//	시스템 권한 그룹 관리 모듈 정의
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * 클라이언트의 시스템 권한 그룹 정보 신규 등록 요청에 대한 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpDto				신규 등록 대상 시스템 권한 그룹 정보 객체
	 * @return		전달된 신규 등록 대상 시스템 권한 정보에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean registerSystemAuthGroup(AuthGrpDto authGrpDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//	
		AuthGrpSC authGrpSC = null;
		Integer insertCount = 0;
		Boolean resultFlag = false;
		try {
			if(SBNUtils.isNull(authGrpDto)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 시스템 권한 그룹 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(authGrpDto.getAuthGrpCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 시스템 권한 그룹 코드 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			//	1. 	
			authGrpSC = new AuthGrpSC();
			authGrpSC.setAuthGrpCd(authGrpDto.getAuthGrpCd());
			
			//	2. 
			resultFlag = authGrpMgmtMapper.isSystemAuthGroupCodeDuplcated(authGrpSC);
			if(resultFlag) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "이미 존재하는 시스템 권한 그룹 코드 입니다.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			log.debug("registerSystemAuthGroup() duplicationFlag=[ {} ]", resultFlag);
			//	3. 
			insertCount = authGrpMgmtMapper.insertSystemAuthGroup(authGrpDto);
			if(insertCount > 0) {
				resultFlag = true;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 클라이언트의 시스템 권한 정보 변경 요청에 대한 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpDto				변경 대상 시스템 권한 그룹 정보 객체
	 * @return		전달된 변경 대상 시스템 권한 그룹 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateSystemAuthGroup(AuthGrpDto authGrpDto) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//			
		Integer updateCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(authGrpDto)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 시스템 권한 그룹 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(authGrpDto.getAuthGrpSeq()) || (authGrpDto.getAuthGrpSeq() <= 0)) &&
				SBNUtils.isNull(authGrpDto.getAuthGrpCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "신규 등록 대상 시스템 권한 그룹 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			updateCount = authGrpMgmtMapper.updateSystemAuthGroup(authGrpDto);
			if(updateCount > 0) {
				resultFlag = true;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 	클라이언트의 시스템 권한 정보 삭제 요청에 대한 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				삭제 대상 시스템 권한 그룹 키 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 시스템 권한 정보 키에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteSystemAuthGroup(AuthGrpSC authGrpSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		//			
		Integer deleteCount = 0;
		Boolean resultFlag = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(authGrpSC)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 시스템 권한 그룹 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(authGrpSC.getAuthGrpSeq()) || (authGrpSC.getAuthGrpSeq() <= 0)) &&
				SBNUtils.isNull(authGrpSC.getAuthGrpCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "삭제 대상 시스템 권한 그룹 키 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			deleteCount = authGrpMgmtMapper.deleteSystemAuthGroup(authGrpSC);
			//	
			Integer deleteAuthGroupMenu = authGrpMgmtMapper.deleteSystemAuthGrpAssignedAllMenu(authGrpSC);
			log.debug("deleteSystemAuthGroup() deleteAuthGroupMenu=[ {} ]", deleteAuthGroupMenu);
			if(deleteCount > 0) {				
				resultFlag = true;
			}
			return resultFlag;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 클라이언트의 시스템 권한 정보에 대한 사용 메뉴 지정 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpMenuList		시스템 권한 그룹에 지정할 권한 그룹 메뉴 목록 객체
	 * @return		전달된 권한 그룹 지정 대상 메뉴 목록 객체에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignSystemAuthGroupMenu(List<AuthGrpMenuDto> authGrpMenuList) throws QNPWebException {
		
		try {
			
			return false;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 클라이언트의 시스템 권한 정보에 대한 사용 메뉴 해제 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpMenuList		시스템 권한 그룹에서 해제할 권한 그룹 메뉴 목록 객체
	 * @return		전달된 시스템 권한 그룹 해제 대상 메뉴 목록 객체에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseSystemAuthGroupMenu(List<AuthGrpMenuDto> authGrpMenuList) throws QNPWebException {
		
		try {
			
			return false;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//	시스템 권한 그룹 서비스 모듈 정의
	//////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 권한 그룹 코드 중복 체크 여부 수행 조건 객체에 대한 그룹 권한 코드 중복 여부 체크 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.03
	 * @param 	authGrpSC				시스템 권한 그룹 코드에 대한 중복 여부 체크 수행 조건 정보 전달 객체
	 * @return		전달된 권한 그룹 코드 중복 체크 여부 수행 조건 객체에 대한 그룹 권한 코드 중복 여부 체크 수행 결과 Flag. 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean isSystemAuthGroupCdDuplicated(AuthGrpSC authGrpSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		Boolean isAuthGrpDuplicated = Boolean.FALSE;
		try {
			if(SBNUtils.isNull(authGrpSC)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 코드 중복 체크 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(authGrpSC.getAuthGrpCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 코드 중복 체크 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			isAuthGrpDuplicated = authGrpMgmtMapper.isSystemAuthGroupCodeDuplcated(authGrpSC);
			return isAuthGrpDuplicated;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조회 조건 정보에 대한 시스템 권한 그룹 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 목록 조회 조건 전달 객체
	 * @return		전달된 조회 조건 정보에 대한 시스템 권한 그룹 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AuthGrpDto> getSystemAuthGroupList(AuthGrpSC authGrpSC) throws  QNPWebException {
		List<AuthGrpDto> systemAuthGroupList = new ArrayList<AuthGrpDto>();
		try {
			systemAuthGroupList = authGrpMgmtMapper.selectSystemAuthGroupList(authGrpSC);
			return systemAuthGroupList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조회 조건 정보에 대한 시스템 권한 그룹 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 상세 정보 조회 조건 전달 객체
	 * @return		전달된 조회 조건 정보에 대한 시스템 권한 그룹 상세 정보 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public AuthGrpDto getSystemAuthGroupDetail(AuthGrpSC authGrpSC) throws  QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		AuthGrpDto authGroupDetail = null;
		try {
			if(SBNUtils.isNull(authGrpSC)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 상세 조회 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(authGrpSC.getAuthGrpSeq()) || (authGrpSC.getAuthGrpSeq() <= 0)) &&
				SBNUtils.isNull(authGrpSC.getAuthGrpCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 상세 조회 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			authGroupDetail = authGrpMgmtMapper.selectSystemAuthGroupDetail(authGrpSC);
			return authGroupDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 시스템 권한 그룹 지정 사용 메뉴에 대한 Tree 목록 객체 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 지정 사용 메뉴에 대한 Tree 목록 객체 추출 조건 전달 객체
	 * @return		시스템 권한 그룹 지정 사용 메뉴에 대한 Tree 목록 객체 추출 결과 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AuthGrpMenuDto> getSystemAuthGroupMenuTreeList(AuthGrpSC authGrpSC) throws  QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		List<AuthGrpMenuDto> systemAuthGrpMenuList = new ArrayList<AuthGrpMenuDto>();
		try {
			if(SBNUtils.isNull(authGrpSC)) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 지정 메뉴 목록 조회 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			if((SBNUtils.isNull(authGrpSC.getAuthGrpSeq()) || (authGrpSC.getAuthGrpSeq() <= 0)) &&
				SBNUtils.isNull(authGrpSC.getAuthGrpCd())) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "시스템 권한 그룹 지정 메뉴 목록 조회 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			systemAuthGrpMenuList = authGrpMgmtMapper.selectSystemAuthGroupMenuTreeList(authGrpSC);
			return systemAuthGrpMenuList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
