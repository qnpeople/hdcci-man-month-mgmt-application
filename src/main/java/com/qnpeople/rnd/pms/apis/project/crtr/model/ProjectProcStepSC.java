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
public class ProjectProcStepSC extends QNPWebBaseModel {

	/* 프로젝트 수행 단계 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 프로젝트 수행 단계 명 */
	private String execStpNm;
	
	/* 프로젝트 수행 단계 코드 */
	private String execStpCd;
	
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
		if(!SBNUtils.isNull(execStpNm)) {
			info.append(", ");
			info.append("execStpNm=").append(execStpNm);
		}
		info.append(" }");
		return info.toString();
	}
}
