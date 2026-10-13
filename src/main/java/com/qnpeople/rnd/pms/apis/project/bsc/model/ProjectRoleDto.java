package com.qnpeople.rnd.pms.apis.project.bsc.model;

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
public class ProjectRoleDto extends QNPWebBaseModel {

	/* 업체 식별자 */
	private Long compSeq;
	/* 프로젝트 역할 코드 */
	private String prjctRoleCd;
	/* 프로젝트 역할 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 프로젝트 역할 삭제 여부(N: 미삭제(기본값), Y:사용) */
	private String deltYn;
	/* 프로젝트 역할 노출 순서 */
	private Short dispOrd;
	/* 업체 명 */
	private String compNm;
	/* 프로젝트 역할 명 */
	private String prjctRoleNm;
	/* 프로젝트 역할 설명 */
	private String prjctRoleDesc;

	/* 프로젝트 역할 최초 등록 일시  */
	private String rgstDt;
	/* 프로젝트 역할 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 프로젝트 역할 최초 등록자 명 */
	private String rgstNm;
	/* 프로젝트 역할 최종 수정 일시 */
	private String updtDt;
	/* 프로젝트 역할 최종 수정자 식별자 */
	private Long updtSeq;
	/* 프로젝트 역할 최종 수정자 명 */
	private String updtNm;
	/* 프로젝트 역할 최종 삭제 일시 */
	private String deltDt;
	/* 프로젝트 역할 최종 삭제 식별자 */
	private Long deltSeq;
	/* 프로젝트 역할 최종 삭제 명 */
	private String deltNm;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("compSeq= ").append(compSeq);
		if(!SBNUtils.isNull(prjctRoleCd)) {
			info.append(", ");
			info.append("prjctRoleCd=").append(prjctRoleCd);
		}
		if(!SBNUtils.isNull(compNm)) {
			info.append(", ");
			info.append("compNm=").append(compNm);
		}		
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(deltYn)) {
			info.append(", ");
			info.append("deltYn=").append(deltYn);
		}
		if(!SBNUtils.isNull(dispOrd)) {
			info.append(", ");
			info.append("dispOrd=").append(dispOrd);
		}	
		if(!SBNUtils.isNull(prjctRoleNm)) {
			info.append(", ");
			info.append("prjctRoleNm=").append(prjctRoleNm);
		}
		if(!SBNUtils.isNull(prjctRoleDesc)) {
			info.append(", ");
			info.append("prjctRoleDesc=").append(prjctRoleDesc);
		}
		if(!SBNUtils.isNull(rgstDt)) {
			info.append(",");
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
