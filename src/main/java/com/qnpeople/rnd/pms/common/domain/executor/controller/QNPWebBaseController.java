package com.qnpeople.rnd.pms.common.domain.executor.controller;

import kr.co.sbn.platformhub.framework.core.common.web.modules.controller.SBNWebBaseController;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.executor.controller
 * @Filename		: QNPWebBaseController.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트로부터 전달된 요청에 대한 수행 후 수행 결과 응답을 전달하는 작업을 수행하는 컨트롤러 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class QNPWebBaseController extends SBNWebBaseController {
	
	/**
	 * 클라이언트의 요청에 대한 작업 수행 후 결과 응답을 전달하는 작업을 수행하는 컨트롤러 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 */
	public QNPWebBaseController() {
		super();
	}
}
