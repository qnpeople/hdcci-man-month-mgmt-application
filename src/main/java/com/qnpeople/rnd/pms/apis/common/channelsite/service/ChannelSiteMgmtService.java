package com.qnpeople.rnd.pms.apis.common.channelsite.service;

import java.util.List;

import com.qnpeople.rnd.pms.apis.common.channelsite.model.ChannelSiteDto;
import com.qnpeople.rnd.pms.apis.common.channelsite.model.ChannelSiteSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseService;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.common.web.exceptions.SBNWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.common.channelsite.service
 * @Filename		: ChannelSiteMgmtService.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.28.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 채널에 대한 사이트 매핑 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스
 * =================================================================================
 */
public interface ChannelSiteMgmtService extends QNPWebBaseService {

	/**
	 * 전달된 조건 정보에 대한 사용 가능 채널에 대한 사이트 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	channelSiteSC			사용 가능한 채널에 대한 사이트 목록 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 채널에 대한 사이트 목록 조회 조건 정보 대한 전체 사용 가능 채널별 사이트 목록 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<ChannelSiteDto> getChannelSiteList(ChannelSiteSC channelSiteSC) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보에 대한 사용 가능 채널에 대한 사이트의 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	channelSiteSC			사용 가능한 채널에 대한 사이트의 상세 정보 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 채널에 대한 사이트의 상세 정보 조회 조건 정보 대한 전체 사용 가능 채널별 사이트 상세 정보 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public ChannelSiteDto getChannelSiteDetail(ChannelSiteSC channelSiteSC) throws QNPWebException;
}
