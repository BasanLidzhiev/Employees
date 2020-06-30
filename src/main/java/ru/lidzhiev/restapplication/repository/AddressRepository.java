package ru.lidzhiev.restapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lidzhiev.restapplication.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
