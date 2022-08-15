package com.example.slowdlvy.security.config;

import com.example.slowdlvy.security.jwt.JwtAuthenticationFilter;
import com.example.slowdlvy.security.jwt.JwtManager;
import com.example.slowdlvy.security.oauth.OAuth2AuthenticationFailureHandler;
import com.example.slowdlvy.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.example.slowdlvy.security.service.PrincipalOauth2UserService;
import com.example.slowdlvy.service.member.RefreshTokenService;
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
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final RefreshTokenService refreshTokenService;

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
                        .antMatchers("/auth/join", "/auth/login","/auth/reissue","/carts/**").permitAll()
                        .anyRequest().authenticated())

                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                .and()

                .successHandler(new OAuth2AuthenticationSuccessHandler(jwtManager,refreshTokenService))
                .failureHandler( new OAuth2AuthenticationFailureHandler())
                .and()
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
