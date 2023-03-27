package com.service.Billing.controller;

import com.service.Billing.entity.Company;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;
import java.util.Optional;

import static com.service.Billing.config.SecurityConfig.SECURITY_CONFIG_NAME;

@Slf4j
@RestController
@CrossOrigin

@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class CompanyController {
    private CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping("/admin/id/{id}")
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

    @GetMapping("admin/tax/{tax}")
    public ResponseMain getByTaxNumber(@PathVariable("tax") int tax){

        return companyService.getByTaxNumber(tax);
    }

}
