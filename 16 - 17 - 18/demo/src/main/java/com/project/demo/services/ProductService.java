package com.project.demo.services;

import com.project.demo.model.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateQOH(Long id, Integer quantityOnHand);

}
