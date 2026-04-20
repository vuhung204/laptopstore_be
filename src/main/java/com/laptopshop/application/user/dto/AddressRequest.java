package com.laptopshop.application.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private String recipientName;
    private String phone;
    private String addressLine;
    private String ward;
    private String district;
    private String city;
    private Boolean isDefault = false;
}
