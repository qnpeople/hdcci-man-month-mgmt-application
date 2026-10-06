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
public class EmployeeSC extends QNPWebBaseModel {

	/* 업체 식별자 */
	private Long compSeq;
	/* 근무자 식별자 */
	private Long empleSeq;
	/* 근무자 유형(공통코드: CMP_EMP_INFO_001) 01.정규직, 02:계약직, 03:위탁직, 04:프리랜서, 99:기타 */
	private String empleTp;
	/* 근무자 상태 유형(공통코드: CMP_EMP_STAT_001) 01.정상, 02:휴직(육아), 03:병가, 04:퇴사, 05:계약종료, 99:기타 */
	private String empleStatTp;
	/* 근무자 등급 유형(공통코드: CMP_EMP_DGR_001) 01.초급, 02:중급, 03:고급, 04:특급, 05:해당없음, 99:기타 */
	private String empleDgrTp;
	/* 근무자 사용 여부(Y: 사용(기본), N: 미사용) */
	private String useYn;
	/* 근무자 명 */
	private String empleNm;	
	/* 근무자 로그인 ID */
	private String empleLognId;
	
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
		if(!SBNUtils.isNull(empleStatTp)) {
			info.append(",");
			info.append("empleStatTp=").append(empleStatTp);
		}
		if(!SBNUtils.isNull(empleDgrTp)) {
			info.append(",");
			info.append("empleDgrTp=").append(empleDgrTp);
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
		info.append(" }");
		return info.toString();
	}
}
