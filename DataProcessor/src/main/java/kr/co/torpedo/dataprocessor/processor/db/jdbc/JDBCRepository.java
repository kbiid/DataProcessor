package kr.co.torpedo.dataprocessor.processor.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.UserRepository;

public class JDBCRepository extends UserRepository {
	private static final Logger invalidFileLogger = LoggerFactory.getLogger(JDBCRepository.class);
	private String url, userId, dbPw, dbTableName, className;

	private void truncateTable() throws SQLException {
		invalidFileLogger.info("JDBCProcessor truncateTable start!");
		String sql = "TRUNCATE " + dbTableName;

		try (Connection con = DriverManager.getConnection(url, userId, dbPw);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			invalidFileLogger.error("JDBCProcessor truncateTable error: " + e);
		}
	}

	private void insertUserToDB(User user) {
		invalidFileLogger.info("JDBCProcessor insert data to table start!");
		String sql = "insert into " + dbTableName + " values(?,?,?,?,?,?)";
		try (Connection con = DriverManager.getConnection(url, userId, dbPw);
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
		}
	}

	private void selectAllUserAndWriteLog() throws SQLException {
		invalidFileLogger.info("JDBCProcessor select data start!");
		String sql = "select * from " + dbTableName;
		User user = null;

		try (Connection con = DriverManager.getConnection(url, userId, dbPw);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			Class.forName(className);
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("email"), rs.getString("gender"), rs.getString("ip_address"));
				jsonParser.marshal(user);
			}
		} catch (ClassNotFoundException e) {
			invalidFileLogger.error("JDBCProcessor select data error" + e);
		}
	}

	private void initDBData() {
		invalidFileLogger.info("JDBCProcessor initDBData");
		url = configReader.getDbUrl();
		userId = configReader.getDbUserId();
		dbPw = configReader.getDbPw();
		dbTableName = configReader.getDbTableName();
	}

	private void updateData(int index) {
		invalidFileLogger.info("JDBCProcessor update data start!");
		String sql = "update " + dbTableName + " set email=? where id=?";

		try (Connection con = DriverManager.getConnection(url, userId, dbPw);
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
	public void update() {
		for (int i = 0; i < indexArray.length; i++) {
			updateData(indexArray[i]);
		}
	}

	private void deleteData(int index) {
		invalidFileLogger.info("JDBCProcessor delete data start!");
		String sql = "delete from " + dbTableName + " where id=?";
		try (Connection con = DriverManager.getConnection(url, userId, dbPw);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			Class.forName(className);
			pstmt.setInt(1, index);
			pstmt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
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
		invalidFileLogger.info("JDBCProcessor save data");
		insertUserToDB(user);
	}

	@Override
	public void writeLog() {
		invalidFileLogger.info("JDBCProcessor set list for savedData");
		try {
			selectAllUserAndWriteLog();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessor setListSavedData Method error!");
		}
	}

	@Override
	public void truncate() {
		try {
			truncateTable();
		} catch (SQLException e) {
			invalidFileLogger.error("JDBCProcessor cleraDB Method error!" + e);
		}
	}

	@Override
	public void initDB() {
		className = "org.mariadb.jdbc.Driver";
		initDBData();
	}
}