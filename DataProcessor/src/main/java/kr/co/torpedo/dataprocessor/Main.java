package kr.co.torpedo.dataprocessor;

import java.io.File;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.repository.JSONParser;
import kr.co.torpedo.dataprocessor.repository.RepositoryFactory;
import kr.co.torpedo.dataprocessor.repository.RepositoryHandler;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class Main {
	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		JSONParser jsonParser = new JSONParser();
		jsonParser.setLogFile(getFile(configReader.getLogFilePath()));
		jsonParser.setDataFile(getFile(configReader.getDatafilePath()));

		UserRepository repository = RepositoryFactory.createProcessor(configReader.getProcessorType());
		repository.setJsonParser(jsonParser);
		RepositoryHandler handler = new RepositoryHandler();
		handler.setConfigReader(configReader);
		handler.setUserRepository(repository);

		handler.setDBData();
		handler.readData();
		handler.truncate();
		handler.insert();
		handler.writeLog();

		handler.setIndexArray();
		handler.setMinMaxIndex();
		handler.update();
		handler.delete();

		handler.writeLog();
	}

	public static File getFile(String path) {
		return new File(path);
	}
}
