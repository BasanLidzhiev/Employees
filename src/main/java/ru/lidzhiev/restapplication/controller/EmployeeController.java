package ru.lidzhiev.restapplication.controller;

import org.springframework.web.bind.annotation.*;
import ru.lidzhiev.restapplication.exception.EmployeeNotFoundException;
import ru.lidzhiev.restapplication.repository.EmployeeRepository;
import ru.lidzhiev.restapplication.entity.Employee;
import ru.lidzhiev.restapplication.request.FindByNameAndRoleRq;
import ru.lidzhiev.restapplication.request.NewEmployeeRq;
import ru.lidzhiev.restapplication.response.EmployeeResponse;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/employees")
    public List<Employee> all() {
        return repository.findAll();
    }

    @PostMapping("/add")
    public Employee newEmployee(@RequestBody NewEmployeeRq rq) {
        Employee employee = new Employee(rq.getName(),rq.getRole());
        return repository.save(employee);
    }

    @GetMapping("/findByName")
    public EmployeeResponse getByName(@RequestBody String name) {
        try {
            Employee findByNameEmployee = repository.findByName(name);
            return new EmployeeResponse(findByNameEmployee.getId(),findByNameEmployee.getName(),
                    findByNameEmployee.getRole(), findByNameEmployee.getCreationDate(), findByNameEmployee.getUpdateDate(),
                    findByNameEmployee.getEmployeeAddress(), 0, "Success");
        } catch (Exception e) {
            return new EmployeeResponse(1, "Employee with name " + name + " does not exist" );
        }
    }

    @GetMapping("/findByRole")
    public EmployeeResponse getByRole(@RequestBody String role) {
        try {
            Employee findByNameEmployee = repository.findByRole(role);
            return new EmployeeResponse(findByNameEmployee.getId(),findByNameEmployee.getName(),
                    findByNameEmployee.getRole(), findByNameEmployee.getCreationDate(), findByNameEmployee.getUpdateDate(),
                    findByNameEmployee.getEmployeeAddress(), 0, "Success");
        } catch (Exception e) {
            return new EmployeeResponse(1, "Employee with role " + role + " does not exist" );
        }
    }

    @GetMapping("/findByNameAndRole")
    public EmployeeResponse getByNameAndRole(@RequestBody FindByNameAndRoleRq rq){
        try {
            Employee findByNameEmployee = repository.findByNameAndRole(rq.getName(), rq.getRole());
            return new EmployeeResponse(findByNameEmployee.getId(),findByNameEmployee.getName(),
                    findByNameEmployee.getRole(), findByNameEmployee.getCreationDate(), findByNameEmployee.getUpdateDate(),
                    findByNameEmployee.getEmployeeAddress(), 0, "Success");
        } catch (Exception e) {
            return new EmployeeResponse(1, "Employee with name and role " + rq.getName() + ", " + rq.getRole() + " does not exist" );
        }
    }

    @GetMapping("/findByNameAndRole1")
    public EmployeeResponse getByNameAndRole1(@RequestBody FindByNameAndRoleRq rq){
        try {
            Employee findByNameEmployee = repository.findByNameAndRole1(rq.getName(), rq.getRole());
            return new EmployeeResponse(findByNameEmployee.getId(),findByNameEmployee.getName(),
                    findByNameEmployee.getRole(), findByNameEmployee.getCreationDate(), findByNameEmployee.getUpdateDate(),
                    findByNameEmployee.getEmployeeAddress(), 0, "Success");
        } catch (Exception e) {
            return new EmployeeResponse(1, "Employee with name and role " + rq.getName() + ", " + rq.getRole() + " does not exist" );
        }
    }

    @GetMapping("/findByNameAndRole2")
    public EmployeeResponse getByNameAndRole2(@RequestBody FindByNameAndRoleRq rq){
        try {
            Employee findByNameEmployee = repository.findByNameAndRole2(rq.getName(), rq.getRole());
            return new EmployeeResponse(findByNameEmployee.getId(),findByNameEmployee.getName(),
                    findByNameEmployee.getRole(), findByNameEmployee.getCreationDate(), findByNameEmployee.getUpdateDate(),
                    findByNameEmployee.getEmployeeAddress(), 0, "Success");
        } catch (Exception e) {
            return new EmployeeResponse(1, "Employee with name and role " + rq.getName() + ", " + rq.getRole() + " does not exist" );
        }
    }

    @GetMapping("/employee/{id}")
    public EmployeeResponse one(@PathVariable Long id) {
        try {
            Employee findByNameEmployee = repository.findById1(id);
            return new EmployeeResponse(findByNameEmployee.getId(),findByNameEmployee.getName(),
                    findByNameEmployee.getRole(), findByNameEmployee.getCreationDate(), findByNameEmployee.getUpdateDate(),
                    findByNameEmployee.getEmployeeAddress(), 0, "Success");
        } catch (Exception e) {
            return new EmployeeResponse(1, "Employee with id " + id + " does not exist" );
        }
    }

    @PostMapping("/employees/{id}")
    public Employee replaceEmployee(@RequestBody NewEmployeeRq rq, @PathVariable Long id) {
        Employee newEmployee = new Employee(rq.getName(), rq.getRole());
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/delete/{id}")
    public EmployeeResponse deleteEmployee(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return new EmployeeResponse(0, "");
        } catch (Exception e) {
            return new EmployeeResponse(1, "Employee with id " + id + " does not exist" );
        }
    }

    @DeleteMapping("/delete")
    public EmployeeResponse deleteEmployeeByName(@RequestBody String name) {
        try {
            Employee employee = repository.findByName(name);
            if (employee != null) {
                repository.deleteByName(name);
                return new EmployeeResponse(0, "");
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return new EmployeeResponse(1, "Employee with name " + name + " does not exist" );
        }
    }

    @PostMapping("employees/set-adresses/{id}&{id2}")
    public void newAddress(@PathVariable Long id, @PathVariable Long id2) {
        repository.setEmployeeAddressId(id, id2);
    }

}
