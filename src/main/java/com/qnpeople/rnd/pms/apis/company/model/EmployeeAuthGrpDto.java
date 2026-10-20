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
public class EmployeeAuthGrpDto extends QNPWebBaseModel {

	/* 업체 식별자 */
	private Long compSeq;
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
	/* 근무자 소속 업체 코드 */
	private String compCd;
	/* 프로젝트 근무자 업무 권한 그룹 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 근무자 소속 업체 명 */
	private String wrkrCompNm;
	/* 근무자 역할 명 */
	private String wrkrRoleNm;
	/* 근무자 명 */
	private String wrkrNm;
	/* 근무자 권한 그룹 지정 최초 등록 일시  */
	private String rgstDt;
	/* 근무자 권한 그룹 지정 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 근무자 권한 그룹 지정 최초 등록자 명 */
	private String rgstNm;
	/* 근무자 권한 그룹 지정 최종 수정 일시 */
	private String updtDt;
	/* 근무자 권한 그룹 지정 최종 수정자 식별자 */
	private Long updtSeq;
	/* 근무자 권한 그룹 지정 최종 수정자 명 */
	private String updtNm;
	/* 근무자 권한 그룹 지정 최종 삭제 일시 */
	private String deltDt;
	/* 근무자 권한 그룹 지정 최종 삭제 식별자 */
	private Long deltSeq;
	/* 근무자 권한 그룹 지정 최종 삭제자 명 */
	private String deltNm;
	
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
			info.append(",");
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
		if(!SBNUtils.isNull(deltDt)) {
			info.append(",");
			info.append("deltDt=").append(deltDt);
		}
		if(!SBNUtils.isNull(deltSeq)) {
			info.append(", ");
			info.append("deltSeq=").append(deltSeq);
		}
		if(!SBNUtils.isNull(deltNm)) {
			info.append(", ");
			info.append("deltNm=").append(deltNm);
		}
		info.append(" }");
		return info.toString();
	}
}
