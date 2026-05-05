package com.itc.uniwallet.wallet.dto;

import lombok.Data;

@Data
public class CallbackPayload {

    private String uniwalletTransactionId;
    private String networkTransactionId;
    private String refNo;
    private String productId;
    private String msisdn;
    private String amount;
    private String narration;
    private String timestamp;
    private String responseCode;
    private String responseMessage;
    private String network;
    private String transflowId;
}
