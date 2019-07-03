package kr.co.torpedo.dataprocessor.repository.db.jdbc;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class DBConnection {
	private static final Logger invalidFileLogger = LoggerFactory.getLogger(UserRepository.class);
	private static DataSource dataSource;
	private static final String DRIVER_NAME;
	private static final String URL;
	private static final String USER_NAME;
	private static final String PWD;

	static {
		PropertyResourceBundle p = null;
		try (FileInputStream inputStream = new FileInputStream(System.getProperty("config.properties"))) {
			p = new PropertyResourceBundle(inputStream);
		} catch (IOException e) {
			invalidFileLogger.error("DBConnection error : " + e);
		}
		DRIVER_NAME = "org.mariadb.jdbc.Driver";
		URL = p.getString("db.url");
		USER_NAME = p.getString("db.userid");
		PWD = p.getString("db.pw");
		dataSource = setupDataSource();
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	private static DataSource setupDataSource() {
		ComboPooledDataSource cdps = new ComboPooledDataSource();
		try {
			cdps.setDriverClass(DRIVER_NAME);
		} catch (PropertyVetoException e) {
			invalidFileLogger.error("DBConnection error : " + e);
		}
		cdps.setJdbcUrl(URL);
		cdps.setUser(USER_NAME);
		cdps.setPassword(PWD);
		cdps.setMinPoolSize(5);
		cdps.setAcquireIncrement(5);
		cdps.setMaxPoolSize(20);
		return cdps;
	}
}
