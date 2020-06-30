package ru.lidzhiev.restapplication.response;

import lombok.Data;
import ru.lidzhiev.restapplication.entity.Address;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
@Data
public class EmployeeResponse {

    private Long id;
    private String name;
    private String role;
    private Date creationDate;
    private Date updateDate;
    private Address employeeAddress;
    private int code;
    private String errorMessage;

    public EmployeeResponse() {
    }

    public EmployeeResponse(Long id, String name, String role, Date creationDate, Date updateDate, Address employeeAddress) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.employeeAddress = employeeAddress;
    }

    public EmployeeResponse(Long id, String name, String role, Date creationDate, Date updateDate, Address employeeAddress, int code, String errorMessage) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.employeeAddress = employeeAddress;
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public EmployeeResponse(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
