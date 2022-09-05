package com.project.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class OrderApproval extends BaseEntity{

    private String approvedBy;

}
