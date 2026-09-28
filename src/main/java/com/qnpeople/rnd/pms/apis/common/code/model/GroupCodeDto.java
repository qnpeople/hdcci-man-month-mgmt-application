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
public class GroupCodeDto extends QNPWebBaseModel {
	
	private String grpCd;
	private Short grpCdLvl;
	private Short  grpCdOrd;
	private String useYn;
	private String grpCdNm;
	private String prntGrpCd;
	private String prntGrpCdNm;
	private String vrnt1;
	private String vrnt2;
	private String vrnt3;
	private String vrnt4;
	private String vrnt5;
	private String grpCdDesc;
	private String rgstDt;
	private Long rgstSeq;
	private String rgstNm;
	private String updtDt;
	private Long updtSeq;
	private String updtNm;
	
	public GroupCodeDto() {
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
		if(!SBNUtils.isNull(prntGrpCd)) {
			info.append(", ");
			info.append("prntGrpCd=").append(prntGrpCd);
		}
		if(!SBNUtils.isNull(prntGrpCdNm)) {
			info.append(", ");
			info.append("prntGrpCdNm=").append(prntGrpCdNm);
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
		if(!SBNUtils.isNull(grpCdDesc)) {
			info.append(", ");
			info.append("grpCdDesc=").append(grpCdDesc);
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
