package kr.co.torpedo.dataprocessor.repository;

import kr.co.torpedo.dataprocessor.repository.db.hibernate.HibernateRepository;
import kr.co.torpedo.dataprocessor.repository.db.jdbc.JDBCRepository;
import kr.co.torpedo.dataprocessor.repository.db.jdbc.JDBCRepositoryWithCP;
import kr.co.torpedo.dataprocessor.repository.db.mybatis.MYBATISRepository;
import kr.co.torpedo.dataprocessor.repository.memory.MemoryRepository;

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
