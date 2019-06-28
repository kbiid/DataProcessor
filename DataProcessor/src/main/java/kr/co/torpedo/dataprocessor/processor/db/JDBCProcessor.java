package kr.co.torpedo.dataprocessor.processor.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class JDBCProcessor extends ProcessorCommon {
	private String url, userId, dbPw, dbTableName, className;

	public JDBCProcessor() {
		className = "org.mariadb.jdbc.Driver";
	}

	private void truncateTable() {
		String sql = "TRUNCATE " + dbTableName;

		try {
			Class.forName(className);

			try (Connection con = DriverManager.getConnection(url, userId, dbPw);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.executeUpdate();
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void insertDataToDB() {
		truncateTable();
		String sql = "insert into " + dbTableName + " values(?,?,?,?,?,?)";
		try {
			Class.forName(className);

			try (Connection con = DriverManager.getConnection(url, userId, dbPw);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
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
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void selectDataFromDB() {
		userList.clear();
		String sql = "select * from " + dbTableName;
		User user = null;

		try {
			Class.forName(className);

			try (Connection con = DriverManager.getConnection(url, userId, dbPw);
					PreparedStatement pstmt = con.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
							rs.getString("email"), rs.getString("gender"), rs.getString("ip_address"));
					userList.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initDbData() {
		url = configReader.getDbUrl();
		userId = configReader.getDbUserId();
		dbPw = configReader.getDbPw();
		dbTableName = configReader.getDbTableName();
	}

	@Override
	public void changeDataByIndexArray() {
		for (int i = 0; i < indexArray.length; i++) {
			userList.get(indexArray[i]).setEmail("aa@naver.com");
		}
	}

	@Override
	public void deleteDataByMinMaxIndex() {
		for (int i = minIndex; i <= maxIndex; i++) {
			userList.remove(minIndex-1);
		}
	}

	@Override
	public void saveData() {
		initDbData();
		insertDataToDB();
	}

	@Override
	public void setListSavedData() {
		insertDataToDB();
		userList.clear();
		selectDataFromDB();
	}
}
