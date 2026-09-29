package com.qnpeople.rnd.pms.apis.service.category.model;

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
public class SrvcCategoryDto extends QNPWebBaseModel {
	
	/* 서비스 카테고리 코드 */
	private String srvcCtgryCd;
	/* 서비스 카테고리 레벨 */
	private Short srvcCtgryLvl;
	/* 서비스 카테고리 순서 */
	private Short srvcCtgryOrd;
	/* 서비스 카테고리 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 상위 서비스 카테고리 코드 */
	private String prntSrvcCtgryCd;
	/* 서비스 카테고리 명 */
	private String srvcCtgryNm;
	/* 서비스 카테고리 설명 */
	private String srvcCtgryDesc;
	/* 서비스 카테고리 최초 등록 일시 */	
	private String rgstDt;
	/* 서비스 카테고리 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 서비스 카테고리 최초 등록자 명 */
	private String rgstNm;
	/* 서비스 카테고리 최종 수정 일시 */
	private String updtDt;
	/* 서비스 카테고리 최종 수정자 식별자 */
	private Long updtSeq;
	/* 서비스 카테고리 최종 수정자 명 */
	private String updtNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("srvcCtgryCd= ").append(srvcCtgryCd);
		if(!SBNUtils.isNull(srvcCtgryLvl) && (srvcCtgryLvl > 0)) {
			info.append(",");
			info.append("srvcCtgryLvl=").append(srvcCtgryLvl);
		}
		if(!SBNUtils.isNull(srvcCtgryOrd) && (srvcCtgryOrd > 0)) {
			info.append(",");
			info.append("srvcCtgryOrd=").append(srvcCtgryOrd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(",");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(prntSrvcCtgryCd)) {
			info.append(",");
			info.append("prntSrvcCtgryCd=").append(prntSrvcCtgryCd);
		}
		if(!SBNUtils.isNull(srvcCtgryNm)) {
			info.append(",");
			info.append("srvcCtgryNm=").append(srvcCtgryNm);
		}
		if(!SBNUtils.isNull(srvcCtgryDesc)) {
			info.append(",");
			info.append("srvcCtgryDesc=").append(srvcCtgryDesc);
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
