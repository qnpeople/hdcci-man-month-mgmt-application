package com.qnpeople.rnd.pms.common.domain.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.sbn.platformhub.framework.core.common.data.entities.SBNResponseData;
import kr.co.sbn.platformhub.framework.core.utils.SBNDateUtils;
import kr.co.sbn.platformhub.framework.core.utils.SBNJsonUtils;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.EqualsAndHashCode;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.domain.response
 * @Filename		: QNPResponseData.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.01.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션의 클라이언트 요청에 대한 정상 수행 결과 응답 데이터를 관리하고 전달하는 작업을 수행하는 데이터 속성 클래스
 * =================================================================================
 */
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class QNPResponseData extends SBNResponseData {

	/**
	 * 웹 클라이언트의 수행 요청에 대한 어플리케이션 정상 수행 결과에 대한 세부 정보를 관리하는 데이터 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 */
	public QNPResponseData() {
		super();
	}
	
	/**
	 * 웹 클라이언트의 수행 요청에 대한 어플리케이션 정상 수행 결과에 대한 세부 정보를 관리하는 데이터 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	data		웹 클라이언트 요청에 대한 프레임워크 어플리케이션 수행 정상 단일 항목 객체
	 */
	public QNPResponseData(Object data) {
		super(data);
	}
	
	/**
	 * 웹 클라이언트의 수행 요청에 대한 어플리케이션 정상 수행 결과에 대한 세부 정보를 관리하는 데이터 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	list			웹 클라이언트 요청에 대한 프레임워크 어플리케이션 수행 정상 결과 목록 객체
	 */
	public QNPResponseData(List<?> list) {
		super(list);
	}
	
	/**
	 * 웹 클라이언트의 수행 요청에 대한 어플리케이션 정상 수행 결과에 대한 세부 정보를 관리하는 데이터 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @param 	data		웹 클라이언트 요청에 대한 프레임워크 어플리케이션 수행 정상 단일 항목 객체
	 * @param 	list			웹 클라이언트 요청에 대한 프레임워크 어플리케이션 수행 정상 결과 목록 객체
	 */
	public QNPResponseData(Object data, List<?> list) {
		super(data, list);
	}
	
	/**
	 * 웹 클라이언트 요청에 대한 프레임워크 내부 정상 수행 결과 정보 객체의 세부 정보를 문자열로 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @return		웹 클라이언트 요청에 대한 프레임워크 내부 정상 수행 결과 정보 객체의 세부 정보에 대한 문자열 정보
	 */
	@JsonIgnore
	public String toStringInfo() {
		StringBuilder info = new StringBuilder();
		info.append(className).append("{ ");
		info.append("id=").append(id);
		if(!SBNUtils.isNull(getCreateDateTime())) {
			info.append(", ");
			info.append("createTime=").append(SBNDateUtils.dateToString(getCreateDateTime()));
		}
		if(!SBNUtils.isNull(data)) {
			info.append(", ");
			info.append("data=").append(data);
		}
		if(!SBNUtils.isNull(list)) {
			info.append(", ");
			if(SBNUtils.isNull(list)) {
				info.append("list=[]");
			} else {
				try {
					info.append("list=").append(SBNJsonUtils.objectToJsonAsString(super.list));
				} catch(JsonProcessingException jsonProcessingException) {
					info.append("list=[]");
				}
			}
		}
		info.append(" }");
		return info.toString();
	}
}
