package kr.co.torpedo.dataprocessor.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.db.jdbc.JDBCRepository;

public class JSONParser {
	private static final Logger logger = LoggerFactory.getLogger(JSONParser.class);
	private JsonArray jsonArray;
	private File logFile, dataFile;

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public File getLogFile() {
		return logFile;
	}

	public JsonArray getJsonArray() {
		return jsonArray;
	}

	public void marshal(User user) {
		logger.info("JSONSerializer marshal");
		JsonObject jObj = null;
		jObj = new JsonObject();
		jObj.addProperty("id", user.getId());
		jObj.addProperty("first_name", user.getFirstName());
		jObj.addProperty("last_name", user.getLastName());
		jObj.addProperty("email", user.getEmail());
		jObj.addProperty("gender", user.getGender());
		jObj.addProperty("ip_address", user.getIpAddress());
		jObj.addProperty("date", user.getIpAddress());
		writeEmployee(jObj.toString());
	}

	private void writeEmployee(String jsonStr) {
		String str = jsonStr;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			bw.write(str);
			bw.write("\r\n");
		} catch (IOException e) {
			logger.error("JSONSerializer IOException : " + e);
			e.printStackTrace();
		}
	}

	public void unmarshal() {
		logger.info("JSONSerializer unmarshal");
		JsonParser parser = new JsonParser();
		try {
			Object obj = parser.parse(new FileReader(dataFile));
			jsonArray = (JsonArray) obj;
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			logger.error("JSONDeSerializer Exception : " + e);
		}
	}
}
