package kr.co.torpedo.dataprocessor.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.domain.User;

public abstract class UserRepository {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(UserRepository.class);
	protected ConfigReader configReader;
	protected JSONParser jsonParser;
	protected int[] indexArray;
	protected int minIndex, maxIndex;

	public void setJsonParser(JSONParser jsonParser) {
		this.jsonParser = jsonParser;
	}

	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	public void setMinMaxIndex() {
		minIndex = configReader.getDeleteIndexMin();
		maxIndex = configReader.getDeleteIndexMax();
	}

	public void readData() {
		jsonParser.unmarshal();
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

	public abstract void update();

	public abstract void delete();

	public abstract void save(User user);

	public abstract void savedDataWriteLog();

	public abstract void truncate();

	public abstract void initDB();
}
