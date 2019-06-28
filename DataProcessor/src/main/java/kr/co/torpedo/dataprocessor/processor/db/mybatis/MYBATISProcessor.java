package kr.co.torpedo.dataprocessor.processor.db.mybatis;

import org.apache.ibatis.session.SqlSession;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class MYBATISProcessor extends ProcessorCommon {
	private SqlSession sqlSession;
	private UserDAO userDao;

	public MYBATISProcessor() {
		sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(true);
		userDao = sqlSession.getMapper(UserDAO.class);
	}

	@Override
	public void changeDataByIndexArray() {
		for (int i = 0; i < indexArray.length; i++) {
			userList.get(indexArray[i] - 1).setEmail("aa@naver.com");
		}
	}

	@Override
	public void deleteDataByMinMaxIndex() {
		for (int i = minIndex; i <= maxIndex; i++) {
			userList.remove(minIndex - 1);
		}
	}

	@Override
	public void saveData() {
		userDao.truncateTable();
		for (User user : userList) {
			userDao.insertUserToDB(user);
		}

	}

	@Override
	public void setListSavedData() {
		saveData();
		userList.clear();
		userList = userDao.selectUserList();
	}
}
