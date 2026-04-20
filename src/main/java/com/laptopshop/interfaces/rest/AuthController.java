package com.laptopshop.interfaces.rest;

import com.laptopshop.application.auth.dto.JwtResponse;
import com.laptopshop.application.auth.dto.LoginRequest;
import com.laptopshop.application.auth.dto.RegisterRequest;
import com.laptopshop.application.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body(Map.of("error", "Tài khoản chưa xác thực email"));
        } catch (LockedException e) {
            return ResponseEntity.status(403).body(Map.of("error", "Tài khoản đã bị khóa"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Email hoặc mật khẩu không đúng"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Đăng ký thành công");
    }
}
