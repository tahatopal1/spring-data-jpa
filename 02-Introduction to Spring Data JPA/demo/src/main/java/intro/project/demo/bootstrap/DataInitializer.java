package intro.project.demo.bootstrap;

import intro.project.demo.model.Book;
import intro.project.demo.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse");
        System.out.println("ID: " + bookDDD.getId());
        Book savedDDD = bookRepository.save(bookDDD);
        System.out.println("ID: " + savedDDD.getId());

        Book bookSIA = new Book("Spring In Action", "234234", "Oriely");
        System.out.println("ID: " + bookSIA.getId());
        Book savedSIA = bookRepository.save(bookSIA);
        System.out.println("ID: " + savedSIA.getId());

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Book title: " + book.getTitle());
        });

    }
}
