package com.qnpeople.rnd.pms.common.auth;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.types.AppAuthGroupType;
import com.qnpeople.rnd.pms.types.AppAuthGubunType;
import com.qnpeople.rnd.pms.types.AppUserType;

import kr.co.sbn.platformhub.framework.core.common.data.auth.SBNAuthData;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import kr.co.sbn.platformhub.framework.securities.domains.jwt.data.SBNJwtAccessTokenData;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Data
public class QNPAuthData<T> extends SBNAuthData implements QNPAuth<T> {

	/** 클라이언트의 권한 및 인증 수행을 위한 JWT 접근 토큰 데이터 객체 */
	protected SBNJwtAccessTokenData jwtAccessToken;
	/** 클라이언트의 시스템 관리 및 이용 권한 그룹 유형 객체 */
	protected List<AppAuthGroupType> authGroupTypeList;
	/** 시스템 접근 인증 구분 유형 */
	protected AppAuthGubunType appAuthGubunType;
	
	/** 클라이언트의 유형 객체 */
	protected AppUserType userType;
	/** 클라이언트 고유 식별자 정보 */
	protected Long clientSeq;
	/** 클라이언트의 시스템 로그인 ID 정보*/
	protected String clientLoginId;
	/** 클라이언트 명 정보 */
	protected String clientNm;
	
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
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(jwtAccessToken)) {
			info.append(", ");
			info.append("jwtAccessToken=").append(jwtAccessToken.toStringInfo());
		}
		if(!SBNUtils.isNull(authGroupTypeList)) {
			info.append(", ");
			info.append("authGroupTypeList=").append(authGroupTypeList);
		}
		if(!SBNUtils.isNull(appAuthGubunType)) {
			info.append(", ");
			info.append("appAuthGubunType=").append(appAuthGubunType);
		}
		if(!SBNUtils.isNull(userType)) {
			info.append(", ");
			info.append("userType=").append(userType);
		}
		if(!SBNUtils.isNull(clientSeq)) {
			info.append(", ");
			info.append("clientSeq=").append(clientSeq);
		}
		if(!SBNUtils.isNull(clientLoginId)) {
			info.append(", ");
			info.append("clientLoginId=").append(clientLoginId);
		}
		if(!SBNUtils.isNull(clientNm)) {
			info.append(", ");
			info.append("clientNm=").append(clientNm);
		}
		if(!SBNUtils.isNull(clientCompCd)) {
			info.append(", ");
			info.append("clientCompCd=").append(clientCompCd);
		}
		if(!SBNUtils.isNull(clientCompNm)) {
			info.append(", ");
			info.append("clientCompNm=").append(clientCompNm);
		}
		if(!SBNUtils.isNull(clientAuthGrpMenuList)) {
			info.append(", ");
			info.append("clientAuthGrpMenuList=").append(clientAuthGrpMenuList);
		}
		if(!SBNUtils.isNull(clientProjectList)) {
			info.append(", ");
			info.append("clientProjectList=").append(clientProjectList);
		}
		if(!SBNUtils.isNull(clientEventProjectList)) {
			info.append(", ");
			info.append("clientEventProjectList=").append(clientEventProjectList);
		}
		info.append(" }");
		return info.toString();
	}
	
}
