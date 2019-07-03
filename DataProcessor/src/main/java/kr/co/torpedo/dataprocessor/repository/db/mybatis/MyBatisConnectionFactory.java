package kr.co.torpedo.dataprocessor.repository.db.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class MyBatisConnectionFactory {
	private static final Logger invalidFileLogger = LoggerFactory.getLogger(UserRepository.class);
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String path = "main/resources/mybatis/config.xml";
			Reader reader = Resources.getResourceAsReader(path);

			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch (IOException e) {
			invalidFileLogger.error("MyBatisConnectionFactory error : " + e);
		}
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
