package com.qnpeople.rnd.pms.apis.system.admin.domain.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminAuthGrpDto;
import com.qnpeople.rnd.pms.apis.system.admin.model.AdminDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class AdminMgmtRequest extends QNPWebClientRequestWrapper {
	
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
	
	/* 관리자 권한 그룹 매핑 사용 여부 */
	private String useYn;
	/* 관리자 권한 그룹 식별자 */
	private Long authGrpSeq;
	/* 권한 그룹 유형(공통코드: SYS_AUTH_GRP_001) 01:통합 권한 그룹, 02:시스템 권한 그룹, 03:서비스 권한 그룹, 04:프로젝트 관리자 그룹, 05:프로젝트 수행자 그룹, 99:기타 */
	private String authGrpTp;
	
	/* 등록/변경 시 관리자 정보 객체 */
	private AdminDto admin;
	/* 시스템 관리자 지정/해지 대상 권한 그룹 키 목록 객체 */
	private List<AdminAuthGrpDto> adminAuthGroupList;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(txId)) {
			info.append(", ");
			info.append("txId= ").append(txId);
		}
		if(!SBNUtils.isNull(admSeq)) {
			info.append(", ");
			info.append("admSeq= ").append(admSeq);
		}
		if(!SBNUtils.isNull(authGrpSeq)) {
			info.append(", ");
			info.append("authGrpSeq= ").append(authGrpSeq);
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
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn= ").append(useYn);
		}
		if(!SBNUtils.isNull(authGrpTp)) {
			info.append(", ");
			info.append("authGrpTp= ").append(authGrpTp);
		}		
		if(!SBNUtils.isNull(admin)) {
			info.append(", ");
			info.append("admin= ").append(admin.toStringInfo());
		}
		if(!SBNUtils.isNull(adminAuthGroupList)) {
			info.append(", ");
			info.append("adminAuthGroupList= ").append(adminAuthGroupList);
		}		
		info.append(" }");
		return info.toString();
	}
}
