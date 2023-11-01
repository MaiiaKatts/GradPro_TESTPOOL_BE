package de.ait.tp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static de.ait.tp.security.config.SecurityExceptionHandlers.*;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
        httpSecurity
                .authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/register/**").permitAll()
                .antMatchers("/api/users/confirm/**").permitAll()
                .antMatchers("/api/**").authenticated()

                .and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(ENTRY_POINT,
                        new AntPathRequestMatcher("/api/**"))
                .accessDeniedHandler(ACCESS_DENIED_HANDLER)

                .and()
                .formLogin()
                .loginProcessingUrl("/api/login")
                .successHandler(LOGIN_SUCCESS_HANDLER)
                .failureHandler(LOGIN_FAILURE_HANDLER);

        httpSecurity.logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(LOGOUT_SUCCESS_HANDLER);

        return httpSecurity.build();

    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder,
                                                         AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }
}