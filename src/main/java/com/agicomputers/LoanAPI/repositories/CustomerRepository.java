package com.agicomputers.LoanAPI.repositories;

import com.agicomputers.LoanAPI.models.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "SELECT * FROM `loan_db.customer where customer.customerId = ?1`", nativeQuery = true)
    Optional<Customer> findByCustomerId(String customerId);

    @Query(value = "SELECT * FROM `loan_db.customer where customer.customerId LIKE ?1", nativeQuery = true)
    Iterable<Customer> findAllWithIdPattern(String sqlRegex);
}
