package com.qnpeople.rnd.pms.apis.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.apis.company.service.CompanyEmployeeMgmtService;
import com.qnpeople.rnd.pms.common.domain.executor.controller.QNPWebBaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.company.controller
 * @Filename		: CompanyEmployeeMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 업체 영역의 업체 근무자 정보 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	업체_업체 근무자 등록
 *  	업체_업체 근무자 변경
 *  	업체_업체 근무자 삭제
 *  [ 서비스 ]
 *  	업체_업체 근무자 목록 조회
 *  	업체_업체 근무자 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/corp/employee")
@Slf4j
public class CompanyEmployeeMgmtController extends QNPWebBaseController {

	/* 실질적인 업체 근무자 관리 작업을 수행하는 인터페이스 객체 */
	@Autowired
	private CompanyEmployeeMgmtService companyEmployeeMgmtService;
	
}
