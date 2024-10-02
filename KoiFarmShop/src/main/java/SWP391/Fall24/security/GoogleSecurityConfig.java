package SWP391.Fall24.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class GoogleSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChainGoogleConfig (HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/","/login").permitAll()// cho phép truy cập không cần đăng nhập
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/save",true)
                );
        return http.build();
    }
}