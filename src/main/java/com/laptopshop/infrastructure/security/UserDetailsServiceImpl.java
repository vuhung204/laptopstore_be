package com.laptopshop.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptopshop.domain.store.entity.Staff;
import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.user.enums.UserStatus;
import com.laptopshop.domain.store.repository.StaffRepository;
import com.laptopshop.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Tìm trong bảng users
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getStatus() == UserStatus.LOCKED)
                throw new LockedException("Tài khoản đã bị khóa");
            if (user.getStatus() == UserStatus.UNVERIFIED)
                throw new DisabledException("Tài khoản chưa xác thực email");

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPasswordHash())
                    .authorities("ROLE_USER")
                    .build();
        }

        // Tìm trong bảng staff
        Staff staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy tài khoản: " + email));

        if (!staff.getIsActive())
            throw new LockedException("Tài khoản nhân viên đã bị vô hiệu hóa");

        // Lấy cả role lẫn permissions làm authorities
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (staff.getRole() != null) {
            // Thêm role: ROLE_SUPER_ADMIN, ROLE_STORE_MANAGER,...
            authorities.add(new SimpleGrantedAuthority(
                    "ROLE_" + staff.getRole().getName().toUpperCase()));

            // Thêm từng permission: orders, inventory, reports,...
            if (staff.getRole().getPermissions() != null) {
                List<String> perms = parsePermissions(staff.getRole().getPermissions());
                perms.forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));
            }
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(staff.getEmail())
                .password(staff.getPasswordHash())
                .authorities(authorities)
                .build();
    }

    private List<String> parsePermissions(String permissionsJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(permissionsJson,
                    mapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (Exception e) {
            return List.of();
        }
    }
}
