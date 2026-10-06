package com.qnpeople.rnd.pms.common.reason;

import org.springframework.http.HttpStatus;

import kr.co.sbn.platformhub.framework.core.constants.SBNConstant;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.reason
 * @Filename		: QNPReasonCode.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 어플리케이션 내부 모듈 수행 중 오류 발생 시 예외 처리 수행을 위한 사유 정의 인터페이스 클래스를 구현한 열거형 클래스
 * =================================================================================
 */
public enum QNPReasonCode implements QNPReasonInterface {
	
	SUCCESS(HttpStatus.OK, SBNConstant.SUCC_CODE , SBNConstant.SUCC_MSG, "수행 성공 사유 코드.", Boolean.TRUE),	
	//	10000 ~ 10049		(공통) 프레임워크 내부 제공 기본 오류 사유 코드
	HOST_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "10000", "서버 미 존재 오류", "서버 미 존재 오류", Boolean.TRUE),
	UNAVAILABLE_ACCESS_ERROR(HttpStatus.UNAUTHORIZED, "10001", "서버 접근 불가 오류", "서버 접근 불가 오류", Boolean.TRUE),
	SERVICE_UNAVAILABLE_ERROR(HttpStatus.NOT_IMPLEMENTED, "10002", "서비스 미 구현 오류", "서비스 미 구현 오류", Boolean.TRUE),
	METHOD_NOT_SUPPORT_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "10003","요청 메소드 미 지원 오류","요청 메소드 미 지원 오류", Boolean.TRUE),
	METHOD_ARGUMEMT_INVALID_ERROR(HttpStatus.BAD_REQUEST, "10004", "파라미터 인자 유효성 오류", "파라미터 인자 유효성 오류", Boolean.TRUE),
	REQUEST_PARAM_ERROR(HttpStatus.BAD_REQUEST, "10005", "요청 파라미터 오류", "요청 파라미터 오류", Boolean.TRUE),
	INVALID_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "10006", "유효하지 않은 요청 오류", "유효하지 않은 요청 오류", Boolean.TRUE),
	PARAMETER_BIND_ERROR(HttpStatus.BAD_REQUEST, "10007", "파라미터 유효성 오류", "파라미터 유효성 오류", Boolean.TRUE),
	PARAMETER_TYPE_BIND_ERROR(HttpStatus.BAD_REQUEST, "10008", "파라미터 유형 유효성 오류", "파라미터 유형 유효성 오류", Boolean.TRUE),
	SERVICE_PREPARING_ERROR(HttpStatus.NOT_IMPLEMENTED, "10009", "서비스 준비 중", "서비스 준비 중", Boolean.TRUE),
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	Common Error						10100 ~ 10149
	FRAMEWORK_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10100", "프레임워크 공통 수행 오류", "프레임워크 공통 수행 오류", Boolean.TRUE),
	FRAMEWORK_NO_DATA_ERROR(HttpStatus.NO_CONTENT, "10101", "수행 결과 미 존재 오류", "요청 수행 결과 미 존재 오류", Boolean.TRUE),
	
	//	Web Application Error 			10150 ~ 10199
	FRAMEWORK_WEB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10150", "프레임워크 웹 수행 공통 수행 오류", "프레임워크 웹 수행 공통 수행 오류", Boolean.TRUE),
	FRAMEWORK_WEB_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10151", "웹 어플리케이션 공통 수행 오류", "웹 어플리케이션 공통 수행 오류", Boolean.TRUE),
	FRAMEWORK_DATABASE_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10152", "어플리케이션 데이터베이스 수행 오류", "어플리케이션 데이터베이스 수행 오류", Boolean.TRUE),
	FRAMEWORK_AUTH_COMMON_ERROR(HttpStatus.UNAUTHORIZED, "10153", "웹 어플리케이션 인증 수행 오류", "웹 어플리케이션 인증 수행 오류", Boolean.TRUE),
	FRAMEWORK_WEB_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10154", "웹 어플리케이션 클라이언트 요청 수행 오류", "웹 어플리케이션 클라이언트 요청  수행 오류", Boolean.TRUE),
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	Common > Feign Error			10200 ~ 10299
	FRAMEWORK_FEIGN_ERROR(HttpStatus.UNAUTHORIZED, "10200", "프레임워크 Feign 수행 오류.", "프레임워크 Feign 수행 오류.", Boolean.TRUE),
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	Common > Security Error		10300 ~ 10399
	FRAMEWORK_SECURITY_ERROR(HttpStatus.UNAUTHORIZED, "10300", "프레임워크 보안 공통 수행 오류.", "프레임워크 보안 공통 수행 오류.", Boolean.TRUE),
	FRAMEWORK_SECURITY_AUTH_ERROR(HttpStatus.UNAUTHORIZED, "10301", "로그인 인증 오류.", "로그인 인증 오류.", Boolean.TRUE),
	//	Common > Security Error > jasypt  10302 ~ 10319
	JASYPT_COMMON_ERROR(HttpStatus.UNAUTHORIZED, "10302", "어플리케이션 프로퍼티 암호화 공통 수행 오류.", "어플리케이션 프로퍼티 암호화 공통 수행 오류.", Boolean.TRUE),
	JASYPT_CYPHER_ERROR(HttpStatus.UNAUTHORIZED, "10303", "어플리케이션 설정 정보 암호화/복호화 수행 오류.", "어플리케이션 설정 정보 암호화/복호화 수행 오류.", Boolean.TRUE),
	JASYPT_CYPHER_ENCRYPT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10304", "어플리케이션 설정 정보 암호화 수행 오류.", "어플리케이션 설정 정보 암호화 수행 오류.", Boolean.TRUE),
	JASYPT_CYPHER_DECRYPT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10305", "어플리케이션 설정 정보 복호화 수행 오류.", "어플리케이션 설정 정보 복호화 수행 오류.", Boolean.TRUE),
	
	//	Common > Security Error > jwt		10320 ~ 10339
	JWT_COMMON_ERROR(HttpStatus.UNAUTHORIZED, "10320", "프레임워크 JWT 공통 수행 오류.", "프레임워크 JWT 공통 수행 오류.", Boolean.TRUE),
	JWT_VERIFICATION_ERROR(HttpStatus.UNAUTHORIZED, "10321", "프레임워크 JWT 검증 오류.", "프레임워크 JWT 검증 오류.", Boolean.TRUE),
	JWT_TOKEN_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10322", "JWT 토큰 공통 수행 오류", "JWT 토큰 공통 수행 오류", Boolean.TRUE),
	JWT_TOKNE_REQ_PARAM_ERROR(HttpStatus.BAD_REQUEST , "10323", "JWT 토큰 요청 파라미터 수행 오류", "JWT 토큰 인증 수행 오류", Boolean.TRUE),
	JWT_TOKNE_FORMAT_ERROR(HttpStatus.BAD_REQUEST , "10324", "JWT 토큰 포맷 오류", "JWT 토큰 포맷 오류", Boolean.TRUE),
	JWT_TOKNE_AUTH_ERROR(HttpStatus.UNAUTHORIZED , "10325", "JWT 토큰 인증 수행 오류", "JWT 토큰 인증 수행 오류", Boolean.TRUE),
	JWT_TOKNE_ALGORITHM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10326", "JWT 토큰 알고리즘 불일치 오류", "JWT 토큰 알고리즘 불일치 오류", Boolean.TRUE),
	JWT_TOKNE_CYPHER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10327", "JWT 토큰 암호화 오류", "JWT 토큰 암호화 오류", Boolean.TRUE),
	JWT_TOKEN_HEADER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10328", "JWT 토큰 헤더 수행 오류", "JWT 토큰 헤더 수행 오류", Boolean.TRUE),
	JWT_TOKNE_ISSUE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10329", "JWT 토큰 발급 수행 오류", "JWT 토큰 발급 수행 오류", Boolean.TRUE),
	JWT_TOKNE_REFRESH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10330", "JWT 토큰 재발급 수행 오류", "JWT 토큰 재발급 수행 오류", Boolean.TRUE),
	JWT_TOKEN_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10331", "JWT 토큰 정보 파싱 수행 오류", "JWT 토큰 정보 파싱 수행 오류", Boolean.TRUE),
	JWT_TOKNE_EXEC_VALID_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10332", "JWT 토큰 수행 유효성 체크 오류", "JWT 토큰 수행 유효성 체크 오류", Boolean.TRUE),
	JWT_TOKNE_EXEC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10333", "JWT 토큰 수행 오류", "JWT 토큰 수행 오류", Boolean.TRUE),
	JWT_TOKNE_EXEC_VERIFY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "10334", "JWT 토큰 수행 검증 오류", "JWT 토큰 수행 검증 오류", Boolean.TRUE),
	JWT_TOKNE_EXPIRE_ERROR(HttpStatus.UNAUTHORIZED , "10335", "JWT 토큰 만료 오류", "JWT 토큰 만료 오류", Boolean.TRUE),
	JWT_TOKNE_NOT_SUPPORT_ERROR(HttpStatus.BAD_REQUEST , "10336", "JWT 토큰 미지원 설정/포맷 오류", "JWT 토큰 미지원 설정/포맷 오류", Boolean.TRUE),
	JWT_TOKNE_MAL_FORMED_ERROR(HttpStatus.BAD_REQUEST , "10337", "잘못된 JWT 토큰 형식 오류", "잘못된 JWT 토큰 형식 오류", Boolean.TRUE),
	JWT_TOKNE_SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED , "10338", "JWT 토큰 서명 검증 오류", "JWT 토큰 서명 검증 오류", Boolean.TRUE),
	JWT_TOKNE_ILLEGAL_ARGUMEMT_ERROR(HttpStatus.UNAUTHORIZED , "10339", "JWT 토큰 부적절한 인자 오류", "JWT 토큰 부적절한 인자 오류", Boolean.TRUE),
	
	//	Common > Security Error > xss		10340 ~ 10359
	XSS_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10340", "프레임워크 XSS 공통 수행 오류.", "프레임워크 XSS 공통 수행 오류.", Boolean.TRUE),
	XSS_VERIFICATION_ERROR(HttpStatus.UNAUTHORIZED, "10341", "프레임워크 XSS 검증 오류.", "프레임워크 XSS 검증 오류.", Boolean.TRUE),
	XSS_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10342", "프레임워크 XSS 필터 수행 오류.", "프레임워크 XSS 필터 수행 오류.", Boolean.TRUE),
	XSS_CONVERSION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10343", "프레임워크 XSS 변환 수행 오류.", "프레임워크 XSS 변환 수행 오류.", Boolean.TRUE),
	
	//	Common > Security Error > csrf		10360 ~ 10379
	CSRF_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10360", "프레임워크 CSRF 공통 수행 오류.", "프레임워크 CSRF 공통 수행 오류.", Boolean.TRUE),
	CSRF_FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, "10361", "프레임워크 CSRF 접근 금지 오류.", "프레임워크 CSRF 접근 금지 오류.", Boolean.TRUE),
	CSRF_VERIFICATION_ERROR(HttpStatus.UNAUTHORIZED, "10362", "프레임워크 CSRF 검증 오류.", "프레임워크 CSRF 검증 오류.", Boolean.TRUE),
	CSRF_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10363", "프레임워크 CSRF 필터 수행 오류.", "프레임워크 CSRF 필터 수행 오류.", Boolean.TRUE),
	
	//	Common > Security Error > referer  10380 ~ 10399
	REFERER_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10380", "프레임워크 REFERER 공통 수행 오류.", "프레임워크 REFERER 공통 수행 오류.", Boolean.TRUE),
	REFERER_VERIFICATION_ERROR(HttpStatus.UNAUTHORIZED, "10381", "프레임워크 REFERER 검증 오류.", "프레임워크 REFERER 검증 오류.", Boolean.TRUE),
	REFERER_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10382", "프레임워크 REFERER 필터 수행 오류.", "프레임워크 REFERER 필터 수행 오류.", Boolean.TRUE),
	
	// Common > Security Error  > cypher 10400 ~ 10499
	CYPHER_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10400", "프레임워크 암호화/복호화 공통 수행 오류.", "프레임워크 암호화/복호화 공통 수행 오류.", Boolean.TRUE),
	CYPHER_SERVICE_NOT_IMPLEMENT_ERROR(HttpStatus.NOT_IMPLEMENTED, "10401", "프레임워크 암호화/복호화 서비스 미구현 오류.", "프레임워크 암호화/복호화 서비스 미구현 오류.", Boolean.TRUE),
	CYPHER_SERVICE_NOT_AVAILABLE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "10402", "프레임워크 암호화/복호화 서비스 불가 오류.", "프레임워크 암호화/복호화 서비스 불가 오류.", Boolean.TRUE),
	CYPHER_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10403", "프레임워크 암호화/복호화 수행 오류.", "프레임워크 암호화/복호화 수행 오류.", Boolean.TRUE),
	CYPHER_ENCRYPT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10404", "프레임워크 암호화 수행 오류.", "프레임워크 암호화 수행 오류.", Boolean.TRUE),
	CYPHER_DECRYPT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10405", "프레임워크 복호화 수행 오류.", "프레임워크 복호화 수행 오류.", Boolean.TRUE),
	
	//	Common > Security > IdentityVefication Error 20300 ~ 20599
	IDENTITY_VERIFICATION_ERROR(HttpStatus.UNAUTHORIZED, "20300","프레임워크 본인 인증 오류", "프레임워크 본인 인증 오류", Boolean.TRUE),
	IDENTITY_VERIFICATION_COMMON_ERROR(HttpStatus.UNAUTHORIZED, "20301","프레임워크 본인 인증 공통 오류", "프레임워크 본인 인증 공통 오류", Boolean.TRUE),	
	IDENTITY_VERIFICATION_REQUEST_ERROR(HttpStatus.UNAUTHORIZED, "20302","프레임워크 본인 인증 수행 오류", "프레임워크 본인 인증 수행 오류", Boolean.TRUE),
	IDENTITY_VERIFICATION_EXECUTION_ERROR(HttpStatus.UNAUTHORIZED, "20303","프레임워크 본인 인증 수행 오류", "프레임워크 본인 인증 수행 오류", Boolean.TRUE),
	IDENTITY_VERIFICATION_RESPONSE_ERROR(HttpStatus.UNAUTHORIZED, "20304","프레임워크 본인 인증 수행 오류", "프레임워크 본인 인증 수행 오류", Boolean.TRUE),
	IDENTITY_VERIFICATION_CALLBACK_ERROR(HttpStatus.UNAUTHORIZED, "20305","프레임워크 본인 인증 결과 콜백 오류", "프레임워크 본인 인증 결과 콜백 오류", Boolean.TRUE),
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	Common > Database Error 	10500 ~ 10599
	FRAMEOWRK_DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10500", "프레임워크 데이터페이스 수행 오류", "프레임워크 데이터페이스 수행 오류", Boolean.TRUE),
	FRAMEOWRK_DATABASE_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10501", "데이터페이스 공통 수행 오류", "데이터페이스 공통 수행 오류", Boolean.TRUE),
	FRAMEOWRK_DATABASE_CONFIGURATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10502", "데이터페이스 설정 수행 오류", "데이터페이스 설정 수행 오류", Boolean.TRUE),
	FRAMEOWRK_DATABASE_ACCESS_ERROR(HttpStatus.NOT_ACCEPTABLE, "10503", "데이터페이스 접근 수행 오류", "데이터페이스 접근 수행 오류", Boolean.TRUE),
	FRAMEOWRK_DATABASE_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10504", "데이터페이스 수행 수행 오류", "데이터페이스 수행 수행 오류", Boolean.TRUE),
	FRAMEOWRK_DATABASE_CONSTRAINT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10506", "데이터페이스 수행 제약 오류", "데이터페이스 수행 제약 오류", Boolean.TRUE),
	FRAMEOWRK_DATABASE_INTEGRITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10507", "데이터페이스 무결성 수행 오류", "데이터페이스 무결성 수행 오류", Boolean.TRUE),
	FRAMEOWRK_DATABASE_SQL_QUERY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10508", "데이터페이스 쿼리 수행 오류", "데이터페이스 쿼리 수행 오류", Boolean.TRUE),
	FRAMEOWRK_DATABASE_DUPLICATION_KEY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10509", "데이터페이스 중복 키 오류", "데이터페이스 중복 키 오류", Boolean.TRUE),
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	Component Error 				10600 ~ 10699
	FRAMEWORK_COMPONENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10600", "프레임워크 컴포넌트 수행 오류", "프레임워크 컴포넌트 수행 오류", Boolean.TRUE),
	FRAMEWORK_LOG_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10601", "프레임워크 로그 컴포넌트 수행 오류", "프레임워크 로그 컴포넌트 수행 오류", Boolean.TRUE),
	FRAMEWORK_LOG_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10602", "프레임워크 로그 컴포넌트 공통 수행 오류", "프레임워크 로그 컴포넌트 공통 수행 오류", Boolean.TRUE),
	
	//	Network Error		 				10700 ~ 10899
	//	Network > Common Error 	10700 ~ 10719
	FRAMEWORK_NETWORK_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10700", "네트워크 공통 수행 오류", "네트워크 공통 수행 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_SERVICE_NOT_IMPLEMENT_ERROR(HttpStatus.NOT_IMPLEMENTED, "10701", "네트워크 연동 서비스 미구현 수행 오류.", "네트워크 연동 서비스 미구현 수행 오류.", Boolean.TRUE),
	FRAMEWORK_NETWORK_SERVICE_NOT_AVAILABLE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "10702", "네트워크 연동 서비스 수행 불가 오류.", "네트워크 연동 서비스 수행 불가 오류.", Boolean.TRUE),
	FRAMEWORK_NETWORK_AUTH_ERROR(HttpStatus.UNAUTHORIZED, "10703", "네트워크 연동 인증 오류", "네트워크 연동 인증 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "107014", "네트워크 원격 HOST 연결 오류", "네트워크 원격 HOST 연결 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_BAD_GATEWAY_ERROR(HttpStatus.BAD_GATEWAY, "10705", "네트워크 원격 통신 프로토콜 오류", "네트워크 원격 통신 프로토콜 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_CONNECT_ERROR(HttpStatus.NOT_ACCEPTABLE, "10706", "네트워크 원격 HOST 연결 오류", "네트워크 원격 HOST 연결 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_DISCONNECT_ERROR(HttpStatus.NOT_ACCEPTABLE, "10707", "네트워크 원격 HOST 연결 해지 오류", "네트워크 원격 HOST 연결 해지 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_INVALD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "10708", "네트워크 원격 HOST 연결 오류", "네트워크 원격 HOST 연결 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_INVALD_PACKET_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10709", "네트워크 부적합 패킷 오류", "네트워크 부적합 패킷 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "10710", "네트워크 원격 HOST 요청 오류", "네트워크 원격 HOST 요청 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10711", "네트워크 수행 오류", "네트워크 수행 오류", Boolean.TRUE),
	FRAMEWORK_NETWORK_RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10712", "네트워크 수신 응답 수행 오류", "네트워크 수신 응답 수행 오류", Boolean.TRUE),
	//	Network > FTP Error 	10720 ~ 10739
	
	//	Network > HTTP Error	10740 ~ 10759
	
	//	Network > TCP Error 	10760 ~ 10779
	
	//	Network > Websocket Error 	10800 ~ 10829
	
	
	//	Network > Mail Error (10830 ~ 10859)
	FRAMEWOWK_MAIL_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10830", "프레임워크 내부 메일 수행 관련 공통 오류", "프레임워크 내부 메일 수행 관련 공통 오류", Boolean.TRUE),
	FRAMEWOWK_MAIL_VALID_FAILURE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10831", "프레임워크 내부 메일 발송 유효성 체크 오류", "프레임워크 내부 메일 발송 유효성 체크 오류", Boolean.TRUE),
	FRAMEWOWK_MAIL_VERIFY_FAILURE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10832", "프레임워크 내부 메일 발송 검증 수행 오류", "프레임워크 내부 메일 발송 검증 수행 오류", Boolean.TRUE),
	FRAMEWOWK_MAIL_SEND_REQUEST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10833", "프레임워크 내부 메일 발송 요청 수행 오류", "프레임워크 내부 메일 발송 요청 수행 오류", Boolean.TRUE),
	FRAMEWOWK_MAIL_SEND_EXECUTE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10834", "프레임워크 내부 메일 발송 수행 오류", "프레임워크 내부 메일 발송 수행 오류", Boolean.TRUE),
	FRAMEWOWK_MAIL_SEND_RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10835", "프레임워크 내부 메일 발송 결과 응답 수행 오류", "프레임워크 내부 메일 발송 결과 응답 수행 오류", Boolean.TRUE),
	
	//	Async Error ( 10900 ~ 10929 )
	FRAMEWORK_ASYNC_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10900", "프레임워크 비동기 쓰레드 공통 수행 오류", "프레임워크 비동기 쓰레드 공통 수행 오류", Boolean.TRUE),
	FRAMEWORK_ASYNC_PROCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10901", "프레임워크 비동기 프로세스 쓰레드 수행 오류", "프레임워크 비동기 프로세스 쓰레드 수행 오류", Boolean.TRUE),
	FRAMEWORK_ASYNC_PROCESSOR_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10902", "프레임워크 비동기 프로세서 쓰레드 수행 오류", "프레임워크 비동기 프로세서 쓰레드 수행 오류", Boolean.TRUE),
	FRAMEWORK_ASYNC_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10903", "프레임워크 비동기 서비스 쓰레드 수행 오류", "프레임워크 비동기 서비스 쓰레드 수행 오류", Boolean.TRUE),
	FRAMEWORK_ASYNC_EXECUTOR_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10904", "프레임워크 비동기 수행 객체 쓰레드 수행 오류", "프레임워크 비동기 수행 객체  쓰레드 수행 오류", Boolean.TRUE),
	
	// Scheduler Error ( 10930 ~ 10949 )
	FRAMEWORK_SCHEDULE_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10930", "프레임워크 스케줄러 공통 수행 오류", "프레임워크 비동기 스케줄러 공통 수행 오류", Boolean.TRUE),
	FRAMEWORK_SCHEDULE_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10931", "프레임워크 스케줄러 수행 오류", "프레임워크 스케줄러 수행 오류", Boolean.TRUE),
	FRAMEWORK_SCHEDULE_CRONTAB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10932", "프레임워크 크론탭 스케줄러 수행 오류", "프레임워크 크론탭 스케줄러 수행 오류", Boolean.TRUE),
	FRAMEWORK_SCHEDULE_DURATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10933", "프레임워크 주기 스케줄러 수행 오류", "프레임워크 주기 스케줄러 수행 오류", Boolean.TRUE),
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	OpenApi Error 					20000 ~ 20099
	FRAMEWORK_OPENAPI_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20000", "프레임워크 오픈API 수행 오류", "프레임워크 오픈API 수행 오류", Boolean.TRUE),
	FRAMEWORK_OPENAPI_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20001", "프레임워크 오픈API 요청 파라미터 오류", "프레임워크 오픈API 요청 유효성 오류", Boolean.TRUE),
	FRAMEWORK_OPENAPI_INVALID_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "20002", "프레임워크 오픈API 요청 파라미터 오류", "프레임워크 오픈API 요청 유효성 오류", Boolean.TRUE),
	FRAMEWORK_OPENAPI_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20003", "프레임워크 오픈API 요청 수행 오류", "프레임워크 오픈API 요청 수행 오류", Boolean.TRUE),
	FRAMEWORK_OPENAPI_RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20004", "프레임워크 오픈API 응답 처리 오류", "프레임워크 오픈API 응답 처리 오류", Boolean.TRUE),
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	WorkFlow Error 					20100 ~ 20119
	FRAMEWORK_WORKFLOW_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20100", "프레임워크 WorkFlow 수행 오류", "프레임워크 WorkFlow 수행 오류", Boolean.TRUE),
	FRAMEWORK_WORKFLOW_COMMON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20101", "프레임워크 WorkFlow 공통 수행 오류", "프레임워크 WorkFlow 공통 수행 오류", Boolean.TRUE),
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	Business Error 		20200 ~  20299
	FRAMEWORK_BUSINESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20200", "어플리케이션 비즈니스 수행 오류", "어플리케이션 비즈니스 공통 수행 오류", Boolean.TRUE),
	NONE_REQUEST_CONDITION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20201", "비즈니스 수행 조건 정보 미 전달 오류", "비즈니스 수행 조건 정보 미 전달 오류", Boolean.TRUE),
	REGISTRATION_EXEC_FAILURE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20202", "등록 요청 수행 실패 오류", "등록 요청 수행 실패 오류", Boolean.TRUE),
	MODIFICATION_EXEC_FAILURE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20203", "변경 요청 수행 실패 오류", "변경 요청 수행 실패 오류", Boolean.TRUE),
	
	//	공통 오류 사유 정의 ( 99990 ~ 99999 )
	ETC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , SBNConstant.ETC_ERR_CODE, SBNConstant.ETC_ERR__MSG, "기타 오류 사유 코드.", Boolean.TRUE),										// 99997
	UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, SBNConstant.UNKNOWN_ERR_CODE, SBNConstant.UNKNOWN_ERR_CODE, "미 확인 오류 사유 코드.", Boolean.TRUE),		// 99998
	FAILURE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, SBNConstant.ERR_CODE, SBNConstant.ERR_MSG, "프레임워크 내부 수행 실패 사유 코드.", Boolean.TRUE);						// 09999
	/* @formatter:on */
	
	//	발생 오류 HTTP 응답 상태 유형 객체
	private HttpStatus reasonHttpStaus;
	//	발생 오류 코드
	private String reasonCode;
	//	발생 오류 메시지
	private String reasonMessage;
	//	발생 오류 설명
	private String reasonDesc;
	//	발생 오류 유형 사용 여부
	private Boolean isUsable;
	
	/*
	 * 프레임워크 내부 정의 오류 발생 시 예외 수행을 위한 예외 유형 정보 정의 객체를 생성하는 객체 생성자
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @update	
	 * @param 		reasonHttpStaus		발생 오류 HTTP 응답 상태 유형 객체
	 * @param 		reasonCode			발생 오류 코드
	 * @param 		reasonMessage		발생 오류 메시지
	 * @param 		reasonDesc				발생 오류 설명
	 * @param 		isUsable					발생 오류 유형 사용 여부
	 */
	private QNPReasonCode(HttpStatus reasonHttpStaus, String reasonCode, String reasonMessage, String reasonDesc, Boolean isUsable) {
		this.reasonHttpStaus = reasonHttpStaus;
		this.reasonCode = reasonCode;
		this.reasonMessage = reasonMessage;
		this.isUsable = isUsable;
	}
	
	/**
	 * 프레임워크 내부 수행 예외 발생 시, WEB 수행 요청에 대한 응답 상태 유형 객체를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @return		프레임워크 내부 수행 예외 발생 시, WEB 수행 요청에 대한 응답 상태 유형 객체
	 */
	public HttpStatus getReasonHttpStaus() {
		return reasonHttpStaus;
	}
	
	/**
	 * 프레임워크 내부 수행 예외 발생 시, 발생 사유에 대한 문자열 코드 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @return		프레임워크 내부 수행 예외 발생 시, 발생 사유에 대한 문자열 코드 정보
	 */
	public String getReasonCode() {
		return reasonCode;
	}
	
	/**
	 * 프레임워크 내부 수행 예외 발생 시, 발생 사유에 대한 사유 메시지 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @return		프레임워크 내부 수행 예외 발생 시, 발생 사유에 대한 사유 메시지 정보
	 */
	public String getReasonMessage() {
		return reasonMessage;
	}
	
	/**
	 * 프레임워크 내부 수행 예외 발생 시, 발생 사유에 대한 사유 상세 설명 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @return		프레임워크 내부 수행 예외 발생 시, 발생 사유에 대한 사유 상세 설명 정보
	 */
	public String getReasonDesc() {
		return reasonDesc;
	}
	
	/**
	 * 프레임워크 내부 수행 예외 발생 시, 발생 사유 정의 사용 여부 Flag 를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @return		프레임워크 내부 수행 예외 발생 시, 발생 사유 정의 사용 여부 Flag
	 */
	public Boolean isUsable() {
		return isUsable;
	}
	
	/**
	 * 프레임워크 내부 수행 예외 발생 시, 발생 사유의 세부 정보에 대한 문자열 정보를 전달하는 메소드
	 * 
	 * @author		BeomSeok.Seo
	 * @date 		2026.09.01
	 * @return		프레임워크 내부 수행 예외 발생 시, 발생 사유의 세부 정보에 대한 문자열 정보
	 */
	public String getReason() {
		StringBuilder reasonInfo = new StringBuilder();
		reasonInfo.append(QNPReasonCode.class.getSimpleName()).append("{ ");
		reasonInfo.append("reasonHttpStaus=").append(reasonHttpStaus.name()).append(", ");
		reasonInfo.append("reasonCode=").append(reasonCode).append(", ");
		reasonInfo.append("reasonMessage=").append(reasonMessage).append(", ");
		reasonInfo.append("reasonDesc=").append(reasonDesc).append(", ");
		reasonInfo.append("isUsable=").append(isUsable);
		reasonInfo.append(" }");
		return reasonInfo.toString();
	}
}
