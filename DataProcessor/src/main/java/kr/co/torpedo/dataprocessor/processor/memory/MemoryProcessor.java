package kr.co.torpedo.dataprocessor.processor.memory;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class MemoryProcessor extends ProcessorCommon {
	private HashMap<Integer, User> userHashMap;
	private ArrayList<User> userList;

	public MemoryProcessor() {
		userHashMap = new HashMap<>();
		userList = new ArrayList<>();
	}

	public HashMap<Integer, User> getUserHashMap() {
		return userHashMap;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void saveDataToHashMap() {
		if (!fileManager.checkDataFile()) {// 데이터 파일이 정상적으로 존재하는 경우
			// 에러 로그 추가해야됨
			try {
				throw new Exception("data file not exist!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		jsonParser.deSelialize(fileManager.getDataFile());
		userList = jsonParser.getUserList();

		for (User user : userList) {
			userHashMap.put(user.getId(), user);
		}
	}

	public void setIndexArray() {
		String str = configReader.getUpdateIndexesString();
		String[] array = str.split(",");
		indexArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			indexArray[i] = Integer.parseInt(array[i]);
		}
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

	public void setListByHashMap() {
		userList.clear();

		for (int i : userHashMap.keySet()) {
			userList.add(userHashMap.get(i));
		}
	}
}
