package com.qnpeople.rnd.pms.apis.common.site.model;

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
public class SiteDto extends QNPWebBaseModel {

	/* 사이트 식별자 */
	private Long siteSeq;
	/* 사이트 코드 */
	private String siteCd;
	/* 사이트 유형(공통코드: CMN_SITE_INFO_001) 01:전체, 02:지역, 03:분야, 04:업무, 99:기타 */
	private String siteTp;
	/* 사이트 유형 명 */
	private String siteTpNm;
	/* 사용 여부 */
	private String useYn;
	/* 사이트 명 */
	private String siteNm;
	/* 사이트 기본 URI 정보 */
	private String siteBscUri;
	/* 사이트 설명 */
	private String siteDesc;
	
	/* 사이트 최초 등록 일시 */
	private String rgstDt;
	/* 사이트 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 사이트 최초 등록자 명 */
	private String rgstNm;
	/* 사이트 최종 수정 일시 */
	private String updtDt;
	/* 사이트 최종 수정자 식별자 */
	private Long updtSeq;
	/* 사이트 최종 수정자 명 */
	private String updtNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("siteSeq= ").append(siteSeq);
		if(!SBNUtils.isNull(siteCd)) {
			info.append(",");
			info.append("siteCd=").append(siteCd);
		}
		if(!SBNUtils.isNull(siteTp)) {
			info.append(",");
			info.append("siteTp=").append(siteTp);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(",");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(siteTpNm)) {
			info.append(",");
			info.append("siteTpNm=").append(siteTpNm);
		}	
		if(!SBNUtils.isNull(siteNm)) {
			info.append(",");
			info.append("siteNm=").append(siteNm);
		}		
		if(!SBNUtils.isNull(siteBscUri)) {
			info.append(",");
			info.append("siteBscUri=").append(siteBscUri);
		}
		if(!SBNUtils.isNull(siteDesc)) {
			info.append(",");
			info.append("siteDesc=").append(siteDesc);
		}
		if(!SBNUtils.isNull(rgstDt)) {
			info.append(",");
			info.append("rgstDt=").append(rgstDt);
		}
		if(!SBNUtils.isNull(rgstSeq)) {
			info.append(",");
			info.append("rgstSeq=").append(rgstSeq);
		}
		if(!SBNUtils.isNull(rgstNm)) {
			info.append(",");
			info.append("rgstNm=").append(rgstNm);
		}
		if(!SBNUtils.isNull(updtDt)) {
			info.append(",");
			info.append("updtDt=").append(updtDt);
		}
		if(!SBNUtils.isNull(updtSeq)) {
			info.append(",");
			info.append("updtSeq=").append(updtSeq);
		}
		if(!SBNUtils.isNull(updtNm)) {
			info.append(",");
			info.append("updtNm=").append(updtNm);
		}
		info.append(" }");
		return info.toString();
	}
}
