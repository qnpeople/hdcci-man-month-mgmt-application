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
public class CompanyDto extends QNPWebBaseModel {

	/* 업체 식별자 */
	private Long compSeq;
	/* 업체 식별 코드 */
	private String compCd;
	/* 업체 유형(공통코드: CMP_COMP_INFO_001) 01:일반, 02:법인, 03:개인, 99:기타  */
	private String compTp;
	/* 업체 유형 명 */
	private String compTpNm;
	/* 업체 청약 상태 유형(공통코드: CMP_COMP_SUBSCR_STAT_001) 01:청약 신청, 02:청약 진행, 03:청약 승인, 04:청약 완료, 05:청약 반려. 06:청약 취소, 07:청약 보류, 99:기타 */
	private String subscrStatTp;
	/* 업체 청약 상태 유형 명 */
	private String subscrStatTpNm;
	/* 업체 계약 상태 유형(공통코드: CMP_COMP_CNTRCT_STAT_001) 01:계약 신청, 02:계약 진행, 03:계약 승인, 04:계약 완료, 05:계약 반려, 06:계약 취소, 07:계약 보류, 99:기타 */
	private String cntrctStatTp;
	/* 업체 계약 상태 유형 명 */
	private String cntrctStatTpNm;
	/* 업체 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 업체 사업자 번호 */
	private String brn;
	/* 업체 명 */
	private String compNm;
	/* 업체 노출 명 */
	private String compDispNm;
	/* 업체 설명 */
	private String compDesc;
	/* 업체 전화 번호 */
	private String compTelNo;
	/* 업체 우편번호 */
	private String compZipCd;
	/* 업체 기본 주소 */
	private String compAddr;
	/* 업체 상세 주소 */
	private String compDtlAddr;
	/* 업체 위치 정보 */
	private String compLoc;
	/* 업체 대표자 명 */
	private String rprsntvNm;
	/* 업체 대표자 이메일 주소(AES156 암호화) */
	private String rprsntvEmalAddr;
	/* 업체 대표자 휴대폰 번호(AES156 암호화)*/
	private String rprsntvMblNo;		
	/* 업체 최초 등록 일시  */
	private String rgstDt;
	/* 업체 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 업체 최초 등록자 명 */
	private String rgstNm;
	/* 업체 최종 수정 일시 */
	private String updtDt;
	/* 업체 최종 수정자 식별자 */
	private Long updtSeq;
	/* 업체 최종 수정자 명 */
	private String updtNm;
	
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
		if(!SBNUtils.isNull(compTpNm)) {
			info.append(", ");
			info.append("compTpNm=").append(compTpNm);
		}
		if(!SBNUtils.isNull(subscrStatTp)) {
			info.append(", ");
			info.append("subscrStatTp=").append(subscrStatTp);
		}
		if(!SBNUtils.isNull(subscrStatTpNm)) {
			info.append(", ");
			info.append("subscrStatTpNm=").append(subscrStatTpNm);
		}
		if(!SBNUtils.isNull(cntrctStatTp)) {
			info.append(", ");
			info.append("cntrctStatTp=").append(cntrctStatTp);
		}
		if(!SBNUtils.isNull(cntrctStatTpNm)) {
			info.append(", ");
			info.append("cntrctStatTpNm=").append(cntrctStatTpNm);
		}		
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(brn)) {
			info.append(", ");
			info.append("brn=").append(brn);
		}
		if(!SBNUtils.isNull(compNm)) {
			info.append(", ");
			info.append("compNm=").append(compNm);
		}
		if(!SBNUtils.isNull(compDispNm)) {
			info.append(", ");
			info.append("compDispNm=").append(compDispNm);
		}
		if(!SBNUtils.isNull(compDesc)) {
			info.append(", ");
			info.append("compDesc=").append(compDesc);
		}
		if(!SBNUtils.isNull(compTelNo)) {
			info.append(", ");
			info.append("compTelNo=").append(compTelNo);
		}
		if(!SBNUtils.isNull(compZipCd)) {
			info.append(",");
			info.append("compZipCd=").append(compZipCd);
		}
		if(!SBNUtils.isNull(compAddr)) {
			info.append(", ");
			info.append("compAddr=").append(compAddr);
		}
		if(!SBNUtils.isNull(compDtlAddr)) {
			info.append(", ");
			info.append("compDtlAddr=").append(compDtlAddr);
		}
		if(!SBNUtils.isNull(compLoc)) {
			info.append(", ");
			info.append("compLoc=").append(compLoc);
		}
		if(!SBNUtils.isNull(rprsntvNm)) {
			info.append(", ");
			info.append("rprsntvNm=").append(rprsntvNm);
		}
		if(!SBNUtils.isNull(rprsntvEmalAddr)) {
			info.append(", ");
			info.append("rprsntvEmalAddr=").append(rprsntvEmalAddr);
		}
		if(!SBNUtils.isNull(rprsntvMblNo)) {
			info.append(", ");
			info.append("rprsntvMblNo=").append(rprsntvMblNo);
		}
		if(!SBNUtils.isNull(rgstDt)) {
			info.append(",");
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
			info.append(",");
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
