package kr.co.torpedo.dataprocessor.repository.concurrency;

import kr.co.torpedo.dataprocessor.repository.RepositoryHandler;

public class RunnableThread implements Runnable {
	private RepositoryHandler handler;

	public void setHandler(RepositoryHandler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
	}

}
