package com.qnpeople.rnd.pms.apis.company.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.company.model.CompanySC;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

@Mapper
public interface CompanyEmployeeMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////
	//	관리
	/////////////////////////////////////////////////////////////////		
	/**
	 * 전달된 업체 관리 조건 정보에 대한 해당 업체의 전체 근무자 정보 삭제 작업 후, 삭제 수행 결과 갯수를 전달하는 메소드
	 *
	 * @author		BeomSeok.Seo
	 * @date 		2026.10.06
	 * @param 	companySC			업체 관리 조건 정보 객체
	 * @return		전달된 업체 관리 조건 정보 객체에 대한 업체의 전체 근무자 삭제 작업 수행 결과 갯수. 정상 수행 시 삭제 갯수, 그렇지 않은 경우 0 전달
	 * @throws 	QNPWebException	업체 근무자 관리를 위한 DB 작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	public Integer deleteAllCompanyEmployees(CompanySC companySC) throws QNPWebException;
	
	
	/////////////////////////////////////////////////////////////////
	//	서비스
	/////////////////////////////////////////////////////////////////		



}
