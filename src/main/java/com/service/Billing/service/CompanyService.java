package com.service.Billing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.service.Billing.dto.CompanyRegDto;
import com.service.Billing.dto.CompanyUpdtDto;
import com.service.Billing.dto.DeleteDto;
import com.service.Billing.dto.ExpireDto;
import com.service.Billing.dto.PayyingDto;
import com.service.Billing.entity.Company;
import com.service.Billing.response.ResponseMain;

@Service
public interface CompanyService {
    
    ResponseMain createNewCompany(CompanyRegDto companyDto);

    Optional<Company> searchTaxandProducId(long taxNumber,long productId);

    ResponseMain updateCompany(CompanyUpdtDto companyDto);

    ResponseMain getCompanyById(long id);

    ResponseMain deleteCompany(DeleteDto deleteDto);

    Optional<List<Company>>  findByTaxNumber(long taxNumber);

    ResponseMain newPayment(PayyingDto payyingDto);

    ResponseMain isExpire(ExpireDto expireDto);
}
