package com.laptopshop.infrastructure.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Component
public class VNPayUtils {
    @Value("${vnpay.tmn-code}")
    private String tmnCode;

    @Value("${vnpay.hash-secret}")
    private String hashSecret;

    @Value("${vnpay.url}")
    private String vnpayUrl;

    @Value("${vnpay.return-url}")
    private String returnUrl;

    public String createPaymentUrl(Long orderId, long amount, String orderInfo) {
        String vnpTxnRef = String.valueOf(orderId);
        String vnpCreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", tmnCode);
        params.put("vnp_Amount", String.valueOf(amount * 100)); // VNPay nhân 100
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", vnpTxnRef);
        params.put("vnp_OrderInfo", orderInfo);
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", returnUrl);
        params.put("vnp_IpAddr", "127.0.0.1");
        params.put("vnp_CreateDate", vnpCreateDate);

        // Build query string
        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String encodedValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
            query.append(encodedKey).append("=").append(encodedValue).append("&");
            hashData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        // Xóa & cuối
        query.deleteCharAt(query.length() - 1);
        hashData.deleteCharAt(hashData.length() - 1);

        String secureHash = hmacSHA512(hashSecret, hashData.toString());
        return vnpayUrl + "?" + query + "&vnp_SecureHash=" + secureHash;
    }

    public boolean verifyCallback(Map<String, String> params) {
        String secureHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        Map<String, String> sorted = new TreeMap<>(params);
        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : sorted.entrySet()) {
            hashData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        hashData.deleteCharAt(hashData.length() - 1);

        String calculatedHash = hmacSHA512(hashSecret, hashData.toString());
        return calculatedHash.equals(secureHash);
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo HMAC SHA512", e);
        }
    }
}
