package ru.lidzhiev.restapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such user")
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Could not find employee " + id);
    }

    public EmployeeNotFoundException(String name) {
        super("Could not find employee " + name);
    }

    public EmployeeNotFoundException(String name, String role) {
        super("Could not find employee " + name + ", " + role);
    }

}
