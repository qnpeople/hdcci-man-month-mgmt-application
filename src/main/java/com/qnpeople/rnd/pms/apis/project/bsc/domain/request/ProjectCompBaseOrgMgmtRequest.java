package com.qnpeople.rnd.pms.apis.project.bsc.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompBaseOrgDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ProjectCompBaseOrgMgmtRequest extends QNPWebClientRequestWrapper {
	
	/* 업체 조직도 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 프로젝트 수행 업체 기본 조직 명 */
	private String compOrgNm;	
	/* 업체 명 */
	private String compNm;
		
	/* 업체 조직도 식별자 */
	private Long compOrgSeq;
	/* 업체 식별자 */
	private Long compSeq;
	/* 업체 코드 */
	private String compCd;
	
	/* 업체 기본 조직도 정보 객체 */
	private ProjectCompBaseOrgDto companyBaseOrganization;
		
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(compOrgSeq)) {
			info.append(", ");
			info.append("compOrgSeq=").append(compOrgSeq);
		}
		if(!SBNUtils.isNull(compSeq)) {
			info.append(", ");
			info.append("compSeq=").append(compSeq);
		}
		if(!SBNUtils.isNull(compCd)) {
			info.append(", ");
			info.append("compCd=").append(compCd);
		}		
		if(!SBNUtils.isNull(compOrgNm)) {
			info.append(", ");
			info.append("compOrgNm=").append(compOrgNm);
		}
		if(!SBNUtils.isNull(compNm)) {
			info.append(", ");
			info.append("compNm=").append(compNm);
		}
		if(!SBNUtils.isNull(companyBaseOrganization)) {
			info.append(", ");
			info.append("companyBaseOrganization=").append(companyBaseOrganization.toStringInfo());
		}
		info.append(" }");
		return info.toString();
	}
}
