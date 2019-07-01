package kr.co.torpedo.dataprocessor.processor.db.jdbc;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class DBConnection {
	private static DataSource dataSource;
	private static final String DRIVER_NAME;
	private static final String URL;
	private static final String USER_NAME;
	private static final String PWD;

	static {
		final ResourceBundle config = ResourceBundle.getBundle("main.resources.config");
		DRIVER_NAME = "org.mariadb.jdbc.Driver";
		URL = config.getString("db.url");
		USER_NAME = config.getString("db.userid");
		PWD = config.getString("db.pw");
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
			ProcessorCommon.invalidFileLogger.error("DBConnection error : " + e);
			e.printStackTrace();
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
