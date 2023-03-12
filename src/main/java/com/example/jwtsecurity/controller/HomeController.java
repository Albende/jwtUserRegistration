package com.example.jwtsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    //@PreAuthorize("hasAnyRole('ADMIN','USER')") // I should declare role as "ROLE_USER" or "ROLE_ADMIN" in database. otherwise Spring boot will not be able to fetch it correctly
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')") but in this case Spring boot will be able to fetch it as we used "hasAuthority" option there

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public String Hello() {
        return "Hello World!";
    }

    //we can apply this rule to our application as Pre or Post Authorize
    //and we can decide who can delete user, who can update user
    //who can see all the users, who can create role and so on so far.

}
