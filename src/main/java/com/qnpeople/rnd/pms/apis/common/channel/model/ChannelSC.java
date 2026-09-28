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
public class ChannelSC extends QNPWebBaseModel {

	/* 채널 식별자 */
	private Long chnlSeq;
	/* 채널 코드 */
	private String chnlCd;
	/* 채널 명 */
	private String chnlNm;
	
	public ChannelSC() {
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
		if(!SBNUtils.isNull(chnlNm)) {
			info.append(",");
			info.append("chnlNm=").append(chnlNm);
		}
		info.append(" }");
		return info.toString();
	}
}
