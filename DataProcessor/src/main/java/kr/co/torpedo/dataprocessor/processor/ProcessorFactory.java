package kr.co.torpedo.dataprocessor.processor;

import kr.co.torpedo.dataprocessor.processor.db.hibernate.HibernateProcessor;
import kr.co.torpedo.dataprocessor.processor.db.jdbc.JDBCProcessor;
import kr.co.torpedo.dataprocessor.processor.db.jdbc.JDBCProcessorWithCP;
import kr.co.torpedo.dataprocessor.processor.db.mybatis.MYBATISProcessor;
import kr.co.torpedo.dataprocessor.processor.memory.MemoryProcessor;

public class ProcessorFactory {
	public static ProcessorCommon createProcessor(String str) {
		ProcessorCommon processor = null;
		switch (str.toLowerCase()) {
		case "memory":
			processor = new MemoryProcessor();
			break;

		case "jdbc":
			processor = new JDBCProcessor();
			break;

		case "mybatis":
			processor = new MYBATISProcessor();
			break;

		case "hiberante":
			processor = new HibernateProcessor();
			break;

		case "jdbcp":
			processor = new JDBCProcessorWithCP();
			break;

		default:
			processor = new MemoryProcessor();
			break;
		}
		return processor;
	}
}
