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
public class WorkTimeCriteriaDto extends QNPWebBaseModel {

	/* 프로젝트 근무 기준 시간 식별자 */
	private Long wrkTmCrtrSeq;
	/* 프로젝트 근무 기준 시간 코드 */
	private String wrkTmCrtrCd;
	/* 프로젝트 근무 시간 측정 기준 유형(공통코드: PRJ_WRK_MEAS_UNIT) 01:년별, 02:월별, 03:주별, 04:일별, 05:시간별, 99:기타 */
	private String wrkTmMeasCrtrTp;
	/* 프로젝트 근무 시간 측정 기준 유형 명 */
	private String wrkTmMeasCrtrTpNm;
	/* 프로젝트 근무 기준 시간 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 프로젝트 근무 기준 시간 삭제 여부(N: 미삭제(기본), Y: 삭제) */
	private String deltYn;
	/* 프로젝트 근무 기준 시간 명 */
	private String wrkTmMeasCrtrNm;
	/* 프로젝트 근무 기준 시간 차수 */
	private Short wrkTmMeasCrtrDgr;
	/* 프로젝트 근무 기준 시간 측정 값 */
	private Float wrkTmMeasCrtrVal;
	/* 프로젝트 근무 시간 설명 */
	private String wrkTmMeasCrtrDesc;
	/* 프로젝트 근무 시간 최초 등록 일시  */
	private String rgstDt;
	/* 프로젝트 근무 시간 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 프로젝트 근무 기준 시간 최초 등록자 명 */
	private String rgstNm;
	/* 프로젝트 근무 시간 최종 수정 일시 */
	private String updtDt;
	/* 프로젝트 근무 시간 최종 수정자 식별자 */
	private Long updtSeq;
	/* 프로젝트 근무 기준 시간 최종 수정자 명 */
	private String updtNm;
	/* 프로젝트 근무 기준 시간 최종 삭제 일시 */
	private String deltDt;
	/* 프로젝트 근무 기준 시간 최종 삭제자 식별자 */
	private Long deltSeq;
	/* 프로젝트 근무 기준 시간 최종 삭제자 명 */
	private String deltNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id= ").append(id);
		if(!SBNUtils.isNull(wrkTmCrtrSeq)) {
			info.append("wrkTmCrtrSeq= ").append(wrkTmCrtrSeq);
		}
		if(!SBNUtils.isNull(wrkTmCrtrCd)) {
			info.append(", ");
			info.append("wrkTmCrtrCd=").append(wrkTmCrtrCd);
		}
		if(!SBNUtils.isNull(wrkTmMeasCrtrTp)) {
			info.append(", ");
			info.append("wrkTmMeasCrtrTp=").append(wrkTmMeasCrtrTp);
		}
		if(!SBNUtils.isNull(wrkTmMeasCrtrTpNm)) {
			info.append(", ");
			info.append("wrkTmMeasCrtrTpNm=").append(wrkTmMeasCrtrTpNm);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(deltYn)) {
			info.append(", ");
			info.append("deltYn=").append(deltYn);
		}
		if(!SBNUtils.isNull(wrkTmMeasCrtrNm)) {
			info.append(", ");
			info.append("wrkTmMeasCrtrNm=").append(wrkTmMeasCrtrNm);
		}
		if(!SBNUtils.isNull(wrkTmMeasCrtrDgr)) {
			info.append(", ");
			info.append("wrkTmMeasCrtrDgr=").append(wrkTmMeasCrtrDgr);
		}
		if(!SBNUtils.isNull(wrkTmMeasCrtrVal)) {
			info.append(", ");
			info.append("wrkTmMeasCrtrVal=").append(wrkTmMeasCrtrVal);
		}
		if(!SBNUtils.isNull(wrkTmMeasCrtrDesc)) {
			info.append(", ");
			info.append("wrkTmMeasCrtrDesc=").append(wrkTmMeasCrtrDesc);
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
