package com.qnpeople.rnd.pms.apis.system.authgrp.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpMenuDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.system.authgrp.service
 * @Filename		: AuthGrpMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.02.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 영역의 권한 그룹 정보 관리를 위한 실질적인 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
public interface AuthGrpMgmtService extends QNPWebBaseService {

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
	public Boolean registerSystemAuthGroup(AuthGrpDto authGrpDto) throws QNPWebException;
	
	/**
	 * 클라이언트의 시스템 권한 정보 변경 요청에 대한 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpDto				변경 대상 시스템 권한 그룹 정보 객체
	 * @return		전달된 변경 대상 시스템 권한 그룹 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateSystemAuthGroup(AuthGrpDto authGrpDto) throws QNPWebException;
	
	/**
	 * 	클라이언트의 시스템 권한 정보 삭제 요청에 대한 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				삭제 대상 시스템 권한 그룹 키 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 시스템 권한 정보 키에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteSystemAuthGroup(AuthGrpSC authGrpSC) throws QNPWebException;
	
	/**
	 * 클라이언트의 시스템 권한 정보에 대한 사용 메뉴 지정 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpMenuList		시스템 권한 그룹에 지정할 권한 그룹 메뉴 목록 객체
	 * @return		전달된 권한 그룹 지정 대상 메뉴 목록 객체에 대한 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignSystemAuthGroupMenu(List<AuthGrpMenuDto> authGrpMenuList) throws QNPWebException;
	
	/**
	 * 클라이언트의 시스템 권한 정보에 대한 사용 메뉴 해제 작업 수행 후, 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpMenuList		시스템 권한 그룹에서 해제할 권한 그룹 메뉴 목록 객체
	 * @return		전달된 시스템 권한 그룹 해제 대상 메뉴 목록 객체에 대한 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseSystemAuthGroupMenu(List<AuthGrpMenuDto> authGrpMenuList) throws QNPWebException;
	
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
	public Boolean isSystemAuthGroupCdDuplicated(AuthGrpSC authGrpSC) throws QNPWebException;
	
	/**
	 * 전달된 조회 조건 정보에 대한 시스템 권한 그룹 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 목록 조회 조건 전달 객체
	 * @return		전달된 조회 조건 정보에 대한 시스템 권한 그룹 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AuthGrpDto> getSystemAuthGroupList(AuthGrpSC authGrpSC) throws QNPWebException;
	
	/**
	 * 전달된 조회 조건 정보에 대한 시스템 권한 그룹 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 상세 정보 조회 조건 전달 객체
	 * @return		전달된 조회 조건 정보에 대한 시스템 권한 그룹 상세 정보 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public AuthGrpDto getSystemAuthGroupDetail(AuthGrpSC authGrpSC) throws  QNPWebException;
	
	/**
	 * 시스템 권한 그룹 지정 사용 메뉴에 대한 Tree 목록 객체 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 지정 사용 메뉴에 대한 Tree 목록 객체 추출 조건 전달 객체
	 * @return		시스템 권한 그룹 지정 사용 메뉴에 대한 Tree 목록 객체 추출 결과 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AuthGrpMenuDto> getSystemAuthGroupMenuTreeList(AuthGrpSC authGrpSC) throws  QNPWebException;
}
