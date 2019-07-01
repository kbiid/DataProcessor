package kr.co.torpedo.dataprocessor.processor.db.mybatis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class MyBatisConnectionFactory {
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String path = "main/resources/mybatis/config.xml";
			Reader reader = Resources.getResourceAsReader(path);

			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch (FileNotFoundException e) {
			ProcessorCommon.invalidFileLogger.error("MyBatisConnectionFactory error : " + e);
			e.printStackTrace();
		} catch (IOException e) {
			ProcessorCommon.invalidFileLogger.error("MyBatisConnectionFactory error : " + e);
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
