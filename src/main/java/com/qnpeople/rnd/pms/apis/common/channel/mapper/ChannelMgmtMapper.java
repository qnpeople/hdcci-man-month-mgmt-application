package com.qnpeople.rnd.pms.apis.common.channel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.common.channel.model.ChannelDto;
import com.qnpeople.rnd.pms.apis.common.channel.model.ChannelSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.common.web.exceptions.SBNWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.common.channel.mapper
 * @Filename		: ChannelMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.23.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 채널 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface ChannelMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	관리를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	서비스를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 조건 정보에 대한 사용 가능 채널 목록 정보를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.23
	 * @param 	groupCodeSC			사용 가능한 채널 목록 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 채널 목록 조회 조건 정보 대한 전체 사용 가능 채널 목록 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<ChannelDto> selectChannelList(ChannelSC channelSC) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보에 대한 채널 상세 정보를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.23
	 * @param 	groupCodeSC			채널 상세 정보 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 채널 상세 정보 조회 조건 정보 대한 채널 상세 정보 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public ChannelDto selectChannelDetail(ChannelSC channelSC) throws QNPWebException;
}
