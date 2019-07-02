package kr.co.torpedo.dataprocessor.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.co.torpedo.dataprocessor.domain.User;

public abstract class UserRepository {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(UserRepository.class);
	protected JSONParser jsonParser;
	protected String url, id, pwd, tableName;

	public void setJsonParser(JSONParser jsonParser) {
		this.jsonParser = jsonParser;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void insert() {
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

	public void readData() {
		jsonParser.unmarshal();
	}

	public abstract void update(int index);

	public abstract void delete(int index);

	public abstract void save(User user);

	public abstract void writeLog();

	public abstract void truncate();
}
