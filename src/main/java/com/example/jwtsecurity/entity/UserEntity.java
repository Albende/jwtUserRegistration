package com.example.jwtsecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USER_TABLE")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    @SequenceGenerator(name = "user_seq_generator", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @Valid
    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    //cascadetype.all means that whatever operation we will do in our entity it should be applied to our roleentity as well
    @JsonManagedReference //helps avoid circular dependency in bidirectional mapping
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<RoleEntity> roles = new ArrayList<>();
}
