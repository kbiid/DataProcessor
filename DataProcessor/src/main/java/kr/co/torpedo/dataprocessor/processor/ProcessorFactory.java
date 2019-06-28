package kr.co.torpedo.dataprocessor.processor;

import kr.co.torpedo.dataprocessor.processor.db.JDBCProcessor;
import kr.co.torpedo.dataprocessor.processor.db.mybatis.MYBATISProcessor;
import kr.co.torpedo.dataprocessor.processor.memory.MemoryProcessor;
import kr.co.torpedo.dataprocessor.type.ProcessorId;

public class ProcessorFactory {
	public static ProcessorCommon createProcessor(ProcessorId processorId) {
		ProcessorCommon processor = null;
		
		switch (processorId) {
		case MEMORY:
			processor = new MemoryProcessor();
			break;
			
		case JDBC:
			processor = new JDBCProcessor();
			break;
			
		case MYBATIS:
			processor = new MYBATISProcessor();
			break;
			
		case HIBERNATE:
			processor = new MemoryProcessor();
			break;
			
		default:
			processor = new MemoryProcessor();
			break;
		}
		
		return processor;
	}
}
