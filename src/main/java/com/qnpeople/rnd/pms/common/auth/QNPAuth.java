package com.qnpeople.rnd.pms.common.auth;

import java.util.List;

import com.qnpeople.rnd.pms.types.AppAuthGroupType;
import com.qnpeople.rnd.pms.types.AppAuthGubunType;
import com.qnpeople.rnd.pms.types.AppUserType;

import kr.co.sbn.platformhub.framework.core.common.data.auth.SBNAuth;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.data.SBNJwtAccessTokenData;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.auth
 * @Filename		: QNPAuth.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션의 클라이언트 요청 수행을 위한 클라이언트의 권한 및 인증 정보 관리 및 제공 작업을 수행하는 인터페이스 클래스
 * =================================================================================
 */
public interface QNPAuth<T> extends SBNAuth {

	/**
	 * 시스템 접근자의 인증 JWT 토큰 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	jwtAccessToken	시스템 접근자의 인증 JWT 토큰 객체
	 */
	public void setJwtAccessToken(SBNJwtAccessTokenData jwtAccessToken);
	
	/**
	 * 시스템 접근자의 인증 JWT 토큰 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		시스템 접근자의 인증 JWT 토큰 객체
	 */
	public SBNJwtAccessTokenData getJwtAccessToken();
	
	/**
	 * 클라이언트의 시스템 관리 및 이용 권한 그룹 유형 목록 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	authGroupTypeList	클라이언트의 시스템 관리 및 이용 권한 그룹 유형 목록 객체
	 */
	public void setAuthGroupTypeList(List<AppAuthGroupType> authGroupTypeList);
	
	/**
	 * 클라이언트의 시스템 관리 및 이용 권한 그룹 유형 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트의 시스템 관리 및 이용 권한 그룹 유형 목록 객체
	 */
	public List<AppAuthGroupType> getAuthGroupTypeList();
	
	/**
	 * 시스템 접근 인증 구분 유형 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	appAuthGubunType		시스템 접근 인증 구분 유형 객체
	 */
	public void setAppAuthGubunType(AppAuthGubunType appAuthGubunType);
	
	/**
	 * 시스템 접근 인증 구분 유형 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		시스템 접근 인증 구분 유형 객체
	 */
	public AppAuthGubunType getAppAuthGubunType();
	
	/**
	 * 클라이언트의 유형 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	userType	클라이언트의 유형 객체
	 */
	public void setUserType(AppUserType userType);
	
	/**
	 * 클라이언트의 유형 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트의 유형 객체
	 */
	public AppUserType getUserType();
	
	/**
	 * 클라이언트 고유 식별자 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	clientSeq	클라이언트 고유 식별자 정보
	 */
	public void setClientSeq(Long clientSeq);
	
	/**
	 * 클라이언트 고유 식별자 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트 고유 식별자 정보
	 */
	public Long getClientSeq();
	
	/**
	 * 클라이언트의 시스템 로그인 ID 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	clientLoginId	클라이언트의 시스템 로그인 ID 정보
	 */
	public void setClientLoginId(String clientLoginId);
	
	/**
	 * 클라이언트의 시스템 로그인 ID 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트의 시스템 로그인 ID 정보
	 */
	public String getClientLoginId();
	
	/**
	 * 클라이언트 명 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	clientNm	클라이언트 명 정보
	 */
	public void setClientNm(String clientNm);
	
	/**
	 * 클라이언트 명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트 명 정보
	 */
	public String getClientNm();
	
	/**
	 * 클라이언트의 소속 업체의 식별자 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param	 	clientCompSeq	클라이언트의 소속 업체의 식별자 정보
	 */
	public void setClientCompSeq(Long clientCompSeq);
	
	/**
	 * 클라이언트의 소속 업체의 식별자 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트의 소속 업체의 식별자 정보
	 */
	public Long getClientCompSeq();
	
	/**
	 * 클라이언트의 소속 업체 코드 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	clientCompCd		클라이언트의 소속 업체 코드 정보
	 */
	public void setClientCompCd(String clientCompCd);
	
	/**
	 * 클라이언트의 소속 업체 코드 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트의 소속 업체 코드 정보
	 */
	public String getClientCompCd();
	
	/**
	 * 클라이언트의 소속 업체 명 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	clientCompNm	클라이언트의 소속 업체 명 정보
	 */
	public void setClientCompNm(String clientCompNm);
	
	/**
	 * 클라이언트의 소속 업체 명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트의 소속 업체 명 정보
	 */
	public String getClientCompNm();
	
	//	사용자 관리 및 이용 권한 메뉴 정의 */
	/**
	 * 사용자 관리 및 이용 권한 메뉴 목록 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	clientAuthGrpMenuList		사용자 관리 및 이용 권한 메뉴 목록 객체
	 */
	public void setClientAuthGrpMenuList(List<?> clientAuthGrpMenuList);
	
	/**
	 * 사용자 관리 및 이용 권한 메뉴 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		사용자 관리 및 이용 권한 메뉴 목록 객체
	 */
	public List<?> getClientAuthGrpMenuList();
	
	/**
	 * 사용자 관리 및 담당 프로젝트 목록 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	clientProjectList	사용자 관리 및 담당 프로젝트 목록 객체
	 */
	public void setClientProjectList(List<?> clientProjectList);
	
	/**
	 * 사용자 관리 및 담당 프로젝트 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		사용자 관리 및 담당 프로젝트 목록 객체
	 */
	public List<?> getClientProjectList();
	
	/**
	 * 클라이언트 담당 이벤트 프로젝트 목록 객체를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @param 	clientEventProjectList	클라이언트 담당 이벤트 프로젝트 목록 객체
	 */
	public void setClientEventProjectList(List<?> clientEventProjectList);
	
	/**
	 * 클라이언트 담당 이벤트 프로젝트 목록 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 * @return		클라이언트 담당 이벤트 프로젝트 목록 객체
	 */
	public List<?> getClientEventProjectList();
	
	public String toStringInfo();
}
