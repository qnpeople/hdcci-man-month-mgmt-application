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
public class EmployeeDto extends QNPWebBaseModel {

	/* 업체 식별자 */
	private Long compSeq;
	/* 근무자 식별자 */
	private Long empleSeq;
	/* 근무자 유형(공통코드: CMP_EMP_INFO_001) 01.정규직, 02:계약직, 03:위탁직, 04:프리랜서, 99:기타 */
	private String empleTp;
	/* 근무자 유형 명 */
	private String empleTpNm;
	/* 근무자 상태 유형(공통코드: CMP_EMP_STAT_001) 01.정상, 02:휴직(육아), 03:병가, 04:퇴사, 05:계약종료, 99:기타 */
	private String empleStatTp;
	/* 근무자 상태 유형 명 */
	private String empleStatTpNm;
	/* 근무자 등급 유형(공통코드: CMP_EMP_DGR_001) 01.초급, 02:중급, 03:고급, 04:특급, 05:해당없음, 99:기타 */
	private String empleDgrTp;
	/* 근무자 등급 유형 명 */
	private String empleDgrTpNm;
	/* 근무자 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 근무자 명 */
	private String empleNm;	
	/* 근무자 로그인 ID */
	private String empleLognId;
	/* 근무자 로그인 비밀번호(SHA512암호화) */
	private String empleLognPwd;
	/* 근무자 로그인 비밀번호(본인인증 암호화) */
	private String authCid;
	/* 근무자 소속 부서 명 */
	private String empleDeptNm;
	/* 근무자 직위/직급 명 */
	private String emplePstnNm;
	/* 근무자 휴대폰 번호(AES256암호화) */
	private String empleMblNo;
	/* 최초 근무자 비밀번호 변경 여부(N: 미 변경(기본값), Y: 변경) */
	private String initPwdChngYn;
	/* 최초 근무자 비밀번호 변경 일시 */
	private String initPwdChngDt;
	/* 근무자 최근 로그인 일시 */
	private String lstLognDt;
	/* 기타 부가 설명 */
	private String adtnDesc;	
	/* 근무자 최초 등록 일시  */
	private String rgstDt;
	/* 근무자 최초 등록자 식별자 */
	private Long rgstSeq;
	/* 근무자 최초 등록자 명 */
	private String rgstNm;
	/* 근무자 최종 수정 일시 */
	private String updtDt;
	/* 근무자 최종 수정자 식별자 */
	private Long updtSeq;
	/* 근무자 최종 수정자 명 */
	private String updtNm;
	
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("compSeq= ").append(compSeq);
		if(!SBNUtils.isNull(empleSeq)) {
			info.append(",");
			info.append("empleSeq=").append(empleSeq);
		}
		if(!SBNUtils.isNull(empleTp)) {
			info.append(",");
			info.append("empleTp=").append(empleTp);
		}
		if(!SBNUtils.isNull(empleTpNm)) {
			info.append(",");
			info.append("empleTpNm=").append(empleTpNm);
		}
		if(!SBNUtils.isNull(empleStatTp)) {
			info.append(",");
			info.append("empleStatTp=").append(empleStatTp);
		}
		if(!SBNUtils.isNull(empleStatTpNm)) {
			info.append(",");
			info.append("empleStatTpNm=").append(empleStatTpNm);
		}
		if(!SBNUtils.isNull(empleDgrTp)) {
			info.append(",");
			info.append("empleDgrTp=").append(empleDgrTp);
		}
		if(!SBNUtils.isNull(empleDgrTpNm)) {
			info.append(",");
			info.append("empleDgrTpNm=").append(empleDgrTpNm);
		}
		if(!SBNUtils.isNull(useYn)) {
			info.append(",");
			info.append("useYn=").append(useYn);
		}
		if(!SBNUtils.isNull(empleNm)) {
			info.append(",");
			info.append("empleNm=").append(empleNm);
		}
		if(!SBNUtils.isNull(empleLognId)) {
			info.append(",");
			info.append("empleLognId=").append(empleLognId);
		}
		if(!SBNUtils.isNull(empleLognPwd)) {
			info.append(",");
			info.append("empleLognPwd=").append(empleLognPwd);
		}
		if(!SBNUtils.isNull(authCid)) {
			info.append(",");
			info.append("authCid=").append(authCid);
		}
		if(!SBNUtils.isNull(empleDeptNm)) {
			info.append(",");
			info.append("empleDeptNm=").append(empleDeptNm);
		}
		if(!SBNUtils.isNull(emplePstnNm)) {
			info.append(",");
			info.append("emplePstnNm=").append(emplePstnNm);
		}
		if(!SBNUtils.isNull(empleMblNo)) {
			info.append(",");
			info.append("empleMblNo=").append(empleMblNo);
		}
		if(!SBNUtils.isNull(initPwdChngYn)) {
			info.append(",");
			info.append("initPwdChngYn=").append(initPwdChngYn);
		}
		if(!SBNUtils.isNull(initPwdChngDt)) {
			info.append(",");
			info.append("initPwdChngDt=").append(initPwdChngDt);
		}
		if(!SBNUtils.isNull(lstLognDt)) {
			info.append(",");
			info.append("lstLognDt=").append(lstLognDt);
		}
		if(!SBNUtils.isNull(adtnDesc)) {
			info.append(",");
			info.append("adtnDesc=").append(adtnDesc);
		}
		if(!SBNUtils.isNull(rgstDt)) {
			info.append(",");
			info.append("rgstDt=").append(rgstDt);
		}
		if(!SBNUtils.isNull(rgstSeq)) {
			info.append(",");
			info.append("rgstSeq=").append(rgstSeq);
		}
		if(!SBNUtils.isNull(rgstNm)) {
			info.append(",");
			info.append("rgstNm=").append(rgstNm);
		}
		if(!SBNUtils.isNull(updtDt)) {
			info.append(",");
			info.append("updtDt=").append(updtDt);
		}
		if(!SBNUtils.isNull(updtSeq)) {
			info.append(",");
			info.append("updtSeq=").append(updtSeq);
		}
		if(!SBNUtils.isNull(updtNm)) {
			info.append(",");
			info.append("updtNm=").append(updtNm);
		}
		info.append(" }");
		return info.toString();
	}
}
