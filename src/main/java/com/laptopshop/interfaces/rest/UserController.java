package com.laptopshop.interfaces.rest;

import com.laptopshop.application.user.dto.*;
import com.laptopshop.application.user.service.UserService;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    private Long getUserId(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"))
                .getId();
    }

    // GET /api/user/profile
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication auth) {
        return ResponseEntity.ok(userService.getProfile(getUserId(auth)));
    }

    // PUT /api/user/profile
    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication auth,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(getUserId(auth), request));
    }

    // PUT /api/user/password
    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            Authentication auth,
            @RequestBody ChangePasswordRequest request) {
        userService.changePassword(getUserId(auth), request);
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

    // GET /api/user/addresses
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponse>> getAddresses(Authentication auth) {
        return ResponseEntity.ok(userService.getAddresses(getUserId(auth)));
    }

    // POST /api/user/addresses
    @PostMapping("/addresses")
    public ResponseEntity<AddressResponse> addAddress(
            Authentication auth,
            @RequestBody AddressRequest request) {
        return ResponseEntity.ok(userService.addAddress(getUserId(auth), request));
    }

    // PUT /api/user/addresses/{id}
    @PutMapping("/addresses/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
            Authentication auth,
            @PathVariable Long id,
            @RequestBody AddressRequest request) {
        return ResponseEntity.ok(userService.updateAddress(getUserId(auth), id, request));
    }

    // DELETE /api/user/addresses/{id}
    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(
            Authentication auth,
            @PathVariable Long id) {
        userService.deleteAddress(getUserId(auth), id);
        return ResponseEntity.noContent().build();
    }
}
