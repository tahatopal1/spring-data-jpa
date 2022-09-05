package com.project.demo;

import com.project.demo.model.Product;
import com.project.demo.model.ProductStatus;
import com.project.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void testSaveProduct() {
        Product product = new Product();
        product.setDescription("New Product");
        product.setProductStatus(ProductStatus.NEW);
        Product saved = productRepository.save(product);

        assertNotNull(saved);
        assertNotNull(saved.getId());

        Product fetched = productRepository.getById(saved.getId());

        assertNotNull(fetched);
        assertNotNull(fetched.getId());
        assertNotNull(fetched.getCreatedDate());
        assertNotNull(fetched.getLastModifiedDate());
    }
}
