package com.qnpeople.rnd.pms.apis.common.channelsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.domain.executor.model.QNPWebBaseModel;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ChannelSiteDto extends QNPWebBaseModel {

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
	/* 사용 여부 */
	private String useYn;
	
	/* 채널 명 */
	private String chnlNm;
	/* 채널 IP */
	private String chnlIp;
	/* 채널 PORT */
	private String chnlPort;
	/* 채널 도메인 */
	private String chnlDmn;
	/* 채널 기본 URI 정보 */
	private String chnlBscUri;
	
	/* 사이트 유형(공통코드: CMN_SITE_INFO_001) 01:전체, 02:지역, 03:분야, 04:업무, 99:기타 */
	private String siteTp;
	/* 사이트 유형 명 */
	private String siteTpNm;
	/* 사이트 명 */
	private String siteNm;
	/* 사이트 기본 URI 정보 */
	private String siteBscUri;
		
	/* 채널별 사이트 최초 등록 일시 */
	private String rgstDt;
	/* 채널별 사이트 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 채널별 사이트 최초 등록자 명 */
	private String rgstNm;
	/* 채널별 사이트 최종 수정 일시 */
	private String updtDt;
	/* 채널별 사이트 최종 수정자 식별자 */
	private Long updtSeq;
	/* 채널별 사이트 최종 수정자 명 */
	private String updtNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(SBNUtils.isNull(pageNo)) {
			info.append(", ");
			info.append("pageNo=").append(pageNo);
		}
		if(SBNUtils.isNull(chnlSiteSeq) && (chnlSiteSeq > 0L)) {
			info.append(", ");
			info.append("chnlSiteSeq=").append(chnlSiteSeq);
		}
		if(SBNUtils.isNull(chnlSeq) && (chnlSeq > 0L)) {
			info.append(", ");
			info.append("chnlSeq=").append(chnlSeq);
		}
		if(SBNUtils.isNull(siteSeq) && (siteSeq > 0L)) {
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
