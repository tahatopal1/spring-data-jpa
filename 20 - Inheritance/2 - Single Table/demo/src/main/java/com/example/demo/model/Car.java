package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("car")
@Getter
@Setter
public class Car extends Vecihle{

    private String trimLevel;

}
