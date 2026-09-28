package com.qnpeople.rnd.pms.common.domain.executor.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kr.co.sbn.platformhub.framework.core.common.web.common.data.model.SBNWebBaseModel;
import lombok.EqualsAndHashCode;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.executor.model
 * @Filename		: QNPWebBaseModel.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트 요청 수행을 위한 데이터베이스 Entity 정보 관리 및 전달 작업 수행을 위한 최상위 데이터 속성 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class QNPWebBaseModel extends SBNWebBaseModel {

	/**
	 * 어플리케이션의 클라이언트 요청 수행 정보 관리 및 전달 작업을 수행하는 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 */
	public QNPWebBaseModel() {
		super();
	}
}
