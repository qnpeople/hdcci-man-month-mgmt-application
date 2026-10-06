package com.qnpeople.rnd.pms.apis.system.admin.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpSC;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.system.admin.service
 * @Filename		: AdminMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 관리자 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface AdminMgmtService extends QNPWebBaseService {

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
	public List<AdminDto> getSysemAdminList(AdminSC adminSC) throws QNPWebException;
	
	/**
	 * 전달된 시스템 관리자 상세 정보 조회 추출 조건 정보에 대한 수행 결과 관리자 상세 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminSC						시스템 관리자 상세 정보 조회 추출 조건 정보 전달 객체
	 * @return		전달된 시스템 관리자 상세 조회 추출 조건 정보에 대한 수행 결과 관리자 상세 정보 객체
	 * @throws 	QNPWebException		클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public AdminDto getSysemAdminDetail(AdminSC adminSC) throws QNPWebException;
	
	/**
	 * 전달된 시스템 관리자 권한 그룹 목록 추출 조건 정보에 대한 권한 그룹 매핑 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpSC		시스템 관리자의 권한 그룹 목록 정보 추출 조건 정보 전달 객체
	 * @return		전달된 시스템 관리자 권한 그룹 목록 추출 조건 정보에 대한 권한 그룹 매핑 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AdminAuthGrpDto> getSystemAdminAuthGroupList(AdminAuthGrpSC adminAuthGrpSC) throws QNPWebException;
	
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
	public Boolean registerNewSystmAdmin(AdminDto adminDto) throws QNPWebException;
	
	/**
	 * 전달된 시스템 관리자 정보에 대한 변경 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param		adminDto				변경 대상 시스템 관리자 정보 전달 객체
	 * @return		전달된 시스템 관리자 정보에 대한 변경 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean updateSystemAdmin(AdminDto adminDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 시스템 관리자 조건 정보에 대한 시스템 관리자 삭제 작업 수행 후 수행 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminSC					삭제 대상 시스템 관리자에 대한 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 시스템 관리자 조건 정보에 대한 시스템 관리자 삭제 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean deleteSystemAdmin(AdminSC adminSC) throws QNPWebException;
	
	/**
	 * 전달된 권한 그룹 지정 대상 시스템 관리자 키 목록 객체에 대한 시스템 권한 그룹 지정 수행 작업 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpList		권한 그룹 지정 대상 시스템 관리자 그룹 권한 목록 객체
	 * @return		전달된 권한 그룹 지정 대상 시스템 관리자의 그룹 권한 에 대한 시스템 권한 그룹 지정 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean assignSystemAdminAuthGroup(List<AdminAuthGrpDto> adminAuthGrpList) throws QNPWebException;
	
	/**
	 * 전달된 권한 그룹 해제 대상 시스템 관리자 키 목록 객체에 대한 시스템 권한 그룹 해제 수행 작업 결과 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpList		권한 그룹 해제 대상 시스템 관리자 권한 그룹 목록 객체
	 * @return		전달된 권한 그룹 해제 대상 시스템 관리자에 대한 권한 그룹 목록 객체에 대한 시스템 권한 그룹 해제 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException		클라이언트의 요청에 대한 실질적인 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean releaseSystemAdminAuthGroup(List<AdminAuthGrpDto> adminAuthGrpList) throws QNPWebException;
}
