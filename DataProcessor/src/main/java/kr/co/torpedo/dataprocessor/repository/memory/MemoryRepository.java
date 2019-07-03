package kr.co.torpedo.dataprocessor.repository.memory;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.JSONParser;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class MemoryRepository extends UserRepository {
	private static final Logger logger = LoggerFactory.getLogger(MemoryRepository.class);
	private HashMap<Integer, User> userHashMap;

	public MemoryRepository() {
		userHashMap = new HashMap<>();
	}

	@Override
	public void update(int key) {
		logger.info("MemoryProcessor update start!");
		if (userHashMap.containsKey(key)) {
			userHashMap.get(key).setEmail("aa@naver.com");
		}
	}

	@Override
	public void delete(int key) {
		logger.info("MemoryProcessor delete start!");
		if (userHashMap.containsKey(key)) {
			userHashMap.remove(key);
		}
	}

	@Override
	public void save(User user) {
		logger.info("MemoryProcessor save start!");
		userHashMap.put(user.getId(), user);
	}

	@Override
	public void writeLog(JSONParser jsonParser) {
		logger.info("MemoryProcessor writeLog start!");
		for (int i : userHashMap.keySet()) {
			jsonParser.marshal(userHashMap.get(i));
		}
	}

	@Override
	public void truncate() {
		logger.info("MemoryProcessor truncate start!");
		userHashMap.clear();
	}

	@Override
	public void close() {
	}
}
