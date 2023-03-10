package com.service.Billing.service.imp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.service.Billing.dto.CompanyCheckDto;
import com.service.Billing.dto.CompanyStatusDto;
import com.service.Billing.dto.PayyingDto;
import com.service.Billing.entity.Billing;
import com.service.Billing.entity.Company;
import com.service.Billing.repos.BillingRepo;

import com.service.Billing.response.StatusResponse;
import com.service.Billing.response.CheckResponse;
import com.service.Billing.response.PayResponse;
import com.service.Billing.response.ResponseMain;
import com.service.Billing.service.BillingService;
import com.service.Billing.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingServiceİmp implements BillingService {
    private final BillingRepo billingRepo;
    private final CompanyService companyService;
    private List<Object> list = new ArrayList<>();

    @Override
    public ResponseMain newPayment(PayyingDto payyingDto,ResponseMain responseMain) {
        ResponseMain newResponseMain = new ResponseMain(1,"Error",null);
        
        Optional<Company> compOptional = companyService.searchTaxandProducId(payyingDto.getTaxNumber(),payyingDto.getProductId());
        if(compOptional.isPresent()){
        Billing bill = new Billing();
        PayResponse payResponse = new PayResponse();
        String transactionId = createTransactionId();
        bill.setAmount(payyingDto.getAmount());
        bill.setMonthlyAmount(compOptional.get().getMonthlyAmount());
        bill.setPayDate(new Date());
        bill.setTaxNumber(payyingDto.getTaxNumber());
        bill.setProductId(payyingDto.getProductId());        
        bill.setTransactionId(transactionId);
        billingRepo.save(bill);
        
        //created response
        newResponseMain.setCode(0);
        newResponseMain.setMessage("Success");
        payResponse = createBillingResponseBody(bill);
        list.clear();
        list.add(payResponse);
        newResponseMain.setResponse(list);
        
        }
        else{
            newResponseMain.setResponse(null);
        }
        return newResponseMain;
    }

    @Override
    public ResponseMain status(CompanyStatusDto companyStatusDto) {
        ResponseMain responseMain = new ResponseMain(1, "Error", null);
        Optional<Billing> bill =billingRepo.findByTransactionId(companyStatusDto.getTransactionId());

        if(bill.isPresent()){
            
            StatusResponse statusResponse = new StatusResponse();
            statusResponse.setDatetime(bill.get().getPayDate());
            statusResponse.setTransactionId(bill.get().getTransactionId());
            statusResponse.setAmount(bill.get().getAmount());
            //response creating
            list.clear();
            list.add(statusResponse);
            responseMain.setResponse(list);
            responseMain.setCode(0);
            responseMain.setMessage("Success");
            System.out.println(bill.get().getTransactionId()+"-"+bill.get().getTaxNumber());     
        }
            
        
        return responseMain;
    }
    
    private PayResponse createBillingResponseBody(Billing bill){
        PayResponse payResponse = new PayResponse();
        payResponse.setProductId(bill.getProductId());
        payResponse.setTaxNumber(bill.getTaxNumber());
        payResponse.setTrancitionId(bill.getTransactionId());
        payResponse.setDatetime(bill.getPayDate());
        payResponse.setStatus(1);
       
        return payResponse;

    }
    private String createTransactionId(){
        String id = "nps" + System.currentTimeMillis();
        return id;
    }

    @Override
    public ResponseMain check(CompanyCheckDto ccd) {
           Optional<List<Company>> companyListO = companyService.findByTaxNumber(ccd.getTaxNumber());
           ResponseMain responseMain = new ResponseMain(1,"Error",null);
           
    
           if(companyListO.get().size()!=0){
            responseMain.setCode(0);
            responseMain.setMessage("Success");
            responseMain.setResponse(companyListO.get().stream().map(p ->mappingResponse(p)).collect(Collectors.toList()));
           }
           return responseMain;
            
         
    }
    private CheckResponse mappingResponse(Company company){
        CheckResponse checkResponse = new CheckResponse();
        if(company.getTaxNumber() != 0){
            checkResponse.setName(company.getName());
            checkResponse.setTaxNumber(company.getTaxNumber());
            checkResponse.setMonthlyAmount(company.getMonthlyAmount());
            checkResponse.setProductId(company.getProductId());
            checkResponse.setMonthlyAmount(company.getMonthlyAmount());
        }

        return checkResponse;
    }
    
    
   



    
}
