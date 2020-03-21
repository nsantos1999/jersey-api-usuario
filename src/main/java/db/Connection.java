package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {
	
	public Connection() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("users");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
	}
}
