package com.service.Billing.dto;

import lombok.Data;

@Data
public class DeleteDto {
    private int taxNumber;
    private int productId;
}
