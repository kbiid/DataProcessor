package kr.co.torpedo.dataprocessor.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader{
	private Properties properties;

	public ConfigReader() {
		properties = new Properties();
		loadProp();
	}

	public Properties getProperties() {
		return properties;
	}

	private void loadProp() {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String getProcessorType() {
		if (properties == null || !properties.containsKey("processor.type")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 processor.type키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 processor.type키가 없습니다.");
		}
		return properties.get("processor.type").toString();
	}

	public String getDatafilePath() {
		if (properties == null || !properties.containsKey("datafile.path")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 datafile.path키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 datafile.path키가 없습니다.");
		}
		return properties.get("datafile.path").toString();
	}

	public String getUpdateIndexesString() {
		if (properties == null || !properties.containsKey("data.updateindexes")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 data.updateindexes키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 data.updateindexes키가 없습니다.");
		}
		return properties.get("data.updateindexes").toString();
	}

	public int getDeleteIndexMin() {
		if (properties == null || !properties.containsKey("data.deleteindex.min")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 data.deleteindex.min키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 data.deleteindex.min키가 없습니다.");
		}
		return Integer.parseInt(properties.get("data.deleteindex.min").toString());
	}

	public int getDeleteIndexMax() {
		if (properties == null || !properties.containsKey("data.deleteindex.max")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 data.deleteindex.max키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 data.deleteindex.max키가 없습니다.");
		}
		return Integer.parseInt(properties.get("data.deleteindex.max").toString());
	}

	public String getLogFilePath() {
		if (properties == null || !properties.containsKey("logfile.path")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 logfile.path키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 logfile.path키가 없습니다.");
		}
		return properties.get("logfile.path").toString();
	}

	public String getDbUrl() {
		if (properties == null || !properties.containsKey("db.url")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 db.url키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 db.url키가 없습니다.");
		}
		return properties.get("db.url").toString();
	}

	public String getDbUserId() {
		if (properties == null || !properties.containsKey("db.userid")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 db.userid키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 db.userid키가 없습니다.");
		}
		return properties.get("db.userid").toString();
	}

	public String getDbPw() {
		if (properties == null || !properties.containsKey("db.pw")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 db.pw키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 db.pw키가 없습니다.");
		}
		return properties.get("db.pw").toString();
	}

	public String getDbTableName() {
		if (properties == null || !properties.containsKey("db.tablename")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 db.tablename키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 db.tablename키가 없습니다.");
		}
		return properties.get("db.tablename").toString();
	}
}
