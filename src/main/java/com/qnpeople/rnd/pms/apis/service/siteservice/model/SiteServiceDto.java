package com.qnpeople.rnd.pms.apis.service.siteservice.model;

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
public class SiteServiceDto extends QNPWebBaseModel {

	/* 채널별 사이트 식별자 */
	private Long chnlSiteSeq;
	/* 채널 식별자 */
	private Long chnlSeq;
	/* 사이트 식별자 */
	private Long siteSeq;
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
	/* 사이트 서비스 설명 */
	private String siteSrvcDesc;
	
	/* 채널 정보 */
	/* 채널 명 */
	private String chnlNm;
	/* 채널 IP */
	private String chnlIp;
	/* 채널 PORT */
	private Integer chnlPort;
	/* 채널 도메인 */
	private String chnlDmn;
	/* 채널 기본 URI 정보 */
	private String chnlBscUri;
	/* 채널 설명 */
	private String chnlDesc;
	
	/* 사이트 정보 */
	/* 사이트 유형(공통코드: CMN_SITE_INFO_001) 01:전체, 02:지역, 03:분야, 04:업무, 99:기타 */
	private String siteTp;
	/* 사이트 유형 명 */
	private String siteTpNm;
	/* 사이트 명 */
	private String siteNm;
	/* 사이트 기본 URI 정보 */
	private String siteBscUri;
	/* 사이트 설명 */
	private String siteDesc;
	
	/* 서비스 정보 */
	/* 서비스 카테고리 코드 */
	private String srvcCtgryCd;
	/* 서비스 유형(공통코드: SVC_SRVC_INFO_001) 01:기본, 02:실버, 03:골드, 04:VIP, 05:VVIP, 06:개별, 99:기타 */
	private String srvcTp;
	/* 서비스 유형 명 */
	private String srvcTpNm;
	/* 서비스 명 */
	private String srvcNm;
	/* 서비스 기본 URI  */
	private String srvcBscUri;
	/* 서비스 설명 */
	private String srvcDesc;
	
	/* 사이트 서비스 매핑 정보 최초 등록 일시  */
	private String rgstDt;
	/* 사이트 서비스 매핑 정보 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 사이트 서비스 매핑 정보 최초 등록자 명 */
	private String rgstNm;
	/* 사이트 서비스 매핑 정보 최종 수정 일시 */
	private String updtDt;
	/* 사이트 서비스 매핑 정보 최종 수정자 식별자 */
	private Long updtSeq;
	/* 사이트 서비스 매핑 정보 최종 수정자 명 */
	private String updtNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("chnlSiteSeq= ").append(chnlSiteSeq);
		if(!SBNUtils.isNull(chnlSeq)) {
			info.append(", ");
			info.append("chnlSeq=").append(chnlSeq);
		}
		if(!SBNUtils.isNull(siteSeq)) {
			info.append(", ");
			info.append("siteSeq=").append(siteSeq);
		}
		if(!SBNUtils.isNull(srvcSeq) && (srvcSeq > 0)) {
			info.append("srvcSeq= ").append(srvcSeq);
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
		if(!SBNUtils.isNull(siteSrvcDesc)) {
			info.append(", ");
			info.append("siteSrvcDesc=").append(siteSrvcDesc);
		}
		if(!SBNUtils.isNull(chnlNm)) {
			info.append(", ");
			info.append("chnlNm=").append(chnlNm);
		}
		if(!SBNUtils.isNull(chnlIp)) {
			info.append(", ");
			info.append("chnlIp=").append(chnlIp);
		}
		if(!SBNUtils.isNull(chnlPort)) {
			info.append(", ");
			info.append("chnlPort=").append(chnlPort);
		}
		if(!SBNUtils.isNull(chnlDmn)) {
			info.append(", ");
			info.append("chnlDmn=").append(chnlDmn);
		}
		if(!SBNUtils.isNull(chnlBscUri)) {
			info.append(", ");
			info.append("chnlBscUri=").append(chnlBscUri);
		}
		if(!SBNUtils.isNull(chnlDesc)) {
			info.append(", ");
			info.append("chnlDesc=").append(chnlDesc);
		}
		if(!SBNUtils.isNull(siteTp)) {
			info.append(", ");
			info.append("siteTp=").append(siteTp);
		}
		if(!SBNUtils.isNull(siteTpNm)) {
			info.append(", ");
			info.append("siteTpNm=").append(siteTpNm);
		}
		if(!SBNUtils.isNull(siteNm)) {
			info.append(", ");
			info.append("siteNm=").append(siteNm);
		}
		if(!SBNUtils.isNull(siteBscUri)) {
			info.append(", ");
			info.append("siteBscUri=").append(siteBscUri);
		}
		if(!SBNUtils.isNull(siteDesc)) {
			info.append(", ");
			info.append("siteDesc=").append(siteDesc);
		}
		if(!SBNUtils.isNull(srvcCtgryCd)) {
			info.append(", ");
			info.append("srvcCtgryCd=").append(srvcCtgryCd);
		}
		if(!SBNUtils.isNull(srvcTp)) {
			info.append(", ");
			info.append("srvcTp=").append(srvcTp);
		}
		if(!SBNUtils.isNull(srvcTpNm)) {
			info.append(", ");
			info.append("srvcTpNm=").append(srvcTpNm);
		}
		if(!SBNUtils.isNull(srvcNm)) {
			info.append(", ");
			info.append("srvcNm=").append(srvcNm);
		}
		if(!SBNUtils.isNull(srvcBscUri)) {
			info.append(", ");
			info.append("srvcBscUri=").append(srvcBscUri);
		}
		if(!SBNUtils.isNull(srvcDesc)) {
			info.append(", ");
			info.append("srvcDesc=").append(srvcDesc);
		}
		if(!SBNUtils.isNull(rgstDt)) {
			info.append(", ");
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
			info.append(", ");
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
		info.append(" }");
		return info.toString();
	}
}
