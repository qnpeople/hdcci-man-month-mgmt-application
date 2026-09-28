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
public class CodeDto extends QNPWebBaseModel {
	
	/* 그룹 코드 */
	private String grpCd;
	/* 코드 */
	private String cd;
	/* 코드 순서 */
	private Short cdOrd;
	/* 코드 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 코드 명 */
	private String cdNm;
	/* 변이1 */
	private String vrnt1;
	/* 변이2 */
	private String vrnt2;
	/* 변이3 */
	private String vrnt3;
	/* 변이4 */
	private String vrnt4;
	/* 변이5 */
	private String vrnt5;
	/* 코드 설명 */
	private String cdDesc;
	/* 코드 최초 등록 일시 */
	private String rgstDt;
	/* 코드 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 코드 최초 등록자 명 */
	private String rgstNm;
	/* 코드 최종 수정 일시 */
	private String updtDt;
	/* 코드 최종 수정자 식별자 */
	private Long updtSeq;
	/* 코드 최종 수정자 멍 */
	private String updtNm;	
	
	public CodeDto() {
		super();
	}
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("grpCd= ").append(grpCd);
		info.append(",");
		info.append("cd=").append(cd);
		if(!SBNUtils.isNull(cdOrd)) {
			info.append(",");
			info.append("cdOrd=").append(cdOrd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(",");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(cdNm)) {
			info.append(",");
			info.append("cdNm=").append(cdNm);
		}
		if(!SBNUtils.isNull(vrnt1)) {
			info.append(",");
			info.append("vrnt1=").append(vrnt1);
		}
		if(!SBNUtils.isNull(vrnt2)) {
			info.append(",");
			info.append("vrnt2=").append(vrnt2);
		}
		if(!SBNUtils.isNull(vrnt3)) {
			info.append(",");
			info.append("vrnt3=").append(vrnt3);
		}
		if(!SBNUtils.isNull(vrnt4)) {
			info.append(",");
			info.append("vrnt4=").append(vrnt4);
		}
		if(!SBNUtils.isNull(vrnt5)) {
			info.append(",");
			info.append("vrnt5=").append(vrnt5);
		}
		if(!SBNUtils.isNull(cdDesc)) {
			info.append(",");
			info.append("cdDesc=").append(cdDesc);
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
