package com.qnpeople.rnd.pms.apis.system.authgrp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpMenuDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.system.authgrp.mapper
 * @Filename		: AuthGrpMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.02.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 영역의 권한 그룹 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface AuthGrpMgmtMapper extends QNPWebBaseMapper {

	/**
	 * 전달된 조건 정보에 대한 시스템 권한 그룹 목록 정보를 DB로부터 추출하여 목록 객체 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 목록 추출 작업을 위한 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 시스템 권한 그룹 목록 객체
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AuthGrpDto> selectSystemAuthGroupList(AuthGrpSC authGrpSC) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보에 대한 시스템 권한 그룹 상세 정보를 DB로부터 추출하여 상세 정보 객체 구성 후 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 상세 추출 작업을 위한 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 시스템 권한 그룹 목록 객체
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public AuthGrpDto selectSystemAuthGroupDetail(AuthGrpSC authGrpSC) throws QNPWebException;
	
	/**
	 * 전달된 신규 등록 대상 권한 그룹 정보에 대한 DB 등록 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpDto				신규 등록 대상 권한 그룹 정보 전달 객체
	 * @return		전달된 신규 등록 대상 권한 그룹 정보에 대한 등록 작업 수행 결과 갯수. 정상 등록 시 등록 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertSystemAuthGroup(AuthGrpDto authGrpDto) throws QNPWebException;
	
	/**
	 * 전달된 변경 대상 권한 그룹 정보에 대한 DB 변경 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpDto				변경 대상 그룹 권한 정보 전달 객체
	 * @return		전달된 변경 대상 그룹 권한 정보에 대한 변경 작업 수행 결과 갯수, 정상 수행 시 변경 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateSystemAuthGroup(AuthGrpDto authGrpDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 권한 그룹 정보에 대한 DB 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				삭제 대상 그룹 권한 키 정보 전달 객체
	 * @return		전달된 삭제 대상 그룹 권한 키 정보에 대한 삭제 작업 수행 결과 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteSystemAuthGroup(AuthGrpSC authGrpSC) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 권한 그룹 키 정보에 대해 사용 지정되어 있는 모든 메뉴 매핑 정보에 대한 DB 삭제 작업 수행 후, 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				삭제 대상 권한 그룹 키 정보 전달 객체
	 * @return		전달된 삭제 대상 권한 그룹 키 정보에 대해 사용 지정되어 있는 모든 메뉴 매핑 정보 삭제 작업 결과 갯수, 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteSystemAuthGrpAssignedAllMenu(AuthGrpSC authGrpSC) throws QNPWebException;
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//
	//////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 전달된 조건 정보에 대한 신규 등록 대상 권한 그룹 코드 중복 여부를 DB로부터 체크 후 체크 수행 결과를 전달하는 메소드
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.03
	 * @param 	authGrpSC				신규 등록 대상 그룹 권한 코드 중복 여부 체크 수행 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 신규 등록 대상 권한 그룹 코드 중복 여부 체크 결과 Flag. 중복인 경우 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean isSystemAuthGroupCodeDuplcated(AuthGrpSC authGrpSC) throws QNPWebException;
	
	/**
	 * 전달된 권한 그룹 키 정보에 대해 매핑되어 있는 사용 가능 메뉴에 대한 Tree 목록 객체를 DB로부터 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpSC				시스템 권한 그룹 매핑 서비스 메뉴 추출 조건 정보 전달 객체
	 * @return		전달된 권한 그룹 키 정보에 대해 매핑되어 있는 사용 가능 메뉴에 대한 Tree 목록 객체
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public List<AuthGrpMenuDto> selectSystemAuthGroupMenuTreeList(AuthGrpSC authGrpSC) throws QNPWebException;
	
	/**
	 * 전달된 권한 그룹의 지정 대상 메뉴 정보에 대한 DB 등록 작업 수행 후, 등록 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param		authGrpMenuDto		시스템 권한 그룹에 지정할 사용 메뉴 정보 전달 객체
	 * @return		전달된 권한 그룹에 지정할 메뉴 정보에 대한 등록 작업 수행 결과 갯수, 정상 수행 시 등록 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertSystemAuthGroupMenu(AuthGrpMenuDto authGrpMenuDto) throws QNPWebException;
	
	/**
	 * 전달된 권한 그룹의 해제 대상 메뉴 정보에 대한 DB 삭제 작업 수행 후, 삭제 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.02
	 * @param 	authGrpMenuDto
	 * @return		전다뢴 권한 그룹에서 해제할 메뉴 정보에 대한 삭제 작업 수행 결과 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	클라이언트 요청 수행을 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteSystemAuthGroupMenu(AuthGrpMenuDto authGrpMenuDto) throws QNPWebException;	
}
