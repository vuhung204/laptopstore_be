package com.laptopshop.application.user.dto;

import com.laptopshop.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserProfileResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String avatarUrl;
    private String status;

    public static UserProfileResponse from(User user) {
        UserProfileResponse dto = new UserProfileResponse();
        dto.id = user.getId();
        dto.fullName = user.getFullName();
        dto.email = user.getEmail();
        dto.phone = user.getPhone();
        dto.avatarUrl = user.getAvatarUrl();
        dto.status = user.getStatus().name();
        return dto;
    }
}
