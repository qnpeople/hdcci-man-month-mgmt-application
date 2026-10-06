package com.qnpeople.rnd.pms.apis.system.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kr.co.sbn.platformhub.framework.core.common.web.common.data.model.SBNWebBaseModel;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Data
public class AdminAuthGrpSC extends SBNWebBaseModel {

	/* 관리자 식별자 */
	private Long admSeq;
	/* 권한 그룹 식별자 */
	private Long authGrpSeq;
	/* 관리자 권한 그룹 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 관리자 유형(공통코드: SYS_ADM_INFO_001) 01:통합 관리자, 02:시스템 관리자, 03:서비스 관리자, 04:업체 관리자, 05:프로젝트 관리자, 99:기타 */
	private String admTp;
	/* 권한 그룹 유형(공통코드: SYS_AUTH_GRP_001) 01:통합 권한 그룹, 02:시스템 권한 그룹, 03:서비스 권한 그룹, 04:프로젝트 관리자 그룹, 05:프로젝트 수행자 그룹, 99:기타 */
	private String authGrpTp;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(admSeq)) {
			info.append(", ");
			info.append("admSeq= ").append(admSeq);
		}
		if(!SBNUtils.isNull(authGrpSeq)) {
			info.append(", ");
			info.append("authGrpSeq= ").append(authGrpSeq);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn= ").append(useYn);
		}
		if(!SBNUtils.isNull(admTp)) {
			info.append(", ");
			info.append("admTp= ").append(admTp);
		}
		if(!SBNUtils.isNull(authGrpTp)) {
			info.append(", ");
			info.append("authGrpTp= ").append(authGrpTp);
		}
		info.append(" }");
		return info.toString();
	}
}
