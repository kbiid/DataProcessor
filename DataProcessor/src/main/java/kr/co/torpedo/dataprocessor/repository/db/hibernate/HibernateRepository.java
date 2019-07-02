package kr.co.torpedo.dataprocessor.repository.db.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class HibernateRepository extends UserRepository {
	public static final Logger invalidFileLogger = LoggerFactory.getLogger(HibernateRepository.class);
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction tx;

	public HibernateRepository() {
		sessionFactory = HibernateConnectionFactory.getSessionFactory();
	}

	@Override
	public void insert() {
		session = sessionFactory.openSession();
		User user;

		JsonArray array = jsonParser.getJsonArray();
		tx = session.beginTransaction();
		for (int i = 0; i < array.size(); i++) {
			JsonObject jobj = (JsonObject) array.get(i);
			int id = Integer.parseInt(jobj.get("id").toString());
			String firstName = jobj.get("first_name").toString();
			String lastName = jobj.get("last_name").toString();
			String email = jobj.get("email").toString();
			String gender = jobj.get("gender").toString();
			String ipAddress = jobj.get("ip_address").toString();
			user = new User(id, firstName, lastName, email, gender, ipAddress);

			session.save(user);
		}
		tx.commit();
		session.close();
	}

	private void truncateTable() {
		invalidFileLogger.info("HieranteProcessor truncateTable start!");
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.createQuery("delete from User").executeUpdate();
		tx.commit();
	}

	private void selectUserFromDB() {
		invalidFileLogger.info("HieranteProcessor selecteData start!");
		session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		criteriaQuery.from(User.class);
		List<User> list = session.createQuery(criteriaQuery).getResultList();
		for (User user : list) {
			jsonParser.marshal(user);
		}
	}

	@Override
	public void update(int index) {
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = (User) session.get(User.class, index);
		user.setEmail("aa@naver.com");
		session.update(user);
		tx.commit();
	}

	@Override
	public void delete(int index) {
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = (User) session.get(User.class, index);
		session.delete(user);
		tx.commit();
	}

	@Override
	public void save(User user) {
		invalidFileLogger.info("HieranteProcessor saveData method start!");
		session.save(user);
	}

	@Override
	public void writeLog() {
		invalidFileLogger.info("HieranteProcessor setListSavedData method start!");
		selectUserFromDB();
	}

	@Override
	public void truncate() {
		truncateTable();
	}
}
