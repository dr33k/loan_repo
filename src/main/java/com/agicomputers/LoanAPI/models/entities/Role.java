package com.agicomputers.LoanAPI.models.entities;

import com.agicomputers.LoanAPI.models.enums.AppUserPermission;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer role_id;

    @Column(nullable = false)
    private String roleName;

    @Column(columnDefinition = "role_enum NOT NULL DEFAULT 'APPUSER'")
    private String roleType;

    @Column(nullable = false)
    private String[] roleAuthorities = {"app_user:read"};

    @Column(nullable = false)
    private String roleDescription;

    public Role(Integer id, String roleName, Set<? extends GrantedAuthority> authorities) {
        this.role_id = id;
        this.roleName = roleName;

//map Set<? extends GrantedAuthorities> into List<String>, add the ROLE and then assign to roleAuthorities
        List<String> temp =
                authorities
                .stream()
                .map((authority)->{return authority.toString();})
                .collect(Collectors.toList());
        if(!temp.contains("ROLE_" + roleName.toUpperCase()))temp.add("ROLE_" + roleName.toUpperCase());

        this.roleAuthorities = temp.toArray(this.roleAuthorities);

    }

    public Integer getId() {
        return this.role_id;
    }

    public void setId(Integer id) {
        this.role_id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String[] getRoleAuthorities() {
        return roleAuthorities;
    }

    public void setRoleAuthorities(String... roleAuthorities) {
        String[] stringRoleAuthorities= {};
        //Add the "ROLE" authority
        Set<String> stringRoleAuthoritiesSet = Set.of(roleAuthorities);
        if(!stringRoleAuthoritiesSet.contains("ROLE_" + roleName.toUpperCase()))
            stringRoleAuthoritiesSet.add("ROLE_" + roleName.toUpperCase());
        stringRoleAuthorities = stringRoleAuthoritiesSet.toArray(stringRoleAuthorities);

        this.roleAuthorities = stringRoleAuthorities;
    }

    public void setRoleAuthorities(Set<GrantedAuthority> authorities) {
        //Add the "ROLE" authority
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()));

        String[] stringRoleAuthorities= {};
        stringRoleAuthorities=
                authorities.stream()
                .map((authority)->{ return authority.toString(); })
                .collect(Collectors.toSet()).
                toArray(stringRoleAuthorities);

        this.roleAuthorities = stringRoleAuthorities;
    }


    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

}
