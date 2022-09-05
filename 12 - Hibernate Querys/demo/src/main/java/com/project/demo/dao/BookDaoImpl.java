package com.project.demo.dao;

import com.project.demo.model.Book;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    private final EntityManagerFactory emf;

    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Book findByISBN(String isbn) {

        EntityManager em = getEntityManager();

        try{
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);
            Book fetched = query.getSingleResult();
            return fetched;
        }finally {
            em.close();
        }
    }

    @Override
    public Book getById(Long id) {
        return getEntityManager().find(Book.class, id);
    }

    @Override
    public Book findBookByTitle(String title) {
//        Refactoring for named query
//        TypedQuery<Book> query = getEntityManager().createQuery("SELECT b FROM Book b" +
//                " WHERE b.title = :title", Book.class);
            EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> query = em.createNamedQuery("find-by-title", Book.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        }finally {
            em.close();
        }

    }

    @Override
    public Book findBookByTitleCriteria(String title) {
        EntityManager em = getEntityManager();

        try{

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

            Root<Book> root = criteriaQuery.from(Book.class);
            ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);

            Predicate titlePred = criteriaBuilder.equal(root.get("title"), titleParam);

            criteriaQuery.select(root).where(titlePred);

            TypedQuery<Book> query = em.createQuery(criteriaQuery);
            query.setParameter(titleParam, title);

            return query.getSingleResult();

        }finally {
            em.close();
        }

    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        em.joinTransaction();
        em.merge(book);
        em.flush();
        em.clear();
        em.getTransaction().commit();
        return getEntityManager().find(Book.class, book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.getTransaction().commit();
    }

    @Override
    public List<Book> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> query = em.createNamedQuery("find-all-books", Book.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public Book findBookByTitleNative(String title) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNativeQuery("SELECT * FROM book WHERE title = :title", Book.class);
            query.setParameter("title", title);
            return (Book) query.getSingleResult();
        }finally {
            em.close();
        }

    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
