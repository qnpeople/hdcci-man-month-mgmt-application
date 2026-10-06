package com.qnpeople.rnd.pms.apis.system.authgrp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.domain.executor.model.QNPWebBaseModel;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Data
public class AuthGrpSC extends QNPWebBaseModel {

	/* 권한 그룹 식별자 */
	private Long authGrpSeq;
	/* 권한 그룹 코드 */
	private String authGrpCd;
	/* 권한 그룹 유형(공통코드: SYS_AUTH_GRP_001) 01:통합 권한 그룹, 02:시스템 권한 그룹, 03:서비스 권한 그룹, 04:프로젝트 관리자 그룹, 05:프로젝트 수행자 그룹, 99:기타 */
	private String authGrpTp;
	/* 권한 그룹 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 권한 그룹 명 */
	private String authGrpNm;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
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
		info.append(" }");
		return info.toString();
	}
}
