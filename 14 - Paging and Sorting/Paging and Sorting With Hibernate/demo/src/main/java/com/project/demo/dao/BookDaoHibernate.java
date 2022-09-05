package com.project.demo.dao;

import com.project.demo.model.Book;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookDaoHibernate implements BookDao{

    private final EntityManagerFactory emf;

    public BookDaoHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        EntityManager entityManager = getEntityManager();
        try {
            String hql = "SELECT b FROM Book b ORDER BY b.title "
                    + pageable.getSort().getOrderFor("title").getDirection().name();

            TypedQuery<Book> query = entityManager.createQuery(hql, Book.class);
            query.setMaxResults(pageable.getPageSize());
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            return query.getResultList();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        EntityManager entityManager = getEntityManager();

        try{
            TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }finally {
            entityManager.close();
        }

    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return null;
    }

    @Override
    public List<Book> findAllBooks() {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Book getById(Long id) {
        EntityManager entityManager = getEntityManager();
        Book book = entityManager.find(Book.class, id);
        entityManager.close();
        return book;
    }

    @Override
    public Book findBookByTitle(String title) {
        EntityManager entityManager = getEntityManager();
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM book WHERE title = :title", Book.class);
            query.setParameter("title", title);
            return (Book) query.getSingleResult();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.flush();
        entityManager.clear();

        Book saved = entityManager.find(Book.class, book.getId());
        entityManager.getTransaction().commit();
        entityManager.close();
        return saved;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id);
        entityManager.remove(book);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

}
