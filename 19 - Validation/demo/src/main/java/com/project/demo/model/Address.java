package com.project.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
@Data
public class Address {

    @Size(max = 30)
    private String address;

    @Size(max = 30)
    private String city;

    @Size(max = 30)
    private String state;

    @Size(max = 30)
    private String zipCode;

}
