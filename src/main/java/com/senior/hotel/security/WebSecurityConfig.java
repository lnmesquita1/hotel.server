package com.senior.hotel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableWebSecurity
@EnableAuthorizationServer
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.anyRequest().authenticated()
        	.and()
        	.httpBasic()
        	.and()
        	.csrf().disable();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
    		.withUser("admin").password("$2a$10$9tO8UzFhimOgyWNCTyzh1uetkfD2MdSU4vecwycARt.5tCAnFOQPa").roles("ADMIN")
    		.and()
    		.withUser("cleiton").password("$2a$10$9tO8UzFhimOgyWNCTyzh1uetkfD2MdSU4vecwycARt.5tCAnFOQPa").roles("USER");
    }
    
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    
   
}
