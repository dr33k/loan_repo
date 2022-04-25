package com.agicomputers.LoanAPI.repositories.user_repositories;

import com.agicomputers.LoanAPI.models.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@NoRepositoryBean
public interface UserRepository<T,ID> extends CrudRepository<T, ID> {
    Optional<? extends T> findByUserId(String userId);
    Optional<? extends T> findByEmail(String email);
    Optional<ID> existsByNin(String nin);
    Optional<ID> existsByEmail(String email);
    void updateCustomerId(ID id, String value);
    Long findLastId();

}
