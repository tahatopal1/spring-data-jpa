package com.project.demo;

import com.project.demo.model.*;
import com.project.demo.repository.CustomerRepository;
import com.project.demo.repository.OrderHeaderRepository;
import com.project.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataLoadTest {

    final String PRODUCT_D1 = "Product 1";
    final String PRODUCT_D2 = "Product 2";
    final String PRODUCT_D3 = "Product 3";

    final String TEST_CUSTOMER = "TEST CUSTOMER";

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void testDBLock() {
        Long id = 1l;

        OrderHeader orderHeader = orderHeaderRepository.findById(id).get();

        Address billTo = new Address();
        billTo.setAddress("Bill me");
        orderHeader.setBillToAddress(billTo);
        orderHeaderRepository.saveAndFlush(orderHeader);

        System.out.println("I updated the order");
    }

    @Test
    void testN_PlusOneProblem2() {
        OrderHeader orderHeader = orderHeaderRepository.findById(1l).get();
    }

    @Test
    void testN_PlusOneProblem() {

        Customer customer = customerRepository.findCustomerByNameIgnoreCase(TEST_CUSTOMER).get();

//        IntSummaryStatistics totalOrdered = orderHeaderRepository.findAllByCustomer(customer).stream()
//                .flatMap(orderHeader -> orderHeader.getOrderLines().stream())
//                .collect(Collectors.summarizingInt(ol -> ol.getQuantityOrdered()));

        List<OrderLine> collected = orderHeaderRepository.findAllByCustomer(customer).stream()
                .flatMap(orderHeader -> orderHeader.getOrderLines().stream())
                .collect(Collectors.toList());

//        System.out.println("total ordered: " + totalOrdered.getSum());
    }

    @Rollback(value = false)
    @Test
    void testDataLoader() {

        List<Product> products = loadProducts();
        Customer customer = loadCustomer();

        int ordersToCreate = 50000;

        for (int i = 0; i < ordersToCreate; i++) {
            System.out.println("Creating Order: " + i);
            saveOrder(customer, products);
        }

        orderHeaderRepository.flush();

    }

    private OrderHeader saveOrder(Customer customer, List<Product> products) {
        Random random = new Random();

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        products.forEach(product -> {
            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(product);
            orderLine.setQuantityOrdered(random.nextInt(20));
            orderHeader.addOrderLine(orderLine);
        });

        return orderHeaderRepository.save(orderHeader);
    }

    private Customer loadCustomer() {
        return getOrSaveCustomer(TEST_CUSTOMER);
    }

    private Customer getOrSaveCustomer(String name) {
        return customerRepository.findCustomerByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Customer c1 = new Customer();
                    c1.setName(name);
                    c1.setEmail("test@example.com");
                    Address address = new Address();
                    address.setAddress("123 Main");
                    address.setCity("New Orleans");
                    address.setState("LA");
                    c1.setAddress(address);
                    return customerRepository.save(c1);
                });
    }

    private List<Product> loadProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(getOrSaveProduct(PRODUCT_D1));
        products.add(getOrSaveProduct(PRODUCT_D2));
        products.add(getOrSaveProduct(PRODUCT_D3));
        return products;
    }

    private Product getOrSaveProduct(String description) {
        return productRepository.findByDescription(description)
                .orElseGet(() -> {
                    Product product = new Product();
                    product.setDescription(description);
                    product.setProductStatus(ProductStatus.NEW);
                    return productRepository.save(product);
                });
    }

}
