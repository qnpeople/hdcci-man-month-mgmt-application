package com.qnpeople.rnd.pms.apis.auth.domain.request;

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
public class AccessAuthRequest extends QNPWebClientRequestWrapper {

	/* 시스템 접근자 인증 구분 유형 객체 (AppAuthGubunType 클래스 참조)  */
	private String authGubunType;
	
	//	관리자 
	/* 관리자 식별자 */
	private String adminSeq;
	/* 관리자 유형(공통코드: SYS_ADM_INFO_001) 01:통합 관리자, 02:시스템 관리자, 03:서비스 관리자, 04:업체 관리자, 05:프로젝트 관리자, 99:기타 */
	private String admTp;
	/* 관리자 명 */
	private String admNm;
	/* 관리자 로그인 ID 정보 */
	private String admLognId;
	/* 관리자 로그인 비밀번호 정보 */
	private String admLognPwd;
	
	// 근무자 
	/* 근무자 소속 업체 식별자 */
	private Long compSeq;
	/* 근무자 소속 업체 코드 */
	private String compCd;
	/* 근무자 소속 업체 명 */
	private String compNm;
	/* 근무자 식별자 */
	private Long empleSeq;
	/* 근무자 유형(공통코드: CMP_EMP_INFO_001) 01.정규직, 02:계약직, 03:위탁직, 04:프리랜서, 99:기타 */
	private String empleTp;
	/* 근무자 상태 유형(공통코드: CMP_EMP_STAT_001) 01.정상, 02:휴직(육아), 03:병가, 04:퇴사, 05:계약종료, 99:기타 */
	private String empleStatTp;
	/* 근무자 등급 유형(공통코드: CMP_EMP_DGR_001) 01.초급, 02:중급, 03:고급, 04:특급, 05:해당없음, 99:기타 */
	private String empleDgrTp;
	/* 근무자 사용 여부 */
	private String useYn;
	/* 근무자 명 */
	private String empleNm;
	/* 근무자 로그인 ID */
	private String empleLognId;
	/* 근무자 로그인 비밀번호 */
	private String empleLognPwd;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(authGubunType)) {
			info.append(", ");
			info.append("authGubunType=").append(authGubunType);
		}
		//	관리자
		if(!SBNUtils.isNull(adminSeq)) {
			info.append(", ");
			info.append("adminSeq=").append(adminSeq);
		}
		if(!SBNUtils.isNull(admTp)) {
			info.append(", ");
			info.append("admTp=").append(admTp);
		}
		if(!SBNUtils.isNull(admNm)) {
			info.append(", ");
			info.append("admNm=").append(admNm);
		}
		if(!SBNUtils.isNull(admLognId)) {
			info.append(", ");
			info.append("admLognId=").append(admLognId);
		}
		if(!SBNUtils.isNull(admLognPwd)) {
			info.append(", ");
			info.append("admLognPwd=").append(admLognPwd);
		}
		//	업체 근무자
		if(!SBNUtils.isNull(compSeq)) {
			info.append(", ");
			info.append("compSeq=").append(compSeq);
		}
		if(!SBNUtils.isNull(compCd)) {
			info.append(", ");
			info.append("compCd=").append(compCd);
		}
		if(!SBNUtils.isNull(compNm)) {
			info.append(", ");
			info.append("compNm=").append(compNm);
		}
		if(!SBNUtils.isNull(empleSeq)) {
			info.append(", ");
			info.append("empleSeq=").append(empleSeq);
		}
		if(!SBNUtils.isNull(empleTp)) {
			info.append(", ");
			info.append("empleTp=").append(empleTp);
		}
		if(!SBNUtils.isNull(empleStatTp)) {
			info.append(", ");
			info.append("empleStatTp=").append(empleStatTp);
		}
		if(!SBNUtils.isNull(empleDgrTp)) {
			info.append(", ");
			info.append("empleDgrTp=").append(empleDgrTp);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(", ");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(empleNm)) {
			info.append(", ");
			info.append("empleNm=").append(empleNm);
		}
		if(!SBNUtils.isNull(empleLognId)) {
			info.append(", ");
			info.append("empleLognId=").append(empleLognId);
		}
		if(!SBNUtils.isNull(empleLognPwd)) {
			info.append(", ");
			info.append("empleLognPwd=").append(empleLognPwd);
		}
		info.append(" }");
		return info.toString();
	}
}
