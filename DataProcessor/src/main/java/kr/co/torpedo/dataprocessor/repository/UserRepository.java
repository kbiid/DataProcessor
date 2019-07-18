package kr.co.torpedo.dataprocessor.repository;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.co.torpedo.dataprocessor.domain.User;

public abstract class UserRepository {
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	protected String url, id, pwd, tableName;
	private JsonArray array;

	public void setArray(JsonArray array) {
		this.array = array;
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
		logger.info("insert start!");
		User user;

		for (int i = 0; i < array.size(); i++) {
			JsonObject jobj = (JsonObject) array.get(i);
			int id = Integer.parseInt(jobj.get("id").toString());
			String firstName = jobj.get("first_name").toString();
			firstName = changeDoubleQuote(firstName);
			String lastName = jobj.get("last_name").toString();
			lastName = changeDoubleQuote(lastName);
			String email = jobj.get("email").toString();
			email = changeDoubleQuote(email);
			String gender = jobj.get("gender").toString();
			gender = changeDoubleQuote(gender);
			String ipAddress = jobj.get("ip_address").toString();
			ipAddress = changeDoubleQuote(ipAddress);
			String date = jobj.get("date").toString();
			date = changeDoubleQuote(date);
			date = date.replace("/", "-");
			user = new User(id, firstName, lastName, email, gender, ipAddress, Date.valueOf(date));

			save(user);
		}
	}

	public void insert(int min, int max) {
		logger.info("insert start!");
		User user;

		for (int i = min; i < max; i++) {
			JsonObject jobj = (JsonObject) array.get(i);
			int id = Integer.parseInt(jobj.get("id").toString());
			String firstName = jobj.get("first_name").toString();
			firstName = changeDoubleQuote(firstName);
			String lastName = jobj.get("last_name").toString();
			lastName = changeDoubleQuote(lastName);
			String email = jobj.get("email").toString();
			email = changeDoubleQuote(email);
			String gender = jobj.get("gender").toString();
			gender = changeDoubleQuote(gender);
			String ipAddress = jobj.get("ip_address").toString();
			ipAddress = changeDoubleQuote(ipAddress);
			String date = jobj.get("date").toString();
			date = changeDoubleQuote(date);
			date = date.replace("/", "-");
			user = new User(id, firstName, lastName, email, gender, ipAddress, Date.valueOf(date));
			synchronized (this) {
				save(user);
			}
		}
	}

	public String changeDoubleQuote(String str) {
		return str = str.replace("\"", "");
	}

	public abstract void save(User user);

	public abstract void update(int key);

	public abstract void delete(int key);

	public abstract void writeLog(JSONParser jsonParser);

	public abstract void truncate();

	public abstract void close();
}
