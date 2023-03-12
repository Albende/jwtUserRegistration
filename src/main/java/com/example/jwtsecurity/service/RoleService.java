package com.example.jwtsecurity.service;

import com.example.jwtsecurity.entity.RoleEntity;
import com.example.jwtsecurity.model.RoleModel;

import java.util.List;

public interface RoleService {
    public RoleModel createRole(RoleModel roleModel);

    public RoleModel updateRole(Long id, RoleModel roleModel);

    public List<RoleModel> getAllRoles();

    public RoleModel getRoleById(Long id);

    public void deleteRoleById(Long id);
}
