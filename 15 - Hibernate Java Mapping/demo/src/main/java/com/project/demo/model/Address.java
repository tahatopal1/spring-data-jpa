package com.project.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {

    private String address;
    private String city;
    private String state;
    private String zipCode;

}
