package com.agicomputers.LoanAPI.models.entities;

import com.agicomputers.LoanAPI.models.enums.AppUserAuthorities;
import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="role")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer roleId;

    @Column(nullable = false)
    private String roleName;

    @Column(nullable = false)
    private String[] roleAuthorities;

    @Column(nullable = false)
    private String roleDescription;

    public Role(Integer id, String roleName, Set<? extends GrantedAuthority> authorities) {
        this.roleId = id;
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

    public void setRoleAuthorities(String... roleAuthorities) {
        String[] stringRoleAuthorities= {};
        //Add the "ROLE" authority
        Set<String> stringRoleAuthoritiesSet = Set.of(roleAuthorities);
        if(!stringRoleAuthoritiesSet.contains("ROLE_" + roleName.toUpperCase()))
            stringRoleAuthoritiesSet.add("ROLE_" + roleName.toUpperCase());
        stringRoleAuthorities = stringRoleAuthoritiesSet.toArray(stringRoleAuthorities);

        this.roleAuthorities = stringRoleAuthorities;
    }

}
