package kr.co.torpedo.dataprocessor.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private Properties properties;

	public ConfigReader() {
		properties = new Properties();
		loadProp();
	}

	public Properties getProperties() {
		return properties;
	}

	private void loadProp() {
		try (InputStream inputStream = getClass().getResourceAsStream("config.properties") ) {
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

	public int getDatafileDir() {
		if (properties == null || !properties.containsKey("datafile.dir")) {
//			ContentWriter.invalidFileLogger.error("properties가 null이거나 datafile.dir키가 없습니다.");
			throw new NullPointerException("properties가 null이거나 datafile.dir키가 없습니다.");
		}
		return Integer.parseInt(properties.get("datafile.dir").toString());
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
}
