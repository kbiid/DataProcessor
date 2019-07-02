package kr.co.torpedo.dataprocessor.repository.memory;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class MemoryRepository extends UserRepository {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(MemoryRepository.class);
	private HashMap<Integer, User> userHashMap;

	public MemoryRepository() {
		userHashMap = new HashMap<>();
	}
	
	@Override
	public void update(int index) {
		if (userHashMap.containsKey(index)) {
			userHashMap.get(index).setEmail("aa@naver.com");
		}
	}

	@Override
	public void delete(int index) {
		if (userHashMap.containsKey(index)) {
			userHashMap.remove(index);
		}
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("MemoryProcessor save data start!");
		userHashMap.put(user.getId(), user);
	}

	@Override
	public void writeLog() {
		invalidFileLogger.info("MemoryProcessor saved data set to list start!");

		for (int i : userHashMap.keySet()) {
			jsonParser.marshal(userHashMap.get(i));
		}
	}

	@Override
	public void truncate() {
		userHashMap.clear();
	}
}
