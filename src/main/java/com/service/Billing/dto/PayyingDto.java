package com.service.Billing.dto;

import lombok.Data;

@Data
public class PayyingDto {
    int taxNumber;
    int productId;
    int amount;

}
