package SWP391.Fall24.config;

import SWP391.Fall24.service.OAuth.CustomOAuth2User;
import SWP391.Fall24.service.OAuth.CustomOAuth2UserService;
import SWP391.Fall24.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private String[] PUBLIC_ENDPOINTS = {"/posts/**", "/caringOrder/**", "/cart/**",
                                        "/consignOrder/**", "/email/**", "/fish/**",
                                        "/orderDetail/**", "/user/**", "/order/**", "/feedback/**"};

    private String[] STAFF_ENDPOINTS = {"/orderManagement/**", "/consignManagement/**", "/caringManagement/**"};

    private String[] MANAGER_ENDPOINTS = {"/userManagement/**"};

    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChainWebConfig(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(request->{
            request.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
            request.requestMatchers("/oauth/**").permitAll();
            request.requestMatchers(STAFF_ENDPOINTS).hasAnyAuthority("Staff");
            request.requestMatchers(MANAGER_ENDPOINTS).hasAnyAuthority("Manager");
            request.anyRequest().authenticated();
        });
        http.oauth2Login().loginPage("/user/login")
                .userInfoEndpoint().userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                        String jwt = userService.processOAuthPostLogin(oauthUser.getEmail(), oauthUser.getName());
                        response.sendRedirect("http://localhost:3000/oauth/callback?jwt=" + jwt);
                    }
                })
                ;
        http.exceptionHandling(e->{
          e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        });
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
