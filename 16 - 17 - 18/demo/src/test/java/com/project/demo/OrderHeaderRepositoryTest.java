package com.project.demo;

import com.project.demo.model.*;
import com.project.demo.repository.CustomerRepository;
import com.project.demo.repository.OrderApprovalRepository;
import com.project.demo.repository.OrderHeaderRepository;
import com.project.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;


    Product product;

    @BeforeEach
    void setUp(){
        Product nProduct = new Product();
        nProduct.setProductStatus(ProductStatus.NEW);
        nProduct.setDescription("Test Product");
        product = productRepository.saveAndFlush(nProduct);
    }

    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        orderHeader.addOrderLine(orderLine);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("Me");
        orderHeader.setOrderApproval(orderApproval);

        Customer customer = customerRepository.findByName("New Customer");
        orderHeader.setCustomer(customer);

        OrderHeader saved = orderHeaderRepository.save(orderHeader);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertNotNull(saved.getOrderLines());
        assertEquals(saved.getOrderLines().size(), 1);
    }

    @Test
    void testSaveOrder(){
        OrderHeader orderHeader = new OrderHeader();

        Customer customer = customerRepository.findByName("New Customer");
        orderHeader.setCustomer(customer);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("Me");
        orderHeader.setOrderApproval(orderApproval);

        orderHeader.setOrderStatus(OrderStatus.COMPLETE);
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }

    @Test
    void testDeleteCascade() {

        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setName("new Customer");
        orderHeader.setCustomer(customerRepository.save(customer));

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(3);
        orderLine.setProduct(product);

        orderHeader.addOrderLine(orderLine);
        OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

        System.out.println("order saved and flushed");

        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();

        assertThrows(EntityNotFoundException.class, () -> {
            OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

            assertNull(fetchedOrder);
        });
    }

}
