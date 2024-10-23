package com.pinsoft.intern.controller;

import com.pinsoft.intern.dto.RoleDTO;
import com.pinsoft.intern.entity.Role;
import com.pinsoft.intern.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/roles")
@Data
@AllArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAll() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) {
        Role role = roleService.find(id);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody RoleDTO roleDTO) {
        Role savedRole = roleService.save(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
