package com.qnpeople.rnd.pms.apis.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.domain.executor.model.QNPWebBaseModel;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

//		=> 프로젝트 작업자로 리팩토링
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Data
public class EmployeeAuthGrpSC extends QNPWebBaseModel {

	/* 프로젝트 근무자 업무 권한 그룹 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 근무자 소속 업체 명 */
	private String wrkrCompNm;
	/* 근무자 역할 명 */
	private String wrkrRoleNm;
	/* 근무자 명 */
	private String wrkrNm;

	/* 업체 식별자 */
	private Long compSeq;
	/* 근무자 소속 업체 코드 */
	private String compCd;
	/* 근무자 식별자 */
	private Long empleSeq;
	/* 업체 조직도 식별자 */
	private Long compOrgSeq;
	/* 프로젝트 조직도 식별자 */
	private Long prjctOrgSeq;
	/* 프로젝트 역할 코드 */
	private String prjctRoleCd;
	/* 권한 그룹 식별자 */
	private String authGrpSeq;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(compSeq)) {
			info.append(", ");
			info.append("compSeq=").append(compSeq);
		}
		if(!SBNUtils.isNull(compCd)) {
			info.append(", ");
			info.append("compCd=").append(compCd);
		}
		if(!SBNUtils.isNull(empleSeq)) {
			info.append(", ");
			info.append("empleSeq=").append(empleSeq);
		}
		if(!SBNUtils.isNull(compOrgSeq)) {
			info.append(", ");
			info.append("compOrgSeq=").append(compOrgSeq);
		}
		if(!SBNUtils.isNull(prjctOrgSeq)) {
			info.append(", ");
			info.append("prjctOrgSeq=").append(prjctOrgSeq);
		}
		if(!SBNUtils.isNull(prjctRoleCd)) {
			info.append(", ");
			info.append("prjctRoleCd=").append(prjctRoleCd);
		}
		if(!SBNUtils.isNull(authGrpSeq)) {
			info.append(", ");
			info.append("authGrpSeq=").append(authGrpSeq);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(wrkrCompNm)) {
			info.append(", ");
			info.append("wrkrCompNm=").append(wrkrCompNm);
		}
		if(!SBNUtils.isNull(wrkrRoleNm)) {
			info.append(", ");
			info.append("wrkrRoleNm=").append(wrkrRoleNm);
		}
		if(!SBNUtils.isNull(wrkrNm)) {
			info.append(", ");
			info.append("wrkrNm=").append(wrkrNm);
		}
		info.append(" }");
		return info.toString();
	}
}
