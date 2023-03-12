package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.exceptions.UserAlreadyRegisteredException;
import com.example.jwtsecurity.model.JwtRequest;
import com.example.jwtsecurity.model.JwtResponse;
import com.example.jwtsecurity.model.UserModel;
import com.example.jwtsecurity.repository.UserRepository;
import com.example.jwtsecurity.service.CustomUserDetailService;
import com.example.jwtsecurity.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class JwtController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) {
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword());

        //authenticate the user firstly
        authenticationManager.authenticate(upat);

        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUserName());

        String jwtToken = jwtUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse(jwtToken);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody UserModel userModel) {

        UserModel userModel1 = customUserDetailService.register(userModel);
        return new ResponseEntity<UserModel>(userModel1, HttpStatus.CREATED);
    }

    @GetMapping("/current-user")
    public UserModel getCurrentUser(Principal principal) {
        UserDetails userDetails = ((UserModel) this.customUserDetailService.loadUserByUsername(principal.getName()));
        return (UserModel) userDetails;
    }

}
