package com.qnpeople.rnd.pms.apis.project.crtr.model;

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
public class ProjectProcStepDto extends QNPWebBaseModel {
	
	/* 프로젝트 수행 단계 코드 */
	private String execStpCd;
	/* 프로젝트 수행 단계 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 프로젝트 수행 단계 삭제 여부(N: 미삭제(기본), Y: 삭제) */
	private String deltYn;
	/* 프로젝트 수행 단계 노출 순서 */
	private Short execStpOrd;
	/* 프로젝트 수행 단계 명 */
	private String execStpNm;
	/* 프로젝트 수행 단계 설명 */
	private String execStpDesc;	
	/* 프로젝트 수행 단계 최초 등록 일시  */
	private String rgstDt;
	/* 프로젝트 수행 단계 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 프로젝트 수행 단계 최초 등록자 명 */
	private String rgstNm;
	/* 프로젝트 수행 단계 최종 수정 일시 */
	private String updtDt;
	/* 프로젝트 수행 단계 최종 수정자 식별자 */
	private Long updtSeq;
	/* 프로젝트 수행 단계 최종 수정자 명 */
	private String updtNm;
	/* 프로젝트 수행 단계 최종 삭제 일시 */
	private String deltDt;
	/* 프로젝트 수행 단계 최종 삭제자 식별자 */
	private Long deltSeq;
	/* 프로젝트 수행 단계 최종 삭제자 명 */
	private String deltNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id= ").append(id);
		if(!SBNUtils.isNull(execStpCd)) {
			info.append("execStpCd= ").append(execStpCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(deltYn)) {
			info.append(", ");
			info.append("deltYn=").append(deltYn);
		}
		if(!SBNUtils.isNull(execStpOrd)) {
			info.append(", ");
			info.append("execStpOrd=").append(execStpOrd);
		}
		if(!SBNUtils.isNull(execStpNm)) {
			info.append(", ");
			info.append("execStpNm=").append(execStpNm);
		}
		if(!SBNUtils.isNull(execStpDesc)) {
			info.append(", ");
			info.append("execStpDesc=").append(execStpDesc);
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
