package com.agicomputers.LoanAPI.models.entities;

import com.agicomputers.LoanAPI.models.enums.AppUserPermission;
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
    private Integer id;

    @Column(nullable = false)
    private String roleName;

    @Transient
    final private String roleAuthoritiesColumnDefinition = "SET("+
            "'customer:read'," +
            "'customer:write', " +
            "'admin:read'," +
            "'admin:write'"+
            ") NOT NULL";

    //The values above need to be defined on creation of the table
    //They can always be modified here

    @Column(columnDefinition = roleAuthoritiesColumnDefinition)
    private String[] roleAuthorities = {};

    @Column(nullable = false)
    private String roleDescription;

    public Role(Integer id, String roleName, Set<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.roleName = roleName;

//map Set<? extends GrantedAuthorities> into List<String>, add the ROLE and then assign to roleAuthorities
        List<String> temp =
                authorities
                .stream()
                .map((authority)->{return authority.toString();})
                .collect(Collectors.toList());
        temp.add("ROLE_" + roleName.toUpperCase());

        this.roleAuthorities = temp.toArray(this.roleAuthorities);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
