package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vecihle_type")
@Getter
@Setter
public class Vecihle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
