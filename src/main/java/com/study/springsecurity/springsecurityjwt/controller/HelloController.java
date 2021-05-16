package com.study.springsecurity.springsecurityjwt.controller;

import com.study.springsecurity.springsecurityjwt.models.AuthenticationRequest;
import com.study.springsecurity.springsecurityjwt.models.AuthenticationResponse;
import com.study.springsecurity.springsecurityjwt.service.MyUserDetailsService;
import com.study.springsecurity.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.OAuth2ResourceServerSecurityMarker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtUtil jwtTokenutil;

    //we will need to fetch jwt token from header, we have created filter for it which will intercept request and examine the header.
    @RequestMapping("/world")
    public String getHelloWorld(){
        return "Hello World - authentication complete.";
    }

    //input user and password, return jwt token for it
    //client should hold this token in cookie and send with every request to validate the request.
    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        //authentication using default technique used by spring security
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                            , authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect credentials");
        }
        // if authentication is success then we will create a jwt token
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtTokenutil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
