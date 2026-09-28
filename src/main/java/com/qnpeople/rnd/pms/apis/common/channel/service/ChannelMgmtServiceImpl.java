package com.qnpeople.rnd.pms.apis.common.channel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.common.channel.mapper.ChannelMgmtMapper;
import com.qnpeople.rnd.pms.apis.common.channel.model.ChannelDto;
import com.qnpeople.rnd.pms.apis.common.channel.model.ChannelSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.common.web.exceptions.SBNWebException;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.common.channel.service
 * @Filename		: ChannelMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.23.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 채널 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class ChannelMgmtServiceImpl extends QNPWebBaseServiceImpl implements ChannelMgmtService {

	/* 채널 관련 클라이언트 요청 수행을 위한 SQL 쿼리 수행 매핑 인터페이스 객체 */
	@Autowired
	private ChannelMgmtMapper channelMgmtMapper;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	관리를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	서비스를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 조건 정보에 대한 사용 가능 채널 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.23
	 * @param 	groupCodeSC			사용 가능한 채널 목록 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 채널 목록 조회 조건 정보 대한 전체 사용 가능 채널 목록 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<ChannelDto> getAvailableChannelList(ChannelSC channelSC) throws QNPWebException {
		List<ChannelDto> availableChannelList = new ArrayList<ChannelDto>();
		try {
			availableChannelList = channelMgmtMapper.selectAvailableChannelList(channelSC);
			return availableChannelList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조건 정보에 대한 사용 가능 채널 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.23
	 * @param 	groupCodeSC			채널 상세 정보 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 채널 상세 정보 조회 조건 정보 대한 채널 상세 정보 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public ChannelDto getChannelDetail(ChannelSC channelSC) throws QNPWebException {
		ChannelDto channelDetail = null;
		try {			
			channelDetail = channelMgmtMapper.selectChannelDetail(channelSC);
			return channelDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
