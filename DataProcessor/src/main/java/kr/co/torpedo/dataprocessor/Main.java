package kr.co.torpedo.dataprocessor;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.manager.FileManager;
import kr.co.torpedo.dataprocessor.processor.JSONParser;
import kr.co.torpedo.dataprocessor.processor.Processor;
import kr.co.torpedo.dataprocessor.processor.ProcessorFactory;

public class Main {
	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		FileManager fileManager = new FileManager();
		JSONParser jsonParser = new JSONParser();
		fileManager.makeDataFile(configReader.getDatafilePath());
		jsonParser.setLogFile(configReader.getLogFilePath());

		Processor processor = ProcessorFactory.createProcessor(configReader.getProcessorType());
		processor.setConfigReader(configReader);
		processor.setFileManager(fileManager);
		processor.setJsonParser(jsonParser);

		processor.initDB();
		processor.readData();
		processor.clearDB();
		processor.insertDB();
		processor.savedDataWriteLog();

		processor.setIndexArray();
		processor.setMinMaxIndex();
		processor.changeDataByIndexArray();
		processor.deleteDataByMinMaxIndex();

		processor.savedDataWriteLog();
	}
}
