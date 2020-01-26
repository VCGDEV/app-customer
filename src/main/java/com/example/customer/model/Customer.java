package com.example.customer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
@Data
public class Customer {
    @Id
    private String uuid;
    private String name;
    private String email;
}
