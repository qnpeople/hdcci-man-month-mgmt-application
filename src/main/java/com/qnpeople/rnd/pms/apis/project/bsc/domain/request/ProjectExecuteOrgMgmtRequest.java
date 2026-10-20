package com.qnpeople.rnd.pms.apis.project.bsc.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectExecuteOrgDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ProjectExecuteOrgMgmtRequest extends QNPWebClientRequestWrapper {

	/* 프로젝트 수행 업체 조직도 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 프로젝트 수행 업체 조직도 명*/
	private String prjctOrgNm;
	/* 프로젝트 수행 업체 상위 조직도 식별자 */
	private String prntPrjctOrgSeq;
	
	/* 프로젝트 수행 업체 조직도 식별자 */
	private Long prjctOrgSeq;
	/* 프로젝트 수행 업체 기본 조직 식별자 */
	private Long compOrgSeq;
	/* 프로젝트 수행 업체 조직도 코드 */
	private String prjctOrgCd;
		
	/* 프로젝트 수행 업체 조직도 정보 객체 */
	private ProjectExecuteOrgDto projectExecuteOrganization;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("txId=").append(txId);
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(prjctOrgNm)) {
			info.append(", ");
			info.append("prjctOrgNm=").append(prjctOrgNm);
		}
		if(!SBNUtils.isNull(prntPrjctOrgSeq)) {
			info.append(", ");
			info.append("prntPrjctOrgSeq=").append(prntPrjctOrgSeq);
		}
		if(!SBNUtils.isNull(prjctOrgSeq)) {
			info.append(", ");
			info.append("prjctOrgSeq=").append(prjctOrgSeq);
		}
		if(!SBNUtils.isNull(compOrgSeq)) {
			info.append(", ");
			info.append("compOrgSeq=").append(compOrgSeq);
		}
		if(!SBNUtils.isNull(prjctOrgCd)) {
			info.append(", ");
			info.append("prjctOrgCd=").append(prjctOrgCd);
		}
		info.append(" }");
		return info.toString();
	}
}
