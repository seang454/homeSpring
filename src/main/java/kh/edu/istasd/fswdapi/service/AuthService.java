package kh.edu.istasd.fswdapi.service;

import kh.edu.istasd.fswdapi.dto.RegisterRequest;
import kh.edu.istasd.fswdapi.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    void verify(String userId);
}
