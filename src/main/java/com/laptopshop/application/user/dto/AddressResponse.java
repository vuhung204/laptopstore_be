package com.laptopshop.application.user.dto;

import com.laptopshop.domain.order.entity.Address;
import lombok.Getter;

@Getter
public class AddressResponse {
    private Long id;
    private String recipientName;
    private String phone;
    private String addressLine;
    private String ward;
    private String district;
    private String city;
    private Boolean isDefault;

    public static AddressResponse from(Address address) {
        AddressResponse dto = new AddressResponse();
        dto.id = address.getId();
        dto.recipientName = address.getRecipientName();
        dto.phone = address.getPhone();
        dto.addressLine = address.getAddressLine();
        dto.ward = address.getWard();
        dto.district = address.getDistrict();
        dto.city = address.getCity();
        dto.isDefault = address.getIsDefault();
        return dto;
    }
}
