package com.itc.uniwallet.wallet.dto;

import lombok.Data;

@Data
public class TransactionRequest {

    private String msisdn;
    private String amount;
    private String network;
    private String narration;
}
