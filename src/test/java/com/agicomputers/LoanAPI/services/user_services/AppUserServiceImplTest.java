package com.agicomputers.LoanAPI.services.user_services;

import com.agicomputers.LoanAPI.models.dto.AppUserDTO;
import com.agicomputers.LoanAPI.models.entities.AppUser;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import com.agicomputers.LoanAPI.repositories.user_repositories.AppUserRepository;
import com.agicomputers.LoanAPI.services.role_service.RoleService;
import com.google.common.base.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:/application-test.properties")
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;
    private AppUserServiceImpl appUserService;

    @BeforeEach
    void setUp(){
        appUserService = new AppUserServiceImpl(appUserRepository,passwordEncoder,roleService);
    }

    @Test
    void getAllUsers() {
        //When
        HashSet<AppUserDTO> appUserDTOs = appUserService.getAllUsers();
        //Then
        verify(appUserRepository).findAll(PageRequest.of(0,20));
        //Also
        atLeast(2);
    }

    @Test
    void getUser() {
        //Given
        String UID = "7802732798JKALF";
        //When
        appUserService.getUser(UID);
        //Then
        verify(appUserRepository).findByAppUserUid(UID);
    }

    @Test
    void getUserWithEmail() {
        //Given
        String email = "seven@gmail.com";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        //When
        appUserService.getUser(email);
        //Then
        verify(appUserRepository).findByAppUserUid(argumentCaptor.capture());
        //Also
        assertThat(email).isEqualTo(argumentCaptor.getValue());
    }

    @Test
    void deleteUser() {
        //Given
        String UID = "7802732798JKALF";
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        //When
        appUserService.deleteUser(UID);
        //Then
        verify(appUserRepository).existsByAppUserUidReturnsId(argumentCaptor.capture());

        //Also
        String uidCaptured = argumentCaptor.getValue();
        assertThat(UID).isEqualTo(uidCaptured);
    }

    @Test
    void createUser() {
        //Given
        AppUserDTO appUserDTO = returnAppUserDTO();
        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);

        //When
        appUserService.createUser(appUserDTO);

        //Then
        verify(appUserRepository).save(argumentCaptor.capture());

        //Also
        AppUser appUserCaptured = argumentCaptor.getValue();
        assertThat(appUserCaptured.getAppUserUid()).isEqualTo(appUserDTO.getAppUserUid());
    }

    @Test
    void updateUser() {
        //Given
        AppUserDTO udto = returnAppUserDTO();
        ArgumentCaptor<AppUser> argumentCaptorForAppUser = ArgumentCaptor.forClass(AppUser.class);
        ArgumentCaptor<String> argumentCaptorForString = ArgumentCaptor.forClass(String.class);
        //When
        appUserService.updateUser(udto);
        //Then
        verify(appUserRepository).findByAppUserUid(argumentCaptorForString.capture());
        assertThat(udto.getAppUserUid()).isEqualTo(argumentCaptorForString.getValue());
    }

    @Test
    @Disabled
    void generateChecksum() {
    }

    @Test
    void nextId() {
        //When
        Long id = appUserService.nextId();
        //Then
        verify(appUserRepository).findNextAppUserId();
        //Also

        System.out.println(id);
    }

    private AppUserDTO returnAppUserDTO(){
        AppUserDTO appUser = new AppUserDTO();
        appUser.setAppUserUid("7802732798JKALF");
        appUser.setAppUserEmail("sevenpointzerozero@gmail.com");
        appUser.setAppUserAddress("I live at Home lol");
        appUser.setAppUserFname("DIRM");
        appUser.setAppUserLname("dfgrd");
        appUser.setAppUserDob(LocalDate.of(2004,05,05));
        appUser.setAppUserPassword("iuoiyhurgt76843hbrtw78");
        appUser.setAppUserOccupation(UserOccupation.ARMED_FORCES);
        appUser.setAppUserPhone1("+2341234567");
        appUser.setAppUserPhone2("+2341234567");
        appUser.setAppUserNIN("123455678909");
        appUser.setAppUserBVN("12345567899");
        appUser.setAppUserDor(LocalDateTime.now());
        return appUser;
    }
}