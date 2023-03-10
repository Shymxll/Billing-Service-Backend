package com.service.Billing.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.service.Billing.dto.CompanyRegDto;
import com.service.Billing.dto.CompanyUpdtDto;
import com.service.Billing.dto.DeleteDto;
import com.service.Billing.dto.ExpireDto;
import com.service.Billing.response.ResponseMain;
import com.service.Billing.service.BillingService;
import com.service.Billing.service.CompanyService;


@RestController
@CrossOrigin

public class CompanyController {
    private CompanyService companyService;
    public CompanyController(CompanyService companyService,BillingService billingService) {
        this.companyService = companyService;
    }

    @GetMapping("/admin/{id}")
    public ResponseMain getCompanyById(@PathVariable("id") long id){

        return companyService.getCompanyById(id);
    }   

    @PostMapping("/admin/register")
    public ResponseMain postnewCompany(@RequestBody CompanyRegDto companyRegDto){

       return companyService.createNewCompany(companyRegDto);
    }
    @DeleteMapping("/admin/delete")
    public ResponseMain deleteCompany(@RequestBody DeleteDto deleteDto){
       return companyService.deleteCompany(deleteDto);
    }

    @PutMapping("/admin/update")
    public ResponseMain updateCompany(@RequestBody CompanyUpdtDto companyDto){
        return companyService.updateCompany(companyDto);
    }

    @PostMapping("/isexpire")
    public ResponseMain isExpire(@RequestBody ExpireDto expireDto){

        return companyService.isExpire(expireDto);
    }

}
