package com.agicomputers.LoanAPI.models.entities;

import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@Data
public class AppUser implements UserDetails {
    @Id
  @SequenceGenerator(name="app_user_generator", sequenceName = "app_user_generator",allocationSize = 1)
    @GeneratedValue(generator ="app_user_generator",strategy = GenerationType.SEQUENCE)

    @Column(nullable = false )
    private long appUserId; //app_user_id

    @Column(nullable = true, unique = true)
    private String appUserUid;          //UID

    @Column( nullable = false)
    private String appUserFname; //app_user_fname

    @Column(nullable = false)
    private String appUserLname;
    
    @ManyToOne
    @JoinColumn(name="role_id",referencedColumnName = "roleId")
    private Role appUserRole;

    @Column(nullable = false)
    private LocalDate appUserDob;

    @Column(nullable = false)
    private LocalDateTime appUserDor = LocalDateTime.now();

    @Column(nullable = false, unique = true)
    private String appUserEmail;

    @Column( length = 20, nullable = false)
    private String appUserPhone1;

    @Column( length = 20, nullable = false)
    private String appUserPhone2;

    @Column(nullable = false, length = 512)
    private String appUserAddress;

    @Column(nullable = false)
    private String appUserPassword;

    @Column(nullable = false)
    private Float appUserBalance = 0.0F;

    @Column(nullable = false, unique = true)
    private String appUserNIN;

    @Column(nullable = true, unique = true)
    private String appUserBVN;

    @Column
    private String appUserPassportPhoto;

    @Column
    private String appUserNINPhoto;

    @Column(nullable = false)
    private UserOccupation appUserOccupation;

    @Column
    private String appUserOccupationLocation;

    @Column
    private Boolean appUserRewarded = false;


    //Derived Properties from UserDetails
    @Column
    private Boolean isAccountNonExpired = true;

    @Column
    private Boolean isAccountNonLocked = true;

    @Column
    private Boolean isCredentialsNonExpired = true;

    @Column
    private Boolean isEnabled = true;

    public AppUser(String appUserUid, String appUserPassword, Role appUserRole) {
        this.appUserUid = appUserUid;
        this.appUserRole = appUserRole;
        this.appUserPassword = appUserPassword;
    }

    //User Service Getters & Setters
    @Override
    public String getUsername() {
        return this.appUserUid;
    }

    @Override
    public String getPassword() {
        return appUserPassword;
    }


    @Override
    public boolean isAccountNonExpired() { return isAccountNonExpired;    }

    @Override
    public boolean isAccountNonLocked() {  return isAccountNonLocked;    }

    @Override
    public boolean isCredentialsNonExpired() { return isCredentialsNonExpired;  }

    @Override
    public boolean isEnabled() {    return isEnabled;    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setAuthorities(String... authorities){
        appUserRole.setRoleAuthorities(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = Set.of(appUserRole.getRoleAuthorities())
                .stream()
                .map((authority)->{return new SimpleGrantedAuthority(authority);})
                .collect(Collectors.toSet());
        return authorities;    }

}
