package com.service.Billing.response;

import lombok.Data;

@Data
public class CheckResponse{
        String name;
        int taxNumber;
        int productId;
        int monthlyAmount;

}