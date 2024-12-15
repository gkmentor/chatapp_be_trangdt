package springboot.chatapp.service;

import springboot.chatapp.dto.LoginRequest;
import springboot.chatapp.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
