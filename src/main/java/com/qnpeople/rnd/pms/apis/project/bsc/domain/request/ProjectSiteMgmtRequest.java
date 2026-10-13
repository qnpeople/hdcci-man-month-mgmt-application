package com.qnpeople.rnd.pms.apis.project.bsc.domain.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteDto;
import com.qnpeople.rnd.pms.apis.project.bsc.model.ProjectChannelSiteSC;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class ProjectSiteMgmtRequest extends QNPWebClientRequestWrapper {

	/* 프로젝트 사이트 매핑 사용 여부(Y: 사용(기본값), N: 미사용) */
	private String useYn;	
	
	/* 프로젝트 기본 정보 식별자 */
	private Long prjctSeq;
	/* 채널 사이트 지정 식별자 */
	private Long chnlSiteSeq;	
	/* 서비스 식별자 */
	private Long srvcSeq;
	
	/* 프로젝트 기본 정보 코드 */
	private String prjctCd;
	/* 채널 코드 */
	private String chnlCd;
	/* 사이트 코드 */
	private String siteCd;
	/* 서비스 코드 */
	private String srvcCd;
	
	/* 프로젝트 채널 사이트 단건 지정 정보 객체 */
	protected ProjectChannelSiteDto projectChannelSite = null;	
	/* 프로젝트 채널 사이트 다중 지정 정보 목록 객체 */
	private List<ProjectChannelSiteDto> projectChannelSiteList = null;	
	/* 프로젝트 채널 사이트 단건 지정 해제 객체 */
	private ProjectChannelSiteSC projectChannelSiteKey = null;
	/* 프로젝트 채널 사이트 지정 다중 해제 키 목록 객체 */
	private List<ProjectChannelSiteSC> projectChannelSiteKeyList = null;
		
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("txId= ").append(txId);
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(prjctSeq)) {
			info.append(", ");
			info.append("prjctSeq=").append(prjctSeq);
		}
		if(!SBNUtils.isNull(chnlSiteSeq)) {
			info.append(", ");
			info.append("chnlSiteSeq=").append(chnlSiteSeq);
		}
		if(!SBNUtils.isNull(srvcSeq)) {
			info.append(", ");
			info.append("srvcSeq=").append(srvcSeq);
		}
		if(!SBNUtils.isNull(prjctCd)) {
			info.append(", ");
			info.append("prjctCd=").append(prjctCd);
		}
		if(!SBNUtils.isNull(chnlCd)) {
			info.append(", ");
			info.append("chnlCd=").append(chnlCd);
		}
		if(!SBNUtils.isNull(siteCd)) {
			info.append(", ");
			info.append("siteCd=").append(siteCd);
		}
		if(!SBNUtils.isNull(srvcCd)) {
			info.append(", ");
			info.append("srvcCd=").append(srvcCd);
		}		
		if(!SBNUtils.isNull(projectChannelSite)) {
			info.append(", ");
			info.append("projectChannelSite=").append(projectChannelSite.toStringInfo());
		}
		if(!SBNUtils.isNull(projectChannelSiteList)) {
			info.append(", ");
			info.append("projectChannelSiteList=").append(projectChannelSiteList);
		}
		if(!SBNUtils.isNull(projectChannelSiteKey)) {
			info.append(", ");
			info.append("projectChannelSiteKey=").append(projectChannelSiteKey.toStringInfo());
		}
		if(!SBNUtils.isNull(projectChannelSiteKeyList)) {
			info.append(", ");
			info.append("projectChannelSiteKeyList=").append(projectChannelSiteKeyList);
		}		
		info.append(" }");
		return info.toString();
	}
}
