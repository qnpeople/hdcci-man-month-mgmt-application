package com.qnpeople.rnd.pms.apis.service.category.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class SrvcCategoryMgmtRequest extends QNPWebClientRequestWrapper {

	/* 서비스 카테고리 코드 */
	private String srvcCtgryCd;
	/* 서비스 카테고리 레벨 */
	private Short srvcCtgryLvl;
	/* 서비스 카테고리 순서 */
	private Short srvcCrgryOrd;
	/* 서비스 카테고리 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 상위 서비스 카테고리 코드 */
	private String prntSrvcCtgryCd;
		
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(SBNUtils.isNull(txId)) {
			info.append(", ");
			info.append("txId=").append(txId);
		}
		if(!SBNUtils.isNull(srvcCtgryCd)) {
			info.append(", ");
			info.append("srvcCtgryCd=").append(srvcCtgryCd);
		}
		if(!SBNUtils.isNull(srvcCtgryLvl) && (srvcCtgryLvl > 0)) {
			info.append(", ");
			info.append("srvcCtgryLvl=").append(srvcCtgryLvl);
		}
		if(!SBNUtils.isNull(srvcCrgryOrd) && (srvcCrgryOrd > 0)) {
			info.append(", ");
			info.append("srvcCrgryOrd=").append(srvcCrgryOrd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(prntSrvcCtgryCd)) {
			info.append(", ");
			info.append("prntSrvcCtgryCd=").append(prntSrvcCtgryCd);
		}
		info.append(" }");
		return info.toString();
	}
}
