package com.study.springsecurity.springsecurityjwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    //this method will be called by spring framework to load a user
    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        //returns user, password and authorities.
        //for now authorities are empty
        return new User("testuser","testpassword", new ArrayList<>());
    }
}
