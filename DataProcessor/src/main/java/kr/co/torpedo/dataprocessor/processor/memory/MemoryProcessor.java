package kr.co.torpedo.dataprocessor.processor.memory;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.Processor;

public class MemoryProcessor extends Processor {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(MemoryProcessor.class);
	private HashMap<Integer, User> userHashMap;

	@Override
	public void changeDataByIndexArray() {
		for (int i = 0; i < indexArray.length; i++) {
			if (userHashMap.containsKey(indexArray[i])) {
				userHashMap.get(indexArray[i]).setEmail("aa@naver.com");
			}
		}
	}

	@Override
	public void deleteDataByMinMaxIndex() {
		for (int i = minIndex; i <= maxIndex; i++) {
			if (userHashMap.containsKey(i)) {
				userHashMap.remove(i);
			}
		}
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("MemoryProcessor save data start!");
		userHashMap.put(user.getId(), user);
	}

	@Override
	public void savedDataWriteLog() {
		invalidFileLogger.info("MemoryProcessor saved data set to list start!");
		userList.clear();

		for (int i : userHashMap.keySet()) {
			jsonParser.marshal(userHashMap.get(i));
		}
	}

	@Override
	public void clearDB() {
	}

	@Override
	public void initDB() {
		userHashMap = new HashMap<>();
	}
}
