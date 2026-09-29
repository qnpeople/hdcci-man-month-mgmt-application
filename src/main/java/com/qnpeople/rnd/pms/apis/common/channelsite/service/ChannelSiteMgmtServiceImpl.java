package com.qnpeople.rnd.pms.apis.common.channelsite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.common.channelsite.mapper.ChannelSiteMgmtMapper;
import com.qnpeople.rnd.pms.apis.common.channelsite.model.ChannelSiteDto;
import com.qnpeople.rnd.pms.apis.common.channelsite.model.ChannelSiteSC;
import com.qnpeople.rnd.pms.common.domain.executor.service.QNPWebBaseServiceImpl;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

import kr.co.sbn.platformhub.framework.core.common.web.exceptions.SBNWebException;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.common.channelsite.service
 * @Filename		: ChannelSiteMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.28.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 채널에 대한 사이트 매핑 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class ChannelSiteMgmtServiceImpl extends QNPWebBaseServiceImpl implements ChannelSiteMgmtService {

	/* 채널별 매핑 사이트 정보 관리 요청 수행을 위한 SQL 쿼리 수행 매핑 인터페이스 객체 */
	@Autowired
	private ChannelSiteMgmtMapper channelSiteMgmtMapper;
	
	/**
	 * 전달된 조건 정보에 대한 사용 가능 채널에 대한 사이트 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	channelSiteSC			사용 가능한 채널에 대한 사이트 목록 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 채널에 대한 사이트 목록 조회 조건 정보 대한 전체 사용 가능 채널별 사이트 목록 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<ChannelSiteDto> getChannelSiteList(ChannelSiteSC channelSiteSC) throws QNPWebException {
		List<ChannelSiteDto> channelSiteList = new ArrayList<ChannelSiteDto>();
		try {
			channelSiteList = channelSiteMgmtMapper.selectChannelSiteList(channelSiteSC);
			return channelSiteList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조건 정보에 대한 사용 가능 채널에 대한 사이트의 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.28
	 * @param 	channelSiteSC			사용 가능한 채널에 대한 사이트의 상세 정보 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 채널에 대한 사이트의 상세 정보 조회 조건 정보 대한 전체 사용 가능 채널별 사이트 상세 정보 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public ChannelSiteDto getChannelSiteDetail(ChannelSiteSC channelSiteSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		ChannelSiteDto channelSiteDetail = null;
		try {
			if(SBNUtils.isNull(channelSiteSC.getChnlSiteSeq()) &&
				(SBNUtils.isNull(channelSiteSC.getChnlSeq()) && (SBNUtils.isNull(channelSiteSC.getSiteSeq())	)) &&
				 (SBNUtils.isNull(channelSiteSC.getChnlCd()) && (SBNUtils.isNull(channelSiteSC.getSiteCd())))) {
				reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "공통 채널별 사이트 상세 조회 요청 키 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			channelSiteDetail = channelSiteMgmtMapper.selectChannelSiteDetail(channelSiteSC);
			return channelSiteDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
