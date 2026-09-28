package com.qnpeople.rnd.pms.apis.common.code.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.apis.common.code.model.CodeDto;
import com.qnpeople.rnd.pms.apis.common.code.model.CodeSC;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeDto;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeSC;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeTreeDto;
import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;
import com.qnpeople.rnd.pms.exceptions.QNPWebException;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.common.code.mapper
 * @Filename		: CodeMgmtMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.21.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 코드 그룹 및 코드 정보 관리를 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface CodeMgmtMapper extends QNPWebBaseMapper {

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	관리를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	서비스를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 사용 가능 공통 그룹 코드에 대한 Tree 구조의 목록 객체를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	groupCodeSC			사용 가능 공통 그룹 코드에 대한 Tree 구조의 목록 추출 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 사용 가능 공통 그룹 코드에 대한 Tree 구조의 목록 객체
	 * @throws 	QNPWebException	사용 가능 공통 코드 Tree 구조 목록 정보 추출 작업 수행 중 오류 발생 시 예외 처리 작업 수행 Exception
	 */
	public List<GroupCodeTreeDto> selectAvailableGroupCodeTreeList(GroupCodeSC groupCodeSC) throws QNPWebException;
	
	/**
	 * 전달된 공통 그룹 코드 추출 조건 정보에 대한 공통 그룹 코드의 상세 정보를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	groupCodeSC			세부 공통 그룹 코드에 대한 상세 정보 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 사용 가능 공통 그룹 코드에 대한 상세 정보 객체
	 * @throws 	QNPWebException	공통 코드의 세부 정보 추출 작업 수행 중 오류 발생 시 예외 처리 작업 수행 Exception
	 */
	public GroupCodeDto selectGroupCodeDetail(GroupCodeSC groupCodeSC) throws QNPWebException;
	
	/**
	 * 전달된 조건에 대한 사용 가능 공통 코드 목록 정보를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.22
	 * @param 	codeSC			사용 가능 공통 코드 목록 정보 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 사용 가능 공통 코드 목록 객체
	 * @throws 	QNPWebException	사용 가능 공통 코드 목록 추출 작업 수행 중 오류 발생 시 예외 처리 작업 수행 Exception
	 */
	public List<CodeDto> selectAvailableCodeList(CodeSC codeSC) throws QNPWebException;
	
	/**
	 * 전달된 조건에 대한 공통 코드 상세 정보를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.22
	 * @param 	codeSC			공통 코드 상세 정보 추출 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 조건에 대한 공통 코드 상세 정보 객체
	 * @throws 	QNPWebException	공통 코드 상세 정보 추출 작업 수행 중 오류 발생 시 예외 처리 작업 수행 Exception
	 */
	public CodeDto selectCodeDetail(CodeSC codeSC) throws QNPWebException;
}
