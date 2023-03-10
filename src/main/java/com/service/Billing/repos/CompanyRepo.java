package com.service.Billing.repos;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.Billing.entity.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Long>{
   
    Company findByName(String name);

    Optional<List<Company>>  findByTaxNumber(long taxNumber);

    Optional<Company> findByTaxNumberAndProductId(long taxNumber, long productId);
    
}
