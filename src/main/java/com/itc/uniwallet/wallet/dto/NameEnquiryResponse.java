package com.itc.uniwallet.wallet.dto;

import lombok.Data;

@Data
public class NameEnquiryResponse {

    private String responseCode;
    private String responseMessage;
    private String name;
}
