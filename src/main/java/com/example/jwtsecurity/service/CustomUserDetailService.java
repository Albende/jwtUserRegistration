package com.example.jwtsecurity.service;


import com.example.jwtsecurity.entity.RoleEntity;
import com.example.jwtsecurity.entity.UserEntity;
import com.example.jwtsecurity.exceptions.UserAlreadyRegisteredException;
import com.example.jwtsecurity.model.RoleModel;
import com.example.jwtsecurity.model.UserModel;
import com.example.jwtsecurity.repository.RoleRepository;
import com.example.jwtsecurity.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NoArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserModel register(UserModel userModel) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userModel, userEntity);
        List<RoleEntity> roleEntities = new ArrayList<>();

        //fetch every role from DB based on role ID and then set this role to user entity roles

        for (RoleModel rm : userModel.getRoles()) {
            Optional<RoleEntity> optRole = roleRepository.findById(rm.getRoleId());

            if (optRole.isPresent()) {
                roleEntities.add(optRole.get());
            }
        }

        userEntity.setRoles(roleEntities);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity = userRepository.save(userEntity);
        BeanUtils.copyProperties(userEntity, userModel);
        List<RoleModel> roleModels = new ArrayList<>();
        RoleModel rm = null;
        for (RoleEntity re : userEntity.getRoles()) {
            rm = new RoleModel();
            rm.setRoleName(re.getRoleName());
            rm.setRoleId(re.getRoleId());
            roleModels.add(rm);
        }
        userModel.setRoles(roleModels);
        return userModel;
    }

    //this methods does the validation for user existence
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(userName);

        if (userEntity == null) {

            throw new UsernameNotFoundException("Username not found " + userName);
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userEntity, userModel);
        List<RoleModel> roleModels = new ArrayList<>();
        RoleModel rm = null;
        for (RoleEntity re : userEntity.getRoles()) {
            rm = new RoleModel();
            rm.setRoleName(re.getRoleName());
            rm.setRoleId(re.getRoleId());
            roleModels.add(rm);
        }
        userModel.setRoles(roleModels);
        return userModel;
    }
}
