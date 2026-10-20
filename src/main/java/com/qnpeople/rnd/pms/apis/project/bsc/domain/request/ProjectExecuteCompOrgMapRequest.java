package com.qnpeople.rnd.pms.apis.project.bsc.domain.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectCompExecuteOrgMapSC;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ProjectExecuteCompOrgMapRequest extends QNPWebClientRequestWrapper {

	/* 프로젝트 식별자 */
	private Long prjctSeq;
	/* 업체 조직도 식별자 */
	private Long compOrgSeq;
	/* 업체 식별자 */
	private Long compSeq;
	/* 프로젝트 업체 조직도 매핑 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;
	/* 프로젝트 코드 */
	private String prjctCd;
	/* 업체 코드 */
	private String compCd;
	/* 프로젝트 명 */
	private String prjctNm;
	/* 업체 명 */
	private String compNm;
	
	/* 단일 처리 시, 프로젝트 수행 업체 조직 맵 정보 객체 */
	private ProjectCompExecuteOrgMapDto projectCompExecuteOrgMap;	
	/* 다중 처리 시, 프로젝트 수행 업체 조직 맵 데이터 목록 객체 */
	private List<ProjectCompExecuteOrgMapDto> projectCompExecuteOrgMapDataList;	
	/* 다중 처리 시, 프로젝트 수행 업체 조직 맵 조건 목록 객체 */
	private List<ProjectCompExecuteOrgMapSC> projectCompExecuteOrgMapSCList;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("txId=").append(txId);
		if(!SBNUtils.isNull(prjctSeq)) {
			info.append(", ");
			info.append("prjctSeq=").append(prjctSeq);
		}
		if(!SBNUtils.isNull(compOrgSeq)) {
			info.append(", ");
			info.append("compOrgSeq=").append(compOrgSeq);
		}
		if(!SBNUtils.isNull(compSeq)) {
			info.append(", ");
			info.append("compSeq=").append(compSeq);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(prjctCd)) {
			info.append(", ");
			info.append("prjctCd=").append(prjctCd);
		}
		if(!SBNUtils.isNull(compCd)) {
			info.append(", ");
			info.append("compCd=").append(compCd);
		}
		if(!SBNUtils.isNull(prjctNm)) {
			info.append(", ");
			info.append("prjctNm=").append(prjctNm);
		}
		if(!SBNUtils.isNull(compNm)) {
			info.append(", ");
			info.append("compNm=").append(compNm);
		}
		if(!SBNUtils.isNull(projectCompExecuteOrgMap)) {
			info.append(", ");
			info.append("projectCompExecuteOrgMap=").append(projectCompExecuteOrgMap);
		}
		if(!SBNUtils.isNull(projectCompExecuteOrgMapDataList)) {
			info.append(", ");
			info.append("projectCompExecuteOrgMapDataList=").append(projectCompExecuteOrgMapDataList);
		}
		if(!SBNUtils.isNull(projectCompExecuteOrgMapSCList)) {
			info.append(", ");
			info.append("projectCompExecuteOrgMapSCList=").append(projectCompExecuteOrgMapSCList);
		}		
		info.append(" }");
		return info.toString();
	}
}
