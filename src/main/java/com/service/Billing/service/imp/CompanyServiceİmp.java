package com.service.Billing.service.imp;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.service.Billing.dto.CompanyRegDto;
import com.service.Billing.dto.CompanyUpdtDto;
import com.service.Billing.dto.DeleteDto;
import com.service.Billing.dto.ExpireDto;
import com.service.Billing.dto.PayyingDto;
import com.service.Billing.entity.Company;
import com.service.Billing.repos.CompanyRepo;
import com.service.Billing.response.CompanyResponse;
import com.service.Billing.response.DeleteResponse;
import com.service.Billing.response.ExpireResponse;
import com.service.Billing.response.RegisterResponse;
import com.service.Billing.response.ResponseMain;
import com.service.Billing.service.CompanyService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceİmp implements CompanyService{
    private final CompanyRepo companyRepo;
    private List<Object> list = new ArrayList<>();

    @Override
    public ResponseMain createNewCompany(CompanyRegDto companyRegDto) {
        
         List<Object> list = new ArrayList<>();
        if(!companyRegDto.getName().isEmpty() && companyRegDto.getProductId() != 0){

            Company comp = new Company();
            comp.setName(companyRegDto.getName());
            comp.setPassword(companyRegDto.getPassword());
            comp.setTaxNumber(companyRegDto.getTaxNumber());
            comp.setProductId(companyRegDto.getProductId());
            comp.setVersion("Demo");
            comp.setWallet(0);
            comp.setEx_date(demoDateMaker());
            comp.setMonthlyAmount(companyRegDto.getMonthlyAmount());
            companyRepo.save(comp);
            //response
            list.clear();
            list.add(new RegisterResponse(true));
            return new ResponseMain(0,"Success",list);
        }
        return new ResponseMain(1,"Error",null);
    }

    private Calendar currenDatePicker(){
       
       
        java.util.Date newData = new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(newData);
        
        return c;
    }

    private Date demoDateMaker(){
        
        Calendar calendar = currenDatePicker();
        calendar.add(Calendar.MONTH, 1);    
        return calendar.getTime();
    }

    @Override
    public ResponseMain updateCompany(CompanyUpdtDto companyUpdtDto) {
        Optional<Company> company = companyRepo.findByTaxNumberAndProductId(companyUpdtDto.getTaxNumber(),companyUpdtDto.getProductId());
        
        if(company.isPresent()){
            Company newCompany = company.get();
            newCompany.setMonthlyAmount(companyUpdtDto.getMonthlyAmount());
            companyRepo.save(newCompany);
            //response
             list.clear();
             list.add(newCompany);
            return new ResponseMain(0,"Success",list);
        }
        return new ResponseMain(1,"Error",null);
    }

    @Override
    public ResponseMain getCompanyById(long id) {
       Optional<Company> company = companyRepo.findById(id);
       if(company.isPresent()){
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName(company.get().getName().toString());
        companyResponse.setTaxNumber(company.get().getTaxNumber());
        companyResponse.setEx_date(company.get().getEx_date());
        companyResponse.setMonthlyAmount(company.get().getMonthlyAmount());
        companyResponse.setProductId(company.get().getProductId());
        companyResponse.setVersion(company.get().getVersion());
        //company response creating
        list.clear();
        list.add(companyResponse);
        return new ResponseMain(0,"Success",list);
       }
       return new ResponseMain(1,"Error",null);
    }

    @Override
    public ResponseMain deleteCompany(DeleteDto deleteDto) {
        if(deleteDto.getProductId()!=0 && deleteDto.getTaxNumber()!=0){
            Optional<Company> compOptional = companyRepo.findByTaxNumberAndProductId(deleteDto.getTaxNumber(), deleteDto.getProductId());
            if(compOptional.isPresent()){
            companyRepo.deleteById(compOptional.get().getId());
            //response 
            list.clear();
            list.add(new DeleteResponse(true));
            return new ResponseMain(0,"Success",list);
                }
            
       }
       return new ResponseMain(1,"Error",null);
    }

   

    @Override
    public ResponseMain newPayment(PayyingDto payyingDto) {
        ResponseMain responseMain = new ResponseMain(1,"Error",null);
        
            Optional<Company> compO =  searchTaxandProducId(payyingDto.getTaxNumber(), payyingDto.getProductId());
            if(compO.isPresent() && payyingDto!=null){ 
                responseMain.setCode(0);
                responseMain.setMessage("Success");
                addWallet(compO.get(),payyingDto.getAmount());
            }  
        return responseMain;
    }

    @Override
    public Optional<Company> searchTaxandProducId(long taxNumber, long productId) {
        Optional<Company> company = companyRepo.findByTaxNumberAndProductId(taxNumber,productId);
        return company;
    }

    public void changeExDate(Company comp){    
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(comp.getEx_date());        
        long payying_month = comp.getWallet() / 50;           
        calendar.add(Calendar.MONTH,(int) payying_month);    
        comp.setEx_date(calendar.getTime());
        comp.setWallet(comp.getWallet()-(payying_month*50));
        comp.setVersion("Licensed");
        companyRepo.save(comp);
            

    }

    public void addWallet(Company comp,long amount){
          
        comp.setWallet(comp.getWallet()+amount);
        changeExDate(comp);
    }

    @Override
    public Optional<List<Company>> findByTaxNumber(long taxNumber) {
        
        return companyRepo.findByTaxNumber(taxNumber);
    }

    @Override
    public ResponseMain isExpire(ExpireDto expireDto) {
    
       Optional<Company> company = companyRepo.findByTaxNumberAndProductId(expireDto.getTaxNumber(), expireDto.getProductId());

       if(company.isPresent()){
        list.clear();
        if(company.get().getEx_date().compareTo(Calendar.getInstance().getTime()) > 0){
            list.add(new ExpireResponse(true,company.get().getEx_date()));
            return new ResponseMain(0,"Success",list);
        }
        else{
            list.add(new ExpireResponse(false,company.get().getEx_date()));
            return new ResponseMain(1,"Error",list);
        }

       }
       
       
        return new ResponseMain(1,"Error",null);
    }

    

 
    
    
}
