package com.agicomputers.LoanAPI.repositories.user_repositories;

import com.agicomputers.LoanAPI.models.entities.AppUser;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:/application-test.properties"})
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @AfterEach
    void tearDown(){
        appUserRepository.deleteAll();
    }

    @Test
    void findByAppUserUid(){
        String appUserUid = "7802732798JKALF";
        AppUser appUser = returnAppUserWithUid(appUserUid);
        appUserRepository.save(appUser);
        //When
        Optional<AppUser> id = appUserRepository.findByAppUserUid(appUserUid);
        //Then
        assertThat(id.isPresent()).isTrue();
    }
    @Test
    void existsByAppUserUidReturnsId() {
       String appUserUid = "7802732798JKALF";
       AppUser appUser = returnAppUserWithUid(appUserUid);
        appUserRepository.save(appUser);
        //When
        Optional<Long> id = appUserRepository.existsByAppUserUidReturnsId(appUserUid);

        //Then
        assertThat(id.isPresent()).isTrue();
    }

    @Test
    void existsByEmail() {
        //Given
        String appUserEmail = "sevenpointzerozero@gmail.com";
        AppUser appUser = returnAppUserWithEmail(appUserEmail);
        appUserRepository.save(appUser);
        //When
        Boolean existsByEmail = appUserRepository.existsByEmail(appUserEmail);
        //Then
        assertThat(existsByEmail).isTrue();
    }

    @Test
    void findNextAppUserId() {
        //When
       Long next =  appUserRepository.findNextAppUserId();
       assertThat(next).isOne();
    }

    @Test
    void existsByNin() {

        //Given
        String nin = "12345678901";
        AppUser appUser = returnAppUserWithNin(nin);
        appUserRepository.save(appUser);
        //When
        Boolean existsByEmail = appUserRepository.existsByNin(nin);
        //Then
        assertThat(existsByEmail).isTrue();
    }

    @Test
    void existsByBvn() {
        //Given
        String bvn = "1234567890";
        AppUser appUser = returnAppUserWithBvn(bvn);
        appUserRepository.save(appUser);
        //When
        Boolean existsByEmail = appUserRepository.existsByBvn(bvn);
        //Then
        assertThat(existsByEmail).isTrue();
    }

    private AppUser returnAppUserWithBvn(String bvn) {
        AppUser appUser = returnAppUser();
        appUser.setAppUserBVN(bvn);
        return appUser;
    }

    private AppUser returnAppUserWithNin(String nin) {
        AppUser appUser = returnAppUser();
        appUser.setAppUserNIN(nin);
        return appUser;
    }

    private AppUser returnAppUserWithEmail(String appUserEmail){
        AppUser appUser = returnAppUser();
        appUser.setAppUserEmail(appUserEmail);
        return appUser;
    }
    private AppUser returnAppUserWithUid(String appUserUid){
        AppUser appUser = returnAppUser();
                appUser.setAppUserUid(appUserUid);
        return appUser;
    }

    private AppUser returnAppUser(){
        AppUser appUser = new AppUser("7802732798JKALF","sevenpointzerozero@gmail.com", "iuoiyhurgt76843hbrtw78", null);
        appUser.setAppUserAddress("I live at Home lol");
        appUser.setAppUserFname("DIRM");
        appUser.setAppUserLname("dfgrd");
        appUser.setAppUserDob(Date.valueOf("2004-05-05"));
        appUser.setAppUserOccupation(UserOccupation.ARMED_FORCES);
        appUser.setAppUserPhone1("+2341234567");
        appUser.setAppUserPhone2("+2341234567");
        appUser.setAppUserNIN("123455678909");
        appUser.setAppUserBVN("12345567899");
        appUser.setAppUserDor(Timestamp.valueOf(LocalDateTime.now()));
return appUser;
    }
}