package kr.co.torpedo.dataprocessor.processor.db.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class HibernateProcessor extends ProcessorCommon {
	private SessionFactory sessionFactory;
	private Session session;

	public HibernateProcessor() {
		sessionFactory = HibernateConnectionFactory.getSessionFactory();
	}

	@SuppressWarnings("rawtypes")
	private void insertUserToDB() {
		ProcessorCommon.invalidFileLogger.info("HieranteProcessor insertData start!");
		session = sessionFactory.openSession();
		Transaction tx = null;
		Query query = null;
		truncateTable();

		for (User user : userList) {
			tx = session.beginTransaction();
			query = session.createSQLQuery("insert into user_tb values(?,?,?,?,?,?)");
			query.setParameter(1, user.getId());
			query.setParameter(2, user.getFirst_name());
			query.setParameter(3, user.getLast_name());
			query.setParameter(4, user.getEmail());
			query.setParameter(5, user.getGender());
			query.setParameter(6, user.getIp_address());
			query.executeUpdate();
			tx.commit();
		}
		session.close();
	}

	private void truncateTable() {
		ProcessorCommon.invalidFileLogger.info("HieranteProcessor truncateTable start!");
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.createQuery("delete from User").executeUpdate();
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	private void selectUserFromDB() {
		ProcessorCommon.invalidFileLogger.info("HieranteProcessor selecteData start!");
		userList.clear();
		session = sessionFactory.openSession();
		session.beginTransaction();

		List<User> list = (List<User>) session.createSQLQuery("select * from user_tb").addEntity(User.class).list();
		for (User user : list) {
			userList.add(user);
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
		ProcessorCommon.invalidFileLogger.info("HieranteProcessor saveData method start!");
		insertUserToDB();
	}

	@Override
	public void setListSavedData() {
		ProcessorCommon.invalidFileLogger.info("HieranteProcessor setListSavedData method start!");
		insertUserToDB();
		userList.clear();
		selectUserFromDB();
	}

}
