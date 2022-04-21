package com.agicomputers.LoanAPI.repositories.user_repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository<Customer, Long> extends UserRepository<Customer,Long> {

    @Query(value = "SELECT * FROM loan_db.customer WHERE customer_id = ?1", nativeQuery = true)
    Optional<Customer> findByUserId(String userId);

    @Query(value = "SELECT max(customer.id) FROM loan_db.customer", nativeQuery = true)
    Long findLastId();

    @Query(value = "SELECT * FROM loan_db.customer WHERE customer_email = ?1", nativeQuery = true)
    Optional<Customer> findByEmail(String email);

    @Query(value="SELECT customer.id FROM loan_db.customer WHERE customernin = ?1", nativeQuery = true)
    Optional<Long> existsByNin(String nin);

    @Query(value="SELECT customer.id FROM loan_db.customer WHERE customer_email = ?1", nativeQuery = true)
    Optional<Long> existsByEmail(String email);


    @Modifying
    @Query(value="UPDATE loan_db.customer SET customer.customer_id = ?2 WHERE id = ?1", nativeQuery = true)
    void updateCustomerId(Long id, String value);
}
