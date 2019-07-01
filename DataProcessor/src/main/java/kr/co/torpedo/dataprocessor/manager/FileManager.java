package kr.co.torpedo.dataprocessor.manager;

import java.io.File;

import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class FileManager {
	private File dataFile;

	public void makeDataFile(String path) {
		dataFile = new File(path);
	}

	public boolean checkDataFile() {
		if (!dataFile.exists()) { // 파일이 없는 경우
			ProcessorCommon.invalidFileLogger.info("File exist");
			return false;
		} else {
			ProcessorCommon.invalidFileLogger.info("File exist");
			return true;
		}
	}

	public File getDataFile() {
		return dataFile;
	}
}
