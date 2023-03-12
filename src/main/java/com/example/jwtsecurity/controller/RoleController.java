package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.model.RoleModel;
import com.example.jwtsecurity.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;

@RestController
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @PostMapping("/create-role")
    public RoleModel createRole(@RequestBody RoleModel roleModel) {

        return roleService.createRole(roleModel);
    }

    @GetMapping("/all-roles")
    public List<RoleModel> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public RoleModel getRoleById(@PathVariable("id") Long id) {
        return roleService.getRoleById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRoleById(@PathVariable("id") Long id) {
        roleService.deleteRoleById(id);
    }

    @PutMapping("/update-role/{id}")
    public RoleModel updateRole(@PathVariable("id") Long id, @RequestBody RoleModel roleModel) {
        return roleService.updateRole(id, roleModel);
    }

}
