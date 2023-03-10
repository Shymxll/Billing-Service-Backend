package com.service.Billing.dto;

import lombok.Data;

@Data
public class CompanyRegDto {
    String name;
    String password;
    int taxNumber;
    int monthlyAmount;
    int productId;
}
