package com.itc.uniwallet.wallet;

import com.itc.uniwallet.wallet.dto.NameEnquiryRequest;
import com.itc.uniwallet.wallet.dto.NameEnquiryResponse;
import com.itc.uniwallet.wallet.dto.TransactionRequest;
import com.itc.uniwallet.wallet.dto.TransactionResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    @PostMapping("/name-enquiry")
    public ResponseEntity<NameEnquiryResponse> nameEnquiry(
        @RequestBody NameEnquiryRequest request
    ) {
        return ResponseEntity.ok(walletService.nameEnquiry(request));
    }

    @PostMapping("/debit")
    public ResponseEntity<TransactionResponse> debit(
        @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(walletService.debit(request));
    }

    @PostMapping("/credit")
    public ResponseEntity<TransactionResponse> credit(
        @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(walletService.credit(request));
    }

    @PostMapping("/provision-sandbox")
    public ResponseEntity<Object> provisionSandbox(
        @RequestParam String callbackUrl
    ) {
        return ResponseEntity.ok(walletService.provisionSandbox(callbackUrl));
    }

    @GetMapping("/status/{refNo}")
    public ResponseEntity<TransactionResponse> checkStatus(
        @PathVariable String refNo
    ) {
        return ResponseEntity.ok(walletService.checkStatus(refNo));
    }
}
