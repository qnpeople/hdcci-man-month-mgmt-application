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
public class ProjectCompBaseOrgDto extends QNPWebBaseModel {

	/* 업체 조직도 식별자 */
	private Long compOrgSeq;
	/* 업체 식별자 */
	private Long compSeq;
	/* 업체 코드 */
	private String compCd;
	/* 프로젝트 수행 업체 기본 조직 명 */
	private String compOrgNm;
	/* 업체 명 */
	private String compNm;
	/* 프로젝트 수행 업체 기본 조직 설명 */
	private String compOrgDesc;
	/* 업체 조직도 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 업체 조직도 삭제 여부(N: 미삭제(기본값), Y: 삭제) */
	private String deltYn;
	/* 업체 조직도 최초 등록 일시  */
	private String rgstDt;
	/* 업체 조직도 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 업체 조직도 최초 등록자 명 */
	private String rgstNm;
	/* 업체 조직도 최종 수정 일시 */
	private String updtDt;
	/* 업체 조직도 최종 수정자 식별자 */
	private Long updtSeq;
	/* 업체 조직도 최종 수정자 명 */
	private String updtNm;
	/* 업체 조직도 최종 삭제 일시 */
	private String deltDt;
	/* 업체 조직도 최종 삭제자 식별자 */
	private Long deltSeq;
	/* 업체 조직도 최종 최종 삭제자 명 */
	private String deltNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
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
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(deltYn)) {
			info.append(", ");
			info.append("deltYn=").append(deltYn);
		}
		if(SBNUtils.isNull(compOrgNm)) {
			info.append(", ");
			info.append("compOrgNm=").append(compOrgNm);
		}
		if(!SBNUtils.isNull(compNm)) {
			info.append(", ");
			info.append("compNm=").append(compNm);
		}
		if(!SBNUtils.isNull(compOrgDesc)) {
			info.append(", ");
			info.append("compOrgDesc=").append(compOrgDesc);
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
