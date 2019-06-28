package kr.co.torpedo.dataprocessor.processor;

import java.util.ArrayList;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.manager.FileManager;
import kr.co.torpedo.dataprocessor.parser.JSONParser;

public abstract class ProcessorCommon {
	protected ConfigReader configReader;
	protected FileManager fileManager;
	protected JSONParser jsonParser;
	protected ArrayList<User> userList;
	protected int[] indexArray;
	protected int minIndex, maxIndex;

	public ProcessorCommon() {
		userList = new ArrayList<>();
	}

	public ArrayList<User> getUserList() {
		return userList;
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

	public void readDataAndSetList() {
		if (!fileManager.checkDataFile()) {// 데이터 파일이 정상적으로 존재하는 경우
			// 에러 로그 추가해야됨
			try {
				throw new Exception("data file not exist!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		jsonParser.deSelialize(fileManager.getDataFile());
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

	public void writeDataToLogFile() {
		jsonParser.setUserList(userList);
		jsonParser.selialize();
	}
	
	public abstract void changeDataByIndexArray();

	public abstract void deleteDataByMinMaxIndex();

	public abstract void saveData();

	public abstract void setListSavedData();
}
