package kr.co.torpedo.dataprocessor.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(ConfigReader.class);
	private Properties properties;

	public ConfigReader() {
		properties = new Properties();
		loadProp();
	}

	private void loadProp() {
//		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")
//		FileInputStream inputStream = new FileInputStream(System.getProperty("config.properties"))
		try (FileInputStream inputStream = new FileInputStream(System.getProperty("config.properties"))) {
			properties.load(inputStream);
		} catch (IOException e) {
			invalidFileLogger.error("error : " + e);
		}
	}

	public String getProcessorType() {
		if (properties == null || !properties.containsKey("processor.type")) {
			invalidFileLogger.error("properties가 null이거나 processor.type키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 processor.type키가 없습니다.");
		}
		return properties.get("processor.type").toString();
	}

	public String getDatafilePath() {
		if (properties == null || !properties.containsKey("datafile.path")) {
			invalidFileLogger.error("properties가 null이거나 datafile.path키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 datafile.path키가 없습니다.");
		}
		return properties.get("datafile.path").toString();
	}

	public String getUpdateIndexesString() {
		if (properties == null || !properties.containsKey("data.updateindexes")) {
			invalidFileLogger.error("properties가 null이거나 data.updateindexes키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 data.updateindexes키가 없습니다.");
		}
		return properties.get("data.updateindexes").toString();
	}

	public int getDeleteIndexMin() {
		if (properties == null || !properties.containsKey("data.deleteindex.min")) {
			invalidFileLogger.error("properties가 null이거나 data.deleteindex.min키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 data.deleteindex.min키가 없습니다.");
		}
		return Integer.parseInt(properties.get("data.deleteindex.min").toString());
	}

	public int getDeleteIndexMax() {
		if (properties == null || !properties.containsKey("data.deleteindex.max")) {
			invalidFileLogger.error("properties가 null이거나 data.deleteindex.max키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 data.deleteindex.max키가 없습니다.");
		}
		return Integer.parseInt(properties.get("data.deleteindex.max").toString());
	}

	public String getLogFilePath() {
		if (properties == null || !properties.containsKey("logfile.path")) {
			invalidFileLogger.error("properties가 null이거나 logfile.path키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 logfile.path키가 없습니다.");
		}
		return properties.get("logfile.path").toString();
	}

	public String getDbUrl() {
		if (properties == null || !properties.containsKey("db.url")) {
			invalidFileLogger.error("properties가 null이거나 db.url키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 db.url키가 없습니다.");
		}
		return properties.get("db.url").toString();
	}

	public String getDbUserId() {
		if (properties == null || !properties.containsKey("db.userid")) {
			invalidFileLogger.error("properties가 null이거나 db.userid키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 db.userid키가 없습니다.");
		}
		return properties.get("db.userid").toString();
	}

	public String getDbPw() {
		if (properties == null || !properties.containsKey("db.pw")) {
			invalidFileLogger.error("properties가 null이거나 db.pw키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 db.pw키가 없습니다.");
		}
		return properties.get("db.pw").toString();
	}

	public String getDbTableName() {
		if (properties == null || !properties.containsKey("db.tablename")) {
			invalidFileLogger.error("properties가 null이거나 db.tablename키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 db.tablename키가 없습니다.");
		}
		return properties.get("db.tablename").toString();
	}

	public int getThreadCount() {
		if (properties == null || !properties.containsKey("thread.count")) {
			invalidFileLogger.error("properties가 null이거나 thread.count키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 thread.count키가 없습니다.");
		}
		return Integer.parseInt(properties.get("thread.count").toString());
	}
}
