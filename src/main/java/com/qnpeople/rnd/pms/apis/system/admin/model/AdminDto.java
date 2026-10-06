package com.qnpeople.rnd.pms.apis.system.admin.model;

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
public class AdminDto extends QNPWebBaseModel {

	/* 관리자 식별자 */
	private Long admSeq;
	/* 관리자 유형(공통코드: SYS_ADM_INFO_001) 01:통합 관리자, 02:시스템 관리자, 03:서비스 관리자, 04:업체 관리자, 05:프로젝트 관리자, 99:기타 */
	private String admTp;
	/* 관리자 유형 명 */
	private String admTpNm;
	/* 관리자 명 */
	private String admNm;	
	/* 관리자 인증 CID(본인 인증 암호화) */
	private String authCid;
	/* 관리자 로그인 ID */
	private String admLognId;
	/* 관리자 로그인 비밀번호(SHA512 암호화) */
	private String admLognPwd;
	/* 관리자 이메일 주소(AES256 암호화) */
	private String admEmalAddr;
	/* 관리자 휴대폰 번호(AES256 암호화) */
	private String admMblNo;
	/* 관리자 소속 부서명 */
	private String admDeptNm;
	/* 관리자 지위/직급 */
	private String admPstnNm;
	/* 최초 로그인 비밀번호 변경 여부(N: 미 변경(기본값), Y: 변경) */
	private String initPwdChngYn;
	/* 최초 로그인 비밀번호 변경 일시 */
	private String initPwdChngDt;
	/* 관리자 최종 로그인 일시 */
	private String lstLognDt;
	/* 관리자 정보 최초 등록 일시  */
	private String rgstDt;
	/* 관리자 정보 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 관리자 정보 최초 등록자 명 */
	private String rgstNm;
	/* 관리자 정보 최종 수정 일시 */
	private String updtDt;
	/* 관리자 정보 최종 수정자 식별자 */
	private Long updtSeq;
	/* 관리자 정보 최종 수정자 명 */
	private String updtNm;
		
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(admSeq)) {
			info.append(", ");
			info.append("admSeq= ").append(admSeq);
		}
		if(!SBNUtils.isNull(admTp)) {
			info.append(", ");
			info.append("admTp= ").append(admTp);
		}
		if(!SBNUtils.isNull(admTpNm)) {
			info.append(", ");
			info.append("admTpNm= ").append(admTpNm);
		}
		if(!SBNUtils.isNull(admNm)) {
			info.append(", ");
			info.append("admNm= ").append(admNm);
		}
		if(!SBNUtils.isNull(authCid)) {
			info.append(", ");
			info.append("authCid= ").append(authCid);
		}
		if(!SBNUtils.isNull(admLognId)) {
			info.append(", ");
			info.append("admLognId= ").append(admLognId);
		}
		if(!SBNUtils.isNull(admLognPwd)) {
			info.append(", ");
			info.append("admLognPwd= ").append(admLognPwd);
		}
		if(!SBNUtils.isNull(admEmalAddr)) {
			info.append(", ");
			info.append("admEmalAddr= ").append(admEmalAddr);
		}
		if(!SBNUtils.isNull(admMblNo)) {
			info.append(", ");
			info.append("admMblNo= ").append(admMblNo);
		}
		if(!SBNUtils.isNull(admDeptNm)) {
			info.append(", ");
			info.append("admDeptNm= ").append(admDeptNm);
		}
		if(!SBNUtils.isNull(admPstnNm)) {
			info.append(", ");
			info.append("admPstnNm= ").append(admPstnNm);
		}
		if(!SBNUtils.isNull(initPwdChngYn)) {
			info.append(", ");
			info.append("initPwdChngYn= ").append(initPwdChngYn);
		}
		if(!SBNUtils.isNull(initPwdChngDt)) {
			info.append(", ");
			info.append("initPwdChngDt= ").append(initPwdChngDt);
		}
		if(!SBNUtils.isNull(lstLognDt)) {
			info.append(", ");
			info.append("lstLognDt= ").append(lstLognDt);
		}
		if(!SBNUtils.isNull(rgstDt)) {
			info.append(", ");
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
			info.append(", ");
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
