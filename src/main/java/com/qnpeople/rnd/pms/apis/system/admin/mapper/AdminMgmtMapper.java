package com.qnpeople.rnd.pms.apis.system.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpSC;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.system.admin.mapper
 * @Filename		: AdminMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 영역의 관리자 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface AdminMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////	
	/**
	 * 전달된 시스템 관리자 목록 조회 조건 정보에 대한 시스템 관리자의 목록 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminSC					시스템 관리자 목록 조회 조건 정보 전달 객체
	 * @return		전달된 시스템 관리자 목록 조회 조건 정보에 대한 시스템 관리자의 목록 객체
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AdminDto> selectSystemAdminList(AdminSC adminSC) throws QNPWebException;
	
	/**
	 * 전달된 시스템 관리자의 상세 정보 조회 조건 정보에 대한 시스템 관리자의 상세 정보 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminSC					시스템 관리자의 상세 정보 조회 조건 정보 전달 객체
	 * @return		전달된 시스템 관리자의 상세 정보 조회 조건 정보에 대한 시스템 관리자의 상세 정보 객체
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public AdminDto selectSystemAdminDetail(AdminSC adminSC) throws QNPWebException;
	
	/**
	 * 전달된 시스템 관리자의 권한 그룹 목록 추출 조건 정보에 대한 시스템 관리자 권한 그룹 목록 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpSC		시스템 관리자의 권한 그룹 목록 추출 조건 정보 전달 객체
	 * @return		전달된 시스템 관리자의 권한 그룹 목록 추출 조건 정보에 대한 시스템 관리자 권한 그룹 목록 객체
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AdminAuthGrpDto> selectSystemAdminAuthGroupList(AdminAuthGrpSC adminAuthGrpSC) throws QNPWebException;
	
	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 신규 등록 대상 시스템 관리자 정보에 대한 DB 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminDto				신규 등록 대상 시스템 관리자 정보 전달 객체
	 * @return		전달된 신규 등록 대상 시스템 관리자 정보에 대한 등록 작업 수행 결과 갯수. 정상 등록 시 등록 갯수, 그렇지 않으면 0 을 전달
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertNewSystemAdmin(AdminDto adminDto) throws QNPWebException;
	
	/**
	 * 전달된 변경 대상 시스템 관리자 정보에 대한 DB 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminDto				변경 대상 시스템 관리자 정보 전달 객체
	 * @return		전달된 변경 대상 시스템 관리자 정보에 대한 변경 작업 수행 결과 갯수. 정상 변경 시 변경 갯수, 그렇지 않으면 0 을 전달
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateSystemAdmin(AdminDto adminDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 시스템 관리자 조건 정보에 대한 DB 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminSC					삭제 대상 시스템 관리자 조건 정보 전달 객체
	 * @return		전달된 삭제 대상 시스템 관리자 조건 정보에 대한 삭제 작업 수행 갯수, 정상 삭제 시 삭제 갯수, 그렇지 않으면 0을 전달
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteSystemAdmin(AdminSC adminSC) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보에 대한 시스템 관리자 지정 권한 그룹 매핑 정보 일괄 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	adminSC					관리자 삭제 작업 후 지정되어 있던 권한 그룹 매핑 정보 삭제 수행 조건 전달 객체
	 * @return		전달된 조건 정보에 대한 관리자 지정 권한 그룹 매핑 정보 일괄 삭제 작업 수행 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 을 전달
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteSystemAdminAssignedAllAuthGroup(AdminSC adminSC) throws QNPWebException;
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	시스템 관리자 권한 그룹 매핑 수행 모듈 정의
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 등록 대상 시스템 관리자 권한 그룹 키 정보 전달 객체에 대한 DB 권한 그룹 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpDto	지정 대상 시스템 관리자 권한 그룹 정보 전달 객체
	 * @return		전달된 등록 대상 시스템 관리자 권한 그룹 키 정보 전달 객체에 대한 권한 그룹 등록 작업 수행 결과 갯수, 정상 등록 시 등록 갯수, 그렇지 않은 경우 0을 전달
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertSysetmAdminAuthGroup(AdminAuthGrpDto adminAuthGrpDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 시스템 관리자 권한 그룹 정보에 대한 DB 권한 그룹 매핑 정보 삭제 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.01
	 * @param 	adminAuthGrpDto		해지 대상 시스템 관리자 권한 그룹 정보 전달 객체
	 * @return		전달된 삭제 대상 시스템 관리자 권한 그룹 정보에 대한 권한 그룹 매핑 정보 삭제 수행 결과 갯수, 정상 삭제 시 삭제 갯수, 그렇지 않은 경우 0을 전달
	 * @throws 	QNPWebException	요청 수행을 위한 DB 작업 쿼리 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteSystemAdminAuthGroup(AdminAuthGrpDto adminAuthGrpDto) throws QNPWebException;
}
