//package com.laptopshop.models.settings;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//
//@Table(name = "site_settings")
//@Entity
//@Getter
//@Setter
//public class SiteSetting {
//
//    @Id
//    @Column(name = "setting_key", length = 100)
//    private String key;
//
//    @Column(name = "setting_value", columnDefinition = "TEXT")
//    private String value;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
//}
