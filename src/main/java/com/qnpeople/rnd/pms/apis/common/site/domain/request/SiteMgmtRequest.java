package com.qnpeople.rnd.pms.apis.common.site.domain.request;

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
public class SiteMgmtRequest extends QNPWebClientRequestWrapper {

	/* 사이트 식별자 */
	private Long siteSeq;
	/* 사이트 코드 */
	private String siteCd;
	/* 사이트 유형(공통코드: CMN_SITE_INFO_001) 01:전체, 02:지역, 03:분야, 04:업무, 99:기타 */
	private String siteTp;
	/* 사용 여부 */
	private String useYn;
	/* 사이트 명 */
	private String siteNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(txId)) {
			info.append(", ");
			info.append("txId=").append(txId);
		}
		if(!SBNUtils.isNull(pageNo)) {
			info.append(", ");
			info.append("pageNo=").append(pageNo);
		}
		if(!SBNUtils.isNull(siteSeq)) {
			info.append("siteSeq= ").append(siteSeq);
		}
		if(!SBNUtils.isNull(siteCd)) {
			info.append(", ");
			info.append("siteCd=").append(siteCd);
		}
		if(!SBNUtils.isNull(siteTp)) {
			info.append(", ");
			info.append("siteTp=").append(siteTp);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(siteNm)) {
			info.append(", ");
			info.append("siteNm=").append(siteNm);
		}
		info.append(" }");
		return info.toString();
	}
}
