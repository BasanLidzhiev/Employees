package ru.lidzhiev.restapplication.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private Integer number;
    private Integer flat;

    public Address() {
    }

    public Address(String city, String street, Integer number, Integer flat) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.flat = flat;
    }
}
