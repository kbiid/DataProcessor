package kr.co.torpedo.dataprocessor.repository.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class JDBCRepository extends UserRepository {
	private static final Logger invalidFileLogger = LoggerFactory.getLogger(JDBCRepository.class);
	private String className;

	public JDBCRepository() {
		className = "org.mariadb.jdbc.Driver";
	}

	@Override
	public void update(int index) {
		invalidFileLogger.info("JDBCProcessor update data start!");
		String sql = "update " + tableName + " set email=? where id=?";

		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);

			pstmt.setString(1, "aa@naver.com");
			pstmt.setInt(2, index);
			pstmt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			invalidFileLogger.error("JDBCProcessor select data error" + e);
		}
	}

	@Override
	public void delete(int index) {
		invalidFileLogger.info("JDBCProcessor delete data start!");
		String sql = "delete from " + tableName + " where id=?";
		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.setInt(1, index);
			pstmt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			invalidFileLogger.error("JDBCProcessor select data error" + e);
		}
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("JDBCProcessor save data");
		invalidFileLogger.info("JDBCProcessor insert data to table start!");
		String sql = "insert into " + tableName + " values(?,?,?,?,?,?)";
		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, user.getFirst_name());
			pstmt.setString(3, user.getLast_name());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getGender());
			pstmt.setString(6, user.getIp_address());
			pstmt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			invalidFileLogger.error("JDBCProcessor insert data to table error: " + e);
			System.out.println(e);
		}
	}

	@Override
	public void writeLog() {
		invalidFileLogger.info("JDBCProcessor set list for savedData");
		invalidFileLogger.info("JDBCProcessor select data start!");
		String sql = "select * from " + tableName;
		User user = null;

		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			Class.forName(className);
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("gender"), rs.getString("ip_address"));
				jsonParser.marshal(user);
			}
		} catch (SQLException | ClassNotFoundException e) {
			invalidFileLogger.error("JDBCProcessor select data error" + e);
		}
	}

	@Override
	public void truncate() {
		invalidFileLogger.info("JDBCProcessor truncateTable start!");
		String sql = "TRUNCATE " + tableName;

		try (Connection con = DriverManager.getConnection(url, id, pwd);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException | ClassNotFoundException e) {
			invalidFileLogger.error("JDBCProcessor truncateTable error: " + e);
		}
	}
}
