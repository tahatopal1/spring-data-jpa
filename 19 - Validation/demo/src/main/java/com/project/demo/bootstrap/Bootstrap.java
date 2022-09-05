package com.project.demo.bootstrap;

import com.project.demo.model.Customer;
import com.project.demo.model.Product;
import com.project.demo.model.ProductStatus;
import com.project.demo.repository.CustomerRepository;
import com.project.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    BootstrapOrderService bootstrapOrderService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductService productService;

    private void updateProduct(){
        Product product = new Product();
        product.setDescription("My Product");
        product.setProductStatus(ProductStatus.NEW);

        Product savedProduct = productService.saveProduct(product);

        Product savedProduct2 = productService.updateQOH(savedProduct.getId(), 25);

        System.out.println("Updated Qty: " + savedProduct2.getQuantityOnHand());
    }

    @Override
    public void run(String... args) throws Exception {

        updateProduct();
        bootstrapOrderService.readOrderData();

        Customer customer = new Customer();
        customer.setName("Testing Version");
        Customer saved = customerRepository.save(customer);
        System.out.println("Version is: " + saved.getVersion());

        saved.setName("Testing Version 2");
        Customer saved2 = customerRepository.save(saved);
        System.out.println("Version is: " + saved2.getVersion());

        saved2.setName("Testint Version 3");
        Customer saved3 = customerRepository.save(saved2);
        System.out.println("Version is: " + saved3.getVersion());

        customerRepository.deleteById(saved.getId());

    }
}
