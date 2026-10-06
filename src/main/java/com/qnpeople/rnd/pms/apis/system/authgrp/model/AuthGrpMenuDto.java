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
public class AuthGrpMenuDto extends QNPWebBaseModel {

	/* 권한 그룹 식별자 */
	private Long authGrpSeq;
	/* 메뉴 식별자 */
	private Long menuSeq;
	/* 메뉴 코드 */
	private String menuCd;
	/* 권한 그룹 메뉴 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 메뉴 명 */
	private String menuNm;
	/* 메뉴 경로 명 */
	private String menuPath;
	/* 권한 그룹 코드 */
	private String authGrpCd;
	/* 권한 그룹 명 */
	private String authGrpNm;
	/* 권한 그룹 메뉴 최초 등록 일시 */
	private String rgstDt;
	/* 권한 그룹 메뉴 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 권한 그룹 메뉴 최초 등록자 명 */
	private String rgstNm;
	/* 권한 그룹 메뉴 최종 수정 일시 */
	private String updtDt;
	/* 권한 그룹 메뉴 최종 수정자 식별자 */
	private Long updtSeq;
	/* 권한 그룹 메뉴 최종 수정자 명 */
	private String updtNm;
	
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
		if(!SBNUtils.isNull(menuCd)) {
			info.append(", ");
			info.append("menuCd= ").append(menuCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn= ").append(useYn);
		}
		if(!SBNUtils.isNull(menuNm)) {
			info.append(", ");
			info.append("menuNm= ").append(menuNm);
		}
		if(!SBNUtils.isNull(menuPath)) {
			info.append(", ");
			info.append("menuPath= ").append(menuPath);
		}
		if(!SBNUtils.isNull(authGrpCd)) {
			info.append(", ");
			info.append("authGrpCd= ").append(authGrpCd);
		}
		if(!SBNUtils.isNull(authGrpNm)) {
			info.append(", ");
			info.append("authGrpNm= ").append(authGrpNm);
		}
		if(!SBNUtils.isNull(rgstDt)) {
			info.append(", ");
			info.append("rgstDt=").append(rgstDt);
		}
		if(!SBNUtils.isNull(rgstSeq)) {
			info.append(", ");
			info.append("rgstSeq=").append(rgstSeq);
		}
		if(!SBNUtils.isNull(rgstNm)) {
			info.append(", ");
			info.append("rgstNm=").append(rgstNm);
		}
		if(!SBNUtils.isNull(updtDt)) {
			info.append(", ");
			info.append("updtDt=").append(updtDt);
		}
		if(!SBNUtils.isNull(updtSeq)) {
			info.append(", ");
			info.append("updtSeq=").append(updtSeq);
		}
		if(!SBNUtils.isNull(updtNm)) {
			info.append(", ");
			info.append("updtNm=").append(updtNm);
		}
		info.append(" }");
		return info.toString();
	}
}
