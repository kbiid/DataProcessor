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
		RepositoryHandler handler = new RepositoryHandler();
		handler.setConfigReader(configReader);
		handler.setUserRepository(repository);
		handler.setJSONParser(jsonParser);

		handler.setDBData();
		handler.setJsonArrayToRepository();
		handler.getJsonParser().unmarshal();
		handler.getUserRepository().truncate();
		handler.getUserRepository().insert();
		handler.writeLog();

		handler.setIndexArray();
		handler.setMinMaxIndex();
		handler.update();
		handler.delete();

		handler.writeLog();
		handler.getUserRepository().close();
	}

	public static File getFile(String path) {
		return new File(path);
	}
}
