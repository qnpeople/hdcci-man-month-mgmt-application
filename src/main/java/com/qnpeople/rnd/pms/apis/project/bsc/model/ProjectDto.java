package com.qnpeople.rnd.pms.apis.project.bsc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kr.co.sbn.platformhub.framework.core.common.web.common.data.model.SBNWebBaseModel;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Data
public class ProjectDto extends SBNWebBaseModel {

	/* 프로젝트 식별자 */
	private Long prjctSeq;
	/* 프로젝트 코드 */
	private String prjctCd;
	/* 프로젝트 유형(공통 코드: PRJ_PRJCT_INFO_001) 01:SI 개발, 02:솔루션 구축, 03:파일럿 구축, 04:검증/테스트, 99:기타 */
	private String prjctTp;
	/* 프로젝트 유형 명 */
	private String prjctTpNm;
	/* 프로젝트 상태 유형(공통 코드: PRJ_PRJCT_STAT_001) 01:착수 준비, 02:착수 보고, 03:진행, 04:완료, 05:취소, 06:종료, 99:기타 */
	private String prjctStatTp;
	/* 프로젝트 상태 유형 명 */
	private String prjctStatTpNm;
	/* 프로젝트 배포 유형(공통 코드: PRJ_PRJCT_DPLY_001) 01:정기 배포, 02:선 검증, 03:HOTFIX, 04:특별 기능, 99:기타 */
	private String prjctDplyTp;
	/* 프로젝트 배포 유형 명 */
	private String prjctDplyTpNm;
	/* 프로젝트 검증 유형(공통 코드: PRJ_PRJCT_VRFY_001) 01:회귀, 02:신규 기능, 03:바이너리 인수, 99:기타 */
	private String prjctVrfcTp;
	/* 프로젝트 검증 유형 명 */
	private String prjctVrfcTpNm;
	/* 앱 호환성 유형(공통 코드: PRJ_PRJCT_CPTB_001) 01:AS_IS, 02:TB_BE, 03:미정, 99:기타 */
	private String prjctCptbTp;
	/* 앱 호환성 유형 명 */
	private String prjctCptbTpNm;
	/* 프로젝트 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 프로젝트 삭제 여부(N: 미삭제(기본값), Y: 삭제) */
	private String deltYn;
	/* 프로젝트 명 */
	private String prjctNm;
	/* 프로젝트 시작 일자 */
	private String prjctStrtDt;
	/* 프로젝트 종료 일자 */
	private String prjctEndDt;
	/* 프로젝트 취소 일자 */
	private String prjctCnclDt;
	/* 프로젝트 취소 사유 */
	private String prjctCnclRsn;
	/* 프로젝트 설명 */
	private String prjctDesc;
	/* 비고 */
	private String etc;
	
	/* 프로젝트 기본 정보 최초 등록 일시  */
	private String rgstDt;
	/* 프로젝트 기본 정보 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 프로젝트 기본 정보 최초 등록자 명 */
	private String rgstNm;
	/* 프로젝트 기본 정보 최종 수정 일시 */
	private String updtDt;
	/* 프로젝트 기본 정보 최종 수정자 식별자 */
	private Long updtSeq;
	/* 프로젝트 기본 정보 최종 수정자 명 */
	private String updtNm;
	/* 프로젝트 기본 정보 최종 삭제 일시 */
	private String deltDt;
	/* 프로젝트 기본 정보 최종 삭제 식별자 */
	private Long deltSeq;
	/* 프로젝트 기본 정보 최종 삭제 명 */
	private String deltNm;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id= ").append(id);
		if(!SBNUtils.isNull(prjctSeq)) {
			info.append(", ");
			info.append("prjctSeq=").append(prjctSeq);
		}
		if(!SBNUtils.isNull(prjctCd)) {
			info.append(", ");
			info.append("prjctCd=").append(prjctCd);
		}
		if(!SBNUtils.isNull(prjctTp)) {
			info.append(", ");
			info.append("prjctTp=").append(prjctTp);
		}
		if(!SBNUtils.isNull(prjctTpNm)) {
			info.append(", ");
			info.append("prjctTpNm=").append(prjctTpNm);
		}
		if(!SBNUtils.isNull(prjctStatTp)) {
			info.append(", ");
			info.append("prjctStatTp=").append(prjctStatTp);
		}
		if(!SBNUtils.isNull(prjctStatTpNm)) {
			info.append(", ");
			info.append("prjctStatTpNm=").append(prjctStatTpNm);
		}
		if(!SBNUtils.isNull(prjctDplyTp)) {
			info.append(", ");
			info.append("prjctDplyTp=").append(prjctDplyTp);
		}
		if(!SBNUtils.isNull(prjctDplyTpNm)) {
			info.append(", ");
			info.append("prjctDplyTpNm=").append(prjctDplyTpNm);
		}
		if(!SBNUtils.isNull(prjctVrfcTp)) {
			info.append(", ");
			info.append("prjctVrfcTp=").append(prjctVrfcTp);
		}
		if(!SBNUtils.isNull(prjctVrfcTpNm)) {
			info.append(", ");
			info.append("prjctVrfcTpNm=").append(prjctVrfcTpNm);
		}
		if(!SBNUtils.isNull(prjctCptbTp)) {
			info.append(", ");
			info.append("prjctCptbTp=").append(prjctCptbTp);
		}
		if(!SBNUtils.isNull(prjctCptbTpNm)) {
			info.append(", ");
			info.append("prjctCptbTpNm=").append(prjctCptbTpNm);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(deltYn)) {
			info.append(", ");
			info.append("deltYn=").append(deltYn);
		}
		if(!SBNUtils.isNull(prjctNm)) {
			info.append(", ");
			info.append("prjctNm=").append(prjctNm);
		}
		if(!SBNUtils.isNull(prjctStrtDt)) {
			info.append(", ");
			info.append("prjctStrtDt=").append(prjctStrtDt);
		}
		if(!SBNUtils.isNull(prjctEndDt)) {
			info.append(", ");
			info.append("prjctEndDt=").append(prjctEndDt);
		}
		if(!SBNUtils.isNull(prjctCnclDt)) {
			info.append(", ");
			info.append("prjctCnclDt=").append(prjctCnclDt);
		}
		if(!SBNUtils.isNull(prjctCnclRsn)) {
			info.append(", ");
			info.append("prjctCnclRsn=").append(prjctCnclRsn);
		}
		if(!SBNUtils.isNull(prjctDesc)) {
			info.append(", ");
			info.append("prjctDesc=").append(prjctDesc);
		}
		if(!SBNUtils.isNull(etc)) {
			info.append(", ");
			info.append("etc=").append(etc);
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
		if(!SBNUtils.isNull(deltDt)) {
			info.append(",");
			info.append("deltDt=").append(deltDt);
		}
		if(!SBNUtils.isNull(deltSeq)) {
			info.append(", ");
			info.append("deltSeq=").append(deltSeq);
		}
		if(!SBNUtils.isNull(deltNm)) {
			info.append(", ");
			info.append("deltNm=").append(deltNm);
		}
		info.append(" }");
		return info.toString();
	}
}
