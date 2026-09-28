package com.qnpeople.rnd.pms.apis.common.channel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qnpeople.rnd.pms.common.domain.executor.controller.QNPWebBaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.common.channel.controller
 * @Filename		: ChannelMgmtController.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.23.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 시스템 채널 관리를 위한 Restful API 방식 클라이언트의 요청을 처리 결과를 전달하는 컨트롤러 클래스 
 *  [ 관리 ]
 *  	
 *  
 *  [ 서비스 ]
 *  	01.채널_사용 가능 채널 목록 조회
 *  	02.채널_채널 상세 조회
 * =================================================================================
 */
@RestController
@RequestMapping("/hdcci/api/common/channel")
@Slf4j
public class ChannelMgmtController extends QNPWebBaseController {

}
