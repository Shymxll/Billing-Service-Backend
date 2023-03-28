package com.service.Billing.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

import static com.service.Billing.config.SecurityConfig.SECURITY_CONFIG_NAME;


@Slf4j
@RestController
@RequestMapping("/bill")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class BillingController {
    CompanyService companyService;
    BillingService billingService;
    
    public BillingController(CompanyService companyService, BillingService billingService) {
        this.companyService = companyService;
        this.billingService = billingService;

    }
    @Cacheable(cacheNames="bill", key="#payyingdto.taxNumber")
    @PostMapping("/pay")
    public ResponseMain newPayment(@RequestBody PayyingDto payyingDto){
        return billingService.newPayment(payyingDto,(companyService.newPayment(payyingDto)));

        
    }
    @Cacheable(cacheNames="bill", key="#companyCheckDto.taxNumber")
    @PostMapping("/check")
    public ResponseMain check(@RequestBody CompanyCheckDto companyCheckDto){
        return billingService.check(companyCheckDto);
    }

    @Cacheable(cacheNames="bill", key="#companyStatusDto.taxNumber")
    @PostMapping("/status")
    public ResponseMain status(@RequestBody CompanyStatusDto companyStatusDto){

        return billingService.status(companyStatusDto);
    }
   
    
}
