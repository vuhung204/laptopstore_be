package com.laptopshop.application.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    private String fullName;
    private String phone;
    private String avatarUrl;
}
