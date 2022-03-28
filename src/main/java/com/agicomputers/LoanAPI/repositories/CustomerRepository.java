package com.agicomputers.LoanAPI.repositories;

import com.agicomputers.LoanAPI.models.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "SELECT * FROM loan_db.customer WHERE customer_id = ?1", nativeQuery = true)
    Optional<Customer> findByCustomerId(String customerId);

    @Query(value = "SELECT max(customer.id) FROM loan_db.customer", nativeQuery = true)
    Long findLastId();

    @Query(value = "SELECT * FROM loan_db.customer WHERE customer_email = ?1", nativeQuery = true)
    Optional<Customer> findByEmail(String email);
}
