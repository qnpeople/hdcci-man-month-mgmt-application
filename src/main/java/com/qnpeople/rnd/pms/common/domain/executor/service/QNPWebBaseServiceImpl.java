package com.qnpeople.rnd.pms.common.domain.executor.service;

import kr.co.sbn.platformhub.framework.core.common.web.modules.service.SBNWebBaseServiceAdaptor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.executor.service
 * @Filename		: QNPWebBaseServiceImpl.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트로부터 전달된 요청에 대한 실질적인 작업을 수행하는 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class QNPWebBaseServiceImpl extends SBNWebBaseServiceAdaptor implements QNPWebBaseService {

	/**
	 * 웹 어플리케이션의 클라이언트 요청에 대한 실질적인 작업을 수행하는 서비스 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 */
	public QNPWebBaseServiceImpl() {
		super();
	}
}
