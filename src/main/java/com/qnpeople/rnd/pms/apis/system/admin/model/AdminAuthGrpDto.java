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
public class AdminAuthGrpDto extends SBNWebBaseModel {

	/* 관리자 식별자 */
	private Long admSeq;
	/* 권한 그룹 식별자 */
	private Long authGrpSeq;
	/* 관리자 권한 그룹 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;	
	//	관리자 정보
	/* 관리자 유형(공통코드: SYS_ADM_INFO_001) 01:통합 관리자, 02:시스템 관리자, 03:서비스 관리자, 04:업체 관리자, 05:프로젝트 관리자, 99:기타 */
	private String admTp;
	/* 관리자 유형 명 */
	private String admTpNm;
	/* 관리자 명 */
	private String admNm;	
	//	권한 그룹 정보
	/* 권한 그룹 코드 */
	private String authGrpCd;
	/* 권한 그룹 유형(공통코드: SYS_AUTH_GRP_001) 01:통합 권한 그룹, 02:시스템 권한 그룹, 03:서비스 권한 그룹, 04:프로젝트 관리자 그룹, 05:프로젝트 수행자 그룹, 99:기타 */
	private String authGrpTp;
	/* 권한 그룹 사용 여부(Y: 사용(기본), N: 미사용) */
	private String authGrpTpNm;
	/* 권한 그룹 명 */
	private String authGrpNm;
	
	/* 관리자 정보 최초 등록 일시  */
	private String rgstDt;
	/* 관리자 정보 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 관리자 정보 최초 등록자 명 */
	private String rgstNm;
	/* 관리자 정보 최종 수정 일시 */
	private String updtDt;
	/* 관리자 정보 최종 수정자 식별자 */
	private Long updtSeq;
	/* 관리자 정보 최종 수정자 명 */
	private String updtNm;
	
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
		if(!SBNUtils.isNull(admTpNm)) {
			info.append(", ");
			info.append("admTpNm= ").append(admTpNm);
		}
		if(!SBNUtils.isNull(admNm)) {
			info.append(", ");
			info.append("admNm= ").append(admNm);
		}
		if(!SBNUtils.isNull(authGrpCd)) {
			info.append(", ");
			info.append("authGrpCd= ").append(authGrpCd);
		}
		if(!SBNUtils.isNull(authGrpTp)) {
			info.append(", ");
			info.append("authGrpTp= ").append(authGrpTp);
		}
		if(!SBNUtils.isNull(authGrpTpNm)) {
			info.append(", ");
			info.append("authGrpTpNm= ").append(authGrpTpNm);
		}
		if(!SBNUtils.isNull(authGrpNm)) {
			info.append(", ");
			info.append("authGrpNm= ").append(authGrpNm);
		}
		if(!SBNUtils.isNull(rgstDt)) {
			info.append(", ");
			info.append("rgstDt= ").append(rgstDt);
		}
		if(!SBNUtils.isNull(rgstSeq)) {
			info.append(", ");
			info.append("rgstSeq= ").append(rgstSeq);
		}
		if(!SBNUtils.isNull(rgstNm)) {
			info.append(", ");
			info.append("rgstNm= ").append(rgstNm);
		}
		if(!SBNUtils.isNull(updtDt)) {
			info.append(", ");
			info.append("updtDt= ").append(updtDt);
		}
		if(!SBNUtils.isNull(updtSeq)) {
			info.append(", ");
			info.append("updtSeq= ").append(updtSeq);
		}
		if(!SBNUtils.isNull(updtNm)) {
			info.append(", ");
			info.append("updtNm= ").append(updtNm);
		}
		info.append(" }");
		return info.toString();
	}
}
