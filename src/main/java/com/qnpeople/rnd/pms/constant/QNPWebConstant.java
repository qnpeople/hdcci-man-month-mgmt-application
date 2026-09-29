package com.qnpeople.rnd.pms.constant;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.constant
 * @Filename		: QNPWebConstant.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  3.0		2026.09.26.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  프레임워크 내부 기본 제공 프레임워크 설정 및 공통 상수 정보를 정의한 상수형 설정 정의 클래스
 * =================================================================================
 */
public class QNPWebConstant {

	/** 작업 정상 수행 공통 코드 정의 */
	public static final String SUCC_CODE = "00000";
	/** 작업 정상 수행 공통 코드 메시지 정의 */
	public static final String SUCC_MSG = "클라이언트 요청 수행 성공.";
	
	/** 작업 기타 오류 수행 공통 코드 정의 */
	public static final String ETC_ERR_CODE = "99997";
	/** 작업 기타 오류 수행 공통 코드 메시지 정의 */
	public static final String ETC_ERR__MSG = "기타 오류 발생";

	/** 작업 미 확인 오류 수행 공통 코드 정의 */
	public static final String UNKNOWN_ERR_CODE = "99998";
	/** 작업 미 확인 오류 수행 공통 코드 메시지 정의 */
	public static final String UNKNOWN_ERR__MSG = "미 확인 오류 발생";
	
	/** 작업 오류 수행 공통 코드 정의 */
	public static final String ERR_CODE = "99999";
	/** 작업 오류 수행 공통 코드 메시지 정의 */
	public static final String ERR_MSG = "클라이언트 요청 수행 오류 발생";
	
	/** HTTP(S) 요청 전달 Content-Type Header 키 정보 */
	public static final String REQ_CONTENT_TYPE_HEADER_NAME = "Content-Type";	
	/** HTTP(S) 기본 요청 Content-Type 정보 */
	public static final String DEFAULT_CLIENT_REQUEST_CONTENT_TYPE = "application/json";
	/** HTTP(S) 기본 응답 Content-Type 정보 */
	public static final String DEFAULT_CLIENT_RESPONSE_CONTENT_TYPE = "application/json";
	/** HTTP(S) 기본 파일 업로드 수행 Content-Type 의 Prefix 값 정보 */
	public static final String FILE_UPLOAD_CONTENT_TYPE_PREFIX = "multipart/form-data";
	
	/** HTTP(S) 요청 URI 와 파라미터 정보 구분 토큰 정보 */
	public static final String HTTP_REQUEST_URI_DELIM_TOKEN = "/";
	/** HTTP(S) 요청 URI 와 파라미터 정보 구분 토큰 정보 */
	public static final String HTTP_REQUEST_URL_DELIM_TOKEN = "?";
	/** HTTP(S) 요청 URL 의 파라미터별 정보 구분 토큰 정보 */
	public static final String HTTP_REQUEST_PARAM_DELIM_TOKEN = "&";
	/** HTTP(S) 요청 URL 의 파라미터 키/값 정보 구분 토큰 정보 */
	public static final String HTTP_REQUEST_PARAM_VALUE_DELIM_TOKEN = "=";
	
	public static final String DEFAULT_HTTP_REQEUST_CHARSET = "UTF-8";
	public static final String DEFAULT_HTTP_RESPONSE_CHARSET = "UTF-8";

	///////////////////////////////////////////////////////////////////////////////////
	//	요청 클라이언트의 원격 주소 처리 관련 정보 정의 
	///////////////////////////////////////////////////////////////////////////////////
	/** HTTP 요청 클라이언트 IP 미 확인 경우(Header 가 'Forwarded') 기본 값 */
	public static final String UNKNOWN_CLIENT_IP_VALUE = "unknown";
	/** HTTP 요청의 프록시 또는 로드 밸런서를 통해 웹 서버로의 접속 클라이언트의 원래 IP 주소 식별 표준 헤더키 정보 ( X-Forward-For: XFF ) */
	public static final String X_FORWARD_FOR_HEADER_KEY = "X-Forward-For";
	/**  */
	public static final String PROXY_CLIENT_IP_HEADER_KEY = "Proxy-Client-IP";
	/**  */
	public static final String WL_PROXY_CLIENT_IP_HEADER_KEY = "WL-Proxy-Client-IP";
	/** */
	public static final String HTTP_CLIENT_IP_HEADER_KEY = "HTTP_CLIENT_IP";
	/** */
	public static final String HTTP_X_FORWARDED_FOR_HEADER_KEY = "HTTP_X_FORWARDED_FOR";
	/** */
	public static final String X_REAL_IP_HEADER_KEY = "X-Real-IP";
	/** */
	public static final String X_REALIP_HEADER_KEY = "X-RealIP";
	/** */
	public static final String  REMOTE_ADDR_HEADER_KEY = "REMOTE_ADDR";
	
	
	//////////////////////////////////////////////////////////////////////////
	//	WEB Client Session 처리 수행 정보 정의 부분
	//////////////////////////////////////////////////////////////////////////
	/** 클라이언트의 서버 세션 정보에 대한 키 명 정보 */
	public static final String CLIENT_SESSION_KEY_NAME = "USER_SESSION";
	
	/** 클라이언트 요청 Body 내용 노출 기본 길이 */
	public static final Integer DEFAULT_REQUEST_BODY_DISPLAY_LENGTH = 100;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	//	WEB Client 의 목록 조회 요청 시 패이징 처리 수행 정보 정의 부분
	/////////////////////////////////////////////////////////////////////////////////////////////////
	/** */
	public static final Integer DEFAULT_ROW_DATA_CNT = 30;
	/** */
	public static final Integer DEFAULT_PAGE_NO = 1;
	
}
