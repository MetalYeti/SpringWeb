package ru.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    public void authConfig(AuthenticationManagerBuilder auth,
                           PasswordEncoder passwordEncoder,
                           UserDetailsService userDetails) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("mem_admin")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN");

        auth.userDetailsService(userDetails);
    }

    @Configuration
    public static class UIWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/**/*.css", "/**/*.js")
                    .permitAll()
                    .antMatchers("/").permitAll()
                    .antMatchers("/users/**").hasAnyRole("ADMIN", "SUPERADMIN")
                    .antMatchers("/products/**").hasAnyRole("USER", "ADMIN")
                    .and()
                    .formLogin();
        }
    }
}
