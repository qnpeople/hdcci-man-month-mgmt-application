package com.qnpeople.rnd.pms.apis.auth.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.qnpeople.rnd.pms.common.domain.executor.entity.QNPWebBaseMapper;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.apis.auth.mapper
 * @Filename		: WorkerAccessAuthMapper.java
 * ====================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.10.14.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  웹 어플리케이션의 인증 영역의 근무자 접근 인증을 위한 수행 SQL 쿼리 매핑하는 작업을 수행하기 위한 인터페이스 클래스 
 * =================================================================================
 */
@Mapper
public interface WorkerAccessAuthMapper extends QNPWebBaseMapper {

}
