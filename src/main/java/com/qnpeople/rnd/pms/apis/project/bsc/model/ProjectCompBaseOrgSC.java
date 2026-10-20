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
public class ProjectCompBaseOrgSC extends QNPWebBaseModel {
	
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
	
	/* 업체 조직도 삭제자 식별자 */
	private Long deltSeq;
	/* 업체 조직도 삭제자 명 */
	private String deltNm;
	
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
		info.append(" }");
		return info.toString();
	}
}
