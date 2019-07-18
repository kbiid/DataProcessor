package kr.co.torpedo.dataprocessor.repository.db.hibernate;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import kr.co.torpedo.dataprocessor.domain.User;

public class HibernateConnectionFactory {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	static {
//		String path = "src/main/resources/hibernate/hibernate.cfg.xml";
		String path = "/home/kbiid/Dataprocessor/dist/conf/hibernate/hibernate.cfg.xml";
		Configuration configuration = new Configuration().configure(new File(path));
		configuration.addAnnotatedClass(User.class);
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
