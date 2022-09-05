package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("truck")
@Getter
@Setter
public class Truck extends Vecihle{

    private Integer payload;

}
