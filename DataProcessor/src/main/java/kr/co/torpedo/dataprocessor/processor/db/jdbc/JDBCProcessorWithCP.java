package kr.co.torpedo.dataprocessor.processor.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class JDBCProcessorWithCP extends ProcessorCommon {
	private Connection conn;
	private String dbTableName;

	public JDBCProcessorWithCP() {
		try {
			conn = DBConnection.getConnection();
		} catch (SQLException e) {
			ProcessorCommon.invalidFileLogger.error("JDBCProcessorWithCP error : " + e);
			e.printStackTrace();
		}
	}

	private void truncateTable() {
		ProcessorCommon.invalidFileLogger.info("JDBCProcessorWithCP truncateTable start!");
		String sql = "TRUNCATE " + dbTableName;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			ProcessorCommon.invalidFileLogger.error("JDBCProcessorWithCP truncateTable error: " + e);
			e.printStackTrace();
		}
	}

	private void insertUserToDB() {
		truncateTable();
		ProcessorCommon.invalidFileLogger.info("JDBCProcessorWithCP insert data to table start!");
		String sql = "insert into " + dbTableName + " values(?,?,?,?,?,?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			for (User user : userList) {
				pstmt.setInt(1, user.getId());
				pstmt.setString(2, user.getFirst_name());
				pstmt.setString(3, user.getLast_name());
				pstmt.setString(4, user.getEmail());
				pstmt.setString(5, user.getGender());
				pstmt.setString(6, user.getIp_address());
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			ProcessorCommon.invalidFileLogger.error("JDBCProcessorWithCP insert data to table error: " + e);
			e.printStackTrace();
		}
	}

	private void selectUserList() {
		userList.clear();
		ProcessorCommon.invalidFileLogger.info("JDBCProcessorWithCP select data start!");
		String sql = "select * from " + dbTableName;
		User user = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("gender"), rs.getString("ip_address"));
				userList.add(user);
			}
		} catch (SQLException e) {
			ProcessorCommon.invalidFileLogger.error("JDBCProcessorWithCP select data error: " + e);
			e.printStackTrace();
		}
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
		ProcessorCommon.invalidFileLogger.info("JDBCProcessorWithCP save data");
		dbTableName = configReader.getDbTableName();
		insertUserToDB();
	}

	@Override
	public void setListSavedData() {
		ProcessorCommon.invalidFileLogger.info("JDBCProcessorWithCP set list for savedData");
		insertUserToDB();
		userList.clear();
		selectUserList();
	}
}
