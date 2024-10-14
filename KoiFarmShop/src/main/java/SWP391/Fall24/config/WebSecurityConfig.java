package SWP391.Fall24.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private String[] PUBLIC_ENDPOINTS = {"/posts/**", "/caringOrder/**", "/cart/**",
                                        "/consignOrder/**", "/email/**", "/fish/**",
                                        "/orderDetail/**", "/user/**"};

    private String[] STAFF_ENDPOINTS = {"/orderManagement", "/consignManagement", "/caringManagement"};

    private String[] MANAGER_ENDPOINTS = {"/userManagement/**"};

    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChainWebConfig(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(request->{
            request.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
            request.requestMatchers(STAFF_ENDPOINTS).hasAnyAuthority("Staff");
            request.requestMatchers(MANAGER_ENDPOINTS).hasAnyAuthority("Manager");
            request.anyRequest().authenticated();
        });
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
