package com.qnpeople.rnd.pms.apis.project.bsc.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class BaseProjectMgmtRequest extends QNPWebClientRequestWrapper {

	/* 프로젝트 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;	
	/* 프로젝트 유형(공통 코드: PRJ_PRJCT_INFO_001) 01:SI 개발, 02:솔루션 구축, 03:파일럿 구축, 04:검증/테스트, 99:기타 */
	private String prjctTp;
	/* 프로젝트 상태 유형(공통 코드: PRJ_PRJCT_STAT_001) 01:착수 준비, 02:착수 보고, 03:진행, 04:완료, 05:취소, 06:종료, 99:기타 */
	private String prjctStatTp;
	/* 프로젝트 배포 유형(공통 코드: PRJ_PRJCT_DPLY_001) 01:정기 배포, 02:선 검증, 03:HOTFIX, 04:특별 기능, 99:기타 */
	private String prjctDplyTp;
	/* 프로젝트 검증 유형(공통 코드: PRJ_PRJCT_VRFY_001) 01:회귀, 02:신규 기능, 03:바이너리 인수, 99:기타 */
	private String prjctVrfcTp;
	/* 앱 호환성 유형(공통 코드: PRJ_PRJCT_CPTB_001) 01:AS_IS, 02:TB_BE, 03:미정, 99:기타 */
	private String prjctCptbTp;
	/* 프로젝트 기본 정보 명 */
	private String prjctNm;	
	/* 프로젝트 시작 일자 */
	private String prjctStrtDt;
	/* 프로젝트 종료 일자 */
	private String prjctEndDt;
	/* 기타 */
	private String etc;
	
	/* 프로젝트 기본 정보 식별자 */
	private Long prjctSeq;
	/* 프로젝트 기본 정보 코드 */
	private String prjctCd;
	
	/* 프로젝트 기본 정보 객체 */
	private ProjectDto project;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("txId= ").append(txId);
		if(!SBNUtils.isNull(prjctSeq)) {
			info.append(", ");
			info.append("prjctSeq=").append(prjctSeq);
		}
		if(!SBNUtils.isNull(prjctCd)) {
			info.append(", ");
			info.append("prjctCd=").append(prjctCd);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(prjctTp)) {
			info.append(", ");
			info.append("prjctTp=").append(prjctTp);
		}
		if(!SBNUtils.isNull(prjctStatTp)) {
			info.append(", ");
			info.append("prjctStatTp=").append(prjctStatTp);
		}
		if(!SBNUtils.isNull(prjctDplyTp)) {
			info.append(", ");
			info.append("prjctDplyTp=").append(prjctDplyTp);
		}
		if(!SBNUtils.isNull(prjctVrfcTp)) {
			info.append(", ");
			info.append("prjctVrfcTp=").append(prjctVrfcTp);
		}
		if(!SBNUtils.isNull(prjctCptbTp)) {
			info.append(", ");
			info.append("prjctCptbTp=").append(prjctCptbTp);
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
		if(!SBNUtils.isNull(etc)) {
			info.append(", ");
			info.append("etc=").append(etc);
		}
		if(!SBNUtils.isNull(project)) {
			info.append(", ");
			info.append("project=").append(project.toStringInfo());
		}
		info.append(" }");
		return info.toString();
	}
}
