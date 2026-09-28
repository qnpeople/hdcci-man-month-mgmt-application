package com.qnpeople.rnd.pms.apis.common.code.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qnpeople.rnd.pms.apis.common.code.mapper.CodeMgmtMapper;
import com.qnpeople.rnd.pms.apis.common.code.model.CodeDto;
import com.qnpeople.rnd.pms.apis.common.code.model.CodeSC;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeDto;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeSC;
import com.qnpeople.rnd.pms.apis.common.code.model.GroupCodeTreeDto;
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
 * @Package		: com.qnpeople.rnd.pms.apis.common.code.service
 * @Filename		: CodeMgmtServiceImpl.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.21.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 공통 영역의 코드 그룹 및 코드 정보 관리를 위한 실질적인 작업 수행 서비스 인터페이스 클래스를 구현한 구현 클래스
 * =================================================================================
 */
@Service
@Slf4j
public class CodeMgmtServiceImpl extends QNPWebBaseServiceImpl implements CodeMgmtService {

	/* 공통 코드 관련 클라이언트 요청 수행을 위한 SQL 쿼리 수행 매핑 인터페이스 객체 */
	@Autowired
	private CodeMgmtMapper codeMgmtMapper;
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	관리를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 조건 정보에 대한 전체 공통 코드 그룹 목록 객체를 추출하여 전달하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	groupCodeSC			공통 그룹 코드에 대한 작업 수행 조건 정보 전달 객체
	 * @return		전달된 공통 그룹 코드에 대한 전체 공통 그룹 코드 목록 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<GroupCodeTreeDto> getTotalGroupCodeTreeList(GroupCodeSC groupCodeSC) throws QNPWebException{
		return null;
	}
	
	/**
	 * 전달된 조건 정보에 대한 전체 공통 코드 목록 정보 추출을 위한 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.22
	 * @param 	codeSC					 사용 가능한 공통 코드 목록 조회 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 공통 그룹 코드에 대한 전체 코드 정보 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<CodeDto> getTotalCodeList(CodeSC codeSC) throws QNPWebException{
		return null;
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	서비스를 위한 모듈 정의 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 전달된 사용 가능 공통 코드 그룹 정보에 대한 Tree 구조의 목록 객체를 추출하여 전달하는 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	groupCodeSC			공통 그룹 코드에 대한 작업 수행 조건 정보 전달 객체
	 * @return		전달된 공통 그룹 코드에 대한 사용 가능 공통 그룹 코드 정보에 대한 Tree 구조 목록 객체
	 * @throws 	SBNWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<GroupCodeTreeDto> getAvailableGroupCodeTreeList(GroupCodeSC groupCodeSC) throws QNPWebException {		
		List<GroupCodeTreeDto> groupCodeList = new ArrayList<GroupCodeTreeDto>();
		try {
			groupCodeList = codeMgmtMapper.selectAvailableGroupCodeTreeList(groupCodeSC);
			return groupCodeList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
		
	/**
	 * 전달된 공통 코드 그룹 정보 전달 객체에 대한 공통 그룹 코드의 세부 정보 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.21
	 * @param 	groupCodeSC			공통 그룹 코드 상세 정보 추출 작업 수행 조건 정보 전달 객체
	 * @return		전달된 공통 그룹 코드에 대한 공통 그룹 코드 상세 정보 전달 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 중 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public GroupCodeDto getGroupCodeDetail(GroupCodeSC groupCodeSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		GroupCodeDto groupCodeDetail = null;
		try {
			if(SBNUtils.isNull(groupCodeSC)) {
				 reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "공통 코드 그룹 상세 조회 요청 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			groupCodeDetail = codeMgmtMapper.selectGroupCodeDetail(groupCodeSC);
			return groupCodeDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조건 정보에 대한 사용 가능 공통 코드 목록 정보 추출을 위한 작업을 수행하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.22
	 * @param 	codeSC					 사용 가능한 공통 코드 목록 조회 수행을 위한 조건 정보 전달 객체
	 * @return		전달된 공통 그룹 코드에 대한 사용 가능 코드 정보 목록 객체
	 * @throws 	QNPWebException	클라이언트의 요청 작업 수행 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public List<CodeDto> getAvilableCodeList(CodeSC codeSC) throws QNPWebException {
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		List<CodeDto> availableCodeList = new ArrayList<CodeDto>();
		try {
			if(SBNUtils.isNull(codeSC)) {
				 reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "사용 가능 공통 코드 목록 조회 요청 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			availableCodeList = codeMgmtMapper.selectAvailableCodeList(codeSC);
			return availableCodeList;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
	
	/**
	 * 전달된 조건 정보에 대한 공통 코드의 상세 정보를 추출하여 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.22
	 * @param 	codeSC					공통 코드 상세 정보 조회를 위한 조건 정보 전달 객체
	 * @return		전달된 공통 코드 상세 정보 조회 조건에 대한 공통 코드 상세 정보 객체
	 * @throws	QNPWebException	클라이언트의 요청 작업 수행 오류 발생 시 예외 처리 작업 Exception 객체
	 */
	public CodeDto getCodeDetail(CodeSC codeSC) throws QNPWebException{
		QNPReasonInterface reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
		String errorCode = reason.getReasonCode();
		String errorMessage = "";
		CodeDto codeDetail = null;
		try {
			if(SBNUtils.isNull(codeSC)) {
				 reason = QNPReasonCode.NONE_REQUEST_CONDITION_ERROR;
				errorCode = reason.getReasonCode();
				errorMessage = "사용 가능 공통 코드 상세 조회 요청 조건 정보 미 전달 오류.";
				throw new QNPWebException(reason, errorCode, errorMessage);
			}
			codeDetail = codeMgmtMapper.selectCodeDetail(codeSC);
			return codeDetail;
		} catch(Exception exception) {
			throw new QNPWebException(exception);
		}
	}
}
