package com.project.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
public class Product extends BaseEntity{

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

}
