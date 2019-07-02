package kr.co.torpedo.dataprocessor.processor;

import kr.co.torpedo.dataprocessor.processor.db.hibernate.HibernateRepository;
import kr.co.torpedo.dataprocessor.processor.db.jdbc.JDBCRepository;
import kr.co.torpedo.dataprocessor.processor.db.jdbc.JDBCRepositoryWithCP;
import kr.co.torpedo.dataprocessor.processor.db.mybatis.MYBATISRepository;
import kr.co.torpedo.dataprocessor.processor.memory.MemoryRepository;

public class RepositoryFactory {
	public static UserRepository createProcessor(String str) {
		UserRepository processor = null;
		switch (str.toLowerCase()) {
		case "memory":
			processor = new MemoryRepository();
			break;

		case "jdbc":
			processor = new JDBCRepository();
			break;

		case "mybatis":
			processor = new MYBATISRepository();
			break;

		case "hiberante":
			processor = new HibernateRepository();
			break;

		case "jdbcp":
			processor = new JDBCRepositoryWithCP();
			break;

		default:
			processor = new MemoryRepository();
			break;
		}
		return processor;
	}
}
