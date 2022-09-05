package com.project.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity{

    private String name;

    @Embedded
    private Address address;

//    @Version
//    private Integer version;

    private String phone;
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private Set<OrderHeader> orderHeaders;

    public void addOrderHeader(OrderHeader orderHeader){
        if (Objects.isNull(orderHeaders))
            orderHeaders = new HashSet<>();
        orderHeaders.add(orderHeader);
        orderHeader.setCustomer(this);
    }

}
