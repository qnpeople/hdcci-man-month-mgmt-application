package com.qnpeople.rnd.pms.apis.service.service.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;


@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ServiceMgmtRequest extends QNPWebClientRequestWrapper {

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
	
	private ServiceDto serviceDto;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(txId)) {
			info.append(", ");
			info.append("txId= ").append(txId);
		}
		if(!SBNUtils.isNull(srvcCtgryCd)) {
			info.append(", ");
			info.append("srvcCtgryCd= ").append(srvcCtgryCd);
		}
		if(!SBNUtils.isNull(srvcSeq) && (srvcSeq > 0L)) {
			info.append(", ");
			info.append("srvcSeq=").append(srvcSeq);
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
		if(!SBNUtils.isNull(serviceDto)) {
			info.append(", ");
			info.append("serviceDto=").append(serviceDto.toStringInfo());
		}		
		info.append(" }");
		return info.toString();
	}
}
