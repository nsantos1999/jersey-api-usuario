package generics;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

public abstract class DAO<T>{
	@PersistenceContext
	protected EntityManager em;
	protected EntityManagerFactory emFactory;
	protected EntityTransaction eTransaction;
	
	protected void createConnection() {
		this.emFactory = Persistence.createEntityManagerFactory("usuario-persistence");
	    this.em = this.emFactory.createEntityManager();
	    this.em.getEntityManagerFactory().getCache().evictAll();
	    this.eTransaction = this.em.getTransaction();
	    this.eTransaction.begin();

		
	}
	
	public void save() throws Exception {
		try {

			this.createConnection();
			int id = Integer.parseInt(this.emFactory.getPersistenceUnitUtil().getIdentifier(this).toString());
			if(id > 0) {
				T object = this.find(id);
				if(object == null) throw new Exception("Item não encontrado");
				
				this.em.merge(this);
			}else {
				System.out.println("DELETANDO");
				this.em.persist(this);
			}
			this.eTransaction.commit();
		}catch(PersistenceException e) {
			throw new Exception(e.getMessage());
		}finally {
			this.em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list() {
		this.createConnection();
		System.out.println(this.getClass().getSimpleName()+".findAll");
		return this.em.createNamedQuery(this.getClass().getSimpleName()+".findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public T find(int id){
		this.createConnection();
		return (T) this.em.find(this.getClass(), id);
	}
	
	public boolean delete() {
		try {
			this.createConnection();
			int id = Integer.parseInt(this.emFactory.getPersistenceUnitUtil().getIdentifier(this).toString());
//			System.out.println(id);
	        this.em.remove(this.em.getReference(this.getClass(), id));
	        this.eTransaction.commit();
			return true;
		}catch(Exception e) {
			System.out.println("Erro Abaixo");

			System.out.println(e.getMessage());
			return false;
		}finally {
			this.em.close();
		}
	}
}