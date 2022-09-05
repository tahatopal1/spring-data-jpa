package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Dolphin extends Mammal{

    private Boolean hasSpots;

}
