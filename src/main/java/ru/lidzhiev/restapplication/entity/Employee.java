package ru.lidzhiev.restapplication.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Employee {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private String role;
    private Date creationDate;
    private Date updateDate;
    @ManyToOne
    private Address employeeAddress;

    Employee() {}

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    Employee(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
