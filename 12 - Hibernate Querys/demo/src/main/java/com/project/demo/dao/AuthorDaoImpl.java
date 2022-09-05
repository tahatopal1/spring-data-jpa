package com.project.demo.dao;

import com.project.demo.model.Author;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Author> findAll() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Author> query = em.createNamedQuery("author-find-all", Author.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :last_name");
            query.setParameter("last_name", lastName + '%');
            List<Author> authors = query.getResultList();
            return authors;
        } finally {
            em.close();
        }
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
//        Commented out to convert to a NamedQuery
//        TypedQuery<Author> query = getEntityManager().createQuery("SELECT a FROM Author a " +
//                " WHERE a.firstName = :first_name AND a.lastName = :last_name", Author.class);

        EntityManager em = getEntityManager();
        TypedQuery<Author> query = em.createNamedQuery("find-by-name", Author.class);

        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        return query.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.joinTransaction();
        em.merge(author);
        em.flush();
        em.clear();
        Author updated = em.find(Author.class, author.getId());
        em.getTransaction().commit();
        em.close();
        return updated;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Author author = em.find(Author.class, id);
        em.remove(author);
        em.getTransaction().commit();
    }

    @Override
    public Author findAuthorByNameCriteria(String name, String surname) {
        EntityManager em = getEntityManager();

        try{
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Author> crieteriaQuery = criteriaBuilder.createQuery(Author.class);

            Root<Author> root = crieteriaQuery.from(Author.class);

            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            crieteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            TypedQuery<Author> query = em.createQuery(crieteriaQuery);
            query.setParameter(firstNameParam, name);
            query.setParameter(lastNameParam, surname);

            return query.getSingleResult();

        }finally {
            em.close();
        }

    }

    @Override
    public Author findAuthorByNameNative(String name, String surname) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNativeQuery("SELECT * FROM author a WHERE a.first_name = ? and a.last_last = ?", Author.class);
            query.setParameter(1, name);
            query.setParameter(2, surname);
            return (Author) query.getSingleResult();
        }finally {
            em.close();
        }

    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
