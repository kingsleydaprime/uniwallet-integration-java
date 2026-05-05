package com.itc.uniwallet.wallet;

import com.itc.uniwallet.wallet.dto.CallbackPayload;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/callback")
public class CallbackController {

    @PostMapping
    public ResponseEntity<Map<String, String>> handleCallback(
        @RequestBody CallbackPayload payload
    ) {
        log.info(
            "Callback received: refNo={}, responseCode={}, amount={}",
            payload.getRefNo(),
            payload.getResponseCode(),
            payload.getAmount()
        );

        switch (payload.getResponseCode()) {
            case "01" -> log.info(
                "Transaction SUCCESSFUL: {}",
                payload.getRefNo()
            );
            case "100" -> log.warn(
                "Transaction FAILED: {}",
                payload.getRefNo()
            );
            case "03" -> log.info(
                "Transaction PROCESSING: {}",
                payload.getRefNo()
            );
            default -> log.warn(
                "Unknown responseCode: {}",
                payload.getResponseCode()
            );
        }

        return ResponseEntity.ok(
            Map.of(
                "responseCode",
                "01",
                "responseMessage",
                "Callback Successful."
            )
        );
    }
}
