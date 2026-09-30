package com.qnpeople.rnd.pms.apis.service.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.service.service.model.ServiceDto;
import com.qnpeople.rnd.pms.apis.service.service.model.ServiceSC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.service.service.mapper
 * @Filename		: ServiceMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.30.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 서비스 영역의 서비스 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
 @Mapper
public interface ServiceMgmtMapper extends QNPWebBaseMapper {

	/**
	 * 전달된 조건 정보에 대한 서비스 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				서비스 목록 정보 객체 추출을 위한 조건 정보 전달 객체
	 * @return		전달된 조건 정보에 대한 서비스 목록 객체
	 * @throws 	QNPWebException	서비스 목록 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public List<ServiceDto> selectServiceList(ServiceSC serviceSC) throws QNPWebException;
	
	/**
	 * 전달된 조건 정보 객체에 대한 서비스 상세 정보 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				서비스 상세 정보 객체 추출을 위한 조건 정보 전달 객체
	 * @return		전달된 조건 정보 객체에 대한 서비스 상세 정보 객체
	 * @throws	 QNPWebException	서비스 상세 정보 추출 작업 중 오류 발생 시 예외 처리 Exception
	 */
	public ServiceDto selectServiceDetail(ServiceSC serviceSC) throws QNPWebException;
	
	/**
	 * 전달된 신규 등록 대상 서비스 정보의 서비스 코드 정보 중복 여부 체크 조건 전달 정보에 대한 중복 여부 체크 작업 수행 후 수행 결과를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				신규 등록 대상 서비스 정보의 서비스 코드 정보 중복 여부 체크 조건 정보 전달 객체
	 * @return		전달된 신규 등록 대상 서비스 정보의 서비스 코드 정보 중복 여부 체크 조건 전달 정보에 대한 중복 여부 체크 작업 결과 Flag. 중복 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	신규 등록 대상 서비스 정보의 서비스 코드 정보 중복 여부 체크 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Boolean selectServiceCdDuplicationFlag(ServiceSC serviceSC) throws QNPWebException;
	
	/**
	 * 전달된 신규 등록 대상 서비스 정보 등록 작업 수행 후 수행 결과를 전달하는 메소드
	 *
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceDto				신규 등록 대상 서비스 정보 전달 객체
	 * @return		전달된 신규 등록 대상 서비스 정보 등록 작업 수행 결과 Flag. 정상 수행 시 true 를, 그렇지 않은 경우 false 를 전달
	 * @throws 	QNPWebException	신규 서비스 정보 등록 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer insertNewService(ServiceDto serviceDto) throws QNPWebException;
	
	/**
	 * 전달된 변경 대상 서비스 정보에 대한 변경 작업 수행 후 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceDto				변경 대상 서비스 정보 전달 객체
	 * @return		전달된 변경 대상 서비스 정보에 대한 변경 작업 수행 결과 갯수, 정상 수행 시 등록 수행 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	서비스 정보 변경 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer updateService(ServiceDto serviceDto) throws QNPWebException;
	
	/**
	 * 전달된 삭제 대상 서비스 정보의 키 조건 전달 정보에 대한 서비스 삭제 수행 후 수행 결과 갯수를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.30
	 * @param 	serviceSC				삭제 대상 서비스 정보의 키 조건 전달 객체
	 * @return		전달된 삭제 대상 서비스 정보의 키 조건 전달 정보에 대한 서비스 삭제 결과 갯수, 정상 삭제 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	서비스 정보 삭제 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteService(ServiceSC serviceSC) throws QNPWebException;
}
