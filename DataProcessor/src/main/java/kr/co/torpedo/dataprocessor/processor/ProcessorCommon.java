package kr.co.torpedo.dataprocessor.processor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.manager.FileManager;

public abstract class ProcessorCommon {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger("log.invalid");
	protected ConfigReader configReader;
	protected FileManager fileManager;
	protected JSONParser jsonParser;
	protected ArrayList<User> userList;
	protected int[] indexArray;
	protected int minIndex, maxIndex;

	public ProcessorCommon() {
		userList = new ArrayList<>();
	}

	public void setJsonParser(JSONParser jsonParser) {
		this.jsonParser = jsonParser;
	}

	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public void setMinMaxIndex() {
		minIndex = configReader.getDeleteIndexMin();
		maxIndex = configReader.getDeleteIndexMax();
	}

	public void readDataAndSetList() {
		if (!fileManager.checkDataFile()) {// 데이터 파일이 정상적으로 존재하는 경우
			try {
				throw new Exception("data file not exist!");
			} catch (Exception e) {
				ProcessorCommon.invalidFileLogger.error("data file not exist!");
				e.printStackTrace();
			}
		}
		jsonParser.unmarshalData(fileManager.getDataFile());
		userList = jsonParser.getUserList();
	}

	public void setIndexArray() {
		String str = configReader.getUpdateIndexesString();
		String[] array = str.split(",");
		indexArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			indexArray[i] = Integer.parseInt(array[i]);
		}
	}

//	public void writeDataToLogFile() {
//		jsonParser.setUserList(userList);
//		jsonParser.marshalData();
//	}

	public abstract void changeDataByIndexArray();

	public abstract void deleteDataByMinMaxIndex();

	public abstract void saveData();

	public abstract void savedDataWriteLog();

	public abstract void clearDB();
}
