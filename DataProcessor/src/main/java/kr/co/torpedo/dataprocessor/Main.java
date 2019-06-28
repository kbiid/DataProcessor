package kr.co.torpedo.dataprocessor;

import kr.co.torpedo.dataprocessor.config.ConfigReader;
import kr.co.torpedo.dataprocessor.manager.FileManager;
import kr.co.torpedo.dataprocessor.parser.JSONParser;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;
import kr.co.torpedo.dataprocessor.processor.ProcessorFactory;
import kr.co.torpedo.dataprocessor.processor.memory.MemoryProcessor;
import kr.co.torpedo.dataprocessor.type.ProcessorId;

public class Main {

	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		FileManager fileManager = new FileManager();
		JSONParser jsonParser = new JSONParser();
		fileManager.makeDataFile(configReader.getDatafilePath());
		jsonParser.setLogFile(configReader.getLogFilePath());

		ProcessorCommon processor = ProcessorFactory.createProcessor(ProcessorId.JDBC);
		processor.setConfigReader(configReader);
		processor.setFileManager(fileManager);
		processor.setJsonParser(jsonParser);
		
		processor.readDataAndSetList();
		processor.saveData();
		processor.writeDataToLogFile();
		
		processor.setIndexArray();
		processor.setMinMaxIndex();
		processor.changeDataByIndexArray();
		processor.deleteDataByMinMaxIndex();
		
		processor.setListSavedData();
		processor.writeDataToLogFile();
		
//		MemoryProcessor memory = new MemoryProcessor();
//		memory.setConfigReader(configReader);
//		memory.setFileManager(fileManager);
//		memory.setJsonParser(jsonParser);
//
//		memory.readDataAndSetList();
//		memory.saveData();
//		memory.writeDataToLogFile();
//
//		memory.setIndexArray();
//		memory.setMinMaxIndex();
//		memory.changeDataByIndexArray();
//		memory.deleteDataByMinMaxIndex();
//
//		memory.setListSavedData();
//		memory.writeDataToLogFile();
	}
}
