package com.qnpeople.rnd.pms.apis.company.model;

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
public class ServiceCompanyDto extends QNPWebBaseModel {

	/* 채널별 사이트 식별자 */
	private Long chnlSiteSeq;
	/* 서비스 식별자 */
	private Long srvcSeq;
	/* 업체 식별자 */
	private Long compSeq;
	/* 채널 식별자 */
	private Long chnlSeq;
	/* 채컬 코드 */
	private String chnlCd;
	/* 사이트 코드 */
	private String siteCd;
	/* 서비스 코드 */
	private String srvcCd;
	/* 업체 코드 */
	private String compCd;
	/* 채널 명 */
	private String chnlNm;
	/* 서비스 명*/
	private String siteNm;
	/* 서비스 명*/
	private String srvcNm;
	/* 업체 명 */
	private String compNm;
	/* 사용 여부 */
	private String useYn;		
	/* 서비스 업체 최초 등록 일시  */
	private String rgstDt;
	/* 서비스 업체 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 서비스 업체 최초 등록자 명 */
	private String rgstNm;
	/* 서비스 업체 최종 수정 일시 */
	private String updtDt;
	/* 서비스 업체 최종 수정자 식별자 */
	private Long updtSeq;
	/* 서비스 업체 최종 수정자 명 */
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
		if(!SBNUtils.isNull(srvcSeq)) {
			info.append(", ");
			info.append("srvcSeq=").append(srvcSeq);
		}
		if(!SBNUtils.isNull(compSeq)) {
			info.append(", ");
			info.append("compSeq=").append(compSeq);
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
		if(!SBNUtils.isNull(compCd)) {
			info.append(", ");
			info.append("compCd=").append(compCd);
		}
		if(!SBNUtils.isNull(chnlNm)) {
			info.append(", ");
			info.append("chnlNm=").append(chnlNm);
		}
		if(!SBNUtils.isNull(siteNm)) {
			info.append(", ");
			info.append("siteNm=").append(siteNm);
		}
		if(!SBNUtils.isNull(srvcNm)) {
			info.append(", ");
			info.append("srvcNm=").append(srvcNm);
		}
		if(!SBNUtils.isNull(compNm)) {
			info.append(",");
			info.append("compNm=").append(compNm);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
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
