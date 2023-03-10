package com.service.Billing.service;
import com.service.Billing.response.ResponseMain;
import com.service.Billing.dto.CompanyCheckDto;
import com.service.Billing.dto.CompanyStatusDto;
import com.service.Billing.dto.PayyingDto;

public interface BillingService {
    
    ResponseMain newPayment(PayyingDto payyingDto,ResponseMain responseMain);

    ResponseMain status(CompanyStatusDto companyStatusDto);

    ResponseMain check(CompanyCheckDto companyCheckDto);

    
}
