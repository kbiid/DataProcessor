package kr.co.torpedo.dataprocessor.repository.concurrency;

import com.google.gson.JsonArray;

import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class RunnableThread implements Runnable {
	private UserRepository repository;
	private int minIndex, maxIndex;

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	public void setJsonArray(JsonArray array) {
		repository.setArray(array);
	}

	public void setMinIndex(int minIndex) {
		this.minIndex = minIndex;
	}

	public void setMaxIndex(int maxIndex) {
		this.maxIndex = maxIndex;
	}

	@Override
	public void run() {
		repository.insert(minIndex, maxIndex);
	}
}
