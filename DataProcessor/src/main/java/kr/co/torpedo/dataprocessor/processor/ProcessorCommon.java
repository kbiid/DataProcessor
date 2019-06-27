package kr.co.torpedo.dataprocessor.processor;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.manager.FileManager;
import kr.co.torpedo.dataprocessor.parser.JSONParser;

public abstract class ProcessorCommon {
	protected ConfigReader configReader;
	protected FileManager fileManager;
	protected JSONParser jsonParser;
	protected int[] indexArray;
	protected int minIndex,maxIndex;

	public void setJsonParser(JSONParser jsonParser) {
		this.jsonParser = jsonParser;
	}

	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public ConfigReader getConfigReader() {
		return configReader;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public JSONParser getJsonParser() {
		return jsonParser;
	}

	public void setMinMaxIndex() {
		minIndex = configReader.getDeleteIndexMin();
		maxIndex = configReader.getDeleteIndexMax();
	}
	
	public abstract void changeDataByIndexArray();
	
	public abstract void deleteDataByMinMaxIndex();
}
