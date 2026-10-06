package com.qnpeople.rnd.pms.apis.system.authgrp.domain.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.authgrp.model.AuthGrpMenuDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class AuthGrpMgmtRequest extends QNPWebClientRequestWrapper {
	
	/* 권한 그룹 유형(공통코드: SYS_AUTH_GRP_001) 01:통합 권한 그룹, 02:시스템 권한 그룹, 03:서비스 권한 그룹, 04:프로젝트 관리자 그룹, 05:프로젝트 수행자 그룹, 99:기타 */
	private String authGrpTp;
	/* 권한 그룹 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 권한 그룹 명 */
	private String authGrpNm;
	
	/* 권한 그룹 상세 조회 시 권한 그룹 식별자 정보 */
	private Long authGrpSeq;
	/* 권한 그룹 상세 조회 시 권한 그룹 코드 정보 */
	private String authGrpCd;
	
	/* 권한 그룹 등록/변경 작업 수행 객체 */
	private AuthGrpDto authGroup;	
	/* 권한 그룹별 지정/해제 사용 메뉴 목록 객체 */
	private List<AuthGrpMenuDto> authGrpMenuList;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(txId)) {
			info.append(", ");
			info.append("txId= ").append(txId);
		}
		if(!SBNUtils.isNull(authGrpSeq)) {
			info.append(", ");
			info.append("authGrpSeq= ").append(authGrpSeq);
		}
		if(!SBNUtils.isNull(authGrpCd)) {
			info.append(", ");
			info.append("authGrpCd= ").append(authGrpCd);
		}
		if(!SBNUtils.isNull(authGrpTp)) {
			info.append(", ");
			info.append("authGrpTp= ").append(authGrpTp);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn= ").append(useYn);
		}
		if(!SBNUtils.isNull(authGrpNm)) {
			info.append(", ");
			info.append("authGrpNm= ").append(authGrpNm);
		}
		if(!SBNUtils.isNull(authGroup)) {
			info.append(", ");
			info.append("authGroup= ").append(authGroup.toStringInfo());
		}
		if(!SBNUtils.isNull(authGrpMenuList)) {
			info.append(", ");
			info.append("authGrpMenuList= ").append(authGrpMenuList);
		}		
		info.append(" }");
		return info.toString();
	}
}
