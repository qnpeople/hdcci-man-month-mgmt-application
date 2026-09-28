package com.qnpeople.rnd.pms.apis.common.channel.model;

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
public class ChannelDto extends QNPWebBaseModel {

	/* 채널 식별자 */
	private Long chnlSeq;
	/* 채널 코드 */
	private String chnlCd;
	/* 사용 여부 */
	private String useYn;
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
	/* 채널 최초 등록 일시 */
	private String rgstDt;
	/* 채널 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 채널 최초 등록자 명 */
	private String rgstNm;
	/* 채널 최종 수정 일시 */
	private String updtDt;
	/* 채널 최종 수정자 식별자 */
	private Long updtSeq;
	/* 채널 최종 수정자 명 */
	private String updtNm;
		
	public ChannelDto() {
		super();
	}
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("chnlSeq= ").append(chnlSeq);
		if(!SBNUtils.isNull(chnlCd)) {
			info.append(",");
			info.append("chnlCd=").append(chnlCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(",");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(chnlNm)) {
			info.append(",");
			info.append("chnlNm=").append(chnlNm);
		}
		if(!SBNUtils.isNull(chnlIp)) {
			info.append(",");
			info.append("chnlIp=").append(chnlIp);
		}
		if(!SBNUtils.isNull(chnlPort)) {
			info.append(",");
			info.append("chnlPort=").append(chnlPort);
		}
		if(!SBNUtils.isNull(chnlDmn)) {
			info.append(",");
			info.append("chnlDmn=").append(chnlDmn);
		}
		if(!SBNUtils.isNull(chnlBscUri)) {
			info.append(",");
			info.append("chnlBscUri=").append(chnlBscUri);
		}
		if(!SBNUtils.isNull(chnlDesc)) {
			info.append(",");
			info.append("chnlDesc=").append(chnlDesc);
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
