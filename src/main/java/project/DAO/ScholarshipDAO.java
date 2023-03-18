package project.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ScholarshipDAO<T> {
    protected static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Sanchous");
    protected final Class<T> type;
    protected EntityManager entityManager;


    public ScholarshipDAO(Class<T> type) {
        this.type = type;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void update(T newRecord) {
        entityManager.getTransaction().begin();
        entityManager.merge(newRecord);
        entityManager.getTransaction().commit();
    }

    public void add(T newRecord) throws Exception {
        entityManager.getTransaction().begin();
        entityManager.persist(newRecord);
        entityManager.getTransaction().commit();
    }

    public void remove(T existingRecord) throws Exception {
        entityManager.getTransaction().begin();
        entityManager.remove(existingRecord);
        entityManager.getTransaction().commit();
    }


}
