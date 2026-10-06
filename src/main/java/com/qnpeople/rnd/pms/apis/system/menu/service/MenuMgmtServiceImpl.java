package com.qnpeople.rnd.pms.apis.system.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.system.menu.mapper.MenuMgmtMapper;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.system.menu.service
 * @Filename		: MenuMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.04.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 시스템 영역의 메뉴 정보 관리를 위한 실질적인 작업을 수행하기 위한 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class MenuMgmtServiceImpl extends QNPWebBaseServiceImpl implements MenuMgmtService {

	/* 시스템의 메뉴 관리를 위한 DB 작업 수행 SQL 쿼리 매핑 인터페이스 객체 */
	@Autowired
	private MenuMgmtMapper menuMgmtMapper;
	
	//////////////////////////////////////////////////////////////////////////////
	//	시스템 메뉴 관리 모듈 정의
	//////////////////////////////////////////////////////////////////////////////
	
	
	//////////////////////////////////////////////////////////////////////////////
	//	시스템 메뉴 서비스 모듈 정의
	//////////////////////////////////////////////////////////////////////////////
	
}
