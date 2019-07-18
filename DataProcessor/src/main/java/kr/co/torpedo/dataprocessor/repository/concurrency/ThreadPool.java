package kr.co.torpedo.dataprocessor.repository.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.repository.RepositoryHandler;

public class ThreadPool {
	private static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);
	private RepositoryHandler handler;
	private RunnableThread rt;
	private ExecutorService exService;

	public void setHandler(RepositoryHandler handler) {
		this.handler = handler;
	}

	public void start() {
		int threadCount = handler.getThreadCount();
		int min, max;
		int processDataCount = (handler.getJsonParser().getJsonArray().size() / threadCount);

		exService = Executors.newFixedThreadPool(threadCount);
		for (int i = 1; i <= threadCount; i++) {
			max = getMaxIndex(i, threadCount, processDataCount);
			min = max - processDataCount;

			rt = new RunnableThread();
			rt.setRepository(handler.getUserRepository());
			rt.setJsonArray(handler.getJsonParser().getJsonArray());
			rt.setMaxIndex(max);
			rt.setMinIndex(min);
			exService.execute(rt);
		}
		exService.shutdown();
		while (!exService.isTerminated()) {
			logger.info("Thread Pool not finish");
		}
	}

	private int getMaxIndex(int index, int threadCount, int processDataCount) {
		if (threadCount % 2 != 0 && threadCount != 1
				&& (handler.getJsonParser().getJsonArray().size() % threadCount) != 0) {
			if (index == threadCount) {
				return ((index * processDataCount) + 1);
			}
		} 
		return (index * processDataCount);
	}
}
