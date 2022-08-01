package com.agicomputers.LoanAPI.repositories.user_repositories;

import com.agicomputers.LoanAPI.models.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository("appUserRepository1")
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    @Query("FROM AppUser AU WHERE AU.appUserUid=?1")
    Optional<AppUser> findByAppUserUid(String appUserUid);

    Optional<AppUser> findByAppUserEmail(String email);
    
    @Query(value = "SELECT nextval('app_user_generator')",nativeQuery = true)
    Long findNextAppUserId();

    @Query(value="SELECT " +
            "CASE WHEN COUNT(AU) > 0 " +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM AppUser AU WHERE AU.appUserNIN = ?1")
    Boolean existsByNin(String nin);

    
    @Query(value="SELECT " +
            "CASE WHEN COUNT(AU) > 0 " +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM AppUser AU WHERE AU.appUserBVN = ?1")
    Boolean existsByBvn(String bvn);
    
    @Query(value="SELECT " +
            "CASE WHEN COUNT(AU) > 0 " +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM AppUser AU WHERE AU.appUserEmail = ?1")
    Boolean existsByEmail(String email);

    @Query(value = "SELECT AU.appUserId FROM AppUser AU WHERE AU.appUserUid = ?1")
    Optional<Long> existsByAppUserUidReturnsId(String appUserUid);

}
