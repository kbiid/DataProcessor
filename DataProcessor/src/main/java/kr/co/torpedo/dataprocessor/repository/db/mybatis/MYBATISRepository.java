package kr.co.torpedo.dataprocessor.repository.db.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.JSONParser;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class MYBATISRepository extends UserRepository {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(MYBATISRepository.class);
	private SqlSession sqlSession;
	private UserDAO userDao;

	public MYBATISRepository() {
		sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(true);
		userDao = sqlSession.getMapper(UserDAO.class);
	}

	@Override
	public void update(int index) {
		invalidFileLogger.info("MTBATISProcessor update start!");
		userDao.update("aa@naver.com", index);
	}

	@Override
	public void delete(int index) {
		invalidFileLogger.info("MTBATISProcessor delete start!");
		userDao.delete(index);
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("MTBATISProcessor save start!");
		userDao.insert(user);
	}

	@Override
	public void writeLog(JSONParser jsonParser) {
		invalidFileLogger.info("MTBATISProcessor writeLog start!");
		ArrayList<User> list = userDao.selectAll();
		for (User user : list) {
			jsonParser.marshal(user);
		}
	}

	@Override
	public void truncate() {
		invalidFileLogger.info("MTBATISProcessor truncate start!");
		userDao.truncate();
	}
}
