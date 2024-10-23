package com.pinsoft.intern.service;

import com.pinsoft.intern.dto.RoleDTO;
import com.pinsoft.intern.entity.Role;
import com.pinsoft.intern.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
public class RoleService {

    private final RoleRepository roleRepository;


    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role find(int id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        return role;
    }

    public Role save(RoleDTO roleDTO) {
        String roleName = roleDTO.getRoleName();

        if (roleName == null || roleName.isEmpty()) {
            throw new EntityNotFoundException("Role name not found");
        }

        Role role = new Role();
        role.setRoleName(roleName);
        return roleRepository.save(role);
    }

    public void delete(int id) {
        Role role = find(id);
        roleRepository.delete(role);
    }

}
