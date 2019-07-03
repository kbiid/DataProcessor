package kr.co.torpedo.dataprocessor.repository.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.JSONParser;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class JDBCRepositoryWithCP extends UserRepository {
	private static final Logger invalidFileLogger = LoggerFactory.getLogger(JDBCRepositoryWithCP.class);
	private Connection conn;

	public JDBCRepositoryWithCP() {
		try {
			conn = DBConnection.getConnection();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessorWithCP error : " + e);
		}
	}

	@Override
	public void update(int key) {
		invalidFileLogger.info("JDBCProcessor update start!");
		String sql = "update " + tableName + " set email=? where id=?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "aa@naver.com");
			pstmt.setInt(2, key);
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			invalidFileLogger.error("JDBCProcessor update error" + e1);
		}
	}

	@Override
	public void delete(int key) {
		invalidFileLogger.info("JDBCProcessor delete start!");
		String sql = "delete from " + tableName + " where id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, key);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessor delete error" + e);
		}
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("JDBCProcessorWithCP save start!");
		String sql = "insert into " + tableName + " values(?,?,?,?,?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, user.getFirst_name());
			pstmt.setString(3, user.getLast_name());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getGender());
			pstmt.setString(6, user.getIp_address());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessorWithCP save error: " + e);
		}
	}

	@Override
	public void writeLog(JSONParser jsonParser) {
		invalidFileLogger.info("JDBCProcessorWithCP writeLog start!");
		String sql = "select * from " + tableName;
		User user = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("gender"), rs.getString("ip_address"));
				jsonParser.marshal(user);
			}
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessorWithCP writeLog error: " + e);
		}
	}

	@Override
	public void truncate() {
		invalidFileLogger.info("JDBCProcessorWithCP truncate start!");
		String sql = "TRUNCATE " + tableName;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessorWithCP truncate error: " + e);
		}
	}

	@Override
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				invalidFileLogger.error("JDBCProcessorWithCP close error: " + e);
			}
		}
	}
}
