package com.service.Billing.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.Billing.entity.Billing;

@Repository
public interface BillingRepo extends JpaRepository<Billing,Long>{

    Optional<Billing> findByTransactionId(String transactionId);
    
}
