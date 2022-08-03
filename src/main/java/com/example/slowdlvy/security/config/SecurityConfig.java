package com.example.slowdlvy.security.config;

import com.example.slowdlvy.security.jwt.JwtAuthenticationFilter;
import com.example.slowdlvy.security.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final JwtManager jwtManager;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomFilter())
                .and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/auth/join", "/auth/login","/auth/refresh").permitAll()
                        .antMatchers("/member").access("hasRole('ROLE_USER')")
                        .anyRequest().authenticated())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class MyCustomFilter extends AbstractHttpConfigurer<MyCustomFilter, HttpSecurity>{
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            builder
                    .addFilter(corsConfig.corsFilter())
                    .addFilterBefore(new JwtAuthenticationFilter(jwtManager), UsernamePasswordAuthenticationFilter.class);
        }
    }
}
