package kr.co.torpedo.dataprocessor.processor.db.mybatis;

import java.util.ArrayList;

import kr.co.torpedo.dataprocessor.domain.User;

public interface UserDAO {
	public ArrayList<User> selectUserList();
	public void truncateTable();
	public void insertUserToDB(User user);
}
