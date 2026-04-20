package com.laptopshop.application.user.service;

import com.laptopshop.application.user.dto.*;
import com.laptopshop.domain.order.entity.Address;
import com.laptopshop.domain.order.repository.AddressRepository;
import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    // ===== Profile =====

    public UserProfileResponse getProfile(Long userId) {
        return userRepository.findById(userId)
                .map(UserProfileResponse::from)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
    }

    @Transactional
    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());

        userRepository.save(user);
        return UserProfileResponse.from(user);
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Mật khẩu hiện tại không đúng");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // ===== Address =====

    public List<AddressResponse> getAddresses(Long userId) {
        return addressRepository.findAllByUserId(userId)
                .stream()
                .map(AddressResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public AddressResponse addAddress(Long userId, AddressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        // Nếu set default thì bỏ default các địa chỉ cũ
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.findByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(a -> {
                        a.setIsDefault(false);
                        addressRepository.save(a);
                    });
        }

        Address address = new Address();
        address.setUser(user);
        address.setRecipientName(request.getRecipientName());
        address.setPhone(request.getPhone());
        address.setAddressLine(request.getAddressLine());
        address.setWard(request.getWard());
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());
        address.setIsDefault(Boolean.TRUE.equals(request.getIsDefault()));

        addressRepository.save(address);
        return AddressResponse.from(address);
    }

    @Transactional
    public AddressResponse updateAddress(Long userId, Long addressId, AddressRequest request) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));

        // Nếu set default thì bỏ default các địa chỉ cũ
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.findByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(a -> {
                        if (!a.getId().equals(addressId)) {
                            a.setIsDefault(false);
                            addressRepository.save(a);
                        }
                    });
        }

        if (request.getRecipientName() != null) address.setRecipientName(request.getRecipientName());
        if (request.getPhone() != null) address.setPhone(request.getPhone());
        if (request.getAddressLine() != null) address.setAddressLine(request.getAddressLine());
        if (request.getWard() != null) address.setWard(request.getWard());
        if (request.getDistrict() != null) address.setDistrict(request.getDistrict());
        if (request.getCity() != null) address.setCity(request.getCity());
        if (request.getIsDefault() != null) address.setIsDefault(request.getIsDefault());

        addressRepository.save(address);
        return AddressResponse.from(address);
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));

        if (Boolean.TRUE.equals(address.getIsDefault())) {
            throw new RuntimeException("Không thể xóa địa chỉ mặc định");
        }

        addressRepository.delete(address);
    }
}
