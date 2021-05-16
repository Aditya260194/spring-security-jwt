package com.study.springsecurity.springsecurityjwt.config;

import com.study.springsecurity.springsecurityjwt.filter.JwtRequestFilter;
import com.study.springsecurity.springsecurityjwt.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
//works with @Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    //use configure method to set values to AuthenticationManagerBuilder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //set our user details service in authentication manager
        auth.userDetailsService(myUserDetailsService);
    }

    //to tell spring to not check authentication if authenticate api is called as authenticate api will be doing authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //disables Cross-Site Request Forgery (CSRF)
        http.csrf().disable()
                //permit request to authenticate page without checking authentication
                .authorizeRequests().antMatchers("/hello/authenticate").permitAll()
                //request to other urls needs to be authenticated
                .anyRequest().authenticated()
                //avoid creating session by spring security as we use JWT
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //making sure our filter is called before UserNamePassword Filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    //without password encoder, it throws error
    //java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    //needed to create bean of authenticate manager
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
