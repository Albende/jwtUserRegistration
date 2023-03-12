package com.example.jwtsecurity.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@NoArgsConstructor
//@Entity
//@Table(name = "jwttesting")
public class JwtResponse {

    private String token;
}
