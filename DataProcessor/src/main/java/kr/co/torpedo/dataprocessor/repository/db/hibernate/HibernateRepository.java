package kr.co.torpedo.dataprocessor.repository.db.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.torpedo.dataprocessor.domain.User;
import kr.co.torpedo.dataprocessor.repository.JSONParser;
import kr.co.torpedo.dataprocessor.repository.UserRepository;

public class HibernateRepository extends UserRepository {
	private static final Logger logger = LoggerFactory.getLogger(HibernateRepository.class);
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction tx;

	public HibernateRepository() {
		sessionFactory = HibernateConnectionFactory.getSessionFactory();
	}

	@Override
	public void insert() {
		logger.info("HieranteProcessor insert start!");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		super.insert();
		tx.commit();
		session.close();
	}

	@Override
	public void insert(int min, int max) {
		logger.info("HieranteProcessor insert start!");
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		super.insert(min, max);
		tx.commit();
		session.close();
	}

	@Override
	public void update(int key) {
		logger.info("HieranteProcessor update start!");
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = (User) session.get(User.class, key);
		user.setEmail("aa@naver.com");
		session.update(user);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(int key) {
		logger.info("HieranteProcessor delete start!");
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = (User) session.get(User.class, key);
		session.delete(user);
		tx.commit();
		session.close();
	}

	@Override
	public void save(User user) {
		logger.info("HieranteProcessor save start!");
		session.save(user);
	}

	@Override
	public void writeLog(JSONParser jsonParser) {
		logger.info("HieranteProcessor writeLog start!");
		session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		criteriaQuery.from(User.class);
		List<User> list = session.createQuery(criteriaQuery).getResultList();
		for (User user : list) {
			jsonParser.marshal(user);
		}
		session.close();
	}

	@Override
	public void truncate() {
		logger.info("HieranteProcessor truncate start!");
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.createQuery("delete from User").executeUpdate();
		tx.commit();
		session.close();
	}

	@Override
	public void close() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
