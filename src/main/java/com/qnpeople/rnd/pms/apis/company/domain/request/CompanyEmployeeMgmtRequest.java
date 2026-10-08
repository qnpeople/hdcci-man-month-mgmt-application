package com.qnpeople.rnd.pms.apis.company.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.apis.company.model.EmployeeDto;
import com.qnpeople.rnd.pms.common.domain.request.QNPWebClientRequestWrapper;

import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
@Data
public class CompanyEmployeeMgmtRequest extends QNPWebClientRequestWrapper {

	/* 업체 식별자 */
	private Long compSeq;
	/* 업체 코드 */
	private String compCd;
	/* 근무자 유형(공통코드: CMP_EMP_INFO_001) 01.정규직, 02:계약직, 03:위탁직, 04:프리랜서, 99:기타 */
	private String empleTp;
	/* 근무자 상태 유형(공통코드: CMP_EMP_STAT_001) 01.정상, 02:휴직(육아), 03:병가, 04:퇴사, 05:계약종료, 99:기타 */
	private String empleStatTp;
	/* 근무자 등급 유형(공통코드: CMP_EMP_DGR_001) 01.초급, 02:중급, 03:고급, 04:특급, 05:해당없음, 99:기타 */
	private String empleDgrTp;
	/* 최초 근무자 비밀번호 변경 여부(N: 미 변경(기본값), Y: 변경) */
	private String initPwdChngYn;
	/* 근무자 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 근무자 명 */
	private String empleNm;
	
	/* 근무자 식별자 */
	private Long empleSeq;
	/* 근무자 로그인 ID*/
	private String empleLognId;
	/* 근무자 이전 로그인 비밀번호 */
	private String oldEmpleLognPwd;
	/* 근무자 변경 로그인 비밀번호 */
	private String newEmpleLognPwd;
	
	
	/* 근무자 등록/변경 정보 전달 객체 */
	private EmployeeDto employee;
	
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("txId= ").append(txId);
		if(!SBNUtils.isNull(compSeq)) {
			info.append(", ");
			info.append("compSeq=").append(compSeq);
		}
		if(!SBNUtils.isNull(compCd)) {
			info.append(", ");
			info.append("compCd=").append(compCd);
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
		if(!SBNUtils.isNull(initPwdChngYn)) {
			info.append(", ");
			info.append("initPwdChngYn=").append(initPwdChngYn);
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
		if(!SBNUtils.isNull(oldEmpleLognPwd)) {
			info.append(", ");
			info.append("oldEmpleLognPwd=").append(oldEmpleLognPwd);
		}
		if(!SBNUtils.isNull(newEmpleLognPwd)) {
			info.append(", ");
			info.append("newEmpleLognPwd=").append(newEmpleLognPwd);
		}
		if(!SBNUtils.isNull(employee)) {
			info.append(", ");
			info.append("employee=").append(employee.toStringInfo());
		}
		info.append(" }");
		return info.toString();
	}
}
