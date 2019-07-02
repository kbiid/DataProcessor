package kr.co.torpedo.dataprocessor;

import java.io.File;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.processor.JSONParser;
import kr.co.torpedo.dataprocessor.processor.UserRepository;
import kr.co.torpedo.dataprocessor.processor.RepositoryFactory;

public class Main {
	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		JSONParser jsonParser = new JSONParser();
		jsonParser.setLogFile(getFile(configReader.getLogFilePath()));
		jsonParser.setDataFile(getFile(configReader.getDatafilePath()));

		UserRepository processor = RepositoryFactory.createProcessor(configReader.getProcessorType());
		processor.setConfigReader(configReader);
		processor.setJsonParser(jsonParser);

		processor.initDB();
		processor.readData();
		processor.truncate();
		processor.insertDB();
		processor.writeLog();

		processor.setIndexArray();
		processor.setMinMaxIndex();
		processor.update();
		processor.delete();

		processor.writeLog();
	}

	public static File getFile(String path) {
		return new File(path);
	}
}
