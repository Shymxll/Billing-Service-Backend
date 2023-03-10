package com.service.Billing.response;

import java.util.Date;

import lombok.Data;

@Data
public class CompanyResponse {
    String name;
    int taxNumber;
    int productId;
    String version;
    int monthlyAmount;
    Date ex_date;
}
