package com.qnpeople.rnd.pms.apis.company.model;

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
public class CompanySC extends QNPWebBaseModel {

	/* 업체 식별자 */
	private Long compSeq;
	/* 업체 식별 코드 */
	private String compCd;
	/* 업체 유형(공통코드: CMP_COMP_INFO_001) 01:일반, 02:법인, 03:개인, 99:기타  */
	private String compTp;
	/* 업체 청약 상태 유형(공통코드: CMP_COMP_SUBSCR_STAT_001) 01:청약 신청, 02:청약 진행, 03:청약 승인, 04:청약 완료, 05:청약 반려. 06:청약 취소, 07:청약 보류, 99:기타 */
	private String subscrStatTp;
	/* 업체 계약 상태 유형(공통코드: CMP_COMP_CNTRCT_STAT_001) 01:계약 신청, 02:계약 진행, 03:계약 승인, 04:계약 완료, 05:계약 반려, 06:계약 취소, 07:계약 보류, 99:기타 */
	private String cntrctStatTp;
	/* 업체 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 업체 삭제 여부(N: 삭제(기본), Y: 미삭제) */
	private String deltYn;
	/* 업체 사업자 번호 */
	private String brn;
	/* 업체 명 */
	private String compNm;
	
	private Long deltSeq;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("compSeq= ").append(compSeq);
		if(!SBNUtils.isNull(compCd)) {
			info.append(", ");
			info.append("compCd=").append(compCd);
		}
		if(!SBNUtils.isNull(compTp)) {
			info.append(", ");
			info.append("compTp=").append(compTp);
		}
		if(!SBNUtils.isNull(subscrStatTp)) {
			info.append(", ");
			info.append("subscrStatTp=").append(subscrStatTp);
		}
		if(!SBNUtils.isNull(cntrctStatTp)) {
			info.append(", ");
			info.append("cntrctStatTp=").append(cntrctStatTp);
		}	
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(deltYn)) {
			info.append(", ");
			info.append("deltYn=").append(deltYn);
		}
		if(!SBNUtils.isNull(brn)) {
			info.append(", ");
			info.append("brn=").append(brn);
		}
		if(!SBNUtils.isNull(compNm)) {
			info.append(", ");
			info.append("compNm=").append(compNm);
		}
		if(!SBNUtils.isNull(deltSeq)) {
			info.append(", ");
			info.append("deltSeq=").append(deltSeq);
		}		
		info.append(" }");
		return info.toString();
	}
}
