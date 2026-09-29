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
public class SrvcCategoryTreeDto extends QNPWebBaseModel {

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
	/* 서비스 카테고리 트리 명 */
	private String srvcCtgryTreeNm;
	/* 서비스 카테고리 설명 */
	private String srvcCtgryDesc;
	/* 서비스 카테고리 정렬 경로 명 */
	private String srvcCtgrySortPath;
	
	
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
		if(!SBNUtils.isNull(srvcCtgryTreeNm)) {
			info.append(",");
			info.append("srvcCtgryTreeNm=").append(srvcCtgryTreeNm);
		}
		if(!SBNUtils.isNull(srvcCtgryDesc)) {
			info.append(",");
			info.append("srvcCtgryDesc=").append(srvcCtgryDesc);
		}
		if(!SBNUtils.isNull(srvcCtgrySortPath)) {
			info.append(",");
			info.append("srvcCtgrySortPath=").append(srvcCtgrySortPath);
		}
		info.append(" }");
		return info.toString();
	}
}
