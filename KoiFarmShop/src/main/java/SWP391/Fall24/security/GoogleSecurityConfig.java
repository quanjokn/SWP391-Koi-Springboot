//package SWP391.Fall24.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class GoogleSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChainGoogleConfig (HttpSecurity http) throws Exception{
//        return http
//                .authorizeHttpRequests(auth ->{
//                    auth.requestMatchers("/posts").permitAll();
//                    auth.requestMatchers("/caringOrder").permitAll();
//                    auth.requestMatchers("/cart").permitAll();
//                    auth.requestMatchers("/consignOrder").permitAll();
//                    auth.requestMatchers("/email").permitAll();
//                    auth.requestMatchers("/fish/fishes-list").permitAll();
//                    auth.requestMatchers("/orderDetail").permitAll();
//                    auth.requestMatchers("/user").permitAll();
//                    auth.anyRequest().authenticated();
//                })
//                .oauth2Login(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .build();
//    }
//}