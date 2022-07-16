package com.agicomputers.LoanAPI.services.role_service;

import com.agicomputers.LoanAPI.models.dto.RoleDTO;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.repositories.user_repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public HashSet<RoleDTO> getAllRoles() {
        log.info("______________________Getting all roles_______________________");
        Set<RoleDTO> roles = new HashSet<RoleDTO>(0);
        Iterable<Role> rolesFromRepo = roleRepository.findAll(PageRequest.of(0, 10));

        RoleDTO rdto;
        Iterator<Role> iterator = rolesFromRepo.iterator();
        while (iterator.hasNext()) {
            rdto = new RoleDTO();
            BeanUtils.copyProperties(iterator.next(), rdto);
            roles.add(rdto);
        }
        return (HashSet<RoleDTO>) roles;
    }

    public RoleDTO getRoleDTO(String roleName) {

        RoleDTO rdto = new RoleDTO();
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
        BeanUtils.copyProperties(roleOptional.orElse(null), rdto);

        return rdto;
    }
    public Role getRoleEntity(String roleName) {
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
        return roleOptional.orElse(null);
    }





}
