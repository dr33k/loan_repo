package com.agicomputers.LoanAPI.repositories;

import com.agicomputers.LoanAPI.models.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "SELECT * FROM loan_db.customer WHERE customer_id = ?1", nativeQuery = true)
    Optional<Customer> findByCustomerId(String customerId);

    @Query(value = "SELECT * FROM loan_db.customer WHERE customer_id LIKE ?1", nativeQuery = true)
    Iterable<Customer> findAllWithIdPattern(String sqlRegex);
}
