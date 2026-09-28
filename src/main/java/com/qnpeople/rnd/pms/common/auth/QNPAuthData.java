package com.qnpeople.rnd.pms.common.auth;

import java.util.ArrayList;
import java.util.List;

import com.qnpeople.rnd.pms.types.QNPAuthGrpupType;
import com.qnpeople.rnd.pms.types.QNPUserType;

import kr.co.sbn.platformhub.framework.core.common.data.auth.SBNAuthData;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.data.SBNJwtAccessTokenData;

/**
 * ================================================================================
 * @param <T>
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.auth
 * @Filename		: QNPAuthData.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션의 클라이언트 요청 수행을 위한 클라이언트의 권한 및 인증 정보 관리 및 제공 작업을 수행하는 인터페이스 클래스
 *  를 구현한 구현 클래스
 * =================================================================================
 */
public class QNPAuthData<T> extends SBNAuthData implements QNPAuth {

	/** 클라이언트의 권한 및 인증 수행을 위한 JWT 접근 토큰 데이터 객체 */
	protected SBNJwtAccessTokenData jwtAccessToken;
	/** 클라이언트의 시스템 관리 및 이용 권한 그룹 유형 객체 */
	protected QNPAuthGrpupType authGrpupType;
	
	/** 클라이언트의 유형 객체 */
	protected QNPUserType userType;
	/** 클라이언트 고유 식별자 정보 */
	protected Long clientSeq;
	/** 클라이언트의 시스템 로그인 ID 정보*/
	protected String clientLoginId;
	
	// 사용자 소속 업체 정보 */
	/** 클라이언트의 소속 업체의 고유 일련 번호 */
	protected Long clientCompSeq;
	/** 클라이언트의 소속 업체 코드 명 정보 */
	protected String clientCompCd;
	/** 클라이언트의 소속 업체 명 정보 */
	protected String clientCompNm;
	
	//	사용자 관리 및 이용 권한 메뉴 정의 */
	protected List<?> clientAuthGrpMenuList = new ArrayList<T>();
	
	//	사용자 관리 및 담당 프로젝트 정의 */
	protected List<?> clientProjectList = new ArrayList<T>();
	protected List<?> clientEventProjectList = new ArrayList<T>();
	
	/**
	 * 웹 어플리케이션 클라이언트 요청 수행을 위한 권한 및 인증 수행을 위한 데이터 정보를 관리하고 제공하는 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 */
	public QNPAuthData() {
		super();
	}
	
	
}
