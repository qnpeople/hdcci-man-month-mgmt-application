package com.qnpeople.rnd.pms.common.database;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qnpeople.rnd.pms.common.reason.QNPReasonCode;
import com.qnpeople.rnd.pms.common.reason.QNPReasonInterface;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

import kr.co.sbn.platformhub.framework.core.common.database.common.config.hikari.SBNHikariDatabasePoolConfig;
import kr.co.sbn.platformhub.framework.core.common.database.common.config.hikari.SBNHikariDatabasePoolConfigData;
import kr.co.sbn.platformhub.framework.core.common.database.common.configuration.postgresql.SBNPostgresqlDatabaseConfiguration;
import kr.co.sbn.platformhub.framework.core.common.database.common.configuration.postgresql.SBNPostgresqlDatabaseConfigurationAdaptor;
import kr.co.sbn.platformhub.framework.core.common.database.exceptions.SBNDatabaseException;
import kr.co.sbn.platformhub.framework.core.common.database.types.SBNDatabaseConnectionPoolType;
import kr.co.sbn.platformhub.framework.core.common.database.types.SBNDatabaseType;
import kr.co.sbn.platformhub.framework.core.utils.SBNUtils;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ================================================================================
 * @Project		: hdcci-man-month-mgmt-application
 * @Package		: com.qnpeople.rnd.pms.common.database
 * @Filename		: QNPHikariDatabaseConfiguration.java
 * 
 * =================================================================================
 *  version      date              author 						description
 * =================================================================================
 *  1.0		2026.08.20.     BeamSeok.Seo				Initialization
 *  =================================================================================
 *  [ 설명 ]
 *  QNP 웹 어플리케이션의 내부 작업 수행을 위한 내부 PostgreSQL 데이터베이스 연동 및 관리를 자동 설정하기 위한 최상위 클래스
 * =================================================================================
 */
@Configuration
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
@Slf4j
public class QNPHikariDatabaseConfiguration extends SBNPostgresqlDatabaseConfigurationAdaptor implements SBNPostgresqlDatabaseConfiguration {

	/* Hikari PostgreSQL 데이터베이스 수행 Entity 검색 패키지 목록 객체 */
	private static final String[] PACKAGES_ENTITY_TO_SCAN = {
			"com.qnpeople.rnd.pmp.apis.**.model"
	};
	/* JPA PostgreSQL 내부 자바 코드에 대한 SQL 변환을 위한 정의 클래스*/
	private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	
	/**
	 * QNP 프레임워크 내부 제공 PostgreSQL 데이터베이스 구축 및 수행 모듈을 자동으로 구축하고 관리하는 작업을 수행하는 객체를 생성하는 기본 객체 생성자
	 * 
	 * @author 	BeomSeok.Seo
	 * @date 		2026.08.20
	 */
	public QNPHikariDatabaseConfiguration() {
		super(SBNDatabaseConnectionPoolType.HIKARI_POOL, "Hikari PostgreSQL Database Configuration", "datasource-postgresql-master");
	}
	
