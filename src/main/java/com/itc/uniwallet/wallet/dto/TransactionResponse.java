package com.itc.uniwallet.wallet.dto;

import lombok.Data;

@Data
public class TransactionResponse {

    private String responseCode;
    private String responseMessage;
    private String uniwalletTransactionId;
}
