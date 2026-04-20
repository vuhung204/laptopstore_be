package com.laptopshop.application.auth.service;

import com.laptopshop.application.auth.dto.JwtResponse;
import com.laptopshop.application.auth.dto.LoginRequest;
import com.laptopshop.application.auth.dto.RegisterRequest;
import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.user.enums.UserStatus;
import com.laptopshop.domain.store.entity.Staff;
import com.laptopshop.domain.user.exception.AuthException;
import com.laptopshop.domain.store.repository.StaffRepository;
import com.laptopshop.domain.user.repository.UserRepository;
import com.laptopshop.infrastructure.security.JwtUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public JwtResponse login(LoginRequest request) {
        System.out.println(">>> Login attempt: " + request.getEmail());
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
            System.out.println(">>> Auth success");

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);
            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            // Lấy fullName + cập nhật lastLogin nếu là User
            String fullName = userRepository.findByEmail(request.getEmail())
                    .map(user -> {
                        user.setLastLogin(LocalDateTime.now());
                        userRepository.save(user);
                        return user.getFullName();
                    })
                    .orElseGet(() -> staffRepository.findByEmail(request.getEmail())
                            .map(Staff::getFullName)
                            .orElse(""));

            return new JwtResponse(token, request.getEmail(), fullName, role);
        } catch (Exception e) {
            System.out.println(">>> Auth failed: " + e.getClass().getName() + " - " + e.getMessage());
            throw e;
        }
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email đã được sử dụng");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
    }
}
