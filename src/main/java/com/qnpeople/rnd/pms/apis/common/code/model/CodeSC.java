package com.qnpeople.rnd.pms.apis.common.code.model;

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
public class CodeSC extends QNPWebBaseModel {

	/* 공통 그룹 코드 정보 */
	private String grpCd;
	/* 공통 코드 정보 */
	private String cd;
	
	public CodeSC() {
		super();
	}
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("grpCd= ").append(grpCd);
		if(!SBNUtils.isNull(cd)) {
			info.append(",");
			info.append("cd=").append(cd);
		}
		info.append(" }");
		return info.toString();
	}
}
