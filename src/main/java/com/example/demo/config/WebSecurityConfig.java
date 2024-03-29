package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserDetailsRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void  configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserDetailsRepository userDetailsRepository){
        return map -> {
            //sub содержит id пользователя
           String id=(String) map.get("sub");
          User user= userDetailsRepository.findById(id).orElseGet(()->{
               User newUser=new User();
               newUser.setId(id);
               newUser.setName((String)map.get("name"));
               newUser.setEmail((String)map.get("email"));
               return newUser;
                   });
         return userDetailsRepository.save(user);
         };

    }
}
