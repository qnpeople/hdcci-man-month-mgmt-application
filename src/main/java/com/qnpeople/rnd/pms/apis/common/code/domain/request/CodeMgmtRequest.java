package com.qnpeople.rnd.pms.apis.common.code.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class CodeMgmtRequest extends QNPWebClientRequestWrapper {

	/* 공통 상위 그룹 코드 정보 */
	private String prntGrpCd;
	/* 공통 그룹 코드 정보 */
	private String grpCd;
	/* 공통 코드 정보 */
	private String cd;
	
	public CodeMgmtRequest() {
		super();
	}
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("grpCd=").append(grpCd);
		if(!SBNUtils.isNull(prntGrpCd)) {
			info.append(", ");
			info.append("prntGrpCd=").append(prntGrpCd);
		}
		info.append(" }");
		return info.toString();
	}
}
