package kr.co.torpedo.dataprocessor.processor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.manager.FileManager;

public abstract class Processor {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(Processor.class);
	protected ConfigReader configReader;
	protected FileManager fileManager;
	protected JSONParser jsonParser;
	protected ArrayList<User> userList;
	protected int[] indexArray;
	protected int minIndex, maxIndex;

	public Processor() {
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

	public void readData() {
		if (!fileManager.checkDataFile()) {// 데이터 파일이 정상적으로 존재하는 경우
			try {
				throw new Exception("data file not exist!");
			} catch (Exception e) {
				invalidFileLogger.error("data file not exist!");
			}
		}
		jsonParser.unmarshal(fileManager.getDataFile());
	}

	public void setIndexArray() {
		String str = configReader.getUpdateIndexesString();
		String[] array = str.split(",");
		indexArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			indexArray[i] = Integer.parseInt(array[i]);
		}
	}

	public void insertDB() {
		User user;

		JsonArray array = jsonParser.getJsonArray();
		for (int i = 0; i < array.size(); i++) {
			JsonObject jobj = (JsonObject) array.get(i);
			int id = Integer.parseInt(jobj.get("id").toString());
			String firstName = jobj.get("first_name").toString();
			String lastName = jobj.get("last_name").toString();
			String email = jobj.get("email").toString();
			String gender = jobj.get("gender").toString();
			String ipAddress = jobj.get("ip_address").toString();
			user = new User(id, firstName, lastName, email, gender, ipAddress);

			save(user);
		}
	}

	public abstract void changeDataByIndexArray();

	public abstract void deleteDataByMinMaxIndex();

	public abstract void save(User user);

	public abstract void savedDataWriteLog();

	public abstract void clearDB();

	public abstract void initDB();
}
