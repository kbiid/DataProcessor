package kr.co.torpedo.dataprocessor.processor.db.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.torpedo.dataprocessor.domain.User;

@Mapper
public interface UserDAO {
	public ArrayList<User> selectUserList();

	public void truncateTable();

	public void insertUserToDB(User user);

	public void updateDB(@Param("email") String email, @Param("id") int id);

	public void deleteData(int id);
}
