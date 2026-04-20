package com.laptopshop.infrastructure.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MoMoUtils {
    @Value("${momo.partner-code}")
    private String partnerCode;

    @Value("${momo.access-key}")
    private String accessKey;

    @Value("${momo.secret-key}")
    private String secretKey;

    @Value("${momo.url}")
    private String momoUrl;

    @Value("${momo.return-url}")
    private String returnUrl;

    @Value("${momo.notify-url}")
    private String notifyUrl;

    public String createPaymentUrl(Long orderId, long amount, String orderInfo) throws Exception {
        String requestId = UUID.randomUUID().toString();
        String orderId_ = "ORDER_" + orderId;

        String rawSignature = "accessKey=" + accessKey
                + "&amount=" + amount
                + "&extraData="
                + "&ipnUrl=" + notifyUrl
                + "&orderId=" + orderId_
                + "&orderInfo=" + orderInfo
                + "&partnerCode=" + partnerCode
                + "&redirectUrl=" + returnUrl
                + "&requestId=" + requestId
                + "&requestType=payWithATM";

        String signature = hmacSHA256(secretKey, rawSignature);

        Map<String, Object> body = new HashMap<>();
        body.put("partnerCode", partnerCode);
        body.put("accessKey", accessKey);
        body.put("requestId", requestId);
        body.put("amount", amount);
        body.put("orderId", orderId_);
        body.put("orderInfo", orderInfo);
        body.put("redirectUrl", returnUrl);
        body.put("ipnUrl", notifyUrl);
        body.put("extraData", "");
        body.put("requestType", "payWithATM");
        body.put("signature", signature);
        body.put("lang", "vi");

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(body);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(momoUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> result = mapper.readValue(response.body(), Map.class);

        return (String) result.get("payUrl");
    }

    public boolean verifyCallback(Map<String, String> params) {
        String signature = params.get("signature");
        String rawSignature = "accessKey=" + accessKey
                + "&amount=" + params.get("amount")
                + "&extraData=" + params.get("extraData")
                + "&message=" + params.get("message")
                + "&orderId=" + params.get("orderId")
                + "&orderInfo=" + params.get("orderInfo")
                + "&orderType=" + params.get("orderType")
                + "&partnerCode=" + params.get("partnerCode")
                + "&payType=" + params.get("payType")
                + "&requestId=" + params.get("requestId")
                + "&responseTime=" + params.get("responseTime")
                + "&resultCode=" + params.get("resultCode")
                + "&transId=" + params.get("transId");

        return hmacSHA256(secretKey, rawSignature).equals(signature);
    }

    private String hmacSHA256(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo HMAC SHA256", e);
        }
    }
}
