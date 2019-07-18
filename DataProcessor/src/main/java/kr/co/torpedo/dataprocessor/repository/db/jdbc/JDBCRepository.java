package kr.co.torpedo.dataprocessor.repository.db.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.JSONParser;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class JDBCRepository extends UserRepository {
	private static final Logger logger = LoggerFactory.getLogger(JDBCRepository.class);
	private String className;

	public JDBCRepository() {
		className = "org.mariadb.jdbc.Driver";
	}

	@Override
	public void update(int key) {
		logger.info("JDBCProcessor update start!");
		String sql = "update " + tableName + " set email=? where id=?";

		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.setString(1, "aa@naver.com");
			pstmt.setInt(2, key);
			pstmt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("JDBCProcessor update error" + e);
		}
	}

	@Override
	public void delete(int key) {
		logger.info("JDBCProcessor delete start!");
		String sql = "delete from " + tableName + " where id=?";
		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.setInt(1, key);
			pstmt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("JDBCProcessor delete error" + e);
		}
	}

	@Override
	public void save(User user) {
		logger.info("JDBCProcessor save start!");
		String sql = "insert into " + tableName + " values(?,?,?,?,?,?,?)";
		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getGender());
			pstmt.setString(6, user.getIpAddress());
			pstmt.setDate(7, user.getDate());
			pstmt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("JDBCProcessor save error: " + e);
			System.out.println(e);
		}
	}

	@Override
	public void writeLog(JSONParser jsonParser) {
		logger.info("JDBCProcessor writeLog start!!");
		String sql = "select * from " + tableName;
		User user = null;

		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			Class.forName(className);
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("gender"), rs.getString("ip_address"),
						Date.valueOf(rs.getString("date")));
				jsonParser.marshal(user);
			}
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("JDBCProcessor select data error" + e);
		}
	}

	@Override
	public void truncate() {
		logger.info("JDBCProcessor truncate start!");
		String sql = "TRUNCATE " + tableName;

		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("JDBCProcessor truncate error: " + e);
		}
	}

	@Override
	public void close() {
	}
}
