package kr.co.torpedo.dataprocessor.processor.db.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.torpedo.dataprocessor.domain.User;

@Mapper
public interface UserDAO {
	public ArrayList<User> selectAll();

	public void truncate();

	public void insert(User user);

	public void update(@Param("email") String email, @Param("id") int id);

	public void delete(int id);
}
