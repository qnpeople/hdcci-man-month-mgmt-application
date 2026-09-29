package com.qnpeople.rnd.pms.common.domain.executor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kr.co.sbn.platformhub.framework.core.common.web.common.data.model.SBNWebBaseModel;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.EqualsAndHashCode;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.executor.model
 * @Filename		: QNPWebBaseModel.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트 요청 수행을 위한 데이터베이스 Entity 정보 관리 및 전달 작업 수행을 위한 최상위 데이터 속성 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class QNPWebBaseModel extends SBNWebBaseModel {

	/** 페이지별 노출 데이터 개수 정보 */
	protected Integer rowDataCount;
	
	/** 목록 조회 요청 페이지 번호 정보 */
	protected Integer pageNo;
	
	/** 페이지별 추출 데이터의 시작 인덱스 정보 */
	protected Integer startRowIndex;
	/** 페이지별 추출 데이터의 종료 인덱스 정보 */
	protected Integer endRowIndex;
	
	/**
	 * 어플리케이션의 클라이언트 요청 수행 정보 관리 및 전달 작업을 수행하는 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 */
	public QNPWebBaseModel() {
		this(null, null);
	}
	
	/**
	 * 어플리케이션의 클라이언트 요청 수행 정보 관리 및 전달 작업을 수행하는 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.26
	 */
	public QNPWebBaseModel(Integer rowDataCount, Integer pageNo) {
		super();
		if(!SBNUtils.isNull(rowDataCount) && (rowDataCount > 0)) {
			this.rowDataCount = rowDataCount;
		}
		if(!SBNUtils.isNull(pageNo) && (pageNo > 0)) {
			this.pageNo = pageNo;
		}
	}
	
	/**
	 * 페이지별 노출 데이터 개수 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	rowDataCount		페이지별 노출 데이터 개수 정보
	 */
	@JsonIgnore
	public void setRowDataCount(Integer rowDataCount) {
		if(!SBNUtils.isNull(rowDataCount) && (rowDataCount > 0)) {
			this.rowDataCount = rowDataCount;
		}
	}
	
	/**
	 * 페이지별 노출 데이터 개수 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		페이지별 노출 데이터 개수 정보
	 */
	@JsonIgnore
	public Integer getRowDataCount() {
		return rowDataCount;
	}
	
	/**
	 * 목록 조회 요청 페이지 번호 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	pageNo		목록 조회 요청 페이지 번호 정보
	 */
	@JsonIgnore
	public void setPageNo(Integer pageNo) {
		if(!SBNUtils.isNull(pageNo) && (pageNo > 0)) {
			this.pageNo = pageNo;
		}
	}
	
	/**
	 * 목록 조회 요청 페이지 번호 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		목록 조회 요청 페이지 번호 정보
	 */
	@JsonIgnore
	public Integer getPageNo() {
		return pageNo;
	}
	
	/**
	 * 페이지별 추출 데이터의 시작 인덱스 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	startRowIndex		페이지별 추출 데이터의 시작 인덱스 정보
	 */
	@JsonIgnore
	public void setStartRowIndex(Integer startRowIndex) {
		if(!SBNUtils.isNull(startRowIndex) && (startRowIndex > 0)) {
			this.startRowIndex = startRowIndex;
		}
	}
	
	/**
	 * 페이지별 추출 데이터의 시작 인덱스 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		페이지별 추출 데이터의 시작 인덱스 정보
	 */
	@JsonIgnore
	public Integer getStartRowIndex() {
		return startRowIndex;
	}
	
	/**
	 * 페이지별 추출 데이터의 종료 인덱스 정보를 설정하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	startRowIndex		페이지별 추출 데이터의 종료 인덱스 정보
	 */
	@JsonIgnore
	public void setRndRowIndex(Integer endRowIndex) {
		if(!SBNUtils.isNull(endRowIndex) && (endRowIndex > 0)) {
			this.endRowIndex = endRowIndex;
		}
	}
	
	/**
	 * 페이지별 추출 데이터의 종료 인덱스 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return 	페이지별 추출 데이터의 종료 인덱스 정보
	 */
	@JsonIgnore
	public Integer getEndRowIndex() {
		return endRowIndex;
	}
}
