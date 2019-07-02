package kr.co.torpedo.dataprocessor.processor.db.mybatis;

import java.util.ArrayList;

import kr.co.torpedo.dataprocessor.domain.User;

public interface UserDAO {
	public ArrayList<User> selectUserList();

	public void truncateTable();

	public void insertUserToDB(User user);

	public void updateDB(String email, int id);

	public void deleteData(int id);
}
