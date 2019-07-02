package kr.co.torpedo.dataprocessor.repository.db.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
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
		userDao.update("aa@naver.com", index);
	}

	@Override
	public void delete(int index) {
		userDao.delete(index);
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("MTBATISProcessor saveData start!");
		userDao.insert(user);
	}

	@Override
	public void writeLog() {
		invalidFileLogger.info("MTBATISProcessor setListSavedData start!");
		ArrayList<User> list = userDao.selectAll();
		for (User user : list) {
			jsonParser.marshal(user);
		}
	}

	@Override
	public void truncate() {
		userDao.truncate();
	}
}
