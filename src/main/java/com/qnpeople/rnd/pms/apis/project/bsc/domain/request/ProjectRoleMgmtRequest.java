package com.qnpeople.rnd.pms.apis.project.bsc.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectRoleDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ProjectRoleMgmtRequest extends QNPWebClientRequestWrapper {

	/* 업체 식별자 */
	private Long compSeq;
	/* 프로젝트 역할 코드 */
	private String prjctRoleCd;
	/* 프로젝트 역할 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 프로젝트 역할 명 */
	private String prjctRoleNm;
	
	/* 업체 코드 */
	private String compCd;
	
	/* 업체별 프로젝트 수행 역할 등록/변경 객체 */
	private ProjectRoleDto compProjectRole;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("txId= ").append(txId);
		if(!SBNUtils.isNull(compSeq)) {
			info.append(", ");
			info.append("compSeq=").append(compSeq);
		}
		if(!SBNUtils.isNull(compCd)) {
			info.append(", ");
			info.append("compCd=").append(compCd);
		}
		if(!SBNUtils.isNull(prjctRoleCd)) {
			info.append(", ");
			info.append("prjctRoleCd=").append(prjctRoleCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(prjctRoleNm)) {
			info.append(", ");
			info.append("prjctRoleNm=").append(prjctRoleNm);
		}
		if(!SBNUtils.isNull(compProjectRole)) {
			info.append(", ");
			info.append("compProjectRole=").append(compProjectRole.toStringInfo());
		}
		info.append(" }");
		return info.toString();
	}
}
