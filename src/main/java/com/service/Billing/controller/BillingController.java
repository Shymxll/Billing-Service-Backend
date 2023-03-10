package com.service.Billing.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.Billing.dto.CompanyCheckDto;
import com.service.Billing.dto.CompanyStatusDto;
import com.service.Billing.dto.PayyingDto;
import com.service.Billing.response.ResponseMain;
import com.service.Billing.service.BillingService;
import com.service.Billing.service.CompanyService;


@RestController
@RequestMapping("/bill")

public class BillingController {
    CompanyService companyService;
    BillingService billingService;
    
    public BillingController(CompanyService companyService, BillingService billingService) {
        this.companyService = companyService;
        this.billingService = billingService;
    }

    @PostMapping("/pay")
    public ResponseMain newPayment(@RequestBody PayyingDto payyingDto){
        
        return billingService.newPayment(payyingDto,(companyService.newPayment(payyingDto)));

        
    }
    @PostMapping("/check")
    public ResponseMain check(@RequestBody CompanyCheckDto companyCheckDto){
        return billingService.check(companyCheckDto);
    }

    @PostMapping("/status")
    public ResponseMain status(@RequestBody CompanyStatusDto csd){

        return billingService.status(csd);
    }
   
    
}