	/**
	 * 구축 대상 PostgreSQL 데이터베이스의 설정 장보 객체에 대한 Hikari 설정 객체 자동 생성 및 구축 작업 수행 후 전달하는 메소드
	 * 
	 * @author 	BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		자동 구축된 데이터베이스 연동 및 수행 설정 정보 객체
	 * @throws 	SBNDatabaseException		작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Primary
	@Bean("postgresqlHikariMaster")
	@ConfigurationProperties(prefix = "spring.datasource.datasource-postgresql-master.hikari")
	public SBNHikariDatabasePoolConfig buildAutoDatabaseConfigData() throws SBNDatabaseException {
		QNPReasonInterface errorReason = QNPReasonCode.FRAMEOWRK_DATABASE_COMMON_ERROR;
		 String errorCode = errorReason.getReasonCode();
		 String errorMessage = "";
		 //
		 SBNHikariDatabasePoolConfigData hikariDatabasePoolConfigData = null;
		 try {
			hikariDatabasePoolConfigData = new SBNHikariDatabasePoolConfigData(SBNDatabaseType.POSTGRESQL, configurationName, databaseConnectionPoolPersistUnitName);
			if(SBNUtils.isNull(hikariDatabasePoolConfigData)) {
				errorMessage = "PostgreSQL Master Hikari Pool 설정 객체 구축 오류.";
				throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
			}
			return hikariDatabasePoolConfigData;
		 } catch(Exception exception) {
			 log.error("buildAutoDatabaseConfigData() exception={}", exception.toString());
			 if(!SBNUtils.isNull(exception.getMessage())) {
				 errorMessage = exception.getMessage();
			 } else {
				 errorMessage = exception.toString();
			 }
			 throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
		 }
	}
	
	/**
	 * 구축 대상 PostgreSQL 데이터베이스 작업 수행을 위한 DataSource 객체에 대해 자동 생성 및 구축 작업 수행 후 전달하는 메소드
	 *  
	 * @author 	BeomSeok.Seo
	 * @date 		2026.08.20
	 * @return		자동 구축된 데이터베이스 작업 수행 DataSource 객체
	 * @throws 	SBNDatabaseException		작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Primary
	@Bean("postgresqlHikariMasterDataSource")
	public DataSource buildAutoDataSource() throws SBNDatabaseException {
		QNPReasonInterface errorReason = QNPReasonCode.FRAMEOWRK_DATABASE_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy = null;
		SBNHikariDatabasePoolConfigData hikariDatabasePoolConfigData = null;
		HikariDataSource hikariDataSource = null;
		try {
			//
			hikariDatabasePoolConfigData = (SBNHikariDatabasePoolConfigData)buildAutoDatabaseConfigData();
			if(SBNUtils.isNull(hikariDatabasePoolConfigData)) {
				errorMessage = "Hikari Database Pool Config 객체 구축 오류.";
				throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
			}
			//
			hikariDatabasePoolConfigData.addDataSourceProperty("cachePrepStmts", "true");
			hikariDatabasePoolConfigData.addDataSourceProperty("useServerPrepStmts", "true");
			hikariDatabasePoolConfigData.addDataSourceProperty("prepStmtCacheSize", "250");
			hikariDatabasePoolConfigData.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			//
			hikariDataSource = new HikariDataSource(hikariDatabasePoolConfigData);
			lazyConnectionDataSourceProxy = new LazyConnectionDataSourceProxy(hikariDataSource);
			//	
			return lazyConnectionDataSourceProxy;
		 } catch(Exception exception) {
			 log.error("buildAutoDataSource() exception={}", exception.toString());
			 if(!SBNUtils.isNull(exception.getMessage())) {
				 errorMessage = exception.getMessage();
			 } else {
				 errorMessage = exception.toString();
			 }
			 throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
		 }
	}
	
	/**
	 * 구축 대상 데이터베이스의 마이바티스 연동 SQL 세션 작업 수행 및 관리를 위한 SqlSessionFactory 객체 자동 생성 및 구축 작업 수행 후 전달하는 메소드
	 *	
	 * @author 	BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	databaseDataSource			자동 구축 대상 데이터베이스 DataSource 객체
	 * @param 	applicationContext			자동 구축을 위한 어플리케이션 Context 객체
	 * @return		전달된 객체를 이용하여 구축 대상 데이터베이스의 마이바티스 연동 SQL 세션 작업 수행 및 관리를 위한 SqlSessionFactory 객체
	 * @throws 	SBNDatabaseException		작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Bean(name = "postgresqlHikariMasterSessionFactory")
	public SqlSessionFactory buildAutoMybatisDatabaseSqlSessionFactory(@Qualifier("postgresqlHikariMasterDataSource") DataSource databaseDataSource, ApplicationContext applicationContext) throws SBNDatabaseException {
		QNPReasonInterface errorReason = QNPReasonCode.FRAMEOWRK_DATABASE_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		SqlSessionFactoryBean sqlSessionFactoryBean = null;
		try {
			//	
			if(SBNUtils.isNull(databaseDataSource)) {
				errorMessage = "DataSource 객체 미 전달 오류.";
				throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
			}
			if(SBNUtils.isNull(applicationContext)) {
				errorMessage = "ApplicationContext 객체 미 전달 오류.";
				throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
			}
			// 
			sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(databaseDataSource);
			sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:mapper/**/*.xml"));
			sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
			//	
			return sqlSessionFactoryBean.getObject();
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
			errorMessage = exception.getMessage();
			} else {
			errorMessage = exception.toString();
			}
			throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
		}
	}
	
	/**
	 * 데이터베이스의 마이바티스 SqlSessionFactory를 이용하여 세션 및 트랜잭션 관리를 위한 세션 템플릿 객체를 자동 구축하여 전달하는 메소드
	 * 
	 * @author 	BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	databaseSqlSessionFactory	데이터베이스의 마이바티스 연동 SQL 세션 수행 및 관리 SqlSessionFactory 객체
	 * @return		전달된 마이바티스 SQL 세션 관리 객체를 이용한 마이바티스 수행 SQL 세션 및 트랜잭션 관리 수행 템플릿 객체
	 * @throws 	SBNDatabaseException			작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	@Bean(name = "postgresqlHikariMasterSessionTemplate")
	public SqlSessionTemplate buildAutoMybatisDatabaseSqlSessionTemplate(@Qualifier("postgresqlHikariMasterSessionFactory") SqlSessionFactory databaseSqlSessionFactory) throws SBNDatabaseException {
		QNPReasonInterface errorReason = QNPReasonCode.FRAMEOWRK_DATABASE_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		SqlSessionTemplate sqlSessionTemplate = null;
		try {
			if(SBNUtils.isNull(databaseSqlSessionFactory)) {
				errorMessage = "SqlSessionFactory 객체 미 전달 오류.";
				throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
			}
			//	
			sqlSessionTemplate = new SqlSessionTemplate(databaseSqlSessionFactory);
			if(SBNUtils.isNull(sqlSessionTemplate)) {
				errorMessage = "SqlSessionTemplate 객체 구축 실패 오류.";
				throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
			}
			//	
			return sqlSessionTemplate;
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
				errorMessage = exception.getMessage();
			} else {
				errorMessage = exception.toString();
			}
			throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
		}
	}
	
	/**
	 * 데이터베이스의 JPA를 다중 DataSource로 구성 시 JPA EntityManagerFactory를 Bean 객체로 등록하는 작업을 수행 후 전달하는 메소드
	 * 
	 * @author 	BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	databaseJpaEntityManagerFactoryBuilder	데이터베이스의 JPA 요소 및 PersistenceUnitManager 관련 설정 Builder 생성 객체
	 * @return		전달된 EntityManagerFactoryBuilder 를 이용한 데이터베이스 소스 및 엔티티 스켄 패키지 설정 및 관리 객체
	 * @throws 	SBNDatabaseException							작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	 @Primary
	 @Bean(name = "postgresqlHikariMasterEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean buildAutoJpaEntityManagerFactory(EntityManagerFactoryBuilder databaseJpaEntityManagerFactoryBuilder) throws SBNDatabaseException {
		 QNPReasonInterface errorReason = QNPReasonCode.FRAMEOWRK_DATABASE_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		Map<String, String> propertiesHashMap = new HashMap<>();
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = null;
		try {
			log.info("buildAutoJpaEntityManagerFactory() 수행 시작.");
			propertiesHashMap.put("hibernate.dialect", HIBERNATE_DIALECT);
			//
			localContainerEntityManagerFactoryBean = databaseJpaEntityManagerFactoryBuilder
					.dataSource(buildAutoDataSource())
					.packages(PACKAGES_ENTITY_TO_SCAN)
					.properties(propertiesHashMap)
					.persistenceUnit(databaseConnectionPoolPersistUnitName)
					.build();
			return localContainerEntityManagerFactoryBean;
		} catch(Exception exception) {
		if(!SBNUtils.isNull(exception.getMessage())) {
			errorMessage = exception.getMessage();
		} else {
			errorMessage = exception.toString();
		}
		throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
		}
	}
	
	/**
	 * 데이터베이스의 JPA 사용 Entity 생성 객체에 대한 JAP 트랜잭션 수행 및 관리 작업을 위한 PlatformTransactionManager 객체를 구축 후 전달하는 메소드
	 * - JDBC 에서 JPA 로의 변경을 위한 기술로 서비스 코드의 트랜잭션을 JDBC 방식에 의존하지 않고 JPA 방식으로 처리하기 위함
	 * 
	 * @author 	BeomSeok.Seo
	 * @date 		2026.08.20
	 * @param 	jpaEntityManagerFactory	데이터베이스의 JPA 수행을 위한 EntityManager 생성 객체 
	 * @return		스프링 기본 제공 트랜잭션 관리를 위한 추상화된 TransactionManager 인터페이스 객체
	 * @throws 	SBNDatabaseException		작업 수행 중 오류 발생 시 예외 처리 Exception
	 */
	 @Primary
	 @Bean(name = "postgresqlHikariMasterTransactionManager")
	 @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
	public PlatformTransactionManager buildAutoPlatformTransactionManager(@Qualifier("postgresqlHikariMasterEntityManagerFactory") EntityManagerFactory jpaEntityManagerFactory) throws SBNDatabaseException {
		QNPReasonInterface errorReason = QNPReasonCode.FRAMEOWRK_DATABASE_COMMON_ERROR;
		String errorCode = errorReason.getReasonCode();
		String errorMessage = "";
		//
		JpaTransactionManager jpaTransactionManager = null;
		try {
			if(SBNUtils.isNull(jpaEntityManagerFactory)) {
				errorMessage = "EntityManagerFactory 객체 미 전달 오류.";
				throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
			}
			//	
			jpaTransactionManager = new JpaTransactionManager(jpaEntityManagerFactory);
			if(SBNUtils.isNull(jpaTransactionManager)) {
				errorMessage = "JpaTransactionManager 객체 구축 실패 오류.";
				throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
			}
			//	
			return jpaTransactionManager;
		} catch(Exception exception) {
			if(!SBNUtils.isNull(exception.getMessage())) {
			errorMessage = exception.getMessage();
			} else {
			errorMessage = exception.toString();
			}
			throw new SBNDatabaseException(errorReason, errorCode, errorMessage);
		}
	 }
	 
	@Configuration
	@EnableJpaRepositories(
		entityManagerFactoryRef = "postgresqlHikariMasterEntityManagerFactory", 
		transactionManagerRef = "postgresqlHikariMasterTransactionManager", 
		basePackages = {
				"com.qnpeople.rnd.pms"
		}
	)
	@MapperScan(value = "com.qnpeople.rnd.pms.**.mapper", sqlSessionFactoryRef = "postgresqlHikariMasterSessionFactory", sqlSessionTemplateRef = "postgresqlHikariMasterSessionTemplate")
	static class PostgreSQLMasterJpaRepositoriesConfig {
	}
}
