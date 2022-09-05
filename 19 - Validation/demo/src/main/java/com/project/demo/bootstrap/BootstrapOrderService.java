package com.project.demo.bootstrap;

import com.project.demo.model.OrderHeader;
import com.project.demo.repository.OrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BootstrapOrderService {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Transactional
    public void readOrderData(){
        System.out.println("I was called!");

        OrderHeader orderHeader = orderHeaderRepository.findById(1L).get();

        orderHeader.getOrderLines().forEach(orderLine -> {
            System.out.println(orderLine.getProduct().getDescription());
        });
    }

}
