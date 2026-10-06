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
public class AuthGrpMenuSC extends QNPWebBaseModel {

	/* 권한 그룹 식별자 */
	private Long authGrpSeq;
	/* 메뉴 식별자 */
	private Long menuSeq;
	/* 권한 그룹 코드 */
	private String authGrpCd;
	/* 메뉴 코드 */
	private String menuCd;
	/* 권한 그룹 메뉴 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(authGrpSeq)) {
			info.append(", ");
			info.append("authGrpSeq= ").append(authGrpSeq);
		}
		if(!SBNUtils.isNull(menuSeq)) {
			info.append(", ");
			info.append("menuSeq= ").append(menuSeq);
		}
		if(!SBNUtils.isNull(authGrpCd)) {
			info.append(", ");
			info.append("authGrpCd= ").append(authGrpCd);
		}
		if(!SBNUtils.isNull(menuCd)) {
			info.append(", ");
			info.append("menuCd= ").append(menuCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn= ").append(useYn);
		}
		info.append(" }");
		return info.toString();
	}
}
