package com.qnpeople.rnd.pms.apis.company.domain.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanyDto;
import com.qnpeople.rnd.pms.apis.company.model.ServiceCompanySC;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ServiceCompanyMgmtRequest extends QNPWebClientRequestWrapper {

	/* 서비스 업체 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 채널별 사이트 식별자 */
	private Long chnlSiteSeq;
	/* 서비스 식별자 */
	private Long srvcSeq;
	/* 업체 식별자 */
	private Long compSeq;
	/* 채널 코드 */
	private String chnlCd;
	/* 사이트 코드 */
	private String siteCd;
	/* 서비스 코드 */
	private String srvcCd;
	/* 업체 코드 */
	private String compCd;
	/* 채널 명 */
	private String chnlNm;
	/* 사이트 명 */
	private String siteNm;
	/* 서비스 명 */
	private String srvcNm;
	/* 업체 명 */
	private String compNm;
		
	/* 서비스 업체 정보 객체 */
	private ServiceCompanyDto serviceCompany;
	/* 서비스 업체 정보 목록 객체 */
	private List<ServiceCompanyDto> serviceCompanyList;
	/* 서비스 업체 삭제 키 목록 객체 */
	private List<ServiceCompanySC> serviceCompanyKeyList;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("txId= ").append(txId);
		if(!SBNUtils.isNull(chnlSiteSeq)) {
			info.append(", ");
			info.append("chnlSiteSeq=").append(chnlSiteSeq);
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
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
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
			info.append(", ");
			info.append("compNm=").append(compNm);
		}
		if(!SBNUtils.isNull(serviceCompany)) {
			info.append(", ");
			info.append("serviceCompany=").append(serviceCompany.toStringInfo());
		}
		if(!SBNUtils.isNull(serviceCompanyList)) {
			info.append(", ");
			info.append("serviceCompanyList=").append(serviceCompanyList);
		}
		if(!SBNUtils.isNull(serviceCompanyKeyList)) {
			info.append(", ");
			info.append("serviceCompanyKeyList=").append(serviceCompanyKeyList);
		}		
		info.append(" }");
		return info.toString();
	}
}
