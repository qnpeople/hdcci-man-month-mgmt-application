package com.qnpeople.rnd.pms.apis.project.crtr.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.project.crtr.model.WorkTimeCriteriaDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ProjectWorkTimeCrtrRequest extends QNPWebClientRequestWrapper {

	/* 프로젝트 근무 기준 시간 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 프로젝트 근무 시간 측정 기준 유형(공통코드: PRJ_WRK_MEAS_UNIT) 01:년별, 02:월별, 03:주별, 04:일별, 05:시간별, 99:기타 */
	private String wrkTmMeasCrtrTp;
	/* 프로젝트 근무 기준 시간 명 */
	private String wrkTmMeasCrtrNm;
	
	/* 프로젝트 근무 기준 시간 식별자 */
	private Long wrkTmCrtrSeq;
	/* 프로젝트 근무 기준 시간 코드 */
	private String wrkTmCrtrCd;
	
	/* 프로젝트 근무 기준 시간 최종 삭제자 식별자 */
	private Long deltSeq;
	/* 프로젝트 근무 기준 시간 최종 삭제자 명 */
	private String deltNm;
	
	/* 프로젝트 근무 기준 시간 상세 정보 객체 */
	private WorkTimeCriteriaDto workTimeCriteria;
	
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
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(wrkTmMeasCrtrNm)) {
			info.append(", ");
			info.append("wrkTmMeasCrtrNm=").append(wrkTmMeasCrtrNm);
		}
		if(!SBNUtils.isNull(deltSeq)) {
			info.append(", ");
			info.append("deltSeq=").append(deltSeq);
		}
		if(!SBNUtils.isNull(deltNm)) {
			info.append(", ");
			info.append("deltNm=").append(deltNm);
		}
		if(!SBNUtils.isNull(workTimeCriteria)) {
			info.append(", ");
			info.append("workTimeCriteria=").append(workTimeCriteria);
		}		
		info.append(" }");
		return info.toString();
	}
}
