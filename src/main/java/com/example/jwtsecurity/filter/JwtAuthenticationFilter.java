package com.example.jwtsecurity.filter;

import com.example.jwtsecurity.service.CustomUserDetailService;
import com.example.jwtsecurity.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//call the filter only once per request
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private CustomUserDetailService customUserDetailService;
    private JwtUtil jwtUtil;

    @Autowired
    public void setCustomUserDetailService(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //get the jwt token from request header
        //validate jwt token if it is valid or not
        String bearerToken = request.getHeader("Authorization");
        String username = null;
        String token = null;
        //check if the token exist or not
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            //extract jwtToken from bearer token
            token = bearerToken.substring(7);
            try {
                //extract username from token
                username = jwtUtil.extractUsername(token);

                //get user details for this user
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

                //security checks
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(upat);

                } else {
                    System.out.println("Invalid token");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Invalid Bearer Token");
        }
        //if all is fine forward request to the endpoint
        filterChain.doFilter(request, response);
    }
}
