package com.study.springsecurity.springsecurityjwt.models;

//used as output
public class AuthenticationResponse {

    private final String jwtToken;

    public AuthenticationResponse(String jwtToken){
        this.jwtToken = jwtToken;
    }

    public String getJwtToken(){
        return  jwtToken;
    }

}
