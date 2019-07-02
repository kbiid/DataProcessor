package kr.co.torpedo.dataprocessor.processor.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.UserRepository;

public class JDBCRepositoryWithCP extends UserRepository {
	private static final Logger invalidFileLogger = LoggerFactory.getLogger(JDBCRepositoryWithCP.class);
	private Connection conn;
	private String dbTableName;

	public JDBCRepositoryWithCP() {
		try {
			conn = DBConnection.getConnection();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessorWithCP error : " + e);
		}
	}

	private void truncateTable() {
		invalidFileLogger.info("JDBCProcessorWithCP truncateTable start!");
		String sql = "TRUNCATE " + dbTableName;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessorWithCP truncateTable error: " + e);
		}
	}

	private void insertUserToDB(User user) {
		invalidFileLogger.info("JDBCProcessorWithCP insert data to table start!");
		String sql = "insert into " + dbTableName + " values(?,?,?,?,?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, user.getFirst_name());
			pstmt.setString(3, user.getLast_name());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getGender());
			pstmt.setString(6, user.getIp_address());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessorWithCP insert data to table error: " + e);
		}
	}

	private void selectAllUserAndWriteLog() {
		invalidFileLogger.info("JDBCProcessorWithCP select data start!");
		String sql = "select * from " + dbTableName;
		User user = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("gender"), rs.getString("ip_address"));
				jsonParser.marshal(user);
			}
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessorWithCP select data error: " + e);
		}
	}

	private void updateData(int index) {
		invalidFileLogger.info("JDBCProcessor update data start!");
		String sql = "update " + dbTableName + " set email=? where id=?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "aa@naver.com");
			pstmt.setInt(2, index);
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			invalidFileLogger.error("JDBCProcessor select data error" + e1);
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < indexArray.length; i++) {
			updateData(indexArray[i]);
		}
	}

	private void deleteData(int index) {
		invalidFileLogger.info("JDBCProcessor delete data start!");
		String sql = "delete from " + dbTableName + " where id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, index);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessor select data error" + e);
		}
	}

	@Override
	public void delete() {
		for (int i = minIndex; i <= maxIndex; i++) {
			deleteData(i);
		}
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("JDBCProcessorWithCP save data");
		insertUserToDB(user);
	}

	@Override
	public void writeLog() {
		invalidFileLogger.info("JDBCProcessorWithCP set list for savedData");
		selectAllUserAndWriteLog();
	}

	@Override
	public void truncate() {
		truncateTable();
	}

	@Override
	public void initDB() {
		dbTableName = configReader.getDbTableName();
	}
}
