package com.example.jwtsecurity.model;

import com.example.jwtsecurity.entity.RoleEntity;
import com.example.jwtsecurity.entity.UserEntity;
import com.example.jwtsecurity.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private String email;
    private String phone;
    private List<RoleModel> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<AuthorityModel> authorityModels = new HashSet<>();
        roles.forEach(
                role -> {
                    authorityModels.add(new AuthorityModel(role.getRoleName()));
                }
        );
        return authorityModels;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
