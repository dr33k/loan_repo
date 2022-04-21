package com.agicomputers.LoanAPI.repositories;

import com.agicomputers.LoanAPI.models.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository<T,ID> extends CrudRepository<T, ID> {
    Optional<? extends T> findByUserId(String userId);
    Optional<? extends T> findByEmail(String email);
    Optional<ID> existsByNin(String nin);
    Optional<ID> existsByEmail(String email);
    void updateCustomerId(ID id, String value);
}
