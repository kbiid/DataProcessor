package kr.co.torpedo.dataprocessor.processor;

import java.util.HashMap;
import java.util.Iterator;

import kr.co.torpedo.dataprocessor.domain.User;

public class MemoryProcessor {
	private HashMap<Integer, User> userHashMap;
	
	public MemoryProcessor() {
		userHashMap = new HashMap<>();
	}

	public HashMap<Integer, User> getUserHashMap() {
		return userHashMap;
	}
	
}
