package com.itc.uniwallet.wallet.dto;

import lombok.Data;

@Data
public class NameEnquiryRequest {

    private String msisdn;
    private String network;
}

// Without lombok
//
// public class NameEnquiryRequestWithoutLombok {

//     private String msisdn;
//     private String network;

//     public NameEnquiryRequestWithoutLombok(String msisdn, String network) {
//         this.msisdn = msisdn;
//         this.network = network;
//     }

//     public String getMsisdn() {
//         return msisdn;
//     }

//     public String getNetwork() {
//         return network;
//     }
// }
