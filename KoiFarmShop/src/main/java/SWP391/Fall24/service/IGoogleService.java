package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Users;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface IGoogleService {
    public Users saveUsersByEmail (OAuth2AuthenticationToken auth);
}