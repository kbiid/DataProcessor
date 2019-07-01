package kr.co.torpedo.dataprocessor.processor.memory;

import java.util.HashMap;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class MemoryProcessor extends ProcessorCommon {
	private HashMap<Integer, User> userHashMap;

	public MemoryProcessor() {
		super();
		userHashMap = new HashMap<>();
	}

	public HashMap<Integer, User> getUserHashMap() {
		return userHashMap;
	}

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
	public void saveData() {
		ProcessorCommon.invalidFileLogger.info("MemoryProcessor save data start!");
		for (User user : userList) {
			userHashMap.put(user.getId(), user);
		}
	}

	@Override
	public void setListSavedData() {
		ProcessorCommon.invalidFileLogger.info("MemoryProcessor saved data set to list start!");
		userList.clear();

		for (int i : userHashMap.keySet()) {
			userList.add(userHashMap.get(i));
		}
	}
}
