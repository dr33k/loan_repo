package com.agicomputers.LoanAPI.repositories.user_repositories;

import com.agicomputers.LoanAPI.models.entities.AppUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository("appUserRepository1")
public interface AppUserRepository extends UserRepository<AppUser,Long> {

    @Override
    @Query(value = "FROM AppUser AU WHERE AU.appUserUid = ?1")
    Optional<AppUser> findByAppUserUid(String appUserUid);

    @Override
    @Query(value = "SELECT nextval('app_user_generator')",nativeQuery = true)
    Long findNextAppUserId();

    @Override
        @Query(value = "FROM AppUser AU WHERE AU.appUserEmail = ?1")
    Optional<AppUser> findByEmail(String email);

    @Override
    @Query(value="SELECT AU.appUserId FROM AppUser AU WHERE AU.appUserNIN = ?1")
    Optional<Long> existsByNin(String nin);

    @Override
    @Query(value="SELECT AU.appUserId FROM AppUser AU WHERE AU.appUserBVN = ?1")
    Optional<Long> existsByBvn(String bvn);


    @Override
    @Query(value="SELECT AU.appUserId FROM AppUser AU WHERE AU.appUserEmail = ?1")
    Optional<Long> existsByEmail(String email);

    @Override
    @Query(value = "SELECT AU.appUserId FROM AppUser AU WHERE AU.appUserUid = ?1")
    Optional<Long> existsByAppUserUid(String appUserUid);

/*      TEST
    @Query(value="SELECT first_name,last_name FROM customer WHERE last_name= ?1",nativeQuery = true)
    String[] findCustomerByLastName(String last_name);
 */
}
