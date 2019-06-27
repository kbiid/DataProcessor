package kr.co.torpedo.dataprocessor.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import kr.co.torpedo.dataprocessor.domain.User;

public class JSONParser {
	private User user;
	private JsonArray jsonArray;
	private ArrayList<User> userList;
	private File logFile;

	public JSONParser() {
		userList = new ArrayList<>();
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public void selialize() {
		JsonObject jObj = null;
		for (User user : userList) {
			jObj = new JsonObject();
			jObj.addProperty("id", user.getId());
			jObj.addProperty("first_name", user.getFirst_name());
			jObj.addProperty("last_name", user.getLast_name());
			jObj.addProperty("email", user.getEmail());
			jObj.addProperty("gender", user.getGender());
			jObj.addProperty("ip_address", user.getIp_address());
			writeEmployee(jObj.toString());
		}
		userList.clear();
	}

	public void writeEmployee(String jsonStr) {
		String str = jsonStr;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			bw.write(str);
			bw.write("\r\n");
		} catch (IOException e) {
//			Parser.invalidFileLogger.error("JSONSerializer IOException : " + e);
		}
	}

	public void deSelialize(File file) {
		JsonParser parser = new JsonParser();
		try {
			Object obj = parser.parse(new FileReader(file));
			jsonArray = (JsonArray) obj;
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
//			Parser.invalidFileLogger.error("JSONDeSerializer Exception : " + e);
		}
	}

	public ArrayList<User> getUserList() {
		userList.clear();
		if (jsonArray == null) {

		}
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jobj = (JsonObject) jsonArray.get(i);
			int id = Integer.parseInt(jobj.get("id").toString());
			String firstName = jobj.get("first_name").toString();
			String lastName = jobj.get("last_name").toString();
			String email = jobj.get("email").toString();
			String gender = jobj.get("gender").toString();
			String ipAddress = jobj.get("ip_address").toString();
			user = new User(id, firstName, lastName, email, gender, ipAddress);

			userList.add(user);
		}
		return userList;
	}
}
