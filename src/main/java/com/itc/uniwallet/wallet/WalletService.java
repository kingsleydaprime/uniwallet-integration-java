package com.itc.uniwallet.wallet;

import com.itc.uniwallet.config.UniwalletConfig;
import com.itc.uniwallet.wallet.dto.NameEnquiryRequest;
import com.itc.uniwallet.wallet.dto.NameEnquiryResponse;
import com.itc.uniwallet.wallet.dto.TransactionRequest;
import com.itc.uniwallet.wallet.dto.TransactionResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final RestTemplate restTemplate;
    private final UniwalletConfig config;

    public NameEnquiryResponse nameEnquiry(NameEnquiryRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-key", config.getApiKey());
        headers.set("x-country", config.getCountry());

        Map<String, String> body = new HashMap<>();
        body.put("transflowId", config.getTransflowId());
        body.put("productId", config.getProductId());
        body.put("msisdn", req.getMsisdn());
        body.put("network", req.getNetwork());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(
            body,
            headers
        );

        ResponseEntity<NameEnquiryResponse> response = restTemplate.exchange(
            config.getBaseUrl() + "/uniwallet/v2/name-enquiry",
            HttpMethod.POST,
            entity,
            NameEnquiryResponse.class
        );

        return response.getBody();
    }

    public TransactionResponse debit(TransactionRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-key", config.getApiKey());
        headers.set("x-country", config.getCountry());

        Map<String, String> body = new HashMap<>();
        body.put("transflowId", config.getTransflowId());
        body.put("productId", config.getProductId());
        // body.put("refNo", UUID.randomUUID().toString());
        body.put("refNo", "PSPRT00122332");
        body.put("msisdn", req.getMsisdn());
        body.put("amount", req.getAmount());
        body.put("network", req.getNetwork());
        body.put("narration", req.getNarration());
        body.put("currency", "GHS");
        body.put("additionalRef", "{}");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(
            body,
            headers
        );

        ResponseEntity<TransactionResponse> response = restTemplate.exchange(
            config.getBaseUrl() + "/uniwallet/v2/debit",
            HttpMethod.POST,
            entity,
            TransactionResponse.class
        );

        return response.getBody();
    }

    public TransactionResponse credit(TransactionRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-key", config.getApiKey());
        headers.set("x-country", config.getCountry());

        Map<String, String> body = new HashMap<>();
        body.put("transflowId", config.getTransflowId());
        body.put("productId", config.getProductId());
        // body.put("refNo", UUID.randomUUID().toString());
        body.put("refNo", "PSPRT00122332");
        body.put("msisdn", req.getMsisdn());
        body.put("amount", req.getAmount());
        body.put("network", req.getNetwork());
        body.put("narration", req.getNarration());
        body.put("currency", "GHS");
        body.put("additionalRef", "{}");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(
            body,
            headers
        );

        ResponseEntity<TransactionResponse> response = restTemplate.exchange(
            config.getBaseUrl() + "/uniwallet/v2/credit",
            HttpMethod.POST,
            entity,
            TransactionResponse.class
        );

        return response.getBody();
    }

    public TransactionResponse checkStatus(String refNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-key", config.getApiKey());
        headers.set("x-country", config.getCountry());

        Map<String, String> body = new HashMap<>();
        body.put("transflowId", config.getTransflowId());
        body.put("productId", config.getProductId());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<TransactionResponse> response = restTemplate.exchange(
                config.getBaseUrl() + "/uniwallet/v2/transaction-status/" + refNo,
                HttpMethod.GET,
                entity,
                TransactionResponse.class
        );

        return response.getBody();
    }

    public Object provisionSandbox(String callbackUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-key", config.getApiKey());
        headers.set("x-country", config.getCountry());

        List<Map<String, String>> body = new ArrayList<>();
        Map<String, String> item = new HashMap<>();
        item.put("transflowId", config.getTransflowId());
        item.put("productId", config.getProductId());
        item.put("callbackUrl", callbackUrl);
        body.add(item);

        HttpEntity<List<Map<String, String>>> entity = new HttpEntity<>(
            body,
            headers
        );

        ResponseEntity<Object> response = restTemplate.exchange(
            config.getBaseUrl() + "/uniwallet/v2/provision/sandbox",
            HttpMethod.POST,
            entity,
            Object.class
        );

        return response.getBody();
    }
}
