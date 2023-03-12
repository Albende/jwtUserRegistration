package com.example.jwtsecurity.service;

import com.example.jwtsecurity.entity.RoleEntity;
import com.example.jwtsecurity.exceptions.RoleNotFoundException;
import com.example.jwtsecurity.model.RoleModel;
import com.example.jwtsecurity.repository.RoleRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public RoleModel createRole(RoleModel roleModel) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(roleModel, roleEntity);
        roleRepository.save(roleEntity);
        BeanUtils.copyProperties(roleEntity, roleModel);
        return roleModel;
    }

    @Override
    public RoleModel updateRole(Long id, RoleModel roleModel) {
        RoleEntity roleEntity = roleRepository.findById(id).
                orElseThrow(() -> new RoleNotFoundException("Role not found with id " + id));
        roleEntity.setRoleName(roleModel.getRoleName());
        roleRepository.save(roleEntity);
        BeanUtils.copyProperties(roleEntity, roleModel);
        return roleModel;
    }

    @Override
    public List<RoleModel> getAllRoles() {

        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleModel> roleModels = new ArrayList<>();

        for (RoleEntity role : roleEntities) {
            RoleModel roleModel = new RoleModel();
            BeanUtils.copyProperties(role, roleModel);
            roleModels.add(roleModel);
        }
        return roleModels;

    }

    @Override
    public RoleModel getRoleById(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).get();
        RoleModel roleModel = new RoleModel();
        BeanUtils.copyProperties(roleEntity, roleModel);
        return roleModel;
    }

    @Override
    public void deleteRoleById(Long id) {

        roleRepository.deleteById(id);
    }
}
