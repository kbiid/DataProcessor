package kr.co.torpedo.dataprocessor.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import kr.co.torpedo.dataprocessor.domain.User;

public class JSONParser {
	private JsonArray jsonArray;
	private File logFile;

	public void setLogFile(String path) {
		logFile = new File(path);
	}

	public JsonArray getJsonArray() {
		return jsonArray;
	}

	public void marshal(User user) {
		JsonObject jObj = null;
		jObj = new JsonObject();
		jObj.addProperty("id", user.getId());
		jObj.addProperty("first_name", user.getFirst_name());
		jObj.addProperty("last_name", user.getLast_name());
		jObj.addProperty("email", user.getEmail());
		jObj.addProperty("gender", user.getGender());
		jObj.addProperty("ip_address", user.getIp_address());
		writeEmployee(jObj.toString());
	}

	private void writeEmployee(String jsonStr) {
		String str = jsonStr;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			bw.write(str);
			bw.write("\r\n");
		} catch (IOException e) {
			Processor.invalidFileLogger.error("JSONSerializer IOException : " + e);
			e.printStackTrace();
		}
	}

	public void unmarshal(File file) {
		JsonParser parser = new JsonParser();
		try {
			Object obj = parser.parse(new FileReader(file));
			jsonArray = (JsonArray) obj;
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			Processor.invalidFileLogger.error("JSONDeSerializer Exception : " + e);
		}
	}
}
