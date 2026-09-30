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
public class ServiceDto extends QNPWebBaseModel {

	/* 서비스 식별자 */
	private Long srvcSeq;
	/* 서비스 카테고리 코드 */
	private String srvcCtgryCd;
	/* 서비스 코드 */
	private String srvcCd;	
	/* 서비스 유형(공통코드: SVC_SRVC_INFO_001) 01:기본, 02:실버, 03:골드, 04:VIP, 05:VVIP, 06:개별, 99:기타 */
	private String srvcTp;
	/* 서비스 유형 명 */
	private String srvcTpNm;	
	/* 서비스 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 서비스 명 */
	private String srvcNm;
	/* 서비스 기본 URI  */
	private String srvcBscUri;
	/* 서비스 설명 */
	private String srvcDesc;	
	
	/* 서비스 최초 등록 일시  */
	private String rgstDt;
	/* 서비스 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 서비스 최초 등록자 명 */
	private String rgstNm;
	/* 서비스 최종 수정 일시 */
	private String updtDt;
	/* 채널 최종 수정자 식별자 */
	private Long updtSeq;
	/* 서비스 최종 수정자 명 */
	private String updtNm;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("srvcSeq= ").append(srvcSeq);
		if(!SBNUtils.isNull(srvcCtgryCd)) {
			info.append(",");
			info.append("srvcCtgryCd=").append(srvcCtgryCd);
		}
		if(!SBNUtils.isNull(srvcCd)) {
			info.append(",");
			info.append("srvcCd=").append(srvcCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(",");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(srvcTp)) {
			info.append(",");
			info.append("srvcTp=").append(srvcTp);
		}
		if(!SBNUtils.isNull(srvcTpNm)) {
			info.append(",");
			info.append("srvcTpNm=").append(srvcTpNm);
		}
		if(!SBNUtils.isNull(srvcDesc)) {
			info.append(",");
			info.append("srvcDesc=").append(srvcDesc);
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
