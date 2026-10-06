package com.qnpeople.rnd.pms.apis.service.siteservice.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class SiteServiceMgmtRequest extends QNPWebClientRequestWrapper {

	/* 채널별 사이트 식별자 */
	private Long chnlSiteSeq;
	/* 서비스 식별자 */
	private Long srvcSeq;
	/* 채널 코드 */
	private String chnlCd;
	/* 사이트 코드 */
	private String siteCd;
	/* 서비스 코드 */
	private String srvcCd;
	/* 사이트 서비스 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 사이트 서비스 명 */
	private String siteSrvcNm;
	
	//	등록/변경 시 사용 데이터 정보 객체
	private SiteServiceDto siteService;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(txId)) {
			info.append(", ");
			info.append("txId= ").append(txId);
		}
		if(!SBNUtils.isNull(chnlSiteSeq) && (chnlSiteSeq > 0L)) {
			info.append(", ");
			info.append("chnlSiteSeq= ").append(chnlSiteSeq);
		}
		if(!SBNUtils.isNull(srvcSeq) && (srvcSeq > 0L)) {
			info.append(", ");
			info.append("srvcSeq=").append(srvcSeq);
		}
		if(!SBNUtils.isNull(chnlCd)) {
			info.append(", ");
			info.append("chnlCd=").append(chnlCd);
		}
		if(!SBNUtils.isNull(siteCd)) {
			info.append(", ");
			info.append("siteCd=").append(siteCd);
		}		
		if(!SBNUtils.isNull(srvcCd)) {
			info.append(", ");
			info.append("srvcCd=").append(srvcCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(siteSrvcNm)) {
			info.append(", ");
			info.append("siteSrvcNm=").append(siteSrvcNm);
		}
		if(!SBNUtils.isNull(siteService)) {
			info.append(", ");
			info.append("siteService=").append(siteService.toStringInfo());
		}
		info.append(" }");
		return info.toString();
	}
}
