package ru.lidzhiev.restapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.lidzhiev.restapplication.entity.Address;
import ru.lidzhiev.restapplication.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByName(String name);

    @Query("select e from Employee e where e.id = ?1")
    Employee findById1(Long id);

    Employee findByRole(String role);

    Employee findByNameAndRole(String name, String role);

    @Query("select e from Employee e where e.name = ?1 and e.role = ?2")
    Employee findByNameAndRole1(String name, String role);

    @Query(value = "select * from Employee where name = ?1 and role = ?2", nativeQuery = true)
    Employee findByNameAndRole2(String name, String role);


    @Transactional
    @Modifying
    @Query("DELETE FROM Employee WHERE name=?1")
    void deleteByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update Employee SET employee_address_id=?2 WHERE id = ?1",nativeQuery = true)
    void setEmployeeAddressId(Long id, Long addressId);
}
