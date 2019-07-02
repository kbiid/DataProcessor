package kr.co.torpedo.dataprocessor.processor.db.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.Processor;

public class MYBATISProcessor extends Processor {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(MYBATISProcessor.class);
	private SqlSession sqlSession;
	private UserDAO userDao;

	@Override
	public void changeDataByIndexArray() {
		for (int i = 0; i < indexArray.length; i++) {
			userDao.updateDB("aa@naver.com", indexArray[i]);
		}
	}

	@Override
	public void deleteDataByMinMaxIndex() {
		for (int i = minIndex; i <= maxIndex; i++) {
			userDao.deleteData(i);
		}
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("MTBATISProcessor saveData start!");
		userDao.insertUserToDB(user);
	}

	@Override
	public void savedDataWriteLog() {
		invalidFileLogger.info("MTBATISProcessor setListSavedData start!");
		ArrayList<User> list = userDao.selectUserList();
		for (User user : list) {
			jsonParser.marshal(user);
		}
	}

	@Override
	public void clearDB() {
		userDao.truncateTable();
	}

	@Override
	public void initDB() {
		sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(true);
		userDao = sqlSession.getMapper(UserDAO.class);
	}
}
