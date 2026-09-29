package com.qnpeople.rnd.pms.apis.service.category.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategoryDto;
import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategorySC;
import com.qnpeople.rnd.pms.apis.service.category.model.SrvcCategoryTreeDto;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.category.mapper
 * @Filename		: SrvcCategoryMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.29.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 서비스 영역의 서비스 카테고리 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface SrvcCategoryMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	관리를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	서비스를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 사용 가능 서비스 카테고리에 대한 Tree 구조의 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.29
	 * @param 	srvcCategorySC		사용 가능 서비스 카테고리코드에 대한 Tree 구조의 목록 추출 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 사용 가능 서비스 카테고리에 대한 Tree 구조의 목록 객체
	 * @throws 	QNPWebException	사용 가능 서비스 카테고리 Tree 구조 목록 정보 추출 작업 수행 중 오류 발생 시 예외 처리 작업 수행 Exception
	 */
	public List<SrvcCategoryTreeDto> selectAvailableServiceCategoryTreeList(SrvcCategorySC srvcCategorySC) throws QNPWebException;
	
	/**
	 * 전달된 서비스 카테고리 추출 조건 정보에 대한 서비스 카테고리의 상세 정보를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.29
	 * @param 	srvcCategorySC
	 * @return		전달된 조건에 대한 서비스 카테고리에 대한 상세 정보 객체
	 * @throws 	QNPWebException	서비스 카테고리의 세부 정보 추출 작업 수행 중 오류 발생 시 예외 처리 작업 수행 Exception
	 */
	public SrvcCategoryDto selectServiceCategoryDetail(SrvcCategorySC srvcCategorySC) throws QNPWebException;
}
