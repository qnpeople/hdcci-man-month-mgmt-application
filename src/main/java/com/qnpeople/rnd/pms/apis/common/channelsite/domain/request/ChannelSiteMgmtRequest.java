package com.qnpeople.rnd.pms.apis.common.channelsite.domain.request;

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
public class ChannelSiteMgmtRequest extends QNPWebClientRequestWrapper {

	/* 채널별 사이트 식별자 */
	private Long chnlSiteSeq;
	/* 채널 식별자 */
	private Long chnlSeq;
	/* 사이트 식별자 */
	private Long siteSeq;
	/* 채널 기본 정보의 채널 코드 */
	private String chnlCd;
	/* 사이트 기본 정보의 사이트 코드 */
	private String siteCd;
	/* 채널별 사이트 사용 여부 */
	private String useYn;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(SBNUtils.isNull(pageNo)) {
			info.append(", ");
			info.append("pageNo=").append(pageNo);
		}
		if(SBNUtils.isNull(txId)) {
			info.append(", ");
			info.append("txId=").append(txId);
		}
		if(!SBNUtils.isNull(chnlSiteSeq) && (chnlSiteSeq > 0L)) {
			info.append(", ");
			info.append("chnlSiteSeq=").append(chnlSiteSeq);
		}
		if(!SBNUtils.isNull(chnlSeq) && (chnlSeq > 0L)) {
			info.append(", ");
			info.append("chnlSeq=").append(chnlSeq);
		}
		if(!SBNUtils.isNull(siteSeq) && (siteSeq > 0L)) {
			info.append(", ");
			info.append("siteSeq=").append(siteSeq);
		}
		if(!SBNUtils.isNull(chnlCd)) {
			info.append(", ");
			info.append("chnlCd=").append(chnlCd);
		}
		if(!SBNUtils.isNull(siteCd)) {
			info.append(", ");
			info.append("siteCd=").append(siteCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		info.append(" }");
		return info.toString();
	}
}
