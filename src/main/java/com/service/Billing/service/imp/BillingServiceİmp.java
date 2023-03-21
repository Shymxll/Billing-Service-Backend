package com.service.Billing.service.imp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        int taxNumber = payyingDto.getTaxNumber();
        if(compOptional.isPresent()){
            PayResponse payResponse = new PayResponse();
            Billing bill = new Billing();
            String transactionId = createTransactionId();
            bill.setAmount(payyingDto.getAmount());
            bill.setMonthlyAmount(compOptional.get().getMonthlyAmount());
            bill.setPayDate(new Date());
            bill.setTaxNumber(payyingDto.getTaxNumber());
            bill.setProductId(payyingDto.getProductId());
            bill.setTransactionId(transactionId);
            billingRepo.save(bill);
        
        //created response
            newResponseMain.setMessage("Success");
            newResponseMain.setCode(0);
            payResponse = createBillingResponseBody(bill);
            list.clear();
            list.add(payResponse);
            newResponseMain.setResponse(list);
            log.info(taxNumber+":  Succesfull payying!" +"  transactionId:" +bill.getTransactionId());

        }
        else{
            newResponseMain.setResponse(null);
            log.info(taxNumber+" Unsuccelfuly payying! TaxNumber or ProductId is wrong");
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
            log.info("Status query sent \n"+" Tax number:"+bill.get().getTaxNumber()+"Transaction id:"+ bill.get().getTransactionId());
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
        String id = "nsp" + System.currentTimeMillis();
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
            log.info("Checked company with tax number\n"+" Tax number:"+ccd.getTaxNumber());
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
