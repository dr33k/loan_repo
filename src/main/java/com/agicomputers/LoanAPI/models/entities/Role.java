package com.agicomputers.LoanAPI.models.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
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

    @Column(columnDefinition = "TEXT[] NOT NULL")
    private String[] roleAuthorities;

    @Column(nullable = false)
    private String roleDescription;


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

    public void setRoleAuthorities(String[] roleAuthorities) {
        this.roleAuthorities = roleAuthorities;
    }

    public void setRoleAuthorities(Set<? extends GrantedAuthority> authorities) {
        String[] stringRoleAuthorities = {};
        authorities.stream().map((authority)->{
            return authority.toString();
        }).collect(Collectors.toSet()).toArray(stringRoleAuthorities);

        this.roleAuthorities = stringRoleAuthorities;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

}
