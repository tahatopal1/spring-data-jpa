package intro.project.demo;

import intro.project.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ComponentScan(basePackages = {"intro.project.demo.bootstrap"})
public class SpringBootJpaTestWithBootstrapSlice {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testJpaTestSplice() {
        long count = bookRepository.count();
        assertThat(count).isGreaterThan(0);
    }


}
