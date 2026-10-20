package com.qnpeople.rnd.pms.apis.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Data
public class AdminAccessAuthDto extends AccessAuthDto {

	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id= ").append(id);
//		if(!SBNUtils.isNull()) {
//			info.append(", ");
//			info.append("=").append();
//		}
//		if(!SBNUtils.isNull()) {
//			info.append(", ");
//			info.append("=").append();
//		}
//		if(!SBNUtils.isNull()) {
//			info.append(", ");
//			info.append("=").append();
//		}
//		if(!SBNUtils.isNull()) {
//			info.append(", ");
//			info.append("=").append();
//		}
		info.append(" }");
		return info.toString();
	}
}
