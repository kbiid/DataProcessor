package kr.co.torpedo.dataprocessor;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.manager.FileManager;
import kr.co.torpedo.dataprocessor.parser.JSONParser;
import kr.co.torpedo.dataprocessor.processor.memory.MemoryProcessor;

public class Main {

	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		FileManager fileManager = new FileManager();
		JSONParser jsonParser = new JSONParser();
		fileManager.makeDataFile(configReader.getDatafilePath());
		jsonParser.setLogFile(configReader.getLogFilePath());

		MemoryProcessor memory = new MemoryProcessor();
		memory.setConfigReader(configReader);
		memory.setFileManager(fileManager);
		memory.setJsonParser(jsonParser);

		memory.saveDataToList();
		memory.saveData();

		memory.setIndexArray();
		memory.setMinMaxIndex();
		memory.changeDataByIndexArray();
		memory.deleteDataByMinMaxIndex();

		memory.setListData();
		memory.saveData();
	}
}
