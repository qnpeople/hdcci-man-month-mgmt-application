package com.qnpeople.rnd.pms.apis.service.service.model;

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
public class ServiceSC extends QNPWebBaseModel {

	/* 서비스 카테고리 코드 */
	private String srvcCtgryCd;
	/* 서비스 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;	
	/* 서비스 유형(공통코드: SVC_SRVC_INFO_001) 01:기본, 02:실버, 03:골드, 04:VIP, 05:VVIP, 06:개별, 99:기타 */
	private String srvcTp;
	/* 서비스 명 */
	private String srvcNm;
	
	/* 서비스 식별자 */
	private Long srvcSeq;
	/* 서비스 코드 */
	private String srvcCd;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("srvcSeq= ").append(srvcSeq);
		if(!SBNUtils.isNull(srvcCtgryCd)) {
			info.append("srvcCtgryCd= ").append(srvcCtgryCd);
		}
		if(!SBNUtils.isNull(srvcCd)) {
			info.append(", ");
			info.append("srvcCd=").append(srvcCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(srvcTp)) {
			info.append(", ");
			info.append("srvcTp=").append(srvcTp);
		}
		if(!SBNUtils.isNull(srvcNm)) {
			info.append(", ");
			info.append("srvcNm=").append(srvcNm);
		}
		info.append(" }");
		return info.toString();
	}
}
