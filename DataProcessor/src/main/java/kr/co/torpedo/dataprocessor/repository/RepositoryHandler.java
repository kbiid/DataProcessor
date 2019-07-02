package kr.co.torpedo.dataprocessor.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.config.ConfigReader;

public class RepositoryHandler {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(RepositoryHandler.class);
	private ConfigReader configReader;
	private UserRepository userRepository;
	private int[] indexArray;
	private int minIndex, maxIndex, threadCount;
	private JSONParser jsonParser;

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setJSONParser(JSONParser jsonParser) {
		this.jsonParser = jsonParser;
	}

	public void setDBData() {
		userRepository.setUrl(configReader.getDbUrl());
		userRepository.setId(configReader.getDbUserId());
		userRepository.setPwd(configReader.getDbPw());
		userRepository.setTableName(configReader.getDbTableName());
	}

	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setThreadCount() {
		threadCount = configReader.getThreadCount();
	}

	public void setMinMaxIndex() {
		minIndex = configReader.getDeleteIndexMin();
		maxIndex = configReader.getDeleteIndexMax();
	}

	public void setIndexArray() {
		String str = configReader.getUpdateIndexesString();
		String[] array = str.split(",");
		indexArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			indexArray[i] = Integer.parseInt(array[i]);
		}
	}

	public void update() {
		for (int i = 0; i < indexArray.length; i++) {
			userRepository.update(indexArray[i]);
		}
	}

	public void delete() {
		for (int i = minIndex; i <= maxIndex; i++) {
			userRepository.delete(i);
		}
	}

	public void insert() {
		userRepository.insert();
	}

	public void readData() {
		jsonParser.unmarshal();
	}

	public void truncate() {
		userRepository.truncate();
	}

	public void writeLog() {
		userRepository.writeLog();
	}
}
