package com.example.laboratorio5_grupoe.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class LogConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/procesarLog")
                .usernameParameter("correo")
                .defaultSuccessUrl("/redirecRol",true);

        http.authorizeRequests()
                .antMatchers("/empleado/listar").hasAnyAuthority()
                .antMatchers(("/empleado/buscar")).hasAnyAuthority("0")
                .antMatchers("/empleado/editar","/empleado/guardar","/empleado/guardar","/empleado/buscar").hasAuthority("1");

        http.logout().logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);



    }
    @Autowired
    DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select email, password, enabled from employees where email = ?")
                .authoritiesByUsernameQuery("select email, password, enabled from employees where email = ?");
    }

}
