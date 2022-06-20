package com.agicomputers.LoanAPI.repositories.user_repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


import java.util.Optional;
@NoRepositoryBean
public interface UserRepository<T,ID> extends CrudRepository<T, ID> {
    Optional<? extends T> findByAppUserUid(String userId);
    Optional<? extends T> findByEmail(String email);
    Optional<ID> existsByNin(String nin);
    Optional<ID> existsByEmail(String email);
    Optional<ID> existsByBvn(String bvn);
    Optional<ID> existsByAppUserUid(String appUserUid);
    Long findNextAppUserId();

}
