package com.qnpeople.rnd.pms.apis.service.siteservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceDto;
import com.qnpeople.rnd.pms.apis.service.siteservice.model.SiteServiceSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.siteservice.mapper
 * @Filename		: SiteServiceMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.31.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 서비스 영역의 사이트 서비스 매핑 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface SiteServiceMgmtMapper extends QNPWebBaseMapper {

	/**
	 * 전달된 조건 정보에 대한 사이트별 서비스 매핑 목록 객체를 DB로부터 추출하여 전달하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.31
	 * @param 	siteServiceSC			사이트별 서비스 매핑 목록 추출 작업 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 사이트별 서비스 매핑 목록 객체
	 * @throws 	QNPWebException	사이트 서비스 매핑 목록 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public List<SiteServiceDto> selectSiteServiceMappingList(SiteServiceSC siteServiceSC) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보에 대한 사이트별 서비스 매핑 상세 정보 객체를 DB로부터 추출하여 전달하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.31
	 * @param 	siteServiceSC			사이트별 서비스 매핑 상세 추출 작업 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 사이트별 서비스 매핑 상세 정보 객체
	 * @throws 	QBPWebException	사이트 서비스 매핑 상세 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public SiteServiceDto selectSiteServiceDetail(SiteServiceSC siteServiceSC) throws QNPWebException;
}
