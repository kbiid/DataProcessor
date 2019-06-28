package kr.co.torpedo.dataprocessor.processor.db.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.processor.ProcessorCommon;

public class HibernateProcessor extends ProcessorCommon {
	private SessionFactory sessionFactory;
	private Session session;

	public HibernateProcessor() {
		sessionFactory = HibernateConnectionFactory.getSessionFactory();
	}

	private void insertUserToDB() {
		session = sessionFactory.openSession();
		session.createQuery("delete from user_tb").executeUpdate();

		for (User user : userList) {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		}
		session.close();
		sessionFactory.close();
	}

	private void selectUserFromDB() {
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
		insertUserToDB();
	}

	@Override
	public void setListSavedData() {
		insertUserToDB();
		userList.clear();
		selectUserFromDB();
	}

}
