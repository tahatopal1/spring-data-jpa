package com.project.demo.bootstrap;


import com.project.demo.model.AuthorUuid;
import com.project.demo.model.Book;
import com.project.demo.repository.AuthorRepository;
import com.project.demo.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public DataInitializer(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);
        System.out.println("ID: " + bookDDD.getId());
        Book savedDDD = bookRepository.save(bookDDD);
        System.out.println("ID: " + savedDDD.getId());

        Book bookSIA = new Book("Spring In Action", "234234", "Oriely", null);
        System.out.println("ID: " + bookSIA.getId());
        Book savedSIA = bookRepository.save(bookSIA);
        System.out.println("ID: " + savedSIA.getId());

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Book title: " + book.getTitle());
        });

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("Joe");
        authorUuid.setLastName("Buck");
        AuthorUuid savedAuthor = authorRepository.save(authorUuid);
        System.out.println("Saved Author UUID: " + savedAuthor.getId());

    }
}
