package com.project.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
@Getter
@Setter
public class OrderLine extends BaseEntity{

    private Integer quantityOrdered;

    @Version
    private Integer version;

    @ManyToOne
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;

}
