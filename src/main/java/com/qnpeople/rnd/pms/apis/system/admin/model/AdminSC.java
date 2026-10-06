package com.qnpeople.rnd.pms.apis.system.admin.model;

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
public class AdminSC extends QNPWebBaseModel {

	/* 관리자 식별자 */
	private Long admSeq;
	/* 관리자 유형(공통코드: SYS_ADM_INFO_001) 01:통합 관리자, 02:시스템 관리자, 03:서비스 관리자, 04:업체 관리자, 05:프로젝트 관리자, 99:기타 */
	private String admTp;
	/* 관리자 로그인 ID */
	private String admLognId;
	/* 관리자 명 */
	private String admNm;	
	/* 최초 로그인 비밀번호 변경 여부(N: 미 변경(기본값), Y: 변경) */
	private String initPwdChngYn;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(admSeq)) {
			info.append(", ");
			info.append("admSeq= ").append(admSeq);
		}
		if(!SBNUtils.isNull(admTp)) {
			info.append(", ");
			info.append("admTp= ").append(admTp);
		}
		if(!SBNUtils.isNull(admLognId)) {
			info.append(", ");
			info.append("admLognId= ").append(admLognId);
		}
		if(!SBNUtils.isNull(admNm)) {
			info.append(", ");
			info.append("admNm= ").append(admNm);
		}
		if(!SBNUtils.isNull(initPwdChngYn)) {
			info.append(", ");
			info.append("initPwdChngYn= ").append(initPwdChngYn);
		}
		info.append(" }");
		return info.toString();
	}
}
