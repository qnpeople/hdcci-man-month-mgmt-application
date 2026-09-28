package com.qnpeople.rnd.pms.apis.common.code.model;

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
public class GroupCodeTreeDto extends QNPWebBaseModel {

	private String grpCd;
	private Short grpCdLvl;
	private Short  grpCdOrd;
	private String useYn;
	private String grpCdNm;
	private String grpCdTreeNm;
	private String vrnt1;
	private String vrnt2;
	private String vrnt3;
	private String vrnt4;
	private String vrnt5;
	private String prntGrpCd;
	private String grpCdSortPath;
	
	public GroupCodeTreeDto() {
		super();
	}
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("grpCd=").append(grpCd);
		if(!SBNUtils.isNull(grpCdLvl)) {
			info.append(", ");
			info.append("grpCdLvl=").append(grpCdLvl);
		}
		if(!SBNUtils.isNull(grpCdOrd)) {
			info.append(", ");
			info.append("grpCdOrd=").append(grpCdOrd);
		}
		if(!SBNUtils.isNull(grpCdNm)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(grpCdNm)) {
			info.append(", ");
			info.append("grpCdNm=").append(grpCdNm);
		}
		if(!SBNUtils.isNull(grpCdTreeNm)) {
			info.append(", ");
			info.append("grpCdTreeNm=").append(grpCdTreeNm);
		}
		if(!SBNUtils.isNull(vrnt1)) {
			info.append(", ");
			info.append("vrnt1=").append(vrnt1);
		}
		if(!SBNUtils.isNull(vrnt2)) {
			info.append(", ");
			info.append("vrnt2=").append(vrnt2);
		}
		if(!SBNUtils.isNull(vrnt3)) {
			info.append(", ");
			info.append("vrnt3=").append(vrnt3);
		}
		if(!SBNUtils.isNull(vrnt4)) {
			info.append(", ");
			info.append("vrnt4=").append(vrnt4);
		}
		if(!SBNUtils.isNull(vrnt5)) {
			info.append(", ");
			info.append("vrnt5=").append(vrnt5);
		}
		if(!SBNUtils.isNull(prntGrpCd)) {
			info.append(", ");
			info.append("prntGrpCd=").append(prntGrpCd);
		}
		if(!SBNUtils.isNull(grpCdSortPath)) {
			info.append(", ");
			info.append("grpCdSortPath=").append(grpCdSortPath);
		}
		info.append(" }");
		return info.toString();
	}
}
