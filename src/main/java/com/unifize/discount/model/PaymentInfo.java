package com.unifize.discount.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInfo {
    private String method; // CARD, UPI, etc
    private String bankName; // Optional
    private String cardType;
}
